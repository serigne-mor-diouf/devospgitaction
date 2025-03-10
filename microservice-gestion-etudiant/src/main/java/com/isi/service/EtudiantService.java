package com.isi.service;

import com.isi.dto.EtudiantDTO;
import com.isi.model.Etudiant;
import org.springframework.data.domain.Page;
import java.util.List;

public interface EtudiantService {
    Etudiant createEtudiant(EtudiantDTO etudiantDTO);
    Etudiant updateEtudiant(Long id, EtudiantDTO etudiantDTO);
    void deleteEtudiant(Long id);
    Etudiant getEtudiantById(Long id);
    Etudiant getEtudiantByMatricule(String matricule);
    List<Etudiant> getAllEtudiants();
    Page<Etudiant> getAllEtudiantsPagines(int page, int size);
    List<Etudiant> getEtudiantsByClasse(Long classeId);
    List<Etudiant> searchEtudiants(String searche);
}