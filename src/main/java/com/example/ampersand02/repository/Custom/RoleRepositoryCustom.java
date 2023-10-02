package com.example.ampersand02.repository.Custom;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RoleRepositoryCustom {
    @Autowired
    private EntityManager r;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    StringBuilder sqlStatement = new StringBuilder();

}
