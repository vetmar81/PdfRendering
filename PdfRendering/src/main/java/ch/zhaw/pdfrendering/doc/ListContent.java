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
	private FontDescription description;
	private List list;
	
	public ListContent(String fontName, int fontSize)
	{
		description = new FontDescription(fontName, fontSize, FontStyle.NORMAL, BaseColor.BLACK);
		list = new List(false, true, 30);
		list.setIndentationLeft(25);
		Chunk symbol = list.getSymbol();
		FontDescription symbolDesc = new FontDescription(fontName, fontSize, FontStyle.BOLD, BaseColor.BLACK);
		symbol.setFont(symbolDesc.getFont());
		list.setListSymbol(symbol);
	}
	
	public void addItem(String text)
	{
		ListItem item = new ListItem(new Chunk(text, description.getFont()));
		item.setLeading(25);
		list.add(item);
	}
	
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
