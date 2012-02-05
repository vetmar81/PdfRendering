/**
 * 
 */
package ch.zhaw.pdfrendering.util;

import java.io.IOException;

import com.itextpdf.text.Document;

/**
 * Helper class for some custom PDF actions.
 * @author Markus Vetsch
 * @since 05.02.2012
 */
public class PdfHelper
{
	private static String readerPath = "C:\\PROGRA~2\\Adobe\\READER~1.0\\Reader\\AcroRd32.exe";
	
	private PdfHelper()
	{		
	}
	
	public static void displayPdf(String filePath)
	{
		try
		{
			Runtime.getRuntime().exec(new String[] {readerPath, filePath});
		}
		catch (IOException e)
		{
			throw new RuntimeException("PDF can't be displayed!", e);
		}		
	}
	
	public static void addAuthorInformation(Document document)
	{
		document.addAuthor(System.getProperty("user.name"));
		document.addCreationDate();
		document.addSubject("iText Document Output");
		document.addTitle("iText - PDF evaluation");
		document.addProducer();
	}
}
