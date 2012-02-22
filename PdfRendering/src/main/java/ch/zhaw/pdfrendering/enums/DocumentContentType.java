/**
 * 
 */
package ch.zhaw.pdfrendering.enums;

/**
 * Represents the type of document information.
 * @author Markus Vetsch
 * 20.01.2012
 */
public enum DocumentContentType
{	
	/**
	 * Represents an image.
	 */
	IMAGE(5),
	
	/**
	 * Represents a heading.
	 */
	HEADING(10),
	
	/**
	 * Represents any text item.
	 */
	SIMPLE_TEXT(15),
	
	/**
	 * Represents a list with items.
	 */
	LIST(20);
	
	/**
	 * Determines the underlying ID of the {@link DocumentContentType}.
	 * @return The ID of the content type.
	 */
	public int getId()
	{
		return id;
	}
	
	private final int id;
	
	private DocumentContentType(int id)
	{
		this.id = id;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	public String toString()
	{
		switch(id)
		{
			case 5:
				return "Image";
			case 10:
				return "Heading";
			case 15:
				return "Simple Text";
			case 20:
				return "List";
			default:
				return "Unspecified";
		}
	}
}
