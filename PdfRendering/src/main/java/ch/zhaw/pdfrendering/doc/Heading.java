/**
 * 
 */
package ch.zhaw.pdfrendering.doc;

import com.itextpdf.text.Paragraph;

import ch.zhaw.pdfrendering.doc.meta.FontDescription;
import ch.zhaw.pdfrendering.doc.meta.FontStyle;
import ch.zhaw.pdfrendering.enums.DocumentContentType;
import ch.zhaw.pdfrendering.enums.HeadingLevel;

/**
 * Represents a heading in a document structure.
 * @author Markus Vetsch
 * @version 1.0, 11.12.2011
 */
public class Heading implements DocumentContent
{	
	private static final float LINE_SPACING = 40;
	
	private static final int SIZE_FIRST = 24;
	private static final int SIZE_SECOND = 18;
	private static final int SIZE_THIRD = 16;
	private static final int SIZE_FOURTH = 14;
	
	private static final float INDENT_FIRST = 0.0f;
	private static final float INDENT_SECOND = 5.0f;
	private static final float INDENT_THIRD = 10.0f;
	private static final float INDENT_FOURTH = 15.0f;
	
	private com.itextpdf.text.Paragraph par;
	private final FontDescription fontDesc;	
	private final HeadingLevel level;
	private String text;

	/**
	 * Creates a new {@link Heading}. Use {@link Heading#create(String, String)} 
	 * or {@link Heading#create(HeadingLevel, String, String)} to create a new instance.
	 * @param fontName - The font to be used for the {@link Heading}.
	 * @param text - The text of the {@link Heading} to be displayed.
	 * @param size - The font size of the {@link Heading}.
	 * @param level - The {@link HeadingLevel}.
	 */
	private Heading(String fontName, String text, int size, HeadingLevel level)
	{
		this.text = text;
		this.level = level;
			
		fontDesc = createDescription(fontName, size);
		par = new com.itextpdf.text.Paragraph(text, fontDesc.getFont());
		
		// Default line spacing 40 pts
		
		par.setLeading(LINE_SPACING);
		
		setIndentByLevel(par);
	}
	
	/* (non-Javadoc)
	 * @see ch.zhaw.pdfrendering.doc.DocumentContent#getText()
	 */
	public String getText()
	{
		return text;
	}
	
	/** Returns the {@link HeadingLevel} of the current instance.
	 * @return The {@link HeadingLevel}.
	 */
	public HeadingLevel getLevel()
	{
		return level;
	}
	
	/**
	 * Creates a new {@link Heading} with default level, specified font and text
	 * @param fontName - The font to be used for the {@link Heading}
	 * @param text - The text of the {@link Heading}
	 * @return The generated {@link Heading}.
	 */
	public static Heading create(String fontName, String text)
	{
		return create(HeadingLevel.DEFAULT, fontName, text);
	}

	/**
	 * Creates a new {@link Heading} with default level, specified font and text
	 * @param level - The {@link HeadingLevel} to be applied.
	 * @param fontName - The font to be used for the {@link Heading}
	 * @param text - The text of the {@link Heading}
	 * @return The generated {@link Heading}.
	 */
	public static Heading create(HeadingLevel level, String fontName, String text)
	{		
		switch(level)
		{
			case CHAPTER:
				return new Heading(fontName, text, SIZE_FIRST, level);
			case SECTION_FIRST:
				return new Heading(fontName, text, SIZE_SECOND, level);
			case SECTION_SECOND:
				return new Heading(fontName, text, SIZE_THIRD, level);
			case SECTION_THIRD:
				return new Heading(fontName, text, SIZE_FOURTH, level);
				
				// Default is always first level heading	
			
			default:
				return new Heading(fontName, text, SIZE_FIRST, level);
		}
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.pdfrendering.doc.DocumentContent#asElement()
	 */
	public com.itextpdf.text.Element asElement()
	{		
		return par;
	}
	
	/* (non-Javadoc)
	 * @see ch.zhaw.pdfrendering.doc.DocumentContent#getType()
	 */
	public DocumentContentType getType()
	{
		return DocumentContentType.HEADING;
	}
	
	/**
	 * Creates a {@link FontDescription} for the specified font name and font size.
	 * @param fontName - The name of the font.
	 * @param size - The size of the font.
	 * @return
	 */
	private FontDescription createDescription(String fontName, int size)
	{
		return new FontDescription(fontName, size, FontStyle.BOLD, com.itextpdf.text.BaseColor.BLACK);
	}
	
	/**
	 * Sets the indentation by {@link HeadingLevel}.
	 * @param par - The underlying iText {@link Paragraph} instance.
	 */
	private void setIndentByLevel(com.itextpdf.text.Paragraph par)
	{
		switch(level)
		{
			case CHAPTER:
				par.setIndentationLeft(INDENT_FIRST);
				break;
			case SECTION_FIRST:
				par.setIndentationLeft(INDENT_SECOND);
				break;
			case SECTION_SECOND:
				par.setIndentationLeft(INDENT_THIRD);
				break;
			case SECTION_THIRD:
				par.setIndentationLeft(INDENT_FOURTH);
				break;
		}
	}
}
