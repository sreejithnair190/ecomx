package me.sreejithnair.ecomx_api.user.core.service;

import me.sreejithnair.ecomx_api.user.core.entity.User;

public interface UserService {
    User getUserByUserId(Long userId);
}
