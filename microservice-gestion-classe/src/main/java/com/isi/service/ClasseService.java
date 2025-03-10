package com.isi.service;

import com.isi.dto.ClasseDTO;
import com.isi.model.Classe;
import org.springframework.data.domain.Page;
import java.util.List;

public interface ClasseService {
    Classe createClasse(ClasseDTO classeDTO);
    Classe updateClasse(Long id, ClasseDTO classeDTO);
    void deleteClasse(Long id);
    Classe getClasseById(Long id);
    Classe getClasseByCode(String code);
    List<Classe> getAllClasses();
    Page<Classe> getAllClassesPaginees(int page, int size);
    List<Classe> getClassesByNiveau(String niveau);
    List<Classe> getClassesByFiliere(String filiere);
    List<Classe> getClassesByAnneeScolaire(String anneeScolaire);
    List<Classe> getClassesByResponsable(Long responsableId);
}