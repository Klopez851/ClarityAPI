package com.example.notesAPI.controller;

import com.example.notesAPI.dto.noteColor.CreateNoteColorDTO;
import com.example.notesAPI.dto.noteColor.UpdateNoteColorDTO;
import com.example.notesAPI.service.NoteColorService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NoteColorControllerTest {

    //Mock Objects/Dependencies
    @Mock
    NoteColorService noteColorService;
    @InjectMocks
    NoteColorController noteColorController;

    @Mock
    private static HttpServletRequest request;

//TEST NAMING CONVENTION: "method_scenario_expected"//

    /////////////////////////
    /// CreateNoteColor() ///
    /////////////////////////

    @Test
    void createNoteColor_RequestBodyIsProperlyFormed_ServiceGetsCalledOnce(){
        //ARRANGE//
        CreateNoteColorDTO colorDTO = new CreateNoteColorDTO("#b5a2c8");

        //ACT//
        //call controller enpoint
        noteColorController.createNoteColor(colorDTO, request);

        //ASSERT//
        //verify service gets called once
        verify(noteColorService, times(1)).createNoteColor(colorDTO,request);
    }

    @Test
    void createNoteColor_RequestBodyIsBlank_IllegalArgumentExceptionGetsThrown(){
        //ARRANGE//
        CreateNoteColorDTO colorDTO = new CreateNoteColorDTO(" ");

        //ACT & ASSERT//
        //assert exception gets thrown
        assertThrows(IllegalArgumentException.class, ()->{
            noteColorController.createNoteColor(colorDTO,request);
        });

    }

    @Test
    void createNoteColor_RequestBodyIsNull_IllegalArgumentExceptionGetsThrown(){
        //ARRANGE//
        CreateNoteColorDTO colorDTO = new CreateNoteColorDTO(null);

        //ACT & ASSERT//
        //assert exception gets thrown
        assertThrows(IllegalArgumentException.class, ()->{
            noteColorController.createNoteColor(colorDTO,request);
        });

    }

    ///////////////////////
    /// GetNoteColors() ///
    ///////////////////////

    @Test
    void getNoteColors() {
        //ARRANGE//
            //mock request already created

        //ACT//
        noteColorController.getNoteColors(request);

        //VERIFY//
        verify(noteColorService, times(1)).getNoteColors(request);
    }

    /////////////////////////
    /// UpdateNoteColor() ///
    /////////////////////////

    @Test
    void updateNoteColor__RequestBodyIsProperlyFormed_ServiceGetsCalledOnce() {
        //ARRANGE//
        UpdateNoteColorDTO updateDTO = new UpdateNoteColorDTO("1", "#b5a2c8");

        //ACT//
        noteColorController.updateNoteColor(updateDTO,request);

        //ASSERT//
        verify(noteColorService,times(1)).updateNoteColor(updateDTO,request);
    }

    @Test
    void deleteNoteColor() {
    }
}