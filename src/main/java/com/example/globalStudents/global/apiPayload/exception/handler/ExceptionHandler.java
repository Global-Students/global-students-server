package com.example.globalStudents.global.apiPayload.exception.handler;

import com.example.globalStudents.global.apiPayload.code.BaseErrorCode;
import com.example.globalStudents.global.apiPayload.exception.GeneralException;

public class ExceptionHandler extends GeneralException {

    public ExceptionHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
