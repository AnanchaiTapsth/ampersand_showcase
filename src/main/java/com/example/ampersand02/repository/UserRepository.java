package com.example.ampersand02.repository;

import com.example.ampersand02.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
