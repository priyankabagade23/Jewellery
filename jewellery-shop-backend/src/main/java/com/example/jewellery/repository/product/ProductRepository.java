package com.example.jewellery.repository.product;

import com.example.jewellery.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
	Optional<Product> findBySku(String sku);

	@Override
	@EntityGraph(attributePaths = "category")
	Page<Product> findAll(Pageable pageable);

	@Override
	@EntityGraph(attributePaths = "category")
	Optional<Product> findById(Long id);

	@EntityGraph(attributePaths = "category")
	Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

	@Query("""
			select p from Product p
			where lower(p.name) like lower(concat('%', :q, '%'))
			   or lower(p.sku) like lower(concat('%', :q, '%'))
			""")
	@EntityGraph(attributePaths = "category")
	Page<Product> search(@Param("q") String query, Pageable pageable);

	@Query("SELECT DISTINCT p.metalType FROM Product p WHERE p.metalType IS NOT NULL")
	List<String> findDistinctMetalTypes();

	@Query("SELECT DISTINCT p.style FROM Product p WHERE p.style IS NOT NULL")
	List<String> findDistinctStyles();

	@Query("SELECT MIN(p.price) FROM Product p WHERE p.isActive = true")
	BigDecimal findMinPrice();

	@Query("SELECT MAX(p.price) FROM Product p WHERE p.isActive = true")
	BigDecimal findMaxPrice();
}
