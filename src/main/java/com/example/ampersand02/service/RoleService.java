package com.example.ampersand02.service;

import com.example.ampersand02.entity.Role;
import com.example.ampersand02.payload.role.RoleDto;
import com.example.ampersand02.payload.role.RoleDtoFindAll;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoleService {
    ResponseEntity<Object> createRole(Role role);
    List<RoleDtoFindAll> convertToRoleDtoList(List<Role> roleList);
    List<RoleDto> convertRoleFindById(List<Role> roleList);
}
