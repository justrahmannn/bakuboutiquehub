package com.atl.bakuboutiquehub.dto;

import lombok.Data;
import java.util.Set;

@Data
public class SignupRequest {
    private String email;
    private String password;
    private String phoneNumber;
    private String fullName;
    private String gender;
    private Integer age;
    private String referralSource;
    private Set<String> roles;
}
