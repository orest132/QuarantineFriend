package me.kollcaku.QuarantineFriends.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.kollcaku.QuarantineFriends.entity.HobbyEntity;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String jobPosition;
    private UserRoleDTO role;
    private String username;

    private List<HobbyEntity> hobbies;

    private List<RequestDTO> requests;

    private String imageUrl;

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", jobPosition='" + jobPosition + '\'' +
                ", role=" + role +
                ", username='" + username + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
