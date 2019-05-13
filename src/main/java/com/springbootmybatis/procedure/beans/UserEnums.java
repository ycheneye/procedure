package com.springbootmybatis.procedure.beans;

import lombok.Getter;

/**
 * @作者 chenyi
 * @date 2019/5/7 19:10
 */
@Getter
public enum  UserEnums {

    USERNAME_PASSWORD_EMPTY(500,"用户名密码不能为空"),
    USERNAME_PASSWORD_ERROR(500,"用户名或密码错误");
    private int code;
    private String msg;

    UserEnums(int code,String msg){
        this.code = code;
        this.msg = msg;
    }
}

