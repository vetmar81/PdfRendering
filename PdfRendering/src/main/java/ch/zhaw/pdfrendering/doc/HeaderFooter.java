/**
 * 
 */
package ch.zhaw.pdfrendering.doc;

import java.awt.Font;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEvent;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Creates simple header and footer.
 * @author Markus Vetsch
 * 03.02.2012
 */

public class HeaderFooter implements PdfPageEvent
{
	private Phrase header;
	private PdfPTable footer;
	
	public HeaderFooter(Document document) 
	{
		header = new Phrase("iText PDF Rendering Test - just a simple header");
		footer = new PdfPTable(1);
		footer.setTotalWidth(Math.abs(document.left() - document.right()));
		footer.getDefaultCell()
		.setHorizontalAlignment(Element.ALIGN_CENTER);
		footer.addCell(new Phrase(new Chunk("This is just a simple footer")
		.setAction(null)));
//		footer.addCell(new Phrase(new Chunk()
//		.setAction(new PdfAction(PdfAction.NOTHING))));
//		footer.addCell(new Phrase(new Chunk()
//		.setAction(new PdfAction(PdfAction.NOTHING))));
//		footer.addCell(new Phrase(new Chunk()
//		.setAction(new PdfAction(PdfAction.NOTHING))));
	}
	
	public void onEndPage(PdfWriter writer, Document document) 
	{
		PdfContentByte cb = writer.getDirectContent();
		if (document.getPageNumber() >= 1) 
		{
			ColumnText.showTextAligned(cb,
			Element.ALIGN_CENTER, header,
			(document.right() - document.left()) / 2
			+ document.leftMargin(), document.top(), 0);
		}
		
		footer.writeSelectedRows(0, -1, 0
		+ document.leftMargin(), document.bottom(), cb);
	}

	public void onOpenDocument(PdfWriter writer, Document document)
	{
		// TODO Auto-generated method stub
		
	}

	public void onStartPage(PdfWriter writer, Document document)
	{
		// TODO Auto-generated method stub
		
	}

	public void onCloseDocument(PdfWriter writer, Document document)
	{

		
	}

	public void onParagraph(PdfWriter writer, Document document,
			float paragraphPosition)
	{
		// TODO Auto-generated method stub
		
	}

	public void onParagraphEnd(PdfWriter writer, Document document,
			float paragraphPosition)
	{
		// TODO Auto-generated method stub
		
	}

	public void onChapter(PdfWriter writer, Document document,
			float paragraphPosition, Paragraph title)
	{
		// TODO Auto-generated method stub
		
	}

	public void onChapterEnd(PdfWriter writer, Document document,
			float paragraphPosition)
	{
		// TODO Auto-generated method stub
		
	}

	public void onSection(PdfWriter writer, Document document,
			float paragraphPosition, int depth, Paragraph title)
	{
		// TODO Auto-generated method stub
		
	}

	public void onSectionEnd(PdfWriter writer, Document document,
			float paragraphPosition)
	{
		// TODO Auto-generated method stub
		
	}

	public void onGenericTag(PdfWriter writer, Document document,
			Rectangle rect, String text)
	{
		// TODO Auto-generated method stub
		
	}
}



