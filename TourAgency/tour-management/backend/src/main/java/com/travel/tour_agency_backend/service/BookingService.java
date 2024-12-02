package com.travel.tour_agency_backend.service;

import com.travel.tour_agency_backend.entity.Booking;
import com.travel.tour_agency_backend.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // Получение всех бронирований
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Получение бронирования по id
    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    // Создание нового бронирования
    public Booking createBooking(Booking booking) {
        System.out.println("Saving booking: " + booking);
        return bookingRepository.save(booking);
    }

    // Обновление данных бронирования
    public Booking updateBooking(Long id, Booking booking) {
        Optional<Booking> existingBooking = bookingRepository.findById(id);
        if (existingBooking.isPresent()) {
            booking.setId(id);  // Обновляем id
            return bookingRepository.save(booking);
        }
        return null;  // Можно выбросить исключение, если бронирование не найдено
    }

    // Удаление бронирования
    public void deleteBooking(Long id) {
        bookingRepository.deleteById(id);
    }
}