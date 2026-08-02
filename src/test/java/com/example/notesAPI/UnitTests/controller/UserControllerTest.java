package com.example.notesAPI.UnitTests.controller;

import com.example.notesAPI.controller.UserController;
import com.example.notesAPI.dto.User.UpdateEmailDTO;
import com.example.notesAPI.dto.User.UpdateUserInfoDTO;
import com.example.notesAPI.dto.User.UserInfoDTO;
import com.example.notesAPI.dto.User.UserLoginDTO;
import com.example.notesAPI.service.UserService;
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
class UserControllerTest {

    //Mock Dependencies
    @Mock
    private UserService service;
    @InjectMocks
    private UserController controller;

    @Mock
    private HttpServletRequest request;

//TEST NAMING CONVENTION: "method_scenario_expected"//

    /// ////////////////
    /// createUser() ///
    /// ////////////////

    @Test
    void createUser_RequestBodyProperlyFormed_ServiceGetsCalledOnce() {
        // ARRANGE //
        UserInfoDTO userDTO = new UserInfoDTO(
                "username",
                "email",
                "Password"
        );

        // ACT //
        controller.createUser(userDTO);

        // ASSERT //
        verify(service, times(1)).createUser(userDTO);
    }

    @Test
    void createUser_UsernameIsBlank_ThrowsIllegalArgumentException() {
        // ARRANGE //
        UserInfoDTO userDTO = new UserInfoDTO(
                " ",
                "email",
                "Password"
        );

        // ACT & ASSERT //
        assertThrows(IllegalArgumentException.class, ()->{
            controller.createUser(userDTO);
        });
    }

    @Test
    void createUser_UsernameIsNull_ThrowsIllegalArgumentException() {
        // ARRANGE //
        UserInfoDTO userDTO = new UserInfoDTO(
                null,
                "email",
                "Password"
        );

        // ACT & ASSERT //
        assertThrows(IllegalArgumentException.class, ()->{
            controller.createUser(userDTO);
        });
    }

    @Test
    void createUser_EmailIsBlank_ThrowsIllegalArgumentException() {
        // ARRANGE //
        UserInfoDTO userDTO = new UserInfoDTO(
                "username",
                " ",
                "Password"
        );

        // ACT & ASSERT //
        assertThrows(IllegalArgumentException.class, ()->{
            controller.createUser(userDTO);
        });
    }

    @Test
    void createUser_EmailIsNull_ThrowsIllegalArgumentException() {
        // ARRANGE //
        UserInfoDTO userDTO = new UserInfoDTO(
                "username",
                null,
                "Password"
        );

        // ACT & ASSERT //
        assertThrows(IllegalArgumentException.class, ()->{
            controller.createUser(userDTO);
        });
    }

    @Test
    void createUser_PasswordIsBlank_ThrowsIllegalArgumentException() {
        // ARRANGE //
        UserInfoDTO userDTO = new UserInfoDTO(
                "username",
                "email",
                " "
        );

        // ACT & ASSERT //
        assertThrows(IllegalArgumentException.class, ()->{
            controller.createUser(userDTO);
        });
    }

    @Test
    void createUser_PasswordIsNull_ThrowsIllegalArgumentException() {
        // ARRANGE //
        UserInfoDTO userDTO = new UserInfoDTO(
                "username",
                "email",
                null
        );

        // ACT & ASSERT //
        assertThrows(IllegalArgumentException.class, ()->{
            controller.createUser(userDTO);
        });
    }


    /// ///////////
    /// login() ///
    /// ///////////

    @Test
    void login_RequestBodyProperlyFormed_ServiceGetsCalledOnce() {
        // ARRANGE //
        UserLoginDTO loginDTO = new UserLoginDTO(
                "email",
                "Password"
        );

        // ACT //
        controller.login(loginDTO);

        // ASSERT //
        verify(service, times(1)).verify(loginDTO);
    }

    @Test
    void login_EmailIsBlank_ThrowsIllegalArgumentException() {
        // ARRANGE //
        UserLoginDTO loginDTO = new UserLoginDTO(
                " ",
                "Password"
        );

        // ACT & ASSERT //
        assertThrows(IllegalArgumentException.class, ()->{
            controller.login(loginDTO);
        });
    }

    @Test
    void login_EmailIsNull_ThrowsIllegalArgumentException() {
        // ARRANGE //
        UserLoginDTO loginDTO = new UserLoginDTO(
                null,
                "Password"
        );

        // ACT & ASSERT //
        assertThrows(IllegalArgumentException.class, ()->{
            controller.login(loginDTO);
        });
    }

    @Test
    void login_PasswordIsBlank_ThrowsIllegalArgumentException() {
        // ARRANGE //
        UserLoginDTO loginDTO = new UserLoginDTO(
                "email",
                " "
        );

        // ACT & ASSERT //
        assertThrows(IllegalArgumentException.class, ()->{
            controller.login(loginDTO);
        });
    }

