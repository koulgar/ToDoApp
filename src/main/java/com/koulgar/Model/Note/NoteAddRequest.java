package com.koulgar.Model.Note;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteAddRequest {

    @NotBlank(message = "user.id.not.null")
    private String userId;

    @NotBlank(message = "note.not.null")
    private String content;

}
