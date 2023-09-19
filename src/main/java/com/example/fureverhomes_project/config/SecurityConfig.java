package com.example.fureverhomes_project.config;

//import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;

//스프링 시큐리티 환경 설정을 위한 클래스
@Configuration
//@EnableWebSecurity
public class SecurityConfig{

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        // 정적 자원에 대해서 Security를 적용하지 않음으로 설정
//        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//    }

    //비밀번호를 암호화하기 위한 BCrypt 인코딩을 통하여 비밀번호에 대한 암호화를 수행
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
//
//        security.csrf().disable()
//                .headers().frameOptions().disable();
//
//        security.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//        security.authorizeHttpRequests()
////                .antMatchers("스웨거나 docs 관련 url","css, js 관련 url").permitAll()
//                .antMatchers("/main").permitAll()
//                .antMatchers("/**").permitAll()
//                .anyRequest().authenticated();
//
//        security.formLogin().disable();
//
//        security.httpBasic().disable();
//
//        return security.build();
//    }
}
