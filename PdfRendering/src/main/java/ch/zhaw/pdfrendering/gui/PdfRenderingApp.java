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
import java.util.StringTokenizer;

import javax.swing.DefaultListModel;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ch.zhaw.pdfrendering.PdfManipulation;
import ch.zhaw.pdfrendering.enums.DocumentContentType;
import ch.zhaw.pdfrendering.manipulation.PdfMerger;
import ch.zhaw.pdfrendering.manipulation.PdfSplitter;
import ch.zhaw.pdfrendering.manipulation.PdfTextTransformation;
import ch.zhaw.pdfrendering.util.ColorConverter;
import ch.zhaw.pdfrendering.util.PdfHelper;
import ch.zhaw.pdfrendering.util.Property;

import javax.swing.ImageIcon;

import com.itextpdf.text.DocumentException;

import ch.zhaw.pdfrendering.doc.ContentFactory;
import ch.zhaw.pdfrendering.doc.DocumentBuilder;
import ch.zhaw.pdfrendering.doc.DocumentContent;
import ch.zhaw.pdfrendering.doc.meta.DocumentDefinition;
import ch.zhaw.pdfrendering.doc.meta.FontDescription;
import ch.zhaw.pdfrendering.doc.meta.FontStyle;
import ch.zhaw.pdfrendering.doc.meta.XmlDocumentDefintion;
import ch.zhaw.pdfrendering.drawing.PdfShapeDrawing;
import ch.zhaw.pdfrendering.drawing.PdfShapeDrawing.Shape;

import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.Icon;
import ch.zhaw.pdfrendering.enums.MainColor;
import javax.swing.ListModel;
import java.awt.Toolkit;

public class PdfRenderingApp extends JFrame
{
	private DefaultListModel model;
	private JList documentList;
	
	private XmlDocumentDefintion xmlDocumentDefinition;
	
	private String exportPath;
	
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
	private JTextField txtSplitInputFile;
	private JTextField txtMergedFilePath;
	private JTextField txtSplitOutputDir;
	private JCheckBox chkDrawCircle;
	private JCheckBox chkDrawTriangle;
	private JCheckBox chkDrawSquare;
	private JComboBox cbxCircleColor;
	private JComboBox cbxTriangleColor;
	private JComboBox cbxSquareColor;
	private JPanel textPanel;
	private JTextField txtOutputPath;
	private JTextField txtSentence;
	private JPanel importDocumentPanel;
	private JList xmlDocumentList;
	private JTextField txtImportXml;
	private JTextField txtDocumentOutput;
	private JEditorPane displayTextPane;
	private JTabbedPane tabbedPane;

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
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Development\\Java\\Projects\\PdfRendering\\res\\icon.png"));
		setTitle("PDF Rendering Application");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		documentPanel = new JPanel();
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		importDocumentPanel = new JPanel();
		tabbedPane.addTab("Import document", null, importDocumentPanel, "Allows import of a document structure from an XML file");
		importDocumentPanel.setLayout(null);
		
		model = new DefaultListModel();
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setToolTipText("");
		panel.setBounds(204, 0, 572, 521);
		importDocumentPanel.add(panel);
		
		displayTextPane = new JEditorPane();
		displayTextPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		displayTextPane.setEditable(false);
		displayTextPane.setFont(new Font("Tahoma", Font.PLAIN, 14));
		displayTextPane.setBounds(10, 342, 537, 168);
		panel.add(displayTextPane);
		
		xmlDocumentList = new JList(model);
		xmlDocumentList.setFont(new Font("Tahoma", Font.BOLD, 14));
		xmlDocumentList.setBorder(new LineBorder(new Color(0, 0, 0)));
		xmlDocumentList.setBounds(0, 0, 194, 510);
		xmlDocumentList.addListSelectionListener(new ListSelectionChangedListener(xmlDocumentList, displayTextPane));
		importDocumentPanel.add(xmlDocumentList);

		JLabel lblText = new JLabel("Attached text:");
		lblText.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblText.setBounds(10, 317, 96, 14);
		panel.add(lblText);
		
		JButton btnClearImportedContent = new JButton("Clear Content");
		btnClearImportedContent.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnClearImportedContent.setBounds(278, 294, 127, 37);
		btnClearImportedContent.addActionListener(new ClearContentListener(xmlDocumentList, displayTextPane));
		panel.add(btnClearImportedContent);
		
