package com.example.notesAPI.controller;

import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.dto.Label.CreateLabelDTO;
import com.example.notesAPI.dto.Label.DeleteLabelDTO;
import com.example.notesAPI.dto.Label.LabelDTO;
import com.example.notesAPI.dto.Label.UpdateLabelDTO;
import com.example.notesAPI.service.LabelService;
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

@AllArgsConstructor
@RestController
@SecurityRequirement(name = "JwtAuth")
@Tag(name = "Label Endpoints")
@RequestMapping("/label")
public class LabelController {

    private LabelService service;

    /// ///////////////////
    /// POST MAPPING/S ///
    /// //////////////////

    //this is for swagger error documentation only
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Label successfully created",
                    //the *content* of the http response body should be the fields in *ApiResponseDTO*
                    content = @Content(
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )),
            @ApiResponse(responseCode = "400", description = "No valid user provided or label name is too long",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class)
                    )),
            @ApiResponse(responseCode = "401", description = "Unauthorized access/Invalid JWT", content = @Content)
            //"content = @Content" necessary for this error code to shows up w/ no response body in swagger ui
    })

    @Operation(summary = "Creates a label", description = "Allows user to create a label")
    @PostMapping("/createLabel")
    public ApiResponseDTO<String> createLabel(@RequestBody CreateLabelDTO userLabel, HttpServletRequest request) {
        if (!userLabel.isValid()) {
            throw new IllegalArgumentException("A label name must be provided");
        }
        return service.createLabel(userLabel, request);
    }

    /// //////////////////
    /// GET MAPPING/S ///
    /// //////////////////

    //this is for swagger error documentation only
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Labels successfully fetched",
                    content = @Content(
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )),
            @ApiResponse(responseCode = "404", description = "Invalid email in JWT claim",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class)
                    )),
            @ApiResponse(responseCode = "401", description = "Unauthorized access/Invalid JWT", content = @Content)
    })

    @Operation(summary = "fetches labels", description = "fetches all labels associated with the provided email in the jwt token")
    @GetMapping("/getLabels")
    public ApiResponseDTO<List<LabelDTO>> getLabels(HttpServletRequest request) {
        return service.getLabels(request);
    }

    //TODO: might create endpoint to get individual labels if needed

    /// ////////////////////
    /// PATCH MAPPING/S ///
    /// ////////////////////

    //this is for swagger error documentation only
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "label successfully updated, or no update was needed because the existing label name was send as request body",
                    content = @Content(
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )),
            @ApiResponse(responseCode = "404", description = "Label with provided ID could not be found",
                    content = @Content(
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )),
            @ApiResponse(responseCode = "401", description = "Unauthorized access/Invalid JWT", content = @Content)
    })

    @Operation(summary = "updates a label", description = "allows users to update any of the labels associated with them as long as a different label name from the name stored is provided")
    @PatchMapping("/updateLabel")
    public ApiResponseDTO<String> updateLabel(@RequestBody UpdateLabelDTO reqLabel, HttpServletRequest request) {
        if (!reqLabel.isValid()) {
            throw new IllegalArgumentException("All fields (labelID, labelName) must be filled");
        }
        return service.updateLabel(reqLabel, request);
    }

    /// /////////////////////
    /// DELETE MAPPING/S ///
    /// /////////////////////

    //this is for swagger error documentation only
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Label successfully deleted",
                    content = @Content(
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )),
            @ApiResponse(responseCode = "404", description = "A label with that ID could not be found, " +
                    "a user with the provided email could not be found, " +
                    "or the label ID provided is not associated with the provided user ",
                    content = @Content(
                            schema = @Schema(implementation = ApiResponseDTO.class)
                    )),
            @ApiResponse(responseCode = "401", description = "Unauthorized access/Invalid JWT", content = @Content)
    })

    @Operation(summary = "deletes a label", description = "Allows users to delete any of their labels as long as they exists in the db")
    @DeleteMapping("/deleteLabel")
    public ApiResponseDTO<String> deleteLabel(@RequestBody DeleteLabelDTO label, HttpServletRequest request) {
        if (!label.isValid()) {
            throw new IllegalArgumentException("A labelID must provided");
        }
        return service.deleteLabel(label, request);
    }
}
