package com.ankit.bookmyshow.models;

import com.ankit.bookmyshow.models.enums.SeatType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Seat extends  BaseModel{
    private String seatNo;
    private SeatType seatType;

    @ManyToOne
    private Screen screen;

}
