package com.example.jewellery.repository.product;

import com.example.jewellery.entity.product.ProductGemstone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductGemstoneRepository extends JpaRepository<ProductGemstone, Long> {
	List<ProductGemstone> findByProductId(Long productId);
	List<ProductGemstone> findByGemstoneId(Long gemstoneId);
}
