package com.koulgar.Domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import java.time.LocalDateTime;

@Document
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Note {

    @Field
    public String item;

    @Field
    public LocalDateTime createdDateTime;

    @Field
    public LocalDateTime updatedDateTime;

}
