package me.kollcaku.QuarantineFriends.service;

import me.kollcaku.QuarantineFriends.configuration.JwtUtils;
import me.kollcaku.QuarantineFriends.dto.UserDTO;
import me.kollcaku.QuarantineFriends.dto.UserRoleDTO;
import me.kollcaku.QuarantineFriends.entity.SignInModel;
import me.kollcaku.QuarantineFriends.entity.UserEntity;
import me.kollcaku.QuarantineFriends.exception.EmailExistException;
import me.kollcaku.QuarantineFriends.exception.UserNotFoundException;
import me.kollcaku.QuarantineFriends.exception.UsernameExistException;
import me.kollcaku.QuarantineFriends.repository.UserRepository;
import me.kollcaku.QuarantineFriends.utility.LoginResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final UserRoleService userRoleService;

    AuthenticationManager authenticationManager;

    JwtUtils jwtUtils;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, UserRoleService userRoleService, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    private String generatedPassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    private String generateUsedId() {
        return RandomStringUtils.randomNumeric(10);
    }

    private void validateUsernameAndEmail(String username, String email) throws UsernameExistException, EmailExistException {
        if (this.userRepository.findUserByUsername(username) != null){
            throw new UsernameExistException("Username already exist");
        }
        if (this.userRepository.findUserByEmail(email)!=null){
            throw new EmailExistException("Email already exist");
        }
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public UserDTO register(String firstName, String lastName, String username, String email, String jobPosition, String password, UserRoleDTO role) throws UserNotFoundException, EmailExistException, UsernameExistException {
        validateUsernameAndEmail(username, email);
//        if (password == null){
//            password = generatedPassword();
//        }

        UserDTO user = new UserDTO();
        user.setUserId(generateUsedId());

        UserRoleDTO userRole = new UserRoleDTO();
        if(role != null) {
            userRole = this.userRoleService.findById(role.getId());
        }
        else {
            userRole = this.userRoleService.findById(1L);
        }
        String encodedPassword = encodePassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        System.out.println("New password: " + password);
//        sendPassword(user, password);
        user.setJobPosition(jobPosition);
        user.setImageUrl("./assets/images/anonymous.png");
        user.setRole(userRole);
        UserEntity userEntity = mapToEntity(user);
        userEntity.setPassword(encodedPassword);
        this.userRepository.save(userEntity);
        return user;
    }

    public ResponseEntity<LoginResponse> login(SignInModel signInModel) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInModel.getUsername(), signInModel.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserEntity user = this.userRepository.findUserByUsername(signInModel.getUsername());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("jwtToken", jwt);
        LoginResponse loginResponse = new LoginResponse(user, jwt);
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(loginResponse);
    }

    public List<UserDTO> getUsers() {
        List<UserDTO>userList = new ArrayList<>();

        for (UserEntity u: this.userRepository.findAll()){
            if (u.getRole().getId() != 2){
                userList.add(mapToDto(u));
            }
        }
        return userList;
    }

    public static UserDTO mapToDto(UserEntity userEntity){

        UserDTO userDTO = new UserDTO();

        if (userEntity != null){
            userDTO.setId(userEntity.getId());
            userDTO.setFirstName(userEntity.getFirstName());
            userDTO.setUsername(userEntity.getUsername());
            userDTO.setLastName(userEntity.getLastName());
            userDTO.setJobPosition(userEntity.getJobPosition());
            userDTO.setRole(UserRoleService.mapToDto(userEntity.getRole()));
            userDTO.setEmail(userEntity.getEmail());
            userDTO.setImageUrl(userEntity.getImageUrl());
            if (userEntity.getRequests() != null) {
                userDTO.setRequests(userEntity.getRequests().stream().map(RequestService::mapToDto).collect(Collectors.toList()));
            }
        }


        return userDTO;
    }

    public static UserEntity mapToEntity(UserDTO userDTO){
        UserEntity userEntity = new UserEntity();
        if (userDTO != null){
            userEntity.setUserId(userDTO.getUserId());
            userEntity.setId(userDTO.getId());
            userEntity.setFirstName(userDTO.getFirstName());
            userEntity.setUsername(userDTO.getUsername());
            userEntity.setLastName(userDTO.getLastName());
            userEntity.setJobPosition(userDTO.getJobPosition());
            userEntity.setRole(UserRoleService.mapToEntity(userDTO.getRole()));
            userEntity.setEmail(userDTO.getEmail());
            userEntity.setImageUrl(userDTO.getImageUrl());
            if (userDTO.getRequests() != null) {
                userEntity.setRequests(userDTO.getRequests().stream().map(RequestService::mapToEntity).collect(Collectors.toList()));
            }
        }

        return userEntity;
    }
}
