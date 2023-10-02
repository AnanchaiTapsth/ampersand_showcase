package com.example.ampersand02.entity;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
@Data
@Table(name = "transaction_log")
@Entity
public class TransactionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "request_body", length = 100000)
    private String requestBody;

    private int httpStatus;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}