		JComboBox cbxDocumentFormat = new JComboBox();
		cbxDocumentFormat.setModel(new DefaultComboBoxModel(new String[] {"A0", "A1", "A2", "A3", "A4", "A5", "A6"}));
		cbxDocumentFormat.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbxDocumentFormat.setBounds(208, 200, 54, 28);
		panel.add(cbxDocumentFormat);
		
		JButton btnExportImportedContent = new JButton("Export Content");
		btnExportImportedContent.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnExportImportedContent.setBounds(415, 294, 132, 37);
		btnExportImportedContent.addActionListener(new ExportContentListener(cbxDocumentFormat));
		panel.add(btnExportImportedContent);
		
		txtImportXml = new JTextField();
		txtImportXml.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtImportXml.setColumns(10);
		txtImportXml.setBounds(10, 44, 347, 26);
		panel.add(txtImportXml);
		
		JLabel lblXmlPath = new JLabel("Browse path of input XML file:");
		lblXmlPath.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblXmlPath.setBounds(10, 11, 191, 22);
		panel.add(lblXmlPath);
		
		JLabel label_10 = new JLabel("Set Document format:");
		label_10.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_10.setBounds(210, 167, 147, 22);
		panel.add(label_10);
		
		JLabel lblSetExportPath = new JLabel("Set export path of document");
		lblSetExportPath.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSetExportPath.setBounds(10, 81, 191, 22);
		panel.add(lblSetExportPath);
		
		txtDocumentOutput = new JTextField();
		txtDocumentOutput.setText("C:\\temp\\PdfRendering\\document_xml.pdf");
		txtDocumentOutput.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtDocumentOutput.setColumns(10);
		txtDocumentOutput.setBounds(10, 114, 347, 26);
		panel.add(txtDocumentOutput);
		
		JButton btnBrowseXml = new JButton("...");
		btnBrowseXml.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnBrowseXml.setBounds(367, 38, 52, 37);
		btnBrowseXml.addActionListener(new BrowseXmlListener());
		
		panel.add(btnBrowseXml);
		
		JButton btnImportContent = new JButton("Import Content");
		btnImportContent.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnImportContent.setBounds(136, 294, 132, 37);
		btnImportContent.addActionListener(new ImportContentListener());
		panel.add(btnImportContent);
		
		JLabel lblMove = new JLabel("Move selected item:");
		lblMove.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMove.setBounds(10, 161, 132, 22);
		panel.add(lblMove);
		
		JButton btnImportMoveUp = new JButton("");
		btnImportMoveUp.setIcon(new ImageIcon("C:\\Development\\Java\\Projects\\PdfRendering\\res\\arrow_up.png"));
		btnImportMoveUp.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnImportMoveUp.setBounds(10, 191, 36, 37);
		btnImportMoveUp.addActionListener(new MoveUpListener(xmlDocumentList, displayTextPane));
		panel.add(btnImportMoveUp);
		
		JButton btnImportMoveDown = new JButton("");
		btnImportMoveDown.setIcon(new ImageIcon("C:\\Development\\Java\\Projects\\PdfRendering\\res\\arrow_down.png"));
		btnImportMoveDown.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnImportMoveDown.setBounds(56, 191, 36, 37);
		btnImportMoveDown.addActionListener(new MoveDownListener(xmlDocumentList, displayTextPane));
		panel.add(btnImportMoveDown);
		tabbedPane.addTab("Create Document", documentPanel);
		tabbedPane.setToolTipTextAt(1, "Create a structured PDF document.");
		documentPanel.setLayout(null);
		
		docSetupPanel = new JPanel();
		docSetupPanel.setToolTipText("Allows Import of XML DocumentDefinition of a basic document structure for the PDF output.");
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
		
		cbxFormat = new JComboBox();
		cbxFormat.setModel(new DefaultComboBoxModel(new String[] {"A0", "A1", "A2", "A3", "A4", "A5", "A6"}));
		cbxFormat.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbxFormat.setBounds(217, 243, 132, 28);
		docSetupPanel.add(cbxFormat);
		
		JButton btnExportContent = new JButton("Export Content");
		btnExportContent.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnExportContent.setBounds(168, 294, 132, 37);
		btnExportContent.addActionListener(new ExportContentListener(cbxFormat));
		docSetupPanel.add(btnExportContent);
		
