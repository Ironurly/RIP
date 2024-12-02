package com.travel.tour_agency_backend.service;

import com.travel.tour_agency_backend.entity.Review;
import com.travel.tour_agency_backend.entity.Tour;
import com.travel.tour_agency_backend.entity.User;
import com.travel.tour_agency_backend.repository.ReviewRepository;
import com.travel.tour_agency_backend.repository.TourRepository;
import com.travel.tour_agency_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final TourRepository tourRepository; // !!!
    private final UserRepository userRepository; // !!!
    @Autowired
    public ReviewService(ReviewRepository reviewRepository, TourRepository tourRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.tourRepository = tourRepository; // !!!
        this.userRepository = userRepository; // !!!

    }

    // Получение всех отзывов
    @Transactional(readOnly = true) // !!!  readOnly = true для методов чтения
    public List<Review> getReviewsByTourId(Long tourId) {
        return reviewRepository.findByTourId(tourId);
    }

    // Получение отзыва по id
    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    // Создание нового отзыва
    public Review createReview(Review review, Long userId, Long tourId) { // !!! userId and tourId
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId)); // !!! Обработка ошибки, если пользователь не найден

        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> new IllegalArgumentException("Tour not found with id: " + tourId)); // !!! Обработка ошибки, если тур не найден


        review.setUser(user);  // !!! Связываем отзыв с пользователем
        review.setTour(tour);  // !!! Связываем отзыв с туром

        return reviewRepository.save(review);
    }

    // Обновление отзыва
    public Review updateReview(Long id, Review review) {
        Optional<Review> existingReview = reviewRepository.findById(id);
        if (existingReview.isPresent()) {
            review.setId(id);  // Обновляем id
            return reviewRepository.save(review);
        }
        return null;  // Можно выбросить исключение, если отзыв не найден
    }

    // Удаление отзыва
    @Transactional
    public void deleteReview(Long reviewId, Long userId, boolean isAdmin) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found with id: " + reviewId));

        // Разрешить удаление, если пользователь - автор отзыва или администратор
        if (!review.getUser().getId().equals(userId) && !isAdmin) {
            throw new SecurityException("You are not authorized to delete this review");
        }

        reviewRepository.deleteById(reviewId);
    }



}