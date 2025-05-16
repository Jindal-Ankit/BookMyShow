package com.ankit.bookmyshow.dtos;

import com.ankit.bookmyshow.models.Booking;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class BookingResponseDto {
    private Booking booking;
    private ResponseStatus responseStatus;
}
