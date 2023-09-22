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
    public List<Object> getRoleAndPermission(Long roleId) {
        LOGGER.info("[START] getRoleAndPermission In RoleRepositoryCustom roleId == : {}", roleId);
        List<Object> result = new ArrayList<>();

        sqlStatement.append(
                " SELECT r.role_id , r.role_name , p.permission_id , p.permission_desc , p.permission_name \n");
        sqlStatement.append(
                " FROM role r JOIN permission p ON r.permission = p.permission_id \n");
        sqlStatement.append(" WHERE 1 = 1 \n");

        if (!roleId.toString().equalsIgnoreCase(null) && !roleId.toString().equalsIgnoreCase("")) {
            sqlStatement.append(" AND r.role_id = :roleId \n");
        }
        LOGGER.info("sql :{}", sqlStatement);
        Query queryModule = r.createNativeQuery(sqlStatement.toString());
        queryModule.setParameter("roleId", roleId);

        List<Object[]> listfromQuery = queryModule.getResultList();

        LOGGER.info("getRoleAndPermission query size {}", listfromQuery.size());

        for (Object[] o : listfromQuery) {
            Map<String, Object> mapResult = new HashMap();

            mapResult.put("roleId", o[0]);
            mapResult.put("roleName", o[1]);
            mapResult.put("PermissionId", o[2]);
            mapResult.put("permissionName", o[3]);
            mapResult.put("permission_desc", o[4]);
            result.add(mapResult);
        }
        LOGGER.info("resultGetRoleAndPermission ================ : {} ", result);
        LOGGER.info("[END]getRoleAndPermission result : {} ", result.size());
        return result;
    }
}
