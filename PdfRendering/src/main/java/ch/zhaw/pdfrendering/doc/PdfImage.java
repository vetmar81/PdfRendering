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
	
	/**
	 * Creates a new {@link PdfImage} instance for PDF embedding of an image file.
	 * @param imagePath - The file path of the image file.
	 * @throws IOException
	 * @throws BadElementException
	 */
	public PdfImage(String imagePath) throws IOException, BadElementException
	{
		image = Image.getInstance(imagePath);
		image.setAlignment(Image.ALIGN_CENTER);
		image.scalePercent(50);
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
