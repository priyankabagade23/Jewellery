package com.example.jewellery.service.category;

import com.example.jewellery.entity.category.Category;
import com.example.jewellery.dto.category.CategoryCreateRequest;
import com.example.jewellery.dto.category.CategoryResponse;
import com.example.jewellery.common.BadRequestException;
import com.example.jewellery.common.NotFoundException;
import com.example.jewellery.repository.category.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
	private final CategoryRepository repo;

	public CategoryService(CategoryRepository repo) {
		this.repo = repo;
	}

	public List<CategoryResponse> list() {
		return repo.findAll().stream().map(CategoryService::toResponse).toList();
	}

	public CategoryResponse get(Long id) {
		return toResponse(findEntity(id));
	}

	@Transactional
	public CategoryResponse create(CategoryCreateRequest req) {
		repo.findByNameIgnoreCase(req.name()).ifPresent(c -> {
			throw new BadRequestException("Category name already exists");
		});
		Category saved = repo.save(new Category(req.name().trim(), req.description()));
		return toResponse(saved);
	}

	@Transactional
	public CategoryResponse update(Long id, CategoryCreateRequest req) {
		Category c = findEntity(id);
		String newName = req.name().trim();
		repo.findByNameIgnoreCase(newName).ifPresent(existing -> {
			if (!existing.getId().equals(id)) {
				throw new BadRequestException("Category name already exists");
			}
		});
		c.setName(newName);
		c.setDescription(req.description());
		return toResponse(c);
	}

	@Transactional
	public void delete(Long id) {
		if (!repo.existsById(id)) {
			throw new NotFoundException("Category not found");
		}
		repo.deleteById(id);
	}

	public Category findEntity(Long id) {
		return repo.findById(id).orElseThrow(() -> new NotFoundException("Category not found"));
	}

	static CategoryResponse toResponse(Category c) {
		return new CategoryResponse(c.getId(), c.getName(), c.getDescription());
	}
}
