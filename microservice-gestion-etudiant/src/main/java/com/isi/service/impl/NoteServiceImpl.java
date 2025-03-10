package com.isi.service.impl;

import com.isi.dto.NoteDTO;
import com.isi.model.Cours;
import com.isi.model.Etudiant;
import com.isi.model.Note;
import com.isi.repository.CoursRepository;
import com.isi.repository.EtudiantRepository;
import com.isi.repository.NoteRepository;
import com.isi.service.NoteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    private final EtudiantRepository etudiantRepository;

    private final CoursRepository coursRepository;

    public NoteServiceImpl(NoteRepository noteRepository, EtudiantRepository etudiantRepository, CoursRepository coursRepository) {
        this.noteRepository = noteRepository;
        this.etudiantRepository = etudiantRepository;
        this.coursRepository = coursRepository;
    }

    private Note convertToEntity(NoteDTO dto) {
        Note note = new Note();

        Etudiant etudiant = etudiantRepository.findById(dto.getEtudiantId())
                .orElseThrow(() -> new EntityNotFoundException("Étudiant non trouvé"));
        note.setEtudiant(etudiant);

        Cours cours = coursRepository.findById(dto.getCoursId())
                .orElseThrow(() -> new EntityNotFoundException("Cours non trouvé"));
        note.setCours(cours);

        note.setNote(dto.getNote());
        note.setDateEvaluation(LocalDate.parse(dto.getDateEvaluation()));
        note.setType(dto.getType());
        note.setSemestre(dto.getSemestre());
        note.setObservation(dto.getObservation());

        return note;
    }

    @Override
    public Note createNote(NoteDTO noteDTO) {
        Note note = convertToEntity(noteDTO);
        return noteRepository.save(note);
    }

    @Override
    public Note updateNote(Long id, NoteDTO noteDTO) {
        Note existingNote = noteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Note non trouvée"));

        if (noteDTO.getNote() != null) existingNote.setNote(noteDTO.getNote());
        if (noteDTO.getDateEvaluation() != null) {
            existingNote.setDateEvaluation(LocalDate.parse(noteDTO.getDateEvaluation()));
        }
        if (noteDTO.getType() != null) existingNote.setType(noteDTO.getType());
        if (noteDTO.getSemestre() != null) existingNote.setSemestre(noteDTO.getSemestre());
        if (noteDTO.getObservation() != null) existingNote.setObservation(noteDTO.getObservation());

        return noteRepository.save(existingNote);
    }

    @Override
    public void deleteNote(Long id) {
        if (!noteRepository.existsById(id)) {
            throw new EntityNotFoundException("Note non trouvée");
        }
        noteRepository.deleteById(id);
    }

    @Override
    public Note getNoteById(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Note non trouvée"));
    }

    @Override
    public List<Note> getNotesByEtudiant(Long etudiantId) {
       return noteRepository.findByEtudiantId(etudiantId);
    }

    @Override
    public List<Note> getNotesByCours(Long coursId) {
        return noteRepository.findByCoursId(coursId);
    }

    @Override
    public List<Note> getNotesByEtudiantAndSemestre(Long etudiantId, String semestre) {
        return noteRepository.findByEtudiantIdAndSemestre(etudiantId, semestre);
    }

    @Override
    public Double calculateMoyenne(Long etudiantId, String semestre) {
        List<Note> notes = noteRepository.findByEtudiantIdAndSemestre(etudiantId, semestre);
        if (notes.isEmpty()) {
            return 0.0;
        }

        double sommeNotes = 0;
        double sommeCoefficients = 0;

        for (Note note : notes) {
            double coefficient = note.getCours().getCoefficient();
            sommeNotes += note.getNote() * coefficient;
            sommeCoefficients += coefficient;
        }

        return sommeCoefficients > 0 ? sommeNotes / sommeCoefficients : 0;
    }

    @Override
    public Page<Note> getAllNotesPaginees(int page, int size) {
        return noteRepository.findAll(PageRequest.of(page, size));
    }
}