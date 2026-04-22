package com.example.jewellery.service.customer;

import com.example.jewellery.common.BadRequestException;
import com.example.jewellery.common.NotFoundException;
import com.example.jewellery.dto.customer.CustomerCreateRequest;
import com.example.jewellery.dto.customer.CustomerResponse;
import com.example.jewellery.entity.customer.Customer;
import com.example.jewellery.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {
	private final CustomerRepository repo;

	public CustomerService(CustomerRepository repo) {
		this.repo = repo;
	}

	public List<CustomerResponse> list() {
		return repo.findAll().stream().map(CustomerService::toResponse).toList();
	}

	public CustomerResponse get(Long id) {
		return toResponse(findEntity(id));
	}

	@Transactional
	public CustomerResponse create(CustomerCreateRequest req) {
		repo.findByEmailIgnoreCase(req.email().trim()).ifPresent(c -> {
			throw new BadRequestException("Email already exists");
		});
		Customer saved = repo.save(new Customer(req.fullName().trim(), req.email().trim(), req.phone()));
		return toResponse(saved);
	}

	public Customer findEntity(Long id) {
		return repo.findById(id).orElseThrow(() -> new NotFoundException("Customer not found"));
	}

	static CustomerResponse toResponse(Customer c) {
		return new CustomerResponse(c.getId(), c.getFullName(), c.getEmail(), c.getPhone());
	}
}
