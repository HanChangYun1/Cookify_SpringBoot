package Cook.Cookify_SpringBoot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CookifySpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(CookifySpringBootApplication.class, args);
	}

}
