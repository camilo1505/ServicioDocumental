package mongo.proyect.servicioDocumental;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.Constants;

@SpringBootApplication
public class ServicioDocumentalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioDocumentalApplication.class, args);
	}

}
