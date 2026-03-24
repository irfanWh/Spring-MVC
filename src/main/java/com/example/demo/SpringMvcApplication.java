package com.example.demo;

import com.example.demo.entities.*;
import com.example.demo.repositories.*;
import com.example.demo.repository.ConsultationRepository;
import com.example.demo.repository.MedecinRepository;
import com.example.demo.repository.PatientRepository;
import com.example.demo.repository.RendezVousRepository;
import com.example.demo.services.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.example.demo.repository.ProductRepository;

import java.util.ArrayList;
import java.util.Date;

@SpringBootApplication //(exclude = org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class)
public class SpringMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(com.example.demo.SpringMvcApplication.class, args);
    }

    @Bean
    CommandLineRunner start(ProductRepository productRepository,
                            AccountService accountService,
                            PatientRepository patientRepository,
                            MedecinRepository medecinRepository,
                            RendezVousRepository rendezVousRepository,
                            ConsultationRepository consultationRepository) {
        return args -> {
            // Seed products
            productRepository.save(new Product(null, "Computer", 3500, 10));
            productRepository.save(new Product(null, "Printer", 1200, 5));
            productRepository.save(new Product(null, "SmartPhone", 2500, 8));
            productRepository.save(new Product(null, "Tablet", 1800, 12));
            productRepository.save(new Product(null, "Monitor", 2200, 7));
            productRepository.save(new Product(null, "Keyboard", 150, 30));
            productRepository.save(new Product(null, "Mouse", 80, 50));
            productRepository.save(new Product(null, "Headset", 350, 20));
            productRepository.save(new Product(null, "Webcam", 450, 15));
            productRepository.save(new Product(null, "Speaker", 600, 10));
            productRepository.save(new Product(null, "Hard Drive", 900, 25));
            productRepository.save(new Product(null, "Router", 700, 18));
            productRepository.findAll().forEach(p -> System.out.println(p.toString()));

            // Seed roles
            accountService.addNewRole(new AppRole(null, "ROLE_USER"));
            accountService.addNewRole(new AppRole(null, "ROLE_ADMIN"));

            // Seed users
            accountService.addNewUser(new AppUser(null, "user1", "1234", true, new ArrayList<>()));
            accountService.addNewUser(new AppUser(null, "admin", "1234", true, new ArrayList<>()));

            // Assign roles
            accountService.addRoleToUser("user1", "ROLE_USER");
            accountService.addRoleToUser("admin", "ROLE_ADMIN");
            accountService.addRoleToUser("admin", "ROLE_USER");

            // Seed patients
            Patient p1 = patientRepository.save(new Patient(null, "Hassan", new Date(), true));
            Patient p2 = patientRepository.save(new Patient(null, "Mohamed", new Date(), false));
            patientRepository.save(new Patient(null, "Fatima", new Date(), true));

            // Seed medecins
            Medecin m1 = medecinRepository.save(new Medecin(null, "Dr. Alami", "alami@med.com", "Cardiology"));
            Medecin m2 = medecinRepository.save(new Medecin(null, "Dr. Bennani", "bennani@med.com", "Dermatology"));

            // Seed rendez-vous
            rendezVousRepository.save(new RendezVous(null, new Date(), StatusRendezVous.PENDING, p1, m1));
            RendezVous rv2 = rendezVousRepository.save(new RendezVous(null, new Date(), StatusRendezVous.DONE, p2, m2));

            // Seed consultations
            consultationRepository.save(new Consultation(null, new Date(), "General checkup - all clear", rv2));
        };
    }
}
