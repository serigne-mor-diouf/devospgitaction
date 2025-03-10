package com.isi.controller;

import com.isi.dto.CoursDto;
import com.isi.model.Cours;
import com.isi.service.CoursService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cours")
@CrossOrigin("*")
@Api(tags = "microservice Gestion des Cours")
public class CoursController {

    private final CoursService coursService;

    public CoursController(CoursService coursService) {
        this.coursService = coursService;
    }

    @PostMapping
    @ApiOperation("Créer un nouveau cours")
    public ResponseEntity<Cours> createCours(@RequestBody CoursDto cours) {
        return new ResponseEntity<>(coursService.saveCours(cours), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation("Mettre à jour un cours")
    public ResponseEntity<Cours> updateCours(@PathVariable Long id, @RequestBody CoursDto cours) {
        return ResponseEntity.ok(coursService.updateCours(id, cours));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Supprimer un cours")
    public ResponseEntity<Void> deleteCours(@PathVariable Long id) {
        coursService.deleteCours(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @ApiOperation("Obtenir un cours par son ID")
    public ResponseEntity<Cours> getCours(@PathVariable Long id) {
        return ResponseEntity.ok(coursService.getCoursById(id));
    }

    @GetMapping
    @ApiOperation("Obtenir tous les cours")
    public ResponseEntity<List<Cours>> getAllCours() {
        return ResponseEntity.ok(coursService.getAllCours());
    }

    @GetMapping("/pagines")
    @ApiOperation("Obtenir tous les cours avec pagination")
    public ResponseEntity<Page<Cours>> getAllCoursPagines(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(coursService.getAllCoursPagines(page, size));
    }

    @GetMapping("/professeur/{professeurId}")
    @ApiOperation("Obtenir les cours par professeur")
    public ResponseEntity<List<Cours>> getCoursByProfesseur(@PathVariable Long professeurId) {
        return ResponseEntity.ok(coursService.getCoursByProfesseur(professeurId));
    }

    @GetMapping("/classe/{classeId}")
    @ApiOperation("Obtenir les cours par classe")
    public ResponseEntity<List<Cours>> getCoursByClasse(@PathVariable Long classeId) {
        return ResponseEntity.ok(coursService.getCoursByClasse(classeId));
    }

    @GetMapping("/code/{code}")
    @ApiOperation("Obtenir un cours par son code")
    public ResponseEntity<Cours> getCoursByCode(@PathVariable String code) {
        return coursService.findByCode(code)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
} 