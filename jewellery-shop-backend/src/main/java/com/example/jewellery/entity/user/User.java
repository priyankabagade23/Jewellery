package com.example.jewellery.entity.user;

import com.example.jewellery.entity.customer.Customer;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", indexes = {
		@Index(name = "idx_user_email", columnList = "email", unique = true),
		@Index(name = "idx_user_username", columnList = "username", unique = true)
})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 50)
	private String username;

	@Column(nullable = false, unique = true, length = 120)
	private String email;

	@Column(nullable = false, length = 255)
	private String password;

	@Column(nullable = false)
	private Boolean enabled = true;

	@Column(nullable = false)
	private Instant createdAt;

	@Column(nullable = false)
	private Instant lastLoginAt;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
	@Column(name = "role")
	private Set<Role> roles = new HashSet<>();

	public User() {}

	public User(String username, String email, String password, Set<Role> roles) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.roles = roles;
		this.createdAt = Instant.now();
		this.lastLoginAt = Instant.now();
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getLastLoginAt() {
		return lastLoginAt;
	}

	public void setLastLoginAt(Instant lastLoginAt) {
		this.lastLoginAt = lastLoginAt;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void addRole(Role role) {
		roles.add(role);
	}

	public void removeRole(Role role) {
		roles.remove(role);
	}
}
