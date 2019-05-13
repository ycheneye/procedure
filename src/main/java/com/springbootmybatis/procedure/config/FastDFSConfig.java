package com.springbootmybatis.procedure.config;

/**
 * @作者 chenyi
 * @date 2019/5/13 15:00
 */
import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

/**
 * 导入FastDFS-Client组件
 */
@Configuration
//表示将FdfsClientConfig类 的对象交给spring管理 @Import非常简单 需要掌握的注解 自己看
@Import(FdfsClientConfig.class)
// 解决jmx重复注册bean的问题  jmx是一个监控与管理的框架 以后有机会在去学习吧
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class FastDFSConfig {

}

