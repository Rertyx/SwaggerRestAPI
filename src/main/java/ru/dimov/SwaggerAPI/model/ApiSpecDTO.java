package ru.dimov.SwaggerAPI.model;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiSpecDTO {

    private UUID id;
    private String name;
    private String version;
    private boolean isValid;
    private Instant createdAt;

}
