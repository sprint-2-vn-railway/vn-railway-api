package com.example.vn_railway.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.Column;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDto implements Validator {
    private Long id;
    private String userName;
    private String password;
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
