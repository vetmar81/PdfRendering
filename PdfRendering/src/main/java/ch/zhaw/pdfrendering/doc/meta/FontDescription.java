/**
 * 
 */
package ch.zhaw.pdfrendering.doc.meta;

/**
 * Helper class providing the dedicated description of a font used for PDF rendering.
 * @author Markus Vetsch
 * @version 1.0, 11.12.2011
 */
public class FontDescription
{
	private static String fontDirectory;
	
	private final com.itextpdf.text.Font font;
	
	/**
	 * @param fontName - The name of the font to be used; Customized fonts to be used,
	 *  					if {@link FontDescription#registerFontDirectory(String)} was called in advance 
	 *  					to define a font directory with customized set of fonts
	 * @param size - The size of the font to be rendered as integer value.
	 * @param style - The {@link FontStyle} to be used for rendering.
	 * @param color - The iText color to be used for rendering.
	 */
	public FontDescription(String fontName, int size, FontStyle style, com.itextpdf.text.BaseColor color)
	{
		this(com.itextpdf.text.FontFactory.getFont(fontName), (float)size, style, color);
	}
	
	/**
	 * @param font - The corresponding iText font instance.
	 * @param size - The size of the font to be rendered as int value.
	 * @param style - The {@link FontStyle} to be used for rendering.
	 */
	public FontDescription(com.itextpdf.text.Font font, float size, FontStyle style, com.itextpdf.text.BaseColor color)
	{
		font.setSize(size);
		font.setStyle(style.iTextFontStyle());
		font.setColor(color);
		
		this.font = font;
	}
	
	/**
	 * @return the dedicated directory containing customized fonts for PDF rendering.
	 */
	public static String getFontDirectory()
	{
		return fontDirectory;
	}
	
	/**
	 * Call this method to register any default-OS directory containing the fonts for PDF creation.
	 * If no OS-directory found, default fonts will be used instead.
	 */
	public static void registerFontDirectory()
	{
		registerFontDirectory(null);
	}
	
	/**
	 * Call this method to register any customized directory containing the fonts for PDF creation.
	 * Otherwise, default fonts will be used instead.
	 * @param fontDirectory - The directory containing customized fonts.
	 */
	public static void registerFontDirectory(String fontDirectory)
	{
		if (fontDirectory == null)
		{
			com.itextpdf.text.FontFactory.registerDirectories();
		}
		
		com.itextpdf.text.FontFactory.registerDirectory(fontDirectory);
		FontDescription.fontDirectory = fontDirectory;
	}
	
	/**
	 * Returns the appropriate font instance from iText library.
	 * @return the font
	 */
	public com.itextpdf.text.Font getFont()
	{
		return font;
	}
}
