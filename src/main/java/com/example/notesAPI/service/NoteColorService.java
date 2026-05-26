package com.example.notesAPI.service;

import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.dto.EmailDTO;
import com.example.notesAPI.dto.noteColor.CreateNoteColorDTO;
import com.example.notesAPI.dto.noteColor.DeleteNoteColorDTO;
import com.example.notesAPI.dto.noteColor.NoteColorDTO;
import com.example.notesAPI.dto.noteColor.UpdateNoteColorDTO;
import com.example.notesAPI.errorHandler.*;
import com.example.notesAPI.model.NoteColor;
import com.example.notesAPI.model.UserTable;
import com.example.notesAPI.repository.NoteColorRepository;
import com.example.notesAPI.repository.UserRepository;
import com.example.notesAPI.utilClasses.RequestValidationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NoteColorService {

    /// CONSTANTS ///
    private final int MAX_COLOR_HEX_SIZE = 9;
    private final UserRepository userRepo;
    private final NoteColorRepository noteColorRepo;
    private final RequestValidationService requestUtil;

    /////////////////////
    /// POST METHOD/S ///
    /////////////////////

    public ApiResponseDTO<String> createNoteColor(CreateNoteColorDTO colorDTO, HttpServletRequest request) {
        //clean data
        String email = requestUtil.extractEmailClaim(request);
        String colorHex= colorDTO.getColorHex().strip();
        NoteColor color;

        //ensure email is associated with an existing user
        Optional<UserTable> user = userRepo.findByEmail(email);

        if(user.isPresent()){
            //create noteColor
            if(!(colorHex.length()>MAX_COLOR_HEX_SIZE)){
                color = new NoteColor(colorHex, user.get());
            }else{
                throw new IllegalArgumentException("color hex can be a maximum of "+ MAX_COLOR_HEX_SIZE+" characters");
            }

            if(!noteColorRepo.existsByColorHEX(colorHex)) {
                //store color
                try {
                    noteColorRepo.save(color);
                } catch (Exception e) {
                    throw new DatabaseErrorException(e.getMessage());
                }
                return new ApiResponseDTO<String>(
                        true,
                        "color successfully saved",
                        null);

            }throw new ResourceAlreadyExistsException("Such color already exists");
        }throw new ResourceNotFoundException("A user associated with the email "+email+" could not be found");
    }

    ////////////////////
    /// GET METHOD/S ///
    ////////////////////

    public ApiResponseDTO<List<NoteColorDTO>> getNoteColors(HttpServletRequest request) {
        //clean data
        String email = requestUtil.extractEmailClaim(request);

        //ensure user exists
        Optional<UserTable> user = userRepo.findByEmail(email);

        if(user.isPresent()){
            //fetch and return all associated colors
            return new ApiResponseDTO<List<NoteColorDTO>>(
                    true,
                    "Colors succesfully fetched",
                    noteColorRepo.findAllByUser(user.get().getUserID())
            );

        }throw new ResourceNotFoundException("A user associated with the email "+email+" could not be found");
    }

    //////////////////////
    /// PATCH METHOD/S ///
    //////////////////////

    public ApiResponseDTO<String> updateNoteColor(UpdateNoteColorDTO colorDTO, HttpServletRequest request) {
        //clean data
        String email = requestUtil.extractEmailClaim(request);
        String newColor = colorDTO.getNewColor().strip();
        int colorID = Integer.parseInt(colorDTO.getColorID().strip());

        //validate input
        if(newColor.length()>MAX_COLOR_HEX_SIZE){
            throw new IllegalArgumentException("color hex can be a maximum of "+ MAX_COLOR_HEX_SIZE+" characters");
        }

        //get color to be updated
        Optional<NoteColor> color = noteColorRepo.findById(colorID);

        //get user
        Optional<UserTable> user = userRepo.findByEmail(email);

        //ensure color to be updates is associated with the provided user
        if(color.isPresent()){
            if(user.isPresent()){
                if(user.get().getUserID() == color.get().getUser().getUserID()){
                    //update and save color
                    color.get().setColorHEX(newColor);

                    try {
                        noteColorRepo.save(color.get());
                    } catch (Exception e) {
                        throw new DatabaseErrorException(e.getMessage());
                    }

                    return new ApiResponseDTO<String>(
                            true,
                            "color successfully updated",
                            null
                    );

                }else{throw new ResourceNotFoundException("A color by that ID associated with the provided email could not be found");}
            }else{throw new ResourceNotFoundException("A user associated with that email could not be found");}
        }else {throw new IdNotFoundException("A color associated with that ID could not be found");}
    }

    ///////////////////////
    /// DELETE METHOD/S ///
    ///////////////////////

    public ApiResponseDTO<String> deleteCoteColor(DeleteNoteColorDTO colorDTO, HttpServletRequest request) {
        //clean data
        String email= requestUtil.extractEmailClaim(request);
        int colorID= Integer.parseInt(colorDTO.getColorID().strip());

        //ensure color to be deletes exists and is associated with the provided email
        Optional<NoteColor> color = noteColorRepo.findById(colorID);
        Optional<UserTable> user = userRepo.findByEmail(email);

        if(color.isPresent()){
            if(user.isPresent()){
                if(user.get().getUserID() == color.get().getUser().getUserID()){

                    noteColorRepo.delete(color.get());

                    return new ApiResponseDTO<String>(
                            true,
                            "color successfull deleted",
                            null
                    );
                }else{throw new ResourceNotFoundException("A color by that ID associated with the provided email could not be found");}
            }else{throw new ResourceNotFoundException("A user by that email could not be found");}
        }else{throw new IdNotFoundException("A color by that ID could not be found");}
    }
}
