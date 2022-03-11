package com.es.empiresales.controller;

import com.es.empiresales.entity.Address;
import com.es.empiresales.entity.Cart;
import com.es.empiresales.entity.User;
import com.es.empiresales.repository.CartRepo;
import com.es.empiresales.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    @Autowired UserRepo userRepo;
    @Autowired CartRepo cartRepo;

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        User user = userRepo.getById((long) 1);

        Address address = new Address();
        address.setCountry("country");
        address.setState("state");
        address.setCity("city");
        address.setStreet("street");
        address.setPincode("pincode");
        user.setAddress(address);

        user = userRepo.save(user);

        Cart cart = new Cart();
        cart.setQuantity(10);
        cart.setUser(user);

        cartRepo.save(cart);

        //userRepo.delete(user);

        System.out.println(user.toString());

        return "working";
    }
}
