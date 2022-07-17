package com.kenko.eeas.controller;

import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kenko.eeas.common.HTTPCode;
import com.kenko.eeas.common.Result;
import com.kenko.eeas.controller.dto.*;
import com.kenko.eeas.controller.vo.QuestionVO;
import com.kenko.eeas.controller.vo.SurveyDetailVO;
import com.kenko.eeas.controller.vo.SurveySubmitVO;
import com.kenko.eeas.entity.*;
import com.kenko.eeas.service.*;
import com.kenko.eeas.utils.TokenUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/survey")
public class SurveyController {
    private static final Log log = Log.get();

    @Resource
    private ISurveyService surveyService;

    @Resource
    private IQuestionService questionService;

    @Resource
    private IQuestionTypeService questionTypeService;

    @Resource
    private IChoiceService choiceService;

    @Resource
    private IAnswerService answerService;

    // 获取当前用户数据总览
    @GetMapping("/overview")
    public Result getOverview() {
        Teacher currentTeacher = TokenUtils.getCurrentTeacher();
        assert currentTeacher != null;
        OverviewDTO overviewDTO = new OverviewDTO();

        QueryWrapper<Survey> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id", currentTeacher.getId());
        queryWrapper.eq("is_deleted", false);

        long surveyCount = 0L;
        long activeSurveyCount = 0L;
        long submitCount = 0L;

        List<Survey> list = surveyService.list(queryWrapper);
        for (Survey survey : list) {
            surveyCount++;
            if (survey.getIsActive()) {
                activeSurveyCount++;
            }
            submitCount += survey.getSubmitCount();
        }

        overviewDTO.setTotalSurvey(surveyCount);
        overviewDTO.setActiveSurvey(activeSurveyCount);
        overviewDTO.setSubmitCount(submitCount);
        return Result.success(overviewDTO);
    }

    // 分页获取问卷列表
    @GetMapping("/page/{pageNum}/{pageSize}")
    public Result getSurveyList(@PathVariable Integer pageNum, @PathVariable Integer pageSize) {
        Teacher currentTeacher = TokenUtils.getCurrentTeacher();
        assert currentTeacher != null;
        QueryWrapper<Survey> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id", currentTeacher.getId());
        queryWrapper.eq("is_deleted", false);
        queryWrapper.orderByDesc("create_time");
        Page<Survey> page = surveyService.page(new Page<>(pageNum, pageSize), queryWrapper);
        return Result.success(page);
    }

    // 创建问卷，返回问卷id
    @PostMapping("/create")
    public Result createSurvey() {
        Teacher currentTeacher = TokenUtils.getCurrentTeacher();
        assert currentTeacher != null;
        Survey newSurvey = new Survey();
        String idStr = IdWorker.getIdStr();
        newSurvey.setId(idStr);
        newSurvey.setTitle("请输入标题");
        newSurvey.setIsActive(false);
        newSurvey.setIsDeleted(false);
        newSurvey.setTeacherId(currentTeacher.getId());
        if (surveyService.save(newSurvey)) {
            return Result.success(idStr);
        }
        return Result.error(HTTPCode.SYSTEM_ERROR, "创建失败");
    }

    // 删除问卷
    @PostMapping("/delete/{id}")
    public Result deleteSurvey(@PathVariable String id) {
        Survey one = getNotDeletedSurvey(id, false, true);
        if (one == null) {
            return Result.error(HTTPCode.NOT_FOUND, "问卷不存在");
        }
        one.setIsDeleted(true);
        return Result.success(surveyService.updateById(one));
    }

    // 切换问卷发布状态
    @PostMapping("/switch/{id}/{active}")
    public Result switchSurveyStatus(@PathVariable String id, @PathVariable Boolean active) {
        Survey one = getNotDeletedSurvey(id, false, true);
        if (one == null) {
            return Result.error(HTTPCode.NOT_FOUND, "问卷不存在");
        }
        if (one.getIsActive() == active) {
            return Result.error(HTTPCode.NOTHING_CHANGE, "问卷状态已经是该状态");
        }
        one.setIsActive(active);
        return Result.success(surveyService.updateById(one));
    }

    // 获取问卷详情
    @GetMapping("/detail/{id}")
    public Result getSurveyDetail(@PathVariable String id) {
        SurveyDetailDTO surveyDetailDTO = new SurveyDetailDTO();

        // 获取问卷
        Survey one = getNotDeletedSurvey(id, false, true);
        if (one == null) {
            return Result.error(HTTPCode.NOT_FOUND, "问卷不存在");
        }
        BeanUtils.copyProperties(one, surveyDetailDTO);
        surveyDetailDTO.setQuestionTypes(questionTypeService.list());
        surveyDetailDTO.setQuestions(getAllQuestions(one.getId()));
        return Result.success(surveyDetailDTO);
    }

    // 修改所有信息，包括问题
    @PostMapping("/detail/{id}")
    public Result setSurveyDetail(@PathVariable String id, @RequestBody @NotNull SurveyDetailVO surveyDetailVO) {
        Survey one = getNotDeletedSurvey(id, false, true);
        if (one == null) {
            return Result.error(HTTPCode.NOT_FOUND, "问卷不存在");
        }
        if (surveyDetailVO.getTitle() != null && !surveyDetailVO.getTitle().isEmpty()) {
            one.setTitle(surveyDetailVO.getTitle());
        }
        if (surveyDetailVO.getDescription() != null && !surveyDetailVO.getDescription().isEmpty()) {
            one.setDescription(surveyDetailVO.getDescription());
        }
        one.setPassword(surveyDetailVO.getPassword());
        if (!surveyService.updateById(one)) {
            return Result.error(HTTPCode.SYSTEM_ERROR, "问卷信息修改失败");
        }

        // 修改问题
        List<QuestionDTO> newQuestions = surveyDetailVO.getQuestions();
        // 删除问题
        List<String> newQuestionIds = new ArrayList<>();
        newQuestions.forEach(question -> newQuestionIds.add(question.getId()));
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("survey_id", one.getId());
        questionService.list(queryWrapper).forEach(question -> {
            if (!newQuestionIds.contains(question.getId())) {
                questionService.removeById(question.getId());
            }
        });

        // 修改或添加问题
        for (int i = 0; i < newQuestions.size(); i++) {
            QuestionDTO questionDTO = newQuestions.get(i);
            Question question = questionService.getById(questionDTO.getId());
            if (question == null) {
                // 问题不存在，则新建问题
                question = new Question();
                question.setNumber(i + 1);
                question.setSurveyId(one.getId());
                question.setQuestionTypeId(questionDTO.getType());
                question.setContent(questionDTO.getContent());
                if (!questionService.save(question)) {
                    return Result.error(HTTPCode.SYSTEM_ERROR, "问题新增失败");
                }
            } else {
                // 问题存在，则修改问题
                question.setContent(questionDTO.getContent());
                question.setNumber(i + 1);
                if (!questionService.updateById(question)) {
                    return Result.error(HTTPCode.SYSTEM_ERROR, "问题信息修改失败");
                }
            }

            // 修改问题的选项
            if  (!(questionDTO.getType().equals("1") || questionDTO.getType().equals("2"))) {
                continue;
            }
            List<ChoiceDTO> newSelections = questionDTO.getSelections();
            // 删除选项
            List<String> newChoiceIds = new ArrayList<>();
            newSelections.forEach(choice -> newChoiceIds.add(choice.getId()));
            QueryWrapper<Choice> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("question_id", question.getId());
            choiceService.list(queryWrapper1).forEach(choice -> {
                if (!newChoiceIds.contains(choice.getId())) {
                    choiceService.removeById(choice.getId());
                }
            });
            // 修改或添加选项
            for (int j = 0; j < newSelections.size(); j++) {
                ChoiceDTO choiceDTO = newSelections.get(j);
                Choice choice = choiceService.getById(choiceDTO.getId());
                if (choice == null) {
                    // 选项不存在，则新建选项
                    choice = new Choice();
                    choice.setNumber(j + 1);
                    choice.setQuestionId(question.getId());
                    choice.setContent(choiceDTO.getContent());
                    choice.setCount(0);
                    if (!choiceService.save(choice)) {
                        return Result.error(HTTPCode.SYSTEM_ERROR, "选项新增失败");
                    }
                } else {
                    // 选项存在，则修改选项
                    choice.setContent(choiceDTO.getContent());
                    choice.setNumber(j + 1);
                    if (!choiceService.updateById(choice)) {
                        return Result.error(HTTPCode.SYSTEM_ERROR, "选项信息修改失败");
                    }
                }
            }

        }
        return Result.success();
    }

    // 获取问卷回答数据
    @GetMapping("/data/{id}")
    public Result getAnswerData(@PathVariable String id) {
        Survey survey = getNotDeletedSurvey(id, false, true);
        if (survey == null) {
            return Result.error(HTTPCode.NOT_FOUND, "问卷不存在");
        }

        SurveyDataDTO result = new SurveyDataDTO();
        BeanUtils.copyProperties(survey, result);

        List<QuestionDataDTO> questionDataDTOs = new ArrayList<>();
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("survey_id", survey.getId());
        List<Question> questions = questionService.list(queryWrapper);
        for (Question question: questions) {
            QuestionDataDTO questionDataDTO = new QuestionDataDTO();
            BeanUtils.copyProperties(question, questionDataDTO);

            List<AnswerDataDTO> answerDataDTOs = new ArrayList<>();
            String questionTypeId = questionDataDTO.getQuestionTypeId();
            if (questionTypeId.equals("1") || questionTypeId.equals("2")) {
                QueryWrapper<Choice> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("question_id", question.getId());
                queryWrapper1.orderByAsc("number");
                List<Choice> choices = choiceService.list(queryWrapper1);
                for (Choice choice: choices) {
                    AnswerDataDTO answerDataDTO = new AnswerDataDTO();
                    BeanUtils.copyProperties(choice, answerDataDTO);
                    answerDataDTOs.add(answerDataDTO);
                }
            } else {
                QueryWrapper<Answer> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("question_id", question.getId());
                List<Answer> answers = answerService.list(queryWrapper1);
                for (Answer answer : answers) {
                    AnswerDataDTO answerDataDTO = new AnswerDataDTO();
                    BeanUtils.copyProperties(answer, answerDataDTO);
                    answerDataDTOs.add(answerDataDTO);
                }
            }
            questionDataDTO.setAnswers(answerDataDTOs);
            questionDataDTOs.add(questionDataDTO);
        }
        result.setQuestions(questionDataDTOs);
        return Result.success(result);
    }

    // 答题者 获取问卷卷面
    @GetMapping("/sheet/{id}")
    public Result getSurveySheet(@PathVariable String id, @RequestParam(required = false) String password) {
        Survey one = getNotDeletedSurvey(id, true, false);
        if (one == null) {
            return Result.error(HTTPCode.NOT_FOUND, "问卷不存在");
        }
        if (one.getPassword() != null && !one.getPassword().equals("")) {
            if (!one.getPassword().equals(password)) {
                return Result.error(HTTPCode.NOT_PERMIT, "密码错误");
            }
        }
        SheetDTO sheetDTO = new SheetDTO();
        BeanUtils.copyProperties(one, sheetDTO);
        sheetDTO.setQuestionTypes(questionTypeService.list());
        sheetDTO.setQuestions(getAllQuestions(one.getId()));
        return Result.success(sheetDTO);
    }

