package com.example.jewellery.seed;

import com.example.jewellery.entity.category.Category;
import com.example.jewellery.repository.category.CategoryRepository;
import com.example.jewellery.entity.collection.JewelleryCollection;
import com.example.jewellery.repository.collection.CollectionRepository;
import com.example.jewellery.entity.customer.Customer;
import com.example.jewellery.repository.customer.CustomerRepository;
import com.example.jewellery.entity.gemstone.Gemstone;
import com.example.jewellery.repository.gemstone.GemstoneRepository;
import com.example.jewellery.entity.inventory.InventoryTransaction;
import com.example.jewellery.entity.inventory.TransactionType;
import com.example.jewellery.repository.inventory.InventoryTransactionRepository;
import com.example.jewellery.entity.material.Material;
import com.example.jewellery.repository.material.MaterialRepository;
import com.example.jewellery.entity.product.*;
import com.example.jewellery.repository.product.ProductGemstoneRepository;
import com.example.jewellery.repository.product.ProductMaterialRepository;
import com.example.jewellery.repository.product.ProductRepository;
import com.example.jewellery.entity.promotion.Discount;
import com.example.jewellery.entity.promotion.DiscountType;
import com.example.jewellery.repository.promotion.DiscountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {
	private final CategoryRepository categoryRepo;
	private final CollectionRepository collectionRepo;
	private final MaterialRepository materialRepo;
	private final GemstoneRepository gemstoneRepo;
	private final ProductRepository productRepo;
	private final ProductMaterialRepository productMaterialRepo;
	private final ProductGemstoneRepository productGemstoneRepo;
	private final CustomerRepository customerRepo;
	private final InventoryTransactionRepository inventoryTransactionRepo;
	private final DiscountRepository discountRepo;

	public DataSeeder(CategoryRepository categoryRepo, CollectionRepository collectionRepo,
					 MaterialRepository materialRepo, GemstoneRepository gemstoneRepo,
					 ProductRepository productRepo, ProductMaterialRepository productMaterialRepo,
					 ProductGemstoneRepository productGemstoneRepo, CustomerRepository customerRepo,
					 InventoryTransactionRepository inventoryTransactionRepo, DiscountRepository discountRepo) {
		this.categoryRepo = categoryRepo;
		this.collectionRepo = collectionRepo;
		this.materialRepo = materialRepo;
		this.gemstoneRepo = gemstoneRepo;
		this.productRepo = productRepo;
		this.productMaterialRepo = productMaterialRepo;
		this.productGemstoneRepo = productGemstoneRepo;
		this.customerRepo = customerRepo;
		this.inventoryTransactionRepo = inventoryTransactionRepo;
		this.discountRepo = discountRepo;
	}

	@Override
	public void run(String... args) {
		try {
			if (categoryRepo.count() == 0) {
				seedCategories();
			}
		} catch (Exception e) {
			// Table might not exist yet, skip seeding
		}
		try {
			if (collectionRepo.count() == 0) {
				seedCollections();
			}
		} catch (Exception e) {
			// Table might not exist yet, skip seeding
		}
		try {
			if (materialRepo.count() == 0) {
				seedMaterials();
			}
		} catch (Exception e) {
			// Table might not exist yet, skip seeding
		}
		try {
			if (gemstoneRepo.count() == 0) {
				seedGemstones();
			}
		} catch (Exception e) {
			// Table might not exist yet, skip seeding
		}
		try {
			if (productRepo.count() == 0) {
				seedProducts();
			}
		} catch (Exception e) {
			// Table might not exist yet, skip seeding
		}
		try {
			if (customerRepo.count() == 0) {
				seedCustomers();
			}
		} catch (Exception e) {
			// Table might not exist yet, skip seeding
		}
		try {
			if (discountRepo.count() == 0) {
				seedDiscounts();
			}
		} catch (Exception e) {
			// Table might not exist yet, skip seeding
		}
	}

	private void seedCategories() {
		List<Category> categories = List.of(
			new Category("Rings", "Beautiful rings for every occasion"),
			new Category("Necklaces", "Elegant necklaces and pendants"),
			new Category("Earrings", "Stunning earrings for all styles"),
			new Category("Bracelets", "Chic bracelets and bangles"),
			new Category("Watches", "Luxury timepieces"),
			new Category("Brooches", "Decorative brooches and pins")
		);

		categoryRepo.saveAll(categories);
	}

	private void seedCollections() {
		List<JewelleryCollection> collections = List.of(
			new JewelleryCollection("Spring Bloom", "Fresh designs inspired by spring flowers", "Spring 2024", 
						 LocalDate.of(2024, 3, 1), true, "/images/collections/spring-bloom.jpg"),
			new JewelleryCollection("Royal Heritage", "Classic designs with a modern twist", "Fall 2024", 
						 LocalDate.of(2024, 9, 1), true, "/images/collections/royal-heritage.jpg"),
			new JewelleryCollection("Ocean Dreams", "Inspired by the beauty of the sea", "Summer 2024", 
						 LocalDate.of(2024, 6, 1), true, "/images/collections/ocean-dreams.jpg")
		);

		collectionRepo.saveAll(collections);
	}

	private void seedMaterials() {
		List<Material> materials = List.of(
			new Material("Gold 24K", "Pure gold", true, 99.9),
			new Material("Gold 18K", "18 karat gold", true, 75.0),
			new Material("Gold 14K", "14 karat gold", true, 58.3),
			new Material("Silver 925", "Sterling silver", true, 92.5),
			new Material("Platinum", "Platinum metal", true, 95.0),
			new Material("Titanium", "Titanium metal", false, 99.0),
			new Material("Stainless Steel", "Stainless steel", false, 100.0)
		);

		materialRepo.saveAll(materials);
	}

	private void seedGemstones() {
		List<Gemstone> gemstones = List.of(
			new Gemstone("Diamond", "Brilliant diamond", "Colorless", 10.0, 1.0, true, 
						new BigDecimal("5000.00")),
			new Gemstone("Ruby", "Deep red ruby", "Red", 9.0, 1.0, true, 
						new BigDecimal("3000.00")),
			new Gemstone("Sapphire", "Blue sapphire", "Blue", 9.0, 1.0, true, 
						new BigDecimal("2500.00")),
			new Gemstone("Emerald", "Green emerald", "Green", 8.0, 1.0, true, 
						new BigDecimal("2000.00")),
			new Gemstone("Amethyst", "Purple amethyst", "Purple", 7.0, 1.0, false, 
						new BigDecimal("100.00")),
			new Gemstone("Topaz", "Blue topaz", "Blue", 8.0, 1.0, false, 
						new BigDecimal("150.00"))
		);

		gemstoneRepo.saveAll(gemstones);
	}

	private void seedProducts() {
		Category ringsCategory = categoryRepo.findByName("Rings").orElseThrow();
		Category necklacesCategory = categoryRepo.findByName("Necklaces").orElseThrow();
		Category earringsCategory = categoryRepo.findByName("Earrings").orElseThrow();
		
		JewelleryCollection springCollection = collectionRepo.findByName("Spring Bloom").orElseThrow();
		JewelleryCollection royalCollection = collectionRepo.findByName("Royal Heritage").orElseThrow();
		
		Material gold18K = materialRepo.findByName("Gold 18K").orElseThrow();
		Material silver925 = materialRepo.findByName("Silver 925").orElseThrow();
		
		Gemstone diamond = gemstoneRepo.findByName("Diamond").orElseThrow();
		Gemstone ruby = gemstoneRepo.findByName("Ruby").orElseThrow();
		Gemstone sapphire = gemstoneRepo.findByName("Sapphire").orElseThrow();

		Product diamondRing = new Product(
			"DR001", "Diamond Solitaire Ring", "Beautiful diamond solitaire ring in 18K gold",
			new BigDecimal("2500.00"), 10, "/images/products/diamond-ring.jpg",
			"Gold 18K", 5.2, 4.8, "7", "Classic", true, true,
			ringsCategory, springCollection
		);

		Product rubyNecklace = new Product(
			"RN001", "Ruby Heart Necklace", "Elegant ruby heart necklace in sterling silver",
			new BigDecimal("1200.00"), 8, "/images/products/ruby-necklace.jpg",
			"Silver 925", 8.5, 6.2, "18\"", "Romantic", true, true,
			necklacesCategory, royalCollection
		);

		Product sapphireEarrings = new Product(
			"SE001", "Sapphire Drop Earrings", "Stunning sapphire drop earrings",
			new BigDecimal("1800.00"), 6, "/images/products/sapphire-earrings.jpg",
			"Gold 18K", 3.8, 3.2, "Drop", "Elegant", false, true,
			earringsCategory, springCollection
		);

		productRepo.saveAll(List.of(diamondRing, rubyNecklace, sapphireEarrings));

		ProductMaterial diamondRingMaterial = new ProductMaterial(diamondRing, gold18K, 4.8, 75.0);
		ProductMaterial rubyNecklaceMaterial = new ProductMaterial(rubyNecklace, silver925, 6.2, 92.5);
		ProductMaterial sapphireEarringsMaterial = new ProductMaterial(sapphireEarrings, gold18K, 3.2, 75.0);

		productMaterialRepo.saveAll(List.of(diamondRingMaterial, rubyNecklaceMaterial, sapphireEarringsMaterial));

		ProductGemstone diamondRingGemstone = new ProductGemstone(diamondRing, diamond, 1, 1.5, new BigDecimal("7500.00"));
		ProductGemstone rubyNecklaceGemstone = new ProductGemstone(rubyNecklace, ruby, 1, 2.0, new BigDecimal("6000.00"));
		ProductGemstone sapphireEarringsGemstone = new ProductGemstone(sapphireEarrings, sapphire, 2, 0.8, new BigDecimal("4000.00"));

		productGemstoneRepo.saveAll(List.of(diamondRingGemstone, rubyNecklaceGemstone, sapphireEarringsGemstone));

		seedInventoryTransactions();
	}

	private void seedInventoryTransactions() {
		List<Product> products = productRepo.findAll();

		for (Product product : products) {
			InventoryTransaction purchase = new InventoryTransaction(
				product, TransactionType.PURCHASE, product.getStockQty(),
				product.getPrice().multiply(new BigDecimal("0.6")), 
				"INITIAL_STOCK", "Initial inventory setup"
			);
			inventoryTransactionRepo.save(purchase);
		}
	}

	private void seedCustomers() {
		List<Customer> customers = List.of(
			new Customer("Emma Johnson", "emma.johnson@email.com", "+1234567890"),
			new Customer("Michael Smith", "michael.smith@email.com", "+1234567891"),
			new Customer("Sophia Williams", "sophia.williams@email.com", "+1234567892"),
			new Customer("James Brown", "james.brown@email.com", "+1234567893"),
			new Customer("Olivia Davis", "olivia.davis@email.com", "+1234567894")
		);

		customerRepo.saveAll(customers);
	}

	private void seedDiscounts() {
		Discount welcomeDiscount = new Discount(
			"WELCOME10", "Welcome Discount", "10% off for new customers",
			DiscountType.PERCENTAGE, new BigDecimal("10.00"), new BigDecimal("100.00"),
			1000, true, Instant.now(), Instant.now().plusSeconds(86400 * 30)
		);

		Discount summerSpecial = new Discount(
			"SUMMER2024", "Summer Special", "$50 off orders over $500",
			DiscountType.FIXED_AMOUNT, new BigDecimal("50.00"), new BigDecimal("500.00"),
			500, true, Instant.now(), Instant.now().plusSeconds(86400 * 60)
		);

		discountRepo.saveAll(List.of(welcomeDiscount, summerSpecial));
	}
}

