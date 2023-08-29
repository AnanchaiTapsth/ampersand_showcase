package com.example.ampersand02.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;
@Data
@Table(name = "\"user\"")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    @JsonIgnore // ไม่แสดงข้อมูล role ใน JSON
    private Role role;


    // public User() {
    //}
}
