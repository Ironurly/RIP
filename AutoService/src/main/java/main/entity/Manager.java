package main.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Сущность «Исполнитель услуги».
 */
@Entity
@Table(name = "managers")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * ФИО исполнителя услуги.
     */
    @Column(name = "name")
    private String name;

    /**
     * Описание к исполнителю.
     */
    @Column(name = "description")
    private String description;
}
