package com.es.empiresales.rest;

import java.util.List;
import java.util.Optional;

import com.es.empiresales.entity.User;
import com.es.empiresales.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRest {
    @Autowired private UserService userService;

    // get all the users
    @GetMapping("/users")
    public ResponseEntity<List<User>> gelAllUser() {
        List<User> userList = userService.getAllUsers();

        if(userList.size() == 0)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.of(Optional.of(userList));
    }

    // get user by id
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        Optional<User> optional = userService.getUserById(id);
        User user = optional.get();

        if(user == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.of(Optional.of(user));
    }

    // add a new user
    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        if(userService.addUser(user))
            return ResponseEntity.status(HttpStatus.CREATED).build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    // update user by id
    @PutMapping("/users/{id}/")
    public ResponseEntity<Void> updateUserById(@PathVariable("id") Long id, @RequestBody User user) {
        try {
            if(userService.updateUserById(id, user))
                return ResponseEntity.ok().build();
            else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // delete user by email
    @DeleteMapping("/users/{email}")
    public ResponseEntity<Void> deleteUserByEmail(@PathVariable("email") String email) {
        try {
            if(userService.deleteUserByEmail(email))
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            else
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
