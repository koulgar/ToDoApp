package com.koulgar.Converter;

import com.koulgar.Domain.Note;
import com.koulgar.Model.Note.NoteDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NoteDtoConverter {

    public NoteDto convert(Note note) {
        return NoteDto.builder()
                .id(note.getId())
                .content(note.getContent())
                .isCompleted(note.getIsCompleted())
                .createdDateTime(note.getCreatedDateTime())
                .updatedDateTime(note.getUpdatedDateTime())
                .build();
    }

    public List<NoteDto> convertAll(List<Note> notes) {
        return notes.stream().map(this::convert).collect(Collectors.toList());
    }
}
