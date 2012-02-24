/**
 * 
 */
package ch.zhaw.pdfrendering.manipulation;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import ch.zhaw.pdfrendering.PdfManipulation;
import ch.zhaw.pdfrendering.util.PdfHelper;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDestination;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfOutline;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Simple class for merging many PDF into single PDF.
 * @author Markus Vetsch
 * @since 05.02.2012
 */
public class PdfMerger implements PdfManipulation
{
	private final String pdfDirectory;
	private final String outputFilePath;
	
	/**
	 * Creates a new {@link PdfMerger} instance.
	 * @param pdfDirectory - The input directory with PDF files to be merged.
	 * @param outputFilePath - The output path of the merged file.
	 */
	public PdfMerger(String pdfDirectory, String outputFilePath)
	{
		this.pdfDirectory = pdfDirectory;
		this.outputFilePath = outputFilePath;
	}
	
	/* (non-Javadoc)
	 * @see ch.zhaw.pdfrendering.PdfManipulation#run()
	 */
	public void run()
	{
		Document document = null;
		
		try
		{
			File pdfDir = new File(pdfDirectory);
			
			if (pdfDir.isDirectory())
			{
				File outFile = new File(outputFilePath);
				FileOutputStream outStream = new FileOutputStream(outFile);
				document = new Document(PageSize.A4);				
				PdfWriter writer = PdfWriter.getInstance(document, outStream);
				
				// Create a map to store imported pages for later bookmarking
				
				Map<Integer, PdfImportedPage> importedPages = new HashMap<Integer, PdfImportedPage>();
				
				writer.setViewerPreferences(PdfWriter.PageLayoutSinglePage | PdfWriter.PageModeUseOutlines);
				document.open();
				
				int addedPages = 0;
				
				for (File pdfFile : pdfDir.listFiles(new PdfFileFilter()))
				{
					if (!pdfFile.getName().equals(outFile.getName()))
					{
						PdfReader reader = new PdfReader(pdfFile.getAbsolutePath());
						
						for (int i = 1; i <= reader.getNumberOfPages(); i++)
						{
							// Add imported page into document
							
							document.newPage();
							PdfImportedPage page = writer.getImportedPage(reader, i);
							writer.getDirectContent().addTemplate(page, 0, 0);
							addedPages++;
							importedPages.put(addedPages, page);
						}
					}					
				}
				
				PdfContentByte cb = writer.getDirectContent();
				cb.saveState();
				PdfOutline root = cb.getRootOutline();
				
				// Create bookmarks from stored page map
				
				for (Entry<Integer,PdfImportedPage> pageMapEntry : importedPages.entrySet())
				{
					PdfDestination dest = new PdfDestination(PdfDestination.FIT);
					PdfAction action = PdfAction.gotoLocalPage(pageMapEntry.getKey(), dest, writer);
					PdfOutline outline = new PdfOutline(root, action, String.format("Imported Page %s", pageMapEntry.getKey().toString()));
				}
				cb.restoreState();
			}
			else
			{
				throw new RuntimeException(String.format("Path %s is not a directory!", pdfDirectory));
			}
		}
		catch (Exception e)
		{
			throw new RuntimeException("Unexpected error occurred while merging PDF files! ", e);
		}	
		finally
		{
			if (document != null && document.isOpen())
			{
				PdfHelper.addAuthorInformation(document);
				
				document.close();
			}				
		}
	}
	
	private class PdfFileFilter implements FileFilter
	{
		public boolean accept(File path)
		{
			return path.isFile() && path.getName().endsWith(".pdf");
		}		
	}
}
