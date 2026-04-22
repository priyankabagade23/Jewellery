package com.example.jewellery.entity.gemstone;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "gemstones", indexes = {
		@Index(name = "idx_gemstone_name", columnList = "name", unique = true)
})
public class Gemstone {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 50)
	private String name;

	@Column(length = 200)
	private String description;

	@Column(nullable = false)
	private String color;

	@Column
	private Double hardness;

	@Column
	private Double caratWeight;

	@Column(nullable = false)
	private Boolean isPrecious;

	@Column
	private BigDecimal pricePerCarat;

	@OneToMany(mappedBy = "gemstone")
	private List<com.example.jewellery.entity.product.ProductGemstone> products = new ArrayList<>();

	protected Gemstone() {}

	public Gemstone(String name, String description, String color, Double hardness, 
				   Double caratWeight, Boolean isPrecious, BigDecimal pricePerCarat) {
		this.name = name;
		this.description = description;
		this.color = color;
		this.hardness = hardness;
		this.caratWeight = caratWeight;
		this.isPrecious = isPrecious;
		this.pricePerCarat = pricePerCarat;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Double getHardness() {
		return hardness;
	}

	public void setHardness(Double hardness) {
		this.hardness = hardness;
	}

	public Double getCaratWeight() {
		return caratWeight;
	}

	public void setCaratWeight(Double caratWeight) {
		this.caratWeight = caratWeight;
	}

	public Boolean getIsPrecious() {
		return isPrecious;
	}

	public void setIsPrecious(Boolean precious) {
		isPrecious = precious;
	}

	public BigDecimal getPricePerCarat() {
		return pricePerCarat;
	}

	public void setPricePerCarat(BigDecimal pricePerCarat) {
		this.pricePerCarat = pricePerCarat;
	}

	public List<com.example.jewellery.entity.product.ProductGemstone> getProducts() {
		return products;
	}
}
