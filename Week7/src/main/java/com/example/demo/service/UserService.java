package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.exception.ResourceException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return user.get();
        }
        throw new ResourceException("User with id "+ id+" not present");
    }

    public User save(User user){
        User existingUser = userRepository.findByUsername(user.getUsername());
        if(existingUser==null){
            throw new ResourceException("User with username already exists!!");
        }
        return userRepository.save(user);
    }

    public User update(User user){
        Optional<User> data = userRepository.findById(user.getId());
        if(data.isEmpty()){
            throw new ResourceException("User with id "+user.getId()+" not present. So update cannot be performed");
        }
        return userRepository.save(user);
    }

    public void deleteUserById(Long id){
        Optional<User> data = userRepository.findById(id);
        if(data.isEmpty()){
            throw new ResourceException("User with id "+ id+" not present. So Deletion cannot be performed");
        }
        userRepository.deleteById(id);;
    }
    
}
