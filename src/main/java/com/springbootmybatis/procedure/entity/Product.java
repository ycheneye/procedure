package com.springbootmybatis.procedure.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @作者 chenyi
 * @date 2019/5/5 15:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "xmcc_product")
public class Product {
    @Id
    private Integer id;
    private Integer categoryId;
    private String name;
    private String subtitle;
    private String mainImage;
    private String subImages;
    private String detail;
    private BigDecimal price;
    private Integer stock;
    private Integer status;
    private Date createTime;
    private Date updateTime;
}
