package com.travel.tour_agency_backend.repository;

import com.travel.tour_agency_backend.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {
    // Дополнительные методы можно добавить здесь
}