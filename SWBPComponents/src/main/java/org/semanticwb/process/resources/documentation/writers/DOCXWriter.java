package org.semanticwb.process.resources.documentation.writers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.docx4j.convert.in.xhtml.XHTMLImporter;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.model.fields.merge.DataFieldName;
import org.docx4j.model.fields.merge.MailMerger;
import org.docx4j.model.structure.SectionWrapper;
import org.docx4j.model.table.TblFactory;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.FooterPart;
import org.docx4j.openpackaging.parts.WordprocessingML.HeaderPart;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.WordprocessingML.StyleDefinitionsPart;
import org.docx4j.relationships.Relationship;
import org.docx4j.sharedtypes.STOnOff;
import org.docx4j.wml.Br;
import org.docx4j.wml.CTShd;
import org.docx4j.wml.CTTblLook;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.Drawing;
import org.docx4j.wml.FldChar;
import org.docx4j.wml.FooterReference;
import org.docx4j.wml.Ftr;
import org.docx4j.wml.Hdr;
import org.docx4j.wml.HdrFtrRef;
import org.docx4j.wml.HeaderReference;
import org.docx4j.wml.Jc;
import org.docx4j.wml.JcEnumeration;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.PPrBase;
import org.docx4j.wml.R;
import org.docx4j.wml.RPr;
import org.docx4j.wml.STBrType;
import org.docx4j.wml.STFldCharType;
import org.docx4j.wml.STThemeColor;
import org.docx4j.wml.SectPr;
import org.docx4j.wml.Style;
import org.docx4j.wml.Styles;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.TblPr;
import org.docx4j.wml.TblWidth;
import org.docx4j.wml.Tc;
import org.docx4j.wml.TcPr;
import org.docx4j.wml.Text;
import org.docx4j.wml.Tr;
import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.process.resources.documentation.model.Activity;
import org.semanticwb.process.resources.documentation.model.DocumentSectionInstance;
import org.semanticwb.process.resources.documentation.model.DocumentationInstance;
import org.semanticwb.process.resources.documentation.model.FreeText;
import org.semanticwb.process.resources.documentation.model.Instantiable;
import org.semanticwb.process.resources.documentation.model.Model;
import org.semanticwb.process.resources.documentation.model.Referable;
import org.semanticwb.process.resources.documentation.model.SectionElement;

/**
 *
 * @author Hasdai Pacheco
 */
public class DOCXWriter implements DocumentWriter {
	private static final Logger log = SWBUtils.getLogger(DOCXWriter.class);
	private static final ObjectFactory objectFactory = new ObjectFactory();
	private final DocumentationInstance di;
	private final org.semanticwb.process.model.Process process;
	private final String assetsPath;
	private final Map config;
	private final HashMap<String, String> fieldValues;
	private HashMap<String, String> styleNameMappings;
	
	private static final String STYLE_H1 = "Heading1";
	private static final String STYLE_H2 = "Heading2";
	private static final String STYLE_H3 = "Heading3";
	private static final String STYLE_H4 = "Heading4";
	private static final String STYLE_NORMAL = "Normal";
	private static final String STYLE_TABLEGRID = "TableGrid";
	private static final String STYLE_HEADER = "Header";
	private static final String STYLE_FOOTER = "Footer";

	/**
	 * Creates a new DOCXWriter for the specified DocumentationInstance object.
	 *
	 * @param di
	 *            DocumentationInstance object
	 * @param assetsPath
	 *            Path to additional assets required in docx generation.
	 */
	public DOCXWriter(DocumentationInstance di, String assetsPath) {
		this(di, assetsPath, null, null);
	}

	/**
	 * Creates a new DOCXWriter for the specified DocumentationInstance object.
	 *
	 * @param di
	 *            DocumentationInstance object
	 * @param assetsPath
	 *            Path to additional assets required in docx generation.
	 * @param configParam
	 *            Configuration parameters.
	 */
	public DOCXWriter(DocumentationInstance di, String assetsPath, Map configParams) {
		this(di, assetsPath, configParams, null);
	}

