package com.example.notesAPI.repository;

import com.example.notesAPI.dto.Note.NoteDTO;
import com.example.notesAPI.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotesRepository extends JpaRepository<Note, Integer> {

    @Query("SELECT new com.example.notesAPI.dto.Note.NoteDTO(n.noteID, n.title, n.textContent, " +
            "new com.example.notesAPI.dto.Label.LabelDTO(n.label.labelName,n.label.id), " +
            "new com.example.notesAPI.dto.noteColor.NoteColorDTO(n.color.colorHEX, n.color.id)," +
            " n.cosmetics, " +
            "n.pinned, n.hidden, " +
            "n.createdAt, n.updatedAt," +
            "n.deleted, n.timeLeftBeforeDeletion)" +
            "FROM Note n " +
            "WHERE n.user.id = ?1 ")
    List<NoteDTO> findAllByUser(int userID);
}
