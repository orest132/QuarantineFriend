package me.kollcaku.QuarantineFriends.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "first_name")
    @NotBlank
    @Size(max = 100)
    private String firstName;
    @Column(name = "last_name")
    @NotBlank
    @Size(max = 100)
    private String lastName;
    @Column(name = "email")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email")
    private String email;
    @Column(name = "job_position")
    private String jobPosition;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private UserRoleEntity role;
    private String password;
    private String username;

    private String imageUrl;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", jobPosition='" + jobPosition + '\'' +
                ", role=" + role +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
