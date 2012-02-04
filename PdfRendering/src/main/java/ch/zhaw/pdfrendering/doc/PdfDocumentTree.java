/**
 * 
 */
package ch.zhaw.pdfrendering.doc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import ch.zhaw.pdfrendering.enums.DocumentContentType;
import ch.zhaw.pdfrendering.util.DocumentContentComparator;
import ch.zhaw.pdfrendering.util.DocumentContentTypeComparator;

/**
 * Concrete implementation for any kind of PDF document tree.
 * @author Markus Vetsch
 * @since 20.01.2012
 */
public class PdfDocumentTree implements DocumentTree
{
	private Map<DocumentContentType, SortedSet<DocumentContent>> documentMap;
	
	/**
	 * Creates a new empty document tree
	 */
	public PdfDocumentTree()
	{
		documentMap = new TreeMap<DocumentContentType, SortedSet<DocumentContent>>(new DocumentContentTypeComparator());
	}
	
	/**
	 * Provides all the content within the tree for iteration.
	 * @return All the content in the document tree for iteration.
	 */
	public Iterable<DocumentContent> getAllContent()
	{
		List<DocumentContent> contents = new ArrayList<DocumentContent>();
		
		for (DocumentContentType contentType : documentMap.keySet())
		{
			contents.addAll(documentMap.get(contentType));
		}
		
		return contents;
	}
	
	/* (non-Javadoc)
	 * @see ch.zhaw.pdfrendering.doc.DocumentTree#addContent(ch.zhaw.pdfrendering.doc.DocumentContent)
	 */
	public void addContent(DocumentContent content)
	{
		if (documentMap.containsKey(content.getType()))
		{
			documentMap.get(content.getType()).add(content);
		}
		else
		{
			SortedSet<DocumentContent> contentSet = new TreeSet<DocumentContent>(new DocumentContentComparator());
			documentMap.put(content.getType(), contentSet);
		}
	}
	
	/**
	 * Removes the specified content, if it's part of the document tree. Otherwise no changes to be applied.
	 * @param content The {@link DocumentContent} to be removed.
	 */
	public void removeContent(DocumentContent content)
	{
		DocumentContentType type = content.getType();
		
		if (documentMap.containsKey(type))
		{			
			if (documentMap.get(type).contains(content))
			{
				documentMap.get(type).remove(content);
			}
		}
	}
	
	/**
	 * Removes all the contents and the {@link DocumentContentType} itself from the document tree.
	 * @param contentType The {@link DocumentContentType}, which all {@link DocumentContent} and the entry itself shall be removed for.
	 */
	public void removeAllContents(DocumentContentType contentType)
	{
		if (documentMap.containsKey(contentType))
		{
			documentMap.get(contentType).clear();
			documentMap.remove(contentType);
		}
	}
}
