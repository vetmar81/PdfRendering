/**
 * 
 */
package ch.zhaw.pdfrendering.doc;

import ch.zhaw.pdfrendering.doc.meta.FontDescription;
import ch.zhaw.pdfrendering.doc.meta.FontStyle;
import ch.zhaw.pdfrendering.enums.DocumentContentType;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;

/**
 * Represents simple text part.
 * @author Markus Vetsch
 * @since 26.01.2012
 */
public class SimpleText implements DocumentContent
{
	private static float LINE_SPACING = 25;
	
	private FontDescription fontDesc;
	private Paragraph par;
	
	/**
	 * Represents a simple text content with specified font name and size.
	 * @param fontName - The font name.
	 * @param fontSize - the font size
	 */
	public SimpleText(String fontName, int fontSize)
	{
		this(new FontDescription(fontName, fontSize, FontStyle.NORMAL, BaseColor.BLACK));
	}
	
	/**
	 * Creates a new simple text content with specified {@link FontDescription} and default line spacing.
	 * @param fontDesc - The font description.
	 */
	public SimpleText(FontDescription fontDesc)
	{
		this(fontDesc, LINE_SPACING);
	}
	
	/**
	 * Creates a new simple text content with specified {@link FontDescription} and line spacing.
	 * @param fontDesc - The font description.
	 * @param lineSpacing - The line spacing value in points.
	 */
	public SimpleText(FontDescription fontDesc, float lineSpacing)
	{
		this.fontDesc = fontDesc;
		par = new Paragraph(lineSpacing);
	}
	
	/* (non-Javadoc)
	 * @see ch.zhaw.pdfrendering.doc.DocumentContent#getText()
	 */
	public String getText()
	{
		StringBuilder builder = new StringBuilder();
		
		for (Chunk chunk : par.getChunks())
		{
			builder.append(chunk.getContent());
		}
		
		return builder.toString();
	}
	
	/**
	 * Appends the specified text to this {@link SimpleText}.
	 * @param text - The text to be appended.
	 */
	public void append(String text)
	{
		par.add(new Chunk(text, fontDesc.getFont()));
	}
	
	/**
	 * Appends the specified text to this {@link SimpleText} and adds a new line afterwards.
	 * @param text - The text to be appended.
	 */
	public void appendLine(String text)
	{
		append(text);
		par.add(Chunk.NEWLINE);
	}
	
	/**
	 * Appends a new paragraph containing the specified text.
	 * @param text - The text to be put into a separate paragraph.
	 */
	public void appendParagraph(String text)
	{
		par.add(new Paragraph(par.getLeading(), text, fontDesc.getFont()));
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
		return DocumentContentType.SIMPLE_TEXT;
	}

}
