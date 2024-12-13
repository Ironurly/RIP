package main.controller;

import lombok.RequiredArgsConstructor;
import main.dto.ProductDto;
import main.entity.Product;
import main.service.ManagerService;
import main.service.ProductService;
import main.service.SessionService;
import main.utils.MessageConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final SessionService sessionService;
    private final ManagerService managerService;

    @GetMapping("/products")
    public String getProducts(Model model) {
        sessionService.setRole(model);
        model.addAttribute("products", productService.mapToProductDto(productService.findAll()));
        model.addAttribute("current_page", "products");
        return "products";
    }

    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id, Model model) {
        if (!productService.canDelete(id)) {
            model.addAttribute("error", MessageConstants.CANNOT_DELETE_PRODUCT);
            return getProducts(model);
        }
        productService.deleteById(id);
        return "redirect:/products";
    }

    @PostMapping("/products/edit/{id}")
    public String editManager(@PathVariable("id") Long id, Model model) {
        sessionService.setRole(model);
        if (Objects.equals(id, -1L)) {
            model.addAttribute("product", new Product());
        } else {
            model.addAttribute("product", productService.mapToProductDto(productService.findById(id).orElseThrow()));
        }
        model.addAttribute("managers", managerService.findAll());
        model.addAttribute("current_page", "products");
        return "products-edit";
    }

    @PostMapping("/products/save")
    public String saveManager(ProductDto productDto, Model model) {
        productService.save(productService.mapToProduct(productDto));
        return "redirect:/products";
    }
}
