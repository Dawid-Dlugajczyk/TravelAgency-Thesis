package com.finalproject.travelagency.controller;

import com.finalproject.travelagency.model.Tour;
import com.finalproject.travelagency.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/statistics")
@CrossOrigin(origins = "http://localhost:4200") // Adjust as per your frontend origin
public class StatisticsController {

    @Autowired private StatisticsService statisticsService;

    @GetMapping("/topReservedTours")
    public ResponseEntity<List<Tour>> getTopReservedTours() {
        List<Tour> topReservedTours = statisticsService.getTopReservedTours();
        return ResponseEntity.ok(topReservedTours);
    }

    @GetMapping("/topDestinations")
    public ResponseEntity<List<String>> getTopDestinations() {
        List<String> topDestinations = statisticsService.getTopDestinations();
        return ResponseEntity.ok(topDestinations);
    }


    @GetMapping("/mostPopularNumberOfDays")
    public ResponseEntity<List<Integer>> getMostPopularNumberOfDays() {
        List<Integer> popularNumberOfDays = statisticsService.getMostPopularNumberOfDays();
        return ResponseEntity.ok(popularNumberOfDays);
    }
    @GetMapping("/mostReservedYearMonth")
    public ResponseEntity<List<String>> getMostReservedYearMonth() {
        List<String> mostReservedYearMonth = statisticsService.getYearMonthWithMostReservations();
        return ResponseEntity.ok(mostReservedYearMonth);
    }
}
