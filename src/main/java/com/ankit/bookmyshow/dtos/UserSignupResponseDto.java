package com.ankit.bookmyshow.dtos;

import com.ankit.bookmyshow.models.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignupResponseDto {
    private User user;
    private ResponseStatus responseStatus;
}
