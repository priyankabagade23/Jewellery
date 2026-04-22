package com.example.jewellery.service.product;

import com.example.jewellery.entity.category.Category;
import com.example.jewellery.entity.product.Product;
import com.example.jewellery.service.category.CategoryService;
import com.example.jewellery.common.BadRequestException;
import com.example.jewellery.common.NotFoundException;
import com.example.jewellery.dto.product.ProductCreateRequest;
import com.example.jewellery.dto.product.ProductResponse;
import com.example.jewellery.dto.product.ProductUpdateRequest;
import com.example.jewellery.repository.product.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
	private final ProductRepository repo;
	private final CategoryService categoryService;

	public ProductService(ProductRepository repo, CategoryService categoryService) {
		this.repo = repo;
		this.categoryService = categoryService;
	}

	@Transactional(readOnly = true)
	public Page<ProductResponse> list(Integer page, Integer size, Long categoryId, String q) {
		Pageable pageable = PageRequest.of(
				page == null ? 0 : page,
				size == null ? 20 : Math.min(size, 100),
				Sort.by("id").descending()
		);

		Page<Product> result;
		if (categoryId != null) {
			result = repo.findByCategoryId(categoryId, pageable);
		} else if (q != null && !q.isBlank()) {
			result = repo.search(q.trim(), pageable);
		} else {
			result = repo.findAll(pageable);
		}
		return result.map(ProductService::toResponse);
	}

	public ProductResponse get(Long id) {
		return toResponse(findEntity(id));
	}

	@Transactional
	public ProductResponse create(ProductCreateRequest req) {
		repo.findBySku(req.sku().trim()).ifPresent(p -> {
			throw new BadRequestException("SKU already exists");
		});
		Category category = categoryService.findEntity(req.categoryId());

		Product saved = repo.save(new Product(
				req.sku().trim(),
				req.name().trim(),
				req.description(),
				req.price(),
				req.stockQty(),
				req.imageUrl(),
				null, null, null, null, null, true, true,
				category, null
		));
		return toResponse(saved);
	}

	@Transactional
	public ProductResponse update(Long id, ProductUpdateRequest req) {
		Product p = findEntity(id);
		Category category = categoryService.findEntity(req.categoryId());
		p.setName(req.name().trim());
		p.setDescription(req.description());
		p.setPrice(req.price());
		p.setStockQty(req.stockQty());
		p.setImageUrl(req.imageUrl());
		p.setCategory(category);
		return toResponse(p);
	}

	@Transactional
	public void delete(Long id) {
		if (!repo.existsById(id)) {
			throw new NotFoundException("Product not found");
		}
		repo.deleteById(id);
	}

	@Transactional
	public void decreaseStockOrThrow(Product product, int qty) {
		if (qty <= 0) throw new BadRequestException("Quantity must be >= 1");
		if (product.getStockQty() < qty) {
			throw new BadRequestException("Not enough stock for product: " + product.getSku());
		}
		product.setStockQty(product.getStockQty() - qty);
	}

	public Product findEntity(Long id) {
		return repo.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
	}

	static ProductResponse toResponse(Product p) {
		return new ProductResponse(
				p.getId(),
				p.getSku(),
				p.getName(),
				p.getDescription(),
				p.getPrice(),
				p.getStockQty(),
				p.getImageUrl(),
				p.getCategory().getId(),
				p.getCategory().getName()
		);
	}
}
