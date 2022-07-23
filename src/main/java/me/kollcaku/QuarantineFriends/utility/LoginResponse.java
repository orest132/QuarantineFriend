package me.kollcaku.QuarantineFriends.utility;

import lombok.Getter;
import lombok.Setter;
import me.kollcaku.QuarantineFriends.entity.UserEntity;

@Getter
@Setter
public class LoginResponse {
    public UserEntity user;
    public String token;

    public LoginResponse(UserEntity user, String token) {
        this.user = user;
        this.token = token;
    }

    public LoginResponse(UserEntity user) {
        this.user = user;
    }
}
