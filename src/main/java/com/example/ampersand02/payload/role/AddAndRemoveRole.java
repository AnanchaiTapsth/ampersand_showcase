package com.example.ampersand02.payload.role;

import com.example.ampersand02.entity.Permission;
import com.example.ampersand02.payload.premission.PermissionDto;
import lombok.Data;

import java.util.List;

@Data
public class AddAndRemoveRole {
    private Long id;
    private Long addPermission;
    private Long removePermission;
}
