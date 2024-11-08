package com.hello.Lim.User.controller;

import com.hello.Lim.User.dto.JoinDTO;
import com.hello.Lim.User.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private JoinService joinService;

    @GetMapping("/login")
    public String loginP() {

        return "Login/login";
    }

    @GetMapping("/join")
    public String joinP() {

        return "Login/join";
    }

    @PostMapping("/joinProc")
    public String joinProcess(JoinDTO joinDTO){

        joinService.joinProcess(joinDTO);

        return "redirect:/login";
    }



}
