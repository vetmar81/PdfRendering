/**
 * 
 */
package ch.zhaw.pdfrendering.manipulation;

import java.io.File;
import java.io.FileOutputStream;

import ch.zhaw.pdfrendering.PdfManipulation;
import ch.zhaw.pdfrendering.util.PdfHelper;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;

/**
 * Simple class for splitting single PDF into many PDF files.
 * @author Markus Vetsch
 * @since 05.02.2012
 */
public class PdfSplitter implements PdfManipulation
{
	private final String inputFile;
	private final String outputDirectory;
	
	public PdfSplitter(String inputFile, String outputDirectory)
	{
		this.inputFile = inputFile;
		this.outputDirectory = outputDirectory;
	}
	
	public void run()
	{		
		try
		{
			File file = new File(inputFile);
			
			if (file.isFile() && file.exists())
			{
				PdfReader reader = new PdfReader(file.getAbsolutePath());
				int pageNumber = reader.getNumberOfPages();
				
				for (int i = 1; i <= pageNumber; i++)
				{
					String outputFile = outputDirectory + File.separator + "split_" + Integer.toString(i) + ".pdf";
					
					Document doc = new Document(PageSize.A4);
					PdfCopy copy = new PdfCopy(doc, new FileOutputStream(new File(outputFile)));
					copy.setViewerPreferences(PdfCopy.PageLayoutSinglePage);
					doc.open();
					copy.addPage(copy.getImportedPage(reader, i));
					PdfHelper.addAuthorInformation(doc);
					doc.close();
				}
			}
			else
			{
				throw new RuntimeException("Invalid input file defined!");
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException("Unexpected error occurred while splitting PDF file! ", e);
		}
	}

}
