package ro.ubm.paw.testeonline.web;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Titlul documentatiei si butonul Authorize din Swagger UI, unde se lipeste tokenul JWT. */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Teste Online API")
                        .description("API pentru teste grilă: profesorii creează teste, studenții le rezolvă")
                        .version("1.0"))
                // descrie tipul de autentificare: header "Authorization: Bearer <token>"
                .components(new Components().addSecuritySchemes("bearer", new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")))
                // aplica schema de mai sus pe toate endpoint-urile
                .addSecurityItem(new SecurityRequirement().addList("bearer"));
    }
}
