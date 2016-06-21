package io.dm848.microservices.authservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.security.Principal;

@SpringBootApplication
@RestController
public class AuthServer extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "auth-server");
        SpringApplication.run(AuthServer.class, args);
    }



    @Configuration
    protected static class AuthenticationManagerConfiguration extends GlobalAuthenticationConfigurerAdapter {

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication()
                    .withUser("mstine").password("secret").roles("USER", "ADMIN").and()
                    .withUser("littleidea").password("secret").roles("USER", "ADMIN").and()
                    .withUser("starbuxman").password("secret").roles("USER", "ADMIN");
        }

    }
}