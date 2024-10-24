package com.restaurant.apis.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "UserRoles")
public class UserRoles {
    @Id
    @Column(name = "UserRolesId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int UserRolesId;

    @ManyToOne
    @JoinColumn(name = "UserId" , nullable = false)
    private User UserId;

    @ManyToOne
    @JoinColumn(name = "RoleId" , nullable = false)
    private Roles RoleId;
}
