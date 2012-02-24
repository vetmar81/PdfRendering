/**
 * 
 */
package ch.zhaw.pdfrendering.doc.meta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;

import com.itextpdf.text.BadElementException;
import ch.zhaw.pdfrendering.doc.DocumentContent;
import ch.zhaw.pdfrendering.doc.Heading;
import ch.zhaw.pdfrendering.doc.ListContent;
import ch.zhaw.pdfrendering.doc.PdfImage;
import ch.zhaw.pdfrendering.doc.SimpleText;
import ch.zhaw.pdfrendering.enums.HeadingLevel;

/**
 * Imports a document definition from an XML file.
 * @author Markus Vetsch
 * @since 22.02.2012
 */
public class XmlDocumentDefintion
{
	private String headerText;
	private String footerText;
	
	private List<DocumentContent> contents;
	
	/**
	 * Creates a new XmlDocumentDefiniton by loading the file into memory.
	 * @param xmlFile - The file to be loaded.
	 * @throws IOException
	 * @throws JDOMException
	 */
	public XmlDocumentDefintion(File xmlFile) throws IOException, JDOMException, BadElementException
	{
		contents = new ArrayList<DocumentContent>();
		
		load(xmlFile);
	}
	
	/**
	 * Returns the stored collection of {@link DocumentContent} items.
	 * @return The set of {@link DocumentContent}.
	 */
	public Collection<DocumentContent> getContents()
	{
		return contents;
	}
	
	/**
	 * Returns the text to be displayed in the header section.
	 * @return The text of the header section.
	 */
	public String getHeaderText()
	{
		return headerText;
	}
	
	/**
	 * Returns the text to be displayed in the footer section.
	 * @return The text of the footer section.
	 */
	public String getFooterText()
	{
		return footerText;
	}
	
	/**
	 * Loads the file contents into memory.
	 * @param xmlFile - The file to be loaded.
	 * @throws IOException
	 * @throws JDOMException
	 */
	private void load(File xmlFile) throws IOException, JDOMException, BadElementException
	{
		SAXBuilder builder = new SAXBuilder(true);
		org.jdom.Document document = builder.build(xmlFile);
		org.jdom.Element root = document.getRootElement();
		
		org.jdom.Element headerElement = (org.jdom.Element) XPath.selectSingleNode(root, "HeaderText");
		org.jdom.Element footerElement = (org.jdom.Element) XPath.selectSingleNode(root, "FooterText");
		
		headerText = headerElement.getTextTrim();
		footerText = footerElement.getTextTrim();
		
		org.jdom.Element textContentsElement = (org.jdom.Element) XPath.selectSingleNode(root, "Contents");
		
		List<?> textContentElements = textContentsElement.getChildren();
		
		for (Object textContent : textContentElements)
		{
			org.jdom.Element contentElement = (org.jdom.Element) textContent;
			if (!contentElement.getName().equals("ListItem"))
			{
				DocumentContent content = convertXmlTextContent(contentElement);
				contents.add(content);
			}
		}
	}
	
	/**
	 * Converts the specified XML {@link org.jdom.Element} into a {@link DocumentContent}.
	 * @param xmlElement - The XML element.
	 * @return The corresponding {@link DocumentContent}.
	 */
	private DocumentContent convertXmlTextContent(org.jdom.Element xmlElement) throws IOException, BadElementException
	{
		String elementName = xmlElement.getName();
		DocumentContent content = null;
		
		if (elementName.equals("Heading"))
		{
			int depth = Integer.parseInt(xmlElement.getAttribute("depth").getValue());
			String font = xmlElement.getAttribute("fontname").getValue();
			String text = xmlElement.getTextTrim();
			
			HeadingLevel level = HeadingLevel.fromDepth(depth);
			
			content = Heading.create(level, font, text);
		}
		else if (elementName.equals("SimpleText"))
		{
			String font = xmlElement.getAttribute("fontname").getValue();
			int size = Integer.parseInt(xmlElement.getAttribute("size").getValue());
			String text = xmlElement.getTextTrim();
			
			SimpleText st = new SimpleText(font, size);
			st.appendLine(text);
			
			content = st;
		}
		else if (elementName.equals("List"))
		{
			String font = xmlElement.getAttribute("fontname").getValue();
			int size = Integer.parseInt(xmlElement.getAttribute("size").getValue());
			
			ListContent list = new ListContent(font, size);
			
			for (Object listItem : xmlElement.getChildren("ListItem"))
			{
				org.jdom.Element listItemElement = (org.jdom.Element) listItem;
				list.addItem(listItemElement.getTextTrim());
			}
			
			content = list;
		}
		else if (elementName.equals("Image"))
		{
			String imagePath = xmlElement.getTextTrim();
			content = new PdfImage(imagePath);
		}
		
		return content;
	}
}
