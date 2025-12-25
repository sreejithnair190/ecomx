package me.sreejithnair.ecomx_api.user.service;

import me.sreejithnair.ecomx_api.user.entity.User;

public interface UserService {
    User getUserByUserId(Long userId);
}
