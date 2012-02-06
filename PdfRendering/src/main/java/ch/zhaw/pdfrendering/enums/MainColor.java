/**
 * 
 */
package ch.zhaw.pdfrendering.enums;

import java.awt.Color;

/**
 * Simple enumeration for the most important colors.
 * @author Markus Vetsch
 * @since 06.02.2012
 *
 */
public enum MainColor
{
	BLACK(Color.BLACK),
	BLUE(Color.BLUE),
	CYAN(Color.CYAN),
	DARK_GRAY(Color.DARK_GRAY),
	GRAY(Color.GRAY),
	GREEN(Color.GREEN),
	LIGHT_GRAY(Color.LIGHT_GRAY),
	MAGENTA(Color.MAGENTA),
	ORANGE(Color.ORANGE),
	PINK(Color.PINK),
	RED(Color.RED),
	WHITE(Color.WHITE),
	YELLOW(Color.YELLOW);
	
	private final Color color;
	
	private MainColor(Color color)
	{
		this.color = color;
	}
	
	public Color getAwtColor()
	{
		return color;
	}
}
