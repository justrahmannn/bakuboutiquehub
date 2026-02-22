package com.atl.bakuboutiquehub.repository;

import com.atl.bakuboutiquehub.model.Boutique;
import com.atl.bakuboutiquehub.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByBoutique(Boutique boutique);
}
