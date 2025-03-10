package com.isi.controller;

import com.isi.dto.SpecialiteDTO;
import com.isi.model.Specialite;
import com.isi.service.SpecialiteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specialites")
@Api(tags = "microservice Gestion des Spécialités")
@CrossOrigin("*")
public class SpecialiteController {


    private final SpecialiteService specialiteService;

    public SpecialiteController(SpecialiteService specialiteService) {
        this.specialiteService = specialiteService;
    }

    @PostMapping
    @ApiOperation("Créer une nouvelle spécialité")
    public ResponseEntity<Specialite> createSpecialite(@RequestBody SpecialiteDTO specialiteDTO) {
        return new ResponseEntity<>(specialiteService.createSpecialite(specialiteDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation("Mettre à jour une spécialité")
    public ResponseEntity<Specialite> updateSpecialite(@PathVariable Long id, @RequestBody SpecialiteDTO specialiteDTO) {
        return ResponseEntity.ok(specialiteService.updateSpecialite(id, specialiteDTO));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Supprimer une spécialité")
    public ResponseEntity<Void> deleteSpecialite(@PathVariable Long id) {
        specialiteService.deleteSpecialite(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @ApiOperation("Obtenir une spécialité par son ID")
    public ResponseEntity<Specialite> getSpecialite(@PathVariable Long id) {
        return ResponseEntity.ok(specialiteService.getSpecialiteById(id));
    }

    @GetMapping
    @ApiOperation("Obtenir toutes les spécialités")
    public ResponseEntity<List<Specialite>> getAllSpecialites() {
        return ResponseEntity.ok(specialiteService.getAllSpecialites());
    }

    @GetMapping("/pagines")
    @ApiOperation("Obtenir toutes les spécialités avec pagination")
    public ResponseEntity<Page<Specialite>> getAllSpecialitesPagines(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(specialiteService.getAllSpecialitesPagines(page, size));
    }
}