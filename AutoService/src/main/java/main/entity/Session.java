package main.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

/**
 * Сущность «Пользовательская сессия».
 */
@Entity
@Table(name = "sessions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Идентификатор пользователя.
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * Дата и время начала сессии.
     */
    @Column(name = "beginDate")
    private Date beginDate;
}
