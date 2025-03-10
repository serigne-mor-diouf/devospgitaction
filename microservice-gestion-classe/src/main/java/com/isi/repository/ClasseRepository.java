package com.isi.repository;

import com.isi.model.Classe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClasseRepository extends JpaRepository<Classe, Long> {
    Optional<Classe> findByCode(String code);
    List<Classe> findByNiveau(String niveau);
    List<Classe> findByFiliere(String filiere);
    List<Classe> findByAnneeScolaire(String anneeScolaire);
    boolean existsByCode(String code);
    List<Classe> findByResponsableId(Long responsableId);
}