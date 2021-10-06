package com.koulgar.Controller;

import com.koulgar.Domain.Note;
import com.koulgar.Model.Note.NoteAddRequest;
import com.koulgar.Model.Note.NoteDeleteRequest;
import com.koulgar.Model.Note.NoteEditRequest;
import com.koulgar.Service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public Note addNote(@Valid @RequestBody NoteAddRequest noteAddRequest) {
        return noteService.addNote(noteAddRequest);
    }

    @PutMapping("/edit-note")
    @ResponseStatus(HttpStatus.OK)
    public void editNote(@Valid @RequestBody NoteEditRequest noteEditRequest) {
        noteService.editNote(noteEditRequest);
    }

    @DeleteMapping("/delete-note")
    @ResponseStatus(HttpStatus.OK)
    public void deleteNote(@Valid @RequestBody NoteDeleteRequest noteDeleteRequest) {
        noteService.deleteNote(noteDeleteRequest);
        noteService.deleteNote(noteDeleteRequest);
    }

}
