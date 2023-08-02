package com.hackathon.functions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.*;

public class ExcelUtil {
	public XSSFWorkbook workbook;
	
	public ExcelUtil() {
		workbook = new XSSFWorkbook();
	}
	
	public ExcelUtil(String filename) {
		FileInputStream fr = null;
		try {
			fr = new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			workbook = new XSSFWorkbook(fr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeToExcel(String filename) {
		FileOutputStream fw = null;
		
		try {
			File file = new File(filename);
			file.createNewFile();
			fw = new FileOutputStream(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			workbook.write(fw);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
