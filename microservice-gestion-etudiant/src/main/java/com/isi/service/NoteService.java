package com.isi.service;

import com.isi.dto.NoteDTO;
import com.isi.model.Note;
import org.springframework.data.domain.Page;
import java.util.List;

public interface NoteService {
    Note createNote(NoteDTO noteDTO);
    Note updateNote(Long id, NoteDTO noteDTO);
    void deleteNote(Long id);
    Note getNoteById(Long id);
    List<Note> getNotesByEtudiant(Long etudiantId);
    List<Note> getNotesByCours(Long coursId);
    List<Note> getNotesByEtudiantAndSemestre(Long etudiantId, String semestre);
    Double calculateMoyenne(Long etudiantId, String semestre);
    Page<Note> getAllNotesPaginees(int page, int size);
}