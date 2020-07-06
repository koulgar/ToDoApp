package com.koulgar.Service;

import com.koulgar.Converter.AddNoteConverter;
import com.koulgar.Domain.Note;
import com.koulgar.Exception.NoteNotFoundException;
import com.koulgar.Exception.UserDoesNotHavePermission;
import com.koulgar.Model.Note.NoteAddRequest;
import com.koulgar.Model.Note.NoteEditRequest;
import com.koulgar.Repository.NoteRepository;
import com.koulgar.Utils.Clock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class NoteServiceTest {

    @InjectMocks
    private NoteService noteService;

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private AddNoteConverter addNoteConverter;

    @Captor
    private ArgumentCaptor<Note> noteArgumentCaptor;

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

    @Test
    public void it_should_edit_note() {
        Clock.freeze();
        LocalDateTime now = Clock.now();
        //given
        NoteEditRequest noteEditRequest = NoteEditRequest.builder()
                .currentUserId("123123")
                .noteId("321321")
                .isCompleted(false)
                .content("Note content 2")
                .build();

        Note note = Note.builder()
                .id("321321")
                .ownerId("123123")
                .content("Note content 1")
                .isCompleted(false)
                .createdDateTime(now)
                .updatedDateTime(now)
                .build();

        Note noteToSave = Note.builder()
                .id("321321")
                .ownerId("123123")
                .content("Note content 2")
                .isCompleted(false)
                .createdDateTime(now)
                .updatedDateTime(now)
                .build();

        when(noteRepository.findById(noteEditRequest.getNoteId())).thenReturn(java.util.Optional.ofNullable(note));

        //when
        noteService.editNote(noteEditRequest);

        //then
        verify(noteRepository).findById(noteEditRequest.getNoteId());
        verify(noteRepository).save(noteArgumentCaptor.capture());
        Note capturedRequest = noteArgumentCaptor.getValue();
        assertThat(noteToSave.getIsCompleted()).isEqualTo(capturedRequest.getIsCompleted());
        assertThat(noteToSave.getContent()).isEqualTo(capturedRequest.getContent());
        assertThat(noteToSave.getId()).isEqualTo(capturedRequest.getId());
        Clock.unfreeze();
    }

    @Test
    public void it_should_throw_exception_when_editing_note_if_note_owner_id_does_not_match() {
        Clock.freeze();
        LocalDateTime now = Clock.now();
        //given
        NoteEditRequest noteEditRequest = NoteEditRequest.builder()
                .currentUserId("123123")
                .noteId("321321")
                .content("Note content")
                .build();

        Note note = Note.builder()
                .id("321321")
                .ownerId("222222")
                .content("Note content")
                .isCompleted(false)
                .createdDateTime(now)
                .updatedDateTime(now)
                .build();

        when(noteRepository.findById(noteEditRequest.getNoteId())).thenReturn(java.util.Optional.ofNullable(note));

        //when
        Throwable throwable = catchThrowable(() -> noteService.editNote(noteEditRequest));

        //then
        verify(noteRepository).findById(noteEditRequest.getNoteId());
        verifyNoMoreInteractions(noteRepository);
        assertThat(throwable).isInstanceOf(UserDoesNotHavePermission.class);
        assertThat(throwable.getMessage()).isEqualTo("Bu notu duzenleme yetkiniz bulunmamaktadir.");
        Clock.unfreeze();
    }

    @Test
    public void it_should_throw_exception_when_editing_note_if_note_id_is_not_valid() {
        Clock.freeze();
        LocalDateTime now = Clock.now();
        //given
        NoteEditRequest noteEditRequest = NoteEditRequest.builder()
                .currentUserId("123123")
                .noteId("321321")
                .content("Note content")
                .build();

        when(noteRepository.findById(noteEditRequest.getNoteId())).thenReturn(java.util.Optional.empty());

        //when
        Throwable throwable = catchThrowable(() -> noteService.editNote(noteEditRequest));

        //then
        verify(noteRepository).findById(noteEditRequest.getNoteId());
        verifyNoMoreInteractions(noteRepository);
        assertThat(throwable).isInstanceOf(NoteNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("Not bulunamadi.");
        Clock.unfreeze();
    }
}