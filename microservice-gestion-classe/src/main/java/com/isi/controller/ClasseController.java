package com.isi.controller;

import com.isi.dto.ClasseDTO;
import com.isi.model.Classe;
import com.isi.service.ClasseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/classes")
@Api(tags = "microservice Gestion des Classes")
@CrossOrigin("*")
public class ClasseController {

    private final ClasseService classeService;

    public ClasseController(ClasseService classeService) {
        this.classeService = classeService;
    }

    @PostMapping
    @ApiOperation("Créer une nouvelle classe")
    public ResponseEntity<Classe> createClasse(@Valid @RequestBody ClasseDTO classeDTO) {
        return new ResponseEntity<>(classeService.createClasse(classeDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation("Mettre à jour une classe")
    public ResponseEntity<Classe> updateClasse(
            @PathVariable Long id,
            @Valid @RequestBody ClasseDTO classeDTO) {
        return ResponseEntity.ok(classeService.updateClasse(id, classeDTO));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Supprimer une classe")
    public ResponseEntity<Void> deleteClasse(@PathVariable Long id) {
        classeService.deleteClasse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @ApiOperation("Obtenir une classe par son ID")
    public ResponseEntity<Classe> getClasse(@PathVariable Long id) {
        return ResponseEntity.ok(classeService.getClasseById(id));
    }

    @GetMapping("/code/{code}")
    @ApiOperation("Obtenir une classe par son code")
    public ResponseEntity<Classe> getClasseByCode(@PathVariable String code) {
        return ResponseEntity.ok(classeService.getClasseByCode(code));
    }

    @GetMapping
    @ApiOperation("Obtenir toutes les classes")
    public ResponseEntity<List<Classe>> getAllClasses() {
        return ResponseEntity.ok(classeService.getAllClasses());
    }

    @GetMapping("/paginees")
    @ApiOperation("Obtenir toutes les classes avec pagination")
    public ResponseEntity<Page<Classe>> getAllClassesPaginees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(classeService.getAllClassesPaginees(page, size));
    }

    @GetMapping("/niveau/{niveau}")
    @ApiOperation("Obtenir les classes par niveau")
    public ResponseEntity<List<Classe>> getClassesByNiveau(@PathVariable String niveau) {
        return ResponseEntity.ok(classeService.getClassesByNiveau(niveau));
    }

    @GetMapping("/filiere/{filiere}")
    @ApiOperation("Obtenir les classes par filière")
    public ResponseEntity<List<Classe>> getClassesByFiliere(@PathVariable String filiere) {
        return ResponseEntity.ok(classeService.getClassesByFiliere(filiere));
    }

    @GetMapping("/annee-scolaire/{anneeScolaire}")
    @ApiOperation("Obtenir les classes par année scolaire")
    public ResponseEntity<List<Classe>> getClassesByAnneeScolaire(
            @PathVariable String anneeScolaire) {
        return ResponseEntity.ok(classeService.getClassesByAnneeScolaire(anneeScolaire));
    }

    @GetMapping("/responsable/{responsableId}")
    @ApiOperation("Obtenir les classes par responsable")
    public ResponseEntity<List<Classe>> getClassesByResponsable(@PathVariable Long responsableId) {
        return ResponseEntity.ok(classeService.getClassesByResponsable(responsableId));
    }
}