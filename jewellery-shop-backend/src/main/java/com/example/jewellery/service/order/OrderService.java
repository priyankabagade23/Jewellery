package com.example.jewellery.service.order;

import com.example.jewellery.entity.cart.CartItem;
import com.example.jewellery.entity.customer.Customer;
import com.example.jewellery.entity.order.OrderEntity;
import com.example.jewellery.entity.order.OrderItem;
import com.example.jewellery.entity.order.OrderStatus;
import com.example.jewellery.entity.product.Product;
import com.example.jewellery.common.BadRequestException;
import com.example.jewellery.common.NotFoundException;
import com.example.jewellery.dto.order.OrderItemResponse;
import com.example.jewellery.dto.order.OrderResponse;
import com.example.jewellery.repository.cart.CartItemRepository;
import com.example.jewellery.repository.order.OrderRepository;
import com.example.jewellery.service.customer.CustomerService;
import com.example.jewellery.service.product.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {
	private final OrderRepository orderRepo;
	private final CartItemRepository cartRepo;
	private final CustomerService customerService;
	private final ProductService productService;

	public OrderService(OrderRepository orderRepo, CartItemRepository cartRepo, CustomerService customerService, ProductService productService) {
		this.orderRepo = orderRepo;
		this.cartRepo = cartRepo;
		this.customerService = customerService;
		this.productService = productService;
	}

	@Transactional
	public OrderResponse checkout(Long customerId, String shippingAddress) {
		Customer customer = customerService.findEntity(customerId);
		List<CartItem> cartItems = cartRepo.findByCustomerId(customerId);
		if (cartItems.isEmpty()) {
			throw new BadRequestException("Cart is empty");
		}

		OrderEntity order = new OrderEntity(customer, shippingAddress.trim());
		BigDecimal total = BigDecimal.ZERO;

		for (CartItem ci : cartItems) {
			Product p = ci.getProduct();
			int qty = ci.getQuantity();
			productService.decreaseStockOrThrow(p, qty);

			OrderItem item = new OrderItem(p, qty, p.getPrice());
			order.addItem(item);
			total = total.add(item.getLineTotal());
		}

		order.setTotalAmount(total);
		OrderEntity saved = orderRepo.save(order);

		cartRepo.deleteByCustomerId(customerId);
		return toResponse(saved);
	}

	@Transactional(readOnly = true)
	public List<OrderResponse> listCustomerOrders(Long customerId) {
		customerService.findEntity(customerId);
		return orderRepo.findByCustomerIdOrderByCreatedAtDesc(customerId).stream()
				.map(OrderService::toResponse)
				.toList();
	}

	@Transactional(readOnly = true)
	public OrderResponse get(Long orderId) {
		return toResponse(orderRepo.findById(orderId).orElseThrow(() -> new NotFoundException("Order not found")));
	}

	static OrderResponse toResponse(OrderEntity order) {
		List<OrderItemResponse> items = order.getItems().stream().map(oi -> new OrderItemResponse(
				oi.getProduct().getId(),
				oi.getProduct().getSku(),
				oi.getProduct().getName(),
				oi.getQuantity(),
				oi.getUnitPrice(),
				oi.getLineTotal()
		)).toList();

		return new OrderResponse(
				order.getId(),
				order.getCustomer().getId(),
				order.getStatus(),
				order.getTotalAmount(),
				order.getCreatedAt(),
				order.getShippingAddress(),
				items
		);
	}
}
