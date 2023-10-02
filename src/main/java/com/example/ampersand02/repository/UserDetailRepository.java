package com.example.ampersand02.repository;

import com.example.ampersand02.entity.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
    boolean existsByUsername(String username);
}
