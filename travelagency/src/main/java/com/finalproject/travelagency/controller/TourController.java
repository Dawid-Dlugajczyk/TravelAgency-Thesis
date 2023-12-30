package com.finalproject.travelagency.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.finalproject.travelagency.model.MealType;
import com.finalproject.travelagency.model.Tour;
import com.finalproject.travelagency.model.TourType;
import com.finalproject.travelagency.service.TourService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.metadata.GenericCallMetaDataProvider;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth/tours")
@CrossOrigin(origins = "http://localhost:4200")
public class TourController {

    private final TourService tourService;

    @Autowired
    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @GetMapping
    public ResponseEntity<List<Tour>> getAllTours(){
        List<Tour> tours = tourService.getAllTours();
        return new ResponseEntity<>(tours, HttpStatus.OK);
    }


    @GetMapping("/find/{id}")
    public ResponseEntity<Tour> getTourById(@PathVariable Long id){
        Tour tour = tourService.getTourById(id);
        return new ResponseEntity<>(tour, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Tour> addTour(@RequestPart("tour") String tourJson,
                                        @RequestPart("imageFile") MultipartFile imageFile) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            Tour tour = objectMapper.readValue(tourJson, Tour.class);
            Tour addedTour = tourService.addTour(tour, imageFile);
            return new ResponseEntity<>(addedTour, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/update/{id}")
    public ResponseEntity<Tour> updateTour(@RequestPart("tour") String tourJson,
                                           @RequestPart("imageFile") MultipartFile imageFile,
                                           @PathVariable Long id){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            Tour tour = objectMapper.readValue(tourJson, Tour.class);
            Tour addedTour = tourService.updateTour(tour, imageFile, id);
            return new ResponseEntity<>(addedTour, HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Tour> deleteTourById(@PathVariable Long id){
        tourService.deleteTourById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Tour>> filterTours(@RequestParam(required = false) List<String> countries,
                                                  @RequestParam(required = false) List<String> cities,
                                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
                                                  @RequestParam(required = false) List<MealType> meals,
                                                  @RequestParam(required = false) String hotelName,
                                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate arrivalDate,
                                                  @RequestParam(required = false) List<TourType> types,
                                                  @RequestParam(required = false) String name,
                                                  @RequestParam(required = false) Double minPrice,
                                                  @RequestParam(required = false) Double maxPrice,
                                                  @RequestParam(required = false) Integer minNumOfDays,
                                                  @RequestParam(required = false) Integer maxNumOfDays) {
        List<Tour> filteredTours = tourService.filterTours(
                countries, cities, departureDate, meals, hotelName, arrivalDate, types, name, minPrice, maxPrice, minNumOfDays, maxNumOfDays);
        return new ResponseEntity<>(filteredTours, HttpStatus.OK);
    }

    @GetMapping("/by-departure-date")
    public ResponseEntity<List<Tour>> getToursByDepartureDate() {
        List<Tour> tours = tourService.getToursBeforeOrOnCurrentDate();

        if (tours.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(tours, HttpStatus.OK);
    }

    @GetMapping("/enums/types")
    public ResponseEntity<List<TourType>> getMealTypes() {
        List<TourType> meals = tourService.getTourTypes();
        return ResponseEntity.ok(meals);
    }

    @GetMapping("/enums/meals")
    public ResponseEntity<List<String>> getTourTypes() {
        List<String> types = tourService.getMealTypes();
        return ResponseEntity.ok(types);
    }

    @GetMapping("/countries")
    public ResponseEntity<List<String>> getAllCountries() {
        List<String> countries = tourService.getAllCountries();
        return ResponseEntity.ok(countries);
    }

    @GetMapping("/cities")
    public ResponseEntity<List<String>> getAllCities() {
        List<String> cities = tourService.getAllCities();
        return ResponseEntity.ok(cities);
    }

    @PutMapping("/places/{tourId}")
    public ResponseEntity<Tour> updateTourPlaces(@PathVariable Long tourId, @RequestParam Integer number){
        Tour tour = tourService.updateTourPlaces(tourId, number);
        return new ResponseEntity<>(tour, HttpStatus.OK);
    }

}
