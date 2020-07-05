package com.koulgar.Converter;

import com.koulgar.Domain.Note;
import com.koulgar.Model.Note.NoteAddRequest;
import com.koulgar.Utils.Clock;
import org.springframework.stereotype.Component;

@Component
public class AddNoteConverter {
    public Note convert(NoteAddRequest noteAddRequest) {
        return Note.builder()
                .ownerId(noteAddRequest.getUserId())
                .content(noteAddRequest.getContent())
                .isCompleted(false)
                .createdDateTime(Clock.now())
                .updatedDateTime(Clock.now())
                .build();
    }

}
