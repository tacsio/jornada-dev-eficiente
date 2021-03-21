package io.tacsio.mercadolivre.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.tacsio.mercadolivre.config.jwt.JwtConfig;
import io.tacsio.mercadolivre.config.jwt.JwtTokenVerifier;
import io.tacsio.mercadolivre.config.jwt.JwtUsernamePasswordAuthenticationFilter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;

import static io.tacsio.mercadolivre.config.security.ApplicationPermission.*;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(JwtConfig.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService authenticationService;
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;
    private final JwtTokenVerifier jwtTokenVerifier;

    public WebSecurityConfig(PasswordEncoder passwordEncoder, UserDetailsService authenticationService, JwtConfig jwtConfig, SecretKey secretKey, JwtTokenVerifier jwtTokenVerifier) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationService = authenticationService;
        this.jwtConfig = jwtConfig;
        this.secretKey = secretKey;
        this.jwtTokenVerifier = jwtTokenVerifier;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                //jwt authentication
                .addFilter(new JwtUsernamePasswordAuthenticationFilter(
                        new ObjectMapper(),
                        authenticationManager(),
                        jwtConfig, secretKey))

                //jwt authorization
                .addFilterAfter(jwtTokenVerifier, JwtUsernamePasswordAuthenticationFilter.class)

                .authorizeRequests()
                //h2-config
                .antMatchers("/h2-console/**").permitAll()
                .and().headers().frameOptions().disable().and()
                //
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users").permitAll()

                .antMatchers(HttpMethod.POST, "/categories").hasAuthority(CATEGORY_WRITE.getPermission())
                .antMatchers(HttpMethod.GET, "/categories").hasAuthority(CATEGORY_READ.getPermission())

                .antMatchers(HttpMethod.POST, "/products").hasAuthority(PRODUCT_WRITE.getPermission())
                .antMatchers(HttpMethod.GET, "/products").hasAuthority(CATEGORY_READ.getPermission())
                .anyRequest()
                .authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(authenticationService)
                .passwordEncoder(passwordEncoder);
    }
}
