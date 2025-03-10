package com.isi.repository;

import com.isi.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByEtudiantId(Long etudiantId);
    List<Note> findByCoursId(Long coursId);
    List<Note> findByEtudiantIdAndCoursId(Long etudiantId, Long coursId);
    List<Note> findByEtudiantIdAndSemestre(Long etudiantId, String semestre);
}