package com.example.ampersand02.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "user_detail")
@Entity
public class UserDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "role") // กำหนดชื่อคอลัมน์ที่เชื่อมกับ Role
    private Role role;
}
