package com.koulgar.Domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    private String id;

    @Field
    private String username;

    @Field
    private String password;

    @Field
    private List<Note> notes = new ArrayList<>();;

    @Field
    private LocalDateTime createdDateTime;
}
