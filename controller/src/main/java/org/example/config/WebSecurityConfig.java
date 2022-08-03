package org.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String OPERATOR_URL = "/operator";
    private static final String OPERATOR_PAGE_NUMBER = "/operator/{pageNumber}";
    private static final String ADMIN = "ADMIN";
    private static final String OPERATOR = "OPERATOR";
    private static final String ADMIN1 = "/admin/*";
    private static final String OPERATOR1 = "/operator/*";
    private static final String CONSUMER = "CONSUMER";
    private static final String CONSUMER1 = "/consumer/*";
    private static final String FOR_ALL = "/forAll";

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(OPERATOR_URL, OPERATOR_PAGE_NUMBER).hasAnyAuthority(ADMIN, OPERATOR)
                .antMatchers(ADMIN1).hasAuthority(ADMIN)
                .antMatchers(OPERATOR1).hasAuthority(OPERATOR)
                .antMatchers(CONSUMER1).hasAuthority(CONSUMER)
                .antMatchers(FOR_ALL).permitAll()
                .and()
                .formLogin().permitAll()
                .defaultSuccessUrl("/", true)
                .and()
                .logout()
                .logoutUrl("/signOut")
                .permitAll()
                .logoutSuccessUrl("/");
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }


}
