package com.example.jewellery.service.cart;

import com.example.jewellery.dto.cart.CartAddRequest;
import com.example.jewellery.dto.cart.CartItemResponse;
import com.example.jewellery.dto.cart.CartResponse;
import com.example.jewellery.dto.cart.CartUpdateRequest;
import com.example.jewellery.common.BadRequestException;
import com.example.jewellery.entity.cart.CartItem;
import com.example.jewellery.entity.customer.Customer;
import com.example.jewellery.entity.product.Product;
import com.example.jewellery.repository.cart.CartItemRepository;
import com.example.jewellery.service.customer.CustomerService;
import com.example.jewellery.service.product.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartService {
	private final CartItemRepository repo;
	private final CustomerService customerService;
	private final ProductService productService;

	public CartService(CartItemRepository repo, CustomerService customerService, ProductService productService) {
		this.repo = repo;
		this.customerService = customerService;
		this.productService = productService;
	}

	@Transactional(readOnly = true)
	public CartResponse getCart(Long customerId) {
		List<CartItem> items = repo.findByCustomerId(customerId);
		return toCartResponse(customerId, items);
	}

	@Transactional
	public CartResponse add(Long customerId, CartAddRequest req) {
		Customer customer = customerService.findEntity(customerId);
		Product product = productService.findEntity(req.productId());

		if (product.getStockQty() <= 0) {
			throw new BadRequestException("Product out of stock");
		}

		CartItem item = repo.findByCustomerIdAndProductId(customerId, req.productId())
				.orElseGet(() -> new CartItem(customer, product, 0));
		int newQty = item.getQuantity() + req.quantity();
		if (newQty > product.getStockQty()) {
			throw new BadRequestException("Quantity exceeds available stock");
		}
		item.setQuantity(newQty);
		repo.save(item);
		return getCart(customerId);
	}

	@Transactional
	public CartResponse update(Long customerId, CartUpdateRequest req) {
		Product product = productService.findEntity(req.productId());
		CartItem item = repo.findByCustomerIdAndProductId(customerId, req.productId())
				.orElseThrow(() -> new BadRequestException("Item not found in cart"));
		if (req.quantity() > product.getStockQty()) {
			throw new BadRequestException("Quantity exceeds available stock");
		}
		item.setQuantity(req.quantity());
		return getCart(customerId);
	}

	@Transactional
	public CartResponse remove(Long customerId, Long productId) {
		repo.findByCustomerIdAndProductId(customerId, productId).ifPresent(repo::delete);
		return getCart(customerId);
	}

	@Transactional
	public void clear(Long customerId) {
		repo.deleteByCustomerId(customerId);
	}

	private CartResponse toCartResponse(Long customerId, List<CartItem> items) {
		List<CartItemResponse> responses = items.stream().map(ci -> {
			Product p = ci.getProduct();
			BigDecimal lineTotal = p.getPrice().multiply(BigDecimal.valueOf(ci.getQuantity()));
			return new CartItemResponse(p.getId(), p.getSku(), p.getName(), p.getPrice(), ci.getQuantity(), lineTotal);
		}).toList();
		BigDecimal total = responses.stream()
				.map(CartItemResponse::lineTotal)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		return new CartResponse(customerId, responses, total);
	}
}
