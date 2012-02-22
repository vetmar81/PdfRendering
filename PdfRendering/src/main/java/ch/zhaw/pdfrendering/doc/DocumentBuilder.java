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
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfBoolean;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfWriter;

import ch.zhaw.pdfrendering.doc.meta.DocumentDefinition;
import ch.zhaw.pdfrendering.doc.meta.FontDescription;
import ch.zhaw.pdfrendering.enums.DocumentEdge;
import ch.zhaw.pdfrendering.util.HeadingNumberCreator;
import ch.zhaw.pdfrendering.util.PdfHelper;
import ch.zhaw.pdfrendering.util.UnitConversion;

/**
 * Responsible for building the output document.
 * @author Markus Vetsch
 * @since 28.01.2012
 */
public class DocumentBuilder
{
	private final DocumentDefinition definition;
	private final Collection<DocumentContent> contents;
	private Document doc;
	private HeaderFooter headerFooter;
	
	public DocumentBuilder(DocumentDefinition definition, Collection<DocumentContent> contents)
	{
		this.definition = definition;
		this.contents = contents;
		
		doc = new Document(definition.getRect());
		doc.setMargins(definition.getMargin(DocumentEdge.LEFT), definition.getMargin(DocumentEdge.RIGHT),
						definition.getMargin(DocumentEdge.TOP), definition.getMargin(DocumentEdge.BOTTOM));
	}
	
	public void createHeaderFooter(String headerText, String footerText)
	{
		headerFooter = new HeaderFooter(doc, headerText, footerText);
	}
	
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
			
			new TitlePage().build(doc, writer);		
			doc.newPage();
			
			if (contents.size() > 0)
			{
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
					//TODO: Activate only, if working
					
//					if (content instanceof Heading)
//					{
//						HeadingNumberCreator.createHeadingNumber((Heading)content, contents);
//					}
					
					doc.add(content.asElement());
				}
				
			}			
			
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
			if (doc.isOpen())
			{
				doc.close();
			}
		}
	}
}
