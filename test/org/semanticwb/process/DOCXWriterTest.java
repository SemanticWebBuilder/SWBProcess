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
import org.docx4j.wml.Br;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.docx4j.wml.STBrType;
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

    private WordprocessingMLPackage openDocument(String path) {
        WordprocessingMLPackage doc = null;
        try {
            doc = WordprocessingMLPackage.load(new FileInputStream(new File(path)));
        } catch (Docx4JException | FileNotFoundException ex) {  }
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
    
    @Test
    public void replaceFieldsDocTemplate() {
        WordprocessingMLPackage doc = openDocument("/Users/hasdai/Documents/GENERADO/sampleinput_fields.docx");
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
            
            result.save(new File("/Users/hasdai/Documents/GENERADO/sampleoutput_fields.docx"));
        } catch (Docx4JException ex) {  }
    }
    
    //@Test
    public void openSaveDocTemplate() {
        try {
            ObjectFactory objectFactory = new ObjectFactory();
            WordprocessingMLPackage doc = openDocument("/Users/hasdai/Documents/GENERADO/sampleinput.docx");
            //System.out.println("------");
            //System.out.println(doc.getMainDocumentPart().getXML());
            //System.out.println("------");
            MainDocumentPart mainPart = doc.getMainDocumentPart();
            R r = objectFactory.createR();
            
            Br breakObj = objectFactory.createBr();
            breakObj.setType(STBrType.PAGE);

            r.getContent().add(breakObj);
            
            P paragraph = objectFactory.createP();
            paragraph.getContent().add(r);
            mainPart.getContents().getBody().getContent().add(paragraph);
            
            mainPart.addParagraphOfText("hello world");
            
            doc.save(new File("/Users/hasdai/Documents/GENERADO/sampleoutput.docx"));
        } catch (Docx4JException d4jex) {
            
        }
    }
}
