package me.kollcaku.QuarantineFriends.service;

import me.kollcaku.QuarantineFriends.dto.RequestDTO;
import me.kollcaku.QuarantineFriends.dto.UserDTO;
import me.kollcaku.QuarantineFriends.entity.RequestEntity;
import me.kollcaku.QuarantineFriends.entity.UserEntity;
import me.kollcaku.QuarantineFriends.repository.RequestRepository;
import me.kollcaku.QuarantineFriends.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {
    RequestRepository requestRepository;
    UserRepository userRepository;

    @Autowired
    public RequestService(RequestRepository requestRepository, UserRepository userRepository) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
    }


    public void sendRequest(RequestDTO request, Long id) {
        UserEntity userEntity = userRepository.findById(id).get();
        userEntity.getRequests().add(RequestService.mapToEntity(request));
        userRepository.save(userEntity);
    }


    public List<RequestDTO> getAllRequests(Long id) {
        UserEntity userEntity = userRepository.findById(id).get();
        UserDTO userDTO = UserService.mapToDto(userEntity);
        return userDTO.getRequests();
    }

    public static RequestDTO mapToDto(RequestEntity requestEntity){

        RequestDTO requestDTO = new RequestDTO();

        if (requestEntity != null){
            requestDTO.setId(requestEntity.getId());
            requestDTO.setFrom_user(UserService.mapToDto(requestEntity.getFrom_user()));
        }
        return requestDTO;
    }

    public static RequestEntity mapToEntity(RequestDTO requestDTO){
        RequestEntity requestEntity = new RequestEntity();
        if (requestDTO != null){
            requestEntity.setId(requestDTO.getId());
            requestEntity.setFrom_user(UserService.mapToEntity(requestDTO.getFrom_user()));
        }
        return requestEntity;
    }

    public void deleteRequest(Long id) {
        this.requestRepository.delete(this.requestRepository.findById(id).get());
    }
}
