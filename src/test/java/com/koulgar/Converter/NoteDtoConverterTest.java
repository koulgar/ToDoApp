package com.koulgar.Converter;

import com.koulgar.Domain.Note;
import com.koulgar.Model.Note.NoteDto;
import com.koulgar.Utils.Clock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class NoteDtoConverterTest {

    @InjectMocks
    private NoteDtoConverter noteDtoConverter;

    @Test
    public void it_should_convert_Note_to_NoteDto() {
        Clock.freeze();
        //given
        Note note = Note.builder()
                .id("123")
                .ownerId("222")
                .content("TEST NOTE")
                .isCompleted(false)
                .createdDateTime(Clock.now())
                .updatedDateTime(Clock.now())
                .build();

        //when
        NoteDto noteDto = noteDtoConverter.convert(note);

        //then
        assertThat(noteDto.getId()).isEqualTo("123");
        assertThat(noteDto.getContent()).isEqualTo("TEST NOTE");
        assertThat(noteDto.getIsCompleted()).isEqualTo(false);
        assertThat(noteDto.getCreatedDateTime()).isEqualTo(Clock.now());
        assertThat(noteDto.getUpdatedDateTime()).isEqualTo(Clock.now());
        Clock.unfreeze();
    }

    @Test
    public void it_should_convert_Note_List_to_NoteDto_List() {
        //given
        Note note1 = Note.builder()
                .id("123")
                .ownerId("111")
                .content("TEST NOTE 1")
                .isCompleted(false)
                .createdDateTime(Clock.now())
                .updatedDateTime(Clock.now())
                .build();
        Note note2 = Note.builder()
                .id("321")
                .ownerId("222")
                .content("TEST NOTE 2")
                .isCompleted(false)
                .createdDateTime(Clock.now())
                .updatedDateTime(Clock.now())
                .build();

        List<Note> notes = Arrays.asList(note1, note2);

        //when
        List<NoteDto> noteDtoList = noteDtoConverter.convertAll(notes);

        //then
        assertThat(noteDtoList.get(0).getId()).isEqualTo("123");
        assertThat(noteDtoList.get(0).getContent()).isEqualTo("TEST NOTE 1");
        assertThat(noteDtoList.get(1).getId()).isEqualTo("321");
        assertThat(noteDtoList.get(1).getContent()).isEqualTo("TEST NOTE 2");
    }
}