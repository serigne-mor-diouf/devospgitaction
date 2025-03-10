package com.isi.service;

import com.isi.dto.InscriptionDTO;
import com.isi.model.Inscription;
import org.springframework.data.domain.Page;
import java.util.List;

public interface InscriptionService {
    Inscription createInscription(InscriptionDTO inscriptionDTO);
    Inscription updateInscription(Long id, InscriptionDTO inscriptionDTO);
    void deleteInscription(Long id);
    Inscription getInscriptionById(Long id);
    List<Inscription> getInscriptionsByEtudiant(Long etudiantId);
    //List<Inscription> getInscriptionsByAnneeAcademique(Long anneeAcademiqueId);
    Page<Inscription> getAllInscriptionsPaginees(int page, int size);
    Double calculateSolde(Long etudiantId, Long anneeAcademiqueId);
}