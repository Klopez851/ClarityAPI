package com.example.notesAPI.dto.Note;

import com.example.notesAPI.dto.Label.LabelDTO;
import com.example.notesAPI.dto.noteColor.NoteColorDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


//this used only to return notes to the frontend, therefore no swagger annotations needed
@Getter
@Setter
@NoArgsConstructor
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

    //this constructor matches the exact order the query NotesRepository.getAllNoteByUser() and .findByUser() requires
    // changes made here need to be reflected there.
    public NoteDTO(int id, String title, String content, String labelName, Integer labelId, String colorHex,
                   Integer colorID, String cosmetics,
                   boolean pinned, boolean hidden,
                   LocalDateTime createdAt, LocalDateTime updatedAt,
                   boolean deleted, LocalDateTime timeLeftBeforeDeletion) {
        this.id = id;
        this.title = title;
        this.textContent = content;
        this.label = labelId != null ? new LabelDTO(labelName, labelId.intValue()) : null;//big int bc it is nullable
        this.noteColor = colorID != null ? new NoteColorDTO(colorHex, colorID.intValue()) : null;
        this.cosmetics = cosmetics;
        this.pinned = pinned;
        this.hidden = hidden;
        this.deleted = deleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.timeLeftBeforeDeletion = timeLeftBeforeDeletion;
    }

    @JsonIgnore
    public boolean isValid() {
        if (pinned && hidden) {
            return false;
        }

        if ((getTitle().isEmpty() || title.isBlank())
                && getTextContent().isEmpty() || textContent.isBlank()) {
            return false;
        }

        if (id < 0) {
            return false;
        }

        return true;
    }
}
