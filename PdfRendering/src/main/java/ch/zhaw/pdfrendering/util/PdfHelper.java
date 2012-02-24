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
	/**
	 * No instance shall be created of this class.
	 */
	private PdfHelper()
	{		
	}
	
	/**
	 * Displays and opens the PDF file in given path within Adobe Reader.
	 * @param filePath - The PDF file path.
	 */
	public static void displayPdf(String filePath)
	{
		try
		{
			Runtime.getRuntime().exec(new String[] {Property.getAdobeReaderPath(), filePath});
		}
		catch (IOException e)
		{
			throw new RuntimeException("PDF can't be displayed!", e);
		}		
	}
	
	/**
	 * Adds author information to the metadata of the {@link Document} to be created.
	 * @param document - The {@link Document} to be created.
	 */
	public static void addAuthorInformation(Document document)
	{
		document.addAuthor(System.getProperty("user.name"));
		document.addCreationDate();
		document.addSubject("iText Document Output");
		document.addTitle("iText - PDF evaluation");
		document.addProducer();
	}
}
