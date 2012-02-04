package ch.zhaw.pdfrendering.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.swing.DefaultListModel;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ch.zhaw.pdfrendering.enums.DocumentContentType;
import ch.zhaw.pdfrendering.util.FontColor;

import javax.swing.ImageIcon;

import com.itextpdf.text.DocumentException;

import ch.zhaw.pdfrendering.doc.ContentFactory;
import ch.zhaw.pdfrendering.doc.DocumentBuilder;
import ch.zhaw.pdfrendering.doc.DocumentContent;
import ch.zhaw.pdfrendering.doc.meta.DocumentDefinition;
import ch.zhaw.pdfrendering.doc.meta.FontDescription;
import ch.zhaw.pdfrendering.doc.meta.FontStyle;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.Icon;

public class PdfRenderingApp extends JFrame
{
	private DefaultListModel model;
	private JList list;
	
	private JPanel contentPane;
	private JPanel documentPanel;
	private JPanel docSetupPanel;
	
	private JComboBox cbxContentType;
	private JComboBox cbxFont;
	private JComboBox cbxFontSize;
	private JComboBox cbxFontStyle;
	private JComboBox cbxDepth;
	private JComboBox cbxFormat;
	
	private JEditorPane editorPane;
	private JTextField txtExportPath;
	private JTextField txtImagePath;
	private JPanel drawPanel;
	private JPanel drawSettingsPanel;
	private JTextField txtCircleRadius;
	private JTextField txtEdgeTriangle;
	private JTextField txtEdgeSquare;
	private JTextField txtDrawingExportPath;
	private JTextField txtMergeDirPath;
	private JTextField txtCtempinputpdf;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					PdfRenderingApp frame = new PdfRenderingApp();
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PdfRenderingApp()
	{
		setTitle("PDF Rendering Application");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		documentPanel = new JPanel();
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tabbedPane.addTab("Create Document", documentPanel);
		tabbedPane.setToolTipTextAt(0, "Create a structured PDF document.");
		documentPanel.setLayout(null);
		
		docSetupPanel = new JPanel();
		docSetupPanel.setBounds(197, 0, 572, 521);
		documentPanel.add(docSetupPanel);
		docSetupPanel.setLayout(null);
		
		editorPane = new JEditorPane();
		editorPane.setFont(new Font("Tahoma", Font.PLAIN, 14));
		editorPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		editorPane.setBounds(10, 342, 556, 168);
		docSetupPanel.add(editorPane);
		
		JLabel lblInsertText = new JLabel("Insert Text:");
		lblInsertText.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblInsertText.setBounds(10, 317, 72, 14);
		docSetupPanel.add(lblInsertText);
		
		cbxContentType = new JComboBox();
		cbxContentType.setModel(new DefaultComboBoxModel(DocumentContentType.values()));
		cbxContentType.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbxContentType.setBounds(10, 36, 165, 28);
		docSetupPanel.add(cbxContentType);
		
		JLabel lblSelectDocumentContent = new JLabel("Select document content:");
		lblSelectDocumentContent.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSelectDocumentContent.setBounds(10, 11, 165, 14);
		docSetupPanel.add(lblSelectDocumentContent);
		
		JButton btnAddContent = new JButton("Add Content");
		btnAddContent.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAddContent.setBounds(447, 294, 119, 37);
		btnAddContent.addActionListener(new AddContentListener());
		docSetupPanel.add(btnAddContent);
		
		JButton btnClearContent = new JButton("Clear Content");
		btnClearContent.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnClearContent.setBounds(310, 294, 127, 37);
		btnClearContent.addActionListener(new ClearContentListener());
		docSetupPanel.add(btnClearContent);
		
		JLabel lblSetFont = new JLabel("Set Font:");
		lblSetFont.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSetFont.setBounds(219, 13, 72, 14);
		docSetupPanel.add(lblSetFont);
		
		cbxFont = new JComboBox();
		cbxFont.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbxFont.setBounds(219, 36, 147, 28);
		docSetupPanel.add(cbxFont);
		
		JLabel lblSetSize = new JLabel("Set Size:");
		lblSetSize.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSetSize.setBounds(504, 11, 62, 14);
		docSetupPanel.add(lblSetSize);
		
		cbxFontSize = new JComboBox();
		cbxFontSize.setModel(new DefaultComboBoxModel(new String[] {"10", "12", "14", "16", "18", "24", "32", "36", "40", "44", "48"}));
		cbxFontSize.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbxFontSize.setBounds(504, 34, 52, 28);
		docSetupPanel.add(cbxFontSize);
		
		JLabel lblSetFontstyle = new JLabel("Set Fontstyle:");
		lblSetFontstyle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSetFontstyle.setBounds(392, 7, 102, 22);
		docSetupPanel.add(lblSetFontstyle);
		
		cbxFontStyle = new JComboBox();
		cbxFontStyle.setModel(new DefaultComboBoxModel(FontStyle.values()));
		cbxFontStyle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbxFontStyle.setBounds(392, 36, 92, 28);
		docSetupPanel.add(cbxFontStyle);
		
		JLabel lblSetHeadingDepth = new JLabel("Set Heading Depth:");
		lblSetHeadingDepth.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSetHeadingDepth.setBounds(10, 75, 132, 22);
		docSetupPanel.add(lblSetHeadingDepth);
		
		cbxDepth = new JComboBox();
		cbxDepth.setModel(new DefaultComboBoxModel(new String[] {"0", "1", "2", "3"}));
		cbxDepth.setSelectedIndex(0);
		cbxDepth.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbxDepth.setBounds(10, 101, 36, 28);
		docSetupPanel.add(cbxDepth);
		
		JButton btnExportContent = new JButton("Export Content");
		btnExportContent.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnExportContent.setBounds(168, 294, 132, 37);
		btnExportContent.addActionListener(new ExportContentListener());
		docSetupPanel.add(btnExportContent);
		
		txtExportPath = new JTextField();
		txtExportPath.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtExportPath.setText("C:\\temp\\test.pdf");
		txtExportPath.setBounds(219, 173, 347, 26);
		docSetupPanel.add(txtExportPath);
		txtExportPath.setColumns(10);
		
		JLabel lblExportPath = new JLabel("Set Export Path:");
		lblExportPath.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblExportPath.setBounds(219, 145, 132, 22);
		docSetupPanel.add(lblExportPath);
		
		JButton btnUp = new JButton("");
		btnUp.setIcon(new ImageIcon("C:\\Development\\Java\\Projects\\PdfRendering\\res\\arrow_up.png"));
		btnUp.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnUp.setBounds(56, 175, 36, 37);
		btnUp.addActionListener(new MoveUpListener());
		docSetupPanel.add(btnUp);
		
		JButton btnDown = new JButton("");
		btnDown.setIcon(new ImageIcon("C:\\Development\\Java\\Projects\\PdfRendering\\res\\arrow_down.png"));
		btnDown.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDown.setBounds(10, 175, 36, 37);
		btnDown.addActionListener(new MoveDownListener());
		docSetupPanel.add(btnDown);
		
		JLabel lblMoveItem = new JLabel("Move selected item:");
		lblMoveItem.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMoveItem.setBounds(10, 145, 132, 22);
		docSetupPanel.add(lblMoveItem);
		
		JButton btnBrowse = new JButton("...");
		btnBrowse.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnBrowse.setBounds(504, 97, 52, 37);
		btnBrowse.addActionListener(new BrowseListener());
		docSetupPanel.add(btnBrowse);
		
		txtImagePath = new JTextField();
		txtImagePath.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtImagePath.setColumns(10);
		txtImagePath.setBounds(219, 103, 265, 26);
		docSetupPanel.add(txtImagePath);
		
		JLabel lblBrowseForImage = new JLabel("Browse for Image:");
		lblBrowseForImage.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBrowseForImage.setBounds(219, 75, 132, 22);
		docSetupPanel.add(lblBrowseForImage);
		
		JLabel lblDocumentFormat = new JLabel("Set Document format:");
		lblDocumentFormat.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDocumentFormat.setBounds(219, 210, 147, 22);
		docSetupPanel.add(lblDocumentFormat);
		
		cbxFormat = new JComboBox();
		cbxFormat.setModel(new DefaultComboBoxModel(new String[] {"A0", "A1", "A2", "A3", "A4", "A4_LANDSCAPE ", "A5", "A6"}));
		cbxFormat.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbxFormat.setBounds(217, 243, 132, 28);
		docSetupPanel.add(cbxFormat);
		
		model = new DefaultListModel();
		list = new JList(model);
		list.setFont(new Font("Tahoma", Font.BOLD, 14));
		list.setBorder(new LineBorder(new Color(0, 0, 0)));
		list.setBounds(0, 0, 194, 510);
		list.addListSelectionListener(new ListSelectionChangedListener());
		documentPanel.add(list);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel mergePanel = new JPanel();
		tabbedPane.addTab("Merge PDF", (Icon) null, mergePanel, "Merges several PDF documents into a single document.");
		mergePanel.setLayout(null);
		
		JLabel lblSetPathTo = new JLabel("Set path to directory with PDF files to be merged:");
		lblSetPathTo.setBounds(10, 9, 333, 17);
		lblSetPathTo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		mergePanel.add(lblSetPathTo);
		
		txtMergeDirPath = new JTextField();
		txtMergeDirPath.setBounds(10, 37, 369, 23);
		txtMergeDirPath.setText("C:\\temp");
		txtMergeDirPath.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtMergeDirPath.setColumns(10);
		mergePanel.add(txtMergeDirPath);
		
		JButton btnMergePdf = new JButton("Merge PDF");
		btnMergePdf.setBounds(404, 30, 123, 37);
		btnMergePdf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		mergePanel.add(btnMergePdf);
		tabbedPane.setEnabledAt(1, true);
		
		JPanel splitPanel = new JPanel();
		tabbedPane.addTab("Split PDF", (Icon) null, splitPanel, "Splits a PDF into single PDF files.");
		splitPanel.setLayout(null);
		
		JLabel lblSetPathTo_1 = new JLabel("Set path to the PDF file to be split into single files:");
		lblSetPathTo_1.setBounds(10, 9, 395, 17);
		lblSetPathTo_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		splitPanel.add(lblSetPathTo_1);
		
		txtCtempinputpdf = new JTextField();
		txtCtempinputpdf.setBounds(10, 37, 368, 23);
		txtCtempinputpdf.setText("C:\\temp\\input.pdf");
		txtCtempinputpdf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtCtempinputpdf.setColumns(10);
		splitPanel.add(txtCtempinputpdf);
		
		JButton btnSplitPdf = new JButton("Split PDF");
		btnSplitPdf.setBounds(399, 30, 110, 36);
		btnSplitPdf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		splitPanel.add(btnSplitPdf);
		tabbedPane.setEnabledAt(2, true);
		
		drawPanel = new JPanel();
		tabbedPane.addTab("Drawing", null, drawPanel, null);
		drawPanel.setLayout(new BorderLayout(0, 0));
		
		drawSettingsPanel = new JPanel();
		drawPanel.add(drawSettingsPanel);
		drawSettingsPanel.setLayout(null);
		
		JCheckBox chckbxDrawCircle = new JCheckBox("Draw Circle");
		chckbxDrawCircle.setBounds(10, 69, 93, 25);
		drawSettingsPanel.add(chckbxDrawCircle);
		chckbxDrawCircle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblNewLabel = new JLabel("Select items to draw:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 24, 141, 14);
		drawSettingsPanel.add(lblNewLabel);
		
		JCheckBox chckbxDrawTriangle = new JCheckBox("Draw Triangle");
		chckbxDrawTriangle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxDrawTriangle.setBounds(10, 130, 124, 25);
		drawSettingsPanel.add(chckbxDrawTriangle);
		
		JCheckBox chckbxDrawSquare = new JCheckBox("Draw Square");
		chckbxDrawSquare.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxDrawSquare.setBounds(10, 193, 112, 25);
		drawSettingsPanel.add(chckbxDrawSquare);
		
		JLabel lblSetSize_1 = new JLabel("Set Radius (cm):");
		lblSetSize_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSetSize_1.setBounds(169, 74, 112, 14);
		drawSettingsPanel.add(lblSetSize_1);
		
		JLabel lblSetEdgeLength = new JLabel("Set Edge length (cm):");
		lblSetEdgeLength.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSetEdgeLength.setBounds(169, 132, 149, 20);
		drawSettingsPanel.add(lblSetEdgeLength);
		
		JLabel label = new JLabel("Set Edge length (cm):");
		label.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label.setBounds(169, 195, 149, 20);
		drawSettingsPanel.add(label);
		
		txtCircleRadius = new JTextField();
		txtCircleRadius.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtCircleRadius.setBounds(337, 69, 93, 25);
		drawSettingsPanel.add(txtCircleRadius);
		txtCircleRadius.setColumns(10);
		
		txtEdgeTriangle = new JTextField();
		txtEdgeTriangle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtEdgeTriangle.setColumns(10);
		txtEdgeTriangle.setBounds(337, 130, 93, 25);
		drawSettingsPanel.add(txtEdgeTriangle);
		
		txtEdgeSquare = new JTextField();
		txtEdgeSquare.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtEdgeSquare.setColumns(10);
		txtEdgeSquare.setBounds(337, 193, 93, 25);
		drawSettingsPanel.add(txtEdgeSquare);
		
		txtDrawingExportPath = new JTextField();
		txtDrawingExportPath.setText("C:\\temp\\test.pdf");
		txtDrawingExportPath.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtDrawingExportPath.setColumns(10);
		txtDrawingExportPath.setBounds(10, 275, 347, 26);
		drawSettingsPanel.add(txtDrawingExportPath);
		
		JLabel label_1 = new JLabel("Set Export Path:");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_1.setBounds(10, 247, 132, 22);
		drawSettingsPanel.add(label_1);
		
		JButton btnExportPdf = new JButton("Export PDF");
		btnExportPdf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnExportPdf.setBounds(382, 269, 132, 37);
		drawSettingsPanel.add(btnExportPdf);
		
		addFonts();
	}
	
	private void addFonts()
	{
		String fontDir = "C:/Windows/Fonts";
		FontDescription.registerFontDirectory(fontDir);
		List<String> fonts = new ArrayList<String>(Arrays.asList(new String[] {"ARIAL", "GEORGIA", "TAHOMA", "TIMES"}));
		
		File fontDirectory = new File(fontDir);
		
		for (File fontFile : fontDirectory.listFiles(new FontFileFilter()))
		{
			String fontName = fontFile.getName().replace(FontFileFilter.SUFFIX, "").toUpperCase();
			
			if (fonts.contains(fontName))
			{
				cbxFont.addItem(fontName);
			}			
		}
	}
	
	private FontDescription getFontDescription()
	{
		String fontName = cbxFont.getSelectedItem().toString();
		int fontSize = Integer.valueOf(cbxFontSize.getSelectedItem().toString());
		FontStyle fontStyle = (FontStyle)cbxFontStyle.getSelectedItem();
		
		return new FontDescription(fontName, fontSize, fontStyle, FontColor.fromAwtColor(Color.BLACK));
	}
	
	private DocumentContentType getSelectedContentType()
	{
		return (DocumentContentType)cbxContentType.getSelectedItem();
	}
	
	private String getImagePath()
	{
		return txtImagePath.getText();
	}
	
	private int getHeadingDepth()
	{
		String selection = cbxDepth.getSelectedItem().toString();
		
		return Integer.valueOf(selection);
	}
	
//	private boolean hasTitlePage()
//	{
//		for (int i = 0; i < model.size(); i++)
//		{
//			DocumentListItem item = (DocumentListItem)model.get(i);
//			
//			if (item.getType() == DocumentContentType.TITLEPAGE)
//			{
//				return true;
//			}
//		}
//		
//		return false;
//	}
	
	private static class FontFileFilter implements FileFilter
	{
		public static String SUFFIX = ".ttf";
		
		public boolean accept(File pathname)
		{
			return pathname.exists() && pathname.isFile() && pathname.getName().endsWith(SUFFIX);
		}
		
	}
	
	/* 
	 * ********************************
	 * EVENT HANDLING IMPLEMENTATIONS *
	 * ********************************
	 */
	
	private class MoveUpListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{			
			int selectedIndex = list.getSelectedIndex();
			DocumentListItem item = (DocumentListItem) list.getSelectedValue();
			
//			if (item.getType() == DocumentContentType.TITLEPAGE)
//			{
//				JOptionPane.showMessageDialog(contentPane, "Can not move titlepage in document.",
//						"Moving titlepage not allowed", JOptionPane.INFORMATION_MESSAGE);
//				return;
//			}
			
			if (selectedIndex != -1 && selectedIndex != 0)
			{
				list.removeListSelectionListener(list.getListSelectionListeners()[0]);
				int newIndex = selectedIndex - 1;
				DocumentListItem selectedItem = (DocumentListItem)model.remove(selectedIndex);
				DocumentListItem previousItem = (DocumentListItem)model.remove(newIndex);
				model.add(newIndex, selectedItem);
				model.add(selectedIndex, previousItem);
				list.setSelectedIndex(newIndex);
				list.addListSelectionListener(new ListSelectionChangedListener());
			}			
		}		
	}
	
