package com.product_service.filter;

import com.product_service.dto.User;
import com.product_service.feign.AuthServiceFeignClient;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthServiceFeignClient authServiceFeignClient;



    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        System.out.println("Authorization Header: " + request.getHeader("Authorization"));
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            String username = jwtService.validateTokenAndRetrieveSubject(jwt);

            User user = authServiceFeignClient.getUser(username, authHeader);

            System.out.println("Username from Auth Service: " + user.getUsername());
            System.out.println("Role from Auth Service: " + user.getRole());

            var authorities = Collections.singleton(
                    new SimpleGrantedAuthority("ROLE_" + user.getRole()));

            System.out.println("Authorities: " + authorities);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            authorities);

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authToken);

            System.out.println("Authentication: " + SecurityContextHolder.getContext().getAuthentication());
        }


        filterChain.doFilter(request, response);


    }
}
