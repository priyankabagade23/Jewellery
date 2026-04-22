package com.example.jewellery.controller.customer;

import com.example.jewellery.dto.customer.CustomerCreateRequest;
import com.example.jewellery.dto.customer.CustomerResponse;
import com.example.jewellery.service.customer.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
	private final CustomerService service;

	public CustomerController(CustomerService service) {
		this.service = service;
	}

	@GetMapping
	public List<CustomerResponse> list() {
		return service.list();
	}

	@GetMapping("/{id}")
	public CustomerResponse get(@PathVariable Long id) {
		return service.get(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CustomerResponse create(@Valid @RequestBody CustomerCreateRequest req) {
		return service.create(req);
	}
}
