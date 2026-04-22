package com.example.jewellery.entity.product;

import com.example.jewellery.entity.category.Category;
import com.example.jewellery.entity.collection.JewelleryCollection;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products", indexes = {
		@Index(name = "idx_product_sku", columnList = "sku", unique = true),
		@Index(name = "idx_product_category", columnList = "category_id"),
		@Index(name = "idx_product_collection", columnList = "collection_id")
})
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 40)
	private String sku;

	@Column(nullable = false, length = 120)
	private String name;

	@Column(length = 500)
	private String description;

	@Column(nullable = false)
	private BigDecimal price;

	@Column(nullable = false)
	private Integer stockQty;

	@Column(length = 500)
	private String imageUrl;

	@Column(length = 20)
	private String metalType;

	@Column
	private Double totalWeight;

	@Column
	private Double metalWeight;

	@Column(length = 50)
	private String size;

	@Column(length = 100)
	private String style;

	@Column(nullable = false)
	private Boolean isCustomizable;

	@Column(nullable = false)
	private Boolean isActive;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "collection_id")
	private JewelleryCollection collection;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductMaterial> materials = new ArrayList<>();

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductGemstone> gemstones = new ArrayList<>();

	protected Product() {}

	public Product(String sku, String name, String description, BigDecimal price, Integer stockQty, String imageUrl, 
				 String metalType, Double totalWeight, Double metalWeight, String size, String style, 
				 Boolean isCustomizable, Boolean isActive, Category category, JewelleryCollection collection) {
		this.sku = sku;
		this.name = name;
		this.description = description;
		this.price = price;
		this.stockQty = stockQty;
		this.imageUrl = imageUrl;
		this.metalType = metalType;
		this.totalWeight = totalWeight;
		this.metalWeight = metalWeight;
		this.size = size;
		this.style = style;
		this.isCustomizable = isCustomizable;
		this.isActive = isActive;
		this.category = category;
		this.collection = collection;
	}

	public void addMaterial(ProductMaterial material) {
		materials.add(material);
		material.setProduct(this);
	}

	public void removeMaterial(ProductMaterial material) {
		materials.remove(material);
		material.setProduct(null);
	}

	public void addGemstone(ProductGemstone gemstone) {
		gemstones.add(gemstone);
		gemstone.setProduct(this);
	}

	public void removeGemstone(ProductGemstone gemstone) {
		gemstones.remove(gemstone);
		gemstone.setProduct(null);
	}

	public Long getId() {
		return id;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getStockQty() {
		return stockQty;
	}

	public void setStockQty(Integer stockQty) {
		this.stockQty = stockQty;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getMetalType() {
		return metalType;
	}

	public void setMetalType(String metalType) {
		this.metalType = metalType;
	}

	public Double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public Double getMetalWeight() {
		return metalWeight;
	}

	public void setMetalWeight(Double metalWeight) {
		this.metalWeight = metalWeight;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public Boolean getIsCustomizable() {
		return isCustomizable;
	}

	public void setIsCustomizable(Boolean customizable) {
		isCustomizable = customizable;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean active) {
		isActive = active;
	}

	public JewelleryCollection getCollection() {
		return collection;
	}

	public void setCollection(JewelleryCollection collection) {
		this.collection = collection;
	}

	public List<ProductMaterial> getMaterials() {
		return materials;
	}

	public List<ProductGemstone> getGemstones() {
		return gemstones;
	}
}
