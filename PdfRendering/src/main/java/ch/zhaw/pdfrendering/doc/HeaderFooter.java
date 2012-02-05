/**
 * 
 */
package ch.zhaw.pdfrendering.doc;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.io.IOException;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEvent;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Creates simple header and footer.
 * @author Markus Vetsch
 * 03.02.2012
 */

public class HeaderFooter implements PdfPageEvent
{
	private Phrase header;
	private Phrase footer;
	private PdfTemplate pageNumberTemplate;
	private BaseFont baseFont;
	
	public HeaderFooter(Document document) 
	{
		header = new Phrase("iText PDF Rendering Test - just a simple header");
		footer = new Phrase("This is just a simple footer");
	}
	
	public void onEndPage(PdfWriter writer, Document document) 
	{
		PdfContentByte cb = writer.getDirectContent();
		
		if (pageNumberTemplate == null && baseFont ==  null)
		{
			pageNumberTemplate = cb.createTemplate(100, 100);
			pageNumberTemplate.setBoundingBox(new Rectangle(-20, -20, 100, 100));
			
			try
			{
				baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}		
		}
		
		float headerPosX = ((document.right() - document.left()) / 2) + document.leftMargin();
		float footerPosY = document.bottom() - 10;
		
		if (document.getPageNumber() >= 1) 
		{
			ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, header, headerPosX, document.top(), 0);			
			ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, footer, document.leftMargin(), footerPosY, 0);
		}
		
		int linePosY = (int) (document.top() - document.bottom() - footerPosY - 20);
		int lineLeft = (int) document.left();
		int lineRight = (int) document.right();
		
		Graphics2D g = cb.createGraphics(document.right() - document.left(), document.top() - document.bottom());
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL));
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawLine(lineLeft, linePosY, lineRight, linePosY);
		g.dispose();
		
		cb.saveState();
		
		String text = "Page " + writer.getPageNumber() + " of ";
		float textSize = baseFont.getWidthPoint(text, 12);
		cb.beginText();
		cb.setFontAndSize(baseFont, 12);
		
		// Distinction for odd / even pages
		
		if ((writer.getPageNumber() % 2) == 1) 
		{
			cb.setTextMatrix(document.left(), footerPosY);
			cb.showText(text);
			cb.endText();
			cb.addTemplate(pageNumberTemplate, document.left() + textSize, footerPosY);
		}
		else 
		{
			float offset = 20;
			float adjust = baseFont.getWidthPoint("0", 12);
			cb.setTextMatrix(
			document.right() - textSize - offset - adjust, footerPosY);
			cb.showText(text);
			cb.endText();
			cb.addTemplate(pageNumberTemplate, document.right() - adjust - offset, footerPosY);
		}
		
		cb.restoreState();
	}

	public void onOpenDocument(PdfWriter writer, Document document)
	{
		
	}

	public void onStartPage(PdfWriter writer, Document document)
	{
		// TODO Auto-generated method stub
		
	}

	public void onCloseDocument(PdfWriter writer, Document document)
	{
		pageNumberTemplate.beginText();
		pageNumberTemplate.setFontAndSize(baseFont, 12);
		pageNumberTemplate.setTextMatrix(0, 0);
		pageNumberTemplate.showText(String.valueOf(writer.getPageNumber() - 1));
		pageNumberTemplate.endText();
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



