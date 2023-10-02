package com.example.ampersand02.controller;
import com.example.ampersand02.entity.Role;
import com.example.ampersand02.entity.UserDetail;
import com.example.ampersand02.exception.ErrorMessageException;
import com.example.ampersand02.service.UserDetailService;
import com.example.ampersand02.service.TransactionLogService;
import com.example.ampersand02.utils.ResponseHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.ampersand02.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

import static com.example.ampersand02.common.Constants.MESSAGE.*;

@Slf4j
@RestController
@RequestMapping("/api/user/")
public class UserDetailController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    UserDetailRepository userRepository;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    TransactionLogService transactionLogService;

    @Autowired
    RoleRepository roleRepository;

    private int httpStatus;

    private  ResponseEntity<Object> responseReturn;

    // URS02: ผู้ดูแลระบบผู้ใช้สามารถสร้างโปรไฟล์ผู้ใช้ได้
    @PostMapping("/createUser")
    public ResponseEntity<Object> createUserProfile(@RequestBody UserDetail userProfileRequest) {
        try {
            transactionLogService.setStartTime();
            responseReturn = userDetailService.createUserProfile(userProfileRequest);
            LOGGER.info("userReturn ===  : {} " ,responseReturn );
            LOGGER.info("userReturn.getStatusCodeValue() ===  : {} " ,responseReturn.getStatusCode().value() );
            this.httpStatus = transactionLogService.checkHttp(responseReturn.getStatusCode().value());
            transactionLogService.savetransactionLog(userProfileRequest.toString(), this.httpStatus);
            return responseReturn;
        } catch (Exception e){
            transactionLogService.savetransactionLog(userProfileRequest.toString() , HttpStatus.INTERNAL_SERVER_ERROR.value());
            throw new ErrorMessageException(ERR_AUTH_0003);
        }
    }

    // URS03: ผู้ดูแลระบบผู้ใช้สามารถแก้ไขโปรไฟล์ผู้ใช้โดย user_id
    @PutMapping("updateUser/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable Long id, @RequestBody UserDetail updatedUser) {
        try {
            transactionLogService.setStartTime();
            UserDetail existingUser = userDetailRepository.findById(id).orElse(null);
            if (existingUser == null) {
                responseReturn = ResponseHelper.bad(ERR_AUTH_0001.getMsg());
            } else {
                LOGGER.info("updateUser JSON: {}", existingUser);
                existingUser.setFirstName(updatedUser.getFirstName());
                existingUser.setLastName(updatedUser.getLastName());
                userDetailRepository.save(existingUser);
                responseReturn = ResponseHelper.success(INFO_MTS_0000.getMsg());
            }
            this.httpStatus = transactionLogService.checkHttp(responseReturn.getStatusCode().value());
            transactionLogService.savetransactionLog(updatedUser.toString(), this.httpStatus);
            return responseReturn;
        } catch (Exception e) {
            transactionLogService.savetransactionLog(updatedUser.toString() , HttpStatus.INTERNAL_SERVER_ERROR.value());
            throw new ErrorMessageException(ERR_AUTH_0003);
        }

    }

    // URS04: ผู้ดูแลระบบผู้ใช้สามารถลบโปรไฟล์ผู้ใช้โดย user_id
    @DeleteMapping("deleteUser/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        try {
            transactionLogService.setStartTime();
            UserDetail existingUser = userDetailRepository.findById(id).orElse(null);
            if (existingUser == null) {
                responseReturn = ResponseHelper.bad(ERR_AUTH_0001.getMsg());
            } else {
                userRepository.delete(existingUser);
                responseReturn = ResponseHelper.success(INFO_MTS_0000.getMsg());
            }
            this.httpStatus = transactionLogService.checkHttp(responseReturn.getStatusCode().value());
            transactionLogService.savetransactionLog("deleteUser id " + id.toString(), this.httpStatus);
            return responseReturn;
        }catch (Exception e) {
            transactionLogService.savetransactionLog("deleteUser id " + id.toString() , HttpStatus.INTERNAL_SERVER_ERROR.value());
            throw new ErrorMessageException(ERR_AUTH_0003);
        }

    }

    //URS05: ผู้ดูแลระบบผู้ใช้สามารถเรียกดูข้อมูลผู้ใช้ตาม user_id
    @GetMapping("findUserById/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id){
        try {
            transactionLogService.setStartTime();
            UserDetail existingUser = userDetailRepository.findById(id).orElse(null);
            if (existingUser == null) {
                responseReturn = ResponseHelper.bad(ERR_AUTH_0001.getMsg());
            } else {
                LOGGER.info("updateUser JSON: {}", existingUser);
                 responseReturn = userDetailService.getUserById(existingUser);
            }
            this.httpStatus = transactionLogService.checkHttp(responseReturn.getStatusCode().value());
            transactionLogService.savetransactionLog(responseReturn.toString(), this.httpStatus);
            return responseReturn;
        }
        catch (Exception e){
            transactionLogService.savetransactionLog(responseReturn.toString() , HttpStatus.INTERNAL_SERVER_ERROR.value());
            throw new ErrorMessageException(ERR_AUTH_0003);
        }
    }

    //URS06: ผู้ดูแลระบบสามารถเรียกดูข้อมูลผู้ใช้ทั้งหมดตาม first_name, Last_name (ต้องรันใหม่)
    @GetMapping("findUserByFnOrLn")
    public ResponseEntity<Object> findUserByFnOrLn(
            @RequestParam(value = "firstName") String firstName ,
            @RequestParam(value = "lastName") String lastName){
        try {
            transactionLogService.setStartTime();
            List<Object> user  = userDetailService.findUserByFnOrLn(firstName , lastName);
            this.httpStatus = transactionLogService.checkHttp(HttpStatus.OK.value());
            transactionLogService.savetransactionLog(user.toString(), this.httpStatus);
            return ResponseHelper.response(HttpStatus.OK , user);
        }
        catch (Exception e){
            transactionLogService.savetransactionLog(e.getMessage() , HttpStatus.INTERNAL_SERVER_ERROR.value());
            throw new ErrorMessageException(ERR_AUTH_0003);
        }
    }

    // URS07: ผู้ดูแลระบบผู้ใช้สามารถแก้ไขบทบาทของผู้ใช้ได้
    @PutMapping("updateRoleByUser/{id}")
    public ResponseEntity<Object> updateRoleByUser(@PathVariable Long id, @RequestBody UserDetail updatedUser) {
        try {
            transactionLogService.setStartTime();
            UserDetail existingUser = userRepository.findById(id).orElse(null);
            if (existingUser == null) {
                responseReturn = ResponseHelper.bad(ERR_AUTH_0001.getMsg());
            } else {
                Optional<Role> opt = roleRepository.findById(updatedUser.getRole().getId());
                if (opt.isEmpty()) {
                    responseReturn = ResponseHelper.bad(ERR_AUTH_0002.getMsg());
                    this.httpStatus = transactionLogService.checkHttp(responseReturn.getStatusCode().value());
                    transactionLogService.savetransactionLog(updatedUser.toString(), this.httpStatus);
                } else {
                    existingUser.setRole(updatedUser.getRole()); // อัปเดต Role ตาม ID ที่ส่งมาใน JSON
                    userRepository.save(existingUser);
                    responseReturn = ResponseHelper.success(INFO_MTS_0000.getMsg());
                    this.httpStatus = transactionLogService.checkHttp(responseReturn.getStatusCode().value());
                    transactionLogService.savetransactionLog(updatedUser.toString(), this.httpStatus);
                }
            }
            return responseReturn;
        } catch (Exception e){
            transactionLogService.savetransactionLog(updatedUser.toString(), this.httpStatus);
            throw new ErrorMessageException(ERR_AUTH_0002);
        }
    }

}

