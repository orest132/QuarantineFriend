package me.kollcaku.QuarantineFriends.controller;

import me.kollcaku.QuarantineFriends.dto.UserDTO;
import me.kollcaku.QuarantineFriends.entity.HobbyEntity;
import me.kollcaku.QuarantineFriends.entity.RegisterModel;
import me.kollcaku.QuarantineFriends.entity.SignInModel;
import me.kollcaku.QuarantineFriends.exception.EmailExistException;
import me.kollcaku.QuarantineFriends.exception.UserNotFoundException;
import me.kollcaku.QuarantineFriends.exception.UsernameExistException;
import me.kollcaku.QuarantineFriends.service.UserService;
import me.kollcaku.QuarantineFriends.utility.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static me.kollcaku.QuarantineFriends.utility.SecurityConstant.ANGULAR_CROSS_ORIGIN;
import static me.kollcaku.QuarantineFriends.utility.SecurityConstant.REQUEST_MAPPING;

@RestController
@RequestMapping(value = {REQUEST_MAPPING})
@CrossOrigin(ANGULAR_CROSS_ORIGIN)
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody RegisterModel registerModel) throws EmailExistException, UserNotFoundException, UsernameExistException {
        UserDTO user = userService.register(registerModel.getUser().getFirstName(), registerModel.getUser().getLastName(), registerModel.getUser().getUsername(), registerModel.getUser().getEmail(), registerModel.getUser().getAge(), registerModel.getPassword(),registerModel.getUser().getRole(),registerModel.getUser().getHobbies());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody SignInModel signInModel){
        return this.userService.login(signInModel);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsers(){
        List<UserDTO> users = this.userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/user/{username}")
    public UserDTO getUserByUsername(@PathVariable("username") String username){
        return this.userService.getUserByUsername(username);
    }

    @GetMapping("/users/sorted/{userId}/{thisMany}")
    public ResponseEntity<List<UserDTO>> getUsersSorted(@PathVariable("userId") Long id, @PathVariable("thisMany") int usersToReturn){
        List<UserDTO> users = this.userService.getUsersSorted(id, usersToReturn);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/reset-password/{id}")
    public void resetPassword(@PathVariable Long id,@RequestBody String password){
        userService.resetPassword(id, password);
    }

    @PutMapping("/forget-password")
    public void forgetPassword(@RequestBody String email){
        userService.forgetPassword(email);
    }

    @DeleteMapping("/user/delete/{userId}")
    public void deleteUserById(@PathVariable("userId") Long id){
        this.userService.deleteUserById(id);
    }

    @PostMapping("/hobby")
    public HobbyEntity newHobby(@RequestBody HobbyEntity hobby){
        return this.userService.newHobby(hobby);
    }

    @PostMapping("/hobby/user/{userId}")
    public HobbyEntity newHobbyByUserId(@RequestBody HobbyEntity hobby, @PathVariable("userId") Long id){
        return this.userService.newHobbyByUserId(hobby,id);
    }

    @GetMapping("/hobbies")
    public List<HobbyEntity> getAllHobbies(){
        return this.userService.getAllHobbies();
    }
}
