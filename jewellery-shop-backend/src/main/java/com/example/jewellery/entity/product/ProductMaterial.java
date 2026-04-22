package com.example.jewellery.entity.product;

import com.example.jewellery.entity.material.Material;
import jakarta.persistence.*;

@Entity
@Table(name = "product_materials", uniqueConstraints = {
		@UniqueConstraint(name = "uk_product_material", columnNames = {"product_id", "material_id"})
})
public class ProductMaterial {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "material_id", nullable = false)
	private Material material;

	@Column(nullable = false)
	private Double weight;

	@Column
	private Double purity;

	protected ProductMaterial() {}

	public ProductMaterial(Product product, Material material, Double weight, Double purity) {
		this.product = product;
		this.material = material;
		this.weight = weight;
		this.purity = purity;
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

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getPurity() {
		return purity;
	}

	public void setPurity(Double purity) {
		this.purity = purity;
	}
}
