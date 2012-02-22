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
	private FontDescription fontDesc;
	private Paragraph par;
	
	public SimpleText(String fontName, int fontSize)
	{
		this(new FontDescription(fontName, fontSize, FontStyle.NORMAL, BaseColor.BLACK));
	}
	
	public SimpleText(FontDescription fontDesc)
	{
		this(fontDesc, 25.0f);
	}
	
	public SimpleText(FontDescription fontDesc, float lineSpacing)
	{
		this.fontDesc = fontDesc;
		par = new Paragraph(lineSpacing);
	}
	
	public String getText()
	{
		StringBuilder builder = new StringBuilder();
		
		for (Chunk chunk : par.getChunks())
		{
			builder.append(chunk.getContent());
		}
		
		return builder.toString();
	}
	
	public void append(String text)
	{
		par.add(new Chunk(text, fontDesc.getFont()));
	}
	
	public void appendLine(String text)
	{
		append(text);
		par.add(Chunk.NEWLINE);
	}
	
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
