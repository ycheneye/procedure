package com.springbootmybatis.procedure.dto;

import com.springbootmybatis.procedure.entity.Product;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

/**
 * @作者 chenyi
 * @date 2019/5/5 15:45
 */
@Data
@NoArgsConstructor
public class ProductDto {
    private String categoryName;
    private String name;
    private String subtitle;
    private String mainImage;
    private String subImages;
    private String detail;
    private BigDecimal price;
    private Integer stock;

    public static ProductDto transfer(Product product){
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);
        return  productDto;
    }
}
