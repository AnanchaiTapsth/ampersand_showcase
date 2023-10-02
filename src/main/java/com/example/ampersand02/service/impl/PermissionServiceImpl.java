package com.example.ampersand02.service.impl;

import com.example.ampersand02.common.Constants;
import com.example.ampersand02.entity.Permission;
import com.example.ampersand02.entity.Role;
import com.example.ampersand02.repository.Custom.RoleRepositoryCustom;
import com.example.ampersand02.repository.PermissionRepository;
import com.example.ampersand02.repository.RoleRepository;
import com.example.ampersand02.service.PermissionService;
import com.example.ampersand02.utils.ResponseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.ampersand02.common.Constants.MESSAGE.*;

@Service
public class PermissionServiceImpl implements PermissionService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PermissionRepository permissionRepository;

    public ResponseEntity<Object> createPermission(Permission permission) {
        try {
            LOGGER.info(" Permission In ServiceImpl = ====== : {}", permission);
            if (permissionRepository.existsByPermissionName(permission.getPermissionName())) {
                return ResponseHelper.bad(ERR_AUTH_2222.getMsg());
            }
            Permission permissionReturn = new Permission();
            permissionReturn.setPermissionName(permission.getPermissionName());
            permissionReturn.setPermissionDescription(permission.getPermissionDescription());
            permissionRepository.save(permissionReturn);
            return ResponseHelper.success(Constants.MESSAGE.INFO_MTS_0000.getMsg());
        } catch (Exception e) {
            // ส่ง Response ข้อผิดพลาดกลับ
            return ResponseHelper.bad(ERR_AUTH_0004.getMsg());
        }
    }
}
