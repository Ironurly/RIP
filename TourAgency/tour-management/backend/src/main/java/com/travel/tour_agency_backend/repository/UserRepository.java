package com.travel.tour_agency_backend.repository;

import com.travel.tour_agency_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Дополнительные методы можно добавить здесь
    Optional<User> findByEmail(String email);
}