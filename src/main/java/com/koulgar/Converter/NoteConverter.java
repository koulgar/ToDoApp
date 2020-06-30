package com.koulgar.Converter;

import com.koulgar.Domain.Note;
import com.koulgar.Model.Note.NoteAddRequest;
import com.koulgar.Utils.Clock;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class NoteConverter {
    public Note convert(NoteAddRequest noteAddRequest) {
        return Note.builder()
                .id(UUID.randomUUID().toString())
                .ownerId(noteAddRequest.getUserId())
                .content(noteAddRequest.getContent())
                .isCompleted(false)
                .createdDateTime(Clock.now())
                .updatedDateTime(Clock.now())
                .build();
    }

}
