package com.travel.tour_agency_backend.controller;


import com.travel.tour_agency_backend.entity.Tour;
import com.travel.tour_agency_backend.entity.User;
import com.travel.tour_agency_backend.entity.Booking;
import com.travel.tour_agency_backend.service.BookingService;
import com.travel.tour_agency_backend.repository.UserRepository;
import com.travel.tour_agency_backend.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import com.travel.tour_agency_backend.repository.TourRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository; // !!!

    @Autowired
    private TourRepository tourRepository;

    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/{id}")
    public Optional<Booking> getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    @PostMapping
    @Transactional // !!!  Добавьте @Transactional
    public ResponseEntity<?> createBooking(@RequestHeader("Authorization") String authHeader, @RequestBody Booking booking) {
        System.out.println("Received booking data: " + booking);
        try {
            String token = authHeader.substring(7);

            if (tokenProvider.validateToken(token)) {
                Long userId = tokenProvider.getUserIdFromJWT(token);
                User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
                System.out.println("userId from token: " + userId);
                booking.setUser(user);

                // !!! Получаем объект Tour по id и устанавливаем его в booking
                Long tourId = booking.getTour().getId();

                Tour tour = tourRepository.findById(tourId)
                        .orElseThrow(() -> new IllegalArgumentException("Tour not found with ID: " + tourId));
                booking.setTour(tour);
                booking.setTotalPrice(tour.getPrice().multiply(BigDecimal.valueOf(booking.getNumPeople())));
                Booking savedBooking = bookingService.createBooking(booking);
                System.out.println("Received booking data: " + savedBooking);
                return ResponseEntity.ok(savedBooking);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create booking: " + e.getMessage());
        }

    }

    @PutMapping("/{id}")
    public Booking updateBooking(@PathVariable Long id, @RequestBody Booking booking) {
        return bookingService.updateBooking(id, booking);
    }

    @DeleteMapping("/{id}")
    public void deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
    }
}