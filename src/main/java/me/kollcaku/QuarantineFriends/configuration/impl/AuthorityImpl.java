package me.kollcaku.QuarantineFriends.configuration.impl;

import me.kollcaku.QuarantineFriends.entity.UserRoleEntity;
import org.springframework.security.core.GrantedAuthority;

public class AuthorityImpl implements GrantedAuthority {
    private UserRoleEntity userRole;

    public AuthorityImpl(UserRoleEntity userRole){
        this.userRole = userRole;
    }
    @Override
    public String getAuthority() {
        return userRole.getName();
    }
}
