package com.finalproject.travelagency.controller;

import com.finalproject.travelagency.model.Person;
import com.finalproject.travelagency.model.Reservation;
import com.finalproject.travelagency.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/reservations")
@CrossOrigin(origins = "http://localhost:4200")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Reservation>> getReservationsByUserId(@PathVariable Long id){
        List<Reservation> reservations = reservationService.getReservationsByUserId(id);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        Reservation newReservation = reservationService.createReservation(reservation);
        return new ResponseEntity<>(newReservation, HttpStatus.CREATED);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Reservation> createReservation(@PathVariable Long id, @RequestBody Reservation reservation) {
        Reservation newReservation = reservationService.updateReservation(id, reservation);
        return new ResponseEntity<>(newReservation, HttpStatus.CREATED);
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long reservationId) {
        Reservation reservation = reservationService.getReservationById(reservationId);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{reservationId}")
    public ResponseEntity<Void> deleteReservationById(@PathVariable Long reservationId) {
        reservationService.deleteReservationById(reservationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}

