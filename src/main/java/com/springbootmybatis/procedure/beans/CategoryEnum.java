package com.springbootmybatis.procedure.beans;

import lombok.Getter;

/**
 * @作者 chenyi
 * @date 2019/5/5 15:27
 */
@Getter
public enum CategoryEnum {
    CATEGORY_NORMAL(1,"正常"),
    CATEGORY_SCRAP(2,"已废弃");

    private int code;
    private String msg;

    CategoryEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
