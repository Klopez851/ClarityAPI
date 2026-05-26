package com.example.notesAPI.service;

import com.example.notesAPI.dto.ApiResponseDTO;
import com.example.notesAPI.dto.Label.DeleteLabelDTO;
import com.example.notesAPI.dto.Label.LabelDTO;
import com.example.notesAPI.dto.Label.CreateLabelDTO;
import com.example.notesAPI.dto.Label.UpdateLabelDTO;
import com.example.notesAPI.errorHandler.IdNotFoundException;
import com.example.notesAPI.errorHandler.ResourceNotFoundException;
import com.example.notesAPI.model.Label;
import com.example.notesAPI.model.UserTable;
import com.example.notesAPI.repository.LabelRepository;
import com.example.notesAPI.repository.UserRepository;
import com.example.notesAPI.utilClasses.RequestValidationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LabelService {

    /// CONSTANTS ///
    private final int MAX_LABEL_NAME_SIZE = 50;
    private final LabelRepository labelRepo;
    private final UserRepository userRepo;
    private final JWTService jwtService;
    private final RequestValidationService requestUtil;

    ////////////////////
    /// POST METHODS ///
    ////////////////////

    public ApiResponseDTO<String> createLabel(CreateLabelDTO userLabel, HttpServletRequest request){
        Label label;

        //clean data
        String labelName = userLabel.getLabel().strip();
        String email = requestUtil.extractEmailClaim(request);

        //find user
        Optional<UserTable> user = userRepo.findByEmail(email);

        // get user
        if (user.isEmpty()) {
            throw new IllegalArgumentException("Valid email needed to create label");
        }
        if(labelName.length()>MAX_LABEL_NAME_SIZE){
            throw new IllegalArgumentException("label is too long, it can be a maximum of "+MAX_LABEL_NAME_SIZE+" characters");
        }else{
            label = new Label(user.get(), labelName);
        }

        //store label
        labelRepo.save(label);

        return new ApiResponseDTO<>(
                true,
                "label successfully created", null);
    }

    ///////////////////
    /// GET METHODS ///
    ///////////////////

    public ApiResponseDTO<List<LabelDTO>> getLabels(HttpServletRequest request) {
        //clean email
        String email = requestUtil.extractEmailClaim(request);

        //get user
        Optional<UserTable> user = userRepo.findByEmail(email);

        if (user.isEmpty()) {
            throw new ResourceNotFoundException("Please provide a valid email to fetch labels");
        }

        //get all labels associated with that user
        List<LabelDTO> labels = labelRepo.findAllByUser(user.get().getUserID());

        //return them
        return new ApiResponseDTO<List<LabelDTO>>(true, "labels successfully fetched", labels);
    }

    /////////////////////
    /// PATCH METHODS ///
    /////////////////////

    public ApiResponseDTO<String> updateLabel(UpdateLabelDTO reqLabel) {
        //clean data
        String reqLabelName = reqLabel.getLabelName().strip();
        int reqLabelID = Integer.parseInt(reqLabel.getLabelID());

        //get label from db
        Optional<Label> label = labelRepo.findById(reqLabelID);

        //make sure label exists the same
        if (label.isEmpty()) {
            throw new IdNotFoundException("A label with that ID doesn't exist");
        }

        //make sure the labels are the same
        if (label.get().getLabelName().equals(reqLabelName)) {
            return new ApiResponseDTO<String>(true,
                    "The label name in your request matches the existing name in the database.", null);
        }
        //update label
        label.get().setLabelName(reqLabelName);

        //save label
        labelRepo.save(label.get());

        return new ApiResponseDTO<String>(
                true,
                "Label successfully updated",
                label.get().getLabelName());
    }

    //////////////////////
    /// DELETE METHODS ///
    //////////////////////

    public ApiResponseDTO<String> deleteLabel(DeleteLabelDTO reqLabel, HttpServletRequest request) {
        //clean/format data
        String email = requestUtil.extractEmailClaim(request);
        int labelID = Integer.parseInt(reqLabel.getLabelID().strip());

        //ensure label and user exists
        Optional<Label> label = labelRepo.findById(labelID);
        Optional<UserTable> user = userRepo.findByEmail(email);

        //delete if label exists, if user exists, and is label is associated with the user
        if(label.isPresent()){
            if(user.isPresent()){
                if(label.get().getUser().getUserID() == user.get().getUserID()){
                    //delete label
                    labelRepo.delete(label.get());
                    return new ApiResponseDTO<String>(true, "label successfully deleted", null);

                }else{throw new ResourceNotFoundException("Could not such label associated with that user");}
            }else{throw new ResourceNotFoundException("A user with the email "+email+" could not be found");}
        }else{throw new IdNotFoundException("A label with that id could not be found");}
    }
}
