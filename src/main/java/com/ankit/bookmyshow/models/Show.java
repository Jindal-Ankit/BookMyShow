package com.ankit.bookmyshow.models;

import com.ankit.bookmyshow.models.enums.Feature;
import com.ankit.bookmyshow.models.enums.ShowSeatStatus;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name = "shows")
public class Show extends BaseModel{

    @ManyToOne
    private Movie movie;
    private Long startTime;
    private Long endTime;

    @ManyToOne
    private Screen screen;

    @Enumerated
    @ElementCollection
    private List<Feature> features;
}


/*
show    movie
1       1
m        1
 */