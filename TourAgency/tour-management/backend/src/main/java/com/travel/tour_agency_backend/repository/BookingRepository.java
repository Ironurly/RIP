package com.travel.tour_agency_backend.repository;

import com.travel.tour_agency_backend.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    // Дополнительные методы можно добавить здесь
    void deleteByTourId(Long tourId);
}