package com.example.ampersand02.controller;

import com.example.ampersand02.domain.Role;
import com.example.ampersand02.repository.RoleRepository;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/role")
public class RoleController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ObjectMapper objectMapper; // อินเจ็คต์ของ ObjectMapper


    // URS07: ผู้ดูแลระบบผู้ใช้สามารถแก้ไขบทบาทของผู้ใช้ได้
    @PostMapping("updateRole")
    public ResponseEntity<String> updateRole(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
        headers.add("responseCode", "0");
        LOGGER.info("[START][updateRole][01] json : {}", json);

        try {
            roleService.updateRole(json);
            return new ResponseEntity<String>(new JSONSerializer().deepSerialize("updateRole success"), headers, HttpStatus.OK);
        } catch (Exception ex) {
            LOGGER.error("[ERROR][updateRole] : {}", ex);

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

    // URS08: ผู้ดูแลระบบผู้ใช้สามารถสร้างบทบาทได้
    @PostMapping("createRole")
    public ResponseEntity<String> createRole(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
        headers.add("responseCode", "0");
        LOGGER.info("[START][createRole][01] json : {}", json);

        try {
            roleService.createRole(json);
            return new ResponseEntity<String>(new JSONSerializer().deepSerialize("createRole Success"), headers, HttpStatus.OK);
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
    public List <Role> getRole() {
        LOGGER.info("[START][findAllRole]");
        List<Role> rolesOj = roleRepository.findAll();

        LOGGER.info("[END][findAllRole] Role Count: {}", rolesOj.size());
        LOGGER.info("[TEST][findAllRole] Role == : {}", rolesOj);

        for (Role role : rolesOj) {
            try {
                String rolJson = objectMapper.writeValueAsString(role); // แปลง role เป็น JSON
                LOGGER.info("Role JSON: {}", rolJson); // แสดง JSON ของ role
            } catch (Exception e) {
                LOGGER.error("Error converting Role to JSON: {}", e.getMessage());
            }
        }

        return rolesOj;
    }

    // URS10: ผู้ดูแลระบบผู้ใช้สามารถเรียกดูบทบาทและ permission Role_id
    @GetMapping("getRoleAndPermission")
    public List<Object>  getRoleAndPermission(@RequestParam(value = "roleId", required = true) Long roleId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
        headers.add("responseCode", "0");
        LOGGER.info("[START][getRoleAndPermission][01] roleId : {}", roleId);

            List<Object> Role = roleService.getRoleAndPermission(roleId);
            return Role;

    }

    // URS11: ผู้ดูแลระบบผู้ใช้สามารถแก้ไข Role_name ได้ด้วย Role_id
    @GetMapping("findRoleById/{id}")
    public Role getRoleById(@PathVariable Long id){
        return roleRepository.findById(id).orElse(null);
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

