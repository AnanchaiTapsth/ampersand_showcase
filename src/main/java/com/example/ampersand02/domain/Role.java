package com.example.ampersand02.domain;
import java.util.Set;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data

@Entity
@Table(name = "role")
@JsonIgnoreProperties("permission")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_name")
    private String roleName;

    @ManyToOne
    @JoinColumn(name = "permission_id", referencedColumnName = "permission_id")
    @JsonManagedReference // แสดงข้อมูล permission ใน JSON
    private Permission permission;


    @OneToMany(mappedBy = "role")
    @JsonBackReference // ไม่แสดงข้อมูล users ใน JSON
    private Set<User> users;

    // Default constructor
    public Role() {
    }

}
