package com.springbootmybatis.procedure.filter;

import com.springbootmybatis.procedure.entity.User;
import com.springbootmybatis.procedure.utils.ApplicationContextHelper;
import com.springbootmybatis.procedure.utils.CookieUtils;
import com.springbootmybatis.procedure.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @作者 chenyi
 * @date 2019/5/7 19:42
 */
@Slf4j
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("过滤拦截开始--------------------");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String uri = request.getRequestURI();
        log.info("当前访问路径：{}", uri);

        if(StringUtils.startsWith(uri,"/user/login")){
            //放过
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        //首先从request中获取保存了登录信息的cookie
        String redisKey = CookieUtils.readCookieValue(request, CookieUtils.LOGIN_COOKIE_NAME);
        RedisUtil redisUtil = ApplicationContextHelper.popBean(RedisUtil.class);
        if(redisKey!=null){
            User loginUser = (User) redisUtil.get(redisKey);
            if(loginUser!=null){
                //每次访问就刷新redis的过期时间设置为30分钟
                redisUtil.setex(redisKey,loginUser, (long) CookieUtils.LOGIN_REDIDS_EXPIRE);
                //放行
                filterChain.doFilter(servletRequest,servletResponse);
                return;
            }
        }
        //走到这儿就不放过，重定向到登录页面,这儿就写点表示下
        // response.sendRedirect("");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write("用户未登录...");

    }

    @Override
    public void destroy() {
        log.info("过滤器销毁--------------------");
    }
}
