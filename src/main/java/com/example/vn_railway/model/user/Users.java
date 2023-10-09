package com.example.vn_railway.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "varchar(20)",unique = true)
    private String code;
    private String name;
    private String dateOfBirth;
    @Column(columnDefinition = "varchar(20)",unique = true)
    private String idCard;
    @Column(columnDefinition = "varchar(50)",unique = true)
    private String mail;
    @Column(columnDefinition = "varchar(15)",unique = true)
    private String phoneNumber;
    private Boolean deleted;
    @ManyToOne
    @JoinColumn(name = "app_user_id",referencedColumnName = "id")
    private AppUser appUser;
}
