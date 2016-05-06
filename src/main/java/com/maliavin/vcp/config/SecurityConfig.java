package com.maliavin.vcp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import com.maliavin.vcp.Constants;
import com.maliavin.vcp.security.RestAuthenticationFailureHandler;
import com.maliavin.vcp.security.RestAuthenticationSuccessHandler;
import com.maliavin.vcp.service.impl.AuthentificationService;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthentificationService authentificationService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        .antMatchers("/my-account/**").hasAuthority(Constants.Role.USER.name())
        .anyRequest().permitAll();

        http.formLogin()
        .successHandler(new RestAuthenticationSuccessHandler())
        .failureHandler(new RestAuthenticationFailureHandler()).loginProcessingUrl("/login")
        .usernameParameter("name")
        .passwordParameter("password");

        http.logout()
        .logoutUrl("/logout")
        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
        .invalidateHttpSession(true)
        .deleteCookies("JSESSIONID");

        http.exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
        http.rememberMe().disable();
        http.csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authentificationService).passwordEncoder(passwordEncoder());
    }
}
