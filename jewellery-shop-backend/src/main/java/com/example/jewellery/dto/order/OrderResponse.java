package com.example.jewellery.dto.order;

import com.example.jewellery.entity.order.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderResponse(
		Long id,
		Long customerId,
		OrderStatus status,
		BigDecimal totalAmount,
		Instant createdAt,
		String shippingAddress,
		List<OrderItemResponse> items
) {}
