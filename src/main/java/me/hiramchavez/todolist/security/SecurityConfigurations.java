package me.hiramchavez.todolist.security;

import lombok.RequiredArgsConstructor;
import me.hiramchavez.todolist.model.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigurations {

    private final SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
//          .cors(Customizer.withDefaults())
          .csrf(AbstractHttpConfigurer::disable)
          .sessionManagement( sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          .authorizeHttpRequests( auth ->
            auth
              .requestMatchers(HttpMethod.OPTIONS)
                .permitAll()
              .requestMatchers(HttpMethod.GET, "/users/me")
                .hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
              .requestMatchers(HttpMethod.POST, "/users/auth")
                .permitAll()
              .requestMatchers(HttpMethod.GET, "/users/{id}")
                .hasAuthority(Role.ADMIN.name())
              .requestMatchers(HttpMethod.GET, "/api-docs/**", "api-docs.yaml")
                .permitAll()
              .requestMatchers(HttpMethod.GET, "/swagger-ui-custom.html", "/swagger-ui/**", "/swagger-ui/")
                .permitAll()
              .requestMatchers(HttpMethod.GET, "/users")
                .hasAnyAuthority(Role.ADMIN.name(), Role.USER.name())
              .requestMatchers(HttpMethod.POST, "/users")
                .permitAll()
              .anyRequest()
                .authenticated()
          )
          .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
          .build();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
