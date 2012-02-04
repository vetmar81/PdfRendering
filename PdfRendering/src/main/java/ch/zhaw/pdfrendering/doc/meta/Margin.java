package ch.zhaw.pdfrendering.doc.meta;

import ch.zhaw.pdfrendering.enums.DocumentEdge;
import ch.zhaw.pdfrendering.enums.Unit;
import ch.zhaw.pdfrendering.util.UnitConversion;

/**
 * Represents a margin within a {@link DocumentDefinition}
 * @author Markus Vetsch
 * @since 18.12.2011
 */
public class Margin
{
	private float value;		// internally always converted to millimeters
	
	private final DocumentEdge edge;
	private final Unit unit;
	
	/**
	 * Defines a new {@link Margin} instance with specified {@link DocumentEdge} and {@link Unit}
	 * @param edge - The {@link DocumentEdge} to apply the margin value to.
	 * @param unit - The {@link Unit} of the margin value.
	 */
	public Margin(DocumentEdge edge, Unit unit)
	{
		this(edge, unit, 0.0f);
	}

	/**
	 * Defines a new {@link Margin} instance with specified {@link DocumentEdge} and {@link Unit}
	 * @param edge - The {@link DocumentEdge} to apply the margin value to.
	 * @param unit - The {@link Unit} of the margin value.
	 * @param value - The value of the margin.
	 */
	public Margin(DocumentEdge edge, Unit unit, float value)
	{
		this.edge = edge;
		this.unit = unit;
		this.value = UnitConversion.asMillimeters(unit, value);
	}
	
	/**
	 * The effective value of the {@link Margin}.
	 * @return the value
	 */
	public float getValue()
	{
		return value;
	}
	
	/**
	 * Sets the {@link Margin} value as specified.
	 * @param value - The value of the {@link Margin} to apply.
	 * @throws IllegalArgumentException if <code>value</code> is negative.
	 */
	public void setValue(float value)
	{
		if (value > 0.0)
		{
			this.value = UnitConversion.asMillimeters(this.unit, value);
		}
		else
		{
			throw new IllegalArgumentException("Margin value can't be negative!");
		}		
	}

	/**
	 * Gets the {@link Edge} the current {@link Margin} belongs to.
	 * @return The {@link Edge} of the current {@link Margin}.
	 */
	public DocumentEdge getEdge()
	{
		return edge;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		String formattedValue = Float.toString(value).concat(String.format(" %s", unit.toString()));
		return String.format("Margin [value = %1$s, edge = %2$s]", formattedValue, edge.toString());
	}
}
