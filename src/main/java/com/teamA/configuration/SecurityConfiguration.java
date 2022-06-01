package com.teamA.configuration;


import com.teamA.service.implementation.MyUserDetailsService;
import com.teamA.session.SessionFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.http.HttpServletResponse;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    private UserDetailsService service;
    private  SessionFilter sessionFilter;

   @Autowired
    public void setSessionFilter(SessionFilter sessionFilter) {
        this.sessionFilter = sessionFilter;
    }


    @Autowired
    public void setUserDetailsService(UserDetailsService service){
        this.service = service;
    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http = http.cors().and().csrf().disable();
        http = http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    authException.getMessage());
        }).and();

        http
                .authorizeRequests()
                .antMatchers("/api/**").authenticated()
                .antMatchers("/login").permitAll()
                .antMatchers("api/create-account").permitAll();


//        http
//                .authorizeRequests()
//                .antMatchers("/api/**").authenticated()
//                .antMatchers("/login/login").permitAll();



        http.addFilterBefore(
                sessionFilter,
                UsernamePasswordAuthenticationFilter.class);

    }



    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }


}
