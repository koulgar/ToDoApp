package com.koulgar.Domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import java.time.LocalDateTime;

@Document
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Note {

    @Id
    private String id;

    @Field
    private String item;

    @Field
    private Boolean isCompleted;

    @Field
    private LocalDateTime createdDateTime;

    @Field
    private LocalDateTime updatedDateTime;

}
