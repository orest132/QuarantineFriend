package me.kollcaku.QuarantineFriends.controller;

import me.kollcaku.QuarantineFriends.dto.ChatDTO;
import me.kollcaku.QuarantineFriends.dto.MessageDTO;
import me.kollcaku.QuarantineFriends.entity.ChatEntity;
import me.kollcaku.QuarantineFriends.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static me.kollcaku.QuarantineFriends.utility.SecurityConstant.ANGULAR_CROSS_ORIGIN;
import static me.kollcaku.QuarantineFriends.utility.SecurityConstant.REQUEST_MAPPING;

@RestController
@RequestMapping(value = {REQUEST_MAPPING})
@CrossOrigin(ANGULAR_CROSS_ORIGIN)
public class ChatController {
    ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/chat/user/{id}")
    public List<ChatDTO> getChatsByUserId(@PathVariable("id") Long id){
        System.out.println("visiting");
        return this.chatService.getChatsByUserId(id);
    }

    @PutMapping("/chat/block/{userBlockingId}")
    public void blockUser(@RequestBody ChatDTO chat, @PathVariable("userBlockingId") Long id){
        this.chatService.makeChatInactiveByUser(chat,id);
    }

    @GetMapping("/chat/{id}")
    public ChatDTO getChatById(@PathVariable("id") Long id){
        return this.chatService.getChatById(id);
    }

}
