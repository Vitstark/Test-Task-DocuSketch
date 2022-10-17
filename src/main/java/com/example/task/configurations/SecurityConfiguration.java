package com.example.task.configurations;

import com.example.task.models.User;
import com.example.task.repositories.UsersRepository;
import com.example.task.security.UserDetailsImpl;
import com.example.task.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/users/*/newpost", "/users/*/*/newcomment")
                .authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/auth/process_login")
                .defaultSuccessUrl("/me", true)
                .failureUrl("/auth/login?error")
                .and()
                .logout().logoutUrl("/auth/logout")
                .logoutSuccessUrl("/");

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserService service) {
        return email -> {
            Optional<User> user = service.findByEmail(email);

            if (user.isEmpty()) {
                throw new UsernameNotFoundException(String.format("User with email %s not found", email));
            }

            return new UserDetailsImpl(user.get());
        };
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}