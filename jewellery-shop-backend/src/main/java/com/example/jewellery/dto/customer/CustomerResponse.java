package com.example.jewellery.dto.customer;

public record CustomerResponse(
		Long id,
		String fullName,
		String email,
		String phone
) {}
