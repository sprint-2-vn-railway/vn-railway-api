package com.example.vn_railway.service.user.impl;

import com.example.vn_railway.dto.user.IAppRoleDto;
import com.example.vn_railway.repository.user.IAppRoleRepository;
import com.example.vn_railway.service.user.IAppRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppRoleService implements IAppRoleService {
    @Autowired
    private IAppRoleRepository appRoleRepository;
    @Override
    public List<IAppRoleDto> findListRoleByUserName(String userName) {
        return appRoleRepository.findRoleListByUserName(userName);
    }
}
