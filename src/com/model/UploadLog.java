package com.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "UploadLog")
@Table(name = "uploadlog")
public class UploadLog {
	
	@Column
	private String fileName;
	@Column
	private Timestamp timeStamp;
	@Column
	private boolean uploadComplete;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	public boolean isUploadComplete() {
		return uploadComplete;
	}

	public void setUploadComplete(boolean uploadComplete) {
		this.uploadComplete = uploadComplete;
	}

}
