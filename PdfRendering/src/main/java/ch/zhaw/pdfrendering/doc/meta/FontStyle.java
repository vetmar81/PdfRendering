/**
 * 
 */
package ch.zhaw.pdfrendering.doc.meta;

/**
 * Defines a font style to be used for rendering a text in a PDF created with iText library.
 * @author Markus Vetsch
 * @version 1.0, 11.12.2011
 */
public enum FontStyle
{
	/**
	 * Regular font representation.
	 */
	NORMAL(com.itextpdf.text.Font.NORMAL),
	/**
	 * <b>Bold</b> font representation.
	 */
	BOLD(com.itextpdf.text.Font.BOLD),
	/**
	 * <b><i>Bold / Italic</i></b> font representation.
	 */
	BOLDITALIC(com.itextpdf.text.Font.BOLDITALIC),
	/**
	 * <i>Italic</i> font representation.
	 */
	ITALIC(com.itextpdf.text.Font.ITALIC),
	/**
	 * Font representation, where text is struck through.
	 */
	STRIKETHRU(com.itextpdf.text.Font.STRIKETHRU),
	/**
	 * Font representation, where text is underlined.
	 */
	UNDERLINE(com.itextpdf.text.Font.UNDERLINE);
	
	private final int styleId;
	
	private FontStyle(int styleId)
	{
		this.styleId = styleId;
	}
	
	/**
	 * Returns the corresponding font style integer code for usage in iText library.
	 * @return The iText font style code.
	 */
	public int iTextFontStyle()
	{
		return styleId;
	}
}
