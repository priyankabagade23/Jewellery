package com.example.jewellery.dto.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CartAddRequest(
		@NotNull Long productId,
		@NotNull @Min(1) Integer quantity
) {}
