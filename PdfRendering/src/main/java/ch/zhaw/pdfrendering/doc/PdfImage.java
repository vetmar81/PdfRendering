/**
 * 
 */
package ch.zhaw.pdfrendering.doc;

import java.io.IOException;

import ch.zhaw.pdfrendering.enums.DocumentContentType;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;

/**
 * Simple class representing image content.
 * @author Markus Vetsch
 * @since 26.01.2012
 */
public class PdfImage implements DocumentContent
{
	private Paragraph par;
	private Image image; 
	
	public PdfImage(String imagePath) throws IOException, BadElementException
	{
		image = Image.getInstance(imagePath);
		image.setAlignment(Image.ALIGN_CENTER);
		par = new Paragraph();
		par.add(image);
	}
	
	/* (non-Javadoc)
	 * @see ch.zhaw.pdfrendering.doc.DocumentContent#getText()
	 */
	public String getText()
	{
		return "";
	}
	
	/* (non-Javadoc)
	 * @see ch.zhaw.pdfrendering.doc.DocumentContent#asElement()
	 */
	public Element asElement()
	{
		return par;
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.pdfrendering.doc.DocumentContent#getType()
	 */
	public DocumentContentType getType()
	{
		return DocumentContentType.IMAGE;
	}

}
