package me.kollcaku.QuarantineFriends.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "matches")
@Getter
@Setter
public class MatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private UserEntity user1;

    @ManyToOne(cascade = CascadeType.ALL)
    private  UserEntity user2;

    private String report;
}
