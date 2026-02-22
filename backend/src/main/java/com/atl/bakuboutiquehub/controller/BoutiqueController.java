package com.atl.bakuboutiquehub.controller;

import com.atl.bakuboutiquehub.dto.BoutiqueDTO;
import com.atl.bakuboutiquehub.dto.ProductDTO;
import com.atl.bakuboutiquehub.service.BoutiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/boutiques")
public class BoutiqueController {

    @Autowired
    private BoutiqueService boutiqueService;

    @PostMapping
    @PreAuthorize("hasAuthority('BOUTIQUE_ADMIN') or hasAuthority('ADMIN')")
    public ResponseEntity<BoutiqueDTO> createBoutique(
            @RequestBody BoutiqueDTO boutiqueDTO,
            Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(boutiqueService.createBoutique(boutiqueDTO, email));
    }

    @GetMapping
    public ResponseEntity<List<BoutiqueDTO>> getAllBoutiques() {
        return ResponseEntity.ok(boutiqueService.getAllBoutiques());
    }

    @PostMapping("/{boutiqueId}/products")
    @PreAuthorize("hasAuthority('BOUTIQUE_ADMIN') or hasAuthority('ADMIN')")
    public ResponseEntity<ProductDTO> addProduct(
            @PathVariable Long boutiqueId,
            @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(boutiqueService.addProduct(boutiqueId, productDTO));
    }

    @GetMapping("/{boutiqueId}/products")
    public ResponseEntity<List<ProductDTO>> getProducts(@PathVariable Long boutiqueId) {
        return ResponseEntity.ok(boutiqueService.getProductsByBoutique(boutiqueId));
    }
}
