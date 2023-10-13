package com.example.vn_railway.repository.user;

import com.example.vn_railway.dto.user.IAppRoleDto;
import com.example.vn_railway.model.user.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IAppRoleRepository extends JpaRepository<AppRole,Long> {
    @Query(value = "  select ar.`name` as roleName \n" +
            "from app_user au\n" +
            "join user_role ur on ur.app_user_id = au.id\n" +
            "join app_role ar on ur.app_user_id = ar.id\n" +
            "where au.user_name = :userName  ",nativeQuery = true)
    List<IAppRoleDto> findRoleListByUserName(@Param("userName") String userName);
}
