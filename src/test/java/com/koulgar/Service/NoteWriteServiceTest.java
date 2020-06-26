package com.koulgar.Service;

import com.koulgar.Converter.NoteConverter;
import com.koulgar.Domain.Note;
import com.koulgar.Domain.User;
import com.koulgar.Exception.UserNotFoundException;
import com.koulgar.Model.Note.NoteAddRequest;
import com.koulgar.Repository.UserRepository;
import com.koulgar.Utils.Clock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class NoteWriteServiceTest {

    @InjectMocks
    private NoteWriteService noteWriteService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private NoteConverter noteConverter;

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
                .id("createdUuid")
                .content("Note content")
                .isCompleted(false)
                .createdDateTime(now)
                .updatedDateTime(now)
                .build();

        User user = User.builder().notes(new ArrayList<>()).build();

        when(userRepository.findById(noteAddRequest.getUserId())).thenReturn(Optional.ofNullable(user));
        when(noteConverter.convert(noteAddRequest.getContent())).thenReturn(note);

        //when
        Note noteResponse = noteWriteService.addNote(noteAddRequest);

        //then
        verify(userRepository).save(Objects.requireNonNull(user));
        assertThat(noteResponse).isEqualTo(note);
        assertThat(user.getNotes().get(0)).isEqualToComparingFieldByField(note);
        Clock.unfreeze();
    }

    @Test
    public void it_should_throw_exception_if_userId_is_not_valid() {
        //given
        NoteAddRequest noteAddRequest = NoteAddRequest.builder().build();

        when(userRepository.findById(noteAddRequest.getUserId())).thenReturn(Optional.empty());

        //when
        Throwable throwable = catchThrowable(() -> noteWriteService.addNote(noteAddRequest));

        //then
        verify(userRepository).findById(noteAddRequest.getUserId());
        assertThat(throwable).isInstanceOf(UserNotFoundException.class);
        assertThat(throwable.getMessage()).isEqualTo("Kullanici mevcut degil.");
        verifyNoMoreInteractions(userRepository);

    }
}