package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "SupplyProduct")
@Table(name = "supplyproduct")
public class SupplyProduct {

	@Column
	private String supplierId;   
	@Column
	private String productId;
	@Column
	private String quantity;
	
	public SupplyProduct(String suppliedId, String productId, String quantity) {
		this.supplierId = suppliedId;
		this.productId = productId;
		this.quantity = quantity;
	}

	public String getSuppliedId() {
		return supplierId;
	}

	public void setSuppliedId(String suppliedId) {
		this.supplierId = suppliedId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

}
