package com.travel.tour_agency_backend.service;

import com.travel.tour_agency_backend.entity.Tour;
import com.travel.tour_agency_backend.repository.TourRepository;
import com.travel.tour_agency_backend.repository.BookingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;
@Service
public class TourService {

    private final TourRepository tourRepository;

    @Autowired
    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    @Autowired
    private BookingRepository bookingRepository;
    // Получение всех туров
    public List<Tour> getAllTours() {
        return tourRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Tour> searchTours(String city, Integer distance, Integer maxGroupSize) { // !!!  Метод для поиска
        return tourRepository.findAll().stream()
                .filter(tour -> city == null || tour.getCity().contains(city))
                .filter(tour -> distance == null || tour.getDistance() >= distance)
                .filter(tour -> maxGroupSize == null || tour.getMaxPeople() >= maxGroupSize)
                .collect(Collectors.toList()); // !!! Collectors.toList()
    }


    // Получение тура по id
    public Optional<Tour> getTourById(Long id) {
        return tourRepository.findById(id);
    }

    // Создание нового тура
    public Tour createTour(Tour tour) {
        return tourRepository.save(tour);
    }

    // Обновление данных тура
    public Tour updateTour(Long id, Tour tour) {
        Optional<Tour> existingTour = tourRepository.findById(id);
        if (existingTour.isPresent()) {
            tour.setId(id);  // Обновляем id
            return tourRepository.save(tour);
        }
        return null;  // Можно выбросить исключение, если тур не найден
    }

    // Удаление тура
    @Transactional
    public void deleteTour(Long id) {
        bookingRepository.deleteByTourId(id);
        tourRepository.deleteById(id);
    }
}