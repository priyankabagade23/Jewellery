package com.example.jewellery.repository.inventory;

import com.example.jewellery.entity.inventory.InventoryTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryTransactionRepository extends JpaRepository<InventoryTransaction, Long> {
	List<InventoryTransaction> findByProductId(Long productId);
}
