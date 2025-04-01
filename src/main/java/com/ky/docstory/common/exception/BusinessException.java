package com.ky.docstory.common.exception;

import com.ky.docstory.common.code.DocStoryResponseCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final DocStoryResponseCode errorCode;

    public BusinessException(DocStoryResponseCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
