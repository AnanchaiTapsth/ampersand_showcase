package com.example.ampersand02.payload.role;

import com.example.ampersand02.payload.premission.PermissionDto;
import lombok.Data;

import java.util.List;

@Data
public class RoleDto {
    private Long id;
    private String roleName;
    private List<PermissionDto> permissions;

    // Constructors, getters, and setters
}
