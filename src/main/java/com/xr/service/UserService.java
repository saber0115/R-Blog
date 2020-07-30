package com.xr.service;

import com.xr.pojo.User;

public interface UserService {
    User checkUser(String username, String password);
}
