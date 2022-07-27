package me.kollcaku.QuarantineFriends.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "report")
@Getter
@Setter
public class ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne()
    private UserEntity reporter;

    @ManyToOne()
    private  UserEntity reportee;

    private String report;
}
