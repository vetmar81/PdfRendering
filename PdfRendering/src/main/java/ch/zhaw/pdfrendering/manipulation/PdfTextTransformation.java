/**
 * 
 */
package ch.zhaw.pdfrendering.manipulation;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;

import ch.zhaw.pdfrendering.PdfManipulation;
import ch.zhaw.pdfrendering.doc.meta.FontDescription;
import ch.zhaw.pdfrendering.doc.meta.FontStyle;
import ch.zhaw.pdfrendering.util.PdfHelper;
import ch.zhaw.pdfrendering.util.Property;

/**
 * Implements simple PDF text transformations
 * @author Markus Vetsch
 * @since 11.02.2012
 */
public class PdfTextTransformation implements PdfManipulation
{
	private static FontDescription[] descriptions;
	
	private final String outputPath;
	private final Collection<String> tokens;
	
	// Helper enum for the kind of manipulation
	private enum TextManipulation
	{
		SPACING,
		SCALING,
		SKEWING,
		TEXT_RISE,
		TEXT_LOWER
	}
	
	// Static initializer to create the various font colours
	
	static
	{
		FontDescription.registerFontDirectory(Property.getFontDirectory());
		
		FontDescription desc1 = new FontDescription("ARIAL", 14, FontStyle.BOLD, BaseColor.BLUE);
		FontDescription desc2 = new FontDescription("ARIAL", 14, FontStyle.ITALIC, BaseColor.RED);
		FontDescription desc3 = new FontDescription("ARIAL", 14, FontStyle.STRIKETHRU, BaseColor.GREEN);
		FontDescription desc4 = new FontDescription("ARIAL", 14, FontStyle.UNDERLINE, BaseColor.BLACK);
		FontDescription desc5 = new FontDescription("Georgia", 14, FontStyle.BOLD, BaseColor.DARK_GRAY);
		FontDescription desc6 = new FontDescription("Georgia", 14, FontStyle.ITALIC, BaseColor.LIGHT_GRAY);
		FontDescription desc7 = new FontDescription("Georgia", 14, FontStyle.STRIKETHRU, BaseColor.ORANGE);
		FontDescription desc8 = new FontDescription("Georgia", 14, FontStyle.UNDERLINE, BaseColor.MAGENTA);
		
		descriptions = new FontDescription[] {desc1, desc2, desc3, desc4, desc5, desc6, desc7, desc8 };
	}
	
	/**
	 * Creates a new {@link PdfTextTransformation} instance.
	 * @param outputPath - The output path for export of the generated PDF file.
	 * @param tokens - The tokens to apply text transformations to.
	 */
	public PdfTextTransformation(String outputPath, Collection<String> tokens)
	{
		this.outputPath = outputPath;
		this.tokens = tokens;
	}
	
	/* (non-Javadoc)
	 * @see ch.zhaw.pdfrendering.PdfManipulation#run()
	 */
	public void run()
	{	
		Document doc = null;
		
		try
		{
			
			doc = new Document(PageSize.A4);
			PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(outputPath));
			writer.setViewerPreferences(PdfWriter.PageLayoutSinglePage);
			doc.open();
			
			// Iterate 30 times over the input tokens
			
			for (int i = 0; i < 30; i++)
			{
				Phrase phrase = new Phrase(20);
				
				for (String token : tokens)
				{
					// Select random font, create a new chunk and apply transformations
					
					FontDescription desc = getRandomDescription();
					Chunk chunk = new Chunk(token, desc.getFont());
					applyManipulations(chunk);
					
					// Add the transformed chunk back into the phrase
					
					phrase.add(chunk);
				}
				
				doc.add(phrase);
			}
		}
		catch (Exception ex)
		{
			throw new RuntimeException("Unexpected error occurred while performing text operation for PDF output! ", ex);
		}
		finally
		{
			if (doc != null && doc.isOpen())
			{
				PdfHelper.addAuthorInformation(doc);
				doc.close();
			}
		}
	}
	
	/**
	 * Applies random text transformations to the specified {@link Chunk}.
	 * @param chunk - The {@link Chunk} to apply text transformations to.
	 */
	private void applyManipulations(Chunk chunk)
	{
		// Create a random set of manipulation to be applied
		
		for (TextManipulation manipulation : createRandomManipulations())
		{
			switch (manipulation)
			{
				// Create random values for the transformation
				
				case SCALING:
					chunk.setHorizontalScaling((float)Math.random());
					break;
				case SPACING:
					chunk.setCharacterSpacing((float)Math.random());
					break;
				case SKEWING:
					boolean positiveAlpha = new Random().nextBoolean();
					boolean positiveBeta = new Random().nextBoolean();
					float alpha = positiveAlpha ? (float)(Math.random() * 360) : (float)(Math.random() * 360) * -1;
					float beta = positiveBeta ? (float)(Math.random() * 360) : (float)(Math.random() * 360) * -1;
					chunk.setSkew(alpha, beta);
					break;
				case TEXT_LOWER:
					float lower = -1 * (float)Math.random() * 10;
					chunk.setTextRise(lower);
					break;
				case TEXT_RISE:
					float rise = (float)Math.random() * 10;
					chunk.setTextRise(rise);
					break;
			}
		}
	}

	/**
	 * Gets a random {@link FontDescription} from the ones created in the static initializer
	 * @return The randfom {@link FontDescription}
	 */
	private static FontDescription getRandomDescription()
	{
		Random random = new Random();
		return descriptions[random.nextInt(descriptions.length)];
	}
	
	/**
	 * Creates a random {@link List} of {@link TextManipulation}.
	 * @return The random {@link List} of {@link TextManipulation}.
	 */
	private static List<TextManipulation> createRandomManipulations()
	{
		Random random = new Random();
		List<TextManipulation> manipulations = new ArrayList<TextManipulation>();
		boolean spacing = random.nextBoolean();
		boolean scaling = random.nextBoolean();
		boolean skewing = random.nextBoolean();
		boolean textRise = random.nextBoolean();
		boolean textLower = random.nextBoolean();
		
		if (spacing)
		{
			manipulations.add(TextManipulation.SPACING);
		}
		if (scaling)
		{
			manipulations.add(TextManipulation.SCALING);
		}
		if (skewing)
		{
			manipulations.add(TextManipulation.SKEWING);
		}
		if (textRise)
		{
			manipulations.add(TextManipulation.TEXT_RISE);
		}
		if (textLower)
		{
			manipulations.add(TextManipulation.TEXT_LOWER);
		}
		
		return manipulations;
	}
}
