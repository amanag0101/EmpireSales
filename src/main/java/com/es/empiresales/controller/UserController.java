package com.es.empiresales.controller;

import java.security.Principal;

import com.es.empiresales.entity.User;
import com.es.empiresales.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    @Autowired UserRepo userRepo;

    @ModelAttribute
    public void commonAttributes(Principal principal, Model m) {
        String username = principal.getName();
        User user = userRepo.findByEmail(username);

        m.addAttribute("username", user.getName());
    }

    @RequestMapping("/user")
    public String home() {
        return "/user/home";
    }
}
