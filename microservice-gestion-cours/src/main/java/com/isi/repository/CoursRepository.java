package com.isi.repository;

import com.isi.model.Cours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoursRepository extends JpaRepository<Cours, Long> {
    Optional<Cours> findByCode(String code);
    boolean existsByCode(String code);
    List<Cours> findByProfesseur_Id(Long professeurId);
    List<Cours> findByClasse_Id(Long classeId);

    List<Cours> findByProfesseurId(Long professeurId);

    List<Cours> findByClasseId(Long classeId);
} 