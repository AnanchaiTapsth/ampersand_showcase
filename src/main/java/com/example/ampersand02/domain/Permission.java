package com.example.ampersand02.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data

@Entity
@Table(name = "permission")
@JsonIgnoreProperties("roles")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id")
    private Long permissionId;

    @Column(name = "permission_name")
    private String permissionName;

    @Column(name = "permission_desc")
    private String permissionDescription;

    @ManyToOne
    @JoinColumn(name = "role", referencedColumnName = "role_id")
    // @JsonIgnore // ไม่แสดงข้อมูล role ใน JSON
    private Role role;
//    @ManyToMany(mappedBy = "roleId") // ระบุ mappedBy เพื่อระบุว่าเป็นความสัมพันธ์แบบ Bidirectional
//    @ManyToMany
//    @JoinTable(
//            name = "role", // ชื่อตารางผูกกัน
//            joinColumns = @JoinColumn(name = "role"),
//            inverseJoinColumns = @JoinColumn(name = "role_id")
//    )
//    private List<Role> role = new ArrayList<>();

    // Getters and setters

    // Default constructor
    public Permission() {
    }
}
