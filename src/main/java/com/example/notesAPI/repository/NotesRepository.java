package com.example.notesAPI.repository;

import com.example.notesAPI.dto.Note.NoteDTO;
import com.example.notesAPI.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotesRepository extends JpaRepository<Note, Integer> {

    //need to fix to handle null label and note color values
//    @Query("SELECT new com.example.notesAPI.dto.Note.NoteDTO(n.noteID, n.title, n.textContent, " +
//            "CASE WHEN n.label IS NOT NULL THEN new com.example.notesAPI.dto.Label.LabelDTO(n.label.labelName, n.label.id) ELSE null END,"+
//            "CASE WHEN n.color IS NOT NULL THEN new com.example.notesAPI.dto.noteColor.NoteColorDTO(n.color.colorHEX, n.color.id) ELSE null END,"+
//            " n.cosmetics, " +
//            "n.pinned, n.hidden, " +
//            "n.createdAt, n.updatedAt," +
//            "n.deleted, n.timeLeftBeforeDeletion)" +
//            "FROM Note n " +
//            "WHERE n.user.id = ?1")
    List<NoteDTO> findAllByUser(int userID);

    @Query("SELECT new com.example.notesAPI.dto.Note.NoteDTO(n.noteID, n.title, n.textContent, " +
            "new com.example.notesAPI.dto.Label.LabelDTO(n.label.labelName,n.label.id), " +
            "new com.example.notesAPI.dto.noteColor.NoteColorDTO(n.color.colorHEX, n.color.id)," +
            " n.cosmetics, " +
            "n.pinned, n.hidden, " +
            "n.createdAt, n.updatedAt," +
            "n.deleted, n.timeLeftBeforeDeletion)" +
            "FROM Note n " +
            "WHERE n.noteID = ?1")
    NoteDTO findByIdAndConvertToDTO(int noteID);
}
