package com.hello.Lim.User.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.security.SecureRandom;

@Data
public class JoinDTO {

    private String username;
    private String password;
}
