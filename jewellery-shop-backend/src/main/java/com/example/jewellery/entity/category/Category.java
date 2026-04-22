package com.example.jewellery.entity.category;

import jakarta.persistence.*;

@Entity
@Table(name = "categories", indexes = {
		@Index(name = "idx_category_name", columnList = "name", unique = true)
})
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 80)
	private String name;

	@Column(length = 300)
	private String description;

	protected Category() {}

	public Category(String name, String description) {
		this.name = name;
		this.description = description;
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
}
