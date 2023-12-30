package com.finalproject.travelagency.service;



import com.finalproject.travelagency.auth.AuthenticationService;
import com.finalproject.travelagency.exception.UserNotFoundException;
import com.finalproject.travelagency.model.User;
import com.finalproject.travelagency.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    @Autowired
    public UserService(UserRepository userRepository, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
    }

    public User addUser(User user){
        user.setPassword(authenticationService.encodePassword(user.getPassword()));
        return userRepository.save(user);
    }


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User updateUser(User user, Long id){
        User newUser = userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id=" + id + "was not found."));
        newUser.setId(newUser.getId());
        newUser.setCity(user.getCity());
        newUser.setAddress(user.getAddress());
        newUser.setEmail(newUser.getEmail());
        newUser.setFirstname(user.getFirstname());
        newUser.setLastname(user.getLastname());
        newUser.setDateOfBirth(user.getDateOfBirth());
        newUser.setPesel(user.getPesel());
        newUser.setPhoneNumber(user.getPhoneNumber());
        newUser.setPassword(authenticationService.encodePassword(newUser.getPassword()));
        return userRepository.save(newUser);
    }

    public User findUserById(Long id){
        return userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id=" + id + "was not found."));
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
