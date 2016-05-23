package org.semanticwb.process.documentation.writers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import org.docx4j.wml.Tbl;
import org.docx4j.wml.TblPr;
import org.docx4j.wml.TblWidth;
import org.docx4j.wml.Tc;
import org.docx4j.wml.TcPr;
import org.docx4j.wml.Text;
import org.docx4j.wml.Tr;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBComparator;
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
import org.semanticwb.process.model.ProcessSite;

/**
 *
 * @author hasdai
 */
public class DOCXWriter implements DocumentWriter {
    private static final Logger log = SWBUtils.getLogger(DOCXWriter.class);
    private static final ObjectFactory objectFactory = new ObjectFactory();
    private final DocumentationInstance di;
    private final org.semanticwb.process.model.Process p;
    private final ProcessSite model;
    private final String assetsPath;
    private final Map config;
    private final HashMap<String, String> fieldValues;
    
    /**
     * Creates a new DOCXWriter for the specified DocumentationInstance object.
     * @param di DocumentationInstance object
     * @param assetsPath Path to additional assets required in docx generation.
     */
    public DOCXWriter(DocumentationInstance di, String assetsPath) {
//        this.di = di;
//        this.p = di.getProcessRef();
//        this.model = p.getProcessSite();
//        this.assetsPath = assetsPath;
//        this.config = new HashMap<String, String>();
        this(di, assetsPath, null, null);
    }
    
    /**
     * Creates a new DOCXWriter for the specified DocumentationInstance object.
     * @param di DocumentationInstance object
     * @param assetsPath Path to additional assets required in docx generation.
     * @param configParam Configuration parameters.
     */
    public DOCXWriter(DocumentationInstance di, String assetsPath, Map configParams) {
        this(di, assetsPath, configParams, null);
    }
    
    /**
     * Creates a new DOCXWriter for the specified DocumentationInstance object.
     * @param di DocumentationInstance object
     * @param assetsPath Path to additional assets required in docx generation.
     * @param configParams Configuration parameters.
     */
    public DOCXWriter(DocumentationInstance di, String assetsPath, Map configParams, HashMap<String, String> fieldValues) {
        this.di = di;
        this.p = di.getProcessRef();
        this.model = p.getProcessSite();
        this.assetsPath = assetsPath;
        this.config = configParams;
        if (null != fieldValues) {
            this.fieldValues = fieldValues;
        } else {
            //Add default field value mappings
            this.fieldValues = new HashMap<String, String>();
            //System.out.println("-->processName:"+this.p.getTitle());
            this.fieldValues.put("processName", this.p.getTitle());
        }
    }
    
    /**
     * Genera un documento a partir de una plantilla y reemplaza los valores correspondientes en los campos MergeField del documento.
     * @param templatePath ruta al documento plantilla.
     * @param includeHeaderFooterindica si se procesarán MergeFields en header y footer.
     * @return WordprocessingMLPackage del documento.
     */
    private WordprocessingMLPackage writeTemplate (String templatePath, boolean includeHeaderFooter) {
        //Open template
        WordprocessingMLPackage doc = null;
        WordprocessingMLPackage result = null;
        try {
            ObjectFactory objectFactory = new ObjectFactory();
            doc = WordprocessingMLPackage.load(new FileInputStream(new File(templatePath)));
            
            //Replace mergeFields
            List<Map<DataFieldName, String>> data = new ArrayList<Map<DataFieldName, String>>();

            Map<DataFieldName, String> map = new HashMap<DataFieldName, String>();
            map.put(new DataFieldName("processName"), p.getTitle());
            map.put(new DataFieldName("processGroup"), p.getProcessGroup().getTitle());
            map.put(new DataFieldName("currentDate"), (new Date()).toString());
            data.add(map);

            MailMerger.setMERGEFIELDInOutput(MailMerger.OutputField.DEFAULT);
            result = MailMerger.getConsolidatedResultCrude(doc, data, includeHeaderFooter);
        } catch (Docx4JException | FileNotFoundException ex) { 
            log.error("Error writing template", ex);
        }
        return result;
    }
    