    @Test
    void login_PasswordIsNull_ThrowsIllegalArgumentException() {
        // ARRANGE //
        UserLoginDTO loginDTO = new UserLoginDTO(
                "email",
                null
        );

        // ACT & ASSERT //
        assertThrows(IllegalArgumentException.class, ()->{
            controller.login(loginDTO);
        });
    }

    /// /////////////
    /// getUser() ///
    /// /////////////

    @Test
    void getUser_RequestProperlyFormed_ServiceGetsCalledOnce() {
        // ARRANGE //
            //mock request already instantiated

        // ACT //
        controller.getUser(request);

        // ASSERT //
        verify(service,times(1)).getUser(request);
    }

    /// /////////////////
    /// updateEmail() ///
    /// /////////////////

    @Test
    void updateEmail_RequestBodyProperlyFormed_ServiceGetsCalledOnce() {
        // ARRANGE //
        UpdateEmailDTO emailDTO = new UpdateEmailDTO("email");

        // ACT //
        controller.updateEmail(emailDTO,request);

        // ASSERT //
        verify(service, times(1)).updateEmail(emailDTO,request);
    }

    @Test
    void updateEmail_EmailIsBlank_ThrowsIllegalArgumentException() {
        // ARRANGE //
        UpdateEmailDTO emailDTO = new UpdateEmailDTO(" ");

        // ACT & ASSERT //
        assertThrows(IllegalArgumentException.class, ()->{
            controller.updateEmail(emailDTO,request);
        });
    }

    @Test
    void updateEmail_EmailIsNull_ThrowsIllegalArgumentException() {
        // ARRANGE //
        UpdateEmailDTO emailDTO = new UpdateEmailDTO(null);

        // ACT & ASSERT //
        assertThrows(IllegalArgumentException.class, ()->{
            controller.updateEmail(emailDTO,request);
        });
    }

    /// ////////////////////
    /// updateUsername() ///
    /// ////////////////////

    @Test
    void updateUsername_RequestBodyProperlyFormed_ServiceGetsCalledOnce() {
        // ARRANGE //
        UpdateUserInfoDTO updateDTO = new UpdateUserInfoDTO("new username");

        // ACT //
        controller.updateUsername(updateDTO,request);

        // ASSERT //
        verify(service, times(1)).updateUsername(updateDTO,request);
    }

    @Test
    void updateUsername_UsernameIsBlank_ThrowsIllegalArgumentException() {
        // ARRANGE //
        UpdateUserInfoDTO updateDTO = new UpdateUserInfoDTO(" ");

        // ACT & ASSERT //
        assertThrows(IllegalArgumentException.class, ()->{
            controller.updateUsername(updateDTO,request);
        });
    }

    @Test
    void updateUsername_UsernameIsNull_ThrowsIllegalArgumentException() {
        // ARRANGE //
        UpdateUserInfoDTO updateDTO = new UpdateUserInfoDTO(null);

        // ACT & ASSERT //
        assertThrows(IllegalArgumentException.class, ()->{
            controller.updateUsername(updateDTO,request);
        });
    }

    /// ////////////////////
    /// updatePassword() ///
    /// ////////////////////

    @Test
    void updatePassword_RequestBodyProperlyFormed_ServiceGetsCalledOnce() {
        // ARRANGE //
        UpdateUserInfoDTO updateDTO = new UpdateUserInfoDTO("new password");

        // ACT //
        controller.updatePassword(updateDTO,request);

        // ASSERT //
        verify(service, times(1)).updatePassword(updateDTO,request);
    }

    @Test
    void updatePassword_PasswordIsBlank_ThrowsIllegalArgumentException() {
        // ARRANGE //
        UpdateUserInfoDTO updateDTO = new UpdateUserInfoDTO(" ");

        // ACT & ASSERT //
        assertThrows(IllegalArgumentException.class, ()->{
            controller.updatePassword(updateDTO,request);
        });
    }

    @Test
    void updatePassword_PasswordIsNull_ThrowsIllegalArgumentException() {
        // ARRANGE //
        UpdateUserInfoDTO updateDTO = new UpdateUserInfoDTO(null);

        // ACT & ASSERT //
        assertThrows(IllegalArgumentException.class, ()->{
            controller.updatePassword(updateDTO,request);
        });
    }

    /// ////////////////
    /// deleteUser() ///
    /// ////////////////

    @Test
    void deleteUser_RequestProperlyFormed_ServiceGetsCalledOnce() {
        // ARRANGE //
        //mock request already instantiated

        // ACT //
        controller.deleteUser(request);

        // ASSERT //
        verify(service,times(1)).deleteUser(request);
    }

    //TODO: add tests to assert proper http codes
}