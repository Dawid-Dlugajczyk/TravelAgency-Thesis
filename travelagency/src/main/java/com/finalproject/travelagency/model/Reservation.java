package com.finalproject.travelagency.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name ="user_id", referencedColumnName = "id")
    private User user;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "reservation_id")
    private List<Person> persons;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;

    private LocalDate bookingDate;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    @Enumerated(EnumType.STRING)
    private  PaymentStatus paymentStatus;

}
