package com.finalproject.travelagency.service;


import com.finalproject.travelagency.exception.TourNotFoundException;
import com.finalproject.travelagency.model.*;
import com.finalproject.travelagency.repository.CommentRepository;
import com.finalproject.travelagency.repository.TourRepository;
import com.finalproject.travelagency.repository.UserRepository;
import jakarta.websocket.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TourService {

    private static final String IMAGE_DIRECTORY = "C:/Users/david/Desktop/Ostatni projekt/FInalProject/TravelAgency/images/";
    private final TourRepository tourRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    @Autowired
    public TourService(TourRepository tourRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.tourRepository = tourRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    public List<Tour> getAllTours(){
        return tourRepository.findAll();
    }

    public Tour getTourById(Long id){
        return tourRepository.findTourById(id)
                .orElseThrow(() -> new TourNotFoundException("Tour with id=" + id + "was not found."));
    }

    public Tour addTour(Tour tour, MultipartFile imageFile) throws IOException {
        try {
            String imagePath = saveImageLocally(imageFile);
            tour.setImagePath(imagePath);
            return tourRepository.save(tour);
        } catch (IOException e) {
            // Handle IO exception
            throw new RuntimeException("Failed to process image file.", e);
        }
    }

    public void addCommentToTour(Long tourId, Long userId, String commentText) throws Exception {
        Tour tour = tourRepository.findById(tourId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (tour != null && user != null) {
            Comment comment = new Comment();
            comment.setTour(tour);
            comment.setUser(user);
            comment.setText(commentText);
            comment.setCommentedAt(LocalDate.now());

            tour.getComments().add(comment);

            tourRepository.save(tour);
        } else {
            throw new Exception("Tour or User not found");
        }
    }
    public Tour updateTour(Tour tour, MultipartFile imageFile, Long id) throws IOException {
        try {
            Optional<Tour> existingTour = tourRepository.findById(id);
            if (existingTour.isPresent()) {
                Tour foundTour = existingTour.get();
                if (imageFile != null && !imageFile.isEmpty()) {
                    String imagePath = saveImageLocally(imageFile);
                    foundTour.setImagePath(imagePath);
                }
                foundTour.setCity(tour.getCity());
                foundTour.setCountry(tour.getCountry());
                foundTour.setDescription(tour.getDescription());
                foundTour.setArrivalDate(tour.getArrivalDate());
                foundTour.setDepartureDate(tour.getDepartureDate());
                foundTour.setHotelName(tour.getHotelName());
                foundTour.setMeal(tour.getMeal());
                foundTour.setName(tour.getName());
                foundTour.setPrice(tour.getPrice());
                foundTour.setType(tour.getType());

                return tourRepository.save(foundTour);

            }else{
                throw new TourNotFoundException("Tour with ID " + id + " not found");
            }

        } catch (IOException e) {
            // Handle IO exception
            throw new RuntimeException("Failed to process image file.", e);
        }
    }
    public void deleteTourById(Long id){
        tourRepository.deleteById(id);
    }

    public List<Comment> getCommentsForTour(Long tourId) {
        Tour tour = tourRepository.findById(tourId).orElse(null);
        if (tour != null) {
            return tour.getComments();
        }
        return Collections.emptyList();
    }

    public List<Tour> filterTours(List<String> countries, List<String> cities, LocalDate departureDate,
                                  List<MealType> meals, String hotelName, LocalDate arrivalDate,
                                  List<TourType> types, String name, Double minPrice, Double maxPrice,Integer minNumberOfDays, Integer maxNumOfDays) {
        return tourRepository.findByFilters(countries, cities, departureDate, meals, hotelName, arrivalDate,
                types, name, minPrice, maxPrice, minNumberOfDays, maxNumOfDays);
    }

    public List<String> getMealTypes() {
        return Arrays.stream(MealType.values())
                .map(Enum::name) // Convert enum to string
                .collect(Collectors.toList());
    }


    public List<TourType> getTourTypes() {
        return Arrays.asList(TourType.values());
    }
    public List<String> getAllCountries() {
        return tourRepository.findAll().stream()
                .map(Tour::getCountry)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<String> getAllCities() {
        return tourRepository.findAll().stream()
                .map(Tour::getCity)
                .distinct()
                .collect(Collectors.toList());
    }



    private String saveImageLocally(MultipartFile imageFile) throws IOException {
        // Generate a unique filename for the image
        String uniqueFileName = generateUniqueFileName(imageFile.getOriginalFilename());

        // Define the path where the image will be stored
        Path imagePath = Paths.get(IMAGE_DIRECTORY + uniqueFileName);

        // Save the file to the specified path
        Files.write(imagePath, imageFile.getBytes());

        // Return the path where the image is stored
        return imagePath.toString();
    }

    // Method to generate a unique filename
    private String generateUniqueFileName(String originalFileName) {
        // Generate a unique filename, for example, using UUID
        String uniqueID = UUID.randomUUID().toString();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        return uniqueID + fileExtension;
    }

    public List<Tour> getToursBeforeOrOnCurrentDate() {
        LocalDate currentDate = LocalDate.now();
        LocalDate oneMonthLater = currentDate.plusMonths(1);
        return tourRepository.findByDepartureDateLessThanEqual(oneMonthLater);
    }
    public Tour updateTourPlaces(Long id, Integer number){
        Tour tour = tourRepository.findTourById(id)
                .orElseThrow(() -> new NoSuchElementException("Reservation not found"));
        tour.setAvailablePlaces(tour.getAvailablePlaces());
        return tourRepository.save(tour);
    }
}
