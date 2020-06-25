package com.koulgar.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.koulgar.Config.MessageSourceTestConfiguration;
import com.koulgar.Model.Note.NoteAddRequest;
import com.koulgar.Domain.Note;
import com.koulgar.Service.NoteService;
import com.koulgar.Utils.Clock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = NoteController.class)
@ContextConfiguration(classes = MessageSourceTestConfiguration.class)
public class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteService noteService;

    @Captor
    private ArgumentCaptor<NoteAddRequest> noteAddRequestArgumentCaptor;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void it_should_add_note() throws Exception {
        Clock.freeze();
        String now = Clock.now().toString();
        //given
        NoteAddRequest noteAddRequest = NoteAddRequest.builder()
                .content("Note item")
                .userId("123asd")
                .build();

        Note note = Note.builder()
                .id("123123123123")
                .content("Note item")
                .isCompleted(false)
                .createdDateTime(Clock.now())
                .updatedDateTime(Clock.now())
                .build();

        when(noteService.addNote(noteAddRequestArgumentCaptor.capture())).thenReturn(note);

        //when
        ResultActions response = mockMvc.perform(post("/notes/add-note")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(noteAddRequest)));

        //then
        verify(noteService).addNote(noteAddRequestArgumentCaptor.capture());
        NoteAddRequest capturedRequest = noteAddRequestArgumentCaptor.getValue();
        assertThat(capturedRequest).isEqualToComparingFieldByField(noteAddRequest);

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("123123123123"))
                .andExpect(jsonPath("$.content").value("Note item"))
                .andExpect(jsonPath("$.isCompleted").value(false))
                .andExpect(jsonPath("$.createdDateTime").value(now))
                .andExpect(jsonPath("$.updatedDateTime").value(now));
        Clock.unfreeze();
    }

    @Test
    public void it_should_throw_exception_when_adding_note_empty() throws Exception {
        NoteAddRequest noteAddRequest = NoteAddRequest.builder()
                .content("")
                .userId("")
                .build();

        //when
        ResultActions resultActions = mockMvc.perform(post("/notes/add-note")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(noteAddRequest)))
                .andExpect(status().isBadRequest());

        //then
        verifyNoInteractions(noteService);
        resultActions
                .andExpect(jsonPath("$.exception", is("MethodArgumentNotValidException")))
                .andExpect(jsonPath("$.errors", containsInAnyOrder(
                        "Kullanici idsi bos olamaz.",
                        "Not icerigi bos olamaz.")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }

    @Test
    public void it_should_throw_exception_when_adding_note_max_lenght_not_valid() throws Exception {
        NoteAddRequest noteAddRequest = NoteAddRequest.builder()
                .content("Lorem ipsum dolor sit amet, consectetuer adipiscing elit. " +
                        "enean commodo ligula eget dolor. " +
                        "Aenean massa. Cum sociis natoque penatibus et magnis dis po.")
                .userId("")
                .build();

        //when
        ResultActions resultActions = mockMvc.perform(post("/notes/add-note")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(noteAddRequest)))
                .andExpect(status().isBadRequest());

        //then
        verifyNoInteractions(noteService);
        resultActions
                .andExpect(jsonPath("$.exception", is("MethodArgumentNotValidException")))
                .andExpect(jsonPath("$.errors", containsInAnyOrder(
                        "Kullanici idsi bos olamaz.",
                        "Not uzunlugu 150 karakteri asamaz.")))
                .andExpect(jsonPath("$.timestamp", notNullValue()));
    }
}