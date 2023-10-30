package com.example.vn_railway.config.sercurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private UserDetailsService jwtUserDetailService;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    /**
     * method: configureGlobal
     * Creater: NhatNHH
     * Date: 15-09-2023
     * param: AuthenticationManagerBuilder auth
     * return: void
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * method: authenticationManagerBean
     * Creater: NhatNHH
     * Date: 15-09-2023
     * return: AuthenticationManager
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();

    }

    /**
     * method: passwordEncoder
     * Creater: NhatNHH
     * Date: 15-09-2023
     * return: PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * method: configure
     * Creater: NhatNHH
     * Date: 15-09-2023
     * return: void
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable().cors().and()
                .authorizeRequests()
                .antMatchers(
                        //All role
                        "/api/user/**",
                        "/ws/**"
                ).permitAll()
                .antMatchers(
                "/api/payment/**",
                        "/api/train/**",
                        "/seat/**",
                        "/api/booking/**",
                        "/api/distance/**"


                ).hasAnyAuthority("ROLE_ADMIN","ROLE_CUSTOMER")
                .antMatchers(

                        //Authen Role admin and manager


                ).hasAnyAuthority("ROLE_ADMIN")

                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and().
                sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

//        httpSecurity
//                .authorizeRequests()
//                .anyRequest().permitAll()
//                .and()
//                .csrf().disable().cors();
    }

}
