package org.example.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Map;
import java.util.stream.Stream;

@Configuration
public class SecurityConfig {
    public static void main(String[] args) {

        ObjectMapper objectMapper = new ObjectMapper();
        record Request(String url,String body){}
        Stream.of(Map.of("url","/contacts",
                "body","body"))

                //tomcat: to java object
                .map(req-> objectMapper.convertValue(req,Request.class))
                //log , etc
                .peek(request -> System.out.println("recived"+request))
                //spring security
                .filter(request -> !request.url.equals("admin-dashboard"))
                //call another controller
                .map(request -> request.body)
                .forEach(System.out::println);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())  // CORS configuration (you can customize this)
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v1/**").permitAll()  // Permit all requests to API
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()  // Corrected path for Swagger UI
                        .anyRequest().authenticated()  // All other requests must be authenticated
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // Stateless session management
                )
                .build();
    }

}
