//package com.ssafy.BackEnd.config;
//
////import com.ssafy.BackEnd.filter.JwtAuthenticationFilter;
////import com.ssafy.BackEnd.filter.JwtRequestFilter;
//import com.ssafy.BackEnd.service.JwtServiceImpl;
////import com.ssafy.BackEnd.service.JwtTokenProvider;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
////import org.springframework.web.filter.CharacterEncodingFilter;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig1 extends WebSecurityConfigurerAdapter {
//
//    private final JwtServiceImpl jwtService;
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception
//    {
//        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
//        web.ignoring().antMatchers("/swagger-resources/**",
//                "/swagger-ui.html", "/webjars/**", "/swagger/**");
//    }
//
//
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        System.out.println("여기니?");
//        http.httpBasic().disable();
//        //http.csrf().disable().formLogin().loginPage("/aaaaa"); // 페이지를 못가게 하는
//
//        http.csrf().disable()
//                .formLogin()
//                .and()
//                .authorizeRequests()
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/team/**").hasRole("USER")
//                .antMatchers("/dashboard/**").permitAll()
//                .antMatchers("/signin").permitAll()
//                .antMatchers("/signup").permitAll()
//                .antMatchers("/chat/**").hasRole("USER");
////                .addFilterBefore(new JwtAuthenticationFilter(jwtService), UsernamePasswordAuthenticationFilter.class)
////                .authorizeRequests();
//
//                //.antMatchers("/*").permitAll();
//
////                .antMatchers("/profile/*").permitAll();
//
//        //        CharacterEncodingFilter filter = new CharacterEncodingFilter();
////        filter.setEncoding("UTF-8");
////        filter.setForceEncoding(true);
////
////
////        http.csrf().disable().formLogin(); // 페이지를 못가게 하는
////
////        http.httpBasic().disable();
////        http.csrf()
////                .disable()
////                .headers()
////                .frameOptions()
////                .disable();
////
////        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
////        http.authorizeRequests()
////                .antMatchers("/admin/**").hasRole("ADMIN")
////                .antMatchers("/team/**").hasRole("USER")
////                .antMatchers("/signin/**").permitAll()
////                .antMatchers("/signup/**").permitAll()
//////                .antMatchers("/signin").permitAll()
//////                .antMatchers("/signup").permitAll()
//////                .antMatchers("/team/*").hasRole("USER")
////                .and()
////                .addFilterBefore(new JwtAuthenticationFilter(jwtService), UsernamePasswordAuthenticationFilter.class);
//
//    }
//
//
//        /**
//         * 테스트를 위해 In-Memory에 계정을 임의로 생성한다.
//         * 서비스에 사용시에는 DB데이터를 이용하도록 수정이 필요하다.
//         */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("eunseo")
//                .password("{noop}1234")
//                .roles("USER")
//                .and()
//                .withUser("eunseo1")
//                .password("{noop}1234")
//                .roles("USER")
//                .and()
//                .withUser("guest")
//                .password("{noop}1234")
//                .roles("GUEST");
//
//    }
//}
