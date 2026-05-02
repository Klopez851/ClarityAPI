package com.example.notesAPI.controller;

import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.dto.EmailDTO;
import com.example.notesAPI.dto.Note.CreateNoteDTO;
import com.example.notesAPI.dto.Note.NoteDTO;
import com.example.notesAPI.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Note Endpoints")
@AllArgsConstructor
@RequestMapping("/note")
public class NoteController {

    private final NoteService service;

    //////////////////////
    /// POST MAPPING/S ///
    //////////////////////

    @Operation(summary = "creates a note", description = "creates a note and associated it with the given email ")
    @PostMapping("/createNote")
    public ApiResponseDTO<String> createNote(@RequestBody CreateNoteDTO note, HttpServletRequest request) {
        if(!note.isValid()){
            throw new IllegalArgumentException("Note must have a title or text content and a valid email");
        }
        return service.createNote(note, request);
    }

    //////////////////////
    /// GET MAPPING/S ////
    //////////////////////

    @Operation(summary = "returns all user notes", description = "returns all the notes associated with the use email provided")
    @GetMapping("/getNotes")
    public ApiResponseDTO<List<NoteDTO>> getNote(@RequestBody EmailDTO email, HttpServletRequest request){
        if(!email.isValid()){
            throw new IllegalArgumentException("please provide a valid email");
        }

        return service.getNotes(email, request);
    }
//
//    @PutMapping("/{id}")
//    public apiResponseDTO updateNote(@PathVariable int id, @RequestBody updateNoteDTO dto){
//        return service.updateNote(id, Optional.ofNullable(dto.getContent()), Optional.ofNullable(dto.getTitle()),
//                Optional.ofNullable(dto.getLabelID()));
//    }
//
//    @DeleteMapping("/{id}")
//    public apiResponseDTO deleteNote(@PathVariable int id){
//        return service.deleteNote(id);
//    }
//
}
//
