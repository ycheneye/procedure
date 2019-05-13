package com.springbootmybatis.procedure.config;

import com.google.common.collect.Lists;
import com.springbootmybatis.procedure.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

/**
 * @作者 chenyi
 * @date 2019/5/7 19:59
 */
@Configuration
public class FilterConfig {


    @Bean
    //FilterRegistrationBean 根据名字就可以看出来，专门用于filter注册管理
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        LoginFilter loginFilter = new LoginFilter();
        filterFilterRegistrationBean.setFilter(loginFilter);
        //设置拦截路径
        filterFilterRegistrationBean.setUrlPatterns(Lists.newArrayList("/*"));
        //正数越小优先级越高
        filterFilterRegistrationBean.setOrder(1);
        return filterFilterRegistrationBean;
    }
}
