/**
 * 
 */
package ch.zhaw.pdfrendering.enums;

import ch.zhaw.pdfrendering.doc.meta.Margin;

/**
 * Represents the unit of the document margin.
 * @author Markus Vetsch
 * @since 18.12.2011
 */
public enum Unit
{		
	/**
	 * Value in points (dpi).
	 */
	POINT("pt"),
	/**
	 * Value in inches (1 inch = 25.4 mm)
	 */
	INCH("in"),
	/**
	 * Value in millimeters.
	 */
	MILLIMETER("mm"),
	/**
	 * Value in unspecified unit..
	 */
	UNSPECIFIED("nil");
	
	private final String shortName;
	
	/**
	 * Creates a new instance of this enumeration with specified short name.
	 * @param shortName - The short name of the {@link Unit}.
	 */
	private Unit(String shortName)
	{
		this.shortName = shortName;
	}
	
	/**
	 * Gets the corresponding {@link Unit} for current {@link Margin}.
	 * @param unit - The unit as {@link String}.
	 * @return The corresponding {@link Unit}.
	 * @throws IllegalArgumentException if invalid unit {@link String} defined.
	 */
	public static Unit getUnit(String unit)
	{
		String corrUnit = unit.toLowerCase();
		
		if (corrUnit.equals("pt") || corrUnit.equals("point"))
		{
			return POINT;
		}
		else if (corrUnit.equals("in") || corrUnit.equals("inch"))
		{
			return INCH;
		}
		else if (corrUnit.equals("mm") || corrUnit.equals("millimeter"))
		{
			return MILLIMETER;
		}
		else
		{
			throw new IllegalArgumentException("Invalid unit defined!");
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString()
	{
		return shortName;
	}
}