		txtExportPath = new JTextField();
		txtExportPath.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtExportPath.setText("C:\\temp\\PdfRendering\\document_custom.pdf");
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
		btnUp.addActionListener(new MoveUpListener(xmlDocumentList, editorPane));
		docSetupPanel.add(btnUp);
		
		JButton btnDown = new JButton("");
		btnDown.setIcon(new ImageIcon("C:\\Development\\Java\\Projects\\PdfRendering\\res\\arrow_down.png"));
		btnDown.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDown.setBounds(10, 175, 36, 37);
		btnDown.addActionListener(new MoveDownListener(xmlDocumentList, editorPane));
		docSetupPanel.add(btnDown);
		
		JLabel lblMoveItem = new JLabel("Move selected item:");
		lblMoveItem.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMoveItem.setBounds(10, 145, 132, 22);
		docSetupPanel.add(lblMoveItem);
		
		JButton btnBrowse = new JButton("...");
		btnBrowse.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnBrowse.setBounds(504, 97, 52, 37);
		btnBrowse.addActionListener(new BrowseImageListener());
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
		
		documentList = new JList(model);
		documentList.setFont(new Font("Tahoma", Font.BOLD, 14));
		documentList.setBorder(new LineBorder(new Color(0, 0, 0)));
		documentList.setBounds(0, 0, 194, 510);
		documentList.addListSelectionListener(new ListSelectionChangedListener(documentList, editorPane));
		documentPanel.add(documentList);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JButton btnClearContent = new JButton("Clear Content");
		btnClearContent.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnClearContent.setBounds(310, 294, 127, 37);
		btnClearContent.addActionListener(new ClearContentListener(documentList, editorPane));
		docSetupPanel.add(btnClearContent);
		
		JPanel mergePanel = new JPanel();
		tabbedPane.addTab("Merge PDF", (Icon) null, mergePanel, "Merges several PDF documents into a single document.");
		mergePanel.setLayout(null);
		
		JLabel lblSetPathTo = new JLabel("Set path to directory with PDF files to be merged:");
		lblSetPathTo.setBounds(10, 9, 333, 17);
		lblSetPathTo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		mergePanel.add(lblSetPathTo);
		
		txtMergeDirPath = new JTextField();
		txtMergeDirPath.setBounds(10, 37, 369, 23);
		txtMergeDirPath.setText("C:\\temp\\PdfRendering\\merge");
		txtMergeDirPath.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtMergeDirPath.setColumns(10);
		mergePanel.add(txtMergeDirPath);
		
		JButton btnMergePdf = new JButton("Merge PDF");
		btnMergePdf.setBounds(402, 92, 123, 37);
		btnMergePdf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnMergePdf.addActionListener(new MergePdfListener());
		mergePanel.add(btnMergePdf);
		
		JLabel lblSetPathOf = new JLabel("Set path of merged output PDF file:");
		lblSetPathOf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSetPathOf.setBounds(10, 71, 333, 17);
		mergePanel.add(lblSetPathOf);
		
		txtMergedFilePath = new JTextField();
		txtMergedFilePath.setText("C:\\temp\\PdfRendering\\merged.pdf");
		txtMergedFilePath.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtMergedFilePath.setColumns(10);
		txtMergedFilePath.setBounds(10, 99, 369, 23);
		mergePanel.add(txtMergedFilePath);
		tabbedPane.setEnabledAt(2, true);
		
		JPanel splitPanel = new JPanel();
		tabbedPane.addTab("Split PDF", (Icon) null, splitPanel, "Splits a PDF into single PDF files.");
		splitPanel.setLayout(null);
		
		JLabel lblSetPathTo_1 = new JLabel("Set path to the PDF file to be split into single files:");
		lblSetPathTo_1.setBounds(10, 9, 395, 17);
		lblSetPathTo_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		splitPanel.add(lblSetPathTo_1);
		
		txtSplitInputFile = new JTextField();
		txtSplitInputFile.setBounds(10, 37, 368, 23);
		txtSplitInputFile.setText("C:\\temp\\PdfRendering\\split\\input.pdf");
		txtSplitInputFile.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtSplitInputFile.setColumns(10);
		splitPanel.add(txtSplitInputFile);
		
