package com.example.locker.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final TokenService tokenService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable().and()
                .cors().and()
                .csrf().disable()
                .authorizeHttpRequests((authz) -> {
                    // Endpoints públicos
                    authz.antMatchers("/auth").permitAll();

                    // Regras de Leitura (GET)
                    authz.antMatchers(HttpMethod.GET, "/api/v1/localizacoes/**", "/api/v1/armarios/**").hasAnyRole("USUARIO", "ADMIN");

                    // Regras de Usuário Autenticado
                    authz.antMatchers(HttpMethod.PUT, "/api/v1/usuarios/trocar-senha").authenticated();

                    // Regras de Admin
                    authz.antMatchers(HttpMethod.POST, "/auth/create-user").hasRole("ADMIN");
                    authz.antMatchers("/api/v1/usuarios/**").hasRole("ADMIN");
                    authz.antMatchers("/api/v1/relatorios/**").hasRole("ADMIN");
                    authz.antMatchers("/api/v1/clientes/**").hasRole("ADMIN");
                    authz.antMatchers(HttpMethod.POST, "/api/v1/localizacoes/**", "/api/v1/armarios/**").hasRole("ADMIN");
                    authz.antMatchers(HttpMethod.PUT, "/api/v1/localizacoes/**", "/api/v1/armarios/**").hasRole("ADMIN");
                    authz.antMatchers(HttpMethod.PATCH, "/api/v1/armarios/**").hasRole("ADMIN");
                    authz.antMatchers(HttpMethod.DELETE, "/api/v1/localizacoes/**", "/api/v1/armarios/**").hasRole("ADMIN");

                    // Qualquer outra requisição precisa estar autenticada
                    authz.anyRequest().authenticated();
                });
        http.addFilterBefore(new TokenAuthenticationFilter(tokenService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers
                ("/api-docs/**", "/swagger-ui/**", "/swagger-resources/**");
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("*")
                        .exposedHeaders("Authorization");
            }
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}