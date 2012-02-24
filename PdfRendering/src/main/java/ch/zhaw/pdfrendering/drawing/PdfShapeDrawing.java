/**
 * 
 */
package ch.zhaw.pdfrendering.drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.FileOutputStream;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import ch.zhaw.pdfrendering.PdfManipulation;
import ch.zhaw.pdfrendering.enums.Unit;
import ch.zhaw.pdfrendering.util.PdfHelper;
import ch.zhaw.pdfrendering.util.UnitConversion;

/**
 * Simple class that allows drawing of some shapes into PDF.
 * @author Markus Vetsch
 * @since 06.02.2012
 */
public class PdfShapeDrawing implements PdfManipulation
{	
	private final String outputFile;
	private final List<ShapeDrawing> shapes;
	
	/**
	 * Creates a new instance of {@link PdfShapeDrawing}.
	 * @param outputFile - The output file to be created.
	 * @param shapes - The set of {@link ShapeDrawing} to be inserted into output document.
	 */
	public PdfShapeDrawing(String outputFile, List<ShapeDrawing> shapes)
	{
		this.outputFile = outputFile;
		this.shapes = shapes;
	}
	
	/* (non-Javadoc)
	 * @see ch.zhaw.pdfrendering.PdfManipulation#run()
	 */
	public void run()
	{
		Document document = null;
		
		try
		{
			document = new Document(PageSize.A4);
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(outputFile));
			writer.setViewerPreferences(PdfWriter.PageLayoutSinglePage);
			document.open();
			
			// Draw shapes
			
			PdfContentByte cb = writer.getDirectContent();
			Graphics2D g = cb.createGraphics(document.right() - document.left(), document.top() - document.bottom());
			for (ShapeDrawing drawing : shapes)
			{
				drawShape(drawing, g);
			}
		}
		catch (Exception ex)
		{
			throw new RuntimeException("Shape drawing to PDF failed!", ex);
		}
		finally
		{
			if (document != null && document.isOpen())
			{
				PdfHelper.addAuthorInformation(document);
				document.close();
			}
		}
	}
	
	/**
	 * Draws the appropriate shape
	 * @param drawing - The {@link ShapeDrawing} providing details about the shape.
	 * @param g - The graphics context.
	 */
	private void drawShape(ShapeDrawing drawing, Graphics2D g)
	{
		switch (drawing.shape)
		{
			case CIRCLE:
				drawCircle(drawing, g);
				break;
			case TRIANGLE:
				drawTriangle(drawing, g);
				break;
			case SQUARE:
				drawSquare(drawing, g);
				break;
		}
	}
	
	/**
	 * Draws a circle from specified {@link ShapeDrawing}.
	 * @param drawing - The {@link ShapeDrawing} providing details about the shape.
	 * @param g - The graphics context
	 */
	private void drawCircle(ShapeDrawing drawing, Graphics2D g)
	{		 
		float radius = UnitConversion.asPoints(Unit.MILLIMETER, drawing.value);
		Ellipse2D.Float shape = new Ellipse2D.Float(200, 25, radius, radius);		
		fillShape(shape, drawing, g);
		g.drawOval((int)shape.x, (int)shape.y, (int)shape.width, (int)shape.height);
		g.dispose();
	}
	
	/**
	 * Draws a regular triangle with edges of equal length
	 * @param drawing - The {@link ShapeDrawing} providing details about the shape.
	 * @param g - The graphics context
	 */
	private void drawTriangle(ShapeDrawing drawing, Graphics2D g)
	{
		float edgeLength = UnitConversion.asPoints(Unit.MILLIMETER, drawing.value);
		
		// Calculation of outer points by pythagoras
		
		Point2D.Float edgeA = new Point2D.Float(150, 750);
		Point2D.Float edgeB = new Point2D.Float(150 + edgeLength, 750);
		Point2D.Float edgeC = new Point2D.Float((float)(150 + (0.5*edgeLength)), (float)(750 - (0.5*edgeLength*Math.sqrt(3))));
		
		// Create the polygon
		
		Polygon polygon = new Polygon();
		polygon.addPoint((int)edgeA.x, (int)edgeA.y);
		polygon.addPoint((int)edgeB.x, (int)edgeB.y);
		polygon.addPoint((int)edgeC.x, (int)edgeC.y);
		fillShape(polygon, drawing, g);
		g.drawPolygon(polygon);
		g.dispose();
	}
	
	/**
	 * Draws a square of specified {@link ShapeDrawing}.
	 * @param drawing - The {@link ShapeDrawing} providing details about the shape.
	 * @param g - The graphics context
	 */
	private void drawSquare(ShapeDrawing drawing, Graphics2D g)
	{
		float edgeLength = UnitConversion.asPoints(Unit.MILLIMETER, drawing.value);
		Rectangle2D.Float shape = new Rectangle2D.Float(150, 250, edgeLength, edgeLength);
		fillShape(shape, drawing, g);
		g.drawRect((int)shape.x, (int)shape.y, (int)shape.width, (int)shape.height);
		g.dispose();
	}
	
	/**
	 * Applys the filling to the specified {@link java.awt.Shape}.
	 * @param shape - The affected {@link java.awt.Shape}.
	 * @param drawing - The {@link ShapeDrawing} providing details about the shape.
	 * @param g - The graphics context
	 */
	private void fillShape(java.awt.Shape shape, ShapeDrawing drawing, Graphics2D g)
	{
		g.setColor(drawing.color);
		g.fill(shape);
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(1.0f));
	}
	
	// Helper enum
	
	public enum Shape
	{
		CIRCLE,
		TRIANGLE,
		SQUARE;
	}
	
	/**
	 * Static inner class for details about shape drawing.
	 */
	public static class ShapeDrawing
	{
		private final Shape shape;
		private final int value;
		private final Color color;
		
		private ShapeDrawing(Shape shape, int value, Color color)
		{
			this.shape = shape;
			this.value = value;
			this.color = color;
		}
		
		public static ShapeDrawing create(Shape shape, int value, Color color)
		{
			return new ShapeDrawing(shape, value, color);
		}
	}

}
