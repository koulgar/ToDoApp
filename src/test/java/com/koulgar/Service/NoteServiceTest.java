package com.koulgar.Service;

import com.koulgar.Converter.AddNoteConverter;
import com.koulgar.Domain.Note;
import com.koulgar.Model.Note.NoteAddRequest;
import com.koulgar.Repository.NoteRepository;
import com.koulgar.Utils.Clock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class NoteServiceTest {

    @InjectMocks
    private NoteService noteService;

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private AddNoteConverter addNoteConverter;

    @Test
    public void it_should_add_note() {
        Clock.freeze();
        LocalDateTime now = Clock.now();
        //given
        NoteAddRequest noteAddRequest = NoteAddRequest.builder()
                .userId("123123")
                .content("Note content")
                .build();

        Note note = Note.builder()
                .ownerId("123123")
                .content("Note content")
                .isCompleted(false)
                .createdDateTime(now)
                .updatedDateTime(now)
                .build();

        when(addNoteConverter.convert(noteAddRequest)).thenReturn(note);

        //when
        noteService.addNote(noteAddRequest);

        //then
        verify(noteRepository).save(note);
        Clock.unfreeze();
    }

}