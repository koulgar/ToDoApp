package com.koulgar.Controller;

import com.koulgar.Model.Note.NoteAddRequest;
import com.koulgar.Model.Note.NoteAddResponse;
import com.koulgar.Service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("notes")
public class NoteController {

    private final NoteService noteService;

    @PostMapping("/add-note")
    @ResponseStatus(HttpStatus.CREATED)
    public NoteAddResponse addNote(@Valid @RequestBody NoteAddRequest noteAddRequest) {
        return noteService.addNote(noteAddRequest);
    }
}