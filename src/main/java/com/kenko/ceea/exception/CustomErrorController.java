package com.kenko.ceea.exception;

import cn.hutool.log.Log;
import com.kenko.ceea.common.Constants;
import com.kenko.ceea.common.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Map;


// 自定义错误处理器
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomErrorController extends BasicErrorController {
    private static final Log log = Log.get();

    @Value("${server.error.path:${error.path:/error}}")
    private String path;

    // TODO: 搞懂这个报错
    public CustomErrorController(ServerProperties serverProperties) {
        super(new DefaultErrorAttributes(), serverProperties.getError());
    }

    // 重写 postman请求的JSON响应
    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        Result result = Result.error(Constants.CODE_404, "Api not found");
        return new ResponseEntity<>(result.toMap(), HttpStatus.NOT_FOUND);
    }

    // 重写 浏览器请求的HTML响应
    @Override
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        // TODO: web访问时的错误处理
        HttpStatus status = getStatus(request);
        Map<String, Object> model = Collections
                .unmodifiableMap(getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.TEXT_HTML)));
        response.setStatus(status.value());
        ModelAndView modelAndView = resolveErrorView(request, response, status, model);
        return (modelAndView != null) ? modelAndView : new ModelAndView("error", model);
    }
}
