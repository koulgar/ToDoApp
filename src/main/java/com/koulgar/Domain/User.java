package com.koulgar.Domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.data.couchbase.core.mapping.id.GeneratedValue;
import org.springframework.data.couchbase.core.mapping.id.GenerationStrategy;

import java.util.Date;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationStrategy.UNIQUE)
    @Field
    public String id;

    @Field
    public String username;

    @Field
    public String password;

    @Field
    public String token;

    @Field
    public List<Note> notes;

    @Field
    public Date createdDateTime;
}
