package com.example.notesAPI.controller;

import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.dto.Label.CreateLabelDTO;
import com.example.notesAPI.dto.Label.DeleteLabelDTO;
import com.example.notesAPI.dto.Label.LabelDTO;
import com.example.notesAPI.dto.Label.UpdateLabelDTO;
import com.example.notesAPI.service.LabelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@SecurityRequirement(name="JwtAuth")
@Tag(name = "Label Endpoints")
@RequestMapping("/label")
public class LabelController {

    private LabelService service;

    /// ///////////////////
    /// POST MAPPING/S ///
    /// //////////////////

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

    @Operation(summary = "fetches labels", description = "fetches all labels associated with the provided email in the jwt token")
    @GetMapping("/getLabels")
    public ApiResponseDTO<List<LabelDTO>> getLabels(HttpServletRequest request) {
        return service.getLabels(request);
    }

    //might create endpoint to get individual labels if needed

    /// ////////////////////
    /// PATCH MAPPING/S ///
    /// ////////////////////
    @Operation(summary = "updates a label", description = "allows users to update any of the labels associated with them as long as a different label name from the name stored is provided")
    @PatchMapping("/updateLabel")
    public ApiResponseDTO<String> updateLabel(@RequestBody UpdateLabelDTO reqLabel) {
        if (!reqLabel.isValid()) {
            throw new IllegalArgumentException("All fields (labelID, labelName) must be filled");
        }
        return service.updateLabel(reqLabel);
    }

    /// /////////////////////
    /// DELETE MAPPING/S ///
    /// /////////////////////

    @Operation(summary = "deletes a label", description = "Allows users to delete any of their labels as long as they exists in the db")
    @DeleteMapping("/deleteLabel")
    public ApiResponseDTO<String> deleteLabel(@RequestBody DeleteLabelDTO label, HttpServletRequest request) {
        if (!label.isValid()) {
            throw new IllegalArgumentException("A labelID must provided");
        }
        return service.deleteLabel(label, request);
    }
}
