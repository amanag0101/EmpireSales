package com.es.empiresales.service;

import java.util.List;
import java.util.Optional;

import com.es.empiresales.entity.User;
import com.es.empiresales.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    @Autowired private UserRepo userRepo;
    
    // get all the users
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    // get user by id
    public Optional<User> getUserById(Long id) {
        return userRepo.findById(id);
    }

    // add a new user
    public Boolean addUser(User user) {
        try {
            userRepo.save(user);
            return true;
        }
        catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // update user by id
    public Boolean updateUserById(Long id, User user) {
        if(user.getId() == id) {
            userRepo.save(user);
            return true;
        }

        return false;
    }

    // delete user by email
    public Boolean deleteUserByEmail(String email) {
        User user = userRepo.findByEmail(email);
        
        if(user == null)
            return false;

        userRepo.delete(user);
        return true;
    }
}
