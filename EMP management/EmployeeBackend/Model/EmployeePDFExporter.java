package com.EmployeeManagement.EmployeeBackend.Model;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
 
import javax.servlet.http.HttpServletResponse;
 
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

public class EmployeePDFExporter {
	
	 private List<Employee> listEmployees;
     
	    public EmployeePDFExporter(List<Employee> listEmployees) {
	        this.listEmployees = listEmployees;
	    }
	 
	    private void writeTableHeader(PdfPTable table) {
	        PdfPCell cell = new PdfPCell();
	        cell.setBackgroundColor(Color.BLUE);
	        cell.setPadding(6);
	         
	        Font font = FontFactory.getFont(FontFactory.HELVETICA);
	        font.setColor(Color.WHITE);
	         
	        cell.setPhrase(new Phrase("Employee ID", font));
	         table.addCell(cell);
	        
	        cell.setPhrase(new Phrase("First Name", font));
	        table.addCell(cell);
	        
	        cell.setPhrase(new Phrase("Last Name", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("E-mail", font));
	        table.addCell(cell);
	         
	         
	        cell.setPhrase(new Phrase("Salary", font));
	        table.addCell(cell);
	         
	        cell.setPhrase(new Phrase("Job", font));
	        table.addCell(cell);       
	    }
	     
	    private void writeTableData(PdfPTable table) {
	        for (Employee user : listEmployees) {
	            table.addCell(String.valueOf(user.getId()));
	            table.addCell(user.getFirstName());
	            table.addCell(user.getLastName());
	            table.addCell(user.getEmail());
	            table.addCell(String.valueOf(user.getSalary()));
	            table.addCell(user.getJob());
	            //table.addCell(String.valueOf(user.isEnabled()));
	        }
	    }
	     
	    public void export(HttpServletResponse response) throws DocumentException, IOException {
	        Document document = new Document(PageSize.A4);
	        PdfWriter.getInstance(document, response.getOutputStream());
	         
	        document.open();
	        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
	        font.setSize(18);
	        font.setColor(Color.BLUE);
	         
	        Paragraph p = new Paragraph("List of Users", font);
	        p.setAlignment(Paragraph.ALIGN_CENTER);
	         
	        document.add(p);
	         
	        PdfPTable table = new PdfPTable(6);
	        table.setWidthPercentage(100f);
	        table.setWidths(new float[] {1.5f, 3.0f, 3.0f, 3.5f, 1.5f, 3.0f});
	        table.setSpacingBefore(10);
	         
	        writeTableHeader(table);
	        writeTableData(table);
	         
	        document.add(table);
	         
	        document.close();
	         
	    }

}
