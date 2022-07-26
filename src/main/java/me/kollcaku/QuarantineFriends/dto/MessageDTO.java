package me.kollcaku.QuarantineFriends.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageDTO {
    private Long id;
    private String message;
    private UserDTO user;
}
