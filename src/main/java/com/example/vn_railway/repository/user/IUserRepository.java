package com.example.vn_railway.repository.user;

import com.example.vn_railway.model.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IUserRepository extends JpaRepository<Users,Long> {
    @Query(value = " select u.id\n" +
            "from users u \n" +
            "join app_user au on u.app_user_id = au.id\n" +
            "where au.user_name = :userName ",nativeQuery = true)
    Long getUserIdByUserName(@Param("userName")String userName);
}
