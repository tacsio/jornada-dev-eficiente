package io.tacsio.mercadolivre.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.tacsio.mercadolivre.model.data.UserRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtTokenVerifier extends OncePerRequestFilter {

    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;
    private final UserRepository userRepository;

    public JwtTokenVerifier(SecretKey secretKey, JwtConfig jwtConfig, UserRepository userRepository) {
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        var authorizationHeader = httpServletRequest.getHeader(jwtConfig.getAuthorizationHeader());

        if (!hasJwtHeader(authorizationHeader)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        var token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "").trim();

        try {
            var claimsJws = Jwts.parserBuilder()
                    .setSigningKey(secretKey).build()
                    .parseClaimsJws(token);

            var body = claimsJws.getBody();
            var username = body.getSubject();
            var grantedAuthorities = parseAuthorities(body);

            var user = userRepository.findByLogin(username).get();
            var authentication = new UsernamePasswordAuthenticationToken(user, null, grantedAuthorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (JwtException e) {
            String error = String.format("Untrusted token: %s", token);
            throw new IllegalStateException(error);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private boolean hasJwtHeader(String authorizationHeader) {
        return Strings.isNotEmpty(authorizationHeader) && authorizationHeader.startsWith(jwtConfig.getTokenPrefix());
    }

    private Set<? extends GrantedAuthority> parseAuthorities(Claims claims) throws JwtException {
        var authorities = (List<Map<String, String>>) claims.get("authorities");

        var grantedAuthorities = authorities.stream()
                .map(it -> it.get("authority"))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return grantedAuthorities;
    }
}
