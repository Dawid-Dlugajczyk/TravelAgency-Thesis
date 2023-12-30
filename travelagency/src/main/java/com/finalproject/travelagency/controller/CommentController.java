package com.finalproject.travelagency.controller;

import com.finalproject.travelagency.model.Comment;
import com.finalproject.travelagency.model.Tour;
import com.finalproject.travelagency.repository.CommentRepository;
import com.finalproject.travelagency.service.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth/tours/{tourId}/comments")
@CrossOrigin(origins = "http://localhost:4200")
public class CommentController {

    @Autowired
    private TourService tourService;
    @Autowired
    private CommentRepository commentRepository;


    @PostMapping("/add")
    public ResponseEntity<String> addCommentToTour (
            @PathVariable("tourId") Long tourId,
            @RequestBody Map<String, Object> request) throws Exception {
        Long userId = Long.parseLong(request.get("userId").toString());
        String commentText = request.get("commentText").toString();
        tourService.addCommentToTour(tourId, userId, commentText);
        return ResponseEntity.ok("Comment added successfully");
    }

    @GetMapping()
    public ResponseEntity<List<Comment>> getCommentsForTour(@PathVariable("tourId") Long tourId) {
        List<Comment> comments = tourService.getCommentsForTour(tourId);
        return ResponseEntity.ok(comments);
    }
    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<Comment> deleteComment(@PathVariable("commentId") Long commentId) {
        commentRepository.deleteById(commentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
