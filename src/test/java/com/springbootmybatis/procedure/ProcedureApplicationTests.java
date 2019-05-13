package com.springbootmybatis.procedure;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.springbootmybatis.procedure.entity.Order;
import com.springbootmybatis.procedure.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ProcedureApplicationTests {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private FastFileStorageClient fastFileStorageClient;


    @Test
    public void contextLoads() {
        List<Order> orders = orderMapper.findAll();
        orders.stream().forEach(System.out::println);
    }

    @Test
    public void test(){
        orderMapper.cancelOrder("20001");
    }


    @Test
    public void upload() throws FileNotFoundException {
        File file = new File("E://imgHEAD/131_13_full_res.jpg");
        FileInputStream fileInputStream = new FileInputStream(file);
        //文件输入流    文件长度   文件格式  第四个不管
        StorePath storePath = fastFileStorageClient.uploadFile(fileInputStream, file.length(), "jpg", null);

        log.info("文件上传返回路径为:{}",storePath.getFullPath());
    }

}
