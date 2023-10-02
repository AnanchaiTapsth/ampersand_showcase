package com.example.ampersand02.service.impl;

import com.example.ampersand02.common.Constants;
import com.example.ampersand02.entity.Permission;
import com.example.ampersand02.entity.Role;
import com.example.ampersand02.exception.ErrorMessageException;
import com.example.ampersand02.payload.premission.PermissionDto;
import com.example.ampersand02.payload.role.RoleDto;
import com.example.ampersand02.payload.role.RoleDtoFindAll;
import com.example.ampersand02.repository.PermissionRepository;
import com.example.ampersand02.repository.RoleRepository;
import com.example.ampersand02.service.RoleService;
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
public class RoleServiceImpl implements RoleService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PermissionRepository permissionRepository;

    public ResponseEntity<Object> createRole(Role role){
        try {
            LOGGER.info(" role In ServiceImpl = ====== : {}" , role);
            if (roleRepository.existsByRoleName(role.getRoleName())) {
                return ResponseHelper.bad(ERR_AUTH_1111.getMsg());
            }
            Role roleReturn = new Role();
            roleReturn.setRoleName(role.getRoleName());
            LOGGER.info("(role.getPermissions().get(0).getId()) ====== : {}" , role.getPermissions().get(0).getId());
            Optional<Permission> opt = permissionRepository.findById(role.getPermissions().get(0).getId());
            LOGGER.info(" Optional<Permission> opt = ====== : {}" , opt);
            if(opt.isEmpty()) {
                return ResponseHelper.bad(ERR_AUTH_0004.getMsg());
            }
            List<Permission> permission = new ArrayList<>();
            permission.add(opt.get());
            LOGGER.info("permission = ====== : {}" , permission);
            roleReturn.setPermissions(permission);
            roleRepository.save(roleReturn);
            return ResponseHelper.success(Constants.MESSAGE.INFO_MTS_0000.getMsg());
        } catch (Exception e){
            // ส่ง Response ข้อผิดพลาดกลับ
            return ResponseHelper.bad(ERR_AUTH_0004.getMsg());
        }
    }

    public List<RoleDtoFindAll> convertToRoleDtoList(List<Role> roleList) {
        try {
            List<RoleDtoFindAll> roleDtoList = new ArrayList<>();
            for (Role role : roleList) {
                RoleDtoFindAll roleDto = new RoleDtoFindAll();
                roleDto.setId(role.getId());
                roleDto.setRoleName(role.getRoleName());
                roleDtoList.add(roleDto);
            }
            return roleDtoList;
        } catch (Exception e){
            // ส่ง Response ข้อผิดพลาดกลับ
            throw new ErrorMessageException(ERR_AUTH_0003);
        }
    }
   public List<RoleDto> convertRoleFindById(List<Role> roleList) {
       try {
           List<RoleDto> roleDtoList = new ArrayList<>();
           for (Role role : roleList) {
               RoleDto roleDto = new RoleDto();
               roleDto.setId(role.getId());
               roleDto.setRoleName(role.getRoleName());
               List<PermissionDto> permissionDtoList = new ArrayList<>();
               for (Permission permission : role.getPermissions()) {
                   PermissionDto permissionDto = new PermissionDto();
                   permissionDto.setId(permission.getId());
                   permissionDto.setPermissionName(permission.getPermissionName());
                   permissionDto.setPermissionDescription(permission.getPermissionDescription());
                   permissionDtoList.add(permissionDto);
               }
               roleDto.setPermissions(permissionDtoList);
               roleDtoList.add(roleDto);
           }
           return roleDtoList;
       }catch (Exception e){
           // ส่ง Response ข้อผิดพลาดกลับ
           throw new ErrorMessageException(ERR_AUTH_0003);
       }
   }
}
