package com.ssafy.BackEnd.config;

import com.ssafy.BackEnd.filter.JwtAuthenticationFilter;
import com.ssafy.BackEnd.service.jwt.JwtServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtServiceImpl jwtService;

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
        web.ignoring().antMatchers("/swagger-resources/**",
                "/swagger-ui.html", "/webjars/**", "/swagger/**", "/signin", "/signup", "/chat/**", "/testgo",
                "/verify/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("여기니?");
        http.cors().and().httpBasic().disable();
        //http.csrf().disable().formLogin().loginPage("/aaaaa"); // 페이지를 못가게 하는

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/team/**").permitAll() //.hasRole("USER")
                .antMatchers("/dashboard/**").permitAll()
                .antMatchers("/signin").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/profile").permitAll()
                .antMatchers("/chat/**").permitAll()
                .antMatchers("/verify/**").permitAll()
                .antMatchers("/certi/**").permitAll()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtService), UsernamePasswordAuthenticationFilter.class);
    }
}
