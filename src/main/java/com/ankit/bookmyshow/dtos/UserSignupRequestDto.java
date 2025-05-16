package com.ankit.bookmyshow.dtos;

import lombok.Data;

@Data
public class UserSignupRequestDto {
    private String name;
    private String mobileNo;
    private String address;
    private String email;
    private String password;
}
