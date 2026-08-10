package com.example.notesAPI.UnitTests.controller;

import com.example.notesAPI.controller.NoteController;
import com.example.notesAPI.dto.Note.CreateNoteDTO;
import com.example.notesAPI.service.NoteService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NoteControllerTest {
    //Mock Dependencies
    @Mock
    private NoteService service;
    @InjectMocks
    private NoteController controller;

    private HttpServletRequest request;


//TEST NAMING CONVENTION: "method_scenario_expected"//

    /// ////////////////
    /// createNote() ///
    /// ////////////////

    @Test
    void createNote_RequestBodyProperlyFormed_ServiceRunsOnce() {
        // ARRANGE //
        CreateNoteDTO createDTO = new CreateNoteDTO(
                Optional.of("test title"),
                Optional.of("test contest"),
                Optional.of(1),
                Optional.of(1));

        // ACT //
        controller.createNote(createDTO, request);

        // ASSERT //
        verify(service, times(1)).createNote(createDTO,request);
    }

    @Test
    void createNote_TitleAndContentAreBothBlank_IllegalArgumentExceptionThrown() {
        // ARRANGE //
        CreateNoteDTO createDTO = new CreateNoteDTO(
                Optional.of(" "),
                Optional.of(" "),
                Optional.of(1),
                Optional.of(1));

        // ACT & ASSERT//
        assertThrows(IllegalArgumentException.class, ()->{
            controller.createNote(createDTO,request);
        });
    }

    /// //////////////
    /// getNotes() ///
    /// //////////////

    @Test
    void getNotes_RequestBodyProperlyFormed_ServiceRunsOnce() {
        // ARRANGE //
            //Mock request already made
        // ACT //
        controller.getNotes(request);

        // ASSERT //
        verify(service,times(1)).getNotes(request);
    }

    /// /////////////
    /// getNote() ///
    /// /////////////

    @Test
    void getNote() {
        // ARRANGE //
        // ACT //
        // ASSERT //
    }

    /// ////////////////
    /// updateNote() ///
    /// ////////////////

    @Test
    void updateNote() {
        // ARRANGE //
        // ACT //
        // ASSERT //
    }

    /// ////////////////////////
    /// updatePinnedStatus() ///
    /// ////////////////////////

    @Test
    void updatePinnedStatus() {
        // ARRANGE //
        // ACT //
        // ASSERT //
    }

    /// ////////////////////////
    /// updateHiddenStatus() ///
    /// ////////////////////////

    @Test
    void updateHiddenStatus() {
        // ARRANGE //
        // ACT //
        // ASSERT //
    }

    /// /////////////////////////
    /// updateDeletedStatus() ///
    /// /////////////////////////

    @Test
    void updateDeletedStatus() {
        // ARRANGE //
        // ACT //
        // ASSERT //
    }

    /// //////////////////////////
    /// updateViewOnlyStatus() ///
    /// //////////////////////////

    @Test
    void updateViewOnlyStatus() {
        // ARRANGE //
        // ACT //
        // ASSERT //
    }

    /// /////////////////
    /// updateLabel() ///
    /// /////////////////

    @Test
    void updateLabel() {
        // ARRANGE //
        // ACT //
        // ASSERT //
    }

    /// /////////////////////
    /// updateNoteColor() ///
    /// /////////////////////

    @Test
    void updateNoteColor() {
        // ARRANGE //
        // ACT //
        // ASSERT //
    }

    /// /////////////////////
    /// updateCosmetics() ///
    /// /////////////////////

    @Test
    void updateCosmetics() {
        // ARRANGE //
        // ACT //
        // ASSERT //
    }

    /// ////////////////
    /// deleteNote() ///
    /// ////////////////

    @Test
    void deleteNote() {
        // ARRANGE //
        // ACT //
        // ASSERT //
    }
}