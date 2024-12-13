package main.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Сущность «Услуга».
 */
@Entity
@Table(name = "products")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Название услуги.
     */
    @Column(name = "name")
    private String name;

    /**
     * Идентификатор исполнителя услуги.
     */
    @Column(name = "manager_id")
    private Long managerId;

    /**
     * Продолжительность услуги в минутах.
     */
    @Column(name = "duration")
    private Integer duration;

    /**
     * Цена услуги в рублях.
     */
    @Column(name = "cost")
    private Integer cost;
}
