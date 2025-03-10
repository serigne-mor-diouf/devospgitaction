package com.isi.service;

import com.isi.dto.ProfesseurDTO;
import com.isi.model.Professeur;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProfesseurService {
    Professeur createProfesseur(ProfesseurDTO professeurDTO);
    Professeur updateProfesseur(Long id, ProfesseurDTO professeurDTO);
    void deleteProfesseur(Long id);
    Professeur getProfesseurById(Long id);
    List<Professeur> getAllProfesseurs();
    Page<Professeur> getAllProfesseursPagines(int page, int size);
    List<Professeur> getProfesseursByDepartement(Long departementId);
    List<Professeur> getProfesseursBySpecialite(Long specialiteId);

}
