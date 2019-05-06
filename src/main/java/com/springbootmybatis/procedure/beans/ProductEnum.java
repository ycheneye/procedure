package com.springbootmybatis.procedure.beans;

import lombok.Getter;

/**
 * @作者 chenyi
 * @date 2019/5/5 15:20
 */
@Getter
public enum ProductEnum {

    PRODUCT_UP(1,"在售"),
    PRODUCT_DOWN(2,"商品下架"),
    PRODUCT_DELETE(3,"删除");

    private int code;
    private String msg;

    ProductEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
