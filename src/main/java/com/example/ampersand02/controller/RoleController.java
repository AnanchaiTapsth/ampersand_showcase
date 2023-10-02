package com.example.ampersand02.controller;

import com.example.ampersand02.entity.Role;
import com.example.ampersand02.entity.RolePermission;
import com.example.ampersand02.exception.ErrorMessageException;
import com.example.ampersand02.payload.role.AddAndRemoveRole;
import com.example.ampersand02.payload.role.RoleDto;
import com.example.ampersand02.payload.role.RoleDtoFindAll;
import com.example.ampersand02.repository.RoleRepository;
import com.example.ampersand02.repository.PermissionRepository;
import com.example.ampersand02.service.RoleService;
import com.example.ampersand02.service.TransactionLogService;
import com.example.ampersand02.utils.ResponseHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.example.ampersand02.common.Constants.MESSAGE.*;


@RestController
@RequestMapping("/api/role/")
public class RoleController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleService roleService;

    @Autowired
    TransactionLogService transactionLogService;

    private  ResponseEntity<Object> responseReturn;
    private int httpStatus;
    @GetMapping("checkRoleId/{id}")
    public ResponseEntity<Object> checkRoleId(@PathVariable Long id){
        transactionLogService.setStartTime();
        Role existingRole = roleRepository.findById(id).orElse(null);
        LOGGER.info("existingRole === : {} " ,existingRole);
        if (existingRole == null) {
            responseReturn = ResponseHelper.bad(ERR_AUTH_0002.getMsg());
        }
        else {
            responseReturn = ResponseHelper.success(INFO_MTS_0000.getMsg());
        }
        this.httpStatus = transactionLogService.checkHttp(responseReturn.getStatusCode().value());
        transactionLogService.savetransactionLog("checkRoleId id "+id.toString(), this.httpStatus);
        return responseReturn;
    }

    // URS08: ผู้ดูแลระบบผู้ใช้สามารถสร้างบทบาทได้
    @PostMapping("createRole")
    public ResponseEntity<Object> createRole(@RequestBody Role createRole) {
        try {
            transactionLogService.setStartTime();
            responseReturn = roleService.createRole(createRole);
            this.httpStatus = transactionLogService.checkHttp(responseReturn.getStatusCode().value());
            transactionLogService.savetransactionLog(createRole.toString(), this.httpStatus);
            return responseReturn;
        } catch (Exception e) {
            transactionLogService.savetransactionLog(createRole.toString() , HttpStatus.INTERNAL_SERVER_ERROR.value());
            throw new ErrorMessageException(ERR_AUTH_0003);
        }
    }

    // URS09: ผู้ดูแลระบบผู้ใช้สามารถเรียกดูชื่อบทบาททั้งหมดได้
    @GetMapping("/findRoleAll")
    public ResponseEntity<Object> getRole() {
        try {
            transactionLogService.setStartTime();
            List<Role> roleList = roleRepository.findAll();
            List<RoleDtoFindAll> roleDtoList = roleService.convertToRoleDtoList(roleList);
           // List<RoleDto> roleDtoList = convertToRoleDtoList(roleList);
            responseReturn = ResponseHelper.successWithList(INFO_AUTH_0000.getMsg(), roleDtoList);
            this.httpStatus = transactionLogService.checkHttp(responseReturn.getStatusCode().value());
            transactionLogService.savetransactionLog(responseReturn.toString(), this.httpStatus);
            return responseReturn;
        } catch (Exception e) {
            transactionLogService.savetransactionLog(responseReturn.toString(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            throw new ErrorMessageException(ERR_AUTH_0003);
        }
    }

    // URS10: ผู้ดูแลระบบผู้ใช้สามารถเรียกดูบทบาทและ permission Role_id
    @GetMapping("getRoleAndPermission/{id}")
    public ResponseEntity<Object> getRoleAndPermission(@PathVariable Long id) {
        try {
            transactionLogService.setStartTime();
            Role role= roleRepository.findById(id).orElse(null);
            List<Role> roleList = new ArrayList<>();
            roleList.add(role);
            if (role == null) {
                responseReturn = ResponseHelper.bad(ERR_AUTH_0002.getMsg());
            }else {
                List<RoleDto> roleDtoList = roleService.convertRoleFindById(roleList);
                responseReturn = ResponseHelper.successWithList(INFO_AUTH_0000.getMsg(), roleDtoList);
            }
            this.httpStatus = transactionLogService.checkHttp(responseReturn.getStatusCode().value());
            transactionLogService.savetransactionLog(responseReturn.toString(), this.httpStatus);
            return responseReturn;
        } catch (Exception e) {
            transactionLogService.savetransactionLog(responseReturn.toString(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            throw new ErrorMessageException(ERR_AUTH_0003);
        }
    }

    // URS11: ผู้ดูแลระบบผู้ใช้สามารถแก้ไข Role_name ได้ด้วย Role_id
    @PutMapping ("updateRole/{id}")
    public ResponseEntity<Object> updateRoleById(@PathVariable Long id, @RequestParam(value = "roleName") String roleName) {
        try {
            transactionLogService.setStartTime();
            Role role= roleRepository.findById(id).orElse(null);
            if (role == null) {
                responseReturn = ResponseHelper.bad(ERR_AUTH_0002.getMsg());
            }else {
                role.setRoleName(roleName);
                roleRepository.save(role);
                responseReturn = ResponseHelper.success(INFO_AUTH_0000.getMsg());
        }
            this.httpStatus = transactionLogService.checkHttp(responseReturn.getStatusCode().value());
            transactionLogService.savetransactionLog(responseReturn.toString(), this.httpStatus);
            return responseReturn;
        }catch (Exception e){
            transactionLogService.savetransactionLog(responseReturn.toString(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            throw new ErrorMessageException(ERR_AUTH_0003);
        }
    }


    // URS12: ผู้ดูแลระบบผู้ใช้สามารถลบบทบาทตาม Role_id
    @DeleteMapping("deleteRole/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        try {
            transactionLogService.setStartTime();
            Role role = roleRepository.findById(id).orElse(null);
            if (role == null) {
                responseReturn = ResponseHelper.bad(ERR_AUTH_0002.getMsg());
            } else {
                roleRepository.delete(role);
                responseReturn = ResponseHelper.success(INFO_AUTH_0000.getMsg());
            }
            this.httpStatus = transactionLogService.checkHttp(responseReturn.getStatusCode().value());
            transactionLogService.savetransactionLog(responseReturn.toString(), this.httpStatus);
            return responseReturn;
        } catch (Exception e) {
            transactionLogService.savetransactionLog(responseReturn.toString(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            throw new ErrorMessageException(ERR_AUTH_0003);
        }
    }
    // URS13
 /*   @PostMapping("addAndRemoveRole")
    public ResponseEntity<Object> addAndRemoveRole(@RequestBody AddAndRemoveRole Json) {
        try {
            transactionLogService.setStartTime();
            List<Permission> permissionList = new ArrayList<>();
            List<Role> roleList = new ArrayList<>();
            RolePermission rolePermissionRetrun = new RolePermission();
            Role role = roleRepository.findById(Json.getId()).orElse(null);
            //RolePermission rolePermission = rolePermissionRepository.findById(Json.getId()).orElse(null);
            LOGGER.info("role ==== {}" , role);
            if (role == null) {
                responseReturn = ResponseHelper.bad(ERR_AUTH_0002.getMsg());
            } else {
                roleList.add(role);
                rolePermissionRetrun.setRoleId(roleList.get(0).getId());
                Permission permissionAdd = permissionRepository.findById(Json.getAddPermission()).orElse(null);
                if (permissionAdd == null) {
                    responseReturn = ResponseHelper.bad(ERR_AUTH_0004.getMsg());
                } else {
                    permissionList.add(permissionAdd);
                    rolePermissionRetrun.setPermissionId(permissionList);
                   // roleRepository.save(role);
                    LOGGER.info("rolePermissionRetrun permissionAdd ==== {}" , rolePermissionRetrun);
                    roleRepository.save(rolePermissionRetrun);
                }
                Permission permissionRemove = permissionRepository.findById(Json.getRemovePermission()).orElse(null);
                if (permissionRemove == null) {
                    responseReturn = ResponseHelper.bad(ERR_AUTH_0004.getMsg());
                } else {
                    permissionList.remove(permissionRemove);
                    rolePermissionRetrun.setPermissionId(permissionList);
                    LOGGER.info("rolePermissionRetrun permissionRemove ==== {}" , rolePermissionRetrun);
                    rolePermissionRepository.delete(rolePermissionRetrun);
                    //permissionList.remove(permissionRemove);
                }
                //rolePermissionRepository.save(rolePermissionRetrun);
                responseReturn = ResponseHelper.success(INFO_AUTH_0000.getMsg());
            }
            this.httpStatus = transactionLogService.checkHttp(responseReturn.getStatusCode().value());
            transactionLogService.savetransactionLog(responseReturn.toString(), this.httpStatus);
            return responseReturn;
        } catch (Exception e) {
            transactionLogService.savetransactionLog(responseReturn.toString(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            throw new ErrorMessageException(ERR_AUTH_0003);
        }
    }
    */

}

