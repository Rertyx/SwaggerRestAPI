package ru.dimov.SwaggerAPI.service;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.OpenAPIV3Parser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OpenApiValidator {

    public boolean isValid(String content){
        try {
            OpenAPI openAPI = new OpenAPIV3Parser().readContents(content, null, null).getOpenAPI();

            return openAPI != null;
        }catch (Exception e){
            log.error("error", e);
            return false;
        }
    }
}
