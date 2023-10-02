package com.example.ampersand02.service;

import com.example.ampersand02.entity.Permission;

import org.springframework.http.ResponseEntity;

public interface PermissionService {
    ResponseEntity<Object> createPermission(Permission permission);
}
