package com.hello.Lim.User.entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity(name = "USERENTITY")
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String username;
    private String password;

    private String role; // 권한 지정 값

}