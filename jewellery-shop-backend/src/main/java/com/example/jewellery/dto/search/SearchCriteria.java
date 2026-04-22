package com.example.jewellery.dto.search;

import java.math.BigDecimal;
import java.util.List;

public class SearchCriteria {
	private String query;
	private List<Long> categoryIds;
	private List<Long> collectionIds;
	private List<Long> gemstoneIds;
	private List<Long> materialIds;
	private List<String> metalTypes;
	private List<String> styles;
	private BigDecimal minPrice;
	private BigDecimal maxPrice;
	private Double minWeight;
	private Double maxWeight;
	private Boolean isCustomizable;
	private String sortBy;
	private String sortDirection;

	public SearchCriteria() {}

	public static SearchCriteriaBuilder builder() {
		return new SearchCriteriaBuilder();
	}

	public String getQuery() { return query; }
	public void setQuery(String query) { this.query = query; }

	public List<Long> getCategoryIds() { return categoryIds; }
	public void setCategoryIds(List<Long> categoryIds) { this.categoryIds = categoryIds; }

	public List<Long> getCollectionIds() { return collectionIds; }
	public void setCollectionIds(List<Long> collectionIds) { this.collectionIds = collectionIds; }

	public List<Long> getGemstoneIds() { return gemstoneIds; }
	public void setGemstoneIds(List<Long> gemstoneIds) { this.gemstoneIds = gemstoneIds; }

	public List<Long> getMaterialIds() { return materialIds; }
	public void setMaterialIds(List<Long> materialIds) { this.materialIds = materialIds; }

	public List<String> getMetalTypes() { return metalTypes; }
	public void setMetalTypes(List<String> metalTypes) { this.metalTypes = metalTypes; }

	public List<String> getStyles() { return styles; }
	public void setStyles(List<String> styles) { this.styles = styles; }

	public BigDecimal getMinPrice() { return minPrice; }
	public void setMinPrice(BigDecimal minPrice) { this.minPrice = minPrice; }

	public BigDecimal getMaxPrice() { return maxPrice; }
	public void setMaxPrice(BigDecimal maxPrice) { this.maxPrice = maxPrice; }

	public Double getMinWeight() { return minWeight; }
	public void setMinWeight(Double minWeight) { this.minWeight = minWeight; }

	public Double getMaxWeight() { return maxWeight; }
	public void setMaxWeight(Double maxWeight) { this.maxWeight = maxWeight; }

	public Boolean getIsCustomizable() { return isCustomizable; }
	public void setIsCustomizable(Boolean customizable) { isCustomizable = customizable; }

	public String getSortBy() { return sortBy; }
	public void setSortBy(String sortBy) { this.sortBy = sortBy; }

	public String getSortDirection() { return sortDirection; }
	public void setSortDirection(String sortDirection) { this.sortDirection = sortDirection; }

	public static class SearchCriteriaBuilder {
		private SearchCriteria criteria = new SearchCriteria();

		public SearchCriteriaBuilder query(String query) {
			criteria.setQuery(query);
			return this;
		}

		public SearchCriteriaBuilder categoryIds(List<Long> categoryIds) {
			criteria.setCategoryIds(categoryIds);
			return this;
		}

		public SearchCriteriaBuilder collectionIds(List<Long> collectionIds) {
			criteria.setCollectionIds(collectionIds);
			return this;
		}

		public SearchCriteriaBuilder gemstoneIds(List<Long> gemstoneIds) {
			criteria.setGemstoneIds(gemstoneIds);
			return this;
		}

		public SearchCriteriaBuilder materialIds(List<Long> materialIds) {
			criteria.setMaterialIds(materialIds);
			return this;
		}

		public SearchCriteriaBuilder metalTypes(List<String> metalTypes) {
			criteria.setMetalTypes(metalTypes);
			return this;
		}

		public SearchCriteriaBuilder styles(List<String> styles) {
			criteria.setStyles(styles);
			return this;
		}

		public SearchCriteriaBuilder minPrice(BigDecimal minPrice) {
			criteria.setMinPrice(minPrice);
			return this;
		}

		public SearchCriteriaBuilder maxPrice(BigDecimal maxPrice) {
			criteria.setMaxPrice(maxPrice);
			return this;
		}

		public SearchCriteriaBuilder minWeight(Double minWeight) {
			criteria.setMinWeight(minWeight);
			return this;
		}

		public SearchCriteriaBuilder maxWeight(Double maxWeight) {
			criteria.setMaxWeight(maxWeight);
			return this;
		}

		public SearchCriteriaBuilder isCustomizable(Boolean isCustomizable) {
			criteria.setIsCustomizable(isCustomizable);
			return this;
		}

		public SearchCriteriaBuilder sortBy(String sortBy) {
			criteria.setSortBy(sortBy);
			return this;
		}

		public SearchCriteriaBuilder sortDirection(String sortDirection) {
			criteria.setSortDirection(sortDirection);
			return this;
		}

		public SearchCriteria build() {
			return criteria;
		}
	}
}
