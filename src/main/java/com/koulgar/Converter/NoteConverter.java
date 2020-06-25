package com.koulgar.Converter;

import com.koulgar.Domain.Note;
import org.springframework.stereotype.Component;

@Component
public class NoteConverter {
    public Note convert(String content) {
        return new Note();
    }
}
