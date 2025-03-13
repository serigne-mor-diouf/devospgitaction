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
    public Cours saveCours(CoursDto coursDTO) {
        if (coursRepository.existsByCode(coursDTO.getCode())) {
            throw new IllegalArgumentException("Un cours avec ce code existe déjà");
        }
        Cours cours = convertToEntity(coursDTO);
        return coursRepository.save(cours);
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
    private Cours convertToEntity(CoursDto dto) {
        Cours cours = new Cours();
        cours.setCode(dto.getCode());
        cours.setNom(dto.getNom());
        cours.setDescription(dto.getDescription());
        cours.setVolumeHoraire(dto.getVolumeHoraire());
        cours.setCoefficient(dto.getCoefficient());
        cours.setDateDebut(LocalDate.parse(dto.getDateDebut()));
        cours.setDateFin(LocalDate.parse(dto.getDateFin()));
        // Les relations seront gérées séparément
        return cours;
    }

    @Override
    public void deleteCours(Long id) {
        Cours cours = coursRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Cours non trouvé avec l'id: " + id));
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
        return coursRepository.findByProfesseur_Id(professeurId);
    }

    @Override
    public List<Cours> getCoursByClasse(Long classeId) {
        return coursRepository.findByClasse_Id(classeId);
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