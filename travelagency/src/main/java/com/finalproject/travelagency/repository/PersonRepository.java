package com.finalproject.travelagency.repository;

import com.finalproject.travelagency.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByReservationId(Long reservationId);
}
