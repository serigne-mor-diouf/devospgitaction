package com.isi.service.impl;

import com.isi.dto.DepartementDTO;
import com.isi.model.Departement;
import com.isi.repository.DepartementRepository;
import com.isi.service.DepartementService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class DepartementServiceImpl implements DepartementService {


    private final DepartementRepository departementRepository;

    public DepartementServiceImpl(DepartementRepository departementRepository) {
        this.departementRepository = departementRepository;
    }

    private Departement convertToDepartement(DepartementDTO dto) {
        Departement departement = new Departement();
        departement.setNom(dto.getNom());
        departement.setDescription(dto.getDescription());
        departement.setChefDepartementId(dto.getChefDepartementId());
        return departement;
    }

    @Override
    public Departement createDepartement(DepartementDTO departementDTO) {
        if (departementRepository.existsByNom(departementDTO.getNom())) {
            throw new IllegalArgumentException("Un département avec ce nom existe déjà");
        }
        Departement departement = convertToDepartement(departementDTO);
        return departementRepository.save(departement);
    }

    @Override
    public Departement updateDepartement(Long id, DepartementDTO departementDTO) {
        Departement existingDepartement = departementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Département non trouvé"));

        if (!existingDepartement.getNom().equals(departementDTO.getNom()) &&
                departementRepository.existsByNom(departementDTO.getNom())) {
            throw new IllegalArgumentException("Un département avec ce nom existe déjà");
        }

        if (departementDTO.getNom() != null) existingDepartement.setNom(departementDTO.getNom());
        if (departementDTO.getDescription() != null)
            existingDepartement.setDescription(departementDTO.getDescription());
        if (departementDTO.getChefDepartementId() != null)
            existingDepartement.setChefDepartementId(departementDTO.getChefDepartementId());

        return departementRepository.save(existingDepartement);
    }

    @Override
    public void deleteDepartement(Long id) {
        if (!departementRepository.existsById(id)) {
            throw new EntityNotFoundException("Département non trouvé");
        }
        departementRepository.deleteById(id);
    }

    @Override
    public Departement getDepartementById(Long id) {
        return departementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Département non trouvé"));
    }

    @Override
    public List<Departement> getAllDepartements() {
        return departementRepository.findAll();
    }

    @Override
    public Page<Departement> getAllDepartementsPagines(int page, int size) {
        return departementRepository.findAll(
                PageRequest.of(page, size, Sort.by("nom").ascending())
        );
    }
}