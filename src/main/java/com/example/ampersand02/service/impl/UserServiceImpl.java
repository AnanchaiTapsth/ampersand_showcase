package com.example.ampersand02.service.impl;

import com.example.ampersand02.domain.Role;
import com.example.ampersand02.domain.User;
import com.example.ampersand02.repository.RoleRepository;
import com.example.ampersand02.repository.UserRepository;
import com.example.ampersand02.repository.Custom.UserRepositoryCustom;
import com.example.ampersand02.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRepositoryCustom userRepositoryCustom;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public List<Object> findUserByFnOrLn(String firstName ,String lastName) {
        List<Object> listUser = userRepositoryCustom.findUserByFnOrLn(firstName , lastName);
        LOGGER.debug("listUser size : {}", listUser.size());
        LOGGER.debug("listUser ========== : {}", listUser);
        return listUser;

    }

    public User createUserProfile(User user) {
        // ตรวจสอบว่าชื่อผู้ใช้ไม่ซ้ำในฐานข้อมูล
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("ชื่อผู้ใช้นี้มีอยู่ในระบบแล้ว");
        }

        // สร้าง User จากข้อมูลใน user
        User userReturn = new User();
        userReturn.setUsername(user.getUsername());
        userReturn.setPassword(user.getPassword());
        userReturn.setFirstName(user.getFirstName());
        userReturn.setLastName(user.getLastName());

        // ค้นหาราย Role จาก role_id

        LOGGER.info("RoleId ====== : {}" , roleRepository.findById(user.getRole().getRoleId()));
        Role role = roleRepository.findById(user.getRole().getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("ไม่พบ Role Id นี้ใน Data"));

        // กำหนด Role ให้กับ User
        userReturn.setRole(role);

        // บันทึก User ในฐานข้อมูล
        return userRepository.save(userReturn);
    }

}
