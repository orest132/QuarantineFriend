package me.kollcaku.QuarantineFriends.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ChatDTO {
    private Long id;
    private UserDTO user1;
    private UserDTO user2;
    private List<MessageDTO> messages;
}
