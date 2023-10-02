package com.example.ampersand02.service.impl;

import com.example.ampersand02.common.Constants;
import com.example.ampersand02.entity.Role;
import com.example.ampersand02.entity.UserDetail;
import com.example.ampersand02.exception.ErrorMessageException;
import com.example.ampersand02.repository.RoleRepository;
import com.example.ampersand02.repository.Custom.UserDetailRepositoryCustom;
import com.example.ampersand02.repository.UserDetailRepository;
import com.example.ampersand02.service.UserDetailService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.ampersand02.utils.ResponseHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.ampersand02.common.Constants.MESSAGE.*;
@Slf4j
@Service
public class UserDetailServiceImpl implements UserDetailService {

private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserDetailRepository userRepository;

    @Autowired
    UserDetailRepositoryCustom userRepositoryCustom;

    @Autowired
    private RoleRepository roleRepository;


   public ResponseEntity <Object> createUserProfile(UserDetail user) {
        try {
            // ตรวจสอบว่าชื่อผู้ใช้ไม่ซ้ำในฐานข้อมูล
            if (userRepository.existsByUsername(user.getUsername())) {
                return ResponseHelper.bad(ERR_AUTH_0000.getMsg());
            }
            UserDetail userReturn = new UserDetail();
            userReturn.setUsername(user.getUsername());
            userReturn.setPassword(user.getPassword());
            userReturn.setFirstName(user.getFirstName());
            userReturn.setLastName(user.getLastName());
            LOGGER.info("u user.getRole().getId() ======== "+ user.getRole().getId() );
            LOGGER.info("RoleId ====== : {}" , roleRepository.findById(user.getRole().getId()));
            Optional<Role> opt = roleRepository.findById(user.getRole().getId());
            if(opt.isEmpty()) {
                return ResponseHelper.bad(ERR_AUTH_0002.getMsg());
            }
            Role role = opt.get();
            LOGGER.info("role ====== : {}" , role);
            userReturn.setRole(role);
            // บันทึก User ในฐานข้อมูล
            userRepository.save(userReturn);
            // ส่ง Response สำเร็จกลับ
            return ResponseHelper.success(Constants.MESSAGE.INFO_MTS_0000.getMsg());
        } catch (Exception e){
            // ส่ง Response ข้อผิดพลาดกลับ
            throw new ErrorMessageException(ERR_AUTH_9999);
        }
    }
    public  ResponseEntity <Object> getUserById(UserDetail existingUser) {
        try {
            log.info("obj ====================================: {}", existingUser);
            Map<String, Object> data = new HashMap<>();
            data.put("user_id", existingUser.getId());
            data.put("password", existingUser.getPassword());
            data.put("username", existingUser.getUsername());
            data.put("first_name", existingUser.getFirstName());
            data.put("last_name", existingUser.getLastName());
            data.put("role_id", existingUser.getRole().getId());
            log.info("data ====================================: {}", data);
            return ResponseHelper.response(HttpStatus.OK, data);
        } catch (Exception e) {
            // ส่ง Response ข้อผิดพลาดกลับ
            throw new ErrorMessageException(ERR_AUTH_9999);
        }
    }
    public List<Object> findUserByFnOrLn(String firstName , String lastName){
       try {
           List<Object> ResponseRetrun = userRepositoryCustom.findUserByFnOrLn(firstName , lastName);
           return ResponseRetrun;
       } catch (Exception e) {
           throw new ErrorMessageException(ERR_AUTH_9999);
       }
    }
}
