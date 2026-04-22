package com.example.jewellery.repository.gemstone;

import com.example.jewellery.entity.gemstone.Gemstone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GemstoneRepository extends JpaRepository<Gemstone, Long> {
	Optional<Gemstone> findByName(String name);
}
