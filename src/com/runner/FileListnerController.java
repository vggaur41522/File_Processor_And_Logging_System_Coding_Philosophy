package com.runner;

import java.util.Scanner;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.config.AppConfig;
import com.constants.Constants;
import com.exception.FileSystemException;
import com.exception.InvalidExtensionException;
import com.process.validation.Validator;

public class FileListnerController {
	static AnnotationConfigApplicationContext context;

	public FileListnerController() {
	}

	public static void main(String[] args) throws FileSystemException, InvalidExtensionException {
		// Spring Context Creation !!!
		context = new AnnotationConfigApplicationContext();
		context.register(AppConfig.class);
		context.refresh();

		FileListnerController fileProcessor = new FileListnerController();
		String filesPath = fileProcessor.userInputControl();

		/*
		 * Validation Before starting process : Check if file system is in
		 */
		if (!Validator.validatePath(filesPath)) {
			System.out.println("File Path Incorrect! Please re-execute the program and give correct path !!!");
		}

		/*
		 * Actual Processing happens here !!
		 */
		fileProcessor.processExecution(filesPath);
	}

	private void processExecution(String filesPath) throws FileSystemException {
		/*
		 * Initiating the thread pool
		 */
		Runner[] pool = new Runner[Constants.NUM_THREADS]; // Can use Thread  Instance as well, Just for ease of not using type-casting
		for (int i = 0; i < pool.length; i++) {
			pool[i] = new Runner(filesPath);
		}
		/*
		 * Starting the threads
		 */
		for (int i = 0; i < pool.length; i++) {
			pool[i].start();
		}
		/*
		 * We can perform actions like checking how much time a particular
		 * thread has taken It it breaches the set MAX_TIME, we can set the
		 * state for thread to DOWN_STATE and it will break call : *****
		 * pool[i].setState(Constants.DOWN_STATE) *****
		 */
		pool[0].setState(Constants.DOWN_STATE);

		/*
		 * Here we can perform additional handling for Thread Join and Wait
		 * operations
		 */

		for (int i = 0; i < pool.length; i++) {
			try {
				pool[i].join();
			} catch (InterruptedException e) {
				throw new FileSystemException("Thread Exceution failed for: " + pool[i].getName());
			}
		}

	}

	private String userInputControl() {
		/*
		 * User Input for File Path System
		 */
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter the fully qualified path where Files are placed:");
		String filesPath = sc.next();
		sc.close();
		return filesPath;
	}

}
