package com.wpay.core.merchant.exception;

import com.wpay.core.merchant.global.dto.ErrorResponse;
import com.wpay.core.merchant.global.dto.ErrorResponseV2;
import com.wpay.core.merchant.global.exception.CustomException;
import com.wpay.core.merchant.global.exception.ErrorCode;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;

@Log4j2
@RestControllerAdvice
public class MpiBasicInfoExceptionHandler {

    @ExceptionHandler({ CustomException.class })
    protected ResponseEntity<?> handleCustomException(CustomException ex) {
        this.logWriteExceptionStackTrace(ex);
        /* ex에 wtid 또는 mid가 있다면 ErrorResponseV2로 세팅 한다. */
        if(Strings.isNotBlank(ex.getWtid()) || Strings.isNotBlank(ex.getMid())){
            ErrorResponseV2 errorResponseV2 = ErrorResponseV2.builder()
                    .httpStatus(ex.getErrorCode().getStatus())
                    .message(Strings.isNotBlank(ex.getMessage()) ? ex.getMessage() : ex.getErrorCode().getMessage())
                    .error(ex.getErrorCode().getStatus().series().name().toLowerCase())
                    .mid(ex.getMid())
                    .wtid(ex.getWtid())
                    .build();
            return ResponseEntity.status(ex.getErrorCode().getStatus()).body(errorResponseV2);
        }

        ErrorResponse errorResponse = ErrorResponse.builder()
                .httpStatus(ex.getErrorCode().getStatus())
                .message(Strings.isNotBlank(ex.getMessage()) ? ex.getMessage() : ex.getErrorCode().getMessage())
                .error(ex.getErrorCode().getStatus().series().name().toLowerCase())
                .build();
        return ResponseEntity.status(ex.getErrorCode().getStatus()).body(errorResponse);
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    protected ResponseEntity<?> handleServerException(ConstraintViolationException ex) {
        this.logWriteExceptionStackTrace(ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .httpStatus(ErrorCode.INVALID_PARAMETER.getStatus())
                .message(Strings.isNotBlank(ex.getMessage()) ? ex.getMessage() : ErrorCode.INVALID_PARAMETER.getMessage())
                .error(ErrorCode.INVALID_PARAMETER.getStatus().series().name().toLowerCase())
                .build();
        return ResponseEntity.status(ErrorCode.INVALID_PARAMETER.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        this.logWriteExceptionStackTrace(ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .httpStatus(ErrorCode.METHOD_NOT_ALLOWED.getStatus())
                .message(ErrorCode.METHOD_NOT_ALLOWED.getMessage())
                .error(ErrorCode.METHOD_NOT_ALLOWED.getStatus().series().name().toLowerCase())
                .build();
        return ResponseEntity.status(ErrorCode.METHOD_NOT_ALLOWED.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<ErrorResponse> handleNoHandlerFoundExceptionException(NoHandlerFoundException ex) {
        this.logWriteExceptionStackTrace(ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .httpStatus(ErrorCode.DISPLAY_NOT_FOUND.getStatus())
                .message(ErrorCode.DISPLAY_NOT_FOUND.getMessage())
                .error(ErrorCode.DISPLAY_NOT_FOUND.getStatus().series().name().toLowerCase())
                .build();
        return ResponseEntity.status(ErrorCode.DISPLAY_NOT_FOUND.getStatus()).body(errorResponse);
    }

    @ExceptionHandler({ Exception.class })
    protected ResponseEntity<?> handleServerException(Exception ex) {
        this.logWriteExceptionStackTrace(ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .httpStatus(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
                .message(ErrorCode.INTERNAL_SERVER_ERROR.getMessage())
                .error(ErrorCode.INTERNAL_SERVER_ERROR.getStatus().series().name().toLowerCase())
                .build();
        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus()).body(errorResponse);
    }

    private void logWriteExceptionStackTrace(Throwable e){
        final StringBuilder sb = new StringBuilder(e.getClass().getSimpleName()).append(": ").append(e.getMessage()).append("\n");
        for(StackTraceElement se : e.getStackTrace()) {
            sb.append("    ").append(se.getClassName()).append("(")
                    .append(se.getMethodName()).append(":").append(se.getLineNumber()).append(")").append("\n");
        }
        log.error(sb.toString());
    }
}
