package com.onion.service.impl;

import com.onion.common.Const;
import com.onion.common.ServerResponse;
import com.onion.dao.UserMapper;
import com.onion.pojo.User;
import com.onion.service.IUserService;
import com.onion.util.MD5Util;
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

        // 密码需要进行MD5加密
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectLogin(username, md5Password);
        if(user == null){
            return ServerResponse.createByErrorMessage("密码错误！");
        }

        // 将用户密码设置为空返回
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功",user);
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    public ServerResponse<String> register(User user){
        // 校验用户名是否存在
        int result = userMapper.checkUsername(user.getUsername());
        if(result > 0){
            return ServerResponse.createByErrorMessage("用户名已存在！");
        }

        // 设置用户角色为普通用户
        user.setRole(Const.Role.ROLE_CUSTOMER);

        // 对用户密码进行MD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));

        // 返回的count为插入的数量，为0时代表插入失败
        int count = userMapper.insert(user);
        if(count == 0){
            return ServerResponse.createByErrorMessage("注册失败");
        }

        return ServerResponse.createBySuccessMessage("注册成功");
    }
}
