package com.example.jewellery.entity.order;

import com.example.jewellery.entity.product.Product;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items", indexes = {
		@Index(name = "idx_order_item_order", columnList = "order_id")
})
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "order_id", nullable = false)
	private OrderEntity order;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@Column(nullable = false)
	private Integer quantity;

	@Column(nullable = false)
	private BigDecimal unitPrice;

	@Column(nullable = false)
	private BigDecimal lineTotal;

	protected OrderItem() {}

	public OrderItem(Product product, Integer quantity, BigDecimal unitPrice) {
		this.product = product;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.lineTotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
	}

	Long getId() {
		return id;
	}

	public OrderEntity getOrder() {
		return order;
	}

	void setOrder(OrderEntity order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public BigDecimal getLineTotal() {
		return lineTotal;
	}
}
