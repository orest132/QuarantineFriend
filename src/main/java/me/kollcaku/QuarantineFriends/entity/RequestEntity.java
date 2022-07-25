package me.kollcaku.QuarantineFriends.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "request")
@Getter
@Setter
public class RequestEntity {
    @Override
    public String toString() {
        return "RequestEntity{" +
                "id=" + id +
                ", from_user=" + from_user +
                '}';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    private UserEntity from_user;
}