		JButton btnSplitPdf = new JButton("Split PDF");
		btnSplitPdf.setBounds(399, 92, 110, 36);
		btnSplitPdf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSplitPdf.addActionListener(new SplitPdfListener());
		splitPanel.add(btnSplitPdf);
		
		txtSplitOutputDir = new JTextField();
		txtSplitOutputDir.setText("C:\\temp\\PdfRendering\\single");
		txtSplitOutputDir.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtSplitOutputDir.setColumns(10);
		txtSplitOutputDir.setBounds(10, 99, 368, 23);
		splitPanel.add(txtSplitOutputDir);
		
		JLabel lblSetPathTo_2 = new JLabel("Set path to the directory to write split PDF files to:");
		lblSetPathTo_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSetPathTo_2.setBounds(10, 71, 395, 17);
		splitPanel.add(lblSetPathTo_2);
		tabbedPane.setEnabledAt(3, true);
		
		drawPanel = new JPanel();
		tabbedPane.addTab("Drawing", null, drawPanel, "Allows drawing of some shapes to the output PDF file.");
		drawPanel.setLayout(new BorderLayout(0, 0));
		
		drawSettingsPanel = new JPanel();
		drawPanel.add(drawSettingsPanel);
		drawSettingsPanel.setLayout(null);
		
		chkDrawCircle = new JCheckBox("Draw Circle");
		chkDrawCircle.setBounds(10, 69, 93, 25);
		drawSettingsPanel.add(chkDrawCircle);
		chkDrawCircle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblNewLabel = new JLabel("Select items to draw:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(10, 24, 141, 14);
		drawSettingsPanel.add(lblNewLabel);
		
		chkDrawTriangle = new JCheckBox("Draw Triangle");
		chkDrawTriangle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chkDrawTriangle.setBounds(10, 130, 124, 25);
		drawSettingsPanel.add(chkDrawTriangle);
		
		chkDrawSquare = new JCheckBox("Draw Square");
		chkDrawSquare.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chkDrawSquare.setBounds(10, 193, 112, 25);
		drawSettingsPanel.add(chkDrawSquare);
		
		JLabel lblSetSize_1 = new JLabel("Set Radius (mm):");
		lblSetSize_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSetSize_1.setBounds(169, 74, 112, 14);
		drawSettingsPanel.add(lblSetSize_1);
		
		JLabel lblSetEdgeLength = new JLabel("Set Edge length (mm):");
		lblSetEdgeLength.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSetEdgeLength.setBounds(169, 132, 149, 20);
		drawSettingsPanel.add(lblSetEdgeLength);
		
		JLabel lblSetEdgeLength_1 = new JLabel("Set Edge length (mm):");
		lblSetEdgeLength_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSetEdgeLength_1.setBounds(169, 195, 149, 20);
		drawSettingsPanel.add(lblSetEdgeLength_1);
		
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
		txtDrawingExportPath.setText("C:\\temp\\PdfRendering\\drawing.pdf");
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
		btnExportPdf.addActionListener(new DrawPdfListener());
		drawSettingsPanel.add(btnExportPdf);
		
		cbxCircleColor = new JComboBox();
		cbxCircleColor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbxCircleColor.setModel(new DefaultComboBoxModel(MainColor.values()));
		cbxCircleColor.setBounds(482, 69, 124, 24);
		drawSettingsPanel.add(cbxCircleColor);
		
		JLabel lblSetShapeColor = new JLabel("Set shape fill color:");
		lblSetShapeColor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSetShapeColor.setBounds(482, 40, 141, 20);
		drawSettingsPanel.add(lblSetShapeColor);
		
		cbxTriangleColor = new JComboBox();
		cbxTriangleColor.setModel(new DefaultComboBoxModel(MainColor.values()));
		cbxTriangleColor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbxTriangleColor.setBounds(482, 130, 124, 24);
		drawSettingsPanel.add(cbxTriangleColor);
		
		cbxSquareColor = new JComboBox();
		cbxSquareColor.setModel(new DefaultComboBoxModel(MainColor.values()));
		cbxSquareColor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbxSquareColor.setBounds(482, 193, 124, 24);
		drawSettingsPanel.add(cbxSquareColor);
		
		textPanel = new JPanel();
		tabbedPane.addTab("Text transformation", null, textPanel, "Prints some variations of the inserted text into output PDF file:");
		textPanel.setLayout(null);
		
		JLabel lblSetPathTo_3 = new JLabel("Set path to the output PDF file to be generated:");
		lblSetPathTo_3.setBounds(10, 73, 298, 17);
		lblSetPathTo_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textPanel.add(lblSetPathTo_3);
		
		txtOutputPath = new JTextField();
		txtOutputPath.setBounds(10, 101, 488, 23);
		txtOutputPath.setText("C:\\temp\\PdfRendering\\text_depiction.pdf");
		txtOutputPath.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtOutputPath.setColumns(10);
		textPanel.add(txtOutputPath);
		
		JLabel lblSpecifyASentence = new JLabel("Specify a sentence to be written to the PDF file.");
		lblSpecifyASentence.setBounds(10, 11, 305, 17);
		lblSpecifyASentence.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textPanel.add(lblSpecifyASentence);
		
		txtSentence = new JTextField();
		txtSentence.setBounds(10, 39, 488, 23);
		txtSentence.setText("The quick brown fox jumps over the lazy dog.");
		txtSentence.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtSentence.setColumns(10);
		textPanel.add(txtSentence);
		
		JButton btnGeneratePdf = new JButton("Generate PDF");
		btnGeneratePdf.setBounds(518, 94, 127, 36);
		btnGeneratePdf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnGeneratePdf.addActionListener(new TextOperationListener());
		textPanel.add(btnGeneratePdf);
		
		addFonts();
	}
	
