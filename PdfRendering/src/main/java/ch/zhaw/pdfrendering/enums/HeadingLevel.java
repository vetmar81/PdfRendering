/**
 * 
 */
package ch.zhaw.pdfrendering.enums;

/**
 * Represents the level of the heading in a document structure
 * @author Markus
 * 21.01.2012
 */
public enum HeadingLevel
{
	/**
	 * Chapter heading
	 */
	CHAPTER(0),
	/**
	 * First level section heading.
	 */
	SECTION_FIRST(1),
	/**
	 * Second level section heading.
	 */
	SECTION_SECOND(2),
	/**
	 * Third level section heading.
	 */
	SECTION_THIRD(3),
	/**
	 * Default - no heading.
	 */
	DEFAULT(-1);
	
	private final int depth;
	
	private HeadingLevel(int depth)
	{
		this.depth = depth;
	}
	
	public static HeadingLevel fromDepth(int depth)
	{
		switch (depth)
		{
			case 0:
				return CHAPTER;
			case 1:
				return SECTION_FIRST;
			case 2:
				return SECTION_SECOND;
			case 3:
				return SECTION_THIRD;
			default:
				return DEFAULT;
		}
	}
	
	public int getDepth()
	{
		return depth + 1;
	}
}