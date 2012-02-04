/**
 * 
 */
package ch.zhaw.pdfrendering.doc;

/**
 * Basic interface for any kind of document tree.
 * @author Markus Vetsch
 * @since 20.01.2012
 */
public interface DocumentTree
{
	/**
	 * Adds any kind of {@link DocumentTree} to the {@link DocumentTree}.
	 * @param content The specific {@link DocumentContent} to be added.
	 */
	void addContent(DocumentContent content);
}
