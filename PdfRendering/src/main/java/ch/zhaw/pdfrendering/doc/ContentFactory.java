/**
 * 
 */
package ch.zhaw.pdfrendering.doc;

import java.io.IOException;

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
	private ContentFactory()
	{		
	}
	
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
		else
		{
			throw new RuntimeException("Invalid DocumentContentType!");
		}
	}
}
