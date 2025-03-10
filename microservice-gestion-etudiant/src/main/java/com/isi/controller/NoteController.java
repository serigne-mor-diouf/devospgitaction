package com.isi.controller;

import com.isi.dto.NoteDTO;
import com.isi.model.Note;
import com.isi.service.NoteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notes")
@Api(tags = " Gestion des Notes")
@CrossOrigin("*")
public class NoteController {


    private  final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    @ApiOperation("Créer une nouvelle note")
    public ResponseEntity<Note> createNote(@RequestBody NoteDTO noteDTO) {
        return new ResponseEntity<>(noteService.createNote(noteDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ApiOperation("Mettre à jour une note")
    public ResponseEntity<Note> updateNote(@PathVariable Long id, @RequestBody NoteDTO noteDTO) {
        return ResponseEntity.ok(noteService.updateNote(id, noteDTO));
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Supprimer une note")
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @ApiOperation("Obtenir une note par son ID")
    public ResponseEntity<Note> getNote(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.getNoteById(id));
    }

    @GetMapping("/etudiant/{etudiantId}")
    @ApiOperation("Obtenir les notes d'un étudiant")
    public ResponseEntity<List<Note>> getNotesByEtudiant(@PathVariable Long etudiantId) {
        return ResponseEntity.ok(noteService.getNotesByEtudiant(etudiantId));
    }

    @GetMapping("/cours/{coursId}")
    @ApiOperation("Obtenir les notes d'un cours")
    public ResponseEntity<List<Note>> getNotesByCours(@PathVariable Long coursId) {
        return ResponseEntity.ok(noteService.getNotesByCours(coursId));
    }

    @GetMapping("/etudiant/{etudiantId}/semestre/{semestre}")
    @ApiOperation("Obtenir les notes d'un étudiant par semestre")
    public ResponseEntity<List<Note>> getNotesByEtudiantAndSemestre(
            @PathVariable Long etudiantId,
            @PathVariable String semestre) {
        return ResponseEntity.ok(noteService.getNotesByEtudiantAndSemestre(etudiantId, semestre));
    }

    @GetMapping("/moyenne")
    @ApiOperation("Calculer la moyenne d'un étudiant pour un semestre")
    public ResponseEntity<Double> calculateMoyenne(
            @RequestParam Long etudiantId,
            @RequestParam String semestre) {
        return ResponseEntity.ok(noteService.calculateMoyenne(etudiantId, semestre));
    }

    @GetMapping("/paginees")
    @ApiOperation("Obtenir toutes les notes avec pagination")
    public ResponseEntity<Page<Note>> getAllNotesPaginees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(noteService.getAllNotesPaginees(page, size));
    }
}