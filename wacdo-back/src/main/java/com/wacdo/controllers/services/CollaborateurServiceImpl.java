package com.wacdo.controllers.services;

import com.wacdo.controllers.entities.Collaborateur;
import com.wacdo.controllers.entities.Role;
import com.wacdo.controllers.exception.FunctionalException;
import com.wacdo.controllers.repositories.CollaborateurRepository;
import com.wacdo.controllers.repositories.RoleRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
@Slf4j
public class CollaborateurServiceImpl implements CollaborateurService {

    private final RoleRepository roleRepository;
    private final CollaborateurRepository collaborateurRepository;
    private final PasswordEncoder passwordEncoder;

    // Pattern pour valider la force du mot de passe : au moins 8 caractères, une
    // majuscule, une minuscule, un chiffre
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");

    public CollaborateurServiceImpl(RoleRepository roleRepository, CollaborateurRepository collaborateurRepository,
            PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.collaborateurRepository = collaborateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Collaborateur save(@NonNull Collaborateur collab) throws FunctionalException {
        if ( collab.getId() != null) {
            Collaborateur existing = collaborateurRepository.findById(collab.getId())
                    .orElseThrow(() -> new FunctionalException("Collaborateur introuvable"));

            existing.setNom(collab.getNom());
            existing.setPrenom(collab.getPrenom());
            existing.setEmail(collab.getEmail());

            // mot de passe seulement si fourni
            if (!collab.getMotDePasse().isBlank()) {
                validateAndEncodePassword(collab.getMotDePasse(), existing);
            }

            return collaborateurRepository.save(existing);
        }

        // CREATE
        validateAndEncodePassword(collab.getMotDePasse(), collab);
        return collaborateurRepository.save(collab);
    }

    private void validateAndEncodePassword(@NonNull String password, @NonNull Collaborateur collaborateur) {
        if (!isPasswordStrong(password)) {
            throw new RuntimeException(
                    "Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule et un chiffre");
        }
        collaborateur.setMotDePasse(passwordEncoder.encode(password));
    }

    /**
     * Valide la force du mot de passe
     * 
     * @param password Le mot de passe à valider
     * @return true si le mot de passe est fort, false sinon
     */
    private boolean isPasswordStrong(@NonNull String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    @Override
    public void deleteById(@NonNull Long id) {
        collaborateurRepository.deleteById(id);
    }

    @Override
    public void delete(@NonNull Collaborateur collab) {
        collaborateurRepository.delete(collab);
    }

    @Override
    public Collaborateur getById(@NonNull Long id) {
        return collaborateurRepository.findById(id).get();
    }

    @Override
    public List<Collaborateur> getAll() {
        return collaborateurRepository.findAll();
    }

    @Override
    public Role addRole(@NonNull Role role) {
        return roleRepository.save(role);
    }

}
