package com.bhaawan.companionMatcher.controller;

import com.bhaawan.companionMatcher.model.UserModel;
import com.bhaawan.companionMatcher.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String greet(){
        return "Hello world !!!";
    }

    @PostMapping("/create")
    public UserModel createUser(@RequestBody UserModel user){
        return userService.createUser(user);
    }

    @GetMapping("/getAll")
    public List<UserModel> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/getInterest/{username}")
    public List<UserModel> getAllUsers(@PathVariable String username){
        return userService.getInterestPerUser(username);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAll(){
        userService.deleteAll();
        return ResponseEntity.ok("All users deleted");
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity<String> deleteAll(@PathVariable String username){
        boolean deleted=userService.deleteOneUser(username);

        if(deleted){
            return ResponseEntity.ok("user "+username+" deleted successfully");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user "+username+" NOT found");
        }
    }
}
