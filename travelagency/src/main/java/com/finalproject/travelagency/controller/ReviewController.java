package com.finalproject.travelagency.controller;

import com.finalproject.travelagency.model.Review;
import com.finalproject.travelagency.model.Tour;
import com.finalproject.travelagency.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth/admin/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(){
        List<Review> reviews = reviewService.getAllReviews();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id){
        Review review = reviewService.getReviewById(id);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @GetMapping("/tour/{id}")
    public ResponseEntity<List<Review>> getReviewsForTour(@PathVariable  Long id){
        List<Review> reviews = reviewService.getReviewsByTourId(id);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Review> addReview(@RequestBody Review review){
        Review newReview = reviewService.addReview(review);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }
    @PutMapping("/update")
    public ResponseEntity<Review> updateReview(@RequestBody Review review){
        Review newReview = reviewService.updateReview(review);
        return new ResponseEntity<>(newReview, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Review> deleteReviewById(@PathVariable Long id){
        reviewService.deleteReviewById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
