package com.example.jewellery.entity.inventory;

import com.example.jewellery.entity.product.Product;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "inventory_transactions", indexes = {
		@Index(name = "idx_inventory_product", columnList = "product_id"),
		@Index(name = "idx_inventory_type", columnList = "transaction_type"),
		@Index(name = "idx_inventory_date", columnList = "created_at")
})
public class InventoryTransaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private TransactionType transactionType;

	@Column(nullable = false)
	private Integer quantity;

	@Column(nullable = false)
	private BigDecimal unitCost;

	@Column(length = 200)
	private String reference;

	@Column(length = 500)
	private String notes;

	@Column(nullable = false)
	private Instant createdAt;

	protected InventoryTransaction() {}

	public InventoryTransaction(Product product, TransactionType transactionType, Integer quantity, 
							 BigDecimal unitCost, String reference, String notes) {
		this.product = product;
		this.transactionType = transactionType;
		this.quantity = quantity;
		this.unitCost = unitCost;
		this.reference = reference;
		this.notes = notes;
		this.createdAt = Instant.now();
	}

	public Long getId() {
		return id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(BigDecimal unitCost) {
		this.unitCost = unitCost;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}
}
