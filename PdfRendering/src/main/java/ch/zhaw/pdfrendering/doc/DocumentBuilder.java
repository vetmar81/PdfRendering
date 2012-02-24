/**
 * 
 */
package ch.zhaw.pdfrendering.doc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

import ch.zhaw.pdfrendering.doc.meta.DocumentDefinition;
import ch.zhaw.pdfrendering.enums.DocumentEdge;
import ch.zhaw.pdfrendering.util.PdfHelper;

/**
 * Responsible for building the output document.
 * @author Markus Vetsch
 * @since 28.01.2012
 */
public class DocumentBuilder
{
	private final Collection<DocumentContent> contents;
	private Document doc;
	private HeaderFooter headerFooter;
	
	/**
	 * Creates a new {@link DocumentBuilder} instance using the specified {@link DocumentDefinition} and
	 * the collection of {@link DocumentContent} as contents.
	 * @param definition - The {@link DocumentDefinition} specifying the page format and margins.
	 * @param contents - The set of {@link DocumentContent} to be inserted into output document.
	 */
	public DocumentBuilder(DocumentDefinition definition, Collection<DocumentContent> contents)
	{
		this.contents = contents;
		
		doc = new Document(definition.getRect());
		doc.setMargins(definition.getMargin(DocumentEdge.LEFT), definition.getMargin(DocumentEdge.RIGHT),
						definition.getMargin(DocumentEdge.TOP), definition.getMargin(DocumentEdge.BOTTOM));
	}
	
	/**
	 * Creates a header and a footer within the document definition.
	 * @param headerText - The text to be displayed in the header section.
	 * @param footerText - The text to be displayed in the footer section.
	 */
	public void createHeaderFooter(String headerText, String footerText)
	{
		headerFooter = new HeaderFooter(doc, headerText, footerText);
	}
	
	/**
	 * Exports a new PDF document to the specified output path.
	 * @param path - The output path of the document.
	 * @throws IOException
	 * @throws DocumentException
	 */
	public void export(String path) throws IOException, DocumentException
	{
		try
		{
			FileOutputStream stream = new FileOutputStream(new File(path));
			PdfWriter writer = PdfWriter.getInstance(doc, stream);
			writer.setViewerPreferences(PdfWriter.PageLayoutSinglePage);
			writer.setLinearPageMode();

			if (!doc.isOpen())
			{
				doc.open();
			}
			
			// Create title page
			
			new TitlePage().build(doc, writer);		
			doc.newPage();
			
			// Add contents, if there are any
			
			if (contents.size() > 0)
			{
				// Create custom or default header / footer
				
				if (headerFooter == null)
				{
					writer.setPageEvent(new HeaderFooter(doc));
				}
				else
				{
					writer.setPageEvent(headerFooter);
				}				
				
				for (DocumentContent content : contents)
				{
					doc.add(content.asElement());
				}
				
			}
			
			// Add author information to the meta data
			
			PdfHelper.addAuthorInformation(doc);
		}
		catch (IOException ex)
		{
			throw ex;
		}
		catch (DocumentException ex)
		{
			throw ex;
		}
		finally
		{
			// Close the document in any case
			
			if (doc.isOpen())
			{
				doc.close();
			}
		}
	}
}
