package com.atl.bakuboutiquehub.repository;

import com.atl.bakuboutiquehub.model.Boutique;
import com.atl.bakuboutiquehub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoutiqueRepository extends JpaRepository<Boutique, Long> {
    Optional<Boutique> findByOwner(User owner);
}
