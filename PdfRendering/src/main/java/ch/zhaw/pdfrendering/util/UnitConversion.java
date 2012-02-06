package ch.zhaw.pdfrendering.util;

import ch.zhaw.pdfrendering.enums.Unit;

/**
 * Helper class for conversion of all values to millimeters.
 * @author Markus Vetsch
 * @since 18.12.2011
 */
public class UnitConversion
{
	private UnitConversion()
	{		
	}
	
	public static float asPoints(Unit unit, float value)
	{
		switch(unit)
		{
			case POINT:
				return value;
			case INCH:
				return com.itextpdf.text.Utilities.inchesToPoints(value);
			case MILLIMETER:
				return com.itextpdf.text.Utilities.millimetersToPoints(value);
			default:
				return value;
		}
	}
	
	/**
	 * Converts the specified margin value internally always to millimeters
	 * @param value - The margin value
	 * @return The corrected margin value in millimeters.
	 */
	public static float asMillimeters(Unit unit, float value)
	{		
		switch(unit)
		{
			case POINT:
				return com.itextpdf.text.Utilities.pointsToMillimeters(value);
			case INCH:
				return com.itextpdf.text.Utilities.inchesToMillimeters(value);
				
			// In any other case mm value assumed
				
			case MILLIMETER:
			default:
				return value;
		}
	}
}
