package com.isi.service.impl;

import com.isi.dto.SpecialiteDTO;
import com.isi.model.Specialite;
import com.isi.repository.SpecialiteRepository;
import com.isi.service.SpecialiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class SpecialiteServiceImpl implements SpecialiteService {

    private final SpecialiteRepository specialiteRepository;

    public SpecialiteServiceImpl(SpecialiteRepository specialiteRepository) {
        this.specialiteRepository = specialiteRepository;
    }

    private Specialite convertToSpecialite(SpecialiteDTO dto) {
        Specialite specialite = new Specialite();
        specialite.setNom(dto.getNom());
        specialite.setDescription(dto.getDescription());
        return specialite;
    }

    @Override
    public Specialite createSpecialite(SpecialiteDTO specialiteDTO) {
        if (specialiteRepository.existsByNom(specialiteDTO.getNom())) {
            throw new IllegalArgumentException("Une spécialité avec ce nom existe déjà");
        }
        Specialite specialite = convertToSpecialite(specialiteDTO);
        return specialiteRepository.save(specialite);
    }

    @Override
    public Specialite updateSpecialite(Long id, SpecialiteDTO specialiteDTO) {
        Specialite existingSpecialite = specialiteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Spécialité non trouvée"));

        if (!existingSpecialite.getNom().equals(specialiteDTO.getNom()) &&
                specialiteRepository.existsByNom(specialiteDTO.getNom())) {
            throw new IllegalArgumentException("Une spécialité avec ce nom existe déjà");
        }

        if (specialiteDTO.getNom() != null) existingSpecialite.setNom(specialiteDTO.getNom());
        if (specialiteDTO.getDescription() != null) existingSpecialite.setDescription(specialiteDTO.getDescription());

        return specialiteRepository.save(existingSpecialite);
    }

    @Override
    public void deleteSpecialite(Long id) {
        if (!specialiteRepository.existsById(id)) {
            throw new EntityNotFoundException("Spécialité non trouvée");
        }
        specialiteRepository.deleteById(id);
    }

    @Override
    public Specialite getSpecialiteById(Long id) {
        return specialiteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Spécialité non trouvée"));
    }

    @Override
    public List<Specialite> getAllSpecialites() {
        return specialiteRepository.findAll();
    }

    @Override
    public Page<Specialite> getAllSpecialitesPagines(int page, int size) {
        return specialiteRepository.findAll(
                PageRequest.of(page, size, Sort.by("nom").ascending())
        );
    }
}