package br.com.systec.controle.financeiro;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Systec Financeiro Pessoal", version = "1.0.0"
		, description = "Sistema para controle financeiro pessoal"))
@SecurityScheme(name = "Authorization", scheme = "bearer",
		type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER)
public class ControleFinanceiroApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControleFinanceiroApplication.class, args);
	}

}
