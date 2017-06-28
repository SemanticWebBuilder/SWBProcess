package org.semanticwb.process.documentation.writers;

import com.lowagie.text.Anchor;
import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.Utilities;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.rtf.field.RtfPageNumber;
import com.lowagie.text.rtf.headerfooter.RtfHeaderFooter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
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
import org.semanticwb.process.documentation.model.Activity;
import org.semanticwb.process.documentation.model.DocumentSectionInstance;
import org.semanticwb.process.documentation.model.DocumentationInstance;
import org.semanticwb.process.documentation.model.FreeText;
import org.semanticwb.process.documentation.model.Instantiable;
import org.semanticwb.process.documentation.model.Model;
import org.semanticwb.process.documentation.model.Referable;
import org.semanticwb.process.documentation.model.SectionElement;
import org.semanticwb.process.documentation.resources.utils.SWPUtils;
import static org.semanticwb.process.documentation.resources.utils.SWPUtils.copyFile;
import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.ProcessSite;
import org.semanticwb.process.model.RepositoryElement;
import org.semanticwb.process.model.RepositoryFile;
import org.semanticwb.process.model.RepositoryURL;

/**
 *
 * @author hasdai
 */
public class RTFWriter implements DocumentWriter {
    private static final Logger log = SWBUtils.getLogger(RTFWriter.class);
    private final DocumentationInstance di;
    private final Process p;
    private final ProcessSite model;
    
    public RTFWriter(DocumentationInstance di) {
        this.di = di;
        this.p = di.getProcessRef();
        this.model = p.getProcessSite();
    }
    
