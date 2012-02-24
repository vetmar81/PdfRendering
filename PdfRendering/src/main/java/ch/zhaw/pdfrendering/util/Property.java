/**
 * 
 */
package ch.zhaw.pdfrendering.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Helper class for access to configuration values in .properties file.
 * @author Markus Vetsch
 * @since 24.02.2012
 */
public class Property
{
	private static Properties properties = new Properties();
	private static boolean isLoaded = false;
	
	private Property()
	{		
	}
	
	// Static initializer
	static
	{
		if (!isLoaded)
		{
			String path = System.getProperty("user.dir") + File.separator + "PdfRendering.properties";
			try
			{				
				properties.load(new FileInputStream(path));
				isLoaded = true;
			}
			catch (IOException ex)
			{
				throw new RuntimeException("Unable to locate and load PdfRendering.properties file!");
			}
		}
	}
	
	/**
	 * Gets the configured font directory.
	 * @return The font directory.
	 */
	public static String getFontDirectory()
	{
		return properties.getProperty("FONT_DIR");
	}
	
	/**
	 * Gets the configured installation path of Adobe Reader.
	 * @return The installation path of Adobe Reader.
	 */
	public static String getAdobeReaderPath()
	{
		return properties.getProperty("ADOBE_READER_PATH");
	}
	
	/**
	 * Gets the file path to the title image file.
	 * @return The file path to the title image file.
	 */
	public static String getTitleImage()
	{
		return properties.getProperty("TITLE_IMAGE_PATH");
	}
}
