package com.example.jewellery.entity.material;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MaterialTest {

    @Test
    void testMaterialCreation() {
        Material material = new Material("Gold", "Yellow gold", true, 24.0);
        
        assertEquals("Gold", material.getName());
        assertEquals("Yellow gold", material.getDescription());
        assertTrue(material.getIsPrecious());
        assertEquals(24.0, material.getPurity());
    }

    @Test
    void testMaterialSetters() {
        Material material = new Material();
        material.setName("Silver");
        material.setDescription("Sterling silver");
        material.setIsPrecious(false);
        material.setPurity(92.5);
        
        assertEquals("Silver", material.getName());
        assertEquals("Sterling silver", material.getDescription());
        assertFalse(material.getIsPrecious());
        assertEquals(92.5, material.getPurity());
    }

    @Test
    void testMaterialProductsList() {
        Material material = new Material("Gold", "Yellow gold", true, 24.0);
        assertNotNull(material.getProducts());
        assertTrue(material.getProducts().isEmpty());
    }
}
