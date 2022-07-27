package me.kollcaku.QuarantineFriends.service;

import me.kollcaku.QuarantineFriends.configuration.JwtUtils;
import me.kollcaku.QuarantineFriends.dto.HobbyReportRowDTO;
import me.kollcaku.QuarantineFriends.dto.UserDTO;
import me.kollcaku.QuarantineFriends.dto.UserRoleDTO;
import me.kollcaku.QuarantineFriends.entity.HobbyEntity;
import me.kollcaku.QuarantineFriends.entity.SignInModel;
import me.kollcaku.QuarantineFriends.entity.UserEntity;
import me.kollcaku.QuarantineFriends.exception.EmailExistException;
import me.kollcaku.QuarantineFriends.exception.UserNotFoundException;
import me.kollcaku.QuarantineFriends.exception.UsernameExistException;
import me.kollcaku.QuarantineFriends.repository.HobbyRepository;
import me.kollcaku.QuarantineFriends.repository.UserRepository;
import me.kollcaku.QuarantineFriends.utility.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.*;

@Service
public class UserService {

    private PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final HobbyRepository hobbyRepository;

    private final UserRoleService userRoleService;

    private final ChatService chatService;

    AuthenticationManager authenticationManager;

    JwtUtils jwtUtils;


    private JavaMailSender javaMailSender;


    @Autowired
    public UserService(ChatService chatService,PasswordEncoder passwordEncoder, UserRepository userRepository, UserRoleService userRoleService, AuthenticationManager authenticationManager, JwtUtils jwtUtils, HobbyRepository hobbyRepository,JavaMailSender javaMailSender) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.hobbyRepository = hobbyRepository;
        this.chatService = chatService;
        this.javaMailSender = javaMailSender;
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

    public UserDTO register(String firstName, String lastName, String username, String email, Long age, String password, UserRoleDTO role, List<HobbyEntity> hobbies) throws UserNotFoundException, EmailExistException, UsernameExistException {
        validateUsernameAndEmail(username, email);
//        if (password == null){
//            password = generatedPassword();
//        }
        System.out.println(age);

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
        user.setAge(age);
        user.setImageUrl("./assets/images/anonymous.png");
        user.setRole(userRole);

        UserEntity userEntity = mapToEntity(user);
        System.out.println(age);

        userEntity.setPassword(encodedPassword);
        userEntity.setHobbies(null);
        userEntity = this.userRepository.save(userEntity);
        userEntity.setHobbies(hobbies);
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
            userDTO.setUserId(userEntity.getUserId());
            userDTO.setId(userEntity.getId());
            userDTO.setBanned(userEntity.isBanned());
            userDTO.setFirstName(userEntity.getFirstName());
            userDTO.setUsername(userEntity.getUsername());
            userDTO.setLastName(userEntity.getLastName());
            userDTO.setAge(userEntity.getAge());
            userDTO.setRole(UserRoleService.mapToDto(userEntity.getRole()));
            userDTO.setEmail(userEntity.getEmail());
            userDTO.setImageUrl(userEntity.getImageUrl());
//            if (userEntity.getRequests() != null) {
//                userDTO.setRequests(userEntity.getRequests().stream().map(RequestService::mapToDto).collect(Collectors.toList()));
//            }
        }


