package com.example.jewellery.entity.customer;

import com.example.jewellery.entity.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "customers", indexes = {
		@Index(name = "idx_customer_email", columnList = "email", unique = true)
})
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 80)
	private String fullName;

	@Column(nullable = false, unique = true, length = 120)
	private String email;

	@Column(length = 20)
	private String phone;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "customer")
	private User user;

	protected Customer() {}

	public Customer(String fullName, String email, String phone) {
		this.fullName = fullName;
		this.email = email;
		this.phone = phone;
	}

	public Long getId() {
		return id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
