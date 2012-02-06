/**
 * 
 */
package ch.zhaw.pdfrendering.drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
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
	
	private void drawShape(ShapeDrawing drawing, Graphics2D g)
	{
		switch (drawing.shape)
		{
			case CIRCLE:
				drawCircle(drawing, g);
			case TRIANGLE:
				drawTriangle(drawing, g);
			case SQUARE:
				drawSquare(drawing, g);
		}
	}
	
	private void drawCircle(ShapeDrawing drawing, Graphics2D g)
	{		 
		float radius = UnitConversion.asPoints(Unit.MILLIMETER, drawing.value);
		Ellipse2D.Float shape = new Ellipse2D.Float(50, 50, radius, radius);		
		g.setColor(drawing.color);
		g.fill(shape);
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(1.0f));
		g.drawOval((int)shape.x, (int)shape.y, (int)shape.width, (int)shape.height);
		g.dispose();
	}
	
	private void drawTriangle(ShapeDrawing drawing, Graphics2D g)
	{
		
	}
	
	private void drawSquare(ShapeDrawing drawing, Graphics2D g)
	{
		
	}
	
	public enum Shape
	{
		CIRCLE,
		TRIANGLE,
		SQUARE;
	}
	
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
