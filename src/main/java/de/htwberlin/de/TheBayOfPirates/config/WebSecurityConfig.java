package de.htwberlin.de.TheBayOfPirates.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .usersByUsernameQuery("select email, password, enabled from users where email=?")
                .authoritiesByUsernameQuery("select u.email, r.role from users u inner join userrole ur on(u.userid=ur.userid) inner join role r on(ur.roleid=r.roleid) where u.email=?")
                .dataSource(dataSource)
                .passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/","/login", "/about", "/register").permitAll()
                .antMatchers( "/home", "/search").hasAnyAuthority("USER", "ADMIN")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .csrf().disable()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/?error=true")
                .usernameParameter("email")
                .passwordParameter("password")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/").deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .permitAll();
    }

}