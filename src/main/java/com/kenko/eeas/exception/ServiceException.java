package com.kenko.eeas.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException{
    private final Integer code;

    public ServiceException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
