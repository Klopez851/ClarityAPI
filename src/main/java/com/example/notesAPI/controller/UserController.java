package com.example.notesAPI.controller;

import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.dto.User.UpdateEmailDTO;
import com.example.notesAPI.dto.User.UpdateUserInfoDTO;
import com.example.notesAPI.dto.User.UserInfoDTO;
import com.example.notesAPI.dto.User.UserLoginDTO;
import com.example.notesAPI.service.UserService;
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

@RestController
@AllArgsConstructor
@Tag(name = "User Endpoints")
@RequestMapping("/user")
public class UserController {

    private final UserService service;

    /// //////////////////
    /// POST MAPPINGS ///
    /// //////////////////

    //this is for swagger error documentation only
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User successfully created",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Max character limit for email or username was exceeded, " +
                    "user could not be saved to the database, or error in request body",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "User already exists",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @Operation(summary = "Creates a new user", description = "Creates a new user if provided with a non-existent email")

    @PostMapping("/createUser")
    public ApiResponseDTO createUser(@RequestBody UserInfoDTO user) {
        if (!user.isValid()) {
            throw new IllegalArgumentException("All fields (username, email, and password) must be filled out");
        }
        return (service.createUser(user));
    }

    //this is for swagger error documentation only
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User verified successfully"),
            @ApiResponse(responseCode = "404", description = "User does not exists",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "400", description = "Error in request body",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @Operation(summary = "Allows user to login", description = "Allows user to log in and returns a custom JWT token " +
            "with lowercase email as claim")

    @PostMapping("/login")
    public String login(@RequestBody UserLoginDTO user) {
        if (!user.isValid()) {
            throw new IllegalArgumentException("All fields must be filled out");
        }
        return service.verify(user);
    }

    /// /////////////////
    /// GET MAPPINGS ///
    /// /////////////////

    //this is for swagger error documentation only
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User sucessfully fetched",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User could not be found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Invalid JWT")
    })
    @Operation(summary = "fetches user information", description = "fetches user information using a valid jwt token")
    // Other controllers define security at the class level; this controller requires endpoint-level annotations due
    // to mixed access
    @SecurityRequirement(name = "JwtAuth")

    @GetMapping("/getUser")
    public ApiResponseDTO<UserInfoDTO> getUser(HttpServletRequest request) {
        return service.getUser(request);
    }

    /// ///////////////////
    /// PATCH MAPPINGS ///
    /// ///////////////////

    //this is for swagger error documentation only
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Email successfully updated",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Email character limit exceeded, email could not be " +
                    "updated in database, or error in request body",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Invalid JWT")
    })
    @Operation(summary = "Allows user to update their email", description = "Allows user to update their email to a non-existing email")
    @SecurityRequirement(name = "JwtAuth")

    @PatchMapping("/updateEmail")
    public ApiResponseDTO<String> updateEmail(@RequestBody UpdateEmailDTO emailDTO, HttpServletRequest request) {
        if (!emailDTO.isValid()) {
            throw new IllegalArgumentException("Must provide new email");
        }
        return service.updateEmail(emailDTO, request);
    }

    //this is for swagger error documentation only
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Username successfully updated",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Username character limit exceeded, username could not be " +
                    "updated in database, or error in request body",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Invalid Email provided, Username updates require a valid " +
                    "email to identify the user to update.",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Invalid JWT")
    })
    @Operation(summary = "Allows user to update their username", description = "Allows user to update their username")
    @SecurityRequirement(name = "JwtAuth")

    @PatchMapping("/updateUsername")
    public ApiResponseDTO<String> updateUsername(@RequestBody UpdateUserInfoDTO usernameDTO, HttpServletRequest request) {
        if (!usernameDTO.isValid()) {
            throw new IllegalArgumentException("Must provide a new username");
        }
        return service.updateUsername(usernameDTO, request);
    }

    //this is for swagger error documentation only
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Password updated successfully",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "New password could not be saved to the database, " +
                    "or error in request body",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Email provided does not exists. a valid email is required " +
                    "for a password update",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access/Invalid JWT")
    })
    @Operation(summary = "Allows user to update their password", description = "Allows user to update their password")
    @SecurityRequirement(name = "JwtAuth")

    @PatchMapping("/updatePassword")
    public ApiResponseDTO<String> updatePassword(@RequestBody UpdateUserInfoDTO passwordDTO, HttpServletRequest request) {
        if (!passwordDTO.isValid()) {
            throw new IllegalArgumentException("Must provide a new password");//assuming front-end will provide correct user email in request
        }
        return service.updatePassword(passwordDTO, request);
    }

    /// ///////////////////
    /// DELETE MAPPING ///
    /// ///////////////////

    //this is for swagger error documentation only
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User successfully deleted",
                    content = @Content(schema = @Schema(implementation = ApiResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "User to be deleted could not be found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "400", description = "User could not be deleted",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access/Invalid JWT")
    })
    @Operation(summary = "Allows user to delete their account", description = "Allows user to delete their account and everything related to them using their jwt token")
    @SecurityRequirement(name = "JwtAuth")

    @DeleteMapping("/deleteUser")
    public ApiResponseDTO<String> deleteUser(HttpServletRequest request) {
        return service.deleteUser(request);
    }

}
