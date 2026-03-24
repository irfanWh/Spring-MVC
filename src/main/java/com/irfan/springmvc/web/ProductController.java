package com.example.demo.web;

import com.example.demo.entities.Product;
import com.example.demo.repositories.ProductRepository;
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
public class ProductController {
    private ProductRepository productRepository;

    @GetMapping("/user/index")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "5") int size,
                        @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        Page<Product> pageProducts = productRepository.findByNameContains(keyword, PageRequest.of(page, size));
        model.addAttribute("listProducts", pageProducts.getContent());
        model.addAttribute("pages", new int[pageProducts.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageProducts.getTotalPages());
        model.addAttribute("keyword", keyword);
        return "products";
    }

    @GetMapping("/admin/delete")
    public String delete(@RequestParam(name = "id") Long id,
                         @RequestParam(name = "page", defaultValue = "0") int page,
                         @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        productRepository.deleteById(id);
        return "redirect:/user/index?page=" + page + "&keyword=" + keyword;
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/user/index";
    }

    @GetMapping("/admin/formProducts")
    public String formProducts(Model model) {
        model.addAttribute("product", new Product());
        return "formProducts";
    }

    @GetMapping("/admin/edit")
    public String editProduct(Model model, @RequestParam(name = "id") Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) return "redirect:/user/index";
        model.addAttribute("product", product);
        return "formProducts";
    }

    @PostMapping("/admin/save")
    public String save(@Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return "formProducts";
        productRepository.save(product);
        return "redirect:/user/index?keyword=" + product.getName();
    }
}
