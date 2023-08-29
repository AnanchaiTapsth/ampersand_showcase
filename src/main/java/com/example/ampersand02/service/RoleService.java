package com.example.ampersand02.service;

import java.util.List;
import com.example.ampersand02.domain.Role;
import com.example.ampersand02.repository.RoleRepository;

public interface RoleService {

   void updateRole(String json);
   void createRole(String json);
   List<Object> getRoleAndPermission(Long roleId);
}
