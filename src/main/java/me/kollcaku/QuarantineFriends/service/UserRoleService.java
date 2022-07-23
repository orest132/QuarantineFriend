package me.kollcaku.QuarantineFriends.service;

import me.kollcaku.QuarantineFriends.dto.UserRoleDTO;
import me.kollcaku.QuarantineFriends.entity.UserRoleEntity;
import me.kollcaku.QuarantineFriends.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService{

    private UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleService(UserRoleRepository userRoleRepository){
        this.userRoleRepository = userRoleRepository;
    }

    public List<UserRoleEntity> findAll() {
        return this.userRoleRepository.findAll();
    }

    public UserRoleDTO findById(Long id) {
        return mapToDto(this.userRoleRepository.findById(id).get());
    }

    public UserRoleEntity mapToEntity(UserRoleDTO userRoleDTO){
        UserRoleEntity userRole = new UserRoleEntity();
        if(userRoleDTO != null) {
            userRole.setId(userRoleDTO.getId());
            userRole.setName(userRoleDTO.getName());
        }
        return userRole;
    }

    public UserRoleDTO mapToDto(UserRoleEntity userRoleEntity){
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        if(userRoleEntity != null) {
            userRoleDTO.setId(userRoleEntity.getId());
            userRoleDTO.setName(userRoleEntity.getName());
        }
        return userRoleDTO;
    }
}
