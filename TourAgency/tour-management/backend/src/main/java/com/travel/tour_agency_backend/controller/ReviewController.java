package com.travel.tour_agency_backend.controller;

import com.travel.tour_agency_backend.entity.Review;
import com.travel.tour_agency_backend.entity.Tour;
import com.travel.tour_agency_backend.repository.TourRepository; // !!!
import com.travel.tour_agency_backend.security.JwtTokenProvider;
import com.travel.tour_agency_backend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @Autowired
    private final TourRepository tourRepository;

    @Autowired
    public ReviewController(ReviewService reviewService, JwtTokenProvider tokenProvider,TourRepository tourRepository) {
        this.reviewService = reviewService;
        this.tokenProvider = tokenProvider;
        this.tourRepository = tourRepository; // !!!
    }

    @Autowired
    private JwtTokenProvider tokenProvider;

    @GetMapping
    public List<Review> getAllReviews(Long tourId) {
        return reviewService.getReviewsByTourId(tourId);
    }

    // @GetMapping("/{id}")
    //public Optional<Review> getReviewById(@PathVariable Long id) {
    //    return reviewService.getReviewById(id);
   // }

    @PutMapping("/{id}")
    public Review updateReview(@PathVariable Long id, @RequestBody Review review) {
        return reviewService.updateReview(id, review);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7); // "Bearer "
            if (tokenProvider.validateToken(token)) {
                Long userId = tokenProvider.getUserIdFromJWT(token);
                boolean isAdmin = "admin".equals(tokenProvider.getUsernameFromJWT(token));

                // Удаляем отзыв, если это автор или администратор
                reviewService.deleteReview(id, userId, isAdmin);
                return ResponseEntity.ok("Review deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete review: " + e.getMessage());
        }
    }



    @PostMapping
    public ResponseEntity<?> createReview(@RequestHeader("Authorization") String authHeader, @RequestBody Review review) {
        try {
            String token = authHeader.substring(7); // "Bearer "
            if (tokenProvider.validateToken(token)) {
                Long userId = tokenProvider.getUserIdFromJWT(token); // Получаем userId из JWT

                // Получаем tourId из объекта review (предполагается, что он передан с frontend)
                Long tourId = review.getTour().getId(); // !!!

                // Создаем отзыв, связывая его с пользователем и туром
                Review savedReview = reviewService.createReview(review, userId, tourId); // !!! Передаем userId и tourId
                return ResponseEntity.ok(savedReview);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
            }
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create review: " + e.getMessage());

        }
    }


}