package tr.com.d3kod.mvc_security.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        UserDetails john = User.builder().username("john").password("{noop}qaz").roles("PERSONEL").build();
        UserDetails mary = User.builder().username("mary").password("{noop}qaz").roles("PERSONEL", "MUDUR").build();
        UserDetails susan = User.builder().username("susan").password("{noop}qaz")
                .roles("PERSONEL", "MUDUR", "MUMKUNMUDUR").build();

        return new InMemoryUserDetailsManager(john, mary, susan);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                configurer -> configurer.anyRequest().authenticated()).formLogin(
                        form -> form.loginPage("/login")
                                .loginProcessingUrl("/authenticate")
                                .permitAll())
                .logout(logout -> logout.permitAll());
        return http.build();
    }
}
