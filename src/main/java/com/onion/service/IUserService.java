package com.onion.service;

import com.onion.common.ServerResponse;
import com.onion.pojo.User;

public interface IUserService {

    ServerResponse<User> login(String username, String password);

    ServerResponse<String> register(User user);
}
