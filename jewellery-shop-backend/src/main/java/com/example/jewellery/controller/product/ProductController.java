package com.example.jewellery.controller.product;

import com.example.jewellery.dto.product.ProductCreateRequest;
import com.example.jewellery.dto.product.ProductResponse;
import com.example.jewellery.dto.product.ProductUpdateRequest;
import com.example.jewellery.service.product.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	private final ProductService service;

	public ProductController(ProductService service) {
		this.service = service;
	}

	@GetMapping
	public Page<ProductResponse> list(
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size,
			@RequestParam(required = false) Long categoryId,
			@RequestParam(required = false) String q
	) {
		return service.list(page, size, categoryId, q);
	}

	@GetMapping("/{id}")
	public ProductResponse get(@PathVariable Long id) {
		return service.get(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProductResponse create(@Valid @RequestBody ProductCreateRequest req) {
		return service.create(req);
	}

	@PutMapping("/{id}")
	public ProductResponse update(@PathVariable Long id, @Valid @RequestBody ProductUpdateRequest req) {
		return service.update(id, req);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
}
