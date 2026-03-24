package com.example.demo.web;

import com.example.demo.entities.Patient;
import com.example.demo.repositories.PatientRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;

    @GetMapping("/user/patients")
    public String patients(Model model,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "5") int size,
                           @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        Page<Patient> pagePatients = patientRepository.findByNameContains(keyword, PageRequest.of(page, size));
        model.addAttribute("listPatients", pagePatients.getContent());
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pagePatients.getTotalPages());
        model.addAttribute("keyword", keyword);
        return "patients";
    }

    @GetMapping("/admin/deletePatient")
    public String deletePatient(@RequestParam(name = "id") Long id,
                                @RequestParam(name = "page", defaultValue = "0") int page,
                                @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        patientRepository.deleteById(id);
        return "redirect:/user/patients?page=" + page + "&keyword=" + keyword;
    }

    @GetMapping("/admin/formPatients")
    public String formPatients(Model model) {
        model.addAttribute("patient", new Patient());
        return "formPatients";
    }

    @GetMapping("/admin/editPatient")
    public String editPatient(Model model, @RequestParam(name = "id") Long id) {
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient == null) return "redirect:/user/patients";
        model.addAttribute("patient", patient);
        return "formPatients";
    }

    @PostMapping("/admin/savePatient")
    public String savePatient(@Valid Patient patient, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "formPatients";
        patientRepository.save(patient);
        return "redirect:/user/patients";
    }
}
