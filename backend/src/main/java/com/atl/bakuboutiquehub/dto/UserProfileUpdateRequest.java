package com.atl.bakuboutiquehub.dto;

import lombok.Data;

@Data
public class UserProfileUpdateRequest {
    private String fullName;
    private String phoneNumber;
}
