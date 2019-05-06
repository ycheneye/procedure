package com.springbootmybatis.procedure.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.springbootmybatis.procedure.beans.ResultEnums;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @作者 chenyi
 * @date 2019/4/16 15:13
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultResponse<T> {
    private int code;
    @JsonProperty("message")
    private String msg;
    @JsonInclude(JsonInclude.Include.NON_NULL)//返回json时忽略为null的属性
    private T data;

    public ResultResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResultResponse fail(){
        return new ResultResponse(ResultEnums.FAIL.getCode(), ResultEnums.FAIL.getMessage());
    }

    public static ResultResponse fail(String msg){
        return new ResultResponse(ResultEnums.FAIL.getCode(), msg);
    }

    public static <T>ResultResponse fail(String msg,T data){
        return new ResultResponse(ResultEnums.FAIL.getCode(), msg ,data);
    }

    public static <T>ResultResponse fail(T data){
        return new ResultResponse(ResultEnums.FAIL.getCode(), ResultEnums.FAIL.getMessage() ,data);
    }

    public static ResultResponse success(){
        return new ResultResponse(ResultEnums.SUCCESS.getCode(), ResultEnums.SUCCESS.getMessage());
    }

    public static <T>ResultResponse success(T data){
        return new ResultResponse(ResultEnums.SUCCESS.getCode(), ResultEnums.SUCCESS.getMessage() ,data);
    }
}
