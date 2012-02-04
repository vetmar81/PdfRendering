/**
 * 
 */
package ch.zhaw.pdfrendering.util;

import java.awt.Color;

import com.itextpdf.text.BaseColor;

/**
 * Helper class for color conversion to itext font color.
 * @author Markus Vetsch
 * @since 26.01.2012
 */
public class FontColor
{
	private FontColor()
	{		
	}
	
	/**
	 * Turns an {@link Color} into a {@link BaseColor} for PDF rendering.
	 * @param color - The Java AWT {@link Color}.
	 * @return The corresponding itext {@link BaseColor}.
	 */
	public static com.itextpdf.text.BaseColor fromAwtColor(Color color)
	{
		return new com.itextpdf.text.BaseColor(color);
	}
}
