package com.springbootmybatis.procedure.mapper;

import com.springbootmybatis.procedure.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @作者 chenyi
 * @date 2019/5/7 19:07
 */
@Mapper
public interface UserMapper {
    User login(@Param("username") String username, @Param("password") String password);
}

