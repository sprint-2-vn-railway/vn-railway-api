package com.example.vn_railway.service.user.impl;

import com.example.vn_railway.model.user.AppUser;
import com.example.vn_railway.repository.user.IAppUserRepository;
import com.example.vn_railway.service.user.IAppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserService implements IAppUserService {
    private IAppUserRepository appUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUserName(username);
        return null;
    }
}
