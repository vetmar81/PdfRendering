/**
 * 
 */
package ch.zhaw.pdfrendering.doc;

import java.awt.Font;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

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
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

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
			
			image = Image.getInstance(System.getProperty("user.dir") + File.separator + "res" + File.separator + "ZHAW_Logo.png");
			image.setAlignment(Image.ALIGN_TOP | Image.ALIGN_RIGHT);
			image.scalePercent(35);
			
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
		float llx = (float) (0.5 * Math.abs(doc.left() - doc.right())) - 200;
		authorText.setSimpleColumn(new Rectangle(llx, doc.bottomMargin(), doc.leftMargin() + 300, doc.bottomMargin() + 120));
		authorText.addText(createAuthorInfo());
		authorText.go();
	}
	
	
	private Phrase createTitle()
	{
		com.itextpdf.text.Font titleFont = new com.itextpdf.text.Font(FontFamily.HELVETICA, 48.0f,
													com.itextpdf.text.Font.BOLD, BaseColor.BLACK);
		
		com.itextpdf.text.Font subTitleFont = new com.itextpdf.text.Font(FontFamily.HELVETICA, 20.0f,
													com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);
		
		Paragraph title = new Paragraph();
		title.add(new Paragraph("PDF Rendering", titleFont));
		title.add(Chunk.NEWLINE);
		title.add(Chunk.NEWLINE);
		title.add(new Paragraph("with iText", titleFont));
		title.setAlignment(Paragraph.ALIGN_CENTER);
		title.add(Chunk.NEWLINE);
		title.add(Chunk.NEWLINE);
		
		Paragraph subTitle = new Paragraph();
		subTitle.add(new Paragraph("A case study for MAS-I5", subTitleFont));
		subTitle.add(Chunk.NEWLINE);
		subTitle.add(new Paragraph("module in OO programming", subTitleFont));
		subTitle.setAlignment(Paragraph.ALIGN_CENTER);
		
		title.add(subTitle);
		
		return title;
	}
	
	private Phrase createAuthorInfo()
	{
		com.itextpdf.text.Font regularFont = new com.itextpdf.text.Font(FontFamily.TIMES_ROMAN,
																	14.0f, com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);
		com.itextpdf.text.Font italicFont = new com.itextpdf.text.Font(FontFamily.TIMES_ROMAN,
																	14.0f, com.itextpdf.text.Font.ITALIC, BaseColor.BLACK);
		com.itextpdf.text.Font linkFont = new com.itextpdf.text.Font(FontFamily.TIMES_ROMAN, 14.0f,
																		com.itextpdf.text.Font.ITALIC | com.itextpdf.text.Font.UNDERLINE,
																		BaseColor.BLUE);
		
		Phrase phrase = new Phrase();
		
		Chunk offsetChunk = new Chunk(new VerticalPositionMark(), 80);
		
		Phrase author = new Phrase("Author:", regularFont);
		author.add(offsetChunk);
		author.add(new Chunk("Markus Vetsch", italicFont));
		author.add(Chunk.NEWLINE);
		
		Phrase address = new Phrase("Address:", regularFont);
		address.add(offsetChunk);
		address.add(new Chunk("Roswiesenstrasse 153", italicFont));
		address.add(Chunk.NEWLINE);
		
		Phrase city = new Phrase("City:", regularFont);
		city.add(offsetChunk);
		city.add(new Chunk("8051 Zürich", italicFont));
		city.add(Chunk.NEWLINE);
		
		Phrase phone = new Phrase("Phone:", regularFont);
		phone.add(offsetChunk);
		phone.add(new Chunk("+41 79 637 52 10", italicFont));
		phone.add(Chunk.NEWLINE);
		
		Phrase email = new Phrase("E-mail:", regularFont);
		email.add(offsetChunk);
		email.add(new Chunk("markusvetsch@gmx.net", linkFont).setAnchor("mailto:markusvetsch@gmx.net"));
		
		phrase.addAll(Arrays.asList(author, address, city, phone, email));
		
		return phrase;
	}
}
