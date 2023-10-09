package com.example.vn_railway.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "app_user_id", referencedColumnName = "id")
    private AppUser appUser;
    @ManyToOne()
    @JoinColumn(name = "app_role_id", referencedColumnName = "id")
    private AppRole appRole;
}
