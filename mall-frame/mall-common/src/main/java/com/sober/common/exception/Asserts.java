package com.sober.common.exception;

import com.sober.common.api.IErrorCode;

/**
 * 断言处理类，用于抛出各种API异常
 * Create by sg on 2020/8/7
 */

public class Asserts {
    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }
}
