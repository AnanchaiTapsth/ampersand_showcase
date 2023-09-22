package com.example.ampersand02.service.impl;
import com.example.ampersand02.repository.PermissionRepository;
import com.example.ampersand02.domain.Permission;

import com.example.ampersand02.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;
    public Permission createPermission(Permission permissionProfileRequest){
        // ตรวจสอบว่าชื่อผู้ใช้ไม่ซ้ำในฐานข้อมูล
        if (permissionRepository.existsByPermissionName(permissionProfileRequest.getPermissionName())) {
            throw new IllegalArgumentException("PermissionName นี้มีอยู่ในระบบแล้ว");
        }

        // สร้าง User จากข้อมูลใน user
        Permission permissionReturn = new Permission();
        permissionReturn.setPermissionName(permissionProfileRequest.getPermissionName());
        permissionReturn.setPermissionDescription(permissionProfileRequest.getPermissionDescription());


        // บันทึก User ในฐานข้อมูล
        return permissionRepository.save(permissionReturn);
    }
}
