package com.example.jewellery.repository.product;

import com.example.jewellery.entity.product.ProductMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMaterialRepository extends JpaRepository<ProductMaterial, Long> {
	List<ProductMaterial> findByProductId(Long productId);
	List<ProductMaterial> findByMaterialId(Long materialId);
}
