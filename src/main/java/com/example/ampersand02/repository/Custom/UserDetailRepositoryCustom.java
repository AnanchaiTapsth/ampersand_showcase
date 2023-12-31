package com.example.ampersand02.repository.Custom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDetailRepositoryCustom {
    @Autowired
    private EntityManager us;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    StringBuilder sqlStatement = new StringBuilder();

    public  List<Object> findUserByFnOrLn(String firstName , String lastName) {
        if(firstName.isEmpty() && lastName.isEmpty()){
            firstName = "";
            lastName = "";
        }
        else if(firstName.isEmpty()){
            firstName = " ";
        }
        else if(lastName.isEmpty()) {
            lastName = " ";
        }

        LOGGER.info("[START] findUserByFnOrLn In UserRepositoryCustom  firstName , lastName == : {} {}", firstName ,lastName );
        List<Object> result = new ArrayList<>();
        sqlStatement.append(
                " SELECT id , first_name , last_name , password , username , role \n");
        sqlStatement.append(
                " FROM  user_detail");
        sqlStatement.append(" WHERE 1 = 1 \n");

        if (!firstName.equalsIgnoreCase("") && !lastName.equalsIgnoreCase("")) {
            sqlStatement.append(" AND (first_name like :firstName OR last_name like :lastName) \n");
        }
        LOGGER.info("sql :{}", sqlStatement);
        Query query = us.createNativeQuery(sqlStatement.toString());
        if (!firstName.equalsIgnoreCase("")) {
            query.setParameter("firstName", "%" + firstName + "%");
        }
        if(!lastName.equalsIgnoreCase("")) {
            query.setParameter("lastName", "%" + lastName + "%");
        }
        List<Object[]> listfromQuery = query.getResultList();

        LOGGER.info("findUserByFnOrLn query size {}", listfromQuery.size());
        for (Object[] o : listfromQuery) {
            Map<String, Object> mapResult = new HashMap();
            mapResult.put("user_id", o[0]);
            mapResult.put("first_name", o[1]);
            mapResult.put("last_name", o[2]);
            mapResult.put("password", o[3]);
            mapResult.put("username", o[4]);
            mapResult.put("role_id", o[5]);
            result.add(mapResult);
        }
        LOGGER.info("[END]findUserByFnOrLn result : {} ", result.size());
        return result;
    }

}

