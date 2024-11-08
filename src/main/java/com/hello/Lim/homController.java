package com.hello.Lim;

import com.hello.Lim.Item.entity.ItemEntity;
import com.hello.Lim.Item.service.ItemService;
import com.hello.Lim.User.dto.JoinDTO;
import com.hello.Lim.User.service.JoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class homController {

    private final ItemService itemService;

    @GetMapping("/")
    public String main(Model model){

        String id = SecurityContextHolder.getContext().getAuthentication().getName();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        List<ItemEntity> itemList = itemService.findItemAll();
        log.info("itemList = {}", itemList);

        model.addAttribute("itemList", itemList);
        model.addAttribute("id", id);
        model.addAttribute("role", role);

        return "main";
    }

    @GetMapping("/admin")
    public String admin() {

        return "admin";
    }




}
