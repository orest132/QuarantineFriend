package me.kollcaku.QuarantineFriends.service;

import me.kollcaku.QuarantineFriends.dto.RequestDTO;
import me.kollcaku.QuarantineFriends.dto.UserDTO;
import me.kollcaku.QuarantineFriends.entity.RequestEntity;
import me.kollcaku.QuarantineFriends.entity.UserEntity;
import me.kollcaku.QuarantineFriends.repository.RequestRepository;
import me.kollcaku.QuarantineFriends.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RequestService {
    RequestRepository requestRepository;
    UserRepository userRepository;

    ChatService chatService;

    @Autowired
    public RequestService(RequestRepository requestRepository, UserRepository userRepository, ChatService chatService) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
        this.chatService = chatService;
    }


    public void sendRequest(RequestDTO request) {
        RequestEntity requestEntity = mapToEntity(request);
        if(requestRepository.getRequestByUsers(requestEntity.getFrom_user().getId(),requestEntity.getTo_user().getId())==null)
            if(!chatService.chatExist(requestEntity.getFrom_user().getId(),requestEntity.getTo_user().getId()))
                requestRepository.save(requestEntity);
            else
                System.out.println("chat for request already exists");
        else
            System.out.println("request already exists");
    }


    public List<RequestDTO> getAllRequests(Long id) {
        UserEntity userEntity = userRepository.findById(id).get();
        List<RequestDTO> userRequests = new ArrayList<>();
        requestRepository.findAll().forEach(requestEntity -> {
            if(requestEntity.getTo_user().getId()==userEntity.getId()){
                userRequests.add(mapToDto(requestEntity));
            }
        });
        return userRequests;
    }

    public void deleteRequest(Long id) {
        RequestEntity requestEntity = this.requestRepository.findById(id).get();
        this.requestRepository.delete(requestEntity);
        this.chatService.addChat(requestEntity);
    }

    public static RequestDTO mapToDto(RequestEntity requestEntity){

        RequestDTO requestDTO = new RequestDTO();

        if (requestEntity != null){
            requestDTO.setId(requestEntity.getId());
            requestDTO.setFrom_user(UserService.mapToDto(requestEntity.getFrom_user()));
            requestDTO.setTo_user(UserService.mapToDto(requestEntity.getTo_user()));
        }
        return requestDTO;
    }

    public static RequestEntity mapToEntity(RequestDTO requestDTO){
        RequestEntity requestEntity = new RequestEntity();
        if (requestDTO != null){
            requestEntity.setId(requestDTO.getId());
            requestEntity.setFrom_user(UserService.mapToEntity(requestDTO.getFrom_user()));
            requestEntity.setTo_user(UserService.mapToEntity(requestDTO.getTo_user()));
        }
        return requestEntity;
    }


}
