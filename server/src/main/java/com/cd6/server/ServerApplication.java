package com.cd6.server;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@SpringBootApplication
@RestController
public class ServerApplication{

    @GetMapping("/user") //index.html에 user 정보를 넘겨줌
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal){
        return Collections.singletonMap("name", principal.getAttribute("name"));
    }

    @GetMapping("/error")
    public String error(HttpServletRequest request) {
        String message = (String) request.getSession().getAttribute("error.message");
        request.getSession().removeAttribute("error.message");
        return message;
    }



    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Configuration
    public static class SecurityConfig{

        private AuthenticationEntryPointFailureHandler handler;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests(authorize -> authorize
                            .requestMatchers("/", "/error", "/webjars/**").permitAll()
                            .anyRequest().authenticated()
                    )
                    .oauth2Login(Customizer.withDefaults())
                    .logout(l -> l
                            .logoutSuccessUrl("/").permitAll()
                    )
                    .csrf(c -> c
                            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    )
                    .oauth2Login(o -> o
                            .failureHandler((request, response, exception) -> {
                                request.getSession().setAttribute("error.message", exception.getMessage());
                                handler.onAuthenticationFailure(request, response, exception);
                            })
                    );


            return http.build();
        }
    }

}