    // 答题者 回答问卷
    @PostMapping("/submit/{id}")
    public Result submitAnswer(@PathVariable String id, @RequestBody @NotNull SurveySubmitVO surveySubmitVO) {
        Survey one = getNotDeletedSurvey(id, true, false);
        if (one == null) {
            return Result.error(HTTPCode.NOT_FOUND, "问卷不存在");
        }
        if (one.getPassword() != null && !one.getPassword().equals("")) {
            if (!one.getPassword().equals(surveySubmitVO.getPassword())) {
                return Result.error(HTTPCode.NOT_PERMIT, "密码错误");
            }
        }

        one.setSubmitCount(one.getSubmitCount() + 1); // 提交次数+1

        // 重复问题取第一个
        List<QuestionVO> rawQuestionsVO = surveySubmitVO.getQuestions();
        List<QuestionVO> questionsVO = new ArrayList<>();
        rawQuestionsVO.forEach(questionVO -> {
            if (questionsVO.stream().noneMatch(q -> q.getId().equals(questionVO.getId()))) {
                questionsVO.add(questionVO);
            }
        });

        List<String> answeredQuestionIds = new ArrayList<>();
        for (QuestionVO questionVO : questionsVO) {
            if (answeredQuestionIds.contains(questionVO.getId())) {
                log.warn(questionVO.getId() + " 问题重复回答");
                continue;
            }
            QueryWrapper<Question> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("id", questionVO.getId());
            queryWrapper1.eq("survey_id", one.getId());
            Question question = questionService.getOne(queryWrapper1);
            if (question == null) {
                log.error(questionVO.getId() + " 问题不存在");
                continue;
            }
            List<String> answerList = questionVO.getAnswers();
            if (answerList == null || answerList.size() == 0) {
                log.error(questionVO.getId() + " 答案为空");
                continue;
            }
            if (!(question.getQuestionTypeId().equals("1") || question.getQuestionTypeId().equals("2"))) {
                // 非选择题，记录答案
                Answer answer = new Answer();
                answer.setQuestionId(question.getId());
                answer.setContent(questionVO.getAnswers().get(0));
                if (!answerService.save(answer)) {
                    log.error(question.getId() + " " + questionVO.getAnswers().get(0) + " 答案保存失败");
                }
            } else {
                // 选择题，记录答案
                List<String> answeredChoiceIds = new ArrayList<>();
                for (String answer : questionVO.getAnswers()) {
                    if (answeredChoiceIds.contains(answer)) {
                        log.warn(question.getId() + " 选项重复回答");
                        continue;
                    }
                    QueryWrapper<Choice> queryWrapper2 = new QueryWrapper<>();
                    queryWrapper2.eq("question_id", question.getId());
                    queryWrapper2.eq("id", answer);
                    Choice choice = choiceService.getOne(queryWrapper2);
                    if (choice == null) {
                        log.error(question.getId() + " " + answer + " 选项不存在");
                    } else {
                        choice.setCount(choice.getCount() + 1);
                        if (!choiceService.updateById(choice)) {
                            log.error(question.getId() + " " + answer + " 选项答案修改失败");
                        } else {
                            answeredChoiceIds.add(answer);
                        }
                    }
                }
            }
            answeredQuestionIds.add(question.getId());
        }
        return Result.success();
    }

    // 根据id获取未删除的问卷
    private Survey getNotDeletedSurvey(String id, Boolean needActive, Boolean needLogin) {
        QueryWrapper<Survey> queryWrapper = new QueryWrapper<>();
        if (needLogin) {
            Teacher currentTeacher = TokenUtils.getCurrentTeacher();
            if (currentTeacher == null) {
                return null;
            }
            queryWrapper.eq("teacher_id", currentTeacher.getId());
        }
        if (needActive) {
            queryWrapper.eq("is_active", true);
        }
        queryWrapper.eq("id", id);
        queryWrapper.eq("is_deleted", false);
        return surveyService.getOne(queryWrapper);
    }

    // 获取问卷所有问题
    private List<QuestionDTO> getAllQuestions(String surveyId) {
        QueryWrapper<Question> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("survey_id", surveyId);
        queryWrapper2.orderByAsc("number");
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        questionService.list(queryWrapper2).forEach(question -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setType(question.getQuestionTypeId());

            String type = questionDTO.getType();
            if (type.equals("1") || type.equals("2")) {  // 单选或多选
                List<ChoiceDTO> choiceDTOList = new ArrayList<>();
                QueryWrapper<Choice> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("question_id", question.getId());
                queryWrapper1.orderByAsc("number");
                choiceService.list(queryWrapper1).forEach(choice -> {
                    ChoiceDTO choiceDTO = new ChoiceDTO();
                    BeanUtils.copyProperties(choice, choiceDTO);
                    choiceDTOList.add(choiceDTO);
                });
                questionDTO.setSelections(choiceDTOList);
            }
            questionDTOList.add(questionDTO);
        });
        return questionDTOList;
    }
}
