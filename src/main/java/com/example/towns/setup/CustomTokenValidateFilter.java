package com.example.towns.setup;

import com.example.towns.authentication.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomTokenValidateFilter extends OncePerRequestFilter {

    @Autowired
    UserDetailsService userDetailsServiceImpl;

    @Autowired
    private TokenUtil tokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String tokenUsername = null;
        String token = null;
        try {
            String authorizationHeader = request.getHeader("Authorization");
            logger.debug("Parse Authorization header " + authorizationHeader);
            String[] bearerSplit = authorizationHeader.split(" ");
            String bearer = bearerSplit[0];
            token = bearerSplit[1];
            if (bearer.equals("Bearer")) {
                tokenUsername = tokenUtil.getUsername(token);
            }
        } catch (Exception e) {
            logger.debug("JWT wasnt parsed from the header: " + e.getMessage());
        }

        if (tokenUsername != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(tokenUsername);

            // if token is valid, manually set authentication
            if (tokenUtil.validate(token, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
                logger.info("Authenticated successfully username: " + tokenUsername);
            }
        }
        chain.doFilter(request, response);
    }
}
