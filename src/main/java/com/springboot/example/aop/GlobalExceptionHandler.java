package com.springboot.example.aop;

import com.springboot.example.util.ErrorPrintUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {BindException.class, ConstraintViolationException.class, MethodArgumentNotValidException.class})
    public Map<String, String> handle(Exception e) {
        ErrorPrintUtil.printErrorMsg(log, e, ">>>>> exception handling has been invoked");

        StringBuilder messageBuilder = new StringBuilder();
        if (e instanceof MethodArgumentNotValidException) {     // json 参数校验无效异常
            List<ObjectError> allErrors = ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors();
            allErrors.forEach(error -> messageBuilder.append(error.getDefaultMessage()).append("; "));
        } else if (e instanceof BindException) {    // 表单参数校验无效异常
            List<ObjectError> allErrors = ((BindException) e).getAllErrors();
            allErrors.forEach(error -> messageBuilder.append(error.getDefaultMessage()).append("; "));
        } else if (e instanceof ConstraintViolationException) {     // 单个参数校验无效异常
            Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) e).getConstraintViolations();
            constraintViolations.forEach(v -> messageBuilder.append(v.getMessage()).append("; "));
        } else {
            messageBuilder.append("parameter not valid").append("; ");
        }
        Map<String, String> map = new HashMap<>();
        map.put("message", messageBuilder.substring(0, messageBuilder.length() - 2));
        return map;
    }
}