    @Override
    public void write(OutputStream ous) {
        WordprocessingMLPackage doc = null;
        boolean fromTemplate = false;
        
        try {
            //Get template path from config
            String tpl = null;
            if (null != config && null != config.get("template")) {
                tpl = (String) config.get("template");
            }
            
            if (null != tpl && !tpl.isEmpty()) {
                fromTemplate = true;
                doc = writeTemplate(tpl, true);
            }
            
            if (null == doc) doc = WordprocessingMLPackage.createPackage();
            MainDocumentPart content = doc.getMainDocumentPart();
            
            //Create header and footer
            if (!fromTemplate && null != config && null != config.get("includeHeaderFooter") && config.get("includeHeaderFooter").equals("true")) {
                createHeader(doc);
                createFooter(doc);
            }
            
            //Add first page
            if (!fromTemplate && null != config && null != config.get("includeFirstPage") && config.get("includeFirstPage").equals("true")) {
                P docTitle = content.addStyledParagraphOfText("Heading1", p.getTitle());
                alignParagraph(docTitle, JcEnumeration.CENTER);
            }
            
            addPageBreak(content);
            
            //Add sections
            Iterator<DocumentSectionInstance> itdsi = SWBComparator.sortSortableObject(di.listDocumentSectionInstances());
            while (itdsi.hasNext()) {
                DocumentSectionInstance dsi = itdsi.next();
                SemanticClass cls = dsi.getSecTypeDefinition() != null && dsi.getSecTypeDefinition().getSectionType() != null ? dsi.getSecTypeDefinition().getSectionType().transformToSemanticClass() : null;
                
                if (null == cls || !dsi.getSecTypeDefinition().isActive()) continue;
                
                //Add section title
                content.addStyledParagraphOfText("Heading2", dsi.getSecTypeDefinition().getTitle());
                
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
                        int writableWidthTwips = doc.getDocumentModel().getSections().get(0).getPageDimensions().getWritableWidthTwips();
                        int cellWidthTwips = new Double(Math.floor((writableWidthTwips/props.length))).intValue();
                        
                        Tbl propsTable = TblFactory.createTable(sectionElementInstances.size()+1, props.length, cellWidthTwips);
                        setStyle(propsTable, "TableGrid");
                        
                        //Add table header
                        Tr headerRow = (Tr) propsTable.getContent().get(0);
                        int c = 0;
                        for (String prop: props) {
                            Tc col = (Tc) headerRow.getContent().get(c++);
                            P colContent = objectFactory.createP();//(P) col.getContent().get(0);
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
                        
                        //Add rows
                        int r = 1;
                        for (SectionElement se : sectionElementInstances) {
                            Tr row = (Tr)propsTable.getContent().get(r++);
                            c = 0;
                            for (String prop : props) {
                                Tc col = (Tc)row.getContent().get(c++);
                                String idProperty = prop.substring(prop.indexOf(";") + 1, prop.length());
                                SemanticProperty sprop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(idProperty);
                                P colContent;
                                
                                if (null == sprop) {
                                    colContent = content.createParagraphOfText("");  
                                } else {
                                    if (!sprop.getPropId().equals(Referable.swpdoc_file.getPropId())) {
                                        colContent = content.createParagraphOfText(se.getSemanticObject().getProperty(sprop) != null ? se.getSemanticObject().getProperty(sprop) : "");
                                    } else {
                                        colContent = content.createParagraphOfText(se.getTitle());
                                    }
                                }
                                setStyle(colContent, "Normal");
                                alignParagraph(colContent, JcEnumeration.BOTH);
                                col.getContent().set(0, colContent);
                            }
                        }
                        
                        //Add table to document
                        content.addObject(propsTable);   
                    }
                } else if (cls.equals(FreeText.sclass)) {
                    XHTMLImporterImpl importer = new XHTMLImporterImpl(doc);
                    for (SectionElement se : sectionElementInstances) {
                        FreeText freeText = (FreeText) se;
                        if (null != se) {
                            String sContent = freeText.getText();
                            if (null != sContent && !sContent.isEmpty()) {
                                sContent = sContent.replace("<!DOCTYPE html>","<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n");
                                sContent = sContent.replace("<html>","<html xmlns=\"http://www.w3.org/1999/xhtml\">");
                                
                                //Override styles and alignment
                                List<Object> objects = importer.convert(sContent,null);
                                for (Object o : objects) {
                                    if (o instanceof Tbl) {
                                        setStyle((Tbl)o, "TableGrid");
                                        
                                        //Set table header background
                                        /*if (null != config.get("forceTableStyles") && "true".equals(config.get("forceTableStyles"))) {
                                            Tbl table = (Tbl)o;
                                            Iterator<Object> rows = table.getContent().iterator();
                                            Tr firstRow = null;
                                            while (rows.hasNext()) {
                                                Object next = rows.next();
                                                if (next instanceof Tr) {
                                                    firstRow = (Tr) next;
                                                    break;
                                                }
                                            }
                                            if (null != firstRow) {
                                                Iterator<Object> cells = firstRow.getContent().iterator();
                                                while (cells.hasNext()) {
                                                    Object next = cells.next();
                                                    fillTableCell((Tc)next, "#F2F2F2");
                                                }
                                            }
                                        }*/
                                    }
                                    if (o instanceof P) {
                                        //Fix harcoded runProperties
                                        List<Object> pChilds = ((P)o).getContent();
                                        for (Object child: pChilds) {
                                            if (child instanceof R) {
                                                //((R)child).setRPr(objectFactory.createRPr());
                                                RPr rpr = ((R)child).getRPr();
                                                if (null != rpr) {
                                                    rpr.getRFonts().setAsciiTheme(null);
                                                    rpr.getRFonts().setAscii(null);
                                                    rpr.getRFonts().setHAnsiTheme(null);
                                                    rpr.getRFonts().setHAnsi(null);
                                                }
                                            }
                                        }
                                        alignParagraph((P)o, JcEnumeration.BOTH);
                                        setStyle((P)o, "Normal");
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
                            XHTMLImporterImpl importer = new XHTMLImporterImpl(doc);
                            content.addStyledParagraphOfText("Heading3", a.getTitle());
                            
                            String sContent = a.getDescription();
                            if (null != sContent && !sContent.isEmpty()) {
                                sContent = sContent.replace("<!DOCTYPE html>","<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n");
                                sContent = sContent.replace("<html>","<html xmlns=\"http://www.w3.org/1999/xhtml\">");
                                
                                //Override styles and alignment
                                List<Object> objects = importer.convert(sContent,null);
                                for (Object o : objects) {
                                    if (o instanceof Tbl) setStyle((Tbl)o, "TableGrid");
                                    if (o instanceof P) {
                                        //Fix harcoded runProperties
                                        List<Object> pChilds = ((P)o).getContent();
                                        for (Object child: pChilds) {
                                            if (child instanceof R) {
                                                //((R)child).setRPr(null);
                                                RPr rpr = ((R)child).getRPr();
                                                if (null != rpr) {
                                                    rpr.getRFonts().setAsciiTheme(null);
                                                    rpr.getRFonts().setAscii(null);
                                                    rpr.getRFonts().setHAnsiTheme(null);
                                                    rpr.getRFonts().setHAnsi(null);
                                                }
                                            }
                                        }
                                        alignParagraph((P)o, JcEnumeration.BOTH);
                                        setStyle((P)o, "Normal");
                                    }
                                }
                                content.getContent().addAll(objects);
                            }
                        }
                    }
                } else if (cls.equals(Model.sclass)) {
                    File img = new File(assetsPath+"/"+p.getId()+".png");
                    if (img.exists()) {
                        FileInputStream fis = new FileInputStream(img);
                        long length = img.length();
                        
                        if (length > Integer.MAX_VALUE) {
                            log.error("File too large in model generation");
                        } else {
                            //Read image bytes
                            byte[] bytes = new byte[(int)length];
                            int offset = 0;
                            int numRead = 0;
                            while (offset < bytes.length && (numRead=fis.read(bytes, offset, bytes.length-offset)) >= 0) {
                                offset += numRead;
                            }
                            
                            if (offset < bytes.length) {
                                log.error("Could not completely read file "+img.getName());
                            }
                            
                            fis.close();
                            
                            //Generate ImagePart
                            BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(doc, bytes);
                            Inline inline = imagePart.createImageInline("","", 0, 1, false);
        
                            //Add image to paragraph
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
                addPageBreak(content);
            }
            doc.save(ous);
        } catch (Docx4JException | FileNotFoundException ex) {
            log.error("Error creating DOCX document", ex);
        } catch (IOException ex) {
            log.error("Error creating DOCX document", ex);
        } catch (Exception ex) {
            log.error("Error creating DOCX document", ex);
        }
    }
    
    /**
     * Sets style of paragraph
     * @param paragraph Paragraph to style
     * @param styleName Style name
     */
    private void setStyle(ContentAccessor element, String styleName) {
        if (element instanceof P) {
            PPrBase.PStyle style = objectFactory.createPPrBasePStyle();
            style.setVal(styleName);
            
            PPr ppr = objectFactory.createPPr();
            ppr.setPStyle(style);
            ((P)element).setPPr(ppr);
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
            ((Tbl)element).setTblPr(tpr);
        }
    }
    
    /**
     * Fija la alineación de un párrafo
     * @param paragraph Párrafo a alinear
     * @param alignment Valor de alineamiento
     */
        private void alignParagraph(P paragraph, JcEnumeration alignment) {
        PPr parProps = paragraph.getPPr();
        if (null == parProps) parProps = objectFactory.createPPr();
        Jc al = objectFactory.createJc();
        al.setVal(alignment);
        parProps.setJc(al);
        
        paragraph.setPPr(parProps);
    }
    
    /**
     * Sets font style (bold/italic) for a paragraph run
     * @param runElement Run
     * @param italic wheter to set italic style
     * @param bold wheter to set bold style
     */
    private void setFontStyle(R runElement, boolean italic, boolean bold) {
        RPr runProps = runElement.getRPr();
        if (null == runProps) runProps = objectFactory.createRPr();
        if (italic) runProps.setI(objectFactory.createBooleanDefaultTrue());
        if (bold) runProps.setB(objectFactory.createBooleanDefaultTrue());
        
        runElement.setRPr(runProps);
    }
    
    /**
     * Fills table cell with given hex color
     * @param cell
     * @param hexColor 
     */
    private void fillTableCell(Tc cell, String hexColor) {
        TcPr cellProps = cell.getTcPr();
        if (null == cellProps) cellProps = objectFactory.createTcPr();
        CTShd shd = objectFactory.createCTShd();
        shd.setFill(hexColor);
        shd.setThemeFill(STThemeColor.BACKGROUND_2);
        cellProps.setShd(shd);
        cell.setTcPr(cellProps);
    }
    
    /**
     * Adds page break to document
     * @param content Document's main part
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
     * @param doc WordprocessingMLPackage for the document.
     * @throws InvalidFormatException 
     */
    private void createFooter(WordprocessingMLPackage doc) throws InvalidFormatException {
        MainDocumentPart content = doc.getMainDocumentPart();
        
        //Create footer
        FooterPart footer = new FooterPart();
        
        Ftr ftr = objectFactory.createFtr();
        P footerParagraph = objectFactory.createP();
        
        setStyle(footerParagraph, "footer");
        
        PPr parProps = objectFactory.createPPr();
        Jc al = objectFactory.createJc();
        al.setVal(JcEnumeration.RIGHT);
        parProps.setJc(al);
        footerParagraph.setPPr(parProps);
        
        //Add field start
        R run = objectFactory.createR();
        FldChar fldChar = objectFactory.createFldChar();
        fldChar.setFldCharType(STFldCharType.BEGIN);
        run.getContent().add(fldChar);
        footerParagraph.getContent().add(run);
        
        //Add pageNumber field
        run = objectFactory.createR();
        Text txt = objectFactory.createText();
        txt.setSpace("preserve");
        txt.setValue(" PAGE   \\* MERGEFORMAT ");
        run.getContent().add(objectFactory.createRInstrText(txt));
        footerParagraph.getContent().add(run);
        
        //Add field end
        run = objectFactory.createR();
        fldChar = objectFactory.createFldChar();
        fldChar.setFldCharType(STFldCharType.END);
        run.getContent().add(fldChar);
        footerParagraph.getContent().add(run);
        
        ftr.getContent().add(footerParagraph);
        footer.setJaxbElement(ftr);
        
        Relationship rel = content.addTargetPart(footer);
        
        //Relate footer to document
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
     * @param doc WordprocessingMLPackage for the document.
     * @throws InvalidFormatException 
     */
    private void createHeader (WordprocessingMLPackage doc) throws InvalidFormatException {
        MainDocumentPart content = doc.getMainDocumentPart();
        
        //Create header
        HeaderPart header = new HeaderPart();
        Relationship rel = content.addTargetPart(header);
        
        Hdr hdr = objectFactory.createHdr();
        
        P headerParagraph = content.createParagraphOfText(p.getTitle());
        hdr.getContent().add(headerParagraph);
        header.setJaxbElement(hdr);
        
        setStyle(headerParagraph, "Header");
        alignParagraph(headerParagraph, JcEnumeration.CENTER);
        
        //Relate to document
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
    
    @Override
    public void write(String path) throws FileNotFoundException {
        write(new FileOutputStream(path));
    }
}