	/**
	 * Creates a new DOCXWriter for the specified DocumentationInstance object.
	 *
	 * @param di
	 *            DocumentationInstance object
	 * @param assetsPath
	 *            Path to additional assets required in docx generation.
	 * @param configParams
	 *            Configuration parameters.
	 */
	public DOCXWriter(DocumentationInstance di, String assetsPath, Map configParams, Map<String, String> fieldValues) {
		this.di = di;
		this.process = di.getProcessRef();
		this.assetsPath = assetsPath;
		this.config = configParams;
		if (null != fieldValues) {
			this.fieldValues = (HashMap<String, String>)fieldValues;
		} else {
			// Add default field value mappings
			this.fieldValues = new HashMap<String, String>();
			this.fieldValues.put("processName", this.process.getTitle());
		}
		this.styleNameMappings = new HashMap<>();
		// Push default Style Names
		this.styleNameMappings.put(STYLE_H1, STYLE_H1);
		this.styleNameMappings.put(STYLE_H2, STYLE_H2);
		this.styleNameMappings.put(STYLE_H3, STYLE_H3);
		this.styleNameMappings.put(STYLE_H4, STYLE_H4);
		this.styleNameMappings.put(STYLE_NORMAL, STYLE_NORMAL);
		this.styleNameMappings.put(STYLE_TABLEGRID, STYLE_TABLEGRID);
		this.styleNameMappings.put(STYLE_HEADER, STYLE_HEADER);
		this.styleNameMappings.put(STYLE_FOOTER, STYLE_FOOTER);
	}

