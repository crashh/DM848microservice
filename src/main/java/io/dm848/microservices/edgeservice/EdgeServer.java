package io.dm848.microservices.edgeservice;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

import org.springframework.cloud.security.oauth2.sso.EnableOAuth2Sso;
import org.springframework.cloud.security.oauth2.sso.OAuth2SsoConfigurer;
import org.springframework.cloud.security.oauth2.sso.OAuth2SsoConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SpringBootApplication
@Controller
@EnableZuulProxy
@EnableOAuth2Sso
public class EdgeServer {

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "edge-server");
        new SpringApplicationBuilder(EdgeServer.class).web(true).run(args);
    }

    @Configuration
    protected static class SecurityConfiguration extends OAuth2SsoConfigurerAdapter {

        @Override
        public void match(OAuth2SsoConfigurer.RequestMatchers matchers) {
            matchers.anyRequest();
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.logout().and().antMatcher("/**").authorizeRequests()
                    .antMatchers("/index.html", "/home.html", "/", "/user-service/**","/eureka/**","/webservice/**","/userservice/**","/authservice/**").permitAll()
                    .anyRequest().authenticated().and().csrf()
                    .csrfTokenRepository(csrfTokenRepository()).and()
                    .addFilterBefore(new RequestContextFilter(), HeaderWriterFilter.class)
                    .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
        }

        private Filter csrfHeaderFilter() {
            return new OncePerRequestFilter() {
                @Override
                protected void doFilterInternal(HttpServletRequest request,
                                                HttpServletResponse response, FilterChain filterChain)
                        throws ServletException, IOException {
                    CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class
                            .getName());
                    if (csrf != null) {
                        Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
                        String token = csrf.getToken();
                        if (cookie == null || token != null
                                && !token.equals(cookie.getValue())) {
                            cookie = new Cookie("XSRF-TOKEN", token);
                            cookie.setPath("/");
                            response.addCookie(cookie);
                        }
                    }
                    filterChain.doFilter(request, response);
                }
            };
        }

        private CsrfTokenRepository csrfTokenRepository() {
            HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
            repository.setHeaderName("X-XSRF-TOKEN");
            return repository;
        }

    }
}