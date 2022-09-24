package com.skeleton.skeletons.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           AuthenticationManager authenticationManager) throws Exception {
        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().httpBasic().and().authorizeHttpRequests(
                        (authz) -> authz.antMatchers("/ping").permitAll().anyRequest().authenticated())
                .authenticationManager(authenticationManager);

        return http.build();
    }

    @Bean
    public AuthenticationManager ldapAuthenticationManager(LdapTemplate ldapTemplate) {
        LdapBindAuthenticationManagerFactory factory = new LdapBindAuthenticationManagerFactory(
                (BaseLdapPathContextSource) ldapTemplate.getContextSource());
        factory.setUserDnPatterns("uid={0},ou=users,ou=ldap_connector_user");

        return factory.createAuthenticationManager();
    }
}
