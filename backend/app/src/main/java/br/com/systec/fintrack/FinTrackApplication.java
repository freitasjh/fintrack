package br.com.systec.fintrack;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
@OpenAPIDefinition(info = @Info(title = "Systec Financeiro Pessoal", version = "1.0.0"
		, description = "Sistema para controle financeiro pessoal"))
@SecurityScheme(name = "Authorization", scheme = "bearer",
		type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER)
public class FinTrackApplication {
	public static void main(String[] args) {
		SpringApplication.run(FinTrackApplication.class, args);
	}

}
