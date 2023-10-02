package com.example.ampersand02.payload.premission;

import lombok.Data;

@Data
public class PermissionDto {
    private Long id;
    private String permissionName;
    private String permissionDescription;

    // Constructors, getters, and setters
}
