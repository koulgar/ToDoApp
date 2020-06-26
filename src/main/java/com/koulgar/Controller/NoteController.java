package com.koulgar.Controller;

import com.koulgar.Model.Note.NoteAddRequest;
import com.koulgar.Domain.Note;
import com.koulgar.Service.NoteWriteService;
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

    private final NoteWriteService noteWriteService;

    @PostMapping("/add-note")
    @ResponseStatus(HttpStatus.CREATED)
    public Note addNote(@Valid @RequestBody NoteAddRequest noteAddRequest) {
        return noteWriteService.addNote(noteAddRequest);
    }
}
