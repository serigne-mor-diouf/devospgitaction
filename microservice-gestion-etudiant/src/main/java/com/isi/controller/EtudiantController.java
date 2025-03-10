package com.isi.controller;

import com.isi.dto.EtudiantDTO;
import com.isi.model.Etudiant;
import com.isi.service.EtudiantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/etudiants")
@Api(tags = "Gestion des Étudiants")
@CrossOrigin("*")
public class EtudiantController {


    private final EtudiantService etudiantService;

    public EtudiantController(EtudiantService etudiantService) {
        this.etudiantService = etudiantService;
    }

    @PostMapping
    @ApiOperation("Créer un nouvel étudiant")
    public ResponseEntity<Etudiant> createEtudiant(@RequestBody EtudiantDTO etudiantDTO) {
        return new ResponseEntity<>(etudiantService.createEtudiant(etudiantDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation("Mettre à jour un étudiant")
    public ResponseEntity<Etudiant> updateEtudiant(@PathVariable Long id, @RequestBody EtudiantDTO etudiantDTO) {
        return ResponseEntity.ok(etudiantService.updateEtudiant(id, etudiantDTO));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Supprimer un étudiant")
    public ResponseEntity<Void> deleteEtudiant(@PathVariable Long id) {
        etudiantService.deleteEtudiant(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @ApiOperation("Obtenir un étudiant par son ID")
    public ResponseEntity<Etudiant> getEtudiant(@PathVariable Long id) {
        return ResponseEntity.ok(etudiantService.getEtudiantById(id));
    }

    @GetMapping("/matricule/{matricule}")
    @ApiOperation("Obtenir un étudiant par son matricule")
    public ResponseEntity<Etudiant> getEtudiantByMatricule(@PathVariable String matricule) {
        return ResponseEntity.ok(etudiantService.getEtudiantByMatricule(matricule));
    }

    @GetMapping
    @ApiOperation("Obtenir tous les étudiants")
    public ResponseEntity<List<Etudiant>> getAllEtudiants() {
        return ResponseEntity.ok(etudiantService.getAllEtudiants());
    }

    @GetMapping("/pagines")
    @ApiOperation("Obtenir tous les étudiants avec pagination")
    public ResponseEntity<Page<Etudiant>> getAllEtudiantsPagines(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(etudiantService.getAllEtudiantsPagines(page, size));
    }

    @GetMapping("/classe/{classeId}")
    @ApiOperation("Obtenir les étudiants d'une classe")
    public ResponseEntity<List<Etudiant>> getEtudiantsByClasse(@PathVariable Long classeId) {
        return ResponseEntity.ok(etudiantService.getEtudiantsByClasse(classeId));
    }

    @GetMapping("/search")
    @ApiOperation("Rechercher des étudiants")
    public ResponseEntity<List<Etudiant>> searchEtudiants(@RequestParam String keyword) {
        return ResponseEntity.ok(etudiantService.searchEtudiants(keyword));
    }
}