package com.bhaawan.companionMatcher.service;

import com.bhaawan.companionMatcher.model.UserModel;
import com.bhaawan.companionMatcher.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public UserModel createUser(UserModel user){
        return userRepo.save(user);
    }

    public List<UserModel> getAllUsers(){
        return userRepo.findAll();
    }

    public List<UserModel> getInterestPerUser(String username){
        Optional<UserModel> user=userRepo.findByNameIgnoreCase(username);
        if(user.isEmpty()){
            return Collections.emptyList();
        }

        UserModel mainUser=user.get();
        List<String> mainUserInterests=mainUser.getInterests();

        List<UserModel> allUsers=userRepo.findAll();
        List<UserModel> matchedUsers=new ArrayList<>();

        allUsers.stream().forEach(currentUser ->{

            if(!currentUser.getName().equalsIgnoreCase(username)){
                List<String> currentUserInterests=currentUser.getInterests();
                long countCommon=currentUserInterests.stream().filter(mainUserInterests::contains).count();

                if(countCommon>=2){
                    matchedUsers.add(currentUser);
                }
            }

        });

        return matchedUsers;
    }

    public void deleteAll(){
        userRepo.deleteAll();
    }

    public boolean deleteOneUser(Long id){
        List<UserModel> allUsers=userRepo.findAll();

        for(UserModel user:allUsers){
            if(Objects.equals(user.getId(), id)){
                userRepo.delete(user);
                return true;
            }
        }
        return false;
    }
}
