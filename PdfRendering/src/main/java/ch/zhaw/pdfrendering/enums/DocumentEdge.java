/**
 * 
 */
package ch.zhaw.pdfrendering.enums;

/**
 * Represents the document edge of a PDF page the margin applies to.
 * @author Markus Vetsch
 * @since 18.12.2011
 */
public enum DocumentEdge
{
	/**
	 * The top edge of a document.
	 */
	TOP,
	/**
	 * The right edge of a document.
	 */
	RIGHT,
	/**
	 * The bottom edge of a document.
	 */
	BOTTOM,
	/**
	 * The left edge of a document.
	 */
	LEFT,
	/**
	 * Unknown edge.
	 */
	UNKNOWN;
	
	/**
	 * Returns the {@link DocumentEdge} of a PDF page corresponding to defined {@link String} value.
	 * @param edge - The corresponding edge of the PDF page as {@link String}.
	 * @return The corresponding {@link DocumentEdge}.
	 * @throws IllegalArgumentException if invalid {@link String} value for the edge specified.
	 */
	public static DocumentEdge getEdge(String edge)
	{
		if (edge.equalsIgnoreCase("top"))
		{
			return TOP;
		}
		else if (edge.equalsIgnoreCase("right"))
		{
			return RIGHT;
		}
		else if (edge.equalsIgnoreCase("bottom"))
		{
			return BOTTOM;
		}
		else if (edge.equalsIgnoreCase("left"))
		{
			return LEFT;
		}
		else
		{
			throw new IllegalArgumentException("Invalid edge defined!");
		}
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString()
	{
		return super.toString().toLowerCase();
	}

}
