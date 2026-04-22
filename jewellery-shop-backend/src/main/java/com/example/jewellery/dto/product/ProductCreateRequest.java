package com.example.jewellery.dto.product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductCreateRequest(
		@NotBlank @Size(max = 40) String sku,
		@NotBlank @Size(max = 120) String name,
		@Size(max = 500) String description,
		@NotNull BigDecimal price,
		@NotNull @Min(0) Integer stockQty,
		@Size(max = 500) String imageUrl,
		@NotNull Long categoryId
) {}
