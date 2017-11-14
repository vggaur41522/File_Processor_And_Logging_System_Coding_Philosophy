package com.runner;

import java.io.File;

import com.constants.Constants;
import com.dao.UploadLogDao;
import com.exception.FileSystemException;
import com.exception.InvalidExtensionException;
import com.model.ContextFromFileName;
import com.process.IProcess;
import com.process.common.FileParsing;
import com.process.factory.ProcessFactory;

public class Runner extends Thread {
	private int state;
	private String filePath;

	Runner(String filesPath) {
		this.filePath = filePath;
		state = Constants.UP_STATE;
	}

	public void setState(int state) {
		this.state = state;
	}

	// Thread overridden Run method which will keep on running
	// While loop included if we want to have just one thread !!
	@Override
	public void run() {
		FileParsing fileParser = new FileParsing();
		ProcessFactory factory = new ProcessFactory();
		while (true) {
			switch (state) {
			case Constants.UP_STATE:
				try {
					// Synchronized method to Fetch Last added file and Move it to processed folder !!
					File fileName = fileParser.pickAndMoveFile(filePath); // Synchronized Method!!!
					/*
					 *  Following two calls creates Context of operation:
					 *  Fetches - File Name, Extension of File and Pattern (If Excel or CSV)
					 */
					ContextFromFileName ctx = getContext(fileName.getName());
					validateContextAndSetPattern(ctx);

					/* Factory Call:
					 * It returns the implementation of Processing based on Pattern (EXCEL or CSV)
					 */
					IProcess process = factory.getProcessInstance(ctx);
					// Actual Processing and DB update happens here ... 
					process.processFile(fileName, ctx.getSuppliedId());
					// Once processing is done without exception. Update the log table with processed as TRUE.
					UploadLogDao.updateLog(fileName.getName(), true);
				} catch (InvalidExtensionException ie) {  // This exception occur in case Extension is incorrect !!!
					System.out.println(ie);
					turnDownSystem();
				} 
				catch (FileSystemException fe){
					turnDownSystem();
				}
				catch (Exception e) {  // General exception capture !!!
					turnDownSystem();
				}
				break;
				/*
				 * This case (DOWN_STATE) can be initiated from ListnerControlled under any specific scenario
				 * like when a particular thread is taking time more than expected !!!
				 * Just set the **** thread.setState( DOWN_STATE ) *** 
				 */
			case Constants.DOWN_STATE:
				// System Exit happens here ..
				turnDownSystem();
				break;
			}
		}
	}

	/*
	 * This method Validates if extension is correct
	 * Also it updates the Pattern. Based on Extension to either EXCEL or CSV 
	 */
	private void validateContextAndSetPattern(ContextFromFileName ctx) throws InvalidExtensionException {
		// Checking excel formats
		for (String ext : Constants.VALID_XLS_EXTENSION) {
			if (ext.equals(ctx.getExtension())) {
				ctx.setPattern("EXCEL");
				return;
			}
		}
		// Checking CSV formats
		for (String ext : Constants.VALID_CSV_EXTENSION) {
			if (ext.equals(ctx.getExtension())) {
				ctx.setPattern("CSV");
				return;
			}
		}
		throw new InvalidExtensionException("File with invalid Extension!!");
	}

	private void turnDownSystem() {
		/*
		 * Implementation for System ShutDown hook is written here. It could be
		 * as simple as System.exit(0);
		 */
		
	}

	// Updating Context for File name and Extension !!
	private ContextFromFileName getContext(String name) throws InvalidExtensionException {
		String[] splitFileName = name.split(".");
		if (splitFileName.length < 2) {
			/*
			 * THrow exception Invalid File Uploaded . Please ensure only files
			 * with acceptable formats are entered
			 */
			throw new InvalidExtensionException("File with no Extension uploaded !!");
		} else {
			String extenstion = splitFileName[1];
			String suppliedId = splitFileName[0];
			return new ContextFromFileName(extenstion, suppliedId);
		}
	}

}
