package com.example.ampersand02.controller;
import com.example.ampersand02.domain.User;
import com.example.ampersand02.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.ampersand02.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import flexjson.JSONSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/user")
public class UserController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper; // อินเจ็คต์ของ ObjectMapper

    // URS02: ผู้ดูแลระบบผู้ใช้สามารถสร้างโปรไฟล์ผู้ใช้ได้
    @PostMapping("createUser")
    public User createUser(@RequestBody User user) {
        LOGGER.info("User JSON: {}", user);
        return userRepository.save(user);
    }

    // URS03: ผู้ดูแลระบบผู้ใช้สามารถแก้ไขโปรไฟล์ผู้ใช้โดย user_id
    @PutMapping("updateUser/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User existingUser = userRepository.findById(id).orElse(null);

        if (existingUser == null) {
            return null;
        }

        LOGGER.info("updateUser JSON: {}", existingUser);
        // อัปเดตข้อมูลผู้ใช้
        //   existingUser.setUsername(updatedUser.getUsername());
        //   existingUser.setPassword(updatedUser.getPassword());
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        // อัปเดต Role ของผู้ใช้
        // existingUser.setRole(updatedUser.getRole()); // อัปเดต Role ตาม ID ที่ส่งมาใน JSON

        // บันทึกการอัปเดต
        return userRepository.save(existingUser);
    }

    // URS04: ผู้ดูแลระบบผู้ใช้สามารถลบโปรไฟล์ผู้ใช้โดย user_id
    @DeleteMapping("deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        User existingUser = userRepository.findById(id).orElse(null);

        if (existingUser == null) {
            return ResponseEntity.notFound().build();
        }

        userRepository.delete(existingUser);
        return ResponseEntity.ok("User deleted successfully");
    }


    // URS05: ผู้ดูแลระบบผู้ใช้สามารถเรียกดูข้อมูลผู้ใช้ตาม user_id
    @GetMapping("findIdUser/{id}")
    public User getUserById(@PathVariable Long id){
        return userRepository.findById(id).orElse(null);
    }

    // URS06: ผู้ดูแลระบบสามารถเรียกดูข้อมูลผู้ใช้ทั้งหมดตาม first_name, Last_name
    @GetMapping("findUserByFnOrLn")
    public ResponseEntity<String> findUserByFnOrLn(@RequestParam(value = "keyword", required = true) String keyword){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
        headers.add("responseCode", "0");
        LOGGER.info("[START][findUserByFnOrLn][01] keyword : {} ", keyword);

        List<Object> User = userService.findUserByFnOrLn(keyword);
        LOGGER.info("[END][findUserByFnOrLn][01] User : {} ", User);
        return new ResponseEntity<String>(new JSONSerializer().deepSerialize(User), headers, HttpStatus.OK);
    }

//    @GetMapping("/findAll")
//    public List<User> getUsers() {
//        LOGGER.info("[START][findAllUser]");
//
//        List<User> users = userRepository.findAll();
//
//        LOGGER.info("[END][findAllUser] Users Count: {}", users.size());
//        LOGGER.info("[TEST][findAllUser] Users == : {}", users);
//
//        for (User user : users) {
//            try {
//                String userJson = objectMapper.writeValueAsString(user); // แปลง User เป็น JSON
//                LOGGER.info("User JSON: {}", userJson); // แสดง JSON ของ User
//            } catch (Exception e) {
//                LOGGER.error("Error converting User to JSON: {}", e.getMessage());
//            }
//        }
//
//        return users;
//    }
//




}

