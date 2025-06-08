package ru.dimov.SwaggerAPI.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "api_spec")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ApiSpec {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String version;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String spec;

    @Column(name = "is_valid", nullable = false)
    private boolean isValid;

    @Column(name = "validation_log", columnDefinition = "TEXT")
    private String validationLog;

    @Column(name = "created_at", nullable = false)
    private Instant createAt = Instant.now();

}
