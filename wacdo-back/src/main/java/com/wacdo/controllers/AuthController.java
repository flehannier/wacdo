package com.wacdo.controllers;

import com.wacdo.dto.AuthRequest;
import com.wacdo.dto.AuthResponse;
import com.wacdo.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(JwtService jwtService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) throws RuntimeException{
        // authenticationManager appelera authenticate du providerManager qui appelera le provider DaoAuthenticationProvider
        // puis loadUserByUsername de CollaborateurDetailsService
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getMotDePasse()
                )
        );

        UserDetails user = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(user);

        ArrayList<String> grantedAuthorities = new ArrayList<>();
        for(GrantedAuthority grantedAuthority : user.getAuthorities()){
            grantedAuthorities.add(grantedAuthority.getAuthority());
        }

        return new AuthResponse(user.getUsername(), grantedAuthorities.getFirst(), token);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // Rien à faire côté serveur
        return ResponseEntity.ok("Logout successful");
    }
}
