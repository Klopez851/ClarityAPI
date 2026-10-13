package com.example.notesAPI.UnitTests.controller;

import com.example.notesAPI.controller.UITemplateController;
import com.example.notesAPI.dto.UITemplate.CreateTemplateDTO;
import com.example.notesAPI.dto.UITemplate.DeleteUITemplateDTO;
import com.example.notesAPI.dto.UITemplate.UpdateTemplateDTO;
import com.example.notesAPI.service.UITemplateService;
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
class UITemplateControllerTest {
    //Mock Objects/dependencies
    @Mock
    private UITemplateService service;
    @InjectMocks
    private UITemplateController controller;@Mock
    private HttpServletRequest request;

//TEST NAMING CONVENTION: "method_scenario_expected"//

    /// ////////////////////
    /// createTemplate() ///
    /// ////////////////////

    @Test
    void createTemplate_RequestBodyIsProperlyFormed_ServiceGetsCalledOnce() {
        //ARRANGE//
        CreateTemplateDTO createDTO = new CreateTemplateDTO(
                "proper name",
                "<insert template details here>"
        );
        //ACT//
        controller.createTemplate(createDTO, request);

        //ASSERT//
        verify(service, times(1)).createTemplate(createDTO, request);
    }

    @Test
    void createTemplate_TemplateNameIsEmpty_ThrowsIllegalArgumentException() {
        //ARRANGE//
        CreateTemplateDTO createDTO = new CreateTemplateDTO(
                " ",
                "<insert template details here>"
        );
        //ACT & ASSERT//
        assertThrows(IllegalArgumentException.class, () -> {
            controller.createTemplate(createDTO, request);
        });
    }

    @Test
    void createTemplate_TemplateNameIsNull_ThrowsIllegalArgumentException() {
        //ARRANGE//
        CreateTemplateDTO createDTO = new CreateTemplateDTO(
                null,
                "<insert template details here>"
        );
        //ACT & ASSERT//
        assertThrows(IllegalArgumentException.class, () -> {
            controller.createTemplate(createDTO, request);
        });
    }

    @Test
    void createTemplate_TemplateDetailsIsEmpty_ThrowsIllegalArgumentException() {
        //ARRANGE//
        CreateTemplateDTO createDTO = new CreateTemplateDTO(
                "proper name",
                " "
        );
        //ACT & ASSERT//
        assertThrows(IllegalArgumentException.class, () -> {
            controller.createTemplate(createDTO, request);
        });
    }

    @Test
    void createTemplate_TemplateDetailsIsNull_ThrowsIllegalArgumentException() {
        //ARRANGE//
        CreateTemplateDTO createDTO = new CreateTemplateDTO(
                "proper name",
                null
        );
        //ACT & ASSERT//
        assertThrows(IllegalArgumentException.class, () -> {
            controller.createTemplate(createDTO, request);
        });
    }

    /// //////////////////
    /// getTemplates() ///
    /// //////////////////

    @Test
    void getTemplates_ProperlyFormedRequestBody_ServiceGetsCalledOnce() {
        //ARRANGE//
        //Mock Request has been instantiated

        //ACT//
        controller.getTemplates(request);

        //ASSERT//
        verify(service, times(1)).getTemplates(request);
    }

    /// ///////////////////////////
    /// updateTemplateDetails() ///
    /// ///////////////////////////

    @Test
    void updateTemplateDetails_RequestBodyIsProperlyFormed_ServiceGetsCalledOnce() {
        //ARRANGE//
        UpdateTemplateDTO updateDTO = new UpdateTemplateDTO(
                "1",
                "<insert updated template details here>"
        );
        //ACT//
        controller.updateTemplateDetails(updateDTO, request);

        //ASSERT//
        verify(service, times(1)).updateTemplateDetails(updateDTO, request);
    }

    @Test
    void updateTemplateDetails_TemplateIdIsEmpty_ThrowsIllegalArgumentException() {
        //ARRANGE//
        UpdateTemplateDTO updateDTO = new UpdateTemplateDTO(
                " ",
                "<insert updated template details here>"
        );
        //ACT & ASSERT//
        assertThrows(IllegalArgumentException.class, () -> {
            controller.updateTemplateDetails(updateDTO, request);
        });
    }

    @Test
    void updateTemplateDetails_TemplateIdIsNull_ThrowsIllegalArgumentException() {
        //ARRANGE//
        UpdateTemplateDTO updateDTO = new UpdateTemplateDTO(
                null,
                "<insert updated template details here>"
        );
        //ACT & ASSERT//
        assertThrows(IllegalArgumentException.class, () -> {
            controller.updateTemplateDetails(updateDTO, request);
        });
    }

