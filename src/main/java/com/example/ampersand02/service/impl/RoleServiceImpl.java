package com.example.ampersand02.service.impl;

import com.example.ampersand02.domain.Permission;
import com.example.ampersand02.domain.Role;
import com.example.ampersand02.repository.Custom.RoleRepositoryCustom;
import com.example.ampersand02.repository.PermissionRepository;
import com.example.ampersand02.repository.RoleRepository;
import com.example.ampersand02.service.RoleService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    RoleRepositoryCustom roleRepositoryCustom;

    @Override
    public void updateRole(String json){
        LOGGER.info("[updateRoleServiceImpl][updateRole][01]");
        JSONParser parser = new JSONParser();
        JSONObject obj = null;
        if (json != null && !json.isEmpty()) {
            try {
                obj = (JSONObject) parser.parse(json);
            } catch (ParseException e) {
                // การแปลงไม่สำเร็จ
                LOGGER.error("Error parsing JSON: {}", e.getMessage());
            }
        } else {
            LOGGER.error("Empty or null JSON input");
        }
        LOGGER.error("obj ========== : {}" , obj);

        String roleId = obj.get("roleId").toString();
        String roleName = obj.get("roleName").toString();
        String permission = obj.get("permission").toString();

        Role roleReal = findById(Long.valueOf(roleId));
        Optional<Permission> permissionObject = permissionRepository.findById(Long.valueOf(permission));
        LOGGER.info("permissionObject value: {}", permissionObject.get().getPermissionId());
        if (!permissionObject.isPresent()) {
            throw new RuntimeException("permission not found");

        }
        LOGGER.info("savepermissionObject id: {}", permissionObject.get().getPermissionId());

        roleReal.setRoleName(roleName);
        roleReal.setPermission(permissionObject.get());
        LOGGER.info("roleReal ======================= : {}", roleReal);
        roleRepository.save(roleReal);


     }
    @Override
    public void createRole(String json){
        LOGGER.info("[createRoleServiceImpl][createRole][01]");
        JSONParser parser = new JSONParser();
        JSONObject obj = null;
        if (json != null && !json.isEmpty()) {
            try {
                obj = (JSONObject) parser.parse(json);
            } catch (ParseException e) {
                // การแปลงไม่สำเร็จ
                LOGGER.error("Error parsing JSON: {}", e.getMessage());
            }
        } else {
            LOGGER.error("Empty or null JSON input");
        }
        LOGGER.error("obj CreateRole ========== : {}" , obj);

        //String roleId = obj.get("roleId").toString();
        String roleName = obj.get("roleName").toString();
        String permission = obj.get("permission").toString();

        Role roleReal = new Role();
        Optional<Permission> permissionObject = permissionRepository.findById(Long.valueOf(permission));
        LOGGER.info("permissionObject value: {}", permissionObject.get().getPermissionId());
        if (!permissionObject.isPresent()) {
            throw new RuntimeException("permission not found");
        }
        LOGGER.info("savePermissionObject id: {}", permissionObject.get().getPermissionId());

        roleReal.setRoleName(roleName);
        roleReal.setPermission(permissionObject.get());
        LOGGER.info("roleReal ======================= : {}", roleReal);
        roleRepository.save(roleReal);


    }

    @Override
    public  List<Object> getRoleAndPermission(Long roleId){
        List<Object> listRole = roleRepositoryCustom.getRoleAndPermission(roleId);
        LOGGER.debug("listRole size : {}", listRole.size());
        LOGGER.debug("listRole ========== : {}", listRole);
        return listRole;
    }

    public Role findById(Long id) {
        LOGGER.info("[EmployeeServiceImpl][findById][01] id : {}", id);

        try {
            Optional<Role> role = roleRepository.findById(id);
            if (role.isPresent()) {
                LOGGER.info("[RoleServiceImpl][findById][02] role is not null!");
                LOGGER.info(" ================== [findById] id :{}", role.get().getRoleId());
                return role.get();
            } else {
                LOGGER.info("[RoleServiceImpl][findById][03] role is null!");
                return null;
            }

        } catch (Exception ex) {
            LOGGER.error("[ERROR][RoleServiceImpl][findById] errorMsg : {}", ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

}
