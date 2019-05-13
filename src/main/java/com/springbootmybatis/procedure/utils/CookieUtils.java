package com.springbootmybatis.procedure.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @作者 chenyi
 * @date 2019/5/7 19:15
 */
@Slf4j
public class CookieUtils {

    //当cookie设置domain为xmcc.com这个的一级域名的时候
    //www.xmcc.com  aaa.xmcc.com等二级域名都可以获得该cookie
    //类似于将cookie设置为 baidu.com 那么zhidao.baidu,com、fanyi.baidu.com都可以获得
    public static final String LOGIN_DOMAIN="xmcc.com";
    //redis登录信息对应的key在cookie中的key名
    public static final String LOGIN_COOKIE_NAME="login_token";
    //redis的用户信息过期时间    30分钟过期
    public static final int LOGIN_REDIDS_EXPIRE=60*30;
    //保存redis登录信息key的cookie的过期时间，设置为一天，这个会根据业务变化
    public static final int LOGIN_COOKIE_EXPIRE=60*60*24;
    //设置为/表示该项目下所有路径
    public static final String  TOP_PATH="/";


    /**
     *
     * @param response 写cookie的
     * @param cookieKey cookie的key
     * @param cookieValue cookie的value
     * @param domain 当前cookie的域名
     * @param maxAge 当前cookie的过期时间
     * @param path 当前cookie的路径
     */
    public static void writeCookie(HttpServletResponse response, String cookieKey, String cookieValue, String domain, String path, int maxAge){
        Cookie cookie = new Cookie(cookieKey,cookieValue);
        //设置域名
        cookie.setDomain(domain);
        //路径
        cookie.setPath(path);
        //一旦设置了过期时间 cookie就会持久化到磁盘 关闭浏览器再打开也存在
        cookie.setMaxAge(maxAge);
        //js脚本将无法读取到Cookie信息，这样能有效的防止XSS攻击
        cookie.setHttpOnly(true);
        log.info("写入cookie，key：{}，value：{}",cookieKey,cookieValue);
        response.addCookie(cookie);
    }

    /**
     * @param request 获得所有的cookie
     * @param cookieName 根据该cookie名字 获得value
     * @return
     */
    public static String readCookieValue(HttpServletRequest request, String cookieName){
        Cookie[] cookies = request.getCookies();
        if(cookies!=null)
            for (Cookie cookie : cookies) {
                //取出cookie数组中与当前cookie相同的，工具类做了null判断 不会抛出空指针
                if(StringUtils.equalsIgnoreCase(cookie.getName(),cookieName)){
                    log.info("根据cookie名称获得cookie的value，cookieName:{},cookieValue:{}",cookieName,cookie.getValue());
                    return cookie.getValue();
                }
            }
        return null;
    }

    /**
     * @param request
     * @param cookieName 要删除cookie的名字
     * @param response 设置maxAge为0再写入就可以了
     */
    public static void deleteCookie(HttpServletRequest request,HttpServletResponse response,String cookieName){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            //取出cookie数组中与当前cookie相同的，工具类做了null判断 不会抛出空指针
            if(StringUtils.equalsAnyIgnoreCase(cookie.getName(),cookieName)){
                log.info("根据cookie名称获得删除cookie，cookieName:{}",cookieName);
                //设置时间为0 就是删除
                cookie.setMaxAge(0);
                cookie.setPath(TOP_PATH);//最好设置为根路径
                response.addCookie(cookie);
                return;
            }
        }
    }
}

