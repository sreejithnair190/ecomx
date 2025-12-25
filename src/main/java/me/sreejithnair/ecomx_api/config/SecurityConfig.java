package me.sreejithnair.ecomx_api.config;

import me.sreejithnair.ecomx_api.auth.filter.AuthFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import static me.sreejithnair.ecomx_api.common.constant.AppConstant.API_VERSION_V1;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthFilter authFilter;
    private final HandlerExceptionResolver exceptionResolver;

    public SecurityConfig(AuthFilter authFilter,
                          @Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
        this.authFilter = authFilter;
        this.exceptionResolver = exceptionResolver;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(API_VERSION_V1 + "/auth/**").permitAll()
                        .requestMatchers(API_VERSION_V1 + "/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) ->
                                exceptionResolver.resolveException(request, response, null, authException))
                        .accessDeniedHandler((request, response, accessDeniedException) ->
                                exceptionResolver.resolveException(request, response, null, accessDeniedException))
                )
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
