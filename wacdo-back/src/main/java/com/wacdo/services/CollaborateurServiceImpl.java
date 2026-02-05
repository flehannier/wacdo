package com.wacdo.services;

import com.wacdo.dto.CollaborateurRequest;
import com.wacdo.entities.Collaborateur;
import com.wacdo.entities.Role;
import com.wacdo.exception.FunctionalException;
import com.wacdo.exception.TechnicalException;
import com.wacdo.repositories.CollaborateurRepository;
import com.wacdo.repositories.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@Slf4j
public class CollaborateurServiceImpl implements CollaborateurService {

    private final RoleRepository roleRepository;
    private final CollaborateurRepository collaborateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final String ADMIN = "ADMIN";
    private final String USER = "USER";

    // Pattern pour valider la force du mot de passe : au moins 8 caractères, une
    // majuscule, une minuscule, un chiffre
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");

    public CollaborateurServiceImpl(RoleRepository roleRepository, CollaborateurRepository collaborateurRepository,
            PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.collaborateurRepository = collaborateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Enreigstrement ou mise à jour d'un collaborateur
     *
     * @param collab Le collaborateur
     * @return Collaborateur
     * @throws FunctionalException, TechnicalException
     */
    @Override
    @Transactional
    public Collaborateur save(@NonNull CollaborateurRequest collab) throws FunctionalException, TechnicalException {
        Collaborateur entity;

        if (collab.id() != null) {
            // --- UPDATE ---
            entity = collaborateurRepository.findById(collab.id())
                    .orElseThrow(() -> new FunctionalException("Collaborateur introuvable"));

        } else {
            // --- CREATE ---
            if (collaborateurRepository.findByEmail(collab.email()) != null) {
                throw new FunctionalException("Email déjà connu");
            }
            entity = new Collaborateur();
        }

        boolean isAdmin = false;
        List<Role> roles= roleRepository.findAll();
        Optional<Role>  roleOptional; 
        if(null != collab.roleId()) {
            roleOptional = roles.stream()
                        .filter(item -> item.getId().equals(collab.roleId())) // Utilise -> et compare les IDs de rôles
                        .findFirst();

            isAdmin = collab.roleId() != null && ADMIN.equals(roleOptional.get().getName());
        }

        // Copier les champs
        entity.setNom(collab.nom());
        entity.setPrenom(collab.prenom());
        entity.setEmail(collab.email());

        Role role = roleRepository.findByNameIgnoreCase(isAdmin ? ADMIN : USER);
        if (role == null) throw new FunctionalException("Rôle introuvable");

        entity.setAdministrateur(isAdmin);
        entity.setRole(role);

        if (collab.motDePasse() != null && !collab.motDePasse().isBlank()) {
            validateAndEncodePassword(collab.motDePasse(), entity);
        }

        return collaborateurRepository.save(entity);
    }

    /**
     * Valide puis encode le mot de passe
     *
     * @param password Le mot de passe
     * @param collaborateur Le collaborateur
     * @throws FunctionalException
     */
    private void validateAndEncodePassword(@NonNull String password, @NonNull Collaborateur collaborateur) throws FunctionalException, TechnicalException {
        if (!isPasswordStrong(password)) {
            throw new FunctionalException(
                    "Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule et un chiffre");
        }

        String encoded = passwordEncoder.encode(password);
        if (encoded == null || encoded.isBlank()) {
            throw new TechnicalException("Erreur lors de l'encodage du mot de passe");
        }
        collaborateur.setMotDePasse(encoded);
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

    /**
     * Suppressoin d'un collaborateur
     * @param id
     */
    @Override
    public void deleteById(@NonNull Long id){
        collaborateurRepository.deleteById(id);
    }

    /**
     * Suppression d'un collaborateur
     * @param collab
     */
    @Override
    public void delete(@NonNull Collaborateur collab) {
        collaborateurRepository.deleteById(collab.getId());
    }

    /**
     * Récupération d'un collaborateur par son id
     * @param id identifiant du collaborateur
     * @return Optional<Collaborateur>
     */
    @Override
    public Collaborateur getById(@NonNull Long id) throws FunctionalException {
        return collaborateurRepository.findById(id).orElseThrow(() -> new FunctionalException("Collaborateur introuvable"));
    }

    /**
     * Récupération d'un collaborateur par son email
     * @param email identifiant du collaborateur
     * @return Optional<Collaborateur>
     */
    @Override
    public Collaborateur getByEmail(@NonNull String email) throws FunctionalException {
        return collaborateurRepository.findByEmail(email);
    }

    /**
     * Retour la liste des collaborateurs
     * @return
     */
    @Override
    public List<Collaborateur> getAll() {
        return collaborateurRepository.findAll();
    }

    @Override
    public Collaborateur findByEmail(@NonNull String email) throws FunctionalException {
        return collaborateurRepository.findByEmail(email);
    }
}