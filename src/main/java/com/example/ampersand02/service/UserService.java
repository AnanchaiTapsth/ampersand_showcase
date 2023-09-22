package com.example.ampersand02.service;

import com.example.ampersand02.domain.Role;
import com.example.ampersand02.domain.User;
import com.example.ampersand02.repository.RoleRepository;
import com.example.ampersand02.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface UserService {

    public User createUserProfile(User user);

    List<Object> findUserByFnOrLn(String firstName ,String lastName);
    // คุณสามารถกำหนดเมธอดอื่น ๆ ที่เกี่ยวข้องกับการจัดการผู้ใช้งานได้ตามความต้องการ
}
