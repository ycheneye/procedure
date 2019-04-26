package com.springbootmybatis.procedure.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @作者 chenyi
 * @date 2019/4/26 15:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {
    private String oId;
    private double oPrice;
    private int oStatu;

}
