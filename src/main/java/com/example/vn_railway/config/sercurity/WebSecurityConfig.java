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
//        httpSecurity.csrf().disable().cors().and()
//                .authorizeRequests()
//                .antMatchers(
//                        //All role
//                        "/api/user/login-by-username/**",
//                        "/api/user/login-by-facebook/**",
//                        "/api/user/register-by-customer/**",
//                        "/api/user/logout/{userName}/**",
//                        "/api/home/**",
//                        "/api/home/search/**",
//                        "/api/home/favorite/**",
//                        "/api/user/get-id-app-user/{userName}",
//                        "/api/carts/get-details/**",
//                        "/api/kindOfMedicines"
//
//                ).permitAll()
//                .antMatchers(
//
//                        "/api/orders/list/**",
//                        "/api/orders/{id}/**",
//                        "/api/carts/add-from-home-details/**",
//                        "/api/carts/add-from-cart/**",
//                        "/api/carts/delete-all/**",
//                        "/api/carts/delete-cart/**",
//                        "/api/carts/getInforCustomer/**",
//                        "/api/carts/check-quantity/**",
//
//                        "/api/carts/get-all/**",
//                        "/api/carts/get-quantity-in-cart/**",
//                        "/api/carts/get-loyalty-point/**",
//                        "/api/carts/check-availability/**",
//                        "/payment/**",
//                        "/customers/api/online-customer/**",
//                        "/customers/user/{id}/**",
//                        "/customers/api/user/{id}/**",
//                        "/customers/api/update-new/{id}/**"
//
//                ).hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER", "ROLE_CUSTOMER", "ROLE_EMPLOYEE")
//                .antMatchers(
//                        "/api/carts/getMedicine/**",
//                        "/api/carts/getAllCartDetailsByUser/**",
//                        "/api/carts/getPrescriptionByName/**",
//                        "/api/carts/getPrescriptionBySymptoms/**",
//                        "/api/carts/getIndication/**",
//                        "/api/carts/getNameEmployee/**",
//                        "/api/carts/getOneMedicineByName/**",
//                        "/api/carts/delete-multi/**",
//                        "/prescription/**",
//                        "/prescription/create/**",
//                        "/prescription/{id}/**",
//                        "/prescription/delete/{id}/**",
//                        "/prescription/edit/**"
//
//
//                        )
//                .hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER", "ROLE_EMPLOYEE")
//                .antMatchers(
//                        //Authen Role admin and manager
//                        "/api/user/register-by-manager/**",
//                        "/api/invoice/**",
//                        "/api/invoice/result/**",
//                        "/api/invoice/delete/{id}/**",
//                        "/api/invoice/search/**",
//                        "/api/invoice/search/result/**",
//                        "/api/invoice/detail/{id}/**",
//                        "/api/invoice/create/**",
//                        "/api/invoice/{invoiceId}/**",
//                        "/api/invoice/edit/**",
//                        "/api/invoice/code/**",
//                        "/api/invoice-detail/{invoiceId}/**",
//
//                        "/api/kindOfMedicines/**",
//                        "/api/kindOfMedicines/kindOfMedicine/{id}/**",
//                        "/api/kindOfMedicines/delete/{id}/**",
//                        "/api/kindOfMedicines/delete-items/**",
//                        "/api/kindOfMedicines/create/**",
//                        "/api/kindOfMedicines/edit/**",
//                        "/api/kindOfMedicines/get/**",
//                        "/api/kindOfMedicines/get/**",
//
//                        "/api/medicine/code/create/**",
//                        "/api/medicine/{id}/**",
//                        "/api/medicine/**",
//                        "/api/medicine/get-medicine/**",
//                        "/api/medicine/search/**",
//                        "/api/medicine/get-list/**",
//                        "/api/medicine/get-medicine/{id}/**",
//                        "/api/medicine/get-unitDetail/{id}/**",
//                        "/api/medicine/get-list-for-invoice/**",
//
//                        "/customers/api/dto/create/**",
//                        "/customers/api/create/**",
//                        "/customers/api/{id}/**",
//                        "/customers/api/list/**",
//                        "/customers/api/delete/{id}/**",
//                        "/customers/api/update/{id}/**",
//
//                        "/api/supplier/**",
//                        "/api/supplier/delete/{id}/**",
//                        "/api/supplier/create-supplier/**",
//                        "/api/supplier/update-supplier/{id}/**",
//                        "/api/supplier/detail-supplier/{id}/**",
//                        "/api/supplier/get/{id}/**",
//                        "/api/supplier/get-detail/{id}/**",
//                        "/api/supplier/list/**",
//
//
//                        "/api/employees/create/**",
//                        "/api/employees/{id}/**",
//                        "/api/employees/update/{id}/**",
//                        "/api/employees/list/{page}/{limit}/{sort}/**",
//                        "/api/employees/list1/{page}/{limit}/{sort}/**",
//                        "/api/employees/delete-employee/**",
//                        "/api/employees/by-user/{username}/**",
//
//
//                        "/indication/{id}/**",
//                        "/indicationDto/{id}/**",
//                        "/indication/delete/{id}/**",
//                        "/indication/create/**",
//                        "/indication/edit/**",
//
//
//                        "/patient/**",
//
//                        "/api/report/general/**",
//                        "/api/report/chart/revenue/**",
//                        "/api/report/chart/profit/**",
//                        "/api/report/sum/**"
//
//                ).hasAnyAuthority("ROLE_ADMIN", "ROLE_MANAGER")
//
//                .anyRequest()
//                .authenticated()
//                .and()
//                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                .and().
//                sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .csrf().disable().cors();
    }

}
