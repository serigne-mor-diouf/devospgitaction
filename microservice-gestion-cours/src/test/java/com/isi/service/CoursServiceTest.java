package com.isi.service;

import com.isi.dto.CoursDto;
import com.isi.model.Cours;
import com.isi.model.Professeur;
import com.isi.model.Classe;
import com.isi.repository.CoursRepository;
import com.isi.repository.ProfesseurRepository;
import com.isi.repository.ClasseRepository;
import com.isi.service.impl.CoursServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CoursServiceTest {

    @Mock
    private CoursRepository coursRepository;

    @Mock
    private ProfesseurRepository professeurRepository;

    @Mock
    private ClasseRepository classeRepository;

    @InjectMocks
    private CoursServiceImpl coursService;

    private Cours cours;
    private CoursDto coursDTO;
    private Professeur professeur;
    private Classe classe;

    @BeforeEach
    void setUp() {
        // Configuration du professeur
        professeur = new Professeur();
        professeur.setId(1L);

        // Configuration de la classe
        classe = new Classe();
        classe.setId(1L);

        // Configuration du DTO
        coursDTO = new CoursDto();
        coursDTO.setCode("COURS-001");
        coursDTO.setNom("Java Programming");
        coursDTO.setDescription("Introduction to Java");
        coursDTO.setVolumeHoraire(30);
        coursDTO.setCoefficient(2);
        coursDTO.setProfesseurId(1L);
        coursDTO.setClasseId(1L);
        coursDTO.setDateDebut("2025-03-13");
        coursDTO.setDateFin("2025-06-13");

        // Configuration du cours
        cours = new Cours();
        cours.setId(1L);
        cours.setCode("COURS-001");
        cours.setNom("Java Programming");
        cours.setDescription("Introduction to Java");
        cours.setVolumeHoraire(30);
        cours.setCoefficient(2);
        cours.setProfesseur(professeur);
        cours.setClasse(classe);
        cours.setDateDebut(LocalDate.parse("2025-03-13"));
        cours.setDateFin(LocalDate.parse("2025-06-13"));
    }

    @Test
    void saveCours_Success() {
        // Given
        when(coursRepository.existsByCode(anyString())).thenReturn(false);
        when(coursRepository.save(any(Cours.class))).thenReturn(cours);

        // When
        Cours result = coursService.saveCours(coursDTO);

        // Then
        assertNotNull(result);
        assertEquals("COURS-001", result.getCode());
        verify(coursRepository).save(any(Cours.class));
    }

    @Test
    void saveCours_DuplicateCode() {
        // Given
        when(coursRepository.existsByCode("COURS-001")).thenReturn(true);

        // When & Then
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> coursService.saveCours(coursDTO)
        );
        assertEquals("Un cours avec ce code existe déjà", exception.getMessage());
    }

    @Test
    void updateCours_Success() {
        // Given
        when(coursRepository.findById(1L)).thenReturn(Optional.of(cours));
        when(professeurRepository.findById(1L)).thenReturn(Optional.of(professeur));
        when(classeRepository.findById(1L)).thenReturn(Optional.of(classe));
        when(coursRepository.save(any(Cours.class))).thenReturn(cours);

        // When
        Cours result = coursService.updateCours(1L, coursDTO);

        // Then
        assertNotNull(result);
        assertEquals(coursDTO.getCode(), result.getCode());
        verify(coursRepository).save(any(Cours.class));
    }

    @Test
    void deleteCours_Success() {
        // Given
        when(coursRepository.findById(1L)).thenReturn(Optional.of(cours));

        // When
        coursService.deleteCours(1L);

        // Then
        verify(coursRepository).deleteById(1L);
    }

    @Test
    void deleteCours_NotFound() {
        // Given
        when(coursRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        EntityNotFoundException exception = assertThrows(
            EntityNotFoundException.class,
            () -> coursService.deleteCours(1L)
        );
        assertEquals("Cours non trouvé avec l'id: 1", exception.getMessage());
    }

    @Test
    void getCoursByProfesseur_Success() {
        // Given
        List<Cours> coursList = new ArrayList<>();
        coursList.add(cours);
        when(coursRepository.findByProfesseur_Id(anyLong())).thenReturn(coursList);

        // When
        List<Cours> result = coursService.getCoursByProfesseur(1L);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("COURS-001", result.get(0).getCode());
    }

    @Test
    void getCoursByClasse_Success() {
        // Given
        List<Cours> coursList = new ArrayList<>();
        coursList.add(cours);
        when(coursRepository.findByClasse_Id(anyLong())).thenReturn(coursList);

        // When
        List<Cours> result = coursService.getCoursByClasse(1L);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("COURS-001", result.get(0).getCode());
    }

    @Test
    void getAllCoursPagines_Success() {
        // Given
        List<Cours> coursList = Arrays.asList(cours);
        Page<Cours> coursPage = new PageImpl<>(coursList);
        when(coursRepository.findAll(any(PageRequest.class))).thenReturn(coursPage);

        // When
        Page<Cours> result = coursService.getAllCoursPagines(0, 10);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("COURS-001", result.getContent().get(0).getCode());
    }
} 