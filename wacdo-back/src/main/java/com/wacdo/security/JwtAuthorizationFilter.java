    package com.wacdo.security;

    import com.auth0.jwt.interfaces.DecodedJWT;
    import com.wacdo.services.JwtService;
    import jakarta.servlet.FilterChain;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import lombok.NonNull;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.GrantedAuthority;
    import org.springframework.security.core.authority.SimpleGrantedAuthority;
    import org.springframework.security.core.context.SecurityContextHolder;
    import org.springframework.stereotype.Component;
    import org.springframework.web.filter.OncePerRequestFilter;

    import java.io.IOException;
    import java.util.List;

    @Component
    public class JwtAuthorizationFilter extends OncePerRequestFilter {

        private final JwtService jwtService;

        public JwtAuthorizationFilter(JwtService jwtService) {
            this.jwtService = jwtService;
        }

        @Override
        protected void doFilterInternal(HttpServletRequest request,
                                        @NonNull HttpServletResponse response,
                                        @NonNull FilterChain filterChain)
                throws ServletException, IOException {

            String path = request.getServletPath();
            if (path.startsWith("/auth/") 
                || path.startsWith("/register")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.equals("/swagger-ui.html")) {
                    filterChain.doFilter(request, response);
                    return;
            }

            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            String token = authHeader.substring(7);
            try {
                DecodedJWT jwt = jwtService.verify(token);

                String email = jwt.getSubject();
                boolean isAdmin = jwt.getClaim("admin").asBoolean();

                List<GrantedAuthority> authorities = List.of(
                        new SimpleGrantedAuthority(isAdmin ? "ROLE_ADMIN" : "ROLE_USER")
                );

                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(email, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\":\"Token JWT invalide ou expiré\"}");
                return;
            }

            filterChain.doFilter(request, response);
        }
    }
