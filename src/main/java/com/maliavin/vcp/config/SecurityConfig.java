package com.maliavin.vcp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import com.maliavin.vcp.Constants;
import com.maliavin.vcp.security.AddPrincipalHeadersFilter;
import com.maliavin.vcp.security.RestAuthenticationFailureHandler;
import com.maliavin.vcp.security.RestAuthenticationSuccessHandler;
import com.maliavin.vcp.service.impl.AuthentificationService;

/**
 * Security configuration
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthentificationService authentificationService;
    
    @Autowired
    private PersistentTokenRepository persistentTokenRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        .antMatchers("/my-account/**").hasAuthority(Constants.Role.USER.name())
        .antMatchers("/admin/**").hasAuthority(Constants.Role.ADMIN.name())
        .anyRequest().permitAll().and().addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
        .addFilterAfter(new AddPrincipalHeadersFilter(), LogoutFilter.class);;

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
        http.rememberMe().rememberMeParameter("rememberMe").rememberMeCookieName("rememberMe")
                .tokenRepository(persistentTokenRepository);
        http.csrf().csrfTokenRepository(csrfTokenRepository());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authentificationService).passwordEncoder(passwordEncoder());
    }
    
    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
      }
}
