package com.example.notesAPI.dto.UITemplate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeleteUITemplateDTO {
    @Schema(name = "templateID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private String templateID;

    @JsonIgnore
    public boolean isValid() {
        if (templateID == null || templateID.isBlank()) {
            return false;
        }
        return true;
    }

}
