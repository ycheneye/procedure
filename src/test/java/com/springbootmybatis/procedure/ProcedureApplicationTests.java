package com.springbootmybatis.procedure;

import com.springbootmybatis.procedure.entity.Order;
import com.springbootmybatis.procedure.mapper.OrderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProcedureApplicationTests {

    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void contextLoads() {
        List<Order> orders = orderMapper.findAll();
        orders.stream().forEach(System.out::println);
    }

    @Test
    public void test(){
        orderMapper.cancelOrder("20001");
    }
}
