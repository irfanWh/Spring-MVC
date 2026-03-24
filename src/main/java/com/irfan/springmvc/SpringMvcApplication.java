package com.irfan.springmvc;

import com.irfan.springmvc.entities.Product;
import com.irfan.springmvc.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration;
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class })
public class SpringMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringMvcApplication.class, args);
    }

    @Bean
    public CommandLineRunner start(ProductRepository productRepository){
        return args -> {
            productRepository.save(new Product(null, "Laptop", 1500.0, 10));
            productRepository.save(new Product(null, "Smartphone", 800.0, 20));
            productRepository.save(new Product(null, "Tablet", 500.0, 15));
            productRepository.findAll().forEach(System.out::println);
        };
    }
}
