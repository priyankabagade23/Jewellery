package com.example.jewellery.controller.category;

import com.example.jewellery.dto.category.CategoryCreateRequest;
import com.example.jewellery.dto.category.CategoryResponse;
import com.example.jewellery.service.category.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	private final CategoryService service;

	public CategoryController(CategoryService service) {
		this.service = service;
	}

	@GetMapping
	public List<CategoryResponse> list() {
		return service.list();
	}

	@GetMapping("/{id}")
	public CategoryResponse get(@PathVariable Long id) {
		return service.get(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CategoryResponse create(@Valid @RequestBody CategoryCreateRequest req) {
		return service.create(req);
	}

	@PutMapping("/{id}")
	public CategoryResponse update(@PathVariable Long id, @Valid @RequestBody CategoryCreateRequest req) {
		return service.update(id, req);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		service.delete(id);
	}
}
