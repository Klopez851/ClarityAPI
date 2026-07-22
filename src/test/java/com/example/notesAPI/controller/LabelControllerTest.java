package com.example.notesAPI.controller;

import com.example.notesAPI.dto.Label.CreateLabelDTO;
import com.example.notesAPI.dto.Label.DeleteLabelDTO;
import com.example.notesAPI.dto.Label.UpdateLabelDTO;
import com.example.notesAPI.service.LabelService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LabelControllerTest {

    //Mock Objects/Dependencies
    @Mock
    LabelService labelService;
    @InjectMocks
    LabelController labelController;

    @Mock
    private static HttpServletRequest request;

//TEST NAMING CONVENTION: "method_scenario_expected"//

    /// /////////////////
    /// CreateLabel() ///
    /// /////////////////

    @Test
    void createLabel_RequestBodyProperlyFormed_ServiceGetsCalledOnce() {
        //ARRANGE//
        CreateLabelDTO labelDTO = new CreateLabelDTO("Test Label Name");

        //ACT//
        labelController.createLabel(labelDTO, request);

        //ASSERT//
        verify(labelService, times(1)).createLabel(labelDTO,request);
    }

    @Test
    void createLabel_LabelNameisBlank_ThrowsIllegalArgumentException() {
        //ARRANGE//
        CreateLabelDTO labelDTO = new CreateLabelDTO(" ");

        //ACT & ASSERT//
        assertThrows(IllegalArgumentException.class,()->{
            labelController.createLabel(labelDTO,request);
        });
    }

    @Test
    void createLabel_LabelNameisNull_ThrowsIllegalArgumentException() {
        //ARRANGE//
        CreateLabelDTO labelDTO = new CreateLabelDTO(null);

        //ACT & ASSERT//
        assertThrows(IllegalArgumentException.class,()->{
            labelController.createLabel(labelDTO,request);
        });
    }

    /// /////////////////
    /// getLabels() ///
    /// /////////////////

    @Test
    void getLabels_RequestIsProperlyFormed_ServiceGetsCalledOnce() {
        //ARRANGE//
            //Mock request has already been made

        //ACT//
        labelController.getLabels(request);

        //ASSERT//
        verify(labelService, times(1)).getLabels(request);
    }

    /// /////////////////
    /// updateLabel() ///
    /// /////////////////

    @Test
    void updateLabel_RequestBodyIsProperlyFormed_ServiceGetsCalledOnce() {
        //ARRANGE//
        UpdateLabelDTO updateDTO = new UpdateLabelDTO("1","Test Label Name");

        //ACT//
        labelController.updateLabel(updateDTO, request);

        //ASSERT//
        verify(labelService, times(1)).updateLabel(updateDTO, request);
    }

    @Test
    void updateLabel_LabelIDisBlank_ThrowsIllegalArgumentException() {
        //ARRANGE//
        UpdateLabelDTO updateDTO = new UpdateLabelDTO(" ","Test Label Name");

        //ACT & ASSERT//
        assertThrows(IllegalArgumentException.class,()->{
            labelController.updateLabel(updateDTO,request);
        });
    }

    @Test
    void updateLabel_LabelIDisNull_ThrowsIllegalArgumentException() {
        //ARRANGE//
        UpdateLabelDTO updateDTO = new UpdateLabelDTO(null,"Test Label Name");

        //ACT & ASSERT//
        assertThrows(IllegalArgumentException.class,()->{
            labelController.updateLabel(updateDTO,request);
        });
    }

    @Test
    void updateLabel_LabelNameisBlank_ThrowsIllegalArgumentException() {
        //ARRANGE//
        UpdateLabelDTO updateDTO = new UpdateLabelDTO("1"," ");

        //ACT & ASSERT//
        assertThrows(IllegalArgumentException.class,()->{
            labelController.updateLabel(updateDTO,request);
        });
    }

    @Test
    void updateLabel_LabelNameisNull_ThrowsIllegalArgumentException() {
        //ARRANGE//
        UpdateLabelDTO updateDTO = new UpdateLabelDTO("1",null);

        //ACT & ASSERT//
        assertThrows(IllegalArgumentException.class,()->{
            labelController.updateLabel(updateDTO,request);
        });
    }

    /// /////////////////
    /// deleteLabel() ///
    /// /////////////////

    @Test
    void deleteLabel_RequestBodyIsProperlyFormed_ServiceGetsCalledOnce() {
        //ARRANGE//
        DeleteLabelDTO deleteDTO = new DeleteLabelDTO("1");

        //ACT//
        labelController.deleteLabel(deleteDTO,request);

        //ASSERT//
        verify(labelService,times(1)).deleteLabel(deleteDTO, request);
    }

    @Test
    void deleteLabel_LabelIDisBlank_ThrowsIllegalArgumentException() {
        //ARRANGE//
        DeleteLabelDTO deleteDTO = new DeleteLabelDTO(" ");

        //ACT & ASSERT//
        assertThrows(IllegalArgumentException.class,()->{
            labelController.deleteLabel(deleteDTO,request);
        });
    }

    @Test
    void deleteLabel_LabelIDisNull_ThrowsIllegalArgumentException() {
        //ARRANGE//
        DeleteLabelDTO deleteDTO = new DeleteLabelDTO(null);

        //ACT & ASSERT//
        assertThrows(IllegalArgumentException.class,()->{
            labelController.deleteLabel(deleteDTO,request);
        });
    }

    //TODO: Add tests to verify HTTP codes
}