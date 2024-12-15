package com.example.kristp.security.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
class SecurityConfig{
    private final CustomAuthenticationFilter customAuthenticationFilter;

    public SecurityConfig(CustomAuthenticationFilter customAuthenticationFilter) {
        this.customAuthenticationFilter = customAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET,"/", "/user/trang-chu", "/dang-nhap", "/dang-ki", "/resources/**", "/logout",
                                "/css/**", "/js/**", "/uploadImage/**", "/error", "/favicon.ico", "/uploadImage/**", "/quan-ly/huy-don-hang").permitAll()
                        .requestMatchers(HttpMethod.POST, "/quan-ly/huy-don-hang").permitAll()
                        .requestMatchers(HttpMethod.POST, "/dang-nhap-khach-hang", "/dang-ki-tai-khoan").permitAll()
                        // Vai trò USER có thể truy cập vào các đường dẫn /user/**
                        .requestMatchers(HttpMethod.GET, "/user/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/user/**").hasRole("USER")

                        // Vai trò ADMIN hoặc STAFF có thể truy cập vào các đường dẫn /quan-ly/**
                        .requestMatchers(HttpMethod.GET, "/quan-ly/**").hasAnyRole("ADMIN", "STAFF")
                        .requestMatchers(HttpMethod.POST, "/quan-ly/**").hasAnyRole("ADMIN", "STAFF")

                        // Vai trò ADMIN có thể truy cập vào các đường dẫn /quan-ly-admin/**
                        .requestMatchers(HttpMethod.GET, "/quan-ly-admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/quan-ly-admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/dang-nhap")
                        .defaultSuccessUrl("/user/trang-chu", true)
                        .permitAll()
                )
                .logout(logout -> logout.permitAll())
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            // Khi lỗi 403, chuyển hướng về trang đăng nhập
                            response.sendRedirect("/dang-nhap");
                        })
                )
                .csrf(csrf -> csrf.disable());
        return http.build();
    }
}
