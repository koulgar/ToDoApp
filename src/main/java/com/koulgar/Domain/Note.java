package com.koulgar.Domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import java.util.Date;

@Document
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class Note {

    @Field
    public String item;

    @Field
    public Date createdDateTime;

    @Field
    public Date updatedDateTime;

}
