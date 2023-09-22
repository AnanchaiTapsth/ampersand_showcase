package com.example.ampersand02.controller;

import com.example.ampersand02.domain.Permission;
import com.example.ampersand02.domain.Role;
import com.example.ampersand02.domain.User;
import com.example.ampersand02.repository.RoleRepository;
import com.example.ampersand02.repository.PermissionRepository;
import com.example.ampersand02.service.RoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import flexjson.JSONSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


@RestController
@RequestMapping("/role")
public class RoleController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private ObjectMapper objectMapper; // อินเจ็คต์ของ ObjectMapper



    // URS08: ผู้ดูแลระบบผู้ใช้สามารถสร้างบทบาทได้
    @PostMapping("createRole")
    public ResponseEntity<String> createRole(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
        headers.add("responseCode", "0");
        LOGGER.info("[START][createRole][01] json : {}", json);

        try {
           Role roleReturn = roleService.createRole(json);
            return new ResponseEntity<String>(new JSONSerializer().deepSerialize("Create Role Id = "+roleReturn.getRoleId()+ " and Permission Id = " +roleReturn.getPermission().getPermissionId()+" Success"), headers, HttpStatus.OK);
        } catch (Exception ex) {
            LOGGER.error("[ERROR][createRole] : {}", ex);

            headers.add("responseCode", "-1");
            headers.add("errMsg", ex.getMessage());
            Map<String, Object> result = new HashMap<>();
            result.put("errMsg", ex.getMessage());
            result.put("errorStatus", "-1");
            result.put("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<String>(new JSONSerializer().deepSerialize(result),
                    headers,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // URS09: ผู้ดูแลระบบผู้ใช้สามารถเรียกดูชื่อบทบาททั้งหมดได้
    @GetMapping("/findRoleAll")
    public ResponseEntity<String> getRole() {
        HttpHeaders headers = new HttpHeaders();
        LOGGER.info("[START][findAllRole]");
        List<Role> rolesOj = roleRepository.findAll();

        LOGGER.info("[END][findAllRole] Role Count: {}", rolesOj.size());
        LOGGER.info("[TEST][findAllRole] Role == : {}", rolesOj);
        StringBuilder result = new StringBuilder();
        for (Role role : rolesOj) {
            try {
                // สร้าง JSON Object เฉพาะ roleId และ roleName
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("roleId", role.getRoleId());
                jsonObject.addProperty("roleName", role.getRoleName());

                String rolJson = jsonObject.toString(); // แปลง JSON Object เป็น JSON String
               // String rolJson = objectMapper.writeValueAsString(role); // แปลง role เป็น JSON
                LOGGER.info("Role JSON: {}", rolJson); // แสดง JSON ของ role
                result.append(rolJson).append("\n");
            } catch (Exception e) {
                LOGGER.error("Error converting Role to JSON: {}", e.getMessage());
            }
        }
        return new ResponseEntity<String>(result.toString(), headers, HttpStatus.OK);
//        return new ResponseEntity<String>(new JSONSerializer()
//                .include("roleId")
//                .include("roleName")
//                .exclude("*")
//                .deepSerialize(rolesOj),headers, HttpStatus.OK);
    }

    // URS10: ผู้ดูแลระบบผู้ใช้สามารถเรียกดูบทบาทและ permission Role_id
    @GetMapping("getRoleAndPermission")
    public ResponseEntity<String> getRoleAndPermission(@RequestParam(value = "roleId") Long roleId) {
        HttpHeaders headers = new HttpHeaders();
        LOGGER.info("[START][findAllRole]");
        // List<Role> rolesOj = roleRepository.findAll();
//        List<Permission> permission= permissionRepository.findAll();
        //   V1
        Role roleReturn = roleRepository.findById(roleId).orElseThrow();
        {

            // List<Role> roleReturn = new ArrayList<>();
//        for (Role role : rolesOj) {
//            if(role.getRoleId().longValue() == roleId){
//                roleReturn.add(role);
//            }
//            try {
//                String rolJson = objectMapper.writeValueAsString(roleReturn); // แปลง role เป็น JSON
//                LOGGER.info("Role JSON: {}", roleReturn); // แสดง JSON ของ role
//
//            } catch (Exception e) {
//                LOGGER.error("Error converting Role to JSON: {}", e.getMessage());
//            }
//        }

            return new ResponseEntity<String>(roleReturn.toString(), headers, HttpStatus.OK);

            // V2
//        List<Object> roleList = roleService.getRoleAndPermission(roleId);
//        return new ResponseEntity<String>(roleList.toString(), headers, HttpStatus.OK);

        }
    }

    // URS11: ผู้ดูแลระบบผู้ใช้สามารถแก้ไข Role_name ได้ด้วย Role_id
    @PutMapping("updateRoleById/{id}")
    public ResponseEntity<String> updateRoleById(@PathVariable Long id, @RequestBody Role updateRole) {
        Role existingRole = roleRepository.findById(id).orElse(null);

        if (existingRole == null) {

            return ResponseEntity.ofNullable("This Role ID does not exist.");
        } else {

            LOGGER.info("updateRole JSON: {}", existingRole);
            // อัปเดตข้อมูลผู้ใช้
            existingRole.setRoleName(updateRole.getRoleName());
            roleRepository.save(existingRole);

            return ResponseEntity.ok("Role Update successfully.");
        }
    }

    // URS12: ผู้ดูแลระบบผู้ใช้สามารถลบบทบาทตาม Role_id
    @DeleteMapping("deleteRole/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        Role existingRole = roleRepository.findById(id).orElse(null);

        if (existingRole == null) {
            return ResponseEntity.notFound().build();
        }

        roleRepository.delete(existingRole);
        return ResponseEntity.ok("Role deleted successfully");
    }
}

