package com.process;

import java.io.File;

import org.springframework.stereotype.Component;

import com.exception.FileSystemException;

@Component
public interface IProcess {
	public void processFile(File fileName,String suppliedId) throws FileSystemException;
}
