package main.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

/**
 * Сущность «Запись».
 */
@Entity
@Table(name = "appointments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Идентификатор услуги.
     */
    @Column(name = "product_id")
    private Long productId;

    /**
     * Идентификатор пользователя.
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * Марка машины.
     */
    @Column(name = "car_brand")
    private String carBrand;

    /**
     * Номер машины.
     */
    @Column(name = "car_number")
    private String carNumber;

    /**
     * Время записи.
     */
    @Column(name = "start_time")
    private Date startTime;
}
