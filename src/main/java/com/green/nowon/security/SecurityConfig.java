package com.green.nowon.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    //챗봇할때쓴거
	@Bean
	public AuthenticationManager authenticationManager( AuthenticationConfiguration authenticationConfiguration) throws Exception {
	    return authenticationConfiguration.getAuthenticationManager();
	}
	
	
    //DB의 인증정보를 이용해서 인증처리하는 service 커스터마이징한 빈
    @Bean
    MyUserDetailsService customUserDetailsService() {
        return new MyUserDetailsService();
    }

    //패스워드 인코더 빈 등록(@Autowired로 가져올 수 있음)
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //소셜로그인 처리하는 메서드
    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> myOAuth2UserService() {
        return new MyOAuth2UserService();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize -> authorize
                        //.antMatchers("/**").permitAll()
                        .antMatchers("/css/**", "/js/**","/images/**","/webjars/**","/files/**").permitAll()
                        .antMatchers("/", "/comm/**","/my-websocket/**").permitAll()
                        .antMatchers("/members/**","/logout").hasRole("USER")
                        .antMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form->form
                        .loginPage("/comm/signin")             //[GET]
                        .loginProcessingUrl("/comm/emailSigninSec")    //[POST] form태그의 action
                        .usernameParameter("email")         //username -> email
                        .passwordParameter("pass")          //password -> pass
                        .permitAll()

                )
                .csrf(csrf->csrf.disable())
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/comm/signin")
                        //.defaultSuccessUrl("/",true)
                        //.successHandler(successHandler())
                        .userInfoEndpoint().userService(myOAuth2UserService())  //인증 작업 하는곳

                )

        ;
        return http.build();
    }
/*
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

            }
        };
    }

    */
}
