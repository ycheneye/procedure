package com.springbootmybatis.procedure.exception;

import com.springbootmybatis.procedure.beans.ResultEnums;
import lombok.Data;

@Data
public class CustomException extends RuntimeException{
    //出现异常都是500
    private int code= ResultEnums.FAIL.getCode();
    private String message;
    public CustomException(String message){
        super(message);
        this.message=message;
    }
}
