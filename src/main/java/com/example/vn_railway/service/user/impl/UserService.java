package com.example.vn_railway.service.user.impl;

import com.example.vn_railway.repository.user.IUserRepository;
import com.example.vn_railway.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    @Override
    public Long getUserIdByUserName(String userName) {
        return userRepository.getUserIdByUserName(userName);
    }
}
