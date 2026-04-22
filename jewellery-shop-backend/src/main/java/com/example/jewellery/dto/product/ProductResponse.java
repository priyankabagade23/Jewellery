package com.example.jewellery.dto.product;

import java.math.BigDecimal;

public record ProductResponse(
		Long id,
		String sku,
		String name,
		String description,
		BigDecimal price,
		Integer stockQty,
		String imageUrl,
		Long categoryId,
		String categoryName
) {}
