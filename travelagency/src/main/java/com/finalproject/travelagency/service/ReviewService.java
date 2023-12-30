package com.finalproject.travelagency.service;

import com.finalproject.travelagency.exception.TourNotFoundException;
import com.finalproject.travelagency.model.Review;
import com.finalproject.travelagency.repository.ReviewRepository;
import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review addReview(Review review){
        return reviewRepository.save(review);
    }

    public Review getReviewById(Long id){
        return reviewRepository.findById(id)
                .orElseThrow(() -> new TourNotFoundException("User with id=" + id + "was not found."));
    }
    public List<Review> getAllReviews(){
        return reviewRepository.findAll();
    }

    public List<Review> getReviewsByTourId(Long id){
        return reviewRepository.findByTourId(id);
    }

    public void deleteReviewById(Long id){
        reviewRepository.deleteById(id);
    }

    public Review updateReview(Review review){
        return reviewRepository.save(review);
    }
}