        return userDTO;
    }

    public static UserEntity mapToEntity(UserDTO userDTO){
        UserEntity userEntity = new UserEntity();
        if (userDTO != null){
            userEntity.setUserId(userDTO.getUserId());
            userEntity.setId(userDTO.getId());
            userEntity.setBanned(userDTO.isBanned());
            userEntity.setFirstName(userDTO.getFirstName());
            userEntity.setUsername(userDTO.getUsername());
            userEntity.setLastName(userDTO.getLastName());
            userEntity.setAge(userDTO.getAge());
            userEntity.setRole(UserRoleService.mapToEntity(userDTO.getRole()));
            userEntity.setEmail(userDTO.getEmail());
            userEntity.setImageUrl(userDTO.getImageUrl());
//            if (userDTO.getRequests() != null) {
//                userEntity.setRequests(userDTO.getRequests().stream().map(RequestService::mapToEntity).collect(Collectors.toList()));
//            }
        }

        return userEntity;
    }

    public HobbyEntity newHobby(HobbyEntity hobby) {
        return this.hobbyRepository.save(hobby);
    }

    public HobbyEntity newHobbyByUserId(HobbyEntity hobby, Long id) {
        UserEntity userEntity = userRepository.findById(id).get();
        userEntity.getHobbies().add(hobby);
        userRepository.save(userEntity);
        return hobbyRepository.getHobbieByString(hobby.getHobby());
    }

    public List<HobbyEntity> getAllHobbies() {
        return this.hobbyRepository.findAll();
    }

    public int calculateTheOthersPoints(UserEntity theUser, UserEntity otherUser){

        int points; int total;
        if(theUser.getAge()==otherUser.getAge()){
            points=5;
            total=5;
        }
        else {
            points = 0;
            total = 5;
        }
        for(HobbyEntity hobby : theUser.getHobbies()){
            total+=2;
            for(HobbyEntity hobbyOther : otherUser.getHobbies()){
                if(hobby.getHobby()==hobbyOther.getHobby())
                    points+=2;
            }
        }
        return points;
    }

    public static HashMap<UserEntity, Integer> sortByValue(HashMap<UserEntity, Integer> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<UserEntity, Integer>> list =
                new LinkedList<Map.Entry<UserEntity, Integer>>(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<UserEntity, Integer>>() {
            public int compare(Map.Entry<UserEntity, Integer> o1,
                               Map.Entry<UserEntity, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<UserEntity, Integer> temp = new LinkedHashMap<UserEntity, Integer>();
        for (Map.Entry<UserEntity, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

        public List<UserDTO> getUsersSorted(Long id, int usersToReturn) {
        UserEntity theUser = this.userRepository.findById(id).get();
        UserEntity admin = this.userRepository.findUserByUsername("admin");
        List<UserEntity> theOtherUsers = this.userRepository.findAll();

        HashMap<UserEntity, Integer> userScores = new HashMap<UserEntity, Integer>();

        theOtherUsers.forEach(otherUser->{
            if(theUser.getId()!=otherUser.getId()&&otherUser.getId()!=admin.getId()&&!this.chatService.chatExist(theUser.getId(),otherUser.getId()))
                userScores.put(otherUser,this.calculateTheOthersPoints(theUser,otherUser));
        });

        Map<UserEntity, Integer> hm1 = sortByValue(userScores);
        List<UserDTO> sortedUsers = new ArrayList<>();

        for (Map.Entry<UserEntity, Integer> en : hm1.entrySet()) {
            sortedUsers.add(mapToDto(en.getKey()));
            System.out.println("Key = " + en.getKey().getUsername() +
                    ", Value = " + en.getValue());
        }
        if(sortedUsers.size()<usersToReturn)
            return sortedUsers;
        else
            return sortedUsers.subList(0,usersToReturn);
    }

    public UserDTO getUserByUsername(String username) {
        return mapToDto(this.userRepository.findUserByUsername(username));
    }

    public void resetPassword(Long id, String password){
        UserEntity user = this.userRepository.findById(id).get();
        user.setPassword(encodePassword(password));
        this.userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        this.userRepository.deleteUserAssociatedHobbies(id);
        this.userRepository.deleteUserAssociatedRequests(id);
        this.userRepository.deleteFromMessageChat(id);
        this.userRepository.deleteUserAssociatedChats(id);
        this.userRepository.deleteUserAssociatedMessages(id);
        this.userRepository.deleteAsocciatedReports(id);
        this.userRepository.deleteById(id);
    }

    public void sendPassword(UserEntity user, String password){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom("portos-project@outlook.com");
        mail.setSubject("Account was created for you");
        mail.setText("Hi " + user.getFirstName() + " " + user.getLastName()  +
                "!\n\nYour password has been reset  as per your request\n\n" +
                "Login Credentials\n" +
                "username: " + user.getUsername() +
                "\npassword: " + password);

        javaMailSender.send(mail);
    }

    public void forgetPassword(String email) {
        UserEntity user = this.userRepository.findUserByEmail(email);
        String password = this.generatedPassword();
        System.out.println("Generated password is: "+password);
        user.setPassword(password);
        sendPassword(user, password);
        this.userRepository.save(user);

    }

    public void banUser(Long id) {

        UserEntity userEntity = this.userRepository.findById(id).get();
        System.out.println(userEntity.isBanned());
        if(userEntity.isBanned()==true){
            userEntity.setBanned(false);
        }
        else
            userEntity.setBanned(true);

        this.userRepository.save(userEntity);
    }

    public List<HobbyReportRowDTO> getHobbieReport() {
        List<HobbyReportRowDTO> hobbyReportRowDTOList = new ArrayList<>();
        int[] counts = this.userRepository.hobbiesCount();
        int[] hobbiesId = this.userRepository.hobbiesId();
        for(int i= 0; i<counts.length;i++){
            HobbyReportRowDTO hobbyReportRowDTO = new HobbyReportRowDTO();
            hobbyReportRowDTO.setHobby(this.hobbyRepository.findById(Integer.toUnsignedLong(hobbiesId[i])).get().getHobby());
            hobbyReportRowDTO.setCount(Integer.toUnsignedLong(counts[i]));
            hobbyReportRowDTOList.add(hobbyReportRowDTO);
        }
        return hobbyReportRowDTOList;
    }
}
