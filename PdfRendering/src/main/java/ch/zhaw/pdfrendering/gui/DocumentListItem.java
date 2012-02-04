/**
 * 
 */
package ch.zhaw.pdfrendering.gui;

import ch.zhaw.pdfrendering.doc.meta.FontDescription;
import ch.zhaw.pdfrendering.enums.DocumentContentType;

/**
 * Helper class for encapsulation of relevant object information for list view.
 * @author Markus Vetsch
 * @since 26.01.2012
 */
public class DocumentListItem
{
	private final DocumentContentType contentType;
	private String text;
	private String imagePath;
	private int headingDepth;
	
	private FontDescription fontDesc;
	
	public DocumentListItem(DocumentContentType contentType, String text)
	{
		this.contentType = contentType;
		this.text = text;
	}
	
	public DocumentListItem(DocumentContentType contentType, String text, String imagePath)
	{
		this(contentType, text);
		this.imagePath = imagePath;
	}
	
	public void setFontDescription(FontDescription fontDesc)
	{
		this.fontDesc = fontDesc;
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
	
	public void setImagePath(String imagePath)
	{
		this.imagePath = imagePath;
	}
	
	public void setHeadingDepth(int depth)
	{
		this.headingDepth = depth;
	}
	
	public FontDescription getFontDescription()
	{
		return fontDesc;
	}
	
	/**
	 * Gets the associated text.
	 * @return The text.
	 */
	public String getText()
	{
		return text;
	}
	
	/**
	 * Gets the corresponding document content type.
	 * @return The document content type.
	 */
	public DocumentContentType getType()
	{
		return contentType;
	}
	
	public String getImagePath()
	{
		return imagePath;
	}
	
	public int getHeadingDepth()
	{
		return headingDepth;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return contentType.toString();
	}
}
