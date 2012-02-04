/**
 * 
 */
package ch.zhaw.pdfrendering.util;

import ch.zhaw.pdfrendering.doc.DocumentContent;
import ch.zhaw.pdfrendering.doc.Heading;
import ch.zhaw.pdfrendering.enums.HeadingLevel;

/**
 * Creates Heading numbers from document structure
 * @author Markus Vetsch
 * @since 03.02.2012
 *
 */
public class HeadingNumberCreator
{
	public static void createHeadingNumber(Heading heading, Iterable<DocumentContent> contents)
	{
		int headingFirst = 0;
		int headingSecond = 0;
		int headingThird = 0;
		int headingFourth = 0;
		
		for (DocumentContent content : contents)
		{
			if (content instanceof Heading)
			{
				Heading h = (Heading) content;
				switch (h.getLevel())
				{
					case CHAPTER:
						headingFirst++;
						break;
					case SECTION_FIRST:
						headingSecond++;
						break;
					case SECTION_SECOND:
						headingThird++;
						break;
					case SECTION_THIRD:
						headingFourth++;
						break;
				}
				
				if (h.getLevel() == heading.getLevel() && h.getText() == heading.getText())
				{
					String number = getNumberByLevel(h.getLevel(), headingFirst, headingSecond, headingThird, headingFourth);
					heading.updateTitle(number);
					return;
				}
			}
		}
	}
	
	private static String getNumberByLevel(HeadingLevel level, int countFirst, int countSecond, int countThird, int countFourth)
	{
		switch (level)
		{
			case CHAPTER:
				return Integer.toString(countFirst);
			case SECTION_FIRST:
				return String.format("%1$s.%2$s", Integer.toString(countFirst), Integer.toString(countSecond));
			case SECTION_SECOND:
				return String.format("%1$s.%2$s.%3$s", Integer.toString(countFirst),
									Integer.toString(countSecond), Integer.toString(countThird));
			case SECTION_THIRD:
				return String.format("%1$s.%2$s.%3$s.%4$s", Integer.toString(countFirst),
						Integer.toString(countSecond), Integer.toString(countThird), Integer.toString(countFourth));
		}
		
		return null;
	}
}
