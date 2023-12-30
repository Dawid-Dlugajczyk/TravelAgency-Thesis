package com.finalproject.travelagency.service;

import com.finalproject.travelagency.model.Tour;
import com.finalproject.travelagency.repository.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    @Autowired
    private TourRepository tourRepository;

    public List<Tour> getTopReservedTours() {
        List<Object[]> topReservedTours = tourRepository.findTopReservedTours();
        return extractTours(topReservedTours);
    }

    public List<String> getTopDestinations() {
        List<Object[]> topDestinations = tourRepository.findTopDestinations();
        return extractDestinations(topDestinations);
    }




    public List<Integer> getMostPopularNumberOfDays() {
        List<Object[]> popularNumberOfDays = tourRepository.findMostPopularNumberOfDays();
        return extractNumberOfDays(popularNumberOfDays);
    }

    public List<String> getYearMonthWithMostReservations() {
        List<Object[]> yearMonthReservations = tourRepository.findYearMonthWithMostReservations();
        return extractYearMonthReservations(yearMonthReservations);
    }

    private List<String> extractYearMonthReservations(List<Object[]> data) {
        return data.stream()
                .map(obj -> String.format("%d-%02d: Reservations - %d",
                        (int) obj[0], (int) obj[1], (long) obj[2]))
                .collect(Collectors.toList());
    }
    private List<Tour> extractTours(List<Object[]> data) {
        return data.stream()
                .map(obj -> (Tour) obj[0])
                .collect(Collectors.toList());
    }

    private List<String> extractDestinations(List<Object[]> data) {
        return data.stream()
                .map(obj -> (String.format("%s, %s - Reservations: %d",
                        obj[0].toString(), obj[1].toString(), obj[2])))
                .collect(Collectors.toList());
    }

    private List<String> extractTypesOrMeals(List<Object[]> data) {
        return data.stream()
                .map(obj -> (String.format("%s - Count: %d", obj[0].toString(), obj[1])))
                .collect(Collectors.toList());
    }

    private List<Integer> extractNumberOfDays(List<Object[]> data) {
        return data.stream()
                .map(obj -> Integer.parseInt(obj[0].toString()))
                .collect(Collectors.toList());
    }
}
