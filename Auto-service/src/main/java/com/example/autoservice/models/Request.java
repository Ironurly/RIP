package com.example.autoservice.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clientName;
    private String phoneNumber;
    private String carModel;
    private String issueDescription;
    private String appointmentDate;
}
