package com.utp.pgio.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class JwtSessionFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtSessionFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.equals("/login") || path.startsWith("/css/")
                || path.startsWith("/js/") || path.startsWith("/img/")
                || path.equals("/") || path.equals("/favicon.ico");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session != null) {
            String token = (String) session.getAttribute("jwt");

            if (token != null && jwtUtil.validarToken(token)) {
                try {
                    String username = jwtUtil.extraerUsername(token);
                    List<String> roles = jwtUtil.extraerRoles(token);

                    if (roles == null || roles.isEmpty()) {
                        System.out.println("[WARN] Usuario " + username + " tiene roles vacíos o null");
                        filterChain.doFilter(request, response);
                        return;
                    }

                    List<SimpleGrantedAuthority> authorities = roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            username, null, authorities);

                    SecurityContextHolder.getContext().setAuthentication(auth);
                } catch (Exception e) {
                    System.out.println("[ERROR] Error procesando JWT: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}