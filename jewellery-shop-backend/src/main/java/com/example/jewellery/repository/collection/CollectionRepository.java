package com.example.jewellery.repository.collection;

import com.example.jewellery.entity.collection.JewelleryCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollectionRepository extends JpaRepository<JewelleryCollection, Long> {
	Optional<JewelleryCollection> findByName(String name);
	List<JewelleryCollection> findByIsActive(Boolean isActive);
}
