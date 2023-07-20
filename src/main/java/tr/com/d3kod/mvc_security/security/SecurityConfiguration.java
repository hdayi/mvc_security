package tr.com.d3kod.mvc_security.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

        @Bean
        public UserDetailsManager userDetailsManager(DataSource dataSource) {
                JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
                userDetailsManager.setUsersByUsernameQuery("select user_id, pw, active from members where user_id=?");
                userDetailsManager.setAuthoritiesByUsernameQuery("select user_id, role from roles where user_id=?");
                return userDetailsManager;
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http.authorizeHttpRequests(
                                configurer -> configurer
                                                .requestMatchers("/").hasRole("EMPLOYEE")
                                                .requestMatchers("/leaders/**").hasRole("MANAGER")
                                                .requestMatchers("/systems/**").hasRole("ADMIN")
                                                .anyRequest().authenticated())
                                .formLogin(
                                                form -> form.loginPage("/login")
                                                                .loginProcessingUrl("/authenticate")
                                                                .permitAll())
                                .logout(logout -> logout.permitAll())
                                .exceptionHandling(configurer -> configurer.accessDeniedPage("/access-denied"));
                return http.build();
        }
}
/*
 * bu da sabit standart tablo ile olursa
 * 
 * @Bean
 * public UserDetailsManager userDetailsManager(DataSource dataSource) {
 * return new JdbcUserDetailsManager(dataSource);
 * }
 * 
 */

// asagisi hard coded
// @Bean
// public InMemoryUserDetailsManager userDetailsManager() {
// UserDetails john =
// User.builder().username("john").password("{noop}qaz").roles("PERSONEL").build();
// UserDetails mary =
// User.builder().username("mary").password("{noop}qaz").roles("PERSONEL",
// "MUDUR").build();
// UserDetails susan = User.builder().username("susan").password("{noop}qaz")
// .roles("PERSONEL", "MUDUR", "MUMKUNMUDUR").build();

// return new InMemoryUserDetailsManager(john, mary, susan);
// }