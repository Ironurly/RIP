package main.repository;

import main.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    public List<Product> findByManagerId(Long managerId);
    public void deleteByManagerId(Long managerId);
}
