package com.restaurant.apis.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Roles")
public class Roles{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="RoleId", nullable = false)
    private int RoleId;

    @Column(name = "RoleName", nullable = false)
    private String RoleName;
}