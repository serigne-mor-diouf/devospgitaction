package com.isi.repository;

import com.isi.model.Professeur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfesseurRepository extends JpaRepository<Professeur,Long> {
        boolean existsByEmail(String email);
        List<Professeur> findByDepartementId(Long departementId);
        List<Professeur> findBySpecialitesId(Long specialiteId);
}
