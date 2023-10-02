package com.example.ampersand02.controller;

import com.example.ampersand02.entity.Permission;
import com.example.ampersand02.exception.ErrorMessageException;
import com.example.ampersand02.repository.PermissionRepository;
import com.example.ampersand02.service.PermissionService;
import com.example.ampersand02.service.TransactionLogService;
import com.example.ampersand02.utils.ResponseHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.ampersand02.common.Constants.MESSAGE.*;

@RestController
@RequestMapping("/api/permission")
public class PermissionController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PermissionService permissionService;


    @Autowired
    TransactionLogService transactionLogService;

    private  ResponseEntity<Object> responseReturn;
    private int httpStatus;
    // URS14: ผู้ดูแลระบบผู้ใช้สามารถสร้างสิทธิ์ได้
    @PostMapping("createPermission")
    public ResponseEntity<Object> createPermission(@RequestBody Permission permissionProfileRequest) {
        try {
            transactionLogService.setStartTime();
            responseReturn = permissionService.createPermission(permissionProfileRequest);
            this.httpStatus = transactionLogService.checkHttp(responseReturn.getStatusCode().value());
            transactionLogService.savetransactionLog(permissionProfileRequest.toString(), this.httpStatus);
            return responseReturn;
        } catch (Exception e) {
            transactionLogService.savetransactionLog(permissionProfileRequest.toString() , HttpStatus.INTERNAL_SERVER_ERROR.value());
            throw new ErrorMessageException(ERR_AUTH_0003);
        }
    }
    //URS15: ผู้ดูแลระบบผู้ใช้สามารถเลือกดูการอนุญาตทั้งหมดได้
    @GetMapping("/findPermissionAll")
    public ResponseEntity<Object> getPermission() {
        try {
            transactionLogService.setStartTime();
            List<Permission> permissionList = permissionRepository.findAll();
            LOGGER.info("permissionList ==== {}", permissionList);
            responseReturn = ResponseHelper.successWithList(INFO_AUTH_0000.getMsg(), permissionList);
            this.httpStatus = transactionLogService.checkHttp(responseReturn.getStatusCode().value());
            transactionLogService.savetransactionLog(responseReturn.toString(), this.httpStatus);
            return responseReturn;
        } catch (Exception e) {
            transactionLogService.savetransactionLog(responseReturn.toString(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            throw new ErrorMessageException(ERR_AUTH_0003);
        }
    }
    @DeleteMapping ("/deletePermission/{id}")
    public ResponseEntity<Object> deletePermission(@PathVariable Long id) {
        try {
            transactionLogService.setStartTime();
            Permission permission= permissionRepository.findById(id).orElse(null);
            LOGGER.info("permission ======== {}" , permission);
            if (permission == null) {
                responseReturn = ResponseHelper.bad(ERR_AUTH_0004.getMsg());
            }else {
                permissionRepository.delete(permission);
                responseReturn = ResponseHelper.success(INFO_AUTH_0000.getMsg());
            }
            LOGGER.info("responseReturn ======== {}" , responseReturn);
            this.httpStatus = transactionLogService.checkHttp(responseReturn.getStatusCode().value());
            transactionLogService.savetransactionLog(responseReturn.toString(), this.httpStatus);
            return responseReturn;
        } catch (Exception e) {
            transactionLogService.savetransactionLog(responseReturn.toString(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            throw new ErrorMessageException(ERR_AUTH_0003);
        }
    }


}