    @Override
    public void write(String basePath) {
        //String basePath = SWBPortal.getWorkPath() + "/models/" + p.getProcessSite().getId() + "/Resource/" + p.getId() + "/download/";
        HeaderFooter header = new HeaderFooter(new Phrase(p.getTitle(), SWPUtils.FONTS.h), false);
        header.setAlignment(Element.ALIGN_CENTER);
        //header.setBorderWidthBottom(1);
        //header.setBorderColorBottom(Color.LIGHT_GRAY);
        Paragraph pFooter = new Paragraph(new RtfPageNumber());
        pFooter.setAlignment(Element.ALIGN_RIGHT);
        pFooter.setFont(SWPUtils.FONTS.h);
        RtfHeaderFooter footer = new RtfHeaderFooter(pFooter);

        try {
            //Create document
            Document doc = new Document(PageSize.LETTER, Utilities.millimetersToPoints(25f), Utilities.millimetersToPoints(25f), Utilities.millimetersToPoints(20f), Utilities.millimetersToPoints(20f));
            RtfWriter2.getInstance(doc, new FileOutputStream(basePath + p.getId() + ".rtf"));
            doc.addTitle(p.getId());
            doc.open();
            
            //Write document title page
            Paragraph pTitle = new Paragraph(p.getTitle(), SWPUtils.FONTS.h1);//Title
            pTitle.setAlignment(Element.ALIGN_CENTER);
            doc.add(Chunk.NEWLINE);
            doc.add(Chunk.NEWLINE);
            doc.add(Chunk.NEWLINE);
            doc.add(Chunk.NEWLINE);
            doc.add(pTitle);
            doc.newPage();
            
            //Set document header and footer
            doc.setHeader(header);
            doc.setFooter(footer);

            //Add sections
            Iterator<DocumentSectionInstance> itdsi = SWBComparator.sortSortableObject(di.listDocumentSectionInstances());
            while (itdsi.hasNext()) {
                DocumentSectionInstance dsi = itdsi.next();
                SemanticClass cls = dsi.getSecTypeDefinition() != null && dsi.getSecTypeDefinition().getSectionType() != null ? dsi.getSecTypeDefinition().getSectionType().transformToSemanticClass() : null;
                
                if (null == cls || !dsi.getSecTypeDefinition().isActive()) continue;
                
                //Add section header
                doc.add(new Paragraph(dsi.getSecTypeDefinition().getTitle(), SWPUtils.FONTS.h2));
                doc.add(Chunk.NEWLINE);
                
                //Gather sectionElement instances
                Iterator<SectionElement> itse = SWBComparator.sortSortableObject(dsi.listDocuSectionElementInstances());
                List<SectionElement> sectionElementInstances = new ArrayList<SectionElement>();
                while (itse.hasNext()) {
                    SectionElement se = itse.next();
                    sectionElementInstances.add(se);
                }
                
                if (cls.isSubClass(Instantiable.swpdoc_Instantiable, false)) {
                    //Get visible props from config
                    String [] props = dsi.getSecTypeDefinition().getVisibleProperties().split("\\|");
                    
                    //Add properties table
                    if (props.length > 0 && !sectionElementInstances.isEmpty()) {
                        Table propsTable = new Table(props.length, (sectionElementInstances.size() + 1));
                        propsTable.setPadding(15);
                        //Add table header
                        for (String propt : props) {//Header
                            String titleprop = propt.substring(0, propt.indexOf(";"));
                            Cell thead = new Cell(new Paragraph(titleprop, SWPUtils.FONTS.th));
                            thead.setHorizontalAlignment(Element.ALIGN_CENTER);
                            thead.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            thead.setGrayFill(0.9f);
                            propsTable.addCell(thead);
                        }
                        
                        //Add rows
                        for (SectionElement se : sectionElementInstances) {//Instances
                            for (String propt : props) {
                                String idProperty = propt.substring(propt.indexOf(";") + 1, propt.length());
                                SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(idProperty);
                                //String value = "es archivo";
                                Element content;
                                
                                if (prop != null && !prop.getPropId().equals(Referable.swpdoc_file.getPropId())) { //Not a reference
                                    content = new Paragraph((se.getSemanticObject().getProperty(prop) != null ? se.getSemanticObject().getProperty(prop) : ""), SWPUtils.FONTS.td);
                                    //value = (se.getSemanticObject().getProperty(prop) != null ? se.getSemanticObject().getProperty(prop) : "");
                                    //--propsTable.addCell(new Cell(value));
                                } else { //Reference
                                    Anchor anchor = new Anchor(se.getTitle(), SWPUtils.FONTS.td);
                                    Referable ref = (Referable) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(se.getURI());
                                    RepositoryElement re = (RepositoryElement) ref.getRefRepository();
                                    VersionInfo versionInfo = ref.getVersion() != null ? ref.getVersion() : re.getLastVersion();

                                    if (re instanceof RepositoryFile) {
                                        String basePathE = SWBPortal.getWorkPath() + "/models/" + p.getProcessSite().getId() + "/swp_RepositoryFile/" + ref.getRefRepository().getId() + "/" + versionInfo.getVersionNumber() + "/";
                                        File baseDir = new File(basePathE);
                                        String basePathDest = SWBPortal.getWorkPath() + "/models/" + p.getProcessSite().getId() + "/Resource/" + p.getId() + "/download/";
                                        File repFile = new File(basePathDest + "rep_files/" + ref.getRefRepository().getId() + "/" + versionInfo.getVersionNumber() + "/");
                                        if (!repFile.exists()) {
                                            repFile.mkdirs();
                                        }
                                        if (baseDir.isDirectory()) {
                                            File[] files = baseDir.listFiles();
                                            for (File file : files) {
                                                String fileName = file.getName().substring(file.getName().lastIndexOf("."));
                                                anchor.setReference("rep_Files/" + ref.getRefRepository().getId() + "/" + versionInfo.getVersionNumber() + "/" + se.getId() + fileName);
                                                copyFile(file.getAbsolutePath(), repFile.getAbsolutePath() + "/" + se.getId() + fileName);
                                                break;
                                            }
                                        }
                                    } else if (re instanceof RepositoryURL) {
                                        anchor.setReference(versionInfo.getVersionFile());
                                    }
                                    content = anchor;
                                    //--propsTable.addCell(new Cell(anchor));
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
                            doc.add(new Paragraph(a.getTitle(), SWPUtils.FONTS.h3));
                            doc.add(Chunk.NEWLINE);
                            addTextHtmlToRtf(a.getDescription(), basePath, doc, se);
                            doc.add(Chunk.NEWLINE);
                        }
                    }
                } else if (cls.equals(Model.sclass)) {
                    Image image2 = Image.getInstance(basePath + "rep_files/" + p.getId() + ".png");//Model Image
                    image2.scaleAbsolute(doc.getPageSize().getWidth()-doc.leftMargin()-doc.rightMargin(),
                            doc.getPageSize().getHeight()-doc.topMargin()-doc.bottomMargin());
                    doc.add(image2);
                }
                doc.newPage();
            }
            //Finish document
            doc.close();

            //Sections
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
            Paragraph content = new Paragraph(d1.text(), SWPUtils.FONTS.body);
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
                        image = Image.getInstance(SWBPortal.getWorkPath() + src.substring(10));//Model Image
                    } else if (src.startsWith("data:image")) {
                        int endImg = src.indexOf("base64,");
                        if (endImg > -1) {
                            endImg += 7;
                            src = src.substring(endImg, src.length());
                        }
                        //Decodificar los datos y guardarlos en un archivo
                        byte[] data = Base64.getDecoder().decode(src);
                        try (OutputStream stream = new FileOutputStream(dir.getAbsolutePath() + "/" + se.getId() + i + ".png")) {
                            stream.write(data);
                            //Insertar la imagen en el documento
                            image = Image.getInstance(dir.getAbsolutePath() + "/" + se.getId() + i + ".png");

                        } catch (IOException ioe) {
                            log.error("Error on getDocument, " + ioe.getLocalizedMessage());
                        }

                    } else {
                        SWPUtils.saveFile(src, dir.getAbsolutePath() + "/" + se.getId() + i + "." + src.substring(src.lastIndexOf(".") + 1));
                        image = Image.getInstance(dir.getAbsolutePath() + "/" + se.getId() + i + "." + src.substring(src.lastIndexOf(".") + 1));//Model Image
                    }

                    if (null != image && width != null && height != null && !width.isEmpty() && !height.isEmpty()) {
                        image.scaleToFit(doc.getPageSize().getWidth()-doc.leftMargin()-doc.rightMargin(),
                            doc.getPageSize().getHeight()-doc.topMargin()-doc.bottomMargin());
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}