package com.koulgar.Model.Note;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteAddResponse {
    private String id;
    private String content;
    private Boolean isCompleted;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
}
