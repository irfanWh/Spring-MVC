package com.example.demo.web;

import com.example.demo.entities.Medecin;
import com.example.demo.repositories.MedecinRepository;
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
public class MedecinController {
    private MedecinRepository medecinRepository;

    @GetMapping("/user/medecins")
    public String medecins(Model model,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "5") int size) {
        Page<Medecin> pageMedecins = medecinRepository.findAll(PageRequest.of(page, size));
        model.addAttribute("listMedecins", pageMedecins.getContent());
        model.addAttribute("pages", new int[pageMedecins.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageMedecins.getTotalPages());
        return "medecins";
    }

    @GetMapping("/admin/formMedecins")
    public String formMedecins(Model model) {
        model.addAttribute("medecin", new Medecin());
        return "formMedecins";
    }

    @GetMapping("/admin/editMedecin")
    public String editMedecin(Model model, @RequestParam(name = "id") Long id) {
        Medecin medecin = medecinRepository.findById(id).orElse(null);
        if (medecin == null) return "redirect:/user/medecins";
        model.addAttribute("medecin", medecin);
        return "formMedecins";
    }

    @GetMapping("/admin/deleteMedecin")
    public String deleteMedecin(@RequestParam(name = "id") Long id,
                                @RequestParam(name = "page", defaultValue = "0") int page) {
        medecinRepository.deleteById(id);
        return "redirect:/user/medecins?page=" + page;
    }

    @PostMapping("/admin/saveMedecin")
    public String saveMedecin(@Valid Medecin medecin, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "formMedecins";
        medecinRepository.save(medecin);
        return "redirect:/user/medecins";
    }
}
