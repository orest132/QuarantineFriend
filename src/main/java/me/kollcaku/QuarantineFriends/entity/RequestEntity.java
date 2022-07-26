package me.kollcaku.QuarantineFriends.entity;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "request")
public class RequestEntity {
    @Override
    public String toString() {
        return "RequestEntity{" +
                "id=" + id +
                ", from_user=" + from_user +
                ", to_user=" + to_user +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFrom_user(UserEntity from_user) {
        this.from_user = from_user;
    }

    public void setTo_user(UserEntity to_user) {
        this.to_user = to_user;
    }

    public Long getId() {
        return id;
    }

    public UserEntity getFrom_user() {
        return from_user;
    }

    public UserEntity getTo_user() {
        return to_user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    private UserEntity from_user;

    @ManyToOne
    private  UserEntity to_user;
}
