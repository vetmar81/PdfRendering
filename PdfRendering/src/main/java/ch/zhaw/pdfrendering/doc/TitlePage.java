/**
 * 
 */
package ch.zhaw.pdfrendering.doc;

import java.awt.Font;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import ch.zhaw.pdfrendering.enums.DocumentContentType;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfTextArray;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Represents a title page within the document.
 * @author Markus Vetsch
 * 20.01.2012
 */
public class TitlePage
{
	private Image image;
	
	public void build(Document doc, PdfWriter writer)
	{
		try
		{
			doc.newPage();
			
			image = Image.getInstance(System.getProperty("user.dir") + File.separator + "res" + File.separator + "zhaw_logo_de.gif");
			image.setAlignment(Image.ALIGN_TOP | Image.ALIGN_RIGHT);
			
			doc.add(image);
			
			PdfContentByte pb = writer.getDirectContent();
			
			buildTitle(pb, doc);
			buildAuthorInfo(pb, doc);
		}
		catch (Exception e)
		{
			throw new RuntimeException("Unable to build titlepage!", e);
		}		
	}
	
	private void buildTitle(PdfContentByte pb, Document doc) throws DocumentException
	{
		ColumnText titleText = new ColumnText(pb);
		float lly = (float) (0.5 * Math.abs(doc.top() - doc.bottom())) - 200;
		float ury = (float) (0.5 * Math.abs(doc.top() - doc.bottom())) + 100;
		float llx = (float) (0.5 * Math.abs(doc.left() - doc.right())) - 200;
		float urx = (float) (0.5 * Math.abs(doc.left() - doc.right())) + 200;
		titleText.setSimpleColumn(llx, lly, urx, ury);
		
		Phrase title = createTitle();
		titleText.addText(title);
		titleText.go();
	}
	
	private void buildAuthorInfo(PdfContentByte pb, Document doc) throws DocumentException
	{
		ColumnText authorText = new ColumnText(pb);
		authorText.setSimpleColumn(new Rectangle(doc.leftMargin(), doc.bottomMargin(), doc.leftMargin() + 200, doc.bottomMargin() + 120));
		authorText.addText(createAuthorInfo());
		authorText.go();
	}
	
	
	private Phrase createTitle()
	{
		com.itextpdf.text.Font titleFont = new com.itextpdf.text.Font(FontFamily.HELVETICA, 48.0f,
				com.itextpdf.text.Font.BOLD, BaseColor.BLACK);
		
		Paragraph title = new Paragraph();
		title.add(new Paragraph("PDF Rendering", titleFont));
		title.add(Chunk.NEWLINE);
		title.add(Chunk.NEWLINE);
		title.add(new Paragraph("with iText", titleFont));
		title.setAlignment(Paragraph.ALIGN_CENTER);
		
		return title;
	}
	
	private Phrase createAuthorInfo()
	{
		com.itextpdf.text.Font font = new com.itextpdf.text.Font(FontFamily.TIMES_ROMAN,
																	14.0f, com.itextpdf.text.Font.ITALIC, BaseColor.BLACK);
		com.itextpdf.text.Font blueFont = new com.itextpdf.text.Font(FontFamily.TIMES_ROMAN, 14.0f,
																		com.itextpdf.text.Font.ITALIC | com.itextpdf.text.Font.UNDERLINE,
																		BaseColor.BLUE);
		
		Phrase phrase = new Phrase();
		phrase.add(new Chunk("Author:    Markus Vetsch", font));
		phrase.add(Chunk.NEWLINE);
		phrase.add(new Chunk("Address:  Roswiesenstrasse 153", font));
		phrase.add(Chunk.NEWLINE);
		phrase.add(new Chunk("City:        8051 Zürich", font));
		phrase.add(Chunk.NEWLINE);
		phrase.add(new Chunk("Phone:    +41 79 637 52 10", font));
		phrase.add(Chunk.NEWLINE);
		phrase.add(new Chunk("E-mail:    ", font));
		phrase.add(new Chunk("markusvetsch@gmx.net", blueFont).setAnchor("mailto:markusvetsch@gmx.net"));
		
		return phrase;
	}
}
