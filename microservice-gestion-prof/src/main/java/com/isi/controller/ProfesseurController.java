package com.isi.controller;

import com.isi.dto.ProfesseurDTO;
import com.isi.model.Professeur;
import com.isi.service.ProfesseurService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professeurs")
@Api(tags = "microservice Gestion des Professeurs")
@CrossOrigin("*")
public class ProfesseurController {

    private final ProfesseurService professeurService;

    public ProfesseurController(ProfesseurService professeurService) {
        this.professeurService = professeurService;
    }

    @PostMapping
    @ApiOperation("Créer un nouveau professeur")
    public ResponseEntity<Professeur> createProfesseur(@RequestBody ProfesseurDTO professeurDTO) {
        return new ResponseEntity<>(professeurService.createProfesseur(professeurDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation("Mettre à jour un professeur")
    public ResponseEntity<Professeur> updateProfesseur(@PathVariable Long id, @RequestBody ProfesseurDTO professeurDTO) {
        return ResponseEntity.ok(professeurService.updateProfesseur(id, professeurDTO));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Supprimer un professeur")
    public ResponseEntity<Void> deleteProfesseur(@PathVariable Long id) {
        professeurService.deleteProfesseur(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @ApiOperation("Obtenir un professeur par son ID")
    public ResponseEntity<Professeur> getProfesseur(@PathVariable Long id) {
        return ResponseEntity.ok(professeurService.getProfesseurById(id));
    }

    @GetMapping
    @ApiOperation("Obtenir tous les professeurs")
    public ResponseEntity<List<Professeur>> getAllProfesseurs() {
        return ResponseEntity.ok(professeurService.getAllProfesseurs());
    }

    @GetMapping("/pagines")
    @ApiOperation("Obtenir tous les professeurs avec pagination")
    public ResponseEntity<Page<Professeur>> getAllProfesseursPagines(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(professeurService.getAllProfesseursPagines(page, size));
    }

    @GetMapping("/departement/{departementId}")
    @ApiOperation("Obtenir les professeurs par département")
    public ResponseEntity<List<Professeur>> getProfesseursByDepartement(@PathVariable Long departementId) {
        return ResponseEntity.ok(professeurService.getProfesseursByDepartement(departementId));
    }

    @GetMapping("/specialite/{specialiteId}")
    @ApiOperation("Obtenir les professeurs par spécialité")
    public ResponseEntity<List<Professeur>> getProfesseursBySpecialite(@PathVariable Long specialiteId) {
        return ResponseEntity.ok(professeurService.getProfesseursBySpecialite(specialiteId));
    }
}