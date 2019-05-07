package com.springbootmybatis.procedure.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.springbootmybatis.procedure.beans.ProductEnum;
import com.springbootmybatis.procedure.beans.RedisEnum;
import com.springbootmybatis.procedure.entity.Cart;
import com.springbootmybatis.procedure.entity.Product;
import com.springbootmybatis.procedure.exception.CustomException;
import com.springbootmybatis.procedure.mapper.CartMapper;
import com.springbootmybatis.procedure.mapper.ProductMapper;
import com.springbootmybatis.procedure.utils.BigDecimalUtil;
import com.springbootmybatis.procedure.utils.RedisUtil;
import com.springbootmybatis.procedure.utils.ResultResponse;
import com.springbootmybatis.procedure.vo.CartProductVo;
import com.springbootmybatis.procedure.vo.CartVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @作者 chenyi
 * @date 2019/5/6 17:19
 */
@Service
@Slf4j
public class CartService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    /**
     * 根据用户id查询该用户的购物车
     * @param userId 用户id
     * @return
     */
    public CartVo queryCartInfo(Integer userId) {
        //创建订单总金额为0  涉及到钱的都用 高精度计算
        BigDecimal totalPrice = new BigDecimal("0");
        //先查出该用户的购物车,还没写登录，所以用户id就用21
        Map<Object, Object> cartFromRedis =
                redisUtil.hmget(RedisEnum.CART_PREFIX.name().concat("_cart_").concat(String.valueOf(userId)));
        CartVo cartVo = null;
        if (CollectionUtils.isEmpty(cartFromRedis)) {
            cartVo = cartMapper.findByUserId(userId);
            List<CartProductVo> productVoList = cartVo.getCartProductVoList();
            List<CartProductVo> trueVos = productVoList.stream().
                    filter(cartProductVo -> cartProductVo.getProductChecked() == 1).collect(Collectors.toList());

            cartVo.setAllChecked(trueVos.size() == productVoList.size() ? true : false);
            for (CartProductVo cartProductVo : trueVos) {
                totalPrice = BigDecimalUtil.add(totalPrice, cartProductVo.getProductTotalPrice());
            }
            cartVo.setCartTotalPrice(totalPrice);

            //设置进redis
            cartFromRedis = Maps.newHashMap();
            cartFromRedis.put("allChecked", cartVo.getAllChecked());
            cartFromRedis.put("cartTotalPrice", cartVo.getCartTotalPrice());
            cartFromRedis.put("userId", cartVo.getUserId());
            for (CartProductVo cartProductVo : productVoList) {
                cartFromRedis.put(cartProductVo.getProductId(), cartProductVo);
            }
            redisUtil.hmset(RedisEnum.CART_PREFIX.name().concat("_cart_").concat(String.valueOf(userId)), cartFromRedis);
        } else {
            cartVo.setUserId((String) cartFromRedis.get("userId"));
            cartVo.setAllChecked((Boolean) cartFromRedis.get("allChecked"));
            cartVo.setCartTotalPrice((BigDecimal) cartFromRedis.get("cartTotalPrice"));
            ArrayList<CartProductVo> cartProductVoList = Lists.newArrayList();
            //封装List
            Set<Map.Entry<Object, Object>> entries = cartFromRedis.entrySet();
            for (Map.Entry entry: entries) {
                CartProductVo cartProductVo = (CartProductVo) entry.getValue();
                cartProductVoList.add(cartProductVo);
            }
            cartVo.setCartProductVoList(cartProductVoList);
        }
        return cartVo;
    }

    /**
     * 商品加入购物车
     * @param productId 产品id
     * @param quantity 产品数量
     * @return
     */
    public boolean addProductToCart(Integer productId, Integer quantity){
        int userId = 21;
        //根据用户id与商品id查询购物车 看该商品是否已经在购物车中
        int exists = cartMapper.exists(productId, userId);
        if (exists>0){
            cartMapper.updateQuantity(productId, userId, quantity);

            //更新缓存
            CartProductVo cartProductVo = (CartProductVo)
                    redisUtil.hget(RedisEnum.CART_PREFIX.name().concat("_cart_").concat(String.valueOf(userId))
                    , String.valueOf(productId));
            cartProductVo.setQuantity(cartProductVo.getQuantity()+quantity);
            redisUtil.hset(RedisEnum.CART_PREFIX.name().concat("_cart_").concat(String.valueOf(userId)),
                    String.valueOf(cartProductVo.getProductId()),
                    cartProductVo);
            return true;
        }else {
            //查询产品是否存在
            Product product = productMapper.isExists(productId);
            if (product != null){
                cartMapper.insert(productId, userId, quantity);
                //更新缓存
                CartProductVo cartProductVo = new CartProductVo();
                cartProductVo.setProductId(product.getId());
                cartProductVo.setQuantity(quantity);
                cartProductVo.setProductName(product.getName());
                cartProductVo.setProductSubtitle(product.getSubtitle());
                cartProductVo.setProductMainImage(product.getMainImage());
                cartProductVo.setProductPrice(product.getPrice());
                cartProductVo.setProductStatus(product.getStatus());
                cartProductVo.setProductTotalPrice(BigDecimalUtil.multi(product.getPrice(), quantity));
                cartProductVo.setProductChecked(1);
                redisUtil.hset(RedisEnum.CART_PREFIX.name().concat("_cart_").concat(String.valueOf(userId)),
                        String.valueOf(productId),
                        cartProductVo);
                return true;
            }
        }
        return false;
    }

    /**
     * 删除购物车里的产品项
     * @param productId
     * @return
     */
    public ResultResponse deleteCartItem(Integer productId){
        int userId = 21;
        try{
            cartMapper.deleteCartItem(userId, productId);
            //更新缓存
            redisUtil.hdel(RedisEnum.CART_PREFIX.name().concat("_cart_").concat(String.valueOf(userId)),
                    String.valueOf(productId));
        } catch (Exception e){
            log.info("删除失败，查看信息==>", e);
            return ResultResponse.fail();
        }
        return ResultResponse.success();
    }
}
