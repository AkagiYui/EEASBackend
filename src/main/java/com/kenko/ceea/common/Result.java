package com.kenko.ceea.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 接口统一返回包装类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Integer code;
    private String msg;
    private Object data;


    public static Result success() {
        return new Result(Constants.CODE_200, "", null);
    }

    public static Result success(Object data) {
        return new Result(Constants.CODE_200, "", data);
    }

    public static Result error() {
        return new Result(Constants.CODE_500, "系统错误", null);
    }
    public static Result error(Integer code) {
        return new Result(code, "", null);
    }
    public static Result error(Integer code, String msg) {
        return new Result(code, msg, null);
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("code", code);
        hashMap.put("msg", msg);
        hashMap.put("data", data);
        return hashMap;
    }
}
