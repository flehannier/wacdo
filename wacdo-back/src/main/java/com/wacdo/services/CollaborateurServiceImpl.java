package com.wacdo.services;

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
import java.util.regex.Pattern;

@Service
@Slf4j
public class CollaborateurServiceImpl implements CollaborateurService {

    private final RoleRepository roleRepository;
    private final CollaborateurRepository collaborateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final String ADMIN = "ADMIN";

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
    public Collaborateur save(@NonNull Collaborateur collab) throws FunctionalException, TechnicalException {
        if (collab.getId() != null) {
            ///Maj collaborateur
            Collaborateur existing = collaborateurRepository.findById(collab.getId())
                    .orElseThrow(() -> new FunctionalException("Collaborateur introuvable"));

            existing.setNom(collab.getNom());
            existing.setPrenom(collab.getPrenom());
            existing.setEmail(collab.getEmail());

            if (null != collab.getRole()){
                if (collab.getRole().getName().equals(ADMIN)) {
                    existing.setAdministrateur(true);
                }
                existing.setRole(collab.getRole());
            }

            // mot de passe seulement si fourni
            if (null != collab.getMotDePasse() && !collab.getMotDePasse().isBlank()) {
                validateAndEncodePassword(collab.getMotDePasse(), existing);
            }

            //update fait à la fin de la transaction
            return existing;
        }

        //Ajout collaborateur
        Collaborateur collaborateur = collaborateurRepository.findByEmail(collab.getEmail());
        if(collaborateur != null){
            throw new FunctionalException("Email déjà connu");
        }

        //USER par defaut
        Role role = roleRepository.findByNameIgnoreCase("USER");
        collab.setAdministrateur(false);
        collab.setRole(role);

        validateAndEncodePassword(collab.getMotDePasse(), collab);

        return collaborateurRepository.save(collab);
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