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
    private Long ownerId;
}
