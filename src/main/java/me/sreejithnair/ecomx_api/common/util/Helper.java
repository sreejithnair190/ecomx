package me.sreejithnair.ecomx_api.common.util;


import me.sreejithnair.ecomx_api.user.core.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class Helper {

    public static User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
