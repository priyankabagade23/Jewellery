package com.example.jewellery.controller.search;

import com.example.jewellery.dto.search.SearchCriteria;
import com.example.jewellery.entity.category.Category;
import com.example.jewellery.entity.collection.JewelleryCollection;
import com.example.jewellery.entity.gemstone.Gemstone;
import com.example.jewellery.entity.material.Material;
import com.example.jewellery.entity.product.Product;
import com.example.jewellery.service.search.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SearchController {

	private final SearchService searchService;

	@GetMapping("/products")
	public ResponseEntity<Page<Product>> searchProducts(
			@RequestParam(required = false) String query,
			@RequestParam(required = false) List<Long> categoryIds,
			@RequestParam(required = false) List<Long> collectionIds,
			@RequestParam(required = false) List<Long> gemstoneIds,
			@RequestParam(required = false) List<Long> materialIds,
			@RequestParam(required = false) List<String> metalTypes,
			@RequestParam(required = false) List<String> styles,
			@RequestParam(required = false) BigDecimal minPrice,
			@RequestParam(required = false) BigDecimal maxPrice,
			@RequestParam(required = false) Double minWeight,
			@RequestParam(required = false) Double maxWeight,
			@RequestParam(required = false) Boolean isCustomizable,
			@RequestParam(defaultValue = "name") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDirection,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "12") int size) {

		SearchCriteria criteria = SearchCriteria.builder()
			.query(query)
			.categoryIds(categoryIds)
			.collectionIds(collectionIds)
			.gemstoneIds(gemstoneIds)
			.materialIds(materialIds)
			.metalTypes(metalTypes)
			.styles(styles)
			.minPrice(minPrice)
			.maxPrice(maxPrice)
			.minWeight(minWeight)
			.maxWeight(maxWeight)
			.isCustomizable(isCustomizable)
			.sortBy(sortBy)
			.sortDirection(sortDirection)
			.build();

		Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? 
			Sort.Direction.DESC : Sort.Direction.ASC;
		Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

		Page<Product> products = searchService.searchProducts(criteria, pageable);
		return ResponseEntity.ok(products);
	}

	@GetMapping("/filters")
	public ResponseEntity<Map<String, Object>> getSearchFilters() {
		return ResponseEntity.ok(Map.of(
			"categories", searchService.getAllCategories(),
			"collections", searchService.getAllCollections(),
			"materials", searchService.getAllMaterials(),
			"gemstones", searchService.getAllGemstones(),
			"metalTypes", searchService.getAllMetalTypes(),
			"styles", searchService.getAllStyles(),
			"priceRange", Map.of(
				"min", searchService.getMinProductPrice(),
				"max", searchService.getMaxProductPrice()
			)
		));
	}

	@GetMapping("/suggestions")
	public ResponseEntity<List<String>> getSearchSuggestions(@RequestParam String query) {
		if (query == null || query.trim().length() < 2) {
			return ResponseEntity.ok(List.of());
		}

		Pageable pageable = PageRequest.of(0, 10);
		SearchCriteria criteria = SearchCriteria.builder()
			.query(query)
			.build();

		Page<Product> products = searchService.searchProducts(criteria, pageable);
		List<String> suggestions = products.getContent().stream()
			.map(Product::getName)
			.distinct()
			.limit(5)
			.toList();

		return ResponseEntity.ok(suggestions);
	}
}
