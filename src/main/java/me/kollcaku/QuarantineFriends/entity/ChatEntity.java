package me.kollcaku.QuarantineFriends.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "chat")
@Getter
@Setter
public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    private UserEntity user1;

    @ManyToOne
    private UserEntity user2;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<MessageEntity> messages;

    private Long chatActiveByUser1;
}
