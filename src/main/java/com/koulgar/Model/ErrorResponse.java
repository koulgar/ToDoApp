package com.koulgar.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ErrorResponse {
    private String exception;
    private LocalDateTime timestamp;
    private List<String> errors = new ArrayList<>();
}
