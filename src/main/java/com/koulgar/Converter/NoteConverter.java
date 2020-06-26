package com.koulgar.Converter;

import com.koulgar.Domain.Note;
import com.koulgar.Utils.Clock;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class NoteConverter {
    public Note convert(String content) {
        return Note.builder()
                .id(UUID.randomUUID().toString())
                .content(content)
                .isCompleted(false)
                .createdDateTime(Clock.now())
                .updatedDateTime(Clock.now())
                .build();
    }

}
