package ru.dimov.SwaggerAPI.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.dimov.SwaggerAPI.model.ApiSpec;

import java.util.List;
import java.util.UUID;


public interface ApiSpecRepository extends JpaRepository<ApiSpec, UUID>{

    List<ApiSpec> findByIsValidTrue();
}
