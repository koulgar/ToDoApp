package com.koulgar.Service;

import com.koulgar.Converter.AddNoteConverter;
import com.koulgar.Domain.Note;
import com.koulgar.Exception.NoteNotFoundException;
import com.koulgar.Exception.UserDoesNotHavePermission;
import com.koulgar.Model.Note.NoteAddRequest;
import com.koulgar.Model.Note.NoteDeleteRequest;
import com.koulgar.Model.Note.NoteEditRequest;
import com.koulgar.Repository.NoteRepository;
import com.koulgar.Utils.Clock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final AddNoteConverter addNoteConverter;
    private final NoteRepository noteRepository;

    @Transactional
    public Note addNote(NoteAddRequest noteAddRequest) {
        Note newNote = addNoteConverter.convert(noteAddRequest);
        return noteRepository.save(newNote);
    }

    public void editNote(NoteEditRequest noteEditRequest) {
        Note note = noteRepository.findById(noteEditRequest.getNoteId()).orElseThrow(() -> new NoteNotFoundException("Not bulunamadi."));
        if (!note.getOwnerId().equals(noteEditRequest.getCurrentUserId())) {
            throw new UserDoesNotHavePermission("Bu notu duzenleme yetkiniz bulunmamaktadir.");
        }
        Note noteToSave = editNoteBody(noteEditRequest, note);
        noteRepository.save(noteToSave);
    }

    private Note editNoteBody(NoteEditRequest noteEditRequest, Note note) {
        note.setContent(noteEditRequest.getContent());
        note.setIsCompleted(noteEditRequest.getIsCompleted());
        note.setUpdatedDateTime(Clock.now());
        return note;
    }

    public void deleteNote(NoteDeleteRequest noteDeleteRequest) {
        Note note = noteRepository.findById(noteDeleteRequest.getNoteId()).orElseThrow(() -> new NoteNotFoundException("Not bulunamadi."));
        if (!note.getOwnerId().equals(noteDeleteRequest.getCurrentUserId())) {
            throw new UserDoesNotHavePermission("Bu notu duzenleme yetkiniz bulunmamaktadir.");
        }
        noteRepository.deleteById(noteDeleteRequest.getNoteId());
    }
}
