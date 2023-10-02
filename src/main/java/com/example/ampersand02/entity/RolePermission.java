package com.example.ampersand02.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import java.util.List;

@Data

public class RolePermission {

    Long roleId;

    List<Permission> permissionId;
}
