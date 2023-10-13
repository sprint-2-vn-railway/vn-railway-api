package com.example.vn_railway.repository.user;

import com.example.vn_railway.dto.user.IAppUserDto;
import com.example.vn_railway.model.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IAppUserRepository extends JpaRepository<AppUser,Long> {
    @Query(value = " select au.user_name as userName, au.`password` as `password` \n" +
            "from app_user au\n" +
            "where au.user_name = :userName \n ",nativeQuery = true)
    IAppUserDto findByUserName(@Param("userName")String userName);
}
