package com.example.demo;

import java.io.File;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.dao.UserRepository;
import com.example.entity.User;

@EntityScan("com.example.entity")
@EnableJpaRepositories("com.example.dao")
@ComponentScan("com.example.service")
@SpringBootApplication
public class MediatequeApplication {

	public static final String UPLOADS_DIR = "uploads";
	
	public static void main(String[] args) {
		SpringApplication.run(MediatequeApplication.class, args);
	}
	
	 @Bean
	 CommandLineRunner demo(final UserRepository ur) {
	       return new CommandLineRunner() {
	            @Override
	            public void run(String... args) throws Exception {
	            	new File(UPLOADS_DIR).mkdirs();

	                if(ur.findByMailAndPassword("admin@admin.com","admin") == null){
	                    User user= new User("admin","admin", "admin@admin.com","admin","illimite");
	                    user.setRole("admin");
	                    user.setExpirer(false);
	                    user.setDateDebut(new Date());
	                    ur.save(user);
	                }
	            }
	        };
	   } 
}
