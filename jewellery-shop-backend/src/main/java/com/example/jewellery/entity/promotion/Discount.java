package com.example.jewellery.entity.promotion;

import com.example.jewellery.entity.category.Category;
import com.example.jewellery.entity.collection.JewelleryCollection;
import com.example.jewellery.entity.product.Product;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "discounts", indexes = {
		@Index(name = "idx_discount_code", columnList = "code", unique = true),
		@Index(name = "idx_discount_active", columnList = "is_active"),
		@Index(name = "idx_discount_start_date", columnList = "start_date"),
		@Index(name = "idx_discount_end_date", columnList = "end_date")
})
public class Discount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 20)
	private String code;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(length = 500)
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private DiscountType discountType;

	@Column(nullable = false)
	private BigDecimal value;

	@Column(nullable = false)
	private BigDecimal minimumOrderAmount;

	@Column(nullable = false)
	private Integer usageLimit;

	@Column(nullable = false)
	private Integer usageCount;

	@Column(nullable = false)
	private Boolean isActive;

	@Column(nullable = false)
	private Instant startDate;

	@Column(nullable = false)
	private Instant endDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "applicable_category_id")
	private Category applicableCategory;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "applicable_collection_id")
	private JewelleryCollection applicableCollection;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "applicable_product_id")
	private Product applicableProduct;

	protected Discount() {}

	public Discount(String code, String name, String description, DiscountType discountType, 
					BigDecimal value, BigDecimal minimumOrderAmount, Integer usageLimit, 
					Boolean isActive, Instant startDate, Instant endDate) {
		this.code = code;
		this.name = name;
		this.description = description;
		this.discountType = discountType;
		this.value = value;
		this.minimumOrderAmount = minimumOrderAmount;
		this.usageLimit = usageLimit;
		this.usageCount = 0;
		this.isActive = isActive;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public DiscountType getDiscountType() {
		return discountType;
	}

	public void setDiscountType(DiscountType discountType) {
		this.discountType = discountType;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public BigDecimal getMinimumOrderAmount() {
		return minimumOrderAmount;
	}

	public void setMinimumOrderAmount(BigDecimal minimumOrderAmount) {
		this.minimumOrderAmount = minimumOrderAmount;
	}

	public Integer getUsageLimit() {
		return usageLimit;
	}

	public void setUsageLimit(Integer usageLimit) {
		this.usageLimit = usageLimit;
	}

	public Integer getUsageCount() {
		return usageCount;
	}

	public void setUsageCount(Integer usageCount) {
		this.usageCount = usageCount;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean active) {
		isActive = active;
	}

	public Instant getStartDate() {
		return startDate;
	}

	public void setStartDate(Instant startDate) {
		this.startDate = startDate;
	}

	public Instant getEndDate() {
		return endDate;
	}

	public void setEndDate(Instant endDate) {
		this.endDate = endDate;
	}

	public Category getApplicableCategory() {
		return applicableCategory;
	}

	public void setApplicableCategory(Category applicableCategory) {
		this.applicableCategory = applicableCategory;
	}

	public JewelleryCollection getApplicableCollection() {
		return applicableCollection;
	}

	public void setApplicableCollection(JewelleryCollection applicableCollection) {
		this.applicableCollection = applicableCollection;
	}

	public Product getApplicableProduct() {
		return applicableProduct;
	}

	public void setApplicableProduct(Product applicableProduct) {
		this.applicableProduct = applicableProduct;
	}
}
