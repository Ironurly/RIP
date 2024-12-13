package main.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import main.dto.ProductDto;
import main.entity.Manager;
import main.entity.Product;
import main.repository.AppointmentRepository;
import main.repository.ManagerRepository;
import main.repository.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Builder
public class ProductService extends BaseService<Product> {
    private final ProductRepository productRepository;

    private final ManagerRepository managerRepository;
    private final AppointmentRepository appointmentRepository;

    @Override
    public JpaRepository<Product, Long> getRepository() {
        return productRepository;
    }

    public List<ProductDto> getProductsList() {
        return productRepository.findAll().stream().map(this::mapToProductDto).collect(Collectors.toList());
    }

    public ProductDto getProductDto(Long productId) {
        Product product = productRepository.findById(productId).orElse(null);
        return mapToProductDto(product);
    }

    public ProductDto mapToProductDto(Product product) {
        Manager manager = managerRepository.findById(product.getManagerId()).orElse(null);
        return ProductDto.builder()
                .id(product.getId())
                .managerId(manager.getId())
                .manager(manager.getName())
                .managerDescription(manager.getDescription())
                .name(product.getName())
                .duration(product.getDuration())
                .cost(product.getCost())
                .build();
    }

    public Product mapToProduct(ProductDto productDto) {
        return Product.builder()
                .id(productDto.getId())
                .managerId(productDto.getManagerId())
                .name(productDto.getName())
                .duration(productDto.getDuration())
                .cost(productDto.getCost())
                .build();
    }

    public List<ProductDto> mapToProductDto(List<Product> products) {
        return products.stream().map(this::mapToProductDto).collect(Collectors.toList());
    }

    public List<Product> findByManagerId(Long managerId) {
        return productRepository.findByManagerId(managerId);
    }

    public boolean canDelete(Long id) {
        return appointmentRepository.findByProductIdIn(List.of(id)).isEmpty();
    }

}
