package ru.dimov.SwaggerAPI.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.dimov.SwaggerAPI.model.ApiSpec;
import ru.dimov.SwaggerAPI.model.ApiSpecCreateUpdateDTO;
import ru.dimov.SwaggerAPI.model.ApiSpecDTO;
import ru.dimov.SwaggerAPI.service.ApiSpecService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/specs")
public class ApiSpecController {

    private final ApiSpecService apiSpecService;

    public ApiSpecController(ApiSpecService apiSpecService) {
        this.apiSpecService = apiSpecService;
    }

    @Operation(summary = "Создать спецификацию", description = "Создаёт новую OpenAPI спецификацию и сохраняет её в БД")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Успешно создана")
    })
    @PostMapping
    public ResponseEntity<ApiSpecDTO> create(@Valid @RequestBody ApiSpecCreateUpdateDTO dto) {
        try {
            ApiSpec saved = apiSpecService.create(dto);
            ApiSpecDTO response = toDto(saved);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Обновить спецификацию", description = "Обновляет существующую спецификацию по ID")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Успешно обновлена"),
            @ApiResponse(responseCode = "404", description = "Не найдено"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ApiSpecDTO> update(
            @Parameter(description = "Идентификатор спецификации")@PathVariable UUID id,
            @Valid @RequestBody ApiSpecCreateUpdateDTO dto){
        try {
            ApiSpec update = apiSpecService.update(id, dto);
            ApiSpecDTO response = toDto(update);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @Operation(summary = "Получить все спецификации", description = "Возвращает список всех OpenAPI спецификаций")
    @GetMapping
    public List<ApiSpecDTO> getAll() {
        return apiSpecService.findAll().stream().map(apiSpec -> toDto(apiSpec)
        ).toList();
    }

    @Operation(summary = "Получить спецификацию по ID", description = "Поиск и возврат по UUID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно найдено"),
            @ApiResponse(responseCode = "404", description = "Не найдено")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ApiSpecDTO> getById(
            @Parameter(description = "Идентификатор спецификации")@PathVariable UUID id) {
        return apiSpecService.findById(id)
                .map(this::toDto
                ).map(x -> ResponseEntity.status(HttpStatus.OK).body(x))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Удалить спецификацию", description = "Удаляет спецификацию по UUID и возвращает удалённую сущность")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешно удалено"),
            @ApiResponse(responseCode = "404", description = "Не найдено")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiSpecDTO> deleteById(
            @Parameter(description = "Идентификатор спецификации")@PathVariable UUID id) {
        return apiSpecService.deleteById(id)
                .map(this::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Получить все валидные спецификации", description = "Возвращает список всех валидных OpenAPI спецификаций")
    @GetMapping("/valid")
    public List<ApiSpecDTO> getValidSpecs() {
        return apiSpecService.findValid().stream().map(this::toDto).toList();
    }

    private ApiSpecDTO toDto(ApiSpec entity) {
        return new ApiSpecDTO(
                entity.getId(),
                entity.getName(),
                entity.getVersion(),
                entity.isValid(),
                entity.getCreateAt()
        );
    }
}
