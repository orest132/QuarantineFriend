package me.kollcaku.QuarantineFriends.entity;

import lombok.Getter;
import lombok.Setter;
import me.kollcaku.QuarantineFriends.dto.UserDTO;

import javax.persistence.*;

@Entity
@Table(name = "message")
@Getter
@Setter
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String message;

    @OneToOne()
    private UserEntity user;
}
