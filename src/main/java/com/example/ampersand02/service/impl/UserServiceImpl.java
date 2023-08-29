package com.example.ampersand02.service.impl;

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

    @Override
    public UserRepository getUserRepository() {
        return userRepository;
    }

    @Override
    public List<Object> findUserByFnOrLn(String keyword) {
        List<Object> listUser = userRepositoryCustom.findUserByFnOrLn(keyword);
        LOGGER.debug("listUser size : {}", listUser.size());
        LOGGER.debug("listUser ========== : {}", listUser);
        return listUser;

    }

}
