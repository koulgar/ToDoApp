package com.koulgar.Repository;

import com.koulgar.Domain.Note;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends CrudRepository<Note, String> {

    List<Note> getNotesByOwnerId(String userId);

}
