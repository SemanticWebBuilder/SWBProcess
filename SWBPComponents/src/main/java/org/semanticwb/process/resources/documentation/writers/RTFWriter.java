package org.semanticwb.process.resources.documentation.writers;

import com.lowagie.text.*;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.rtf.field.RtfPageNumber;
import com.lowagie.text.rtf.headerfooter.RtfHeaderFooter;
import com.lowagie.text.rtf.style.RtfFont;
import org.jsoup.Jsoup;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.VersionInfo;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.RepositoryElement;
import org.semanticwb.process.model.RepositoryFile;
import org.semanticwb.process.model.RepositoryURL;
import org.semanticwb.process.model.documentation.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import static org.semanticwb.process.resources.utils.SWPUtils.copyFile;
import static org.semanticwb.process.resources.utils.SWPUtils.saveFile;

/**
 * Impmenents a RTF Writer for process documentation.
 * @author Hasdai Pacheco
 */
public class RTFWriter implements DocumentWriter {
	private static final Logger log = SWBUtils.getLogger(RTFWriter.class);
	private final DocumentationInstance di;
	private final Process p;

	/**
	 * Constructor.
	 * @param di {@link DocumentationInstance} object.
	 */
	public RTFWriter(DocumentationInstance di) {
		this.di = di;
		this.p = di.getProcessRef();
	}

	@Override
	public void write(String basePath) {
		HeaderFooter header = new HeaderFooter(new Phrase(p.getTitle(), FONTS.h), false);
		header.setAlignment(Element.ALIGN_CENTER);
		Paragraph pFooter = new Paragraph(new RtfPageNumber());
		pFooter.setAlignment(Element.ALIGN_RIGHT);
		pFooter.setFont(FONTS.h);
		RtfHeaderFooter footer = new RtfHeaderFooter(pFooter);

		try {
			// Create document
			Document doc = new Document(PageSize.LETTER, Utilities.millimetersToPoints(25f),
					Utilities.millimetersToPoints(25f), Utilities.millimetersToPoints(20f),
					Utilities.millimetersToPoints(20f));
			RtfWriter2.getInstance(doc, new FileOutputStream(basePath + p.getId() + ".rtf"));
			doc.addTitle(p.getId());
			doc.open();

			// Write document title page
			Paragraph pTitle = new Paragraph(p.getTitle(), FONTS.h1);// Title
			pTitle.setAlignment(Element.ALIGN_CENTER);
			doc.add(Chunk.NEWLINE);
			doc.add(Chunk.NEWLINE);
			doc.add(Chunk.NEWLINE);
			doc.add(Chunk.NEWLINE);
			doc.add(pTitle);
			doc.newPage();

			// Set document header and footer
			doc.setHeader(header);
			doc.setFooter(footer);

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

				// Add section header
				doc.add(new Paragraph(dsi.getSecTypeDefinition().getTitle(), FONTS.h2));
				doc.add(Chunk.NEWLINE);

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
						Table propsTable = new Table(props.length, (sectionElementInstances.size() + 1));
						propsTable.setPadding(15);
						// Add table header
						for (String propt : props) {// Header
							String titleprop = propt.substring(0, propt.indexOf(';'));
							Cell thead = new Cell(new Paragraph(titleprop, FONTS.th));
							thead.setHorizontalAlignment(Element.ALIGN_CENTER);
							thead.setVerticalAlignment(Element.ALIGN_MIDDLE);
							thead.setGrayFill(0.9f);
							propsTable.addCell(thead);
						}

						// Add rows
						for (SectionElement se : sectionElementInstances) {// Instances
							for (String propt : props) {
								String idProperty = propt.substring(propt.indexOf(';') + 1, propt.length());
								SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary()
										.getSemanticPropertyById(idProperty);
								Element content;

								if (prop != null && !prop.getPropId().equals(Referable.swpdoc_file.getPropId())) { // Not a reference
									content = new Paragraph((se.getSemanticObject().getProperty(prop) != null
											? se.getSemanticObject().getProperty(prop)
											: ""), FONTS.td);
								} else { // Reference
									Anchor anchor = new Anchor(se.getTitle(), FONTS.td);
									Referable ref = (Referable) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(se.getURI());
									RepositoryElement re = ref.getRefRepository();
									VersionInfo versionInfo = ref.getVersion() != null ? ref.getVersion()
											: re.getLastVersion();

									if (re instanceof RepositoryFile) {
										String basePathE = SWBPortal.getWorkPath() + "/models/"
												+ p.getProcessSite().getId() + "/swp_RepositoryFile/"
												+ ref.getRefRepository().getId() + "/" + versionInfo.getVersionNumber()
												+ "/";
										File baseDir = new File(basePathE);
										String basePathDest = SWBPortal.getWorkPath() + "/models/"
												+ p.getProcessSite().getId() + "/Resource/" + p.getId() + "/download/";
										File repFile = new File(basePathDest + "rep_files/" + ref.getRefRepository().getId() + "/" + versionInfo.getVersionNumber() + "/");
										if (!repFile.exists()) {
											repFile.mkdirs();
										}
										if (baseDir.isDirectory()) {
											File[] files = baseDir.listFiles();
											if (null != files && files.length > 0) {
												File file = files[0];
												String fileName = file.getName().substring(file.getName().lastIndexOf('.'));
												anchor.setReference("rep_Files/" + ref.getRefRepository().getId() + "/" + versionInfo.getVersionNumber() + "/" + se.getId() + fileName);
												copyFile(file.getAbsolutePath(), repFile.getAbsolutePath() + "/" + se.getId() + fileName);
											}
										}
									} else if (re instanceof RepositoryURL) {
										anchor.setReference(versionInfo.getVersionFile());
									}
									content = anchor;
								}
								Cell td = new Cell(content);
								propsTable.addCell(td);
							}
						}
						doc.add(propsTable);
					}
				} else if (cls.equals(FreeText.sclass)) {
					for (SectionElement se : sectionElementInstances) {
						FreeText freeText = (FreeText) se;
						File dir = new File(basePath + "rep_files/img/" + se.getId() + "/");
						if (!dir.exists()) {
							dir.mkdirs();
						}
						if (freeText.getText() != null) {
							addTextHtmlToRtf(freeText.getText(), basePath, doc, se);
						}
					}
				} else if (cls.equals(Activity.sclass)) {
					for (SectionElement se : sectionElementInstances) {
						Activity a = (Activity) se;
						if (a.getDescription() != null && !a.getDescription().isEmpty()) {
							doc.add(new Paragraph(a.getTitle(), FONTS.h3));
							doc.add(Chunk.NEWLINE);
							addTextHtmlToRtf(a.getDescription(), basePath, doc, se);
							doc.add(Chunk.NEWLINE);
						}
					}
				} else if (cls.equals(Model.sclass)) {
					Image image2 = Image.getInstance(basePath + "rep_files/" + p.getId() + ".png");// Model Image
					image2.scaleAbsolute(doc.getPageSize().getWidth() - doc.leftMargin() - doc.rightMargin(),
							doc.getPageSize().getHeight() - doc.topMargin() - doc.bottomMargin());
					doc.add(image2);
				}
				doc.newPage();
			}
			// Finish document
			doc.close();

			// Sections
		} catch (DocumentException | IOException de) {
			log.error("Error writing RTF document", de);
		}
	}

	public static void addTextHtmlToRtf(String html, String basePath, com.lowagie.text.Document doc, SectionElement se) {
		try {
			File dir = new File(basePath + "rep_files/img/" + se.getId() + "/");
			if (!dir.exists()) {
				dir.mkdirs();
			}
			org.jsoup.nodes.Document d = Jsoup.parse(html);
			Elements elements = d.select("[src]");
			html = d.html();
			int i = 1;
			org.jsoup.nodes.Document d1 = Jsoup.parse(html.substring(0));
			Paragraph content = new Paragraph(d1.text(), FONTS.body);
			content.setAlignment(Element.ALIGN_JUSTIFIED_ALL);
			doc.add(content);
			for (org.jsoup.nodes.Element element : elements) {
				if (element.tagName().equals("img")) {
					element.replaceWith(new TextNode("", element.baseUri()));
					int init = html.indexOf(element.outerHtml());
					int end = init + element.outerHtml().length();
					html = html.substring(end);
					String src = element.attr("src");
					String width = element.attr("width");
					String height = element.attr("height");
					Image image = null;
					if (src.startsWith("../..")) {
						image = Image.getInstance(SWBPortal.getWorkPath() + src.substring(10));// Model Image
					} else if (src.startsWith("data:image")) {
						int endImg = src.indexOf("base64,");
						if (endImg > -1) {
							endImg += 7;
							src = src.substring(endImg, src.length());
						}
						// Decodificar los datos y guardarlos en un archivo
						byte[] data = Base64.getDecoder().decode(src);
						try (OutputStream stream = new FileOutputStream(dir.getAbsolutePath() + "/" + se.getId() + i + ".png")) {
							stream.write(data);
							// Insertar la imagen en el documento
							image = Image.getInstance(dir.getAbsolutePath() + "/" + se.getId() + i + ".png");
						} catch (IOException ioe) {
							log.error("Error on getDocument, " + ioe.getLocalizedMessage());
						}

					} else {
						saveFile(src, dir.getAbsolutePath() + "/" + se.getId() + i + "." + src.substring(src.lastIndexOf('.') + 1));
						image = Image.getInstance(dir.getAbsolutePath() + "/" + se.getId() + i + "." + src.substring(src.lastIndexOf('.') + 1));// Model Image
					}

					if (null != image && width != null && height != null && !width.isEmpty() && !height.isEmpty()) {
						image.scaleToFit(doc.getPageSize().getWidth() - doc.leftMargin() - doc.rightMargin(), doc.getPageSize().getHeight() - doc.topMargin() - doc.bottomMargin());
					}
					doc.add(image);
					i++;
				}
			}
		} catch (DocumentException | IOException e) {
			log.error(e);
		}
	}

	@Override
	public void write(OutputStream ous) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Static class for Font definitions.
	 */
	private static class FONTS {
		public static final String ARIAL = "Arial";
		public static final Font body = new RtfFont(ARIAL, 10);
		public static final Font h = new RtfFont(ARIAL, 8);
		public static final Font h1 = new RtfFont(ARIAL, 16, Font.BOLD);
		public static final Font h2 = new RtfFont(ARIAL, 14, Font.BOLDITALIC);
		public static final Font h3 = new RtfFont(ARIAL, 13, Font.BOLD);
		public static final Font th = new RtfFont(ARIAL, 10, Font.BOLD);
		public static final Font td = new RtfFont(ARIAL, 9);

		private FONTS() {
			throw new IllegalStateException("FONTS Class cannot be instantiated");
		}
	}
}