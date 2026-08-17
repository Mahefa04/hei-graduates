package school.hei.graduates.conf;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConf {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        auth ->
                                auth
                                        .requestMatchers(
                                                "/ping",
                                                "/swagger-ui/**",
                                                "/v3/api-docs/**")
                                        .permitAll()

                                        .requestMatchers("/users/**")
                                        .hasRole("ADMIN")

                                        .anyRequest()
                                        .authenticated())
                .httpBasic(basic -> {})
                .build();
    }
}