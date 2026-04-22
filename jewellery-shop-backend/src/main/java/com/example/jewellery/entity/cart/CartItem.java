package com.example.jewellery.entity.cart;

import com.example.jewellery.entity.customer.Customer;
import com.example.jewellery.entity.product.Product;
import jakarta.persistence.*;

@Entity
@Table(name = "cart_items", uniqueConstraints = {
		@UniqueConstraint(name = "uk_cart_customer_product", columnNames = {"customer_id", "product_id"})
})
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@Column(nullable = false)
	private Integer quantity;

	protected CartItem() {}

	public CartItem(Customer customer, Product product, Integer quantity) {
		this.customer = customer;
		this.product = product;
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Product getProduct() {
		return product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
