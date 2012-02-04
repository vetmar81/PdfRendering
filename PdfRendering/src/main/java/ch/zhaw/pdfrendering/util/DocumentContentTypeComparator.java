package ch.zhaw.pdfrendering.util;

import ch.zhaw.pdfrendering.enums.DocumentContentType;

import java.util.Comparator;

/**
 * Comparator implementation for any type of {@link DocumentContentType}.
 * @author Markus Vetsch
 * @since 20.01.2012
 *
 * @param <DocumentContent> Any kind of {@link DocumentContentType}.
 */
public class DocumentContentTypeComparator implements Comparator<DocumentContentType>
{
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(DocumentContentType either, DocumentContentType other)
	{
		return either.getId() - other.getId();
	}

}
