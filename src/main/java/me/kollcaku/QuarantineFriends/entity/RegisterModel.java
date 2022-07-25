package me.kollcaku.QuarantineFriends.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.kollcaku.QuarantineFriends.dto.UserDTO;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterModel {
    private UserDTO user;
    private String password;
}
