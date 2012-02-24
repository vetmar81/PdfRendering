/**
 * 
 */
package ch.zhaw.pdfrendering.doc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import com.itextpdf.text.BadElementException;

import ch.zhaw.pdfrendering.enums.DocumentContentType;
import ch.zhaw.pdfrendering.enums.HeadingLevel;
import ch.zhaw.pdfrendering.gui.DocumentListItem;

/**
 * Simple factory class for creation of {@link DocumentContent}.
 * @author Markus Vetsch
 * @since 27.01.2012
 */
public class ContentFactory
{
	/**
	 * Use static method {@link ContentFactory#createContent(DocumentContentType, DocumentListItem)} to get an instance
	 */
	private ContentFactory()
	{		
	}
	
	/**
	 * Creates the correct {@link DocumentContent} for
	 *  specified {@link DocumentContentType} and {@link DocumentListItem} from GUI model.
	 * @param type - The {@link DocumentContentType}.
	 * @param item - The {@link DocumentListItem} providing data from the GUI model.
	 * @return The appropriate {@link DocumentContent}
	 * @throws IOException
	 * @throws BadElementException
	 */
	public static DocumentContent createContent(DocumentContentType type, DocumentListItem item) throws IOException, BadElementException
	{
		if (type == DocumentContentType.HEADING)
		{
			HeadingLevel level = HeadingLevel.fromDepth(item.getHeadingDepth());
			return Heading.create(level, item.getFontDescription().getFont().getFamilyname(), item.getText());
		}
		else if (type == DocumentContentType.SIMPLE_TEXT)
		{
			SimpleText text = new SimpleText(item.getFontDescription());
			text.appendParagraph(item.getText());
			
			return text;
		}
		else if (type == DocumentContentType.IMAGE)
		{
			return new PdfImage(item.getImagePath());
		}
		else if (type == DocumentContentType.LIST)
		{
			return createListContent(item);
		}
		else
		{
			throw new RuntimeException("Invalid DocumentContentType!");
		}	
	}
	
	/**
	 * Creates a {@link ListContent} from specified {@link DocumentListItem}.
	 * @param item - The {@link DocumentListItem} providing the relevant information from GUI model.
	 * @return The created {@link ListContent}.
	 * @throws IOException
	 */
	private static ListContent createListContent(DocumentListItem item) throws IOException
	{
		ListContent content = new ListContent(item.getFontDescription());
		BufferedReader reader = new BufferedReader(new StringReader(item.getText()));
		String currentLine;
		
		while ((currentLine = reader.readLine()) != null)
		{
			content.addItem(currentLine);
		}
		
		return content;
	}
}
