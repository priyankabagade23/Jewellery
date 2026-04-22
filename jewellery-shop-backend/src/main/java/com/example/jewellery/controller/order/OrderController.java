package com.example.jewellery.controller.order;

import com.example.jewellery.dto.order.CheckoutRequest;
import com.example.jewellery.dto.order.OrderResponse;
import com.example.jewellery.service.order.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {
	private final OrderService service;

	public OrderController(OrderService service) {
		this.service = service;
	}

	@PostMapping("/api/customers/{customerId}/checkout")
	@ResponseStatus(HttpStatus.CREATED)
	public OrderResponse checkout(@PathVariable Long customerId, @Valid @RequestBody CheckoutRequest req) {
		return service.checkout(customerId, req.shippingAddress());
	}

	@GetMapping("/api/customers/{customerId}/orders")
	public List<OrderResponse> listCustomerOrders(@PathVariable Long customerId) {
		return service.listCustomerOrders(customerId);
	}

	@GetMapping("/api/orders/{orderId}")
	public OrderResponse get(@PathVariable Long orderId) {
		return service.get(orderId);
	}
}
