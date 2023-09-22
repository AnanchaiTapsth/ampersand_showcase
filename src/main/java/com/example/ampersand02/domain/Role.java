package com.example.ampersand02.domain;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data

@Entity
@Table(name = "role")
@JsonIgnoreProperties("permissions")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "role_name")
    private String roleName;

//    @ManyToMany(mappedBy = "permission_id")
//    @ManyToMany()
//    @JoinTable(
//            name = "permission", // ชื่อตารางผูกกัน
//            joinColumns = @JoinColumn(name = "role"), // คอลัมน์ที่เชื่อมโยงจาก Role
//            inverseJoinColumns = @JoinColumn(name = "permission_id") // คอลัมน์ที่เชื่อมโยงจาก Permission
//    )
//    private List<Permission> permission = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "permission", referencedColumnName = "permission_id")
    private Permission permission;

//    @OneToMany
//    @JoinColumn(name = "permission", referencedColumnName = "permission_id")
//    private List <Permission> permissions = new ArrayList<>();
    public Role() {
    }
}