package com.example.forum.exception;

import com.example.forum.common.AppResult;

/**
 * 自定义异常
 */
public class ApplicationException extends RuntimeException {

    protected AppResult errorResult;

    /**
     * 构造方法
     * @param errorResult
     */
    public ApplicationException (AppResult errorResult) {
        super(errorResult.getMessage());
        this.errorResult = errorResult;
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public AppResult getErrorResult() {
        return errorResult;
    }
}
