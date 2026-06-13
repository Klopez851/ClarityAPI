package com.example.notesAPI.controller;

import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.dto.noteColor.CreateNoteColorDTO;
import com.example.notesAPI.dto.noteColor.DeleteNoteColorDTO;
import com.example.notesAPI.dto.noteColor.NoteColorDTO;
import com.example.notesAPI.dto.noteColor.UpdateNoteColorDTO;
import com.example.notesAPI.service.NoteColorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Tag(name = "Note Color Endpoints")
@SecurityRequirement(name="JwtAuth")
@RestController
@RequestMapping(("/noteColor"))
public class NoteColorController {

    private final NoteColorService service;

    /// ///////////////////
    /// POST MAPPING/S ///
    /// ///////////////////

    @Operation(summary = "stores a color", description = "stores a custom color by hex number")
    @PostMapping("/createColor")
    public ApiResponseDTO<String> createNoteColor(@RequestBody CreateNoteColorDTO colorDTO, HttpServletRequest request) {
        if (!colorDTO.isValid()) {
            throw new IllegalArgumentException("A colorHex must be provided");
        }
        return service.createNoteColor(colorDTO, request);
    }

    /// //////////////////
    /// GET MAPPING/S ///
    /// //////////////////

    @Operation(summary = "fetch colors", description = " fetch all colors associated with the email provided by the jwt token")
    @GetMapping("/getColors")
    public ApiResponseDTO<List<NoteColorDTO>> getNoteColors(HttpServletRequest request) {
        return service.getNoteColors(request);
    }

    /// ////////////////////
    /// PATCH MAPPING/S ///
    /// ////////////////////

    @Operation(summary = "updates an existing color", description = "updates an existing color")
    @PatchMapping("/updateColor")
    public ApiResponseDTO<String> updateNoteColor(@RequestBody UpdateNoteColorDTO colorDTO, HttpServletRequest request) {
        if (!colorDTO.isValid()) {
            throw new IllegalArgumentException("All fields (colorID, newColor) must be filled out");
        }
        return service.updateNoteColor(colorDTO, request);
    }

    /// /////////////////////
    /// DELETE MAPPING/S ///
    /// /////////////////////

    @Operation(summary = "Deletes a color", description = "deletes an existing custom color associated with the provided email")
    @DeleteMapping("/deleteColor")
    public ApiResponseDTO<String> deleteNoteColor(@RequestBody DeleteNoteColorDTO colorDTO, HttpServletRequest request) {
        if (!colorDTO.isValid()) {
            throw new IllegalArgumentException("A colorID must be provided");
        }
        return service.deleteCoteColor(colorDTO, request);
    }
}