    @Test
    void updateTemplateDetails_NewTemplateDetailIsEmpty_ThrowsIllegalArgumentException() {
        //ARRANGE//
        UpdateTemplateDTO updateDTO = new UpdateTemplateDTO(
                "1",
                " "
        );
        //ACT & ASSERT//
        assertThrows(IllegalArgumentException.class, () -> {
            controller.updateTemplateDetails(updateDTO, request);
        });
    }

    @Test
    void updateTemplateDetails_NewTemplateDetailIsNull_ThrowsIllegalArgumentException() {
        //ARRANGE//
        UpdateTemplateDTO updateDTO = new UpdateTemplateDTO(
                "1",
                null
        );
        //ACT & ASSERT//
        assertThrows(IllegalArgumentException.class, () -> {
            controller.updateTemplateDetails(updateDTO, request);
        });
    }

    /// ////////////////////////
    /// updateTemplateName() ///
    /// ////////////////////////

    @Test
    void updateTemplateName_RequestBodyIsProperlyFormed_ServiceGetsCalledOnce() {
        //ARRANGE//
        UpdateTemplateDTO updateDTO = new UpdateTemplateDTO(
                "1",
                "new template name"
        );
        //ACT//
        controller.updateTemplateName(updateDTO, request);

        //ASSERT//
        verify(service, times(1)).updateTemplateName(updateDTO, request);
    }

    @Test
    void updateTemplateName_TemplateIdIsEmpty_ThrowsIllegalArgumentException() {
        //ARRANGE//
        UpdateTemplateDTO updateDTO = new UpdateTemplateDTO(
                " ",
                "new template name"
        );
        //ACT & ASSERT//
        assertThrows(IllegalArgumentException.class, () -> {
            controller.updateTemplateName(updateDTO, request);
        });
    }

    @Test
    void updateTemplateName_TemplateIdIsNull_ThrowsIllegalArgumentException() {
        //ARRANGE//
        UpdateTemplateDTO updateDTO = new UpdateTemplateDTO(
                null,
                "new template name"
        );
        //ACT & ASSERT//
        assertThrows(IllegalArgumentException.class, () -> {
            controller.updateTemplateName(updateDTO, request);
        });
    }

    @Test
    void updateTemplateName_NewTemplateNameIsEmpty_ThrowsIllegalArgumentException() {
        //ARRANGE//
        UpdateTemplateDTO updateDTO = new UpdateTemplateDTO(
                "1",
                " "
        );
        //ACT & ASSERT//
        assertThrows(IllegalArgumentException.class, () -> {
            controller.updateTemplateName(updateDTO, request);
        });
    }

    @Test
    void updateTemplateName_NewTemplateNameIsNull_ThrowsIllegalArgumentException() {
        //ARRANGE//
        UpdateTemplateDTO updateDTO = new UpdateTemplateDTO(
                "1",
                null
        );
        //ACT & ASSERT//
        assertThrows(IllegalArgumentException.class, () -> {
            controller.updateTemplateName(updateDTO, request);
        });
    }

    /// ////////////////////
    /// deleteTemplate() ///
    /// ////////////////////

    @Test
    void deleteTemplate_RequestBodyIsProperlyFormed_ServiceGetsCalledOnce() {
        //ARRANGE//
        DeleteUITemplateDTO deleteDTO = new DeleteUITemplateDTO("1");
        //ACT//
        controller.deleteTemplate(deleteDTO, request);

        //ASSERT//
        verify(service, times(1)).deleteTemplate(deleteDTO, request);
    }

    @Test
    void deleteTemplate_TemplateIdIsEmpty_ThrowsIllegalArgumentException() {
        //ARRANGE//
        DeleteUITemplateDTO deleteDTO = new DeleteUITemplateDTO(" ");        //ACT & ASSERT//
        //ARRANGE//
        assertThrows(IllegalArgumentException.class, () -> {
            controller.deleteTemplate(deleteDTO, request);
        });
    }

    @Test
    void deleteTemplate_TemplateIdIsNull_ThrowsIllegalArgumentException() {
        //ARRANGE//
        DeleteUITemplateDTO deleteDTO = new DeleteUITemplateDTO(null);
        //ACT & ASSERT//
        assertThrows(IllegalArgumentException.class, () -> {
            controller.deleteTemplate(deleteDTO, request);
        });
    }

}
//TODO: add test to ensure correctness of HTTTP codes