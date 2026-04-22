package com.example.jewellery.dto.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerCreateRequest(
		@NotBlank @Size(max = 80) String fullName,
		@NotBlank @Email @Size(max = 120) String email,
		@Size(max = 20) String phone
) {}
