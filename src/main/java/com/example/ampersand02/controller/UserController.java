package com.example.ampersand02.controller;
import com.example.ampersand02.domain.User;
import com.example.ampersand02.service.UserService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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

    @PostMapping("/create")
    public ResponseEntity<String> createUserProfile(@RequestBody User userProfileRequest) {
        LOGGER.info("userProfileRequest ===  : {} " ,userProfileRequest );
        User createdUser = userService.createUserProfile(userProfileRequest);
        return ResponseEntity.ok("createUserProfile " + createdUser.getUsername() + " successfully");
    }


    // URS03: ผู้ดูแลระบบผู้ใช้สามารถแก้ไขโปรไฟล์ผู้ใช้โดย user_id
    @PutMapping("updateUser/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User existingUser = userRepository.findById(id).orElse(null);

        if (existingUser == null) {

            return ResponseEntity.ofNullable("This User ID does not exist.");
        }
        else {

            LOGGER.info("updateUser JSON: {}", existingUser);
            // อัปเดตข้อมูลผู้ใช้
            //   existingUser.setUsername(updatedUser.getUsername());
            //   existingUser.setPassword(updatedUser.getPassword());
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setLastName(updatedUser.getLastName());
            // อัปเดต Role ของผู้ใช้
             existingUser.setRole(updatedUser.getRole()); // อัปเดต Role ตาม ID ที่ส่งมาใน JSON

            userRepository.save(existingUser);

            return ResponseEntity.ok("User Update successfully.");
        }
    }

    // URS04: ผู้ดูแลระบบผู้ใช้สามารถลบโปรไฟล์ผู้ใช้โดย user_id
    @DeleteMapping("deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        User existingUser = userRepository.findById(id).orElse(null);

        if (existingUser == null) {
            return ResponseEntity.ofNullable("This User ID does not exist.");
        }

        userRepository.delete(existingUser);
        return ResponseEntity.ok("User deleted successfully");
    }


    // URS05: ผู้ดูแลระบบผู้ใช้สามารถเรียกดูข้อมูลผู้ใช้ตาม user_id
    @GetMapping("findUserById/{id}")
    public ResponseEntity<String> getUserById(@PathVariable Long id){
        HttpHeaders headers = new HttpHeaders();
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null) {
            return ResponseEntity.ofNullable("This User ID does not exist.");
        }
        return new ResponseEntity<String>(new JSONSerializer()
                .include("userId")
                .include("username")
                .include("password")
                .include("firstName")
                .include("lastName")
                .include("role.roleId")
                .exclude("*")
                .deepSerialize(existingUser),headers, HttpStatus.OK);
    }

    // URS06: ผู้ดูแลระบบสามารถเรียกดูข้อมูลผู้ใช้ทั้งหมดตาม first_name, Last_name
    @GetMapping("findUserByFnOrLn")
    public ResponseEntity<String> findUserByFnOrLn(
            @RequestParam(value = "firstName") String firstName ,
            @RequestParam(value = "lastName") String lastName){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=utf-8");
        headers.add("responseCode", "0");
        if(firstName.isEmpty() && lastName.isEmpty()){
            firstName = "";
            lastName = "";
        }
        else if(firstName.isEmpty() && !lastName.isEmpty()){
            firstName = "ABCDE";
        }
        else {
            lastName = "ABCDE";
        }
        LOGGER.info("[START][findUserByFnOrLn][01] firstName , lastName : {} {} ", firstName , lastName);

        List<Object> User = userService.findUserByFnOrLn(firstName , lastName);
        LOGGER.info("[END][findUserByFnOrLn][01] User : {} ", User);
        return new ResponseEntity<String>(new JSONSerializer().deepSerialize(User), headers, HttpStatus.OK);
    }

    // URS07: ผู้ดูแลระบบผู้ใช้สามารถแก้ไขบทบาทของผู้ใช้ได้

    @PutMapping("updateRoleByUser/{id}")
    public ResponseEntity<String> updateRoleByUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser == null) {

            return ResponseEntity.ofNullable("This User ID does not exist.");
        }
        else {
            LOGGER.info("updateUser JSON: {}", existingUser);
            existingUser.setRole(updatedUser.getRole()); // อัปเดต Role ตาม ID ที่ส่งมาใน JSON

            userRepository.save(existingUser);

            return ResponseEntity.ok("Update new Role == "+ existingUser.getRole().getRoleId() + " successfully.");
        }
    }



}

