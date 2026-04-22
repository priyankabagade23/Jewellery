package com.example.jewellery.entity.order;

import com.example.jewellery.entity.customer.Customer;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders", indexes = {
		@Index(name = "idx_orders_customer", columnList = "customer_id")
})
public class OrderEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private OrderStatus status;

	@Column(nullable = false)
	private BigDecimal totalAmount;

	@Column(nullable = false, updatable = false)
	private Instant createdAt;

	@Column(nullable = false, length = 200)
	private String shippingAddress;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> items = new ArrayList<>();

	protected OrderEntity() {}

	public OrderEntity(Customer customer, String shippingAddress) {
		this.customer = customer;
		this.shippingAddress = shippingAddress;
		this.status = OrderStatus.CREATED;
		this.createdAt = Instant.now();
		this.totalAmount = BigDecimal.ZERO;
	}

	public void addItem(OrderItem item) {
		items.add(item);
		item.setOrder(this);
	}

	public Long getId() {
		return id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public List<OrderItem> getItems() {
		return items;
	}
}
