package com.example.jewellery.dto.order;

import java.math.BigDecimal;

public record OrderItemResponse(
		Long productId,
		String sku,
		String name,
		Integer quantity,
		BigDecimal unitPrice,
		BigDecimal lineTotal
) {}
