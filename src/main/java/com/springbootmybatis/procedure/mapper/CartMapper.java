package com.springbootmybatis.procedure.mapper;

import com.springbootmybatis.procedure.vo.CartVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


/**
 * @作者 chenyi
 * @date 2019/5/6 18:55
 */
@Mapper
public interface CartMapper {
    CartVo findByUserId(@Param("userId") Integer userId);

    int exists(@Param("productId") Integer productId, @Param("userId") Integer userId);

    void updateQuantity(@Param("productId") Integer productId,
                        @Param("userId") Integer userId,
                        @Param("quantity") Integer quantity);

    void insert(@Param("productId") Integer productId,
                @Param("userId") Integer userId,
                @Param("quantity") Integer quantity);

    void deleteCartItem(@Param("userid") Integer userid, @Param("productId") Integer productId);
}
