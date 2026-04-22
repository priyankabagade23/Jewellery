package com.example.jewellery.entity.product;

import com.example.jewellery.entity.gemstone.Gemstone;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "product_gemstones", uniqueConstraints = {
		@UniqueConstraint(name = "uk_product_gemstone", columnNames = {"product_id", "gemstone_id"})
})
public class ProductGemstone {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "gemstone_id", nullable = false)
	private Gemstone gemstone;

	@Column(nullable = false)
	private Integer quantity;

	@Column
	private Double caratWeight;

	@Column
	private BigDecimal price;

	protected ProductGemstone() {}

	public ProductGemstone(Product product, Gemstone gemstone, Integer quantity, Double caratWeight, BigDecimal price) {
		this.product = product;
		this.gemstone = gemstone;
		this.quantity = quantity;
		this.caratWeight = caratWeight;
		this.price = price;
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

	public Gemstone getGemstone() {
		return gemstone;
	}

	public void setGemstone(Gemstone gemstone) {
		this.gemstone = gemstone;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getCaratWeight() {
		return caratWeight;
	}

	public void setCaratWeight(Double caratWeight) {
		this.caratWeight = caratWeight;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
