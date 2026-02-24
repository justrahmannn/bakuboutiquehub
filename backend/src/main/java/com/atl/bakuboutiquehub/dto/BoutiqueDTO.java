package com.atl.bakuboutiquehub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoutiqueDTO {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String contactNumber;
    private String workingHours;
    private java.util.List<String> categories;
    private java.util.List<String> brands;
    private Long ownerId;
}
