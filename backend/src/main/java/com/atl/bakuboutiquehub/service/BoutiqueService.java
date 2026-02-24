package com.atl.bakuboutiquehub.service;

import com.atl.bakuboutiquehub.dto.BoutiqueDTO;
import com.atl.bakuboutiquehub.dto.ProductDTO;
import com.atl.bakuboutiquehub.model.Boutique;
import com.atl.bakuboutiquehub.model.Product;
import com.atl.bakuboutiquehub.model.User;
import com.atl.bakuboutiquehub.repository.BoutiqueRepository;
import com.atl.bakuboutiquehub.repository.ProductRepository;
import com.atl.bakuboutiquehub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoutiqueService {

    @Autowired
    private BoutiqueRepository boutiqueRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public BoutiqueDTO createBoutique(BoutiqueDTO dto, String ownerEmail) {
        User owner = userRepository.findByEmail(ownerEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + ownerEmail));

        Boutique boutique = Boutique.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .address(dto.getAddress())
                .contactNumber(dto.getContactNumber())
                .workingHours(dto.getWorkingHours())
                .categories(dto.getCategories())
                .brands(dto.getBrands())
                .owner(owner)
                .build();

        Boutique saved = boutiqueRepository.save(boutique);
        return mapToDTO(saved);
    }

    public List<BoutiqueDTO> getAllBoutiques() {
        return boutiqueRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO addProduct(Long boutiqueId, ProductDTO dto) {
        Boutique boutique = boutiqueRepository.findById(boutiqueId)
                .orElseThrow(() -> new RuntimeException("Boutique not found"));

        Product product = Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .imageUrl(dto.getImageUrl())
                .stockCount(dto.getStockCount())
                .boutique(boutique)
                .build();

        Product saved = productRepository.save(product);
        return mapToProductDTO(saved);
    }

    public List<ProductDTO> getProductsByBoutique(Long boutiqueId) {
        Boutique boutique = boutiqueRepository.findById(boutiqueId)
                .orElseThrow(() -> new RuntimeException("Boutique not found"));

        return productRepository.findByBoutique(boutique).stream()
                .map(this::mapToProductDTO)
                .collect(Collectors.toList());
    }

    private BoutiqueDTO mapToDTO(Boutique boutique) {
        return BoutiqueDTO.builder()
                .id(boutique.getId())
                .name(boutique.getName())
                .description(boutique.getDescription())
                .address(boutique.getAddress())
                .contactNumber(boutique.getContactNumber())
                .workingHours(boutique.getWorkingHours())
                .categories(boutique.getCategories())
                .brands(boutique.getBrands())
                .ownerId(boutique.getOwner().getId())
                .build();
    }

    private ProductDTO mapToProductDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .stockCount(product.getStockCount())
                .boutiqueId(product.getBoutique().getId())
                .build();
    }
}
