package com.isi.service.impl;

import com.isi.dto.ProfesseurDTO;
import com.isi.model.Departement;
import com.isi.model.Professeur;
import com.isi.model.Specialite;
import com.isi.repository.DepartementRepository;
import com.isi.repository.ProfesseurRepository;
import com.isi.repository.SpecialiteRepository;
import com.isi.service.ProfesseurService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

    @Service
    public class ProfesseurServiceImpl implements ProfesseurService {


        private final ProfesseurRepository professeurRepository;

        private final DepartementRepository departementRepository;

        private final SpecialiteRepository specialiteRepository;

        public ProfesseurServiceImpl(ProfesseurRepository professeurRepository, DepartementRepository departementRepository, SpecialiteRepository specialiteRepository) {
            this.professeurRepository = professeurRepository;
            this.departementRepository = departementRepository;
            this.specialiteRepository = specialiteRepository;
        }

        private Professeur convertToProfesseur(ProfesseurDTO dto) {
            Professeur professeur = new Professeur();
            professeur.setNom(dto.getNom());
            professeur.setPrenom(dto.getPrenom());
            professeur.setEmail(dto.getEmail());
            professeur.setTelephone(dto.getTelephone());
            professeur.setGrade(dto.getGrade());

            if (dto.getDepartementId() != null) {
                Departement departement = departementRepository.findById(dto.getDepartementId())
                        .orElseThrow(() -> new EntityNotFoundException("Département non trouvé"));
                professeur.setDepartement(departement);
            }

            if (dto.getSpecialiteIds() != null && !dto.getSpecialiteIds().isEmpty()) {
                List<Specialite> specialites = new ArrayList<>();
                for (Long specialiteId : dto.getSpecialiteIds()) {
                    Specialite specialite = specialiteRepository.findById(specialiteId)
                            .orElseThrow(() -> new EntityNotFoundException("Spécialité non trouvée"));
                    specialites.add(specialite);
                }
                professeur.setSpecialites(specialites);
            }

            return professeur;
        }

        @Override
        public Professeur createProfesseur(ProfesseurDTO professeurDTO) {
            if (professeurRepository.existsByEmail(professeurDTO.getEmail())) {
                throw new IllegalArgumentException("Un professeur avec cet email existe déjà");
            }

            Professeur professeur = convertToProfesseur(professeurDTO);
            return professeurRepository.save(professeur);
        }

        @Override
        public Professeur updateProfesseur(Long id, ProfesseurDTO professeurDTO) {
            Professeur existingProfesseur = professeurRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Professeur non trouvé"));

            if (!existingProfesseur.getEmail().equals(professeurDTO.getEmail()) &&
                    professeurRepository.existsByEmail(professeurDTO.getEmail())) {
                throw new IllegalArgumentException("Un professeur avec cet email existe déjà");
            }

            if (professeurDTO.getNom() != null) existingProfesseur.setNom(professeurDTO.getNom());
            if (professeurDTO.getPrenom() != null) existingProfesseur.setPrenom(professeurDTO.getPrenom());
            if (professeurDTO.getEmail() != null) existingProfesseur.setEmail(professeurDTO.getEmail());
            if (professeurDTO.getTelephone() != null) existingProfesseur.setTelephone(professeurDTO.getTelephone());
            if (professeurDTO.getGrade() != null) existingProfesseur.setGrade(professeurDTO.getGrade());

            if (professeurDTO.getDepartementId() != null) {
                Departement departement = departementRepository.findById(professeurDTO.getDepartementId())
                        .orElseThrow(() -> new EntityNotFoundException("Département non trouvé"));
                existingProfesseur.setDepartement(departement);
            }

            if (professeurDTO.getSpecialiteIds() != null && !professeurDTO.getSpecialiteIds().isEmpty()) {
                List<Specialite> specialites = new ArrayList<>();
                for (Long specialiteId : professeurDTO.getSpecialiteIds()) {
                    Specialite specialite = specialiteRepository.findById(specialiteId)
                            .orElseThrow(() -> new EntityNotFoundException("Spécialité non trouvée"));
                    specialites.add(specialite);
                }
                existingProfesseur.setSpecialites(specialites);
            }

            return professeurRepository.save(existingProfesseur);
        }

        @Override
        public void deleteProfesseur(Long id) {
            if (!professeurRepository.existsById(id)) {
                throw new EntityNotFoundException("Professeur non trouvé");
            }
            professeurRepository.deleteById(id);
        }

        @Override
        public Professeur getProfesseurById(Long id) {
            return professeurRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Professeur non trouvé"));
        }

        @Override
        public List<Professeur> getAllProfesseurs() {
            return professeurRepository.findAll();
        }

        @Override
        public Page<Professeur> getAllProfesseursPagines(int page, int size) {
            return professeurRepository.findAll(
                    PageRequest.of(page, size, Sort.by("nom").ascending())
            );
        }

        @Override
        public List<Professeur> getProfesseursByDepartement(Long departementId) {
            return professeurRepository.findByDepartementId(departementId);
        }

        @Override
        public List<Professeur> getProfesseursBySpecialite(Long specialiteId) {
            return professeurRepository.findBySpecialitesId(specialiteId);
        }
}
