package com.springbootmybatis.procedure.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @作者 chenyi
 * @date 2019/5/6 16:59
 */
@Data
public class CartProductVo implements Serializable {
    private Integer productId;
    private Integer quantity;
    private String productName;
    private String productSubtitle;
    private String productMainImage;
    private BigDecimal productPrice;
    private Integer productStatus;
    private BigDecimal productTotalPrice;
    private Integer productChecked;
}
