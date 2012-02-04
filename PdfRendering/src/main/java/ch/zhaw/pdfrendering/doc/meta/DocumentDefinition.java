/**
 * 
 */
package ch.zhaw.pdfrendering.doc.meta;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.itextpdf.text.RectangleReadOnly;

import ch.zhaw.pdfrendering.doc.meta.Margin;
import ch.zhaw.pdfrendering.enums.DocumentEdge;
import ch.zhaw.pdfrendering.enums.Unit;
import ch.zhaw.pdfrendering.util.UnitConversion;

/**
 * Defines document definitions, such as format, margins etc.
 * @author Markus Vetsch
 * @since 18.12.2011
 */
public class DocumentDefinition
{
	private static float DEFAULT_MARGIN = 10;			// Default margin 10 mm
	
	private final float width, height;	
	private Map<DocumentEdge, Margin> margins;
	
	com.itextpdf.text.Rectangle rect;
	
	/**
	 * Creates a {@link DocumentDefinition} instance according to specified well-known format (such as A0, A1, A2 ...)
	 * @param format - The applicable page format.
	 */
	public DocumentDefinition(String format)
	{
		this.rect = com.itextpdf.text.PageSize.getRectangle(format.toUpperCase());
		
		//TODO: Why does this not work?
		
		if (format.toUpperCase().equals("A4_LANDSCAPE"))
		{
			this.height = UnitConversion.asMillimeters(Unit.POINT, rect.getWidth());
			this.width = UnitConversion.asMillimeters(Unit.POINT, rect.getHeight());
		}
		else
		{
			this.width = UnitConversion.asMillimeters(Unit.POINT, rect.getWidth());
			this.height = UnitConversion.asMillimeters(Unit.POINT, rect.getHeight());
		}		
		
		// add default margins
		
		addMargins();
	}
	
	public DocumentDefinition(Unit unit, float width, float height)
	{		
		this.width = UnitConversion.asMillimeters(unit, width);
		this.height = UnitConversion.asMillimeters(unit, height);
		
		// add default margins
		
		addMargins();
	}
	
	public RectangleReadOnly getRect()
	{
		return new RectangleReadOnly(this.rect);
	}
	
	/**
	 * Gets the width of current {@link DocumentDefinition} in millimeters.
	 * @return the width in millimeters.
	 */
	public float getWidth()
	{
		return width;
	}

	/**
	 * Gets the height of current {@link DocumentDefinition} in millimeters.
	 * @return the height in millimeters.
	 */
	public float getHeight()
	{
		return height;
	}

	/**
	 * Evaluates, if current {@link DocumentDefinition} is in landscape / portrait page format.
	 * @return <b>true</b>, if current document format represents landscape format; otherwise <b>false</b> is returned.
	 */
	public boolean isLandscape()
	{
		return width > height;
	}
	
	public void setAllMargins(float value)
	{
		for (Entry<DocumentEdge, Margin> margin : margins.entrySet())
		{
			setMargin(margin.getKey(), value);
		}
	}
	
	/**
	 * Sets the margin of specified {@link Edge} to the desired value. If value is invalid, nothing is changed.
	 * @param edge - The affected edge of a document to set the margin for.
	 * @param value - The value to be applicable for the margin.
	 */
	public void setMargin(DocumentEdge edge, float value)
	{
		try
		{
			margins.get(edge).setValue(value);
		}
		catch (IllegalArgumentException ex)
		{
			return;
		}		
	}
	
	/**
	 * Gets the current margin value applicable for specified {@link Edge}.
	 * @param edge - The {@link Edge} to determine the margin value for.
	 * @return The margin value at specified {@link Edge}.
	 */
	public float getMargin(DocumentEdge edge)
	{
		return margins.get(edge).getValue();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return String.format("DocumentDefinition [width=%s, height=%s, margins=%s, landscape=%s]",
								width, height, margins, isLandscape());
	}

	/**
	 * Adds defaulted page margins
	 */
	private void addMargins()
	{
		margins = new HashMap<DocumentEdge, Margin>();
		
		margins.put(DocumentEdge.TOP, new Margin(DocumentEdge.TOP, Unit.MILLIMETER, DEFAULT_MARGIN));
		margins.put(DocumentEdge.RIGHT, new Margin(DocumentEdge.RIGHT, Unit.MILLIMETER, DEFAULT_MARGIN));
		margins.put(DocumentEdge.BOTTOM, new Margin(DocumentEdge.BOTTOM, Unit.MILLIMETER, DEFAULT_MARGIN));
		margins.put(DocumentEdge.LEFT, new Margin(DocumentEdge.LEFT, Unit.MILLIMETER, DEFAULT_MARGIN));
	}
}
