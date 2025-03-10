package com.isi.controller;

import com.isi.dto.InscriptionDTO;
import com.isi.model.Inscription;
import com.isi.service.InscriptionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inscriptions")
@Api(tags = "Gestion des etuiants")
@CrossOrigin("*")
public class InscriptionController {


    private final InscriptionService inscriptionService;

    public InscriptionController(InscriptionService inscriptionService) {
        this.inscriptionService = inscriptionService;
    }

    @PostMapping
    @ApiOperation("Créer une nouvelle inscription")
    public ResponseEntity<Inscription> createInscription(@RequestBody InscriptionDTO inscriptionDTO) {
        return new ResponseEntity<>(inscriptionService.createInscription(inscriptionDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation("Mettre à jour une inscription")
    public ResponseEntity<Inscription> updateInscription(@PathVariable Long id, @RequestBody InscriptionDTO inscriptionDTO) {
        return ResponseEntity.ok(inscriptionService.updateInscription(id, inscriptionDTO));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Supprimer une inscription")
    public ResponseEntity<Void> deleteInscription(@PathVariable Long id) {
        inscriptionService.deleteInscription(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @ApiOperation("Obtenir une inscription par son ID")
    public ResponseEntity<Inscription> getInscription(@PathVariable Long id) {
        return ResponseEntity.ok(inscriptionService.getInscriptionById(id));
    }

    @GetMapping("/etudiant/{etudiantId}")
    @ApiOperation("Obtenir les inscriptions d'un étudiant")
    public ResponseEntity<List<Inscription>> getInscriptionsByEtudiant(@PathVariable Long etudiantId) {
        return ResponseEntity.ok(inscriptionService.getInscriptionsByEtudiant(etudiantId));
    }
//
//    @GetMapping("/annee-academique/{anneeId}")
//    @ApiOperation("Obtenir les inscriptions d'une année académique")
//    public ResponseEntity<List<Inscription>> getInscriptionsByAnneeAcademique(@PathVariable Long anneeId) {
//        return ResponseEntity.ok(inscriptionService.getInscriptionsByAnneeAcademique(anneeId));
//    }


    @GetMapping("/paginees")
    @ApiOperation("Obtenir toutes les inscriptions avec pagination")
    public ResponseEntity<Page<Inscription>> getAllInscriptionsPaginees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(inscriptionService.getAllInscriptionsPaginees(page, size));
    }

    @GetMapping("/solde")
    @ApiOperation("Calculer le solde d'un étudiant pour une année académique")
    public ResponseEntity<Double> calculateSolde(
            @RequestParam Long etudiantId,
            @RequestParam Long anneeAcademiqueId) {
        return ResponseEntity.ok(inscriptionService.calculateSolde(etudiantId, anneeAcademiqueId));
    }
}