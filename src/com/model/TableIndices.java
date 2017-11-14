package com.model;

public class TableIndices {
	public int getProductIdIndex() {
		return productIdIndex;
	}

	public void setProductIdIndex(int productIdIndex) {
		this.productIdIndex = productIdIndex;
	}

	public int getQuantityIndex() {
		return quantityIndex;
	}

	public void setQuantityIndex(int quantityIndex) {
		this.quantityIndex = quantityIndex;
	}

	private int productIdIndex;
	private int quantityIndex;
	
	public TableIndices(int productIdIndex, int quantityIndex) {
		this.productIdIndex = productIdIndex;
		this.quantityIndex = quantityIndex;
	}

	public TableIndices() {
	}

}
