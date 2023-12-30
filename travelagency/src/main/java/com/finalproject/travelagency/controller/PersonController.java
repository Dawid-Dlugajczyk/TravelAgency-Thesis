package com.finalproject.travelagency.controller;

import com.finalproject.travelagency.model.Person;
import com.finalproject.travelagency.repository.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/persons")
@CrossOrigin(origins = "http://localhost:4200")
public class PersonController {
    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/reservation/{reservationId}")
    public ResponseEntity<List<Person>> getPersonsByReservationId(@PathVariable Long reservationId) {
        List<Person> reservations = personRepository.findByReservationId(reservationId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }
}
