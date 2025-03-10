package com.isi.repository;

import com.isi.model.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    Optional<Etudiant> findByMatricule(String matricule);
    Optional<Etudiant> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByMatricule(String matricule);
    List<Etudiant> findByClasseId(Long classeId);
    List<Etudiant> findByNomContainingOrPrenomContainingOrMatriculeContaining(
            String nom, String prenom, String matricule);
}