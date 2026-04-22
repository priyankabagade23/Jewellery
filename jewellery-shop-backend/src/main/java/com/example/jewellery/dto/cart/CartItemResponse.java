package com.example.jewellery.dto.cart;

import java.math.BigDecimal;

public record CartItemResponse(
		Long productId,
		String sku,
		String name,
		BigDecimal unitPrice,
		Integer quantity,
		BigDecimal lineTotal
) {}
