package com.example.vn_railway.repository.user;

import com.example.vn_railway.model.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IAppUserRepository extends JpaRepository<AppUser,Long> {
    @Query(value = " select * from app_user au where au.name = :userName ",nativeQuery = true)
    AppUser findByUserName(@Param("userName")String userName);
}
