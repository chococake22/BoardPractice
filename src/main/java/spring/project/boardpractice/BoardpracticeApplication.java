package spring.project.boardpractice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BoardpracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardpracticeApplication.class, args);
	}

}
