package com.example.jewellery.dto.cart;

import java.math.BigDecimal;
import java.util.List;

public record CartResponse(
		Long customerId,
		List<CartItemResponse> items,
		BigDecimal total
) {}
