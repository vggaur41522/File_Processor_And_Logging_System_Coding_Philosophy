package com.model;

public class ContextFromFileName {

	public ContextFromFileName(String extension, String suppliedId) {
		this.extension = extension;
		this.suppliedId = suppliedId;
	}

	private String extension;
	private String suppliedId;
	private String pattern;

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getSuppliedId() {
		return suppliedId;
	}

	public void setSuppliedId(String suppliedId) {
		this.suppliedId = suppliedId;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

}
