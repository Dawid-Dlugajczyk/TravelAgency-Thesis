package com.finalproject.travelagency.repository;

import com.finalproject.travelagency.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> getReservationsByUserId(Long reservation_id);
}
