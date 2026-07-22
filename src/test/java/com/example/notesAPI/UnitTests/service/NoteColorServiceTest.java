package com.example.notesAPI.UnitTests.service;

import com.example.notesAPI.repository.NoteColorRepository;
import com.example.notesAPI.repository.UserRepository;
import com.example.notesAPI.service.NoteColorService;
import com.example.notesAPI.utilClasses.RequestValidationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NoteColorServiceTest {

    //Mock Objects/Dependencies
    @Mock
    UserRepository userRepo;
    @Mock
    NoteColorRepository noteColorRepo;
    @Mock
    RequestValidationService requestValidationService;

    @InjectMocks
    NoteColorService noteColorService;

//test naming convention "method_scenario_expected"
    @Test
    void createNoteColor() {
        //Arrange
        //Act
        //Assert
    }

    @Test
    void getNoteColors() {
    }

    @Test
    void updateNoteColor() {
    }

    @Test
    void deleteCoteColor() {
    }
}