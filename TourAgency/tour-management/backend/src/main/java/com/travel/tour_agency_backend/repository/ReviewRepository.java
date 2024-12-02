package com.travel.tour_agency_backend.repository;

import com.travel.tour_agency_backend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByTourId(Long tourId);
    // Дополнительные методы можно добавить здесь
}