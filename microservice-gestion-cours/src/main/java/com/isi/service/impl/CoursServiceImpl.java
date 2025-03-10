package com.isi.service.impl;

import com.isi.dto.CoursDto;
import com.isi.model.Classe;
import com.isi.model.Cours;
import com.isi.model.Professeur;
import com.isi.repository.ClasseRepository;
import com.isi.repository.CoursRepository;
import com.isi.repository.ProfesseurRepository;
import com.isi.service.CoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CoursServiceImpl implements CoursService {


    private final CoursRepository coursRepository;

    private final ProfesseurRepository professeurRepository;

    private final ClasseRepository classeRepository ;

    public CoursServiceImpl(CoursRepository coursRepository, ProfesseurRepository professeurRepository, ClasseRepository classeRepository) {
        this.coursRepository = coursRepository;
        this.professeurRepository = professeurRepository;
        this.classeRepository = classeRepository;
    }

    @Override
    public Cours saveCours(CoursDto cours) {
        Cours cour = convertToEntity(cours);
        return coursRepository.save(cour);
    }

    @Override
    public Cours updateCours(Long id, CoursDto coursDTO) {
        // Vérifier si le cours existe
        Cours existingCours = coursRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cours non trouvé avec l'id: " + id));

        // Convertir le DTO vers l'entité et mettre à jour
        updateCoursToDTO(existingCours, coursDTO);

        return coursRepository.save(existingCours);
    }

    private void updateCoursToDTO(Cours cours, CoursDto dto) {
        if (dto.getNom() != null) cours.setNom(dto.getNom());
        if (dto.getDescription() != null) cours.setDescription(dto.getDescription());
        if (dto.getCode() != null) cours.setCode(dto.getCode());
        if (dto.getVolumeHoraire() != null) cours.setVolumeHoraire(dto.getVolumeHoraire());
        if (dto.getCoefficient() != null) cours.setCoefficient(dto.getCoefficient());

        // Conversion et mise à jour des dates
        if (dto.getDateDebut() != null) {
            cours.setDateDebut(LocalDate.parse(dto.getDateDebut()));
        }
        if (dto.getDateFin() != null) {
            cours.setDateFin(LocalDate.parse(dto.getDateFin()));
        }

        // Mise à jour des relations
        if (dto.getProfesseurId() != null) {
            Professeur professeur = professeurRepository.findById(dto.getProfesseurId())
                    .orElseThrow(() -> new EntityNotFoundException("Professeur non trouvé avec l'id: " + dto.getProfesseurId()));
            cours.setProfesseur(professeur);
        }
        if (dto.getClasseId() != null) {
            Classe classe = classeRepository.findById(dto.getClasseId())
                    .orElseThrow(() -> new EntityNotFoundException("Classe non trouvée avec l'id: " + dto.getClasseId()));
            cours.setClasse(classe);
        }
    }

    // Méthode de conversion DTO vers Entité
        private Cours convertToEntity(CoursDto coursDTO) {
        Cours cours = new Cours();
        cours.setNom(coursDTO.getNom());
        cours.setDescription(coursDTO.getDescription());
        cours.setCode(coursDTO.getCode());
        cours.setVolumeHoraire(coursDTO.getVolumeHoraire());
        cours.setCoefficient(coursDTO.getCoefficient());

        if (coursDTO.getDateDebut() != null) {
            cours.setDateDebut(LocalDate.parse(coursDTO.getDateDebut()));
        }
        if (coursDTO.getDateFin() != null) {
            cours.setDateFin(LocalDate.parse(coursDTO.getDateFin()));
        }

        // Récupération du professeur
        if (coursDTO.getProfesseurId() != null) {
            Professeur professeur = professeurRepository.findById(coursDTO.getProfesseurId())
                    .orElseThrow(() -> new EntityNotFoundException("Professeur non trouvé avec l'id: " + coursDTO.getProfesseurId()));
            cours.setProfesseur(professeur);
        }

        // Récupération de la classe
        if (coursDTO.getClasseId() != null) {
            Classe classe = classeRepository.findById(coursDTO.getClasseId())
                    .orElseThrow(() -> new EntityNotFoundException("Classe non trouvée avec l'id: " + coursDTO.getClasseId()));
            cours.setClasse(classe);
        }

        return cours;
    }

        @Override
    public void deleteCours(Long id) {
        if (!coursRepository.existsById(id)) {
            throw new EntityNotFoundException("Cours non trouvé avec l'id: " + id);
        }
        coursRepository.deleteById(id);
    }

    @Override
    public Cours getCoursById(Long id) {
        return coursRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cours non trouvé avec l'id: " + id));
    }

    @Override
    public List<Cours> getAllCours() {
        return coursRepository.findAll();
    }

    @Override
    public Page<Cours> getAllCoursPagines(int page, int size) {
        return coursRepository.findAll(
            PageRequest.of(page, size, Sort.by("dateCreation").descending())
        );
    }

    @Override
    public List<Cours> getCoursByProfesseur(Long professeurId) {
        return coursRepository.findByProfesseurId(professeurId);
    }

    @Override
    public List<Cours> getCoursByClasse(Long classeId) {
        return coursRepository.findByClasseId(classeId);
    }

    @Override
    public Optional<Cours> findByCode(String code) {
        return coursRepository.findByCode(code);
    }

    @Override
    public boolean existsByCode(String code) {
        return coursRepository.existsByCode(code);
    }
} 