package com.springbootmybatis.procedure.controller;

import com.springbootmybatis.procedure.beans.ResultEnums;
import com.springbootmybatis.procedure.service.UserService;
import com.springbootmybatis.procedure.utils.CookieUtils;
import com.springbootmybatis.procedure.utils.RedisUtil;
import com.springbootmybatis.procedure.utils.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @作者 chenyi
 * @date 2019/5/7 19:26
 */
@RestController
@RequestMapping("user/")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtils;
    @PostMapping("login/{username}/{password}")
    public ResultResponse login(HttpServletResponse response, HttpServletRequest request,
                                @PathVariable("username") String username,
                                @PathVariable("password") String password){
        ResultResponse login = userService.login(username, password);
        //判断用户是否登录成功
        if(login.getCode()== ResultEnums.SUCCESS.getCode()){
            //登录成功将用户信息放入redis,这儿简单起见 直接用sessionId的字符串作为key，只要是不重复的字符串都可以
            String redisKey = request.getSession().getId();
            //放入redis设置30分钟过期
            redisUtils.setex(redisKey,login.getData(), (long) CookieUtils.LOGIN_REDIDS_EXPIRE);
            //将key写入cookie中，这样用户只要能拿到cookie的情况就能拿到redisKey从而获得用户登录信息
            CookieUtils.writeCookie(response,CookieUtils.LOGIN_COOKIE_NAME,
                    redisKey,CookieUtils.LOGIN_DOMAIN,CookieUtils.TOP_PATH,CookieUtils.LOGIN_COOKIE_EXPIRE);
        }
        return login;
    }

}
