package com.ankit.bookmyshow.services;

import com.ankit.bookmyshow.exceptions.UserAlreadyExists;
import com.ankit.bookmyshow.models.User;
import com.ankit.bookmyshow.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User signup(String name, String email, String mobile_no, String password) {
        // find if user already exists -> yes -> throw exception -> no create user
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()){
            throw new UserAlreadyExists("User " + email + " already exists.");
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setMobileNo(mobile_no);
        user.setPassword(bCryptPasswordEncoder.encode(password));

        userRepository.save(user);

        return user;
    }
}