	private void addFonts()
	{
		String fontDir = Property.getFontDirectory();
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
		
		return new FontDescription(fontName, fontSize, fontStyle, ColorConverter.fromAwtColor(Color.BLACK));
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
		private final JList list;
		private final JEditorPane displayPane;
		
		public MoveUpListener(JList list, JEditorPane displayPane)
		{
			this.list = list;
			this.displayPane = displayPane;
		}
		
		public void actionPerformed(ActionEvent e)
		{			
			int selectedIndex = list.getSelectedIndex();
			DocumentListItem item = (DocumentListItem) list.getSelectedValue();
			
			if (selectedIndex != -1 && selectedIndex != 0)
			{
				list.removeListSelectionListener(list.getListSelectionListeners()[0]);
				int newIndex = selectedIndex - 1;
				DocumentListItem selectedItem = (DocumentListItem)model.remove(selectedIndex);
				DocumentListItem previousItem = (DocumentListItem)model.remove(newIndex);
				model.add(newIndex, selectedItem);
				model.add(selectedIndex, previousItem);
				list.setSelectedIndex(newIndex);
				list.addListSelectionListener(new ListSelectionChangedListener(list, displayPane));
			}			
		}		
	}
	
	private class MoveDownListener implements ActionListener
	{
		private final JList list;
		private final JEditorPane displayPane;
		
		public MoveDownListener(JList list, JEditorPane displayPane)
		{
			this.list = list;
			this.displayPane = displayPane;
		}
		
		public void actionPerformed(ActionEvent e)
		{
			int selectedIndex = list.getSelectedIndex();
			DocumentListItem item = (DocumentListItem) list.getSelectedValue();
			
			if (selectedIndex != -1 && selectedIndex != model.getSize() - 1)
			{
				list.removeListSelectionListener(list.getListSelectionListeners()[0]);
				int newIndex = selectedIndex + 1;
				DocumentListItem nextItem = (DocumentListItem)model.remove(newIndex);
				DocumentListItem selectedItem = (DocumentListItem)model.remove(selectedIndex);
				model.add(selectedIndex, nextItem);
				model.add(newIndex, selectedItem);
				list.setSelectedIndex(newIndex);
				list.addListSelectionListener(new ListSelectionChangedListener(list, displayPane));
			}			
		}		
	}
	
	private class AddContentListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			DocumentContentType type = getSelectedContentType();
			String imagePath = getImagePath();

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
		private JList list;
		private JEditorPane displayPane;
		
		public ClearContentListener(JList list, JEditorPane displayPane)
		{
			this.list = list;
			this.displayPane = displayPane;
		}
		
