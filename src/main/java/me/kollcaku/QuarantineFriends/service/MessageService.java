package me.kollcaku.QuarantineFriends.service;

import me.kollcaku.QuarantineFriends.dto.ChatDTO;
import me.kollcaku.QuarantineFriends.dto.MessageDTO;
import me.kollcaku.QuarantineFriends.dto.RequestDTO;
import me.kollcaku.QuarantineFriends.entity.ChatEntity;
import me.kollcaku.QuarantineFriends.entity.MessageEntity;
import me.kollcaku.QuarantineFriends.entity.RequestEntity;
import me.kollcaku.QuarantineFriends.repository.ChatRepository;
import me.kollcaku.QuarantineFriends.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    MessageRepository messageRepository;
    ChatRepository chatRepository;

    SubscribeService subscribeService;

    @Autowired
    public MessageService(MessageRepository messageRepository, ChatRepository chatRepository, SubscribeService subscribeService) {
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
        this.subscribeService = subscribeService;
    }

    public void postMessageToChat(Long id, MessageDTO message) {
        ChatEntity chat = this.chatRepository.findById(id).get();
        System.out.println(message.getUser().getId()+"this is id");
        List<MessageEntity> messages = chat.getMessages();
        messages.add(mapToEntity(message));
        chat.setMessages(messages);
        this.subscribeService.sendEventToAll("sendMessage",message);
        this.chatRepository.save(chat);
    }

    public List<MessageDTO> getMessagesByChatId(Long id) {
        ChatDTO chat = ChatService.mapToDto(this.chatRepository.findById(id).get());
        return chat.getMessages();
    }

    public static MessageDTO mapToDto(MessageEntity messageEntity){

        MessageDTO messageDTO = new MessageDTO();

        if (messageEntity != null){
            messageDTO.setId(messageEntity.getId());
            messageDTO.setUser(UserService.mapToDto(messageEntity.getUser()));
            messageDTO.setMessage(messageEntity.getMessage());
        }
        return messageDTO;
    }

    public static MessageEntity mapToEntity(MessageDTO messageDTO){
        MessageEntity messageEntity = new MessageEntity();
        if (messageDTO != null){
            messageEntity.setId(messageDTO.getId());
            messageEntity.setUser(UserService.mapToEntity(messageDTO.getUser()));
            messageEntity.setMessage(messageDTO.getMessage());
        }
        return messageEntity;
    }
}
