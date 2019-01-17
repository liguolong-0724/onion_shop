package com.onion.dao;

import com.onion.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    // 验证用户名是否存在
    int checkUsername(String username);

    // mybatis入参为多个时，需要使用@Param注解
    User selectLogin(@Param("username") String username, @Param("password") String password);
}