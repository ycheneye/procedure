package com.springbootmybatis.procedure.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * @作者 chenyi
 * @date 2019/4/16 15:16
 */

@Getter
public enum ResultEnums {
    SUCCESS(200,"成功"),
    FAIL(500,"失败");

    private int code;
    private String message;

    ResultEnums(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