		public void actionPerformed(ActionEvent e)
		{
			this.list.removeListSelectionListener(this.list.getListSelectionListeners()[0]);			
			int answer = JOptionPane.showConfirmDialog(contentPane,
														"Would you really like to clear all document contents?",
														"Clear Document", JOptionPane.YES_NO_OPTION);
			if (answer == JOptionPane.YES_OPTION)
			{
				model.removeAllElements();
			}
			this.list.addListSelectionListener(new ListSelectionChangedListener(this.list, this.displayPane));
		}
		
	}
	
	private class ExportContentListener implements ActionListener
	{
		private final JComboBox formatComboBox;
		
		public ExportContentListener(JComboBox formatComboBox)
		{
			this.formatComboBox = formatComboBox;
		}
		
		public void actionPerformed(ActionEvent e)
		{
			int selectedTabIndex = tabbedPane.getSelectedIndex();
			String title = tabbedPane.getTitleAt(selectedTabIndex);
			
			if (title.equalsIgnoreCase("Create Document"))
			{
				exportPath = txtExportPath.getText();
			}
			else if (title.equalsIgnoreCase("Import Document"))
			{
				exportPath = txtDocumentOutput.getText();
			}
			
			try
			{
				List<DocumentContent> contents = new ArrayList<DocumentContent>();
				
				for (int i = 0; i < model.getSize(); i++)
				{
					DocumentListItem listItem = (DocumentListItem)model.get(i);
					DocumentContent content;
					
					if (listItem.hasContent())
					{
						content = listItem.getContent();
					}
					else
					{
						content = ContentFactory.createContent(listItem.getType(), listItem);
					}
					
					contents.add(content);
				}
				
				DocumentDefinition definition = new DocumentDefinition(formatComboBox.getSelectedItem().toString());
				definition.setAllMargins(50.0f);
				DocumentBuilder builder = new DocumentBuilder(definition, contents);
				
				if (xmlDocumentDefinition != null)
				{
					builder.createHeaderFooter(xmlDocumentDefinition.getHeaderText(), xmlDocumentDefinition.getFooterText());
				}
			
				builder.export(exportPath);
				PdfHelper.displayPdf(exportPath);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				JOptionPane.showMessageDialog(contentPane, String.format("PDF export crashed! Reason: %s", ex.getMessage()),
						"Unexpected export error", JOptionPane.ERROR_MESSAGE);
			}			
		}		
	}
	
	private class BrowseImageListener implements ActionListener
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
		private final JList documentList;
		private final JEditorPane displayPane;
		
		public ListSelectionChangedListener(JList list, JEditorPane displayPane)
		{
			this.documentList = list;
			this.displayPane = displayPane;
		}
		
		public void valueChanged(ListSelectionEvent e)
		{
			displayPane.setText(null);
			DocumentListItem item = (DocumentListItem)this.documentList.getSelectedValue();
			displayPane.setText(item.getText());
		}
	}
	
	private class MergePdfListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String pdfDirectory = txtMergeDirPath.getText();
			String mergedFile = txtMergedFilePath.getText();
			
			PdfManipulation manipulation = new PdfMerger(pdfDirectory, mergedFile);
			
			try
			{
				manipulation.run();
				PdfHelper.displayPdf(txtMergedFilePath.getText());
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				JOptionPane.showMessageDialog(contentPane, String.format("PDF merge crashed! Reason: %s", ex.getMessage()),
						"Unexpected merge error", JOptionPane.ERROR_MESSAGE);
			}			
			
		}		
	}
	
	private class SplitPdfListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String inputFile = txtSplitInputFile.getText();
			String outputDirectory = txtSplitOutputDir.getText();

			
			PdfManipulation manipulation = new PdfSplitter(inputFile, outputDirectory);
			
			try
			{
				manipulation.run();
				JOptionPane.showMessageDialog(contentPane, "Splitting successfully completed", "Success", JOptionPane.INFORMATION_MESSAGE);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				JOptionPane.showMessageDialog(contentPane, String.format("PDF split crashed! Reason: %s", ex.getMessage()),
						"Unexpected merge error", JOptionPane.ERROR_MESSAGE);
			}			
		}
		
	}
	
	private class DrawPdfListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String outputFile = txtDrawingExportPath.getText();
			
			boolean drawCircle = chkDrawCircle.isSelected();
			boolean drawTriangle = chkDrawTriangle.isSelected();
			boolean drawSquare = chkDrawSquare.isSelected();
			
			int radius;
			int triangleEdge;
			int squareEdge;
			
			try
			{
				List<PdfShapeDrawing.ShapeDrawing> shapes = new ArrayList<PdfShapeDrawing.ShapeDrawing>();
				
				if (drawCircle)
				{
					radius = Integer.parseInt(txtCircleRadius.getText());
					Color color = ((MainColor)cbxCircleColor.getSelectedItem()).getAwtColor();
					shapes.add(PdfShapeDrawing.ShapeDrawing.create(Shape.CIRCLE, radius, color));
				}
				if (drawTriangle)
				{
					triangleEdge = Integer.parseInt(txtEdgeTriangle.getText());
					Color color = ((MainColor)cbxTriangleColor.getSelectedItem()).getAwtColor();
					shapes.add(PdfShapeDrawing.ShapeDrawing.create(Shape.TRIANGLE, triangleEdge, color));
				}
				if (drawSquare)
				{
					squareEdge = Integer.parseInt(txtEdgeSquare.getText());
					Color color = ((MainColor)cbxSquareColor.getSelectedItem()).getAwtColor();
					shapes.add(PdfShapeDrawing.ShapeDrawing.create(Shape.SQUARE, squareEdge, color));
				}
				if (!drawCircle && !drawTriangle && !drawSquare)
				{
					JOptionPane.showMessageDialog(contentPane, "No shape selected to be drawn to be PDF!",
							"Empty selection", JOptionPane.INFORMATION_MESSAGE);
				}
				
				try
				{
					new PdfShapeDrawing(outputFile, shapes).run();
					PdfHelper.displayPdf(outputFile);
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
					JOptionPane.showMessageDialog(contentPane, String.format("PDF generation crashed! Reason: %s", ex.getMessage(),
							"Unexpected error", JOptionPane.INFORMATION_MESSAGE));
				}				
			}
			catch (Exception ex)
			{
				JOptionPane.showMessageDialog(contentPane, "Invalid number defined!",
						"Invalid number format", JOptionPane.ERROR_MESSAGE);
			}			
		}		
	}
	
	private class TextOperationListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			StringTokenizer st = new StringTokenizer(txtSentence.getText());
			List<String> tokens = new ArrayList<String>();
			
			while (st.hasMoreTokens())
			{
				String token = st.nextToken(" ");
				tokens.add(token);
			}
			
			try
			{
				String outPath = txtOutputPath.getText();
				
				new PdfTextTransformation(outPath, tokens).run();
				PdfHelper.displayPdf(outPath);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				JOptionPane.showMessageDialog(contentPane, String.format("PDF generation crashed! Reason: %s", ex.getMessage(),
						"Unexpected error", JOptionPane.INFORMATION_MESSAGE));
			}			
		}		
	}
	
	private class ImportContentListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String xmlFilePath = txtImportXml.getText();
			File xmlFile = new File(xmlFilePath);
			
			try
			{
				xmlDocumentDefinition = new XmlDocumentDefintion(xmlFile);
				
				for (DocumentContent content : xmlDocumentDefinition.getContents())
				{
					model.addElement(new DocumentListItem(content));
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				JOptionPane.showMessageDialog(contentPane, String.format("Unable to import content definiton from XML file! Reason: %s", ex.getMessage(),
						"Unexpected error", JOptionPane.INFORMATION_MESSAGE));
			}		
		}		
	}
	
	private class BrowseXmlListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			JFileChooser fileBrowser = new JFileChooser("C:/temp/PdfRendering");
			fileBrowser.setLocale(Locale.ENGLISH);
			fileBrowser.setFileFilter(new XmlFileFilter(".xml"));
			fileBrowser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileBrowser.setMultiSelectionEnabled(false);
			
			int result = fileBrowser.showOpenDialog(contentPane);
			
			if (result == JFileChooser.APPROVE_OPTION)
			{
				String xmlPath = fileBrowser.getSelectedFile().getAbsolutePath();
				fileBrowser.setSelectedFile(fileBrowser.getSelectedFile());
				
				txtImportXml.setText(xmlPath);				
				editorPane.setText(null);
			}
		}
		
		private class XmlFileFilter extends javax.swing.filechooser.FileFilter
		{
			final String extension;
			
			XmlFileFilter(final String extension)
			{
				this.extension = extension;
			}
			
			@Override
			public boolean accept(File file)
			{
				return file.isFile() && file.getName().endsWith(extension);
			}

			@Override
			public String getDescription()
			{
				String desc = String.format("*.%s", extension);
				return String.format("XML Files: (%s)", desc);
			}
		}
	}
}
