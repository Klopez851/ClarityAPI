package com.example.notesAPI.controller;

import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.dto.UITemplate.CreateTemplateDTO;
import com.example.notesAPI.dto.UITemplate.DeleteUITemplateDTO;
import com.example.notesAPI.dto.UITemplate.GetTemplateDTO;
import com.example.notesAPI.dto.UITemplate.UpdateTemplateDTO;
import com.example.notesAPI.service.UITemplateService;
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
@AllArgsConstructor
@Tag(name = "UI Template Endpoints")
@SecurityRequirement(name="JwtAuth")
@RequestMapping("/uitemplate")
public class UITemplateController {

    private final UITemplateService service;

    /// /////////////////
    /// POST METHODS ///
    /// /////////////////
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = " UI template has been successfully created",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class ))),
            @ApiResponse(responseCode = "400",description = "Template name exceeds character limit or unable to add template to database, or",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class ))),
            @ApiResponse(responseCode = "404",description = "Invalid User provided by JWT",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401",description = "Invalid JWT")
    })
    @Operation(summary = "creates ui templates", description = "allows users to store their custom ui templates")
    @PostMapping("/create")
    public ApiResponseDTO<String> createTemplate(@RequestBody CreateTemplateDTO template, HttpServletRequest request) {
        if (!template.isValid()) {
            throw new IllegalArgumentException("All fields (template name, and template details) must be filled out");
        }
        return service.createTemplate(template, request);
    }

    /// /////////////////
    /// GET METHODS ///
    /// /////////////////
    @ApiResponses({
            @ApiResponse(responseCode = "404",description = "Invalid email in JWT",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class ))),
            @ApiResponse(responseCode = "200",description = "",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "401",description = "Invalid JWT",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class )))
    })
    @Operation(summary = "fetches ui templates", description = "fetches all ui templates associated with the provided email")
    @GetMapping("/getTemplates")
    public ApiResponseDTO<List<GetTemplateDTO>> getTemplates(HttpServletRequest request) {
        return service.getTemplates(request);
    }

    /// /////////////////
    /// PATCH METHODS ///
    /// /////////////////

    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "template details successfully updated",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class ))),
            @ApiResponse(responseCode = "404",description = "Non-existent template ID, provided ID isnt associated " +
                    "with the given email, or invalid email provided by Jwt",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401",description = "Invalid JWT"),
            @ApiResponse(responseCode = "400",description = "Unable to save template to database",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class )))
    })
    @Operation(summary = "updates a template's details", description = "updates a ui template's details")
    @PatchMapping("/updateTemplateDetails")
    public ApiResponseDTO<String> updateTemplateDetails(@RequestBody UpdateTemplateDTO template, HttpServletRequest request) {
        if (!template.isValid()) {
            throw new IllegalArgumentException("The field newInfo must be provided");
        }

        return service.updateTemplateDetails(template, request);
    }

    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "template name successfully updated",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class ))),
            @ApiResponse(responseCode = "404",description = "Non-existent template ID, provided ID isn't associated " +
                    "with the given email, or invalid email provided by Jwt",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401",description = "Invalid JWT"),
            @ApiResponse(responseCode = "400",description = "Unable to save template to database, or max character limit exceeded",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class )))
    })
    @Operation(summary = "updates a template's name", description = "updates a ui template's name")
    @PatchMapping("/updateTemplateName")
    public ApiResponseDTO<String> updateTemplateName(@RequestBody UpdateTemplateDTO templateDTO, HttpServletRequest request) {
        if (!templateDTO.isValid()) {
            throw new IllegalArgumentException("All fields (templateID, newInfo) must be filled out");
        }
        return service.updateTemplateName(templateDTO, request);
    }

    /// ///////////////////
    /// DELETE METHODS ///
    /// ///////////////////

    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "template was successfully deleted",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class ))),
            @ApiResponse(responseCode = "404",description = "Non-existent template ID, provided ID isnt associated " +
                    "with the given email, or invalid email provided by Jwt",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401",description = "Invalid JWT"),
            @ApiResponse(responseCode = "400",description = "Unable to delete template from database",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class )))
    })
    @Operation(summary = "deletes a ui template", description = "deletes a given ui template as long as its associated with the given email")
    @DeleteMapping("/deleteUserTemplate")
    public ApiResponseDTO<String> deleteTemplate(@RequestBody DeleteUITemplateDTO template, HttpServletRequest request) {
        //make sure data is valid
        if (!template.isValid()) {
            throw new IllegalArgumentException("A templateID must be provided");
        }
        //give data to service
        return service.deleteTemplate(template, request);
    }

    // TODO: might make an endpoint to delete default templates

}
