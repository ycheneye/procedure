package com.springbootmybatis.procedure.controller;

import com.springbootmybatis.procedure.beans.PageBean;
import com.springbootmybatis.procedure.dto.ProductDto;
import com.springbootmybatis.procedure.service.ProductService;
import com.springbootmybatis.procedure.utils.ResultResponse;
import com.springbootmybatis.procedure.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @作者 chenyi
 * @date 2019/5/5 15:37
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/getInfo/{productId}")
    public ResultResponse getProductInfo(@PathVariable Integer productId){
        ProductDto productDto = productService.getProductInfo(productId);
        return ResultResponse.success(productDto);
    }

    @RequestMapping("/getProductByCid/{cid}/{currentPage}/{pageSize}")
    public ResultResponse getProductByCid(
            @PathVariable Integer cid ,
            @PathVariable Integer currentPage ,
            @PathVariable Integer pageSize){
        PageBean<ProductVo> pageBean = productService.getProductByCid(cid, currentPage, pageSize);
        return ResultResponse.success(pageBean);
    }
}
