package com.dao;

import java.sql.Timestamp;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
@Transactional(readOnly = true) 
@Repository
public class UploadLogDao {
	
	public static void insertInto(String fileName,Timestamp ts,boolean uploadComplete){
		/* Database Implementation Using JPA*/
	}
	
	public static void updateLog(String fileName,boolean uploadComplete){
		/* Database implementation using JPA */
	}
	
}
