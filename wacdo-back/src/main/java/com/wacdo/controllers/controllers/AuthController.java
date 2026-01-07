package com.wacdo.controllers.controllers;

import com.wacdo.controllers.dto.AuthRequest;
import com.wacdo.controllers.dto.AuthResponse;
import com.wacdo.controllers.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        try {
            // authenticationManager appelera authenticate du providerManager qui appelera le provider DaoAuthenticationProvider
            // puis loadUserByUsername de CollaborateurService
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getMotDePasse()
                    )
            );

            UserDetails user = (UserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(user);

            return new AuthResponse(token);

        } catch (BadCredentialsException ex) {
            throw new RuntimeException("Nom d'utilisateur, mot de passe incorrecte.");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // Rien à faire côté serveur
        return ResponseEntity.ok("Logout successful");
    }
}
