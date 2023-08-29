package com.example.ampersand02.domain;
import jakarta.persistence.*;
import lombok.Data;

@Data

@Entity
@Table(name = "permission")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id")
    private Long permissionId;

    @Column(name = "permission_name")
    private String permissionName;

    @Column(name = "permission_desc")
    private String permissionDescription;

    // Constructors, getters, setters, and other methods

    // Default constructor
    public Permission() {
    }
}
