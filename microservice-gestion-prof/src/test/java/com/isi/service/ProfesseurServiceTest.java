package com.isi.service;

import com.isi.dto.ProfesseurDTO;
import com.isi.model.Departement;
import com.isi.model.Professeur;
import com.isi.model.Specialite;
import com.isi.repository.DepartementRepository;
import com.isi.repository.ProfesseurRepository;
import com.isi.repository.SpecialiteRepository;
import com.isi.service.impl.ProfesseurServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfesseurServiceTest {

    @Mock
    private ProfesseurRepository professeurRepository;

    @Mock
    private DepartementRepository departementRepository;

    @Mock
    private SpecialiteRepository specialiteRepository;

    @InjectMocks
    private ProfesseurServiceImpl professeurService;

    private ProfesseurDTO professeurDTO;
    private Professeur professeur;
    private Departement departement;
    private Specialite specialite;

    @BeforeEach
    void setUp() {
        // Initialisation du département
        departement = new Departement();
        departement.setId(1L);
        departement.setNom("Informatique");

        // Initialisation de la spécialité
        specialite = new Specialite();
        specialite.setId(1L);
        specialite.setNom("Java");

        // Initialisation du DTO
        professeurDTO = new ProfesseurDTO();
        professeurDTO.setNom("Doe");
        professeurDTO.setPrenom("John");
        professeurDTO.setEmail("john.doe@isi.com");
        professeurDTO.setTelephone("123456789");
        professeurDTO.setGrade("Docteur");
        professeurDTO.setDepartementId(1L);
        professeurDTO.setSpecialiteIds(Arrays.asList(1L));

        // Initialisation du professeur
        professeur = new Professeur();
        professeur.setId(1L);
        professeur.setNom("Doe");
        professeur.setPrenom("John");
        professeur.setEmail("john.doe@isi.com");
        professeur.setTelephone("123456789");
        professeur.setGrade("Docteur");
        professeur.setDepartement(departement);
        professeur.setSpecialites(Arrays.asList(specialite));
    }

    @Test
    void createProfesseur_Success() {
        when(professeurRepository.existsByEmail(anyString())).thenReturn(false);
        when(departementRepository.findById(anyLong())).thenReturn(Optional.of(departement));
        when(specialiteRepository.findById(anyLong())).thenReturn(Optional.of(specialite));
        when(professeurRepository.save(any(Professeur.class))).thenReturn(professeur);

        Professeur result = professeurService.createProfesseur(professeurDTO);

        assertNotNull(result);
        assertEquals("Doe", result.getNom());
        assertEquals("john.doe@isi.com", result.getEmail());
        verify(professeurRepository).save(any(Professeur.class));
    }

    @Test
    void createProfesseur_DuplicateEmail() {
        when(professeurRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> 
            professeurService.createProfesseur(professeurDTO)
        );
        verify(professeurRepository, never()).save(any(Professeur.class));
    }

    @Test
    void updateProfesseur_Success() {
        when(professeurRepository.findById(anyLong())).thenReturn(Optional.of(professeur));
        when(departementRepository.findById(anyLong())).thenReturn(Optional.of(departement));
        when(specialiteRepository.findById(anyLong())).thenReturn(Optional.of(specialite));
        when(professeurRepository.save(any(Professeur.class))).thenReturn(professeur);

        Professeur result = professeurService.updateProfesseur(1L, professeurDTO);

        assertNotNull(result);
        assertEquals(professeurDTO.getNom(), result.getNom());
        assertEquals(professeurDTO.getEmail(), result.getEmail());
        verify(professeurRepository).save(any(Professeur.class));
    }

    @Test
    void getProfesseurById_Success() {
        when(professeurRepository.findById(anyLong())).thenReturn(Optional.of(professeur));

        Professeur result = professeurService.getProfesseurById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Doe", result.getNom());
    }

    @Test
    void getProfesseurById_NotFound() {
        when(professeurRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> 
            professeurService.getProfesseurById(1L)
        );
    }

    @Test
    void getAllProfesseursPagines_Success() {
        List<Professeur> professeurs = Arrays.asList(professeur);
        Page<Professeur> professeurPage = new PageImpl<>(professeurs);
        when(professeurRepository.findAll(any(PageRequest.class))).thenReturn(professeurPage);

        Page<Professeur> result = professeurService.getAllProfesseursPagines(0, 10);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Doe", result.getContent().get(0).getNom());
    }

    @Test
    void getProfesseursByDepartement_Success() {
        List<Professeur> professeurs = Arrays.asList(professeur);
        when(professeurRepository.findByDepartementId(anyLong())).thenReturn(professeurs);

        List<Professeur> result = professeurService.getProfesseursByDepartement(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Doe", result.get(0).getNom());
    }

    @Test
    void getProfesseursBySpecialite_Success() {
        List<Professeur> professeurs = Arrays.asList(professeur);
        when(professeurRepository.findBySpecialitesId(anyLong())).thenReturn(professeurs);

        List<Professeur> result = professeurService.getProfesseursBySpecialite(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Doe", result.get(0).getNom());
    }

    @Test
    void deleteProfesseur_Success() {
        when(professeurRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(professeurRepository).deleteById(anyLong());

        professeurService.deleteProfesseur(1L);

        verify(professeurRepository).deleteById(1L);
    }

    @Test
    void deleteProfesseur_NotFound() {
        when(professeurRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> 
            professeurService.deleteProfesseur(1L)
        );
        verify(professeurRepository, never()).deleteById(anyLong());
    }
} 