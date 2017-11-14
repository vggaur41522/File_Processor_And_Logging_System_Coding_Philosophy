package com.process.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.dao.SupplyProductDao;
import com.exception.FileSystemException;
import com.model.SupplyProduct;

import com.model.TableIndices;
import com.process.IProcess;
import com.process.common.FileParsing;

@Component("ProcessCsv")
@Lazy
public class ProcessExcel implements IProcess{
	String delimiter = "\\s";
	FileParsing fp = new FileParsing();
	SupplyProductDao sp;
	@Override
	public void processFile(File fileName, String supplierId) throws FileSystemException {
		try{
			// This function will capture the Indices for Product 
			TableIndices ti = fp.getIndices(fileName,delimiter,"product","inventory");
			List<SupplyProduct> supplyProductData = fp.getContentFromFile(ti,fileName,supplierId,delimiter);
			// Calling JPA interface method which automatically created an implementation !!!!
			sp.save(supplyProductData);
		}
		catch (Exception e){
			throw new FileSystemException("Data insertion failed due to: "+e);
		}
	}
}
