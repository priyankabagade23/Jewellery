package com.example.jewellery.repository.order;

import com.example.jewellery.entity.order.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
	List<OrderEntity> findByCustomerIdOrderByCreatedAtDesc(Long customerId);
}
