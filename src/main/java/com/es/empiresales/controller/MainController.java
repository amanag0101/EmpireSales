package com.es.empiresales.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.es.empiresales.entity.Category;
import com.es.empiresales.entity.User;
import com.es.empiresales.helper.Message;
import com.es.empiresales.repository.CategoryRepo;
import com.es.empiresales.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    @Autowired UserRepo userRepo;
    @Autowired CategoryRepo categoryRepo;

    @ModelAttribute
    public void commonAttributes(Model m) {
        List<Category> allCategoriesList = categoryRepo.findAll();
        if(allCategoriesList != null)
            m.addAttribute("allCategoriesList", allCategoriesList);
    }

    // default page
    @RequestMapping("/")
    public String index() {
        return "redirect:/all/0";
    }

    @RequestMapping("/all/0")
    public String showAllProducts() {
        return "index";
    }

    // regiser page
    @RequestMapping("/register")
    public String register(Model m) {
        m.addAttribute("user", new User());
        return "register";
    }

    // process registration form
    @PostMapping("/processing-registration-form")
    public String processRegistrationForm(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
    @RequestParam(value = "password-repeat") String passwordRepeat, 
    @RequestParam(value = "agreement", defaultValue = "false") Boolean agreement, HttpSession session) {
        // if agreement not accepted
        if(!agreement) {
            session.setAttribute("message", new Message("error", "Please accept the Terms & Conditions."));
			return "/register";
        }

        // if password does not match password-repeat
        if(!user.getPassword().equals(passwordRepeat)) {
            session.setAttribute("message", new Message("error", "Password does not match."));
			return "/register";
        }

        // errors handled by bean validator
        if(bindingResult.hasErrors()) 
            return "/register";

        // check for unique email    
        User userByEmail = userRepo.findByEmail(user.getEmail());
        if(userByEmail != null) {
            session.setAttribute("message", new Message("error", "Email already taken!"));
			return "/register";
        }

        // check for unique phone number
        User userByPhone = userRepo.findByPhone(user.getPhone());
        if(userByPhone != null) {
            session.setAttribute("message", new Message("error", "Phone Number already taken!."));
			return "/register";
        }

        // add user in the database
        try {
            userRepo.save(user);
            session.setAttribute("message", new Message("success", "Registration Complete!"));
			return "redirect:/register";
        }
        catch(Exception e) {
            e.printStackTrace();
            session.setAttribute("message", new Message("error", "Some error occurred."));
			return "/register";
        }
    }
}
