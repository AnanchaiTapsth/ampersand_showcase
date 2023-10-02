package com.example.ampersand02.service;

import com.example.ampersand02.entity.UserDetail;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserDetailService {

     ResponseEntity <Object> createUserProfile(UserDetail user);

     ResponseEntity <Object> getUserById(UserDetail existingUser);
     List<Object> findUserByFnOrLn(String firstName , String lastName);
}
