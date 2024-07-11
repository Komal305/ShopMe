package com.shopme.admin.user;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.shopme.common.entity.User;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

public class UserExcelExporter extends AbstractExporter{
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	
	
	public UserExcelExporter() {
		workbook = new  XSSFWorkbook();
	}

	private void writeHeaderLine() {
		sheet = workbook.createSheet("Users");
		XSSFRow row= sheet.createRow(0);
		
		XSSFCellStyle cellStyle=workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		//font.setColor(Color.GREEN);
		font.setFontHeight(15);
		cellStyle.setFont(font);
		
		createCell(row, 0, "User Id", cellStyle);
		createCell(row, 1, "Email", cellStyle);
		createCell(row, 2, "First Name", cellStyle);
		createCell(row, 3, "Last Name", cellStyle);
		createCell(row, 4, "Roles", cellStyle);
		createCell(row, 5, "Enabled", cellStyle);
		
	}
	
	private void writeDataLines(List<User> listUser)
	{
		int rowIndex = 1;
		
		XSSFCellStyle cellStyle=workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		
		font.setFontHeight(13);
		cellStyle.setFont(font);
		
		
		for(User user:listUser) {
			XSSFRow row= sheet.createRow(rowIndex++);
			int columnIndex =0 ;
			createCell(row, columnIndex++, user.getId(), cellStyle);
			createCell(row, columnIndex++, user.getEmail(), cellStyle);
			createCell(row, columnIndex++, user.getFirstName(), cellStyle);
			createCell(row, columnIndex++, user.getLastName(), cellStyle);
			createCell(row, columnIndex++, user.getRoles().toString(), cellStyle);
			createCell(row, columnIndex++, user.getEnabled(), cellStyle);
		}
	}
	
	public void export(List<User> listUser, HttpServletResponse response) throws IOException {
		super.setResponseHeader(response, "application/octet-stream", ".xlsx");
		
		
		writeHeaderLine();
		writeDataLines(listUser);
		
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
		
	}
	

	private void createCell(XSSFRow row , int columnIndex, Object value, CellStyle style) {
		XSSFCell cell = row.createCell(columnIndex);
		sheet.autoSizeColumn(columnIndex);
		
		if(value instanceof Integer) {
			cell.setCellValue((Integer) value);
		}else if(value instanceof Boolean) {
			cell.setCellValue((Boolean)value);
		}else {
			cell.setCellValue((String) value);
		}
		
		cell.setCellStyle(style);
	}
}
