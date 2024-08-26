package org.twids.SpringBlog.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.twids.SpringBlog.util.constants.Privillages;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private static final String[] WHITELIST = {
        "/",
        "/login",
        "/register",
        "/db-console/**",
        "/resources/**",
        "/posts/**",
        "/forgot-password",
        "/reset-password",
        "/change-password"
    };
    
    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
        .headers().frameOptions().sameOrigin()
        .and()
        //İstek yetkilendirme yapılandırmasını başlatır.
        .authorizeHttpRequests()
        //WHITELIST içinde tanımlanan URL'lere erişimi tüm kullanıcılara (giriş yapmamış olanlar dahil) izin verir.
        .requestMatchers(WHITELIST).permitAll()
        /*WHITELIST'e alınmamış tüm diğer isteklerin kimlik doğrulaması gerektirdiğini belirtir.
        .anyRequest().authenticated()*/
        ///profile ile başlayan tüm URL'lere erişim için kullanıcıların kimlik doğrulaması yapmasını zorunlu kılar.
        .requestMatchers("/profile/**").authenticated()
        .requestMatchers("/update_photo/**").authenticated()
        .requestMatchers("/posts/add/**").authenticated()
        .requestMatchers("/admin/**").hasRole("ADMIN")
        .requestMatchers("/editor/**").hasAnyRole("ADMIN","EDITOR")
        
        //.antMatchers("/admin") yazsaydık diğer /admin ile örtüşürdü...genel olarak rol yerine authority kullanmak daha iyi.
        .requestMatchers("/test").hasAuthority(Privillages.ACCESS_ADMIN_PANEL.getPrivillage())       
        .and()
        //kullanıcıların "/login" sayfasından giriş yapabilmesini sağlıyoruz. 
        //giriş formunda "email" ve "password" alanları kullanılıyor(account attribute).
        //başarılı girişte ana sayfaya, başarısız girişte "/login?error" sayfasına yönlendiriyoruz.
            .formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/login")
            .usernameParameter("email")
            .passwordParameter("password")
            .defaultSuccessUrl("/", true)
            .failureUrl("/login?error")
            .permitAll()
        .and()
        //kullanıcıların "/logout" URL'sine erişerek çıkış yapmasını sağlıyoruz. 
        //başarılı çıkıştan sonra "home" sayfasına yönlendirme yapıyoruz.
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")
        .and()
        .rememberMe().rememberMeParameter("remember-me")
        .and()
        //temel HTTP kimlik doğrulamasını da etkinleştiriyoruz.
        .httpBasic();

        //TODO: remove these after upgrading the DB from H2 infile DB
        http.csrf().disable();
        http.headers().frameOptions().disable();

        //yapılandırılan HttpSecurity nesnesini geri döndürür.
        return http.build();
    }   
}
