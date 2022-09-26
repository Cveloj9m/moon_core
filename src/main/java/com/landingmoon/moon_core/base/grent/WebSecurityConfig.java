package com.landingmoon.moon_core.base.grent;

import com.landingmoon.moon_core.base.grent.constant.GrentConst;
import com.landingmoon.moon_core.base.grent.filter.JsonAuthenticationFilter;
import com.landingmoon.moon_core.base.grent.filter.JwtOncePerRequestFilter;
import com.landingmoon.moon_core.base.grent.handler.CustomFailedHandler;
import com.landingmoon.moon_core.base.grent.handler.CustomLogoutHandler;
import com.landingmoon.moon_core.base.grent.handler.CustomSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

/**
 * @Author LandingMoon
 * @Date 2022/8/29 0:28
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    private final RedisTemplate redisTemplate;
    private final JwtOncePerRequestFilter jwtOncePerRequestFilter;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final CustomSuccessHandler customSuccessHandler;
    private final CustomFailedHandler customFailedHandler;
    private final CustomLogoutHandler customLogoutHandler;

    public WebSecurityConfig(RedisTemplate redisTemplate, CustomFailedHandler customFailedHandler, CustomLogoutHandler customLogoutHandler, CustomSuccessHandler customSuccessHandler, JwtOncePerRequestFilter jwtOncePerRequestFilter, AuthenticationConfiguration authenticationConfiguration) throws Exception {
        this.redisTemplate = redisTemplate;
        this.jwtOncePerRequestFilter = jwtOncePerRequestFilter;
        this.customSuccessHandler = customSuccessHandler;
        this.authenticationConfiguration = authenticationConfiguration;
        this.customLogoutHandler = customLogoutHandler;
        this.customFailedHandler = customFailedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
//        corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
        configurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return configurationSource;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource()).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .logout().logoutUrl("/user/logout").logoutSuccessHandler(customLogoutHandler).and()
                .authorizeRequests()
                .antMatchers(GrentConst.LOGIN_URL, "/captcha").anonymous()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtOncePerRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JsonAuthenticationFilter(redisTemplate, authenticationConfiguration.getAuthenticationManager()
                        , customSuccessHandler, customFailedHandler), JwtOncePerRequestFilter.class)
                .build();
//                .headers().frameOptions().sameOrigin().cacheControl() 不知道这行代码是干嘛的，以后可以研究一下
    }


}
