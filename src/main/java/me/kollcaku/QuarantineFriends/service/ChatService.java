package me.kollcaku.QuarantineFriends.service;

import me.kollcaku.QuarantineFriends.dto.ChatDTO;
import me.kollcaku.QuarantineFriends.dto.RequestDTO;
import me.kollcaku.QuarantineFriends.entity.ChatEntity;
import me.kollcaku.QuarantineFriends.entity.RequestEntity;
import me.kollcaku.QuarantineFriends.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {
    ChatRepository chatRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    boolean chatExist(Long user1Id, Long user2Id){
        if(chatRepository.getChatByUsersPresent(user1Id, user2Id)!=null)
            return true;
        else
            return false;
    }

    public void addChat(RequestEntity requestEntity){
        ChatEntity chat = new ChatEntity();
        chat.setUser1(requestEntity.getFrom_user());
        chat.setUser2(requestEntity.getTo_user());
        if(!chatExist(chat.getUser1().getId(), chat.getUser2().getId()))
            chatRepository.save(chat);
        else
            System.out.println("chat already exists");
    }

    public static ChatDTO mapToDto(ChatEntity chatEntity){

        ChatDTO chatDTO = new ChatDTO();

        if (chatEntity != null){
            chatDTO.setId(chatEntity.getId());
            chatDTO.setUser1(UserService.mapToDto(chatEntity.getUser1()));
            chatDTO.setUser2(UserService.mapToDto(chatEntity.getUser2()));
            if (chatEntity.getMessages() != null) {
                chatDTO.setMessages(chatEntity.getMessages().stream().map(MessageService::mapToDto).collect(Collectors.toList()));
            }
        }
        return chatDTO;
    }

    public static ChatEntity mapToEntity(ChatDTO chatDTO){
        ChatEntity chatEntity = new ChatEntity();
        if (chatDTO != null){
            chatEntity.setId(chatDTO.getId());
            chatEntity.setUser1(UserService.mapToEntity(chatDTO.getUser1()));
            chatEntity.setUser2(UserService.mapToEntity(chatDTO.getUser2()));
            if (chatDTO.getMessages() != null) {
                chatEntity.setMessages(chatDTO.getMessages().stream().map(MessageService::mapToEntity).collect(Collectors.toList()));
            }

        }
        return chatEntity;
    }

    public List<ChatDTO> getChatsByUserId(Long id) {
        return this.chatRepository.getChatByUserId(id).stream().map(ChatService::mapToDto).collect(Collectors.toList());
    }

    public ChatDTO getChatById(Long id) {
        return mapToDto(this.chatRepository.findById(id).get());
    }
}
