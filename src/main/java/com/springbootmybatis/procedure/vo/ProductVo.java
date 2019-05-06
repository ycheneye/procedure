package com.springbootmybatis.procedure.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 用于存放少量字段,用于浏览商品的时候展示
 * 如果直接用商品product类来缓存，因为库存是修改最多的，那么更新缓存太频繁了
 */
@Data
public class ProductVo implements Serializable {
    private Integer id;
    //商品名称
    private String name;
    //商品副标题
    private String subtitle;
    //主图片
    private String mainImage;

    //商品价格
    private BigDecimal price;

}
