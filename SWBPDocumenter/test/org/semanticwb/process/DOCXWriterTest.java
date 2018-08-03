/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process;

import com.lowagie.text.pdf.codec.Base64;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.docx4j.model.fields.merge.DataFieldName;
import org.docx4j.model.fields.merge.MailMerger;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.WordprocessingML.StyleDefinitionsPart;
import org.docx4j.wml.Br;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.docx4j.wml.STBrType;
import org.docx4j.wml.Style;
import org.docx4j.wml.Styles;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hasdai
 */
public class DOCXWriterTest {
    
    public DOCXWriterTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void runTests() {
        try {
            WordprocessingMLPackage doc = getDocument("ProcessINFOTECTemplate.docx");//WordprocessingMLPackage.createPackage();
            listDocumentStyles(doc);
            
            openSaveDocTemplate(getDocument("sampleinput.docx"));
        } catch (Docx4JException ex) {}
    }

    private WordprocessingMLPackage getDocument(String path) {
        WordprocessingMLPackage doc = null;
        if (null == path) {
            try {
                doc = WordprocessingMLPackage.createPackage();
            } catch (Docx4JException ex) {}
        } else {
            try {
                doc = WordprocessingMLPackage.load(new FileInputStream(new File(path)));
            } catch (Docx4JException | FileNotFoundException ex) {  }
        }
        
        return doc;
    }
    
    private void addPageBreak(WordprocessingMLPackage doc) {
        try {
            ObjectFactory objectFactory = new ObjectFactory();
            MainDocumentPart mainPart = doc.getMainDocumentPart();
            R r = objectFactory.createR();
            
            Br breakObj = objectFactory.createBr();
            breakObj.setType(STBrType.PAGE);

            r.getContent().add(breakObj);
            
            P paragraph = objectFactory.createP();
            paragraph.getContent().add(r);
            mainPart.getContents().getBody().getContent().add(paragraph);
            
        } catch (Docx4JException d4jex) {
            
        }
    }
    
    private void listDocumentStyles(WordprocessingMLPackage doc) throws Docx4JException {
        //WordprocessingMLPackage doc = WordprocessingMLPackage.createPackage();//openDocument("sampleinput_fields.docx");
        MainDocumentPart mdp = doc.getMainDocumentPart();
        
        StyleDefinitionsPart styleDef = mdp.getStyleDefinitionsPart();
        Map<String, Style> styles = styleDef.getKnownStyles();
        
        Styles _styles = styleDef.getContents();
        
        System.out.println("---Document known styles---");
        for(String name : styles.keySet()) {
            System.out.println("Name: "+name+" "+styles.get(name).isDefault());
        }
        
        System.out.println("---Styles content---");
        List<Style> st = _styles.getStyle();
        for (Style s : st) {
            System.out.println("name: "+s.getName().getVal()+" id: "+s.getStyleId()+" "+s.isDefault());
        }
    }
    
    private void replaceFieldsDocTemplate(WordprocessingMLPackage doc) {
        //WordprocessingMLPackage doc = getDocument("sampleinput_fields.docx");
        List<Map<DataFieldName, String>> data = new ArrayList<Map<DataFieldName, String>>();
        
        Map<DataFieldName, String> map = new HashMap<DataFieldName, String>();
        map.put( new DataFieldName("processName"), "IDEF");
        map.put( new DataFieldName("processGroup"), "II.01.01.01");
        map.put( new DataFieldName("currentDate"), "2016-05-12");
        data.add(map);
        
        MailMerger.setMERGEFIELDInOutput(MailMerger.OutputField.DEFAULT);
        
        try {
            WordprocessingMLPackage result = MailMerger.getConsolidatedResultCrude(doc, data, true);
            MainDocumentPart mdp = result.getMainDocumentPart();
            
            //addPageBreak(doc);
            mdp.addParagraphOfText("hello world");
            
            result.save(new File("sampleoutput_fields.docx"));
        } catch (Docx4JException ex) {  }
    }
    
    private void openSaveDocTemplate(WordprocessingMLPackage doc) {
        try {
            ObjectFactory objectFactory = new ObjectFactory();
            MainDocumentPart mainPart = doc.getMainDocumentPart();
            
            R r = objectFactory.createR();
            
            Br breakObj = objectFactory.createBr();
            breakObj.setType(STBrType.PAGE);

            r.getContent().add(breakObj);
            
            P paragraph = objectFactory.createP();
            paragraph.getContent().add(r);
            mainPart.getContents().getBody().getContent().add(paragraph);
            
            mainPart.addStyledParagraphOfText("Heading1","hello world");
            
            doc.save(new File("sampleoutput.docx"));
        } catch (Docx4JException d4jex) {
            
        }
    }
}
