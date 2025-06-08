package ru.dimov.SwaggerAPI.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dimov.SwaggerAPI.model.ApiSpec;
import ru.dimov.SwaggerAPI.model.ApiSpecCreateUpdateDTO;
import ru.dimov.SwaggerAPI.model.ApiSpecDTO;
import ru.dimov.SwaggerAPI.repository.ApiSpecRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ApiSpecService {

    private final ObjectMapper objectMapper;
    private final ApiSpecRepository apiSpecRepository;

    private final OpenApiValidator openApiValidator;

    public ApiSpecService(ApiSpecRepository apiSpecRepository, OpenApiValidator openApiValidator, ObjectMapper objectMapper) {
        this.apiSpecRepository = apiSpecRepository;
        this.openApiValidator = openApiValidator;
        this.objectMapper = objectMapper;
    }

    public ApiSpec create(ApiSpecCreateUpdateDTO dto) throws JsonProcessingException {
        String specString = objectMapper.writeValueAsString(dto.getSpec());
        boolean valid = openApiValidator.isValid(specString);
        String validationLog = valid ? "Validation passed" : "Validation failed";

        ApiSpec apiSpec = new ApiSpec();
        apiSpec.setName(dto.getName());
        apiSpec.setVersion(dto.getVersion());
        apiSpec.setSpec(specString);
        apiSpec.setValid(valid);
        apiSpec.setValidationLog(validationLog);

        return apiSpecRepository.save(apiSpec);
    }

    public ApiSpec update (UUID id, ApiSpecCreateUpdateDTO dto) throws JsonProcessingException {
        String specString = objectMapper.writeValueAsString(dto.getSpec());
        ApiSpec apiSpec = apiSpecRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ApiSpec not found: " + id));

        apiSpec.setName(dto.getName());
        apiSpec.setVersion(dto.getVersion());
        apiSpec.setSpec(specString);
        boolean valid = openApiValidator.isValid(specString);
        String validationLog = valid ? "Validation passed" : "Validation failed";
        apiSpec.setValid(valid);
        apiSpec.setValidationLog(validationLog);
        return apiSpecRepository.save(apiSpec);
    }

    public List<ApiSpec> findAll(){
        return apiSpecRepository.findAll();
    }

    public Optional<ApiSpec> findById(UUID id) {
        return apiSpecRepository.findById(id);
    }

    @Transactional
    public Optional<ApiSpec> deleteById(UUID id){
        return apiSpecRepository.findById(id).map(spec ->{
            apiSpecRepository.delete(spec);
            return spec;
        });
    }

    public List<ApiSpec> findValid(){
        return apiSpecRepository.findByIsValidTrue();
    }
}
