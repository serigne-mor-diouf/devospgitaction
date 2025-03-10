package com.isi.controller;

import com.isi.dto.DepartementDTO;
import com.isi.model.Departement;
import com.isi.service.DepartementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departements")
@Api(tags = "microservice Gestion des Départements")
@CrossOrigin("*")
public class DepartementController {

    private final DepartementService departementService;

    public DepartementController(DepartementService departementService) {
        this.departementService = departementService;
    }

    @PostMapping
    @ApiOperation("Créer un nouveau département")
    public ResponseEntity<Departement> createDepartement(@RequestBody DepartementDTO departementDTO) {
        return new ResponseEntity<>(departementService.createDepartement(departementDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation("Mettre à jour un département")
    public ResponseEntity<Departement> updateDepartement(@PathVariable Long id, @RequestBody DepartementDTO departementDTO) {
        return ResponseEntity.ok(departementService.updateDepartement(id, departementDTO));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Supprimer un département")
    public ResponseEntity<Void> deleteDepartement(@PathVariable Long id) {
        departementService.deleteDepartement(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @ApiOperation("Obtenir un département par son ID")
    public ResponseEntity<Departement> getDepartement(@PathVariable Long id) {
        return ResponseEntity.ok(departementService.getDepartementById(id));
    }

    @GetMapping
    @ApiOperation("Obtenir tous les départements")
    public ResponseEntity<List<Departement>> getAllDepartements() {
        return ResponseEntity.ok(departementService.getAllDepartements());
    }

    @GetMapping("/pagines")
    @ApiOperation("Obtenir tous les départements avec pagination")
    public ResponseEntity<Page<Departement>> getAllDepartementsPagines(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(departementService.getAllDepartementsPagines(page, size));
    }
}