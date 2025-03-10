package com.isi.service;

import com.isi.dto.CoursDto;
import com.isi.model.Cours;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CoursService {
    Cours saveCours(CoursDto cours);
    Cours updateCours(Long id, CoursDto coursDTO);
    void deleteCours(Long id);
    Cours getCoursById(Long id);
    List<Cours> getAllCours();
    Page<Cours> getAllCoursPagines(int page, int size);
    List<Cours> getCoursByProfesseur(Long professeurId);
    List<Cours> getCoursByClasse(Long classeId);
    Optional<Cours> findByCode(String code);
    boolean existsByCode(String code);
}
