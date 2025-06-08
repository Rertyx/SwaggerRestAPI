package ru.dimov.SwaggerAPI.model;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiSpecCreateUpdateDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Version is required")
    private String version;

    @NotBlank(message = "Spec content is required")
    private Map<String, Object> spec;
}
