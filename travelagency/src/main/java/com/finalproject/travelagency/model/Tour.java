package com.finalproject.travelagency.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tours")
public class Tour implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "tour_id", nullable=false, updatable = false)
    Long id;

    @OneToMany(mappedBy = "tour", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Reservation> reservations;

    @Column(name = "trip_name")
    String name;

    @Column
    Double price;

    @Column(name="country")
    String country;

    @Column(name="city")
    String city;

    @Column
    Integer numberOfDays;

    @Column
    LocalDate departureDate;

    @Column
    LocalDate arrivalDate;

    @Column(name = "meal")
    @Enumerated(EnumType.STRING)
    MealType meal;

    @Column
    String hotelName;

    @Column
    String description;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    TourType type;

    @Column(name = "image_path")
    private String imagePath; // New field to store the image path

    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Comment> comments;

    @Column
    Integer availablePlaces;

}
