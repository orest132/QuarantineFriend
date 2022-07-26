package me.kollcaku.QuarantineFriends.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestDTO {
    private Long id;
    private UserDTO from_user;
    private UserDTO to_user;
}
