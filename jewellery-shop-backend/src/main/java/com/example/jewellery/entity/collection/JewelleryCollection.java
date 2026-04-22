package com.example.jewellery.entity.collection;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "collections", indexes = {
		@Index(name = "idx_collection_name", columnList = "name", unique = true),
		@Index(name = "idx_collection_season", columnList = "season")
})
public class JewelleryCollection {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 80)
	private String name;

	@Column(length = 500)
	private String description;

	@Column(length = 50)
	private String season;

	@Column(nullable = false)
	private LocalDate launchDate;

	@Column(nullable = false)
	private Boolean isActive;

	@Column(length = 500)
	private String imageUrl;

	@OneToMany(mappedBy = "collection")
	private List<com.example.jewellery.entity.product.Product> products = new ArrayList<>();

	protected JewelleryCollection() {}

	public JewelleryCollection(String name, String description, String season, LocalDate launchDate, Boolean isActive, String imageUrl) {
		this.name = name;
		this.description = description;
		this.season = season;
		this.launchDate = launchDate;
		this.isActive = isActive;
		this.imageUrl = imageUrl;
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

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public LocalDate getLaunchDate() {
		return launchDate;
	}

	public void setLaunchDate(LocalDate launchDate) {
		this.launchDate = launchDate;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean active) {
		isActive = active;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<com.example.jewellery.entity.product.Product> getProducts() {
		return products;
	}
}
