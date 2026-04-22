package com.example.jewellery.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CheckoutRequest(
		@NotBlank @Size(max = 200) String shippingAddress
) {}
