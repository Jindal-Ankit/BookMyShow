package com.ankit.bookmyshow.models;

import com.ankit.bookmyshow.models.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Booking extends BaseModel{
    private String refNum;

    @ManyToMany
    private List<ShowSeat> showSeats;


    private BookingStatus bookingStatus;

    @OneToMany(mappedBy = "booking")
    private List<Payment> payments;
    private double amount;

    @ManyToOne
    private User user;

}

/*
booking show_seat
1       m
m        1 (is booking is cancelled)

booking payments
 1  M
 1   1
 booking USER
 1          1
 m           1
 */