package com.se.Fuel_Quota_Management_System.security;

import com.se.Fuel_Quota_Management_System.service.CustomUserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    JwtUtil jwtUtil;

    CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal
            (HttpServletRequest request,
             HttpServletResponse response,
             FilterChain filterChain) throws ServletException, IOException
    {
        String token = request.getHeader("Authorization");

        if( token != null && token.startsWith("Bearer ")){
            token = token.substring(7);
            String username = jwtUtil.extractUserName(token);

            if(username !=  null &&
                    SecurityContextHolder.getContext().getAuthentication() == null)
            {
                UserDetails userDetails = customUserDetailService.loadUserByUsername(username);

                if(jwtUtil.isTokenValid(token))
                {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        filterChain.doFilter(request,response);
    }



}
