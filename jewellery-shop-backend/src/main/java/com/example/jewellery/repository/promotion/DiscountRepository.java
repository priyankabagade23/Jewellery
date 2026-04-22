package com.example.jewellery.repository.promotion;

import com.example.jewellery.entity.promotion.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {
	Optional<Discount> findByCode(String code);
	
	@Query("SELECT d FROM Discount d WHERE d.isActive = true AND d.startDate <= :now AND d.endDate >= :now")
	List<Discount> findActiveDiscounts(@Param("now") Instant now);
	
	@Query("SELECT d FROM Discount d WHERE d.code = :code AND d.isActive = true AND d.startDate <= :now AND d.endDate >= :now")
	Optional<Discount> findActiveDiscountByCode(@Param("code") String code, @Param("now") Instant now);
}
