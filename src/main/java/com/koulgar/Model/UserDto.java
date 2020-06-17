package com.koulgar.Model;

import com.koulgar.Domain.Note;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    public String username;
    public List<Note> notes;
    public LocalDateTime createdDateTime;
}

