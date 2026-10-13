package com.example.notesAPI.controller;

import com.example.notesAPI.DTOs.ApiResponseDTO;
import com.example.notesAPI.DTOs.Note.*;
import com.example.notesAPI.service.NoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Note Endpoints")
@SecurityRequirement(name = "JwtAuth")
@AllArgsConstructor
@RequestMapping("/note")
public class NoteController {

    private final NoteService service;

    /// ///////////////////
    /// POST MAPPING/S ///
    /// ///////////////////

    //this is for swagger error documentation only
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Note was successfully created",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Title max character limit exceeded, note could not be " +
                    "saved to database, or error in request body",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "User associated with email in JWT could not be found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403", description = "LabelID or NoteColorID provided isnt associated with " +
                    "the email in the JWT",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Invalid JWT")
    })
    @Operation(summary = "creates a note", description = "creates a note and associated it with the email provided by the " +
            "jwt token, \"not required\" means if no value needs to be passed, the field itself with an empty string as " +
            "a value must be still present in request")

    @PostMapping("/createNote")
    public ApiResponseDTO<String> createNote(@RequestBody CreateNoteDTO note, HttpServletRequest request) {
        if (!note.isValid()) {
            throw new IllegalArgumentException("Note must have a title or text content");
        }
        return service.createNote(note, request);
    }

    /// ///////////////////
    /// GET MAPPING/S ////
    /// ///////////////////

    //this is for swagger error documentation only
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Notes were successfully fetched",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User associated with email in JWT could not be found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Invalid JWT")
    })
    @Operation(summary = "returns all user notes", description = "returns all the notes associated with the use email provided")

    @GetMapping("/getNotes")
    public ApiResponseDTO<List<NoteDTO>> getNotes(HttpServletRequest request) {
        return service.getNotes(request);
    }

    //this is for swagger error documentation only
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Note was successfully fetched",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User associated with email in JWT could not be found, " +
                    "Note ID doesn't exist, or note ID is not associated with the email provided",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Invalid JWT")
    })
    @Operation(summary = "returns a single note", description = "Returns a single note with the provided note id")

    @GetMapping("/getNote/{noteID}")
    public ApiResponseDTO<NoteDTO> getNote(@PathVariable int noteID, HttpServletRequest request) {
        return service.getNote(noteID, request);
    }

    /// ///////////////////
    /// PUT MAPPING/S ////
    /// ///////////////////

    //this is for swagger error documentation only
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Note was successfully updated",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Updated note could not be " +
                    "saved to database, or error in request body",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "User associated with email in JWT could not be found, " +
                    "Note Id doesn't exist, or note Id isnt associated with the provided user",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403", description = "LabelID or NoteColorID provided isnt associated with " +
                    "the email in the JWT",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Invalid JWT")
    })
    @Operation(summary = "updates a note", description = "updates a users note")

    @PutMapping("/updateNote")
    public ApiResponseDTO<String> updateNote(@RequestBody UpdateNoteDTO noteDTO, HttpServletRequest request) {
        if (!noteDTO.isValid()) {
            throw new IllegalArgumentException("- Either the title or body field must contain content (both cannot be empty).\n" +
                    "- The fields pinned and hidden cannot both be set to true at the same time." +
                    "- The id field must contain a value bigger than 0");
        }

        return service.updateNote(noteDTO, request);
    }

    /// /////////////////////
    /// PATCH MAPPING/S ////
    /// /////////////////////

    //this is for swagger error documentation only
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pinned status was successfully updated",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Updated note could not be saved to database, or error in " +
                    "request body",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "User associated with email in JWT could not be found, " +
                    "provided Note ID could not be found, provided Note ID isn't associated with the provided email",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Invalid JWT")
    })
    @Operation(summary = "updates a notes pinned status")

    @PatchMapping("/updatePinned")
    public ApiResponseDTO<String> updatePinnedStatus(@RequestBody UpdateBooleanStatusDTO noteDTO, HttpServletRequest request) {
        if (!noteDTO.isValid()) {
            throw new IllegalArgumentException("All fields (noteID, newValue) must be filled out");
        }
        return service.updatePinned(noteDTO, request);
    }

    //this is for swagger error documentation only
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Hidden status was successfully updated",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Updated note could not be saved to database, or error in " +
                    "request body",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "User associated with email in JWT could not be found, " +
                    "provided Note ID could not be found, provided Note ID isn't associated with the provided email",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Invalid JWT")
    })
    @Operation(summary = "updates a notes hidden status")

    @PatchMapping("/updateHidden")
    public ApiResponseDTO<String> updateHiddenStatus(@RequestBody UpdateBooleanStatusDTO noteDTO, HttpServletRequest request) {
        if (!noteDTO.isValid()) {
            throw new IllegalArgumentException("All fields (noteID, newValue) must be filled out");
        }
        return service.updateHidden(noteDTO, request);
    }

    //this is for swagger error documentation only
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Deleted status was successfully updated",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Updated note could not be saved to database, or error in " +
                    "request body",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "User associated with email in JWT could not be found, " +
                    "provided Note ID could not be found, provided Note ID isn't associated with the provided email",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Invalid JWT")
    })
    @Operation(summary = "updates a notes deleted status")

    @PatchMapping("/updateDeleted")
    public ApiResponseDTO<String> updateDeletedStatus(@RequestBody UpdateBooleanStatusDTO noteDTO, HttpServletRequest request) {
        if (!noteDTO.isValid()) {
            throw new IllegalArgumentException("All fields (noteID, newValue) must be filled out");
        }
        return service.updateDeleted(noteDTO, request);
    }

    //this is for swagger error documentation only
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "ViewOnly status was successfully updated",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Updated note could not be saved to database, or error in " +
                    "request body",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "User associated with email in JWT could not be found, " +
                    "provided Note ID could not be found, provided Note ID isn't associated with the provided email",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Invalid JWT")
    })
    @Operation(summary = "updates a notes view only status")

    @PatchMapping("/updateViewOnly")
    public ApiResponseDTO<String> updateViewOnlyStatus(@RequestBody UpdateBooleanStatusDTO noteDTO, HttpServletRequest request) {
        if (!noteDTO.isValid()) {
            throw new IllegalArgumentException("All fields (noteID, newValue) must be filled out");
        }
        return service.updateViewOnly(noteDTO, request);
    }

    //this is for swagger error documentation only
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Note label was successfully updated",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Updated note could not be saved to database, or error in " +
                    "request body",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "User associated with email in JWT could not be found, " +
                    "provided Note ID could not be found, provided label ID could not be found, or provided Note " +
                    "ID isn't associated with the provided email",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Invalid JWT")
    })
    @Operation(summary = "updates a note's label")

    @PatchMapping("/updateLabel")
    public ApiResponseDTO<String> updateLabel(@RequestBody UpdateNoteLabelDTO labelDTO, HttpServletRequest request) {
        if (!labelDTO.isValid()) {
            throw new IllegalArgumentException("All fields (noteID, labelID) must be filled out");
        }
        return service.updateLabel(labelDTO, request);
    }

    //this is for swagger error documentation only
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Note color was successfully updated",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Updated note could not be saved to database, or error in " +
                    "request body",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "User associated with email in JWT could not be found, " +
                    "provided Note ID could not be found, provided Note ID isn't associated with the provided email," +
                    " or provided note color ID could not be found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Invalid JWT")
    })
    @Operation(summary = "updates a note's color")

    @PatchMapping("/updateNoteColor")
    public ApiResponseDTO<String> updateNoteColor(@RequestBody UpdateColorDTO colorDTO, HttpServletRequest request) {
        if (!colorDTO.isValid()) {
            throw new IllegalArgumentException("All fields (noteID, colorID) must be filled out");
        }
        return service.updateNoteColor(colorDTO, request);
    }

    //this is for swagger error documentation only
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Note cosmetics was successfully updated",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Updated note could not be saved to database, or error in " +
                    "request body",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "User associated with email in JWT could not be found, " +
                    "provided Note ID could not be found, provided Note ID isn't associated with the provided email",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Invalid JWT")
    })
    @Operation(summary = "updates a note's cosmetics")

    @PatchMapping("/updateCosmetics")
    public ApiResponseDTO<String> updateCosmetics(@RequestBody UpdateCosmeticDTO cosmeticsDTO, HttpServletRequest request) {
        if (!cosmeticsDTO.isValid()) {
            throw new IllegalArgumentException("All fields (noteID, cosmetics) must be filled out");
        }

        return service.updateCosmetics(cosmeticsDTO, request);
    }

    /// //////////////////////
    /// DELETE MAPPING/S ////
    /// //////////////////////

    //this is for swagger error documentation only
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Note Was successfully deleted",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "note could not be deleted, or error in " +
                    "request body",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "User associated with email in JWT could not be found, " +
                    "provided Note ID could not be found, provided Note ID isn't associated with the provided email",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Invalid JWT")
    })
    @Operation(summary = "deletes a note")

    @DeleteMapping("/deleteNote")
    public ApiResponseDTO<String> deleteNote(@RequestBody DeleteNoteDTO noteDTO, HttpServletRequest request) {
        if (!noteDTO.isValid()) {
            throw new IllegalArgumentException("A noteID must be provided");
        }
        return service.deleteNote(noteDTO, request);
    }

}