	/**
	 * Creates a new document from a template and replaces MergeFields.
	 *
	 * @param templatePath
	 *            path to document template
	 * @param includeHeaderFooterindica
	 *            whether to process MergeFields in header and footer
	 * @return WordprocessingMLPackage.
	 */
	private WordprocessingMLPackage writeTemplate(String templatePath, boolean includeHeaderFooter) {
		// Open template
		WordprocessingMLPackage doc = null;

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			doc = WordprocessingMLPackage.load(new FileInputStream(new File(templatePath)));
			mapStyles(doc.getMainDocumentPart());

			// Replace mergeFields
			List<Map<DataFieldName, String>> data = new ArrayList<>();

			Map<DataFieldName, String> map = new HashMap<>();
			String ownerName = "";
			if (null != process.getCreator() && null != process.getCreator().getFullName())
				ownerName = process.getCreator().getFullName();

			map.put(new DataFieldName("processName"), process.getTitle());
			map.put(new DataFieldName("processGroup"), process.getProcessGroup().getTitle());
			map.put(new DataFieldName("processOwner"), ownerName);
			map.put(new DataFieldName("currentDate"), (sdf.format(new Date())));
			data.add(map);

			MailMerger.setMERGEFIELDInOutput(MailMerger.OutputField.DEFAULT);
			MailMerger.performMerge(doc, map, includeHeaderFooter);
		} catch (Docx4JException | FileNotFoundException ex) {
			log.error("Error writing template", ex);
		}
		return doc;
	}

	@Override
	public void write(OutputStream ous) {
		WordprocessingMLPackage doc = null;
		boolean fromTemplate = false;

		try {
			// Get template path from config
			String tpl = null;
			if (null != config && null != config.get("template")) {
				tpl = (String) config.get("template");
			}

			if (null != tpl && !tpl.isEmpty()) {
				fromTemplate = true;
				doc = writeTemplate(tpl, true);
			}

			if (null == doc)
				doc = WordprocessingMLPackage.createPackage();
			MainDocumentPart content = doc.getMainDocumentPart();

			// Create header and footer
			if (!fromTemplate && null != config && null != config.get("includeHeaderFooter") && config.get("includeHeaderFooter").equals("true")) {
				createHeader(doc);
				createFooter(doc);
			}

			// Add first page
			if (!fromTemplate && null != config && null != config.get("includeFirstPage") && config.get("includeFirstPage").equals("true")) {
				String h1Name = styleNameMappings.get(STYLE_H1);
				if (null == h1Name)
					h1Name = STYLE_H1;

				P docTitle = content.addStyledParagraphOfText(h1Name, process.getTitle());
				alignParagraph(docTitle, JcEnumeration.CENTER);
			}

			// Add sections
			Iterator<DocumentSectionInstance> itdsi = SWBComparator.sortSortableObject(di.listDocumentSectionInstances());
			while (itdsi.hasNext()) {
				DocumentSectionInstance dsi = itdsi.next();
				SemanticClass cls = dsi.getSecTypeDefinition() != null
						&& dsi.getSecTypeDefinition().getSectionType() != null
								? dsi.getSecTypeDefinition().getSectionType().transformToSemanticClass()
								: null;

				if (null == cls || !dsi.getSecTypeDefinition().isActive())
					continue;

				// Add section title
				String h2Name = styleNameMappings.get(STYLE_H2);
				if (null == h2Name)
					h2Name = STYLE_H2;
				content.addStyledParagraphOfText(h2Name, dsi.getSecTypeDefinition().getTitle());

				// Gather sectionElement instances
				Iterator<SectionElement> itse = SWBComparator.sortSortableObject(dsi.listDocuSectionElementInstances());
				List<SectionElement> sectionElementInstances = new ArrayList<>();
				while (itse.hasNext()) {
					SectionElement se = itse.next();
					sectionElementInstances.add(se);
				}

				if (cls.isSubClass(Instantiable.swpdoc_Instantiable, false)) {
					// Get visible props from config
					String[] props = dsi.getSecTypeDefinition().getVisibleProperties().split("\\|");

					// Add properties table
					if (props.length > 0 && !sectionElementInstances.isEmpty()) {
						int writableWidthTwips = doc.getDocumentModel().getSections().get(0).getPageDimensions().getWritableWidthTwips();
						//EHSP: removed casts
						int cellWidthTwips = Double.valueOf((writableWidthTwips / props.length)).intValue();

						Tbl propsTable = TblFactory.createTable(sectionElementInstances.size() + 1, props.length, cellWidthTwips);
						String gridName = styleNameMappings.get(STYLE_TABLEGRID);
						if (null == gridName)
							gridName = STYLE_TABLEGRID;
						setStyle(propsTable, gridName);

						// Add table header
						Tr headerRow = (Tr) propsTable.getContent().get(0);
						int c = 0;
						for (String prop : props) {
							Tc col = (Tc) headerRow.getContent().get(c++);
							P colContent = objectFactory.createP();

							TcPr cellProps = col.getTcPr();
							cellProps.getTcW().setType(TblWidth.TYPE_DXA);

							Text colText = objectFactory.createText();
							colText.setValue(prop.substring(0, prop.indexOf(";")));
							R colRun = objectFactory.createR();
							colRun.getContent().add(colText);

							setFontStyle(colRun, false, true);

							colContent.getContent().add(colRun);
							col.getContent().set(0, colContent);
							alignParagraph(colContent, JcEnumeration.CENTER);
							fillTableCell(col, "#F2F2F2");
						}

						// Add rows
						int r = 1;
						for (SectionElement se : sectionElementInstances) {
							Tr row = (Tr) propsTable.getContent().get(r++);
							c = 0;
							for (String prop : props) {
								Tc col = (Tc) row.getContent().get(c++);
								String idProperty = prop.substring(prop.indexOf(';') + 1, prop.length());
								SemanticProperty sprop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(idProperty);
								P colContent;

								if (null == sprop) {
									colContent = content.createParagraphOfText("");
								} else {
									if (!sprop.getPropId().equals(Referable.swpdoc_file.getPropId())) {
										colContent = content.createParagraphOfText(se.getSemanticObject().getProperty(sprop) != null
														? se.getSemanticObject().getProperty(sprop)
														: "");
									} else {
										colContent = content.createParagraphOfText(se.getTitle());
									}
								}
								String normalName = styleNameMappings.get(STYLE_NORMAL);
								if (null == normalName)
									normalName = STYLE_NORMAL;
								setStyle(colContent, normalName);
								alignParagraph(colContent, JcEnumeration.BOTH);
								col.getContent().set(0, colContent);
							}
						}

						// Add table to document
						content.addObject(propsTable);
					}
				} else if (cls.equals(FreeText.sclass)) {
					XHTMLImporter importer = new XHTMLImporterImpl(doc);
					for (SectionElement se : sectionElementInstances) {
						FreeText freeText = (FreeText) se;
						if (null != se) {
							String sContent = freeText.getText();
							if (null != sContent && !sContent.isEmpty()) {
								sContent = sContent.replace("<!DOCTYPE html>", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\n");
								sContent = replaceImageRelativePath(sContent);

								// Override styles and alignment
								List<Object> objects = importer.convert(sContent, null);
								for (Object o : objects) {
									removeRFonts(o);
									if (o instanceof P) {
										alignParagraph((P) o, JcEnumeration.BOTH);
									}
									if (o instanceof Tbl) {
										String gridName = styleNameMappings.get(STYLE_TABLEGRID);
										if (null == gridName)
											gridName = STYLE_TABLEGRID;
										setStyle((Tbl) o, gridName);
									}
								}
								content.getContent().addAll(objects);
							}
						}
					}
				} else if (cls.equals(Activity.sclass)) {
					for (SectionElement se : sectionElementInstances) {
						Activity a = (Activity) se;
						if (a.getDescription() != null && !a.getDescription().isEmpty()) {
							XHTMLImporter importer = new XHTMLImporterImpl(doc);

							String h3Name = styleNameMappings.get(STYLE_H3);
							if (null == h3Name)
								h3Name = STYLE_H3;
							content.addStyledParagraphOfText(h3Name, a.getTitle());

							String sContent = a.getDescription();
							if (null != sContent && !sContent.isEmpty()) {
								sContent = sContent.replace("<!DOCTYPE html>", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\n");

								// Override styles and alignment
								List<Object> objects = importer.convert(sContent, null);
								for (Object o : objects) {
									removeRFonts(o);
									if (o instanceof P) {
										alignParagraph((P) o, JcEnumeration.BOTH);
									}

									if (o instanceof Tbl) {
										String gridName = styleNameMappings.get(STYLE_TABLEGRID);
										if (null == gridName)
											gridName = STYLE_TABLEGRID;
										setStyle((Tbl) o, gridName);
									}
								}
								content.getContent().addAll(objects);
							}
						}
					}
				} else if (cls.equals(Model.sclass)) {
					File img = new File(assetsPath + "/" + process.getId() + ".png");
					if (img.exists()) {
						FileInputStream fis = new FileInputStream(img);
						long length = img.length();

						if (length > Integer.MAX_VALUE) {
							log.error("File too large in model generation");
						} else {
							// Read image bytes
							byte[] bytes = new byte[(int) length];
							int offset = 0;
							int numRead = 0;
							while (offset < bytes.length && (numRead = fis.read(bytes, offset, bytes.length - offset)) >= 0) {
								offset += numRead;
							}

							if (offset < bytes.length) {
								log.error("Could not completely read file " + img.getName());
							}

							fis.close();

							// Generate ImagePart
							BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(doc, bytes);
							Inline inline = imagePart.createImageInline("", "", 0, 1, false);

							// Add image to paragraph
							P p = objectFactory.createP();
							R run = objectFactory.createR();
							p.getContent().add(run);
							Drawing drawing = objectFactory.createDrawing();
							run.getContent().add(drawing);
							drawing.getAnchorOrInline().add(inline);
							content.getContent().add(p);
						}
					}
				}

				if (null != config.get("addPageBreaks") && config.get("addPageBreaks").equals("true")) {
					addPageBreak(content);
				}
			}
			doc.save(ous);
		} catch (Exception ex) {
			log.error("Error creating DOCX document", ex);
		}
	}

	/**
	 * Removes run fonts from an element
	 *
	 * @param ca
	 *            Starting element
	 */
	private void removeRFonts(Object ca) {
		if (ca instanceof R) {
			RPr rpr = ((R) ca).getRPr();
			if (null != rpr)
				rpr.setRFonts(null);
		} else if (ca instanceof ContentAccessor) {
			List<Object> childs = ((ContentAccessor) ca).getContent();
			for (Object c : childs) {
				removeRFonts((ContentAccessor) c);
			}
		}
	}

	/**
	 * Sets style of paragraph
	 *
	 * @param paragraph
	 *            Paragraph to style
	 * @param styleName
	 *            Style name
	 */
	private void setStyle(ContentAccessor element, String styleName) {
		if (element instanceof P) {
			PPrBase.PStyle style = objectFactory.createPPrBasePStyle();
			style.setVal(styleName);

			PPr ppr = objectFactory.createPPr();
			ppr.setPStyle(style);
			((P) element).setPPr(ppr);
		} else if (element instanceof Tbl) {
			TblPr.TblStyle style = objectFactory.createCTTblPrBaseTblStyle();
			style.setVal(styleName);

			TblPr tpr = objectFactory.createTblPr();
			CTTblLook tblLook = objectFactory.createCTTblLook();
			tblLook.setFirstColumn(STOnOff.FALSE);
			tblLook.setFirstRow(STOnOff.TRUE);
			tblLook.setLastColumn(STOnOff.FALSE);
			tblLook.setLastRow(STOnOff.FALSE);
			tblLook.setNoHBand(STOnOff.FALSE);
			tblLook.setNoVBand(STOnOff.TRUE);
			tpr.setTblLook(tblLook);

			tpr.setTblStyle(style);
			((Tbl) element).setTblPr(tpr);
		}
	}

	/**
	 * Sets paragraph alignment
	 *
	 * @param paragraph
	 *            P to set alignment to
	 * @param alignment
	 *            Alignment value
	 */
	private void alignParagraph(P paragraph, JcEnumeration alignment) {
		PPr parProps = paragraph.getPPr();
		if (null == parProps)
			parProps = objectFactory.createPPr();
		Jc al = objectFactory.createJc();
		al.setVal(alignment);
		parProps.setJc(al);

		paragraph.setPPr(parProps);
	}

	/**
	 * Sets font style (bold/italic) for a paragraph run
	 *
	 * @param runElement
	 *            Run
	 * @param italic
	 *            wheter to set italic style
	 * @param bold
	 *            wheter to set bold style
	 */
	private void setFontStyle(R runElement, boolean italic, boolean bold) {
		RPr runProps = runElement.getRPr();
		if (null == runProps)
			runProps = objectFactory.createRPr();
		if (italic)
			runProps.setI(objectFactory.createBooleanDefaultTrue());
		if (bold)
			runProps.setB(objectFactory.createBooleanDefaultTrue());

		runElement.setRPr(runProps);
	}

	/**
	 * Fills table cell with given hex color
	 *
	 * @param cell
	 * @param hexColor
	 */
	private void fillTableCell(Tc cell, String hexColor) {
		TcPr cellProps = cell.getTcPr();
		if (null == cellProps)
			cellProps = objectFactory.createTcPr();
		CTShd shd = objectFactory.createCTShd();
		shd.setFill(hexColor);
		shd.setThemeFill(STThemeColor.BACKGROUND_2);
		cellProps.setShd(shd);
		cell.setTcPr(cellProps);
	}

	/**
	 * Adds page break to document
	 *
	 * @param content
	 *            Document's main part
	 * @throws Docx4JException
	 */
	private void addPageBreak(MainDocumentPart content) throws Docx4JException {
		Br breakObj = objectFactory.createBr();
		breakObj.setType(STBrType.PAGE);

		P paragraph = objectFactory.createP();
		paragraph.getContent().add(breakObj);
		content.getContents().getBody().getContent().add(paragraph);
	}

	/**
	 * Creates document footer including page number
	 *
	 * @param doc
	 *            WordprocessingMLPackage for the document.
	 * @throws InvalidFormatException
	 */
	private void createFooter(WordprocessingMLPackage doc) throws InvalidFormatException {
		MainDocumentPart content = doc.getMainDocumentPart();

		// Create footer
		FooterPart footer = new FooterPart();

		Ftr ftr = objectFactory.createFtr();
		P footerParagraph = objectFactory.createP();

		String footerlName = styleNameMappings.get(STYLE_FOOTER);
		if (null == footerlName)
			footerlName = STYLE_FOOTER;
		setStyle(footerParagraph, footerlName);

		PPr parProps = objectFactory.createPPr();
		Jc al = objectFactory.createJc();
		al.setVal(JcEnumeration.RIGHT);
		parProps.setJc(al);
		footerParagraph.setPPr(parProps);

		// Add field start
		R run = objectFactory.createR();
		FldChar fldChar = objectFactory.createFldChar();
		fldChar.setFldCharType(STFldCharType.BEGIN);
		run.getContent().add(fldChar);
		footerParagraph.getContent().add(run);

		// Add pageNumber field
		run = objectFactory.createR();
		Text txt = objectFactory.createText();
		txt.setSpace("preserve");
		txt.setValue(" PAGE   \\* MERGEFORMAT ");
		run.getContent().add(objectFactory.createRInstrText(txt));
		footerParagraph.getContent().add(run);

		// Add field end
		run = objectFactory.createR();
		fldChar = objectFactory.createFldChar();
		fldChar.setFldCharType(STFldCharType.END);
		run.getContent().add(fldChar);
		footerParagraph.getContent().add(run);

		ftr.getContent().add(footerParagraph);
		footer.setJaxbElement(ftr);

		Relationship rel = content.addTargetPart(footer);

		// Relate footer to document
		List<SectionWrapper> sections = doc.getDocumentModel().getSections();

		SectPr sectPr = sections.get(sections.size() - 1).getSectPr();

		if (null == sectPr) {
			sectPr = objectFactory.createSectPr();
			content.addObject(sectPr);
			sections.get(sections.size() - 1).setSectPr(sectPr);
		}

		FooterReference footerReference = objectFactory.createFooterReference();
		footerReference.setId(rel.getId());
		footerReference.setType(HdrFtrRef.DEFAULT);
		sectPr.getEGHdrFtrReferences().add(footerReference);
	}

	/**
	 * Creates document header including process name
	 *
	 * @param doc
	 *            WordprocessingMLPackage for the document.
	 * @throws InvalidFormatException
	 */
	private void createHeader(WordprocessingMLPackage doc) throws InvalidFormatException {
		MainDocumentPart content = doc.getMainDocumentPart();

		// Create header
		HeaderPart header = new HeaderPart();
		Relationship rel = content.addTargetPart(header);

		Hdr hdr = objectFactory.createHdr();

		P headerParagraph = content.createParagraphOfText(process.getTitle());
		hdr.getContent().add(headerParagraph);
		header.setJaxbElement(hdr);

		String headerName = styleNameMappings.get(STYLE_HEADER);
		if (null == headerName)
			headerName = STYLE_HEADER;
		setStyle(headerParagraph, headerName);
		alignParagraph(headerParagraph, JcEnumeration.CENTER);

		// Relate to document
		List<SectionWrapper> sections = doc.getDocumentModel().getSections();
		SectPr sectPr = sections.get(sections.size() - 1).getSectPr();

		if (null == sectPr) {
			sectPr = objectFactory.createSectPr();
			content.addObject(sectPr);
			sections.get(sections.size() - 1).setSectPr(sectPr);
		}

		HeaderReference headerReference = objectFactory.createHeaderReference();
		headerReference.setId(rel.getId());
		headerReference.setType(HdrFtrRef.DEFAULT);
		sectPr.getEGHdrFtrReferences().add(headerReference);
	}

	/**
	 * Updates style mapping table
	 *
	 * @param mappings
	 */
	public void setStyleNameMappings(Map<String, String> mappings) {
		this.styleNameMappings = (HashMap<String, String>)mappings;
	}

	@Override
	public void write(String path) throws FileNotFoundException {
		write(new FileOutputStream(path));
	}

	/**
	 * Searches for spanish style names in style definition and map them in style
	 * table.
	 *
	 * @param doc
	 *            MainDocumentPart
	 * @param styleTable
	 *            Style mapping table
	 */
	private void mapStyles(MainDocumentPart doc) {
		if (null == this.styleNameMappings || this.styleNameMappings.isEmpty())
			return;

		StyleDefinitionsPart sdp = doc.getStyleDefinitionsPart();
		Styles docStyles = (Styles) sdp.getJaxbElement();

		for (Style s : docStyles.getStyle()) {
			if (s.getStyleId().equals("Ttulo1")) {
				this.styleNameMappings.put(STYLE_H1, "Ttulo1");
			}
			if (s.getStyleId().equals("Ttulo2")) {
				this.styleNameMappings.put(STYLE_H2, "Ttulo2");
			}
			if (s.getStyleId().equals("Ttulo3")) {
				this.styleNameMappings.put(STYLE_H3, "Ttulo3");
			}
		}
	}

	/**
	 * Changes image relative paths in section contents.
	 *
	 * @param inputHTML
	 *            HTML content from section.
	 * @return Processed HTML with replaced relative image paths
	 */
	private String replaceImageRelativePath(String inputHTML) {
		org.jsoup.nodes.Document d = null;
		String workPath = SWBPortal.getWorkPath();
		if (inputHTML != null) {
			d = Jsoup.parse(inputHTML, "", Parser.xmlParser());
			Elements elements = d.select("[src]");
			for (org.jsoup.nodes.Element src : elements) {
				if (src.tagName().equals("img") || src.tagName().equals("iframe")) {
					String attr = src.attr("src");
					if (attr.contains("../..")) {
						//EHSP: Set result to attr
						attr = attr.replace("../..", workPath);
						File f = new File(attr);
						if (f.exists()) {
							try {
								src.attr("src", f.toURI().toURL().toString());
							} catch (MalformedURLException mex) {
								src.attr("src", attr);
								log.error(mex);
							}
						}
					}
				}
			}
		}
		return null != d ? d.html() : "";
	}
}
