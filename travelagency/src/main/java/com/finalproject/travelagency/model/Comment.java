package com.finalproject.travelagency.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "tour_id")
    private Tour tour;

    @Column(columnDefinition = "TEXT")
    private String text;

    private LocalDate commentedAt;
}