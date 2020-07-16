package com.koulgar.Model.Note;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteDeleteRequest {

    @NotBlank(message = "user.id.not.null")
    private String currentUserId;

    @NotBlank(message = "note.id.not.null")
    private String noteId;
}
