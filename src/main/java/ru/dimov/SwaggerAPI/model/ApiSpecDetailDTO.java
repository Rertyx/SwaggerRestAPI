package ru.dimov.SwaggerAPI.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiSpecDetailDTO {
    private String spec;
    private String validationLog;
}
