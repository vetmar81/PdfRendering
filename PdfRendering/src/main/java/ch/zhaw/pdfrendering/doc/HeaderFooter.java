/**
 * 
 */
package ch.zhaw.pdfrendering.doc;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEvent;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Creates a header and footer based on implementing the {@link PdfPageEvent} interface.
 * @author Markus Vetsch
 * @since 03.02.2012
 */
public class HeaderFooter implements PdfPageEvent
{
	private Phrase header;
	private Phrase footer;
	private PdfTemplate pageNumberTemplate;
	private BaseFont baseFont;
	
	/**
	 * Creates a header and footer for specified {@link Document} with default texts.
	 * The header and footer creation is optimized for page format A4.
	 * @param document - The {@link Document} to create a header and footer for.
	 */
	public HeaderFooter(Document document) 
	{
		this(document, "Header - created with iText PDF", "Footer - created with iText PDF");
	}
	
	/**
	 * Creates a header and footer for specified {@link Document} with specified texts.
	 * The header and footer creation is optimized for page format A4.
	 * @param document - The {@link Document} to create a header and footer for.
	 * @param headerText - The text to be rendered in a header. section
	 * @param footerText - The text to be rendered in the footer. section
	 */
	public HeaderFooter(Document document, String headerText, String footerText)
	{
		header = new Phrase(headerText);
		footer = new Phrase(footerText);
	}
	
	/* (non-Javadoc)
	 * @see com.itextpdf.text.pdf.PdfPageEvent#onEndPage(com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
	 */
	public void onEndPage(PdfWriter writer, Document document) 
	{
		PdfContentByte cb = writer.getDirectContent();
		boolean isOddPage = (writer.getPageNumber() % 2) == 1;
		
		// Create a template for the page number creation
		
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
		
		// Swap header / footer left / right position for even / odd pages
		
		float headerPosX = (isOddPage) ? document.leftMargin() : ((document.right() - document.left()) / 2) + document.leftMargin() + 30;
		float footerPosY = document.bottom() - 10;
		
		if (document.getPageNumber() >= 1) 
		{
			ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, header, headerPosX, document.top() + 15, 0);	
			
			// Swap footer positon too for even / odd pages
			
			if (isOddPage)
			{
				ColumnText.showTextAligned(cb, Element.ALIGN_RIGHT, footer, document.right(), footerPosY, 0);
			}
			else
			{
				ColumnText.showTextAligned(cb, Element.ALIGN_LEFT, footer, document.leftMargin(), footerPosY, 0);
			}
		}
		
		// Define line extent
		
		int lineLeft = (int) document.left();
		int lineRight = (int) document.right() + 50;
		
		// Define header / footer vertical position
		// Attention PDF uses different coordinate origin (lower left corner)
		// compared to java.awt.Graphics2D (upper left corner)
		
		float graphicsHeight = (document.top() + document.topMargin()) - document.bottom();
		int headerLinePosY = 0;
		int footerLinePosY = (int) (graphicsHeight - footerPosY - 20);
		
		// Draw line below header / above footer

		Graphics2D g = cb.createGraphics(lineRight - lineLeft, (document.top() + document.topMargin()) - document.bottom());
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL));
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawLine(lineLeft, headerLinePosY, lineRight, headerLinePosY);
		g.drawLine(lineLeft, footerLinePosY, lineRight, footerLinePosY);
		g.dispose();
		
		cb.saveState();
		
		// Set and measure page number text
		
		String text = "Page " + writer.getPageNumber() + " of ";
		float textSize = baseFont.getWidthPoint(text, 12);
		cb.beginText();
		cb.setFontAndSize(baseFont, 12);
		
		// Distinction for odd / even pages
		
		if (isOddPage) 
		{
			cb.setTextMatrix(document.left(), footerPosY);
			cb.showText(text);
			cb.endText();
			cb.addTemplate(pageNumberTemplate, document.left() + textSize, footerPosY);
		}
		else 
		{
			float adjust = baseFont.getWidthPoint("0", 12);
			cb.setTextMatrix(
			document.right() - textSize - adjust, footerPosY);
			cb.showText(text);
			cb.endText();
			cb.addTemplate(pageNumberTemplate, document.right() - adjust, footerPosY);
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

	/* (non-Javadoc)
	 * @see com.itextpdf.text.pdf.PdfPageEvent#onCloseDocument(com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
	 */
	public void onCloseDocument(PdfWriter writer, Document document)
	{
		// Writes the page number text on ending the page
		
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



