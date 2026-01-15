package com.wacdo.security;

import com.wacdo.entities.Collaborateur;
import com.wacdo.repositories.CollaborateurRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
public class CollaborateurDetailsService implements UserDetailsService {

    private final CollaborateurRepository collaborateurRepository;

    public CollaborateurDetailsService(CollaborateurRepository collaborateurRepository) {
        this.collaborateurRepository = collaborateurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Collaborateur collab = collaborateurRepository.findByEmail(email);
        if (collab == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé");
        }

        GrantedAuthority authority = new SimpleGrantedAuthority(collab.getRole().getName());
        return new User(collab.getEmail(), collab.getMotDePasse(), Collections.singletonList(authority));
    }
}
