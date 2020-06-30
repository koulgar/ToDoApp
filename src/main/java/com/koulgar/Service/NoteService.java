package com.koulgar.Service;

import com.koulgar.Converter.NoteConverter;
import com.koulgar.Domain.Note;
import com.koulgar.Model.Note.NoteAddRequest;
import com.koulgar.Repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteConverter noteConverter;
    private final NoteRepository noteRepository;

    @Transactional
    public Note addNote(NoteAddRequest noteAddRequest) {
        Note newNote = noteConverter.convert(noteAddRequest);
        return noteRepository.save(newNote);
    }

}
