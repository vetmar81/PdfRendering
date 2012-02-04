/**
 * 
 */
package ch.zhaw.pdfrendering.doc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
import ch.zhaw.pdfrendering.util.UnitConversion;

/**
 * Responsible for building the output document.
 * @author Markus Vetsch
 * @since 28.01.2012
 */
public class DocumentBuilder
{
	private final DocumentDefinition definition;
	private final Iterable<DocumentContent> contents;
	private Document doc;
	
	public DocumentBuilder(DocumentDefinition definition, Iterable<DocumentContent> contents)
	{
		this.definition = definition;
		this.contents = contents;
		
		doc = new Document(definition.getRect());
		doc.setMargins(definition.getMargin(DocumentEdge.LEFT), definition.getMargin(DocumentEdge.RIGHT),
						definition.getMargin(DocumentEdge.TOP), definition.getMargin(DocumentEdge.BOTTOM));
	}
	
	public void export(String path) throws IOException, DocumentException
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
		
		writer.setPageEvent(new HeaderFooter(doc));
		
		for (DocumentContent content : contents)
		{
			//TODO: Activate only, if working
			
//			if (content instanceof Heading)
//			{
//				HeadingNumberCreator.createHeadingNumber((Heading)content, contents);
//			}
			
			doc.add(content.asElement());
		}
		
		doc.addAuthor("Markus Vetsch");
		doc.addCreationDate();
		doc.addSubject("iText Document Output");
		doc.addTitle("iText Test");
		doc.addProducer();

		doc.close();
	}
	
	private void reorderPages(Document document, PdfWriter writer) throws DocumentException
	{
		int totalPages = writer.getPageNumber();
		int[] reorder = new int[totalPages];
		for (int i = 0; i < totalPages; i++)
		{
			reorder[i] = i + 1;
			if (reorder[i] > totalPages)
			reorder[i] -= totalPages;
			System.err.println("page " + reorder[i]
			+ " changes to page " + (i + 1));
		}
		
		document.newPage();
		writer.reorderPages(reorder);
	}
}
