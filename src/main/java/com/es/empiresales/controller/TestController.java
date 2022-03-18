package com.es.empiresales.controller;

import com.es.empiresales.entity.Address;
import com.es.empiresales.entity.User;
import com.es.empiresales.repository.CartRepo;
import com.es.empiresales.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    @Autowired UserRepo userRepo;
    @Autowired CartRepo cartRepo;
    @Autowired BCryptPasswordEncoder passwordEncoder;

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        User user = new User();
        user.setName("admin");
        user.setEmail("admin@email.com");
        user.setPhone("1234567890");
        user.setRole("ROLE_ADMIN");
        user.setPassword(passwordEncoder.encode("admin"));

        Address address = new Address();
        address.setCountry("country");
        address.setState("state");
        address.setCity("city");
        address.setStreet("street");
        address.setPincode("pincode");
        user.setAddress(address);

        user = userRepo.save(user);

        System.out.println(user.toString());

        return "working";
    }
}
