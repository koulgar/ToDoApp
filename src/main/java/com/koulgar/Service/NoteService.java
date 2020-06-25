package com.koulgar.Service;

import com.koulgar.Converter.NoteConverter;
import com.koulgar.Domain.Note;
import com.koulgar.Domain.User;
import com.koulgar.Exception.UserNotFoundException;
import com.koulgar.Model.Note.NoteAddRequest;
import com.koulgar.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final UserRepository userRepository;
    private final NoteConverter noteConverter;

    public Note addNote(NoteAddRequest noteAddRequest) {
        User user = userRepository.getUserById(noteAddRequest.getUserId())
                .orElseThrow(() -> new UserNotFoundException("Kullanici mevcut degil."));
        Note newNote = noteConverter.convert(noteAddRequest.getContent());
        addNoteToUser(user, newNote);
        userRepository.save(user);
        return newNote;
    }

    private void addNoteToUser(User user, Note newNote) {
        List<Note> userNotes = user.getNotes();
        userNotes.add(newNote);
        user.setNotes(userNotes);
    }
}
