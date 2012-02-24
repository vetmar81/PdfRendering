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
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;

/**
 * Allows definition of a list content for a document.
 * @author Markus Vetsch
 * @since 22.02.2012
 */
public class ListContent implements DocumentContent
{
	private static float LINE_SPACING = 25;
	private static float INDENT_LEFT = 30;
	private static float INDENT_SYMBOL = 30;
	
	private FontDescription description;
	private List list;
	
	/**
	 * Creates a new {@link ListContent} with specified font name and size.
	 * @param fontName - The name of the font to be applied.
	 * @param fontSize - The size of the font to be applied.
	 */
	public ListContent(String fontName, int fontSize)
	{
		this(new FontDescription(fontName, fontSize, FontStyle.NORMAL, BaseColor.BLACK));
	}
	
	public ListContent(FontDescription description)
	{
		this.description = description;
		list = new List(false, true, INDENT_SYMBOL);
		list.setIndentationLeft(INDENT_LEFT);
		list.setLowercase(true);
		
		// Reset symbol by applying a specified font
		
		Chunk symbol = list.getSymbol();
		FontDescription symbolDesc = new FontDescription(description.getFontName(), description.getFontSize(), FontStyle.BOLD, BaseColor.BLACK);
		symbol.setFont(symbolDesc.getFont());
		list.setListSymbol(symbol);
	}
	
	/**
	 * Adds the specified text to the {@link ListContent} instance..
	 * @param text - The text to be added.
	 */
	public void addItem(String text)
	{
		ListItem item = new ListItem(new Chunk(text, description.getFont()));
		item.setLeading(LINE_SPACING);
		list.add(item);
	}
	
	/* (non-Javadoc)
	 * @see ch.zhaw.pdfrendering.doc.DocumentContent#getText()
	 */
	public String getText()
	{
		StringBuilder builder = new StringBuilder();
		
		for (Element element : list.getItems())
		{
			ListItem item = (ListItem) element;
			builder.append(item.getContent() + System.getProperty("line.separator"));
		}
		
		return builder.toString();
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.pdfrendering.doc.DocumentContent#asElement()
	 */
	public Element asElement()
	{
		return list;
	}

	/* (non-Javadoc)
	 * @see ch.zhaw.pdfrendering.doc.DocumentContent#getType()
	 */
	public DocumentContentType getType()
	{
		return DocumentContentType.LIST;
	}

}
