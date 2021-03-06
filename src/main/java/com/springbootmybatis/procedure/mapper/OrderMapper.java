package com.springbootmybatis.procedure.mapper;

import com.springbootmybatis.procedure.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @作者 chenyi
 * @date 2019/4/26 15:04
 */
@Mapper
public interface OrderMapper {
    List<Order> findAll();

    void cancelOrder(@Param("orderId") String orderId);
}
