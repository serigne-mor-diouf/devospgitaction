package com.isi.service.impl;

import com.isi.dto.EtudiantDTO;
import com.isi.model.Etudiant;
import com.isi.model.Classe;
import com.isi.repository.EtudiantRepository;
import com.isi.repository.ClasseRepository;
import com.isi.service.EtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

@Service
public class EtudiantServiceImpl implements EtudiantService {


    private final EtudiantRepository etudiantRepository;


    private final ClasseRepository classeRepository;

    public EtudiantServiceImpl(EtudiantRepository etudiantRepository, ClasseRepository classeRepository) {
        this.etudiantRepository = etudiantRepository;
        this.classeRepository = classeRepository;
    }

    private Etudiant convertToEntity(EtudiantDTO dto) {
        Etudiant etudiant = new Etudiant();
        etudiant.setNom(dto.getNom());
        etudiant.setPrenom(dto.getPrenom());
        etudiant.setMatricule(dto.getMatricule());
        etudiant.setEmail(dto.getEmail());
        etudiant.setTelephone(dto.getTelephone());
        if (dto.getDateNaissance() != null) {
            etudiant.setDateNaissance(LocalDate.parse(dto.getDateNaissance()));
        }
        etudiant.setAdresse(dto.getAdresse());
        etudiant.setLieuNaissance(dto.getLieuNaissance());
        etudiant.setNationalite(dto.getNationalite());
        etudiant.setGenre(dto.getGenre());

        if (dto.getClasseId() != null) {
            Classe classe = classeRepository.findById(dto.getClasseId())
                    .orElseThrow(() -> new EntityNotFoundException("Classe non trouvée"));
            etudiant.setClasse(classe);
        }

        return etudiant;
    }

    @Override
    public Etudiant createEtudiant(EtudiantDTO etudiantDTO) {
        if (etudiantRepository.existsByEmail(etudiantDTO.getEmail())) {
            throw new IllegalArgumentException("Un étudiant avec cet email existe déjà");
        }
        if (etudiantRepository.existsByMatricule(etudiantDTO.getMatricule())) {
            throw new IllegalArgumentException("Un étudiant avec ce matricule existe déjà");
        }

        Etudiant etudiant = convertToEntity(etudiantDTO);
        return etudiantRepository.save(etudiant);
    }

    @Override
    public Etudiant updateEtudiant(Long id, EtudiantDTO etudiantDTO) {
        Etudiant existingEtudiant = etudiantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Étudiant non trouvé"));

        if (!existingEtudiant.getEmail().equals(etudiantDTO.getEmail()) &&
                etudiantRepository.existsByEmail(etudiantDTO.getEmail())) {
            throw new IllegalArgumentException("Un étudiant avec cet email existe déjà");
        }

        if (!existingEtudiant.getMatricule().equals(etudiantDTO.getMatricule()) &&
                etudiantRepository.existsByMatricule(etudiantDTO.getMatricule())) {
            throw new IllegalArgumentException("Un étudiant avec ce matricule existe déjà");
        }

        if (etudiantDTO.getNom() != null) existingEtudiant.setNom(etudiantDTO.getNom());
        if (etudiantDTO.getPrenom() != null) existingEtudiant.setPrenom(etudiantDTO.getPrenom());
        if (etudiantDTO.getEmail() != null) existingEtudiant.setEmail(etudiantDTO.getEmail());
        if (etudiantDTO.getTelephone() != null) existingEtudiant.setTelephone(etudiantDTO.getTelephone());
        if (etudiantDTO.getDateNaissance() != null) {
            existingEtudiant.setDateNaissance(LocalDate.parse(etudiantDTO.getDateNaissance()));
        }
        if (etudiantDTO.getAdresse() != null) existingEtudiant.setAdresse(etudiantDTO.getAdresse());
        if (etudiantDTO.getLieuNaissance() != null) existingEtudiant.setLieuNaissance(etudiantDTO.getLieuNaissance());
        if (etudiantDTO.getNationalite() != null) existingEtudiant.setNationalite(etudiantDTO.getNationalite());
        if (etudiantDTO.getGenre() != null) existingEtudiant.setGenre(etudiantDTO.getGenre());

        if (etudiantDTO.getClasseId() != null) {
            Classe classe = classeRepository.findById(etudiantDTO.getClasseId())
                    .orElseThrow(() -> new EntityNotFoundException("Classe non trouvée"));
            existingEtudiant.setClasse(classe);
        }

        return etudiantRepository.save(existingEtudiant);
    }

    @Override
    public void deleteEtudiant(Long id) {
        if (!etudiantRepository.existsById(id)) {
            throw new EntityNotFoundException("Étudiant non trouvé");
        }
        etudiantRepository.deleteById(id);
    }

    @Override
    public Etudiant getEtudiantById(Long id) {
        return etudiantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Étudiant non trouvé"));
    }

    @Override
    public Etudiant getEtudiantByMatricule(String matricule) {
        return etudiantRepository.findByMatricule(matricule)
                .orElseThrow(() -> new EntityNotFoundException("Étudiant non trouvé"));
    }

    @Override
    public List<Etudiant> getAllEtudiants() {
        return etudiantRepository.findAll();
    }

    @Override
    public Page<Etudiant> getAllEtudiantsPagines(int page, int size) {
        return etudiantRepository.findAll(
                PageRequest.of(page, size, Sort.by("nom").ascending())
        );
    }

    @Override
    public List<Etudiant> getEtudiantsByClasse(Long classeId) {
        return etudiantRepository.findByClasseId(classeId);
    }

    @Override
    public List<Etudiant> searchEtudiants(String keyword) {
        return etudiantRepository.findByNomContainingOrPrenomContainingOrMatriculeContaining(
                keyword, keyword, keyword);
    }
}