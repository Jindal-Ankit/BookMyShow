package com.ankit.bookmyshow.controllers;

import com.ankit.bookmyshow.dtos.ResponseStatus;
import com.ankit.bookmyshow.dtos.UserSignupRequestDto;
import com.ankit.bookmyshow.dtos.UserSignupResponseDto;
import com.ankit.bookmyshow.models.User;
import com.ankit.bookmyshow.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public UserSignupResponseDto signUp(@RequestBody UserSignupRequestDto userSignupRequestDto) {

        User user = userService.signup(userSignupRequestDto.getName(), userSignupRequestDto.getEmail(), userSignupRequestDto.getMobileNo(), userSignupRequestDto.getPassword());

        UserSignupResponseDto userSignupResponseDto = new UserSignupResponseDto();
        userSignupResponseDto.setUser(user);
        userSignupResponseDto.setResponseStatus(ResponseStatus.SUCCESS);

        return userSignupResponseDto;
    }
}
