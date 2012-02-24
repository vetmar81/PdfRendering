/**
 * 
 */
package ch.zhaw.pdfrendering.gui;

import ch.zhaw.pdfrendering.doc.DocumentContent;
import ch.zhaw.pdfrendering.doc.Heading;
import ch.zhaw.pdfrendering.doc.meta.FontDescription;
import ch.zhaw.pdfrendering.enums.DocumentContentType;

/**
 * Helper class for encapsulation of relevant object information for list view model.
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
	
	private DocumentContent content;
	
	/**
	 * Creates a new {@link DocumentListItem} from specified {@link DocumentContent}
	 * @param content - The content, which to create a new {@link DocumentListItem} for.
	 */
	public DocumentListItem(DocumentContent content)
	{
		this(content.getType(), content.getText());
		
		this.content = content;
	}
	
	/**
	 * Creates a new {@link DocumentListItem} from specified {@link DocumentContentType} and text.
	 * @param contentType - The type of content.
	 * @param text - The assigned text.
	 */
	public DocumentListItem(DocumentContentType contentType, String text)
	{
		this(contentType, text, null);
	}
	
	/**
	 * @param contentType
	 * @param text
	 * @param imagePath
	 */
	public DocumentListItem(DocumentContentType contentType, String text, String imagePath)
	{
		this.contentType = contentType;
		this.text = text;
		this.imagePath = imagePath;
	}
	
	/**
	 * Determines, whether a concrete content was assigned to this {@link DocumentListItem}.
	 * @return true, if a concrete content was assigned; otherwise false.
	 */
	public boolean hasContent()
	{
		return content != null;
	}
	
	/**
	 * Gets the concrete content, that was assigned.
	 * @return The assigned {@link DocumentContent}.
	 */
	public DocumentContent getContent()
	{
		return content;
	}
	
	/**
	 * Sets the {@link FontDescription}.
	 * @param fontDesc - The {@link FontDescription} to be applied.
	 */
	public void setFontDescription(FontDescription fontDesc)
	{
		this.fontDesc = fontDesc;
	}
	
	/**
	 * Sets the text
	 * @param text - The text to be applied.
	 */
	public void setText(String text)
	{
		this.text = text;
	}
	
	/**
	 * Sets the image path.
	 * @param imagePath - The image path to be applied.
	 */
	public void setImagePath(String imagePath)
	{
		this.imagePath = imagePath;
	}
	
	/**
	 * Sets the image {@link Heading} depth.
	 * @param imagePath - The {@link Heading} depth to be applied.
	 */
	public void setHeadingDepth(int depth)
	{
		this.headingDepth = depth;
	}
	
	/**
	 * Gets the assigned {@link FontDescription}.
	 * @return The assigned {@link FontDescription}.
	 */
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
	
	/**
	 * Gets the image path.
	 * @return - The assigned image path.
	 */
	public String getImagePath()
	{
		return imagePath;
	}
	
	/**
	 * Gets the depth of assigned {@link Heading}.
	 * @return The depth of the assigned {@link Heading}.
	 */
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
