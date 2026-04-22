package com.example.jewellery.controller.cart;

import com.example.jewellery.dto.cart.CartAddRequest;
import com.example.jewellery.dto.cart.CartResponse;
import com.example.jewellery.dto.cart.CartUpdateRequest;
import com.example.jewellery.service.cart.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers/{customerId}/cart")
public class CartController {
	private final CartService service;

	public CartController(CartService service) {
		this.service = service;
	}

	@GetMapping
	public CartResponse get(@PathVariable Long customerId) {
		return service.getCart(customerId);
	}

	@PostMapping("/items")
	public CartResponse add(@PathVariable Long customerId, @Valid @RequestBody CartAddRequest req) {
		return service.add(customerId, req);
	}

	@PutMapping("/items")
	public CartResponse update(@PathVariable Long customerId, @Valid @RequestBody CartUpdateRequest req) {
		return service.update(customerId, req);
	}

	@DeleteMapping("/items/{productId}")
	public CartResponse remove(@PathVariable Long customerId, @PathVariable Long productId) {
		return service.remove(customerId, productId);
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void clear(@PathVariable Long customerId) {
		service.clear(customerId);
	}
}
