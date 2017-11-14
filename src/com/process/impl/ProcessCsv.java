package com.process.impl;

import java.io.File;
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
public class ProcessCsv implements IProcess{
	String delimiter = ",";
	FileParsing fp = new FileParsing();
	SupplyProductDao sp;
	@Override
	public void processFile(File fileName, String supplierId) throws FileSystemException{
		try{
			TableIndices ti = fp.getIndices(fileName,delimiter,"Product","Quantity");
			List<SupplyProduct> supplyProductData = fp.getContentFromFile(ti,fileName,supplierId,delimiter);
			// Calling JPA method to save Data !!!
			sp.save(supplyProductData);
		}
		catch (Exception e){
			throw new FileSystemException("Data insertion failed due to: "+e);
		}
	}

}
