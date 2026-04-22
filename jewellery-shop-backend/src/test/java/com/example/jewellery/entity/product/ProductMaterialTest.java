package com.example.jewellery.entity.product;

import com.example.jewellery.entity.material.Material;
import com.example.jewellery.entity.product.Product;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductMaterialTest {

    @Test
    void testProductMaterialCreation() {
        Material material = new Material("Gold", "Yellow gold", true, 24.0);
        Product product = new Product();
        product.setName("Gold Ring");
        
        ProductMaterial productMaterial = new ProductMaterial(product, material, 5.0, 24.0);
        
        assertEquals(product, productMaterial.getProduct());
        assertEquals(material, productMaterial.getMaterial());
        assertEquals(5.0, productMaterial.getWeight());
        assertEquals(24.0, productMaterial.getPurity());
    }

    @Test
    void testProductMaterialSetters() {
        ProductMaterial productMaterial = new ProductMaterial();
        Material material = new Material("Silver", "Sterling silver", false, 92.5);
        Product product = new Product();
        product.setName("Silver Necklace");
        
        productMaterial.setProduct(product);
        productMaterial.setMaterial(material);
        productMaterial.setWeight(10.0);
        productMaterial.setPurity(92.5);
        
        assertEquals(product, productMaterial.getProduct());
        assertEquals(material, productMaterial.getMaterial());
        assertEquals(10.0, productMaterial.getWeight());
        assertEquals(92.5, productMaterial.getPurity());
    }
}
