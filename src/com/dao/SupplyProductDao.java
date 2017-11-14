package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.model.SupplyProduct;
@Transactional(readOnly = true) 
@Repository
public interface SupplyProductDao  extends JpaRepository{
	
	public List save(List<SupplyProduct> supplyProductData);  // These interface doesn't require implementation

}
