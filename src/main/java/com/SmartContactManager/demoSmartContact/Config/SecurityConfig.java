package com.SmartContactManager.demoSmartContact.Config;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.SecurityFilterChain;

import com.SmartContactManager.demoSmartContact.services.imp.SecurityCustomUserDetail;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityCustomUserDetail userDetailService;

    @Autowired
    private OAuthAuthenticationSucessHandler handler;

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .authorizeHttpRequests(authorize -> {
                authorize.requestMatchers("/user/**").authenticated(); // Secure user-specific routes
                authorize.anyRequest().permitAll(); // Allow all other requests
            })
            .formLogin(formlogin -> {
                formlogin.loginPage("/login"); // Custom login page
                formlogin.loginProcessingUrl("/authenticate"); // URL to submit login form
                formlogin.defaultSuccessUrl("/user/profile", true); // Redirect after login
                formlogin.usernameParameter("email"); // Username parameter
                formlogin.passwordParameter("password"); // Password parameter
            })
            .logout(logout -> {
                logout.logoutUrl("/do-logout"); // Logout URL
                logout.logoutSuccessUrl("/login"); // Redirect after logout
                logout.invalidateHttpSession(true); // Invalidate session
                logout.deleteCookies("JSESSIONID"); // Delete cookies
            })
            .oauth2Login(oauth -> {
                oauth.loginPage("/login"); // Custom login page for OAuth2
                oauth.successHandler(handler); // OAuth2 login success handler
                
            });

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    
}