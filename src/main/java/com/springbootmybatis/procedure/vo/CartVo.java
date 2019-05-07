package com.springbootmybatis.procedure.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @作者 chenyi
 * @date 2019/5/6 17:10
 */
@Data
public class CartVo implements Serializable {
    private List<CartProductVo> cartProductVoList;
    private BigDecimal cartTotalPrice;
    private Boolean allChecked;
    private String userId;
}
