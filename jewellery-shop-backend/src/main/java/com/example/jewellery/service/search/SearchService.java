package com.example.jewellery.service.search;

import com.example.jewellery.dto.search.SearchCriteria;
import com.example.jewellery.entity.category.Category;
import com.example.jewellery.entity.collection.JewelleryCollection;
import com.example.jewellery.entity.gemstone.Gemstone;
import com.example.jewellery.entity.material.Material;
import com.example.jewellery.entity.product.Product;
import com.example.jewellery.repository.category.CategoryRepository;
import com.example.jewellery.repository.collection.CollectionRepository;
import com.example.jewellery.repository.gemstone.GemstoneRepository;
import com.example.jewellery.repository.material.MaterialRepository;
import com.example.jewellery.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

	private final ProductRepository productRepository;
	private final CategoryRepository categoryRepository;
	private final CollectionRepository collectionRepository;
	private final MaterialRepository materialRepository;
	private final GemstoneRepository gemstoneRepository;

	public Page<Product> searchProducts(SearchCriteria criteria, Pageable pageable) {
		Specification<Product> spec = Specification.where(null);

		if (criteria.getQuery() != null && !criteria.getQuery().trim().isEmpty()) {
			spec = spec.and((root, query, cb) -> {
				String searchTerm = "%" + criteria.getQuery().toLowerCase() + "%";
				return cb.or(
					cb.like(cb.lower(root.get("name")), searchTerm),
					cb.like(cb.lower(root.get("description")), searchTerm),
					cb.like(cb.lower(root.get("sku")), searchTerm),
					cb.like(cb.lower(root.get("style")), searchTerm),
					cb.like(cb.lower(root.get("metalType")), searchTerm)
				);
			});
		}

		if (criteria.getCategoryIds() != null && !criteria.getCategoryIds().isEmpty()) {
			spec = spec.and((root, query, cb) -> 
				root.get("category").get("id").in(criteria.getCategoryIds()));
		}

		if (criteria.getCollectionIds() != null && !criteria.getCollectionIds().isEmpty()) {
			spec = spec.and((root, query, cb) -> 
				root.get("collection").get("id").in(criteria.getCollectionIds()));
		}

		if (criteria.getMinPrice() != null) {
			spec = spec.and((root, query, cb) -> 
				cb.greaterThanOrEqualTo(root.get("price"), criteria.getMinPrice()));
		}

		if (criteria.getMaxPrice() != null) {
			spec = spec.and((root, query, cb) -> 
				cb.lessThanOrEqualTo(root.get("price"), criteria.getMaxPrice()));
		}

		if (criteria.getMetalTypes() != null && !criteria.getMetalTypes().isEmpty()) {
			spec = spec.and((root, query, cb) -> 
				root.get("metalType").in(criteria.getMetalTypes()));
		}

		if (criteria.getStyles() != null && !criteria.getStyles().isEmpty()) {
			spec = spec.and((root, query, cb) -> 
				root.get("style").in(criteria.getStyles()));
		}

		if (criteria.getIsCustomizable() != null) {
			spec = spec.and((root, query, cb) -> 
				cb.equal(root.get("isCustomizable"), criteria.getIsCustomizable()));
		}

		if (criteria.getMinWeight() != null) {
			spec = spec.and((root, query, cb) -> 
				cb.greaterThanOrEqualTo(root.get("totalWeight"), criteria.getMinWeight()));
		}

		if (criteria.getMaxWeight() != null) {
			spec = spec.and((root, query, cb) -> 
				cb.lessThanOrEqualTo(root.get("totalWeight"), criteria.getMaxWeight()));
		}

		if (criteria.getGemstoneIds() != null && !criteria.getGemstoneIds().isEmpty()) {
			spec = spec.and((root, query, cb) -> {
				var gemstonesJoin = root.join("gemstones");
				return gemstonesJoin.get("gemstone").get("id").in(criteria.getGemstoneIds());
			});
		}

		if (criteria.getMaterialIds() != null && !criteria.getMaterialIds().isEmpty()) {
			spec = spec.and((root, query, cb) -> {
				var materialsJoin = root.join("materials");
				return materialsJoin.get("material").get("id").in(criteria.getMaterialIds());
			});
		}

		spec = spec.and((root, query, cb) -> cb.equal(root.get("isActive"), true));

		return productRepository.findAll(spec, pageable);
	}

	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	public List<JewelleryCollection> getAllCollections() {
		return collectionRepository.findByIsActive(true);
	}

	public List<Material> getAllMaterials() {
		return materialRepository.findAll();
	}

	public List<Gemstone> getAllGemstones() {
		return gemstoneRepository.findAll();
	}

	public List<String> getAllMetalTypes() {
		return productRepository.findDistinctMetalTypes();
	}

	public List<String> getAllStyles() {
		return productRepository.findDistinctStyles();
	}

	public BigDecimal getMinProductPrice() {
		return productRepository.findMinPrice();
	}

	public BigDecimal getMaxProductPrice() {
		return productRepository.findMaxPrice();
	}
}
