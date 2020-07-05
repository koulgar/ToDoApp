package com.koulgar.Converter;

import com.koulgar.Domain.Note;
import com.koulgar.Model.Note.NoteAddRequest;
import com.koulgar.Utils.Clock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class AddNoteConverterTest {

    @InjectMocks
    private AddNoteConverter addNoteConverter;

    @Test
    public void it_should_convert_noteAddRequest_to_note() {
        Clock.freeze();
        //given
        NoteAddRequest noteAddRequest = NoteAddRequest.builder().userId("123123").content("test note").build();

        //when
        Note note = addNoteConverter.convert(noteAddRequest);

        //then
        assertThat(note.getOwnerId()).isEqualTo("123123");
        assertThat(note.getContent()).isEqualTo("test note");
        assertThat(note.getIsCompleted()).isEqualTo(false);
        assertThat(note.getCreatedDateTime()).isEqualTo(Clock.now());
        assertThat(note.getUpdatedDateTime()).isEqualTo(Clock.now());
        Clock.unfreeze();
    }
}