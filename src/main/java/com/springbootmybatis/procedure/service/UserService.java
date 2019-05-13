package com.springbootmybatis.procedure.service;

import com.springbootmybatis.procedure.beans.UserEnums;
import com.springbootmybatis.procedure.entity.User;
import com.springbootmybatis.procedure.mapper.UserMapper;
import com.springbootmybatis.procedure.utils.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
/**
 * @作者 chenyi
 * @date 2019/5/7 19:11
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;


    public ResultResponse login(String username, String password) {
        if(StringUtils.isBlank(username)||StringUtils.isBlank(password)){
            return ResultResponse.fail(UserEnums.USERNAME_PASSWORD_EMPTY.getMsg());
        }
        User login = userMapper.login(username, password);
        if(login==null){
            return ResultResponse.fail(UserEnums.USERNAME_PASSWORD_ERROR.getMsg());
        }
        return ResultResponse.success(login);
    }

}
