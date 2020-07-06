package com.koulgar.Model.Note;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteEditRequest {

    @NotBlank(message = "user.id.not.null")
    private String currentUserId;

    @NotBlank(message = "note.id.not.null")
    private String noteId;

    @NotNull(message = "note.status.not.null")
    private Boolean isCompleted;

    @Size(max = 150, message = "note.content.lenght.not.valid")
    @NotBlank(message = "note.not.null")
    private String content;

}
