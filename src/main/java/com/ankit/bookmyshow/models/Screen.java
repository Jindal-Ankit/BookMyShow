package com.ankit.bookmyshow.models;

import com.ankit.bookmyshow.models.enums.Feature;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Screen extends BaseModel{
    private String name;

    @OneToMany(mappedBy = "screen")
    private List<Seat> seats;

    @OneToMany(mappedBy = "screen")
    private List<Show> shows;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private List<Feature> features;

    /*
    screen seat
    1       m
     1       1

     */
}
