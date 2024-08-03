package task.test.management.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import task.test.management.service.UserInfoService;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {
    @Bean
    PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationProvider authProvider(UserInfoService userInfoService){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(encoder());
        provider.setUserDetailsService(userInfoService);
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security, AuthenticationProvider provider) throws Exception{
        security.authorizeHttpRequests((request) -> request
                        .requestMatchers("/admin/*").hasRole("ADMIN")
                        .requestMatchers("/user/*").hasRole("USER")
                        .requestMatchers("/login").anonymous()
                        .requestMatchers("/logout", "/").authenticated())
                .csrf((csrf) -> csrf.disable() )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .usernameParameter("username")
                        .passwordParameter("password"))
                .logout((form) -> form
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login"))
                .authenticationProvider(provider);

        return security.build();
    }


}
