package com.isi.service;

import com.isi.dto.ClasseDTO;
import com.isi.model.Classe;
import com.isi.repository.ClasseRepository;
import com.isi.service.impl.ClasseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ClasseServiceImplTest {

    @Mock
    private ClasseRepository classeRepository;

    @InjectMocks
    private ClasseServiceImpl classeService;

    private ClasseDTO classeDTO;
    private Classe classe;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialisation des données de test
        classeDTO = new ClasseDTO();
        classeDTO.setCode("L1-INFO");
        classeDTO.setNom("Licence 1 Informatique");
        classeDTO.setNiveau("Licence 1");
        classeDTO.setFiliere("Informatique");
        classeDTO.setCapaciteMax(50);
        classeDTO.setAnneeScolaire("2023-2024");
        classeDTO.setResponsableId(1L);
        classeDTO.setDescription("Classe de première année");

        classe = new Classe();
        classe.setId(1L);
        classe.setCode("L1-INFO");
        classe.setNom("Licence 1 Informatique");
        classe.setNiveau("Licence 1");
        classe.setFiliere("Informatique");
        classe.setCapaciteMax(50);
        classe.setAnneeScolaire("2023-2024");
        classe.setResponsableId(1L);
        classe.setDescription("Classe de première année");
    }

    @Test
    void createClasse_Success() {
        when(classeRepository.existsByCode(anyString())).thenReturn(false);
        when(classeRepository.save(any(Classe.class))).thenReturn(classe);

        Classe result = classeService.createClasse(classeDTO);

        assertNotNull(result);
        assertEquals("L1-INFO", result.getCode());
        assertEquals("Licence 1 Informatique", result.getNom());
        verify(classeRepository).save(any(Classe.class));
    }

    @Test
    void createClasse_DuplicateCode() {
        when(classeRepository.existsByCode(anyString())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            classeService.createClasse(classeDTO);
        });
    }

    @Test
    void getClasseById_Success() {
        when(classeRepository.findById(1L)).thenReturn(Optional.of(classe));

        Classe result = classeService.getClasseById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("L1-INFO", result.getCode());
    }

    @Test
    void getClasseById_NotFound() {
        when(classeRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            classeService.getClasseById(1L);
        });
    }

    @Test
    void getAllClasses_Success() {
        List<Classe> classes = Arrays.asList(classe);
        when(classeRepository.findAll()).thenReturn(classes);

        List<Classe> result = classeService.getAllClasses();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("L1-INFO", result.get(0).getCode());
    }
} 