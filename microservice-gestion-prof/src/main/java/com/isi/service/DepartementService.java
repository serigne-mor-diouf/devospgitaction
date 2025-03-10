package com.isi.service;

import com.isi.dto.DepartementDTO;
import com.isi.model.Departement;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DepartementService {
    Departement createDepartement(DepartementDTO departementDTO);
    Departement updateDepartement(Long id, DepartementDTO departementDTO);
    void deleteDepartement(Long id);
    Departement getDepartementById(Long id);
    List<Departement> getAllDepartements();
    Page<Departement> getAllDepartementsPagines(int page, int size);

}
