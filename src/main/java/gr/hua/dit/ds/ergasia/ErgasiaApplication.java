package gr.hua.dit.ds.ergasia;

//import gr.hua.dit.ds.ergasia.service.UserService;
//import gr.hua.dit.ds.ergasia.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
		(exclude = { SecurityAutoConfiguration.class,
		UserDetailsServiceAutoConfiguration.class })
public class ErgasiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErgasiaApplication.class, args);
	}

//	@Bean
//	CommandLineRunner initDatabase(UserService userService) {
//		return args -> {
//			userService.ensureAdminUser();
//		};
//	}
}
