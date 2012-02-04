/**
 * 
 */
package ch.zhaw.pdfrendering.doc;

import ch.zhaw.pdfrendering.doc.meta.FontDescription;
import ch.zhaw.pdfrendering.doc.meta.FontStyle;
import ch.zhaw.pdfrendering.enums.DocumentContentType;
import ch.zhaw.pdfrendering.enums.HeadingLevel;

/**
 * Represents a heading in a document structure.
 * @author Markus Vetsch
 * @version 1.0, 11.12.2011
 */
public class Heading implements DocumentContent, Comparable<Heading>
{	
	private static final int SIZE_FIRST = 24;
	private static final int SIZE_SECOND = 18;
	private static final int SIZE_THIRD = 16;
	private static final int SIZE_FOURTH = 14;
	
	private static final float INDENT_FIRST = 0.0f;
	private static final float INDENT_SECOND = 5.0f;
	private static final float INDENT_THIRD = 10.0f;
	private static final float INDENT_FOURTH = 15.0f;
	
	private com.itextpdf.text.Paragraph par;
	private com.itextpdf.text.Section section;
	
	private final FontDescription fontDesc;	
	private final HeadingLevel level;
	private String text;

	private Heading(String fontName, String text, int size, HeadingLevel level)
	{
		this.text = text;
		this.level = level;
			
		fontDesc = createDescription(fontName, size);
		par = new com.itextpdf.text.Paragraph(text, fontDesc.getFont());
		
		setIndentByLevel(par);
	}
	
	public String getText()
	{
		return text;
	}
	
	public HeadingLevel getLevel()
	{
		return level;
	}
	
	public void updateTitle(String number)
	{
		text = String.format("%1$s) %2$s", number, text);
		par = new com.itextpdf.text.Paragraph(text, fontDesc.getFont());
		setIndentByLevel(par);
	}
	
	public static Heading create(String fontName, String text)
	{
		return create(HeadingLevel.DEFAULT, fontName, text);
	}

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

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Heading o)
	{
		return Integer.valueOf(level.getDepth()).compareTo(Integer.valueOf(o.level.getDepth()));
	}

	private FontDescription createDescription(String fontName, int size)
	{
		return new FontDescription(fontName, size, FontStyle.BOLD, com.itextpdf.text.BaseColor.BLACK);
	}
	
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
