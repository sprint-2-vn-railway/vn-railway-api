package com.example.vn_railway.service.user;

import com.example.vn_railway.dto.user.IAppRoleDto;

import java.util.List;

public interface IAppRoleService {
    List<IAppRoleDto> findListRoleByUserName(String userName);
}
