package com.atl.bakuboutiquehub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "boutiques")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Boutique {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    private String address;

    private String contactNumber;

    private String workingHours;

    @ElementCollection
    @CollectionTable(name = "boutique_categories", joinColumns = @JoinColumn(name = "boutique_id"))
    @Column(name = "category")
    private List<String> categories;

    @ElementCollection
    @CollectionTable(name = "boutique_brands", joinColumns = @JoinColumn(name = "boutique_id"))
    @Column(name = "brand")
    private List<String> brands;

    @OneToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @OneToMany(mappedBy = "boutique", cascade = CascadeType.ALL)
    private List<Product> products;
}
