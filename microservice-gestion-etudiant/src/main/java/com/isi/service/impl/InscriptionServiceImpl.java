package com.isi.service.impl;

import com.isi.dto.InscriptionDTO;
import com.isi.model.Inscription;
import com.isi.model.Etudiant;
import com.isi.model.AnneeAcademique;
import com.isi.repository.InscriptionRepository;
import com.isi.repository.EtudiantRepository;
import com.isi.repository.AnneeAcademiqueRepository;
import com.isi.service.InscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

@Service
public class InscriptionServiceImpl implements InscriptionService {


    private final InscriptionRepository inscriptionRepository;

    private final EtudiantRepository etudiantRepository;

    private final AnneeAcademiqueRepository anneeAcademiqueRepository;

    public InscriptionServiceImpl(InscriptionRepository inscriptionRepository, EtudiantRepository etudiantRepository, AnneeAcademiqueRepository anneeAcademiqueRepository) {
        this.inscriptionRepository = inscriptionRepository;
        this.etudiantRepository = etudiantRepository;
        this.anneeAcademiqueRepository = anneeAcademiqueRepository;
    }

    private Inscription convertToEntity(InscriptionDTO dto) {
        Inscription inscription = new Inscription();

        Etudiant etudiant = etudiantRepository.findById(dto.getEtudiantId())
                .orElseThrow(() -> new EntityNotFoundException("Étudiant non trouvé"));
        inscription.setEtudiant(etudiant);

        inscription.setDateInscription(LocalDate.parse(dto.getDateInscription()));
        inscription.setStatut(dto.getStatut());
        inscription.setFraisInscription(dto.getFraisInscription());
        inscription.setFraisScolarite(dto.getFraisScolarite());
        inscription.setMontantVerse(dto.getMontantVerse());
        inscription.setObservation(dto.getObservation());

        return inscription;
    }

    @Override
    public Inscription createInscription(InscriptionDTO inscriptionDTO) {

        Inscription inscription = convertToEntity(inscriptionDTO);
        return inscriptionRepository.save(inscription);
    }

    @Override
    public Inscription updateInscription(Long id, InscriptionDTO inscriptionDTO) {
        Inscription existingInscription = inscriptionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inscription non trouvée"));

        if (inscriptionDTO.getDateInscription() != null) {
            existingInscription.setDateInscription(LocalDate.parse(inscriptionDTO.getDateInscription()));
        }
        if (inscriptionDTO.getStatut() != null) existingInscription.setStatut(inscriptionDTO.getStatut());
        if (inscriptionDTO.getFraisInscription() != null) existingInscription.setFraisInscription(inscriptionDTO.getFraisInscription());
        if (inscriptionDTO.getFraisScolarite() != null) existingInscription.setFraisScolarite(inscriptionDTO.getFraisScolarite());
        if (inscriptionDTO.getMontantVerse() != null) existingInscription.setMontantVerse(inscriptionDTO.getMontantVerse());
        if (inscriptionDTO.getObservation() != null) existingInscription.setObservation(inscriptionDTO.getObservation());

        return inscriptionRepository.save(existingInscription);
    }

    @Override
    public void deleteInscription(Long id) {
        if (!inscriptionRepository.existsById(id)) {
            throw new EntityNotFoundException("Inscription non trouvée");
        }
        inscriptionRepository.deleteById(id);
    }

    @Override
    public Inscription getInscriptionById(Long id) {
        return inscriptionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Inscription non trouvée"));
    }

    @Override
    public List<Inscription> getInscriptionsByEtudiant(Long etudiantId) {
        return inscriptionRepository.findByEtudiantId(etudiantId);
    }

//    @Override
//    public List<Inscription> getInscriptionsByAnneeAcademique(Long anneeAcademiqueId) {
//        return inscriptionRepository.findByAnneeAcademiqueId(anneeAcademiqueId);
//    }


    @Override
    public Page<Inscription> getAllInscriptionsPaginees(int page, int size) {
        return inscriptionRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Double calculateSolde(Long etudiantId, Long anneeAcademiqueId) {
      return  null ;
    }
}