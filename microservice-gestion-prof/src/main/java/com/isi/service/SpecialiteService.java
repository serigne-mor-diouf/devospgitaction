package com.isi.service;

import com.isi.dto.SpecialiteDTO;
import com.isi.model.Specialite;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SpecialiteService {
    Specialite createSpecialite(SpecialiteDTO specialiteDTO);
    Specialite updateSpecialite(Long id, SpecialiteDTO specialiteDTO);
    void deleteSpecialite(Long id);
    Specialite getSpecialiteById(Long id);
    List<Specialite> getAllSpecialites();
    Page<Specialite> getAllSpecialitesPagines(int page, int size);

}
