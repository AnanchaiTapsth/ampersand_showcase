package com.example.ampersand02.controller;

import com.example.ampersand02.domain.Permission;
import com.example.ampersand02.repository.PermissionRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private ObjectMapper objectMapper; // อินเจ็คต์ของ ObjectMapper

    // URS14: ผู้ดูแลระบบผู้ใช้สามารถสร้างสิทธิ์ได้
    @PostMapping("createPermission")
    public Permission createUser(@RequestBody Permission permission) {
        LOGGER.info("permission JSON: {}", permission);
        return permissionRepository.save(permission);
    }

    //URS15: ผู้ดูแลระบบผู้ใช้สามารถเลือกดูการอนุญาตทั้งหมดได้
    @GetMapping("/findPermissionAll")
    public List <Permission> getPermission() {
        LOGGER.info("[START][findAllPermission]");
        List<Permission> permissionsOj = permissionRepository.findAll();

        LOGGER.info("[END][findAllPermission] permission Count: {}", permissionsOj.size());
        LOGGER.info("[TEST][findAllPermission] permission == : {}", permissionsOj);

        for (Permission permission : permissionsOj) {
            try {
                String rolJson = objectMapper.writeValueAsString(permission); //
                LOGGER.info("permission JSON: {}", rolJson); // แสดง JSON ของ permission
            } catch (Exception e) {
                LOGGER.error("Error converting permission to JSON: {}", e.getMessage());
            }
        }

        return permissionsOj;
    }

    @DeleteMapping("deletePermission/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        Permission existingPermission = permissionRepository.findById(id).orElse(null);

        if (existingPermission == null) {
            return ResponseEntity.notFound().build();
        }

        permissionRepository.delete(existingPermission);
        return ResponseEntity.ok("Permission deleted successfully");
    }

}
