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

import ch.zhaw.pdfrendering.doc.DocumentContent;
import ch.zhaw.pdfrendering.doc.Heading;
import ch.zhaw.pdfrendering.doc.ListContent;
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
	
	public XmlDocumentDefintion(File xmlFile) throws IOException, JDOMException
	{
		contents = new ArrayList<DocumentContent>();
		
		create(xmlFile);
	}
	
	public Collection<DocumentContent> getContents()
	{
		return contents;
	}
	
	public String getHeaderText()
	{
		return headerText;
	}
	
	public String getFooterText()
	{
		return footerText;
	}
	
	private void create(File xmlFile) throws IOException, JDOMException
	{
		org.jdom.Document document = new SAXBuilder().build(xmlFile);
		org.jdom.Element root = document.getRootElement();
		
		org.jdom.Element headerElement = (org.jdom.Element) XPath.selectSingleNode(root, "HeaderText");
		org.jdom.Element footerElement = (org.jdom.Element) XPath.selectSingleNode(root, "FooterText");
		
		headerText = headerElement.getTextTrim();
		footerText = footerElement.getTextTrim();
		
		org.jdom.Element textContentsElement = (org.jdom.Element) XPath.selectSingleNode(root, "Contents");
		
		List textContentElements = textContentsElement.getChildren();
		
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
	
	private DocumentContent convertXmlTextContent(org.jdom.Element xmlElement)
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
			
		}
		
		return content;
	}
}
