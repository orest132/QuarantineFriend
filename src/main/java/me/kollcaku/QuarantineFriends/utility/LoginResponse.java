package me.kollcaku.QuarantineFriends.utility;

import lombok.Getter;
import lombok.Setter;
import me.kollcaku.QuarantineFriends.dto.UserDTO;
import me.kollcaku.QuarantineFriends.entity.UserEntity;

@Getter
@Setter
public class LoginResponse {
    public UserDTO user;
    public String token;

    public LoginResponse(UserDTO user, String token) {
        this.user = user;
        this.token = token;
    }

    public LoginResponse(UserDTO user) {
        this.user = user;
    }
}
