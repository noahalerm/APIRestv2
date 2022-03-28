package com.example.apirestv2.security;

import com.example.apirestv2.model.services.MyUserDetailsService;
import com.example.apirestv2.security.jwt.JWTAuthorizationFilter;
import com.example.apirestv2.security.jwt.MyAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    //ATTRIBUTES
    private final MyAuthenticationEntryPoint myAuthenticationEntryPoint;
    private final MyUserDetailsService myUserDetailsService;
    private final PasswordEncoder encoder;
    private final JWTAuthorizationFilter jwtAuthorizationFilter;

    //TESTING METHOD
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().anyRequest();
    }

    //"inMemoryAuthentication()" TESTING METHOD
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .passwordEncoder(xifrat)
//                .withUser("Montse")
//                .password(xifrat.encode("secret"))
//                .roles("ADMIN");
//    }

    //METHODS
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(encoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
////                .cors()
////                .and()
//                .httpBasic()
//                .and()
////                .exceptionHandling().authenticationEntryPoint(myAuthenticationEntryPoint)
////                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//
//
//                //H2-CONSOLE
//                .authorizeRequests().antMatchers("/").permitAll().and()
//                .authorizeRequests().antMatchers("/h2-console/**").permitAll()
//                .and()
//                .csrf().disable()
//                .headers().frameOptions().disable()
//                .and()
//
//
//                .authorizeRequests()
//                .antMatchers(HttpMethod.POST,"/login").permitAll()
////                .antMatchers(HttpMethod.GET,"/login").permitAll()
////                .antMatchers(HttpMethod.GET, "/me/**").hasRole("ADMIN") //FORBIDDEN TESTS
//                .antMatchers(HttpMethod.GET, "/login/**","/users/**", "/series/**").hasRole("USER")
////                .antMatchers(HttpMethod.POST, "/login/**", "/series/**").hasRole("USER")
////                .antMatchers(HttpMethod.PUT, "/series/**").hasRole("USER")
////                .antMatchers(HttpMethod.DELETE, "/series/**").hasRole("ADMIN")
////                .antMatchers(HttpMethod.POST, "/series/**").hasAnyRole("USER", "ADMIN")
//                .anyRequest().authenticated().and()
//                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
//    }

//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
//        return source;
//    }
}
