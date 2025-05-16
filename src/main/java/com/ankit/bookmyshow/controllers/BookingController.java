package com.ankit.bookmyshow.controllers;

import com.ankit.bookmyshow.dtos.BookingRequestDto;
import com.ankit.bookmyshow.dtos.BookingResponseDto;
import com.ankit.bookmyshow.dtos.ResponseStatus;
import com.ankit.bookmyshow.models.Booking;
import com.ankit.bookmyshow.services.BookingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookingController {

    //bookticket
     // booking service
    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }


    @PostMapping("/book")
    public BookingResponseDto bookTicket(@RequestBody BookingRequestDto bookingRequestDto) {

            BookingResponseDto bookingResponseDto = new BookingResponseDto();

            try{
                Booking booking =  bookingService.bookTicket(bookingRequestDto.getUserId(),
                        bookingRequestDto.getShowSeatIds(),
                        bookingRequestDto.getShowId());
                bookingResponseDto.setBooking(booking);
                bookingResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
            } catch (Exception e) {
                bookingResponseDto.setResponseStatus(ResponseStatus.FAILURE);
            }
            return bookingResponseDto;


    }

}
