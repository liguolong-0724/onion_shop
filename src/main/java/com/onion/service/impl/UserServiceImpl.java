package com.onion.service.impl;

import com.onion.common.ServerResponse;
import com.onion.dao.UserMapper;
import com.onion.pojo.User;
import com.onion.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @Override
    public ServerResponse<User> login(String username, String password) {
        int result = userMapper.checkUsername(username);
        if(result == 0){
            return ServerResponse.createByErrorMessage("用户名不存在！");
        }

        User user = userMapper.selectLogin(username, password);
        if(user == null){
            return ServerResponse.createByErrorMessage("密码错误！");
        }

        // 将用户密码设置为空返回
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功",user);
    }
}
