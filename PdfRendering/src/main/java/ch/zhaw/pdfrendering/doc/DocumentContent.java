/**
 * 
 */
package ch.zhaw.pdfrendering.doc;

import com.itextpdf.text.Element;

import ch.zhaw.pdfrendering.enums.DocumentContentType;

/**
 * Basic interface for any kind of document content.
 * @author Markus Vetsch
 * @since 20.01.2012
 */
public interface DocumentContent
{	
	/**Gets the underlying text of the content
	 * @return The underlying text.
	 */
	String getText();
	
	/**
	 * Converts the specific {@link DocumentContent} to an iText {@link Element}, that can be appended to a document.
	 * @return The corresponding iText {@link Element}.
	 */
	com.itextpdf.text.Element asElement();
	
	/**
	 * Returns the underlying document content type information.
	 * @return The document content type information as specified in {@link DocumentContentType}.
	 */
	DocumentContentType getType();
}
