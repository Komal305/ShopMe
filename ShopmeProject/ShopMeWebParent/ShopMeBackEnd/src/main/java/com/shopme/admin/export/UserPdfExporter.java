package com.shopme.admin.export;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.shopme.admin.user.AbstractExporter;
import com.shopme.common.entity.User;

import jakarta.servlet.http.HttpServletResponse;

public class UserPdfExporter extends AbstractExporter {

	public void export(List<User> listUser, HttpServletResponse response) throws IOException {
		super.setResponseHeader(response, "application/text", ".pdf");
		Document document=new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.BLUE);
		font.setSize(15);
		
		Paragraph paragraph=new Paragraph("List of Users", font);
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);
		
		document.add(paragraph);
		
		PdfPTable table=new PdfPTable(6);
		table.setWidthPercentage(100f);
		table.setSpacingBefore(10);
		table.setWidths(new float[] {1.0f, 4.5f, 2.0f, 2.0f, 3.0f, 1.5f});
		
		writeTableHeader(table);
		writeTableData(table, listUser);
		
		document.add(table);
		document.close();
	
	}
	
	private void writeTableHeader(PdfPTable table)
	{
		PdfPCell cell= new PdfPCell();
		cell.setBackgroundColor(Color.cyan);
		cell.setPadding(5);
		cell.hasBorder(2);
		
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.white);
		
		cell.setPhrase(new Phrase("ID", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("EMAIL", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("FIRST NAME", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("LAST NAME", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("ROLES", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("ENABLED", font));
		table.addCell(cell);
	}
	
	private void writeTableData(PdfPTable table, List<User> listUsers)
	{
		for(User user:listUsers)
		{
			table.addCell(String.valueOf(user.getId()));
			table.addCell(user.getEmail());
			table.addCell(user.getFirstName());
			table.addCell(user.getLastName());
			table.addCell(user.getRoles().toString());
			table.addCell(String.valueOf(user.getEnabled()));
		}
	}
}
