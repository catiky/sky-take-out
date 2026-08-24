package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 * 属于 SpringMVC 全局异常处理注解
 *  @ Slf4j **自动帮你生成日志对象 log，不用手动 new Logger**。
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * 用BaseException来捕获所有的业务异常，BaseException是所有业务异常的父类 也就是AccountLockedException的父类
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
//        打印日志 这 是 **Lombok 的 @Slf4j 日志打印语句**
        log.error("异常信息：{}", ex.getMessage());
//        前端页面返回结果
        return Result.error(ex.getMessage());
    }
    /**
     * 捕获其他异常
     * 用户名重复
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex){
       //Duplicate entry 'zhangsan' for key 'employee.idx_username'
        String message = ex.getMessage();
        if(message.contains("Duplicate entry")){
            String[] meg=ex.getMessage().split(" ");
            String msg=meg[2]+ MessageConstant.ALREADY_EXISTS;
            return Result.error(msg);
        }
        return Result.error(MessageConstant.UNKNOWN_ERROR);
    }
}
