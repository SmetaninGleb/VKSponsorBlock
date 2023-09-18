package ru.vksponsorblock.VKSponsorBlock.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.vksponsorblock.VKSponsorBlock.security.utils.JwtUtil;
import ru.vksponsorblock.VKSponsorBlock.services.user.UserValidateService;
import ru.vksponsorblock.VKSponsorBlock.utils.exceptions.UserNotFoundException;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Value("${jwt.username-claim}")
    private String usernameClaim;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final UserValidateService userValidateService;

    @Autowired
    public JwtFilter(JwtUtil jwtUtil,
                     UserDetailsService userDetailsService,
                     UserValidateService userValidateService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userValidateService = userValidateService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (!hasAuthorizationBearer(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = getToken(request);
        DecodedJWT decodedJWT;
        try {
            decodedJWT = jwtUtil.validateToken(token);
        } catch (JWTVerificationException exception)
        {
            filterChain.doFilter(request, response);
            return;
        }

        String username = String.valueOf(decodedJWT.getClaim(usernameClaim));
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userDetails,
                        userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }

    private Boolean hasAuthorizationBearer(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || header.isBlank() || !header.startsWith("Bearer ")) {
            return false;
        }
        return true;
    }

    private String getToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        return header.split(" ")[1].trim();
    }
}
