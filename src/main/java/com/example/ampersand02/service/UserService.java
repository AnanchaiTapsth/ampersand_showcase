package com.example.ampersand02.service;

import com.example.ampersand02.repository.UserRepository;

import java.util.List;

public interface UserService {
    UserRepository getUserRepository();

    List<Object> findUserByFnOrLn(String keyword);
    // คุณสามารถกำหนดเมธอดอื่น ๆ ที่เกี่ยวข้องกับการจัดการผู้ใช้งานได้ตามความต้องการ
}
