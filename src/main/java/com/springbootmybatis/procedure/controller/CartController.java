package com.springbootmybatis.procedure.controller;

import com.springbootmybatis.procedure.beans.ProductEnum;
import com.springbootmybatis.procedure.service.CartService;
import com.springbootmybatis.procedure.utils.ResultResponse;
import com.springbootmybatis.procedure.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @作者 chenyi
 * @date 2019/5/6 17:13
 */
@RestController
@RequestMapping("cart/")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("addProduct/{productId}/{quantity}")
    public ResultResponse addProductToCart(@PathVariable Integer productId, @PathVariable Integer quantity){
        boolean b = cartService.addProductToCart(productId, quantity);
        return b ? ResultResponse.success() : ResultResponse.fail(ProductEnum.PRODUCT_DELETE.getMsg());
    }

    @GetMapping("queryCartInfo")
    public ResultResponse queryCartInfo(){
        Integer userId=21;
        CartVo cart = cartService.queryCartInfo(userId);
        return ResultResponse.success(cart);
    }

    @DeleteMapping("deleteCartItem/{productId}")
    public ResultResponse deleteCartItem(@PathVariable Integer productId ){
        return cartService.deleteCartItem(productId);
    }
}
