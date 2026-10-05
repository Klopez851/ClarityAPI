package com.example.notesAPI.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Note {
    /// ////////////////////
    /// CLASS VARIABLES ///
    /// ////////////////////
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private int noteID; //making this an int (not a long) since its not a big webapp, its for personal use

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserTable user;

    @ManyToOne
    @JoinColumn(name = "label_id")//name of fk in note table that refers to the label table
    private Label label;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private NoteColor color;

    private String title;
    private String textContent;
    private boolean pinned;
    private boolean hidden;
    private String cosmetics;
    private boolean viewOnly;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    private boolean deleted;
    private LocalDateTime timeLeftBeforeDeletion;

    //needs to be public for hibernate/jackson
    public Note(UserTable user, Label label, NoteColor color, String title, String textContent,
                boolean pinned, boolean hidden, String cosmetics, boolean viewOnly,
                LocalDateTime createdAt, LocalDateTime updatedAt, boolean deleted,
                LocalDateTime timeLeftBeforeDeletion) {
        this.user = user;
        this.label = label;
        this.color = color;
        this.title = title;
        this.textContent = textContent;
        this.pinned = pinned;
        this.hidden=hidden;
        this.cosmetics = cosmetics;
        this.viewOnly = viewOnly;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deleted = deleted;
        this.timeLeftBeforeDeletion = timeLeftBeforeDeletion;

    }

    // TODO: remove to string if its just for debugging purposes
    public String toString() {
        return (noteID + " " + user.getEmail() + " " + title + " " + textContent);
    }

    public static class Builder{

        // MANDATORY FIELDS
        private UserTable user = null;
        private String title = null;
        private String textContent = null;

        // OPTIONAL FIELDS
        private Label label = null;
        private NoteColor color = null;
        private boolean pinned = false;
        private boolean hidden = false;
        private String cosmetics = "<Insert cosmetics here>";
        private boolean viewOnly = false;
        private LocalDateTime createdAt = LocalDateTime.now();
        private LocalDateTime updatedAt = LocalDateTime.now();
        private boolean deleted = false;
        private LocalDateTime timeLeftBeforeDeletion = null;

        public Builder setUser(UserTable user){
            this.user = user;
            return this;
        }

        public Builder setTitle(String title){
            this.title = title;
            return this;
        }

        public Builder setTextContent(String textContent){
            this.textContent = textContent;
            return this;
        }

        public Builder setLabel(Label label){
            if(label == null){
                return this;
            }
            this.label = label;
            return this;
        }

        public Builder setColor(NoteColor color) {
            if(color == null){
                return this;
            }
            this.color = color;
            return this;
        }

        public Builder setPinned(boolean pinned) {
            this.pinned = pinned;
            return this;
        }

        public Builder setHidden(boolean hidden) {
            this.hidden = hidden;
            return this;
        }

        public Builder setCosmetics(String cosmetics) {
            this.cosmetics = cosmetics;
            return this;
        }

        public Builder setViewOnly(boolean viewOnly) {
            this.viewOnly = viewOnly;
            return this;
        }

        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setUpdatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public Builder setDeleted(boolean deleted) {
            this.deleted = deleted;
            return this;
        }

        public Builder setTimeLeftBeforeDeletion(LocalDateTime timeLeftBeforeDeletion) {
            this.timeLeftBeforeDeletion = timeLeftBeforeDeletion;
            return this;
        }

        public Note build(){
            if(user == null){
                throw new IllegalArgumentException("Invalid user provided");
            }

            if(title.isBlank() && this.textContent.isBlank()){
                throw new IllegalArgumentException("Title and text content cannot both be blank");
            }

            return new Note(user,label, color, title,textContent,pinned,hidden,cosmetics,
                    viewOnly,createdAt,updatedAt,deleted,timeLeftBeforeDeletion);
        }
    }
}
