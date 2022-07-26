package me.kollcaku.QuarantineFriends.controller;

import me.kollcaku.QuarantineFriends.dto.MessageDTO;
import me.kollcaku.QuarantineFriends.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static me.kollcaku.QuarantineFriends.utility.SecurityConstant.ANGULAR_CROSS_ORIGIN;
import static me.kollcaku.QuarantineFriends.utility.SecurityConstant.REQUEST_MAPPING;

@RestController
@RequestMapping(value = {REQUEST_MAPPING})
@CrossOrigin(ANGULAR_CROSS_ORIGIN)
public class MessageController {

    MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/message/chat/{chatId}")
    public void postMessageToChat(@PathVariable("chatId") Long id, @RequestBody MessageDTO message){
        this.messageService.postMessageToChat(id, message);
    }

    @GetMapping("/get/messages/chat/{chatId}")
    public List<MessageDTO> getMessagesByChatId(@PathVariable("chatId") Long id){
        return this.messageService.getMessagesByChatId(id);
    }

}
