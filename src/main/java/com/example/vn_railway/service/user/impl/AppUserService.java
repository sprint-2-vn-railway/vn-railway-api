package com.example.vn_railway.service.user.impl;

import com.example.vn_railway.dto.user.IAppRoleDto;
import com.example.vn_railway.dto.user.IAppUserDto;
import com.example.vn_railway.dto.user.JwtResponseUserDetails;
import com.example.vn_railway.model.user.AppUser;
import com.example.vn_railway.model.user.UserRole;
import com.example.vn_railway.repository.user.IAppUserRepository;
import com.example.vn_railway.service.user.IAppRoleService;
import com.example.vn_railway.service.user.IAppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppUserService implements IAppUserService {
    @Autowired
    private IAppUserRepository appUserRepository;
    @Autowired
    private IAppRoleService appRoleService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        IAppUserDto appUserDto = appUserRepository.findByUserName(username);
        if (appUserDto == null) {
            throw new UsernameNotFoundException("User name or password is wrong");
        }
        List<IAppRoleDto> roleDtoList = appRoleService.findListRoleByUserName(username);
        List<GrantedAuthority> grantList = new ArrayList<>();
        for (IAppRoleDto role : roleDtoList) {
            grantList.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return new JwtResponseUserDetails(
                appUserDto.getUserName(),
                appUserDto.getPassword(),
                grantList);
    }
}
