package com.smoothstack.utopia.config;

import com.smoothstack.utopia.config.EnvVariableConfig;
import com.smoothstack.utopia.filter.*;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final EnvVariableConfig envConfig;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers(
                "/api/airplanes/**",
                "/api/airplane-types/**",
                "/api/airports/**",
                "/api/flights/**",
                "/api/routes/**"
            ).hasAnyAuthority("Admin")
            .anyRequest().authenticated()
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(new AuthorizationFilter(envConfig), UsernamePasswordAuthenticationFilter.class);
    }
}

