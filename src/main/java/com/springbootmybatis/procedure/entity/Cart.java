package com.springbootmybatis.procedure.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @作者 chenyi
 * @date 2019/5/6 17:30
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "xmcc_cart")
public class Cart {
    @Id
    private Integer id;
    private Integer userId;
    private Integer productId;
    private Integer quantity;
    private Integer checked;
    private Date createTime;
    private Date updateTime;
}
