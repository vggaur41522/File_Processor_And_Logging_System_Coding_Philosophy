package com.process.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.constants.Constants;
import com.dao.UploadLogDao;
import com.exception.FileSystemException;
import com.model.SupplyProduct;
import com.model.TableIndices;

public class FileParsing {

	/* 
	 * This method tries to find the Index of Product and Quantity and Update the TableIndices Object
	 * Later we will use these indices to just fetch these specific items !!!
	 */
	public TableIndices getIndices(File fileName,String delimiter, String productTag, String quantityTag) throws IOException{
		// Simple implementation. It will check the content of first line
		BufferedReader brTest = new BufferedReader(new FileReader(fileName.getAbsolutePath()));
		String textFirstLine = brTest.readLine();
		// Split the first Line based on delimiter !!
		String[] splitStr = textFirstLine.split(delimiter);
		TableIndices tb = new TableIndices();
		// Basically we going through each column name and comparing it with know Column Names !!!
		for(int i=0;i<splitStr.length;i++){
			if(splitStr[i].equalsIgnoreCase(productTag))
				tb.setProductIdIndex(i);
			if(splitStr[i].equalsIgnoreCase(quantityTag))
				tb.setQuantityIndex(i);
		}
		return tb;
	}

	/*
	 * Returns the current time stamp
	 */
	public Timestamp getCurrentTimeStamp(){
		Date date = new Date();
		// getTime() returns current time in milliseconds
		long time = date.getTime();
		// Passed the milliseconds to constructor of Timestamp class
		Timestamp ts = new Timestamp(time);
		return ts;
	}
	/*
	 * This Method picks any Unprocessed File and send it back. Also move the file to processed folder !
	 */
	public synchronized File pickAndMoveFile(String path) throws FileSystemException{
		File fileName = pickLastUpdatedFile(path);
		if(moveFile(fileName)){
			// Update Logs by calling UploadLogDao
			UploadLogDao.insertInto(fileName.getName(),getCurrentTimeStamp(), false);
		}
		else
		{
			// Throw Exception !!!  [ Either File Not Exist or File Sytem Error ] 
			throw new FileSystemException("Inserting to Log file failed. File system error");
		}
		return fileName;
	}

	/*
	 * This method picks the Latest Modified File from directory
	 */
	public File pickLastUpdatedFile(String dir) {
		File fl = new File(dir);
		File[] files = fl.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.isFile();
			}
		});
		long lastMod = Long.MIN_VALUE;
		File choice = null;
		for (File file : files) {
			if (file.lastModified() > lastMod) {
				choice = file;
				lastMod = file.lastModified();
			}
		}
		return choice;
	}

	/*
	 * This method ensures that processes files are moved to processed Directory
	 * Also, Once the processing is done we will update the UploadLog table
	 */
	public boolean moveFile(File currFile) throws FileSystemException {
		try {
			if (currFile.renameTo(new File(Constants.PROCESSED_FILE_PATH + currFile.getName()))) {
				System.out.println("File is moved successful!");
				return true;
			} else {
				System.out.println("File is failed to move!");
				return false;
			}

		} catch (Exception e) {
			throw new FileSystemException("Moving file to processed Folder failed due to:"+e);
		}
	}

	/* 
	 * This is an important method which will fetch the List of SupplyProduct Object 
	 * I am using Java 8 Stream over here !!!!
	 */
	public List<SupplyProduct> getContentFromFile(TableIndices ti, File fileName, String supplierId, String delimiter) throws FileSystemException {
		List<SupplyProduct> resultList;
		// Java 8 Stream to read file content .... 
		try (Stream<String> stream = Files.lines(Paths.get(fileName.getAbsolutePath()))) {
			resultList = stream.map(string -> string.split(delimiter))
				  .map(array -> new SupplyProduct(supplierId, array[ti.getProductIdIndex()],array[ti.getQuantityIndex()]))
				  .collect(Collectors.toList());
			//Result list will contain all the items from File. Skipping the first record !!!
			resultList.remove(0);
			
		} catch (IOException e) {
			throw new FileSystemException("Exception during reading content: "+e);
		}
		return resultList;
	}
}
