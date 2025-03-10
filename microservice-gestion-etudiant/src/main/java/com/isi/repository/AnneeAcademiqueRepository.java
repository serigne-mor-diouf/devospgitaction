package com.isi.repository;

import com.isi.model.AnneeAcademique;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AnneeAcademiqueRepository extends JpaRepository<AnneeAcademique ,  Long> {

}
