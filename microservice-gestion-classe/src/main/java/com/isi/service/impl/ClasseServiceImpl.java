package com.isi.service.impl;

import com.isi.dto.ClasseDTO;
import com.isi.model.Classe;
import com.isi.repository.ClasseRepository;
import com.isi.service.ClasseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ClasseServiceImpl implements ClasseService {

    private final ClasseRepository classeRepository;

    public ClasseServiceImpl(ClasseRepository classeRepository) {
        this.classeRepository = classeRepository;
    }

    private Classe convertToEntity(ClasseDTO dto) {
        Classe classe = new Classe();
        classe.setCode(dto.getCode());
        classe.setNom(dto.getNom());
        classe.setNiveau(dto.getNiveau());
        classe.setFiliere(dto.getFiliere());
        classe.setCapaciteMax(dto.getCapaciteMax());
        classe.setAnneeScolaire(dto.getAnneeScolaire());
        classe.setResponsableId(dto.getResponsableId());
        classe.setDescription(dto.getDescription());
        return classe;
    }

    @Override
    public Classe createClasse(ClasseDTO classeDTO) {
        if (classeRepository.existsByCode(classeDTO.getCode())) {
            throw new IllegalArgumentException("Une classe avec ce code existe déjà");
        }
        Classe classe = convertToEntity(classeDTO);
        return classeRepository.save(classe);
    }

    @Override
    public Classe updateClasse(Long id, ClasseDTO classeDTO) {
        Classe existingClasse = classeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Classe non trouvée"));

        if (!existingClasse.getCode().equals(classeDTO.getCode()) &&
                classeRepository.existsByCode(classeDTO.getCode())) {
            throw new IllegalArgumentException("Une classe avec ce code existe déjà");
        }

        if (classeDTO.getCode() != null) existingClasse.setCode(classeDTO.getCode());
        if (classeDTO.getNom() != null) existingClasse.setNom(classeDTO.getNom());
        if (classeDTO.getNiveau() != null) existingClasse.setNiveau(classeDTO.getNiveau());
        if (classeDTO.getFiliere() != null) existingClasse.setFiliere(classeDTO.getFiliere());
        if (classeDTO.getCapaciteMax() != null) existingClasse.setCapaciteMax(classeDTO.getCapaciteMax());
        if (classeDTO.getAnneeScolaire() != null) existingClasse.setAnneeScolaire(classeDTO.getAnneeScolaire());
        if (classeDTO.getResponsableId() != null) existingClasse.setResponsableId(classeDTO.getResponsableId());
        if (classeDTO.getDescription() != null) existingClasse.setDescription(classeDTO.getDescription());
        return classeRepository.save(existingClasse);
    }

    @Override
    public void deleteClasse(Long id) {
        if (!classeRepository.existsById(id)) {
            throw new EntityNotFoundException("Classe non trouvée");
        }
        classeRepository.deleteById(id);
    }

    @Override
    public Classe getClasseById(Long id) {
        return classeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Classe non trouvée"));
    }

    @Override
    public Classe getClasseByCode(String code) {
        return classeRepository.findByCode(code)
                .orElseThrow(() -> new EntityNotFoundException("Classe non trouvée"));
    }

    @Override
    public List<Classe> getAllClasses() {
        return classeRepository.findAll();
    }

    @Override
    public Page<Classe> getAllClassesPaginees(int page, int size) {
        return classeRepository.findAll(
                PageRequest.of(page, size, Sort.by("nom").ascending())
        );
    }

    @Override
    public List<Classe> getClassesByNiveau(String niveau) {
        return classeRepository.findByNiveau(niveau);
    }

    @Override
    public List<Classe> getClassesByFiliere(String filiere) {
        return classeRepository.findByFiliere(filiere);
    }

    @Override
    public List<Classe> getClassesByAnneeScolaire(String anneeScolaire) {
        return classeRepository.findByAnneeScolaire(anneeScolaire);
    }

    @Override
    public List<Classe> getClassesByResponsable(Long responsableId) {
        return classeRepository.findByResponsableId(responsableId);
    }
}