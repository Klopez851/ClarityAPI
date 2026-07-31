package com.example.notesAPI.UnitTests.controller;

import com.example.notesAPI.controller.NoteColorController;
import com.example.notesAPI.dto.noteColor.CreateNoteColorDTO;
import com.example.notesAPI.dto.noteColor.DeleteNoteColorDTO;
import com.example.notesAPI.dto.noteColor.UpdateNoteColorDTO;
import com.example.notesAPI.service.NoteColorService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NoteColorControllerTest {

    //Mock Objects/Dependencies
    @Mock
    private NoteColorService service;
    @InjectMocks
    private NoteColorController controller;

    @Mock
    private static HttpServletRequest request;

//TEST NAMING CONVENTION: "method_scenario_expected"//

    /// //////////////////////
    /// CreateNoteColor() ///
    /// //////////////////////

    @Test
    void createNoteColor_RequestBodyIsProperlyFormed_ServiceGetsCalledOnce() {
        //ARRANGE//
        CreateNoteColorDTO colorDTO = new CreateNoteColorDTO("#b5a2c8");

        //ACT//
        //call controller enpoint
        controller.createNoteColor(colorDTO, request);

        //ASSERT//
        //verify service gets called once
        verify(service, times(1)).createNoteColor(colorDTO, request);
    }

    @Test
    void createNoteColor_RequestBodyIsBlank_IllegalArgumentExceptionGetsThrown() {
        //ARRANGE//
        CreateNoteColorDTO colorDTO = new CreateNoteColorDTO(" ");

        //ACT & ASSERT//
        //assert exception gets thrown
        assertThrows(IllegalArgumentException.class, () -> {
            controller.createNoteColor(colorDTO, request);
        });

    }

    @Test
    void createNoteColor_RequestBodyIsNull_IllegalArgumentExceptionGetsThrown() {
        //ARRANGE//
        CreateNoteColorDTO colorDTO = new CreateNoteColorDTO(null);

        //ACT & ASSERT//
        //assert exception gets thrown
        assertThrows(IllegalArgumentException.class, () -> {
            controller.createNoteColor(colorDTO, request);
        });

    }

    /// ////////////////////
    /// GetNoteColors() ///
    /// ////////////////////

    @Test
    void getNoteColors_RequestProperlyFormed_ServiceCalledOnce() {
        //ARRANGE//
        //mock request already created

        //ACT//
        controller.getNoteColors(request);

        //VERIFY//
        verify(service, times(1)).getNoteColors(request);
    }

    /// //////////////////////
    /// UpdateNoteColor() ///
    /// //////////////////////

    @Test
    void updateNoteColor__RequestBodyIsProperlyFormed_ServiceGetsCalledOnce() {
        //ARRANGE//
        UpdateNoteColorDTO updateDTO = new UpdateNoteColorDTO("1", "#b5a2c8");

        //ACT//
        controller.updateNoteColor(updateDTO, request);

        //ASSERT//
        verify(service, times(1)).updateNoteColor(updateDTO, request);
    }

    @Test
    void updateNoteColor_ColorIDisBlank_IllegalArgumentExceptionGetsThrown() {
        //ARRANGE//
        UpdateNoteColorDTO updateDTO = new UpdateNoteColorDTO(" ", "#b5a2c8");

        //ACT & ASSERT//
        assertThrows(IllegalArgumentException.class, () -> {
            controller.updateNoteColor(updateDTO, request);
        });
    }

    @Test
    void updateNoteColor_ColorIDisNull_IllegalArgumentExceptionGetsThrown() {
        //ARRANGE//
        UpdateNoteColorDTO updateDTO = new UpdateNoteColorDTO(null, "#b5a2c8");

        //ACT & ASSERT//
        assertThrows(IllegalArgumentException.class, () -> {
            controller.updateNoteColor(updateDTO, request);
        });
    }

    @Test
    void updateNoteColor_NewColorIsBlank_IllegalArgumentExceptionGetsThrown() {
        //ARRANGE//
        UpdateNoteColorDTO updateDTO = new UpdateNoteColorDTO("1", " ");

        //ACT & ASSERT//
        assertThrows(IllegalArgumentException.class, () -> {
            controller.updateNoteColor(updateDTO, request);
        });
    }

    @Test
    void updateNoteColor_NewColorIsNull_IllegalArgumentExceptionGetsThrown() {
        //ARRANGE//
        UpdateNoteColorDTO updateDTO = new UpdateNoteColorDTO("1", null);

        //ACT & ASSERT//
        assertThrows(IllegalArgumentException.class, () -> {
            controller.updateNoteColor(updateDTO, request);
        });
    }

    /// //////////////////////
    /// DeleteNoteColor() ///
    /// //////////////////////

    @Test
    void deleteNoteColor_RequestBodyIsProperlyFormed_ServiceGetsCalledOnce() {
        //ARRANGE//
        DeleteNoteColorDTO deleteDTO = new DeleteNoteColorDTO("1");

        //ACT//
        controller.deleteNoteColor(deleteDTO, request);

        //ASSERT//
        verify(service, times(1)).deleteCoteColor(deleteDTO, request);
    }

    @Test
    void deleteNoteColor_ColorIDIsBlank_IllegalArgumentExceptionGetsThrown() {
        //ARRANGE//
        DeleteNoteColorDTO deleteDTO = new DeleteNoteColorDTO(" ");

        //ACT & ASSERT//
        assertThrows(IllegalArgumentException.class, () -> {
            controller.deleteNoteColor(deleteDTO, request);
        });
    }

    @Test
    void deleteNoteColor_ColorIDNull_IllegalArgumentExceptionGetsThrown() {
        //ARRANGE//
        DeleteNoteColorDTO deleteDTO = new DeleteNoteColorDTO(null);

        //ACT & ASSERT//
        assertThrows(IllegalArgumentException.class, () -> {
            controller.deleteNoteColor(deleteDTO, request);
        });
    }
}