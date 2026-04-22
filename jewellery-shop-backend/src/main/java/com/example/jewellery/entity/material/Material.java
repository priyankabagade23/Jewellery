package com.example.jewellery.entity.material;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "materials", indexes = {
		@Index(name = "idx_material_name", columnList = "name", unique = true)
})
public class Material {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 50)
	private String name;

	@Column(length = 200)
	private String description;

	@Column(nullable = false)
	private Boolean isPrecious;

	@Column
	private Double purity;

	@OneToMany(mappedBy = "material")
	private List<com.example.jewellery.entity.product.ProductMaterial> products = new ArrayList<>();

	protected Material() {}

	public Material(String name, String description, Boolean isPrecious, Double purity) {
		this.name = name;
		this.description = description;
		this.isPrecious = isPrecious;
		this.purity = purity;
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

	public Boolean getIsPrecious() {
		return isPrecious;
	}

	public void setIsPrecious(Boolean precious) {
		isPrecious = precious;
	}

	public Double getPurity() {
		return purity;
	}

	public void setPurity(Double purity) {
		this.purity = purity;
	}

	public List<com.example.jewellery.entity.product.ProductMaterial> getProducts() {
		return products;
	}
}