	private class MoveDownListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			int selectedIndex = list.getSelectedIndex();
			DocumentListItem item = (DocumentListItem) list.getSelectedValue();
			
//			if (item.getType() == DocumentContentType.TITLEPAGE)
//			{
//				JOptionPane.showMessageDialog(contentPane, "Can not move titlepage in document.",
//						"Moving titlepage not allowed", JOptionPane.INFORMATION_MESSAGE);
//				return;
//			}
			
			if (selectedIndex != -1 && selectedIndex != model.getSize() - 1)
			{
				list.removeListSelectionListener(list.getListSelectionListeners()[0]);
				int newIndex = selectedIndex + 1;
				DocumentListItem nextItem = (DocumentListItem)model.remove(newIndex);
				DocumentListItem selectedItem = (DocumentListItem)model.remove(selectedIndex);
				model.add(selectedIndex, nextItem);
				model.add(newIndex, selectedItem);
				list.setSelectedIndex(newIndex);
				list.addListSelectionListener(new ListSelectionChangedListener());
			}			
		}		
	}
	
	private class AddContentListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			DocumentContentType type = getSelectedContentType();
			String imagePath = getImagePath();
			
//			if (hasTitlePage() && type == DocumentContentType.TITLEPAGE)
//			{
//				JOptionPane.showMessageDialog(contentPane, "Can only add 1 titlepage to document.",
//												"Titlepage already defined", JOptionPane.INFORMATION_MESSAGE);
//				return;
//			}
			if (type == DocumentContentType.IMAGE && (imagePath == null || imagePath != ""))
			{
				JOptionPane.showMessageDialog(contentPane, "Can't add image content - please specify image path first.",
											"Undefined image path", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			String text = editorPane.getText() == null ? "" : editorPane.getText();
			DocumentListItem item = new DocumentListItem(type, text);
			item.setFontDescription(getFontDescription());
			item.setImagePath(imagePath);
			item.setHeadingDepth(getHeadingDepth());
			model.addElement(item);	
			editorPane.setText(null);
		}
	}
	
	private class ClearContentListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			list.removeListSelectionListener(list.getListSelectionListeners()[0]);			
			int answer = JOptionPane.showConfirmDialog(contentPane,
														"Would you really like to clear all document contents?",
														"Clear Document", JOptionPane.YES_NO_OPTION);
			if (answer == JOptionPane.YES_OPTION)
			{
				model.removeAllElements();
			}
			list.addListSelectionListener(new ListSelectionChangedListener());
		}
		
	}
	
	private class ExportContentListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				List<DocumentContent> contents = new ArrayList<DocumentContent>();
				
				for (int i = 0; i < model.getSize(); i++)
				{
					DocumentListItem listItem = (DocumentListItem)model.get(i);
					DocumentContent content = ContentFactory.createContent(listItem.getType(), listItem);
					
					contents.add(content);
				}
				
				DocumentDefinition definition = new DocumentDefinition(cbxFormat.getSelectedItem().toString());
				definition.setAllMargins(20.0f);
				DocumentBuilder builder = new DocumentBuilder(definition, contents);
			
				builder.export(txtExportPath.getText());
				Runtime.getRuntime().exec(new String[] {"C:\\PROGRA~2\\Adobe\\READER~1.0\\Reader\\AcroRd32.exe", txtExportPath.getText()});
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				JOptionPane.showMessageDialog(contentPane, String.format("PDF export crashed! Reason: %s", ex.getMessage()),
						"Unexpected export error", JOptionPane.ERROR_MESSAGE);
			}			
		}		
	}
	
	private class BrowseListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JFileChooser fileBrowser = new JFileChooser(System.getProperty("user.dir"));
			fileBrowser.setLocale(Locale.ENGLISH);
			fileBrowser.setFileFilter(new ImageFileFilter(".gif", ".jpg", ".tif", ".tiff", ".png"));
			fileBrowser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			fileBrowser.setMultiSelectionEnabled(false);
			
			int result = fileBrowser.showOpenDialog(contentPane);
			
			if (result == JFileChooser.APPROVE_OPTION)
			{
				DocumentContentType type = getSelectedContentType();
				String text = editorPane.getText() == null ? "" : editorPane.getText();
				String imagePath = fileBrowser.getSelectedFile().getAbsolutePath();
				fileBrowser.setSelectedFile(fileBrowser.getSelectedFile());
				
				model.addElement(new DocumentListItem(type, text, imagePath));
				
				editorPane.setText(null);
			}
			
		}
		
		private class ImageFileFilter extends javax.swing.filechooser.FileFilter
		{
			String[] extensions;
			
			ImageFileFilter(String ... extensions)
			{
				this.extensions = extensions;
			}
			
			@Override
			public boolean accept(File file)
			{
				if (file.isFile())
				{
					for (int i = 0; i < extensions.length; i++)
					{
						if (file.getName().endsWith(extensions[i]))
						{
							return true;
						}
					}
				}
				
				return false;
			}

			@Override
			public String getDescription()
			{
				String desc = Arrays.toString(extensions).replace("[", "").replace("]", "");
				return String.format("Image Files: (%s)", desc);
			}
		}
	}

	private class ListSelectionChangedListener implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e)
		{
			editorPane.setText(null);
			DocumentListItem item = (DocumentListItem)list.getSelectedValue();
			editorPane.setText(item.getText());
		}
	}
}
