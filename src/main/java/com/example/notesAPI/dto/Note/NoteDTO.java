package com.example.notesAPI.dto.Note;

import com.example.notesAPI.dto.Label.LabelDTO;
import com.example.notesAPI.dto.noteColor.NoteColorDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonPropertyOrder({"id", "title", "textContent", "label", "noteColor", "cosmetics", "pinned", "hidden",
        "createdAt", "updatedAt", "deleted", "timeLeftBeforeDeletion"})
public class NoteDTO {

    private int id;
    private LabelDTO label;
    private NoteColorDTO noteColor;

    private String title;
    private String textContent;
    private String cosmetics;

    private boolean pinned;
    private boolean hidden;
    private boolean viewOnly = false; //will remove default value once i figure out how to share notes amongst users
    private boolean deleted;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime timeLeftBeforeDeletion;

    //this constructor matches the exact order the query NotesRepository.findAllByUser() requires
    // changes made here need to be reflected there.
    public NoteDTO(int id, String title, String content, LabelDTO label, NoteColorDTO noteColor, String cosmetics,
                   boolean pinned, boolean hidden,
                   LocalDateTime createdAt, LocalDateTime updatedAt,
                   boolean deleted, LocalDateTime timeLeftBeforeDeletion){
        this.id = id;
        this.title = title;
        this.textContent = content;
        this.label = label;
        this.noteColor = noteColor;
        this.cosmetics = cosmetics;
        this.pinned = pinned;
        this.hidden = hidden;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.timeLeftBeforeDeletion = timeLeftBeforeDeletion;
    }

    @JsonIgnore
    public boolean isValid(){
        if(isEqual(pinned, hidden)){
            return false;
        }

        if ((getTitle().isEmpty() || title.isBlank())
                && getTextContent().isEmpty() || textContent.isBlank()){
            return false;
        }

        return true;
    }

    ////////////////////////
    /// PRIVATE METHOD/S ///
    ////////////////////////

    private boolean isEqual(boolean attribute1, boolean attribute2){
        return (attribute1 == attribute2);
    }
}
