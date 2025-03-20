package br.edu.ifpb.pweb2.flashg.infra.security;

import br.edu.ifpb.pweb2.flashg.infra.handler.CustomAuthenticationFailureHandler;
import br.edu.ifpb.pweb2.flashg.infra.handler.CustomAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Habilita segurança baseada em métodos (@PreAuthorize, @PostAuthorize, etc.)
public class SecurityConfigurations {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http    .csrf().disable()
                .authorizeHttpRequests(authorize -> authorize
                        // Rotas públicas (acesso sem autenticação)
                        .requestMatchers(
                                "/css/**",
                                "/js/**",
                                "/libs/**",
                                "/assets/**",
                                "/auth/**"
                        ).permitAll()

                        // Rotas específicas para ROLE ADMIN
                        .requestMatchers(
                                "/showAllPhotographers",
                                "/blockAction",
                                "/blockActionComment"
                        ).hasRole("ADMIN")

                        // Todas as outras rotas exigem pelo menos que o usuário seja autenticado
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth/signin")
                        .loginProcessingUrl("/auth/login")
                        .usernameParameter("email")
                        .successHandler(authenticationSuccessHandler())
                        .failureHandler(authenticationFailureHandler())
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/auth/signin?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return new CustomAuthenticationFailureHandler();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
