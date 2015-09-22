/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.documentation.resources.utils;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.log4j.Level;
import org.jsoup.Jsoup;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Descriptiveable;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.User;
import org.semanticwb.model.VersionInfo;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceModes;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBResourceURLImp;
import org.semanticwb.process.documentation.model.Activity;
import org.semanticwb.process.documentation.model.DocumentSection;
import org.semanticwb.process.documentation.model.DocumentSectionInstance;
import org.semanticwb.process.documentation.model.DocumentTemplate;
import org.semanticwb.process.documentation.model.DocumentationInstance;
import org.semanticwb.process.documentation.model.ElementReference;
import org.semanticwb.process.documentation.model.FreeText;
import org.semanticwb.process.documentation.model.Instantiable;
import org.semanticwb.process.documentation.model.Model;
import org.semanticwb.process.documentation.model.Referable;
import org.semanticwb.process.documentation.model.SectionElement;
import org.semanticwb.process.documentation.model.SectionElementRef;
import org.semanticwb.process.documentation.model.TemplateContainer;
import org.semanticwb.process.model.ProcessElement;
import org.semanticwb.process.model.ProcessGroup;
import org.semanticwb.process.model.RepositoryDirectory;
import org.semanticwb.process.model.RepositoryElement;
import org.semanticwb.process.model.RepositoryFile;
import org.semanticwb.process.model.RepositoryURL;
import org.semanticwb.process.model.SubProcess;
import org.semanticwb.process.resources.ProcessFileRepository;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author carlos.alvarez
 */
public class SWPUtils {

    public final static String PARAM_REQUEST = "paramRequest";//Bean paramRequest
    public final static String LIST_TEMPLATES_CONTAINER = "listContainers";//Listar instancias de objeto DocumentTemplate
    public final static String LIST_PROCESSES = "listTemplates";//Listar instancias de objeto Process
    public final static String FORMAT_PNG = "png";
    public final static String FORMAT_SVG = "svg";
    public final static String FORMAT_HTML = "html";
    public final static String FORMAT_WORD = "word";
    private final static Logger log = SWBUtils.getLogger(SWPUtils.class);

    static public List<TemplateContainer> listTemplateContainers(HttpServletRequest request, SWBParamRequest paramRequest) {
        ArrayList<TemplateContainer> unpaged = new ArrayList<TemplateContainer>();
        WebSite model = paramRequest.getWebPage().getWebSite();
        String lang = "es";
        User user = paramRequest.getUser();
        if (user != null && user.getLanguage() != null) {
            lang = user.getLanguage();
        }
        int page = 1;
        int itemsPerPage = 10;
        Iterator<TemplateContainer> tplContainers_it = TemplateContainer.ClassMgr.listTemplateContainers(model);
        if (tplContainers_it != null && tplContainers_it.hasNext()) {
            Iterator<TemplateContainer> it = SWBComparator.sortByDisplayName(tplContainers_it, lang);
            while (it.hasNext()) {
                TemplateContainer dt = it.next();
                unpaged.add(dt);
            }
        }
        //Realizar paginado de instancias
        int maxPages = 1;
        if (request.getParameter("p") != null && !request.getParameter("p").trim().equals("")) {
            page = Integer.valueOf(request.getParameter("p"));
            if (page < 0) {
                page = 1;
            }
        }
        if (itemsPerPage < 10) {
            itemsPerPage = 10;
        }
        if (unpaged.size() >= itemsPerPage) {
            maxPages = (int) Math.ceil((double) unpaged.size() / itemsPerPage);
        }
        if (page > maxPages) {
            page = maxPages;
        }
        int sIndex = (page - 1) * itemsPerPage;
        if (unpaged.size() > itemsPerPage && sIndex > unpaged.size() - 1) {
            sIndex = unpaged.size() - itemsPerPage;
        }
        int eIndex = sIndex + itemsPerPage;
        if (eIndex >= unpaged.size()) {
            eIndex = unpaged.size();
        }
        request.setAttribute("maxPages", maxPages);
        ArrayList<TemplateContainer> ret = new ArrayList<TemplateContainer>();
        for (int i = sIndex; i < eIndex; i++) {
            TemplateContainer dt = unpaged.get(i);
            ret.add(dt);
        }
        return ret;
    }

    static public List<org.semanticwb.process.model.Process> listProcessesByTemplate(HttpServletRequest request, SWBParamRequest paramRequest) {
        WebSite model = paramRequest.getWebPage().getWebSite();
        String uritc = request.getParameter("uritc") != null ? request.getParameter("uritc") : "";
        TemplateContainer tc = (TemplateContainer) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uritc);
        TemplateContainer tctemp = null;
        List<org.semanticwb.process.model.Process> list = new ArrayList<org.semanticwb.process.model.Process>();
        Iterator<org.semanticwb.process.model.Process> iterator = org.semanticwb.process.model.Process.ClassMgr.listProcesses(model);
        while (iterator.hasNext()) {//Filtrado de procesos por algún criterio
            org.semanticwb.process.model.Process process = iterator.next();
            Iterator<TemplateContainer> templates = TemplateContainer.ClassMgr.listTemplateContainerByProcess(process, model);
            if (templates.hasNext()) {
                tctemp = templates.next();
            }
            if (process.isDeleted()
                    || (tctemp != null && tc != null && !tc.getURI().equals(tctemp.getURI()))
                    || (tc == null && tctemp != null)) {
                tctemp = null;
                continue;
            }
            list.add(process);
        }
        return list;
    }

    static public List<org.semanticwb.process.model.Process> listProcesses(HttpServletRequest request, SWBParamRequest paramRequest) {
        List<org.semanticwb.process.model.Process> list = new ArrayList<org.semanticwb.process.model.Process>();
        WebSite model = paramRequest.getWebPage().getWebSite();
        String idpg = request.getParameter("idpg") != null ? request.getParameter("idpg") : "";

        if (!idpg.isEmpty()) {
            ProcessGroup group = ProcessGroup.ClassMgr.getProcessGroup(idpg, model);//(ProcessGroup) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(idpg);
            if (null != group) {
                Iterator<org.semanticwb.process.model.Process> iterator = group.listProcesses();//org.semanticwb.process.model.Process.ClassMgr.listProcesses(model);
                while (iterator.hasNext()) {
                    org.semanticwb.process.model.Process process = iterator.next();
                    /*if (process.isDeleted() || !process.isActive()) {
                     continue;
                     }*/
                    if (process.isValid()) {
                        list.add(process);
                    }
                }
            }
        }
        list = SWBUtils.Collections.copyIterator(SWBComparator.sortByDisplayName(list.iterator(), paramRequest.getUser().getLanguage()));
        return list;
    }

    static public List<SectionElement> listSectionElementByTemplate(DocumentTemplate dt, SemanticClass sc) {
        List<SectionElement> list = new ArrayList<SectionElement>();
        Iterator<SemanticObject> it = sc.listInstances();
        while (it.hasNext()) {
            SemanticObject so = it.next();
            SectionElement se = (SectionElement) so.createGenericInstance();
            if (se.getDocumentTemplate().getURI().equals(dt.getURI())) {
                list.add(se);
            }
        }
        return list;
    }

    static public Document getDocument(DocumentationInstance di, HttpServletRequest request, boolean export) {
        Document doc = SWBUtils.XML.getNewDocument();
        org.semanticwb.process.model.Process p = di.getProcessRef();
        Element root = doc.createElement("root");
        root.setAttribute("title", p.getTitle());
        root.setAttribute("uri", p.getURI());
        doc.appendChild(root);
        String colorTask = "";
        boolean hasModel = false;
        try {
            Iterator<DocumentSectionInstance> itdsi = SWBComparator.sortSortableObject(di.listDocumentSectionInstances());
            while (itdsi.hasNext()) {//Sections
                DocumentSectionInstance dsi = itdsi.next();
                if (!dsi.getSecTypeDefinition().isActive()) {
                    continue;
                }
                SemanticClass cls = dsi.getSecTypeDefinition() != null && dsi.getSecTypeDefinition().getSectionType() != null ? dsi.getSecTypeDefinition().getSectionType().transformToSemanticClass() : null;

                if (cls != null) {
                    if (cls.equals(Model.sclass)) {//Model
                        hasModel = true;
                        continue;
                    }
                    root.appendChild(doc.createTextNode("\n\t"));
                    Element section = doc.createElement("section");
                    root.appendChild(section);
                    section.setAttribute("className", cls.getName());
                    section.setAttribute("title", dsi.getSecTypeDefinition().getTitle());
                    section.setAttribute("uri", dsi.getURI());
                    section.setAttribute("url", cls.getName() + dsi.getId());

                    Iterator<SectionElement> itse = SWBComparator.sortSortableObject(dsi.listDocuSectionElementInstances());
                    int count = 1;
                    while (itse.hasNext()) {//Instances
                        section.appendChild(doc.createTextNode("\n\t\t"));
                        SectionElement se = itse.next();
                        Element instance = doc.createElement("instance");
                        section.appendChild(instance);
                        instance.setAttribute("id", se.getId());
                        instance.setAttribute("uri", se.getURI());
                        instance.setAttribute("className", cls.getName());
                        instance.setAttribute("count", count + "");
                        count++;
                        if (cls.isSubClass(Instantiable.swpdoc_Instantiable, false)) {//Elements Instantiable
                            String[] props = dsi.getSecTypeDefinition().getVisibleProperties().split("\\|");
                            for (String propt : props) {
                                String idprop = propt.substring(propt.indexOf(";") + 1, propt.length());
                                String titleprop = propt.substring(0, propt.indexOf(";"));
                                SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(idprop);
                                String value = "";
                                Element property = doc.createElement("property");
                                instance.appendChild(property);
                                property.setAttribute("title", titleprop);
                                property.setAttribute("propid", idprop);
                                if (prop != null && !prop.getPropId().equals(Referable.swpdoc_file.getPropId())) {
                                    value = (se.getSemanticObject().getProperty(prop) != null ? se.getSemanticObject().getProperty(prop) : "");
                                } else {//Show URL download file
                                    if (se instanceof ElementReference) {
                                        ElementReference er = (ElementReference) se;
                                        if (er.getElementRef() == null) {
                                            dsi.removeDocuSectionElementInstance(er);
                                            er.remove();
                                            continue;
                                        }
                                        se = (SectionElement) er.getElementRef();
                                    }
                                    Referable ref = (Referable) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(se.getURI());
                                    RepositoryDirectory rd = ref.getRefRepository().getRepositoryDirectory();
                                    SWBResourceURL urld = new SWBResourceURLImp(request, rd.getResource(), rd, SWBResourceModes.UrlType_RENDER);

                                    RepositoryElement re = (RepositoryElement) ref.getRefRepository();
                                    VersionInfo vi = ref.getVersion() != null ? ref.getVersion() : re.getLastVersion();
                                    urld.setMode(ProcessFileRepository.MODE_GETFILE).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("fid", ref.getRefRepository().getId());
                                    urld.setParameter("verNum", vi.getVersionNumber() + "");
                                    String urlDownload = urld.toString();
                                    if (export) { // add file to zip
                                        String basePath = SWBPortal.getWorkPath() + "/models/" + p.getProcessSite().getId() + "/swp_RepositoryFile/" + ref.getRefRepository().getId() + "/" + vi.getVersionNumber() + "/";
                                        File baseDir = new File(basePath);
                                        String basePathDest = SWBPortal.getWorkPath() + "/models/" + p.getProcessSite().getId() + "/Resource/" + p.getId() + "/download/";
                                        File repFile = new File(basePathDest + "rep_files/" + ref.getRefRepository().getId() + "/" + vi.getVersionNumber() + "/");
                                        if (!repFile.exists()) {
                                            repFile.mkdirs();
                                        }
                                        if (baseDir.isDirectory()) {
                                            File[] files = baseDir.listFiles();
                                            for (File file : files) {
                                                urlDownload = "rep_files/" + ref.getRefRepository().getId() + "/" + vi.getVersionNumber() + "/" + file.getName();
                                                copyFile(file.getAbsolutePath(), repFile.getAbsolutePath() + "/" + file.getName());
                                                break;
                                            }
                                        }
                                    }
                                    if (re instanceof RepositoryFile) {
                                        value = "<a target=\"_blank\" href=\"" + urlDownload + "\">" + ref.getRefRepository().getTitle() + " <i class=\"fa fa-download\"></i></a>";
                                    } else if (re instanceof RepositoryURL) {
                                        value = "<a target=\"_blank\" href=\"" + vi.getVersionFile() + "\">" + ref.getRefRepository().getTitle() + " <i class=\"fa fa-external-link\"></i></a>";
                                    }

                                }
                                property.appendChild(doc.createTextNode(value));
                            }

                        } else if (cls.equals(FreeText.sclass)) {//FreeText
                            //Validar el export
                            FreeText ft = (FreeText) se;
                            String html = ft.getText().replace("&ldquo;", "&quot;");
                            html = html.replace("&rdquo;", "&quot;");
                            html = html.replace("&ndash;", "-");
                            org.jsoup.nodes.Document d = null;
                            if (html != null) {
                                d = Jsoup.parse(html);
                                Elements elements = d.select("[src]");
                                for (org.jsoup.nodes.Element src : elements) {
                                    if (src.tagName().equals("img") || src.tagName().equals("iframe")) {
                                        String attr = src.attr("src");
                                        if (attr.indexOf("../..") > -1) {
                                            src.attr("src", src.attr("src").substring(5));
                                        }
                                        if (export && attr.indexOf("http") < 0) {
                                            File file = new File(SWBPortal.getWorkPath() + "/" + src.attr("src").substring(5));
                                            String basePathDest = SWBPortal.getWorkPath() + "/models/" + p.getProcessSite().getId() + "/Resource/" + p.getId() + "/download/";
                                            File repFile = new File(basePathDest + "rep_files/" + se.getId() + "/");
                                            if (!repFile.exists()) {
                                                repFile.mkdirs();
                                            }
                                            copyFile(file.getAbsolutePath(), repFile.getAbsolutePath() + "/" + file.getName());
                                            src.attr("src", "rep_files/" + se.getId() + "/" + file.getName());
                                        }
                                    }
                                }
                            }
                            instance.appendChild(doc.createTextNode((d != null ? d.html() : "")));
                        } else if (cls.equals(Activity.sclass)) {//Activity
                            Activity a = (Activity) se;
                            Element property = doc.createElement("property");
                            instance.appendChild(property);
                            property.setAttribute("title", Descriptiveable.swb_title.getLabel());
                            property.setAttribute("propid", Descriptiveable.swb_title.getPropId());
                            property.appendChild(doc.createTextNode(a.getTitle() != null ? a.getTitle() : ""));

                            Element propertyd = doc.createElement("property");
                            instance.appendChild(propertyd);
                            propertyd.setAttribute("title", Descriptiveable.swb_description.getLabel());
                            propertyd.setAttribute("propid", Descriptiveable.swb_description.getPropId());

                            String html = a.getDescription();
                            org.jsoup.nodes.Document d = null;
                            if (html != null) {
                                d = Jsoup.parse(html);
                                Elements elements = d.select("[src]");
                                for (org.jsoup.nodes.Element src : elements) {
                                    if (src.tagName().equals("img") || src.tagName().equals("iframe")) {
                                        String attr = src.attr("src");
                                        if (attr.indexOf("../..") > -1) {
                                            src.attr("src", src.attr("src").substring(5));
                                        }
                                        if (export && attr.indexOf("http") < 0) {
                                            File file = new File(SWBPortal.getWorkPath() + "/" + src.attr("src").substring(5));
                                            String basePathDest = SWBPortal.getWorkPath() + "/models/" + p.getProcessSite().getId() + "/Resource/" + p.getId() + "/download/";
                                            File repFile = new File(basePathDest + "rep_files/" + se.getId() + "/");
                                            if (!repFile.exists()) {
                                                repFile.mkdirs();
                                            }
                                            copyFile(file.getAbsolutePath(), repFile.getAbsolutePath() + "/" + file.getName());
                                            src.attr("src", "rep_files/" + se.getId() + "/" + file.getName());
                                        }
                                    }
                                }
                            }
                            propertyd.appendChild(doc.createTextNode(d != null ? d.html() : ""));

                            instance.setAttribute("fill", a.getFill());
                            instance.setAttribute("id", a.getActivityRef().getProcessActivity().getId());

                            //Activity act = (Activity) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(se.getURI());
                            if (a.getFill() != null) {
                                if (colorTask.length() > 0) {
                                    colorTask += "|";
                                }
                                colorTask += a.getActivityRef().getProcessActivity().getURI() + ";" + a.getFill();
                            }
                            Iterator<SectionElementRef> itser = SWBComparator.sortSortableObject(a.listSectionElementRefs());
                            if (itser.hasNext()) {
                                instance.setAttribute("related", "true");
                            } else {
                                instance.setAttribute("related", "false");
                            }
                            Map mapSect = new HashMap();
                            while (itser.hasNext()) {
                                SectionElementRef ser = itser.next();
                                if(ser.getSectionElement()!= null){
                                String uris = "";
                                if (mapSect.containsKey(ser.getSectionElement().getParentSection())) {
                                    uris = mapSect.get(ser.getSectionElement().getParentSection()).toString() + "|" + ser.getSectionElement();
                                } else {
                                    uris = ser.getSectionElement().getURI();
                                }
                                mapSect.put(ser.getSectionElement().getParentSection(), uris);
                                }
                            }
                            Iterator itset = mapSect.entrySet().iterator();
                            while (itset.hasNext()) {
                                Map.Entry e = (Map.Entry) itset.next();
                                Element eds = doc.createElement("documentSection");
                                instance.appendChild(eds);
                                eds.setAttribute("uri", e.getKey().toString());
                                DocumentSection ds = (DocumentSection) e.getKey();
                                String[] props = ds.getVisibleProperties().split("\\|");
                                eds.setAttribute("title", ds.getTitle());
                                eds.setAttribute("url", "related" + ds.getId() + "act" + a.getId());

                                String[] uris = e.getValue().toString().split("\\|");
                                int i = 0;
                                for (String uri : uris) {
                                    Element related = doc.createElement("related");
                                    related.setAttribute("count", i + "");
                                    i++;
                                    SemanticClass scls = ds.getSectionType().transformToSemanticClass();
                                    eds.appendChild(related);
                                    related.setAttribute("uri", uri);
                                    related.setAttribute("className", scls.getName());
                                    SectionElement ser = (SectionElement) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uri);
                                    SemanticObject so = SemanticObject.createSemanticObject(uri);
                                    if (so != null) {
                                        for (String propt : props) {
                                            String idprop = propt.substring(propt.indexOf(";") + 1, propt.length());
                                            String titleprop = propt.substring(0, propt.indexOf(";"));
                                            SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(idprop);
                                            Element erprop = doc.createElement("relatedprop");
                                            related.appendChild(erprop);
                                            erprop.setAttribute("title", titleprop);
                                            erprop.setAttribute("propid", idprop);
                                            String value = "";
                                            if (prop != null && !prop.getPropId().equals(Referable.swpdoc_file.getPropId())) {
                                                value = ser.getSemanticObject().getProperty(prop) != null ? ser.getSemanticObject().getProperty(prop) : "";
                                            } else {//Show URL download file
                                                if (ser instanceof ElementReference) {
                                                    ElementReference er = (ElementReference) ser;
                                                    if (er.getElementRef() == null) {
                                                        dsi.removeDocuSectionElementInstance(er);
                                                        er.remove();
                                                        continue;
                                                    }
                                                    ser = (SectionElement) er.getElementRef();
                                                }
                                                Referable ref = (Referable) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(ser.getURI());
                                                RepositoryDirectory rd = ref.getRefRepository().getRepositoryDirectory();
                                                SWBResourceURL urld = new SWBResourceURLImp(request, rd.getResource(), rd, SWBResourceModes.UrlType_RENDER);
                                                urld.setMode(ProcessFileRepository.MODE_GETFILE).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("fid", ref.getRefRepository().getId());
                                                RepositoryElement re = (RepositoryElement) ref.getRefRepository();
                                                VersionInfo vi = ref.getVersion() != null ? ref.getVersion() : re.getLastVersion();
                                                urld.setParameter("verNum", vi.getVersionNumber() + "");

                                                String urlDownload = urld.toString();
                                                if (export) { // add file to zip
                                                    String basePath = SWBPortal.getWorkPath() + "/models/" + p.getProcessSite().getId() + "/swp_RepositoryFile/" + ref.getRefRepository().getId() + "/" + vi.getVersionNumber() + "/";
                                                    File baseDir = new File(basePath);
                                                    String basePathDest = SWBPortal.getWorkPath() + "/models/" + p.getProcessSite().getId() + "/Resource/" + p.getId() + "/download/";
                                                    File repFile = new File(basePathDest + "rep_files/" + ref.getRefRepository().getId() + "/" + vi.getVersionNumber() + "/");
                                                    if (!repFile.exists()) {
                                                        repFile.mkdirs();
                                                    }
                                                    if (baseDir.isDirectory()) {
                                                        File[] files = baseDir.listFiles();
                                                        for (File file : files) {
                                                            urlDownload = "rep_files/" + ref.getRefRepository().getId() + "/" + vi.getVersionNumber() + "/" + file.getName();
                                                            copyFile(file.getAbsolutePath(), repFile.getAbsolutePath() + "/" + file.getName());
                                                            break;
                                                        }
                                                    }
                                                }
                                                if (re instanceof RepositoryFile) {
                                                    value = "<a target=\"_blank\" href=\"" + urlDownload + "\">" + ref.getRefRepository().getTitle() + " <i class=\"fa fa-download\"></i></a>";
                                                } else if (re instanceof RepositoryURL) {
                                                    value = "<a target=\"_blank\" href=\"" + vi.getVersionFile() + "\">" + ref.getRefRepository().getTitle() + " <i class=\"fa fa-external-link\"></i></a>";
                                                }
//                                                if (re instanceof RepositoryFile) {
//                                                    value = "<a href=\"" + urld + "\">" + ref.getRefRepository().getTitle() + " <i class=\"fa fa-download\"></i></a>";
//                                                } else if (re instanceof RepositoryURL) {
//                                                    value = "<a target=\"_blank\" href=\"" + vi.getVersionFile() + "\">" + ref.getRefRepository().getTitle() + " <i class=\"fa fa-external-link\"></i></a>";
//                                                }
                                            }
                                            erprop.appendChild(doc.createTextNode(value));
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
            }
            if (hasModel) {
                org.semanticwb.process.model.Process process = di.getProcessRef();
                Element model = doc.createElement("model");
                root.appendChild(doc.createTextNode("\n\t"));
//                String mode = paramRequest.getMode();
                String data = process.getData();
//                System.out.println("\n\ndata : \n" + data+"\n\n");
//                data = data.replace("\\n", "");
//                data = data.replace("\\r", "");
//                data = data.replace("\\\"", "");
                model.appendChild(doc.createTextNode(getModel(data, process.getURI(), export) + "\n\t\t"));
                root.appendChild(model);
                root.appendChild(doc.createTextNode("\n\t"));
                if (colorTask.length() > 0) {
                    Element colorTaskE = doc.createElement("colorTask");
                    root.appendChild(doc.createTextNode("\n\t"));

                    String[] tasks = colorTask.split("\\|");
                    int i = 1;
                    String script = "<script>";
                    for (String task : tasks) {
                        script += "var colorTask" + i + " = $(document.getElementById('" + task.substring(0, task.lastIndexOf(";")) + "')).attr('style', 'fill:#" + task.substring(task.lastIndexOf(";") + 1, task.length()) + "');";
                        i++;
//                    script +=" var colorTask= $(document.getElementById('" + task.substring(0, task.lastIndexOf(";") +"')).attr('style','fill:'"+task.substring(task.lastIndexOf(";"), task.length())+"");"));
                    }
                    script += "</script>";
                    colorTaskE.appendChild(doc.createTextNode(script + "\n\t\t"));
                    root.appendChild(colorTaskE);
                    root.appendChild(doc.createTextNode("\n\t"));
                }
            }

        } catch (Exception e) {
            log.error("Error on getDocument, " + e.getLocalizedMessage());
        }
        return doc;
    }

    static public List<VersionInfo> listVersions(RepositoryElement el) {
        ArrayList<VersionInfo> ret = new ArrayList<VersionInfo>();
        if (el != null) {
            VersionInfo vi = el.getLastVersion();
            VersionInfo ver = null;
            if (null != vi) {
                ver = vi;
                while (ver.getPreviousVersion() != null) {
                    ver = ver.getPreviousVersion();
                }
            }
            if (ver != null) {
                ret.add(ver);
                while (ver != null) {
                    ver = ver.getNextVersion();
                    if (ver != null) {
                        ret.add(ver);
                    }
                }
            }
        }
        return ret;
    }

    public static String getModel(String data, String uri, boolean export) {
        ProcessElement pe = (ProcessElement) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uri);
        String model
                = //        String model = "        <link href=\"" + SWBPortal.getContextPath() + "/swbadmin/css/bootstrap/bootstrap.css\" rel=\"stylesheet\" type=\"text/css\"></link>\n"
                //                + "        <link href=\"" + SWBPortal.getContextPath() + "/swbadmin/css/fontawesome/font-awesome.css\" rel=\"stylesheet\" type=\"text/css\"></link>\n"
                //+ "        <link href=\"" + SWBPortal.getContextPath() + "/swbadmin/jsp/process/commons/css/swbp.css\" rel=\"stylesheet\" type=\"text/css\"></link>\n"
                "        <link href=\"" + SWBPortal.getContextPath() + "/swbadmin/jsp/process/modeler/images/modelerFrame.css\" rel=\"stylesheet\" type=\"text/css\"></link>\n"
                //                + "        <script src=\"" + SWBPortal.getContextPath() + "/swbadmin/js/jquery/jquery.js\"></script>\n"
                + "        <script type=\"text/javascript\" src=\"" + SWBPortal.getContextPath() + "/swbadmin/jsp/process/modeler/toolkit.js\"></script>\n"
                + "        <script type=\"text/javascript\" src=\"" + SWBPortal.getContextPath() + "/swbadmin/jsp/process/modeler/modeler.js\"></script>\n";
//                + "        <script src=\"" + SWBPortal.getContextPath() + "/swbadmin/js/bootstrap/bootstrap.js\"></script>\n";
        if (export) {
            model = "        <link href=\"css/bootstrap/bootstrap.css\" rel=\"stylesheet\" type=\"text/css\"></link>\n"
                    + "        <link href=\"css/fontawesome/font-awesome.css\" rel=\"stylesheet\" type=\"text/css\"></link>\n"
                    + "        <link href=\"css/swbp.css\" rel=\"stylesheet\" type=\"text/css\"></link>\n"
                    + "        <link href=\"css/modeler/modelerFrame.css\" rel=\"stylesheet\" type=\"text/css\"></link>\n"
                    + "        <script src=\"js/jquery/jquery.js\"></script>\n"
                    + "        <script type=\"text/javascript\" src=\"js/modeler/toolkit.js\"></script>\n"
                    + "        <script type=\"text/javascript\" src=\"js/modeler/modeler.js\"></script>\n"
                    + "        <script src=\"js/bootstrap/bootstrap.js\"></script>\n";
        }
        model += "<ul class=\"list-unstyled list-inline hidden-print text-center\">\n"
                + "    <li>\n"
                + "        <a href=\"#\" class=\"btn btn-default\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"Zoom in\" onclick=\"zoomin();return false;\"><i class=\"fa fa-search-plus\"></i></a>\n"
                + "    </li>\n"
                + "    <li>\n"
                + "        <a href=\"#\" class=\"btn btn-default\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"Zoom out\" onclick=\"zoomout();return false;\"><i class=\"fa fa-search-minus\"></i></a>\n"
                + "    </li>\n"
                + "    <li>\n"
                + "        <a href=\"#\" class=\"btn btn-default\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"Reset zoom\" onclick=\"resetZoom();return false;\"><i class=\"fa fa-desktop\"></i></a>\n"
                + "    </li>\n"
                + "    <li>\n"
                + "        <a href=\"#\" class=\"btn btn-default\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"Pan left\" onclick=\"handlePanning('left');return false;\"><i class=\"fa fa-arrow-left\"></i></a>\n"
                + "    </li>\n"
                + "    <li>\n"
                + "        <a href=\"#\" class=\"btn btn-default\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"Pan down\" onclick=\"handlePanning('down');return false;\"><i class=\"fa fa-arrow-down\"></i></a>\n"
                + "    </li>\n"
                + "    <li>\n"
                + "        <a href=\"#\" class=\"btn btn-default\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"Pan up\" onclick=\"handlePanning('up');return false;\"><i class=\"fa fa-arrow-up\"></i></a>\n"
                + "    </li>\n"
                + "    <li>\n"
                + "        <a href=\"#\" class=\"btn btn-default\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"Pan right\" onclick=\"handlePanning('right');return false;\"><i class=\"fa fa-arrow-right\"></i></a>\n"
                + "    </li>\n"
                + "    <li>\n";
        if (export) {
            //Generar las dos imagenes
            model += "        <a href=\"rep_files/" + pe.getId() + ".png\" class=\"btn btn-default\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"Descargar PNG\"><i class=\"fa fa-file-image-o\"></i></a>\n"
                    + "    </li>\n"
                    + "    <li>\n"
                    + "        <a href=\"rep_files/" + pe.getId() + ".svg\" class=\"btn btn-default\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"Descargar SVG\"><i class=\"fa fa-file-code-o\"></i></a>\n"
                    + "    </li>\n";
        } else {
            model += "        <a href=\"#\" class=\"btn btn-default\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"Descargar PNG\" onclick=\"submit_download_form('png');\"><i class=\"fa fa-file-image-o\"></i></a>\n"
                    + "    </li>\n"
                    + "    <li>\n"
                    + "        <a href=\"#\" class=\"btn btn-default\" data-placement=\"bottom\" data-toggle=\"tooltip\" data-original-title=\"Descargar SVG\" onclick=\"submit_download_form('svg');\"><i class=\"fa fa-file-code-o\"></i></a>\n"
                    + "    </li>\n";
        }
        model += "</ul>\n"
                + "              <svg id=\"modeler\" xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"100\" height=\"100\" viewBox=\"0 0 1200 800\" class=\"modeler\">\n"
                + "                <style type=\"text/css\"><![CDATA[\n"
                + "                    /*.resizeBox {\n"
                + "                        stroke:#008000;\n"
                + "                        fill:url(#linearGradientStartEvent);\n"
                + "                        stroke-width:1.5;  \n"
                + "                    }*/\n"
                + "                    .task {\n"
                + "                        stroke:#79adc8;\n"
                + "                        fill:url(#linearGradientTask);\n"
                + "                        stroke-width:2;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n";
        if (pe instanceof SubProcess) {// si es SubProcess
            model += "             var obj = Modeler.getGraphElementByURI(null, \"" + uri + "\");\n"
                    + "             ToolKit.setLayer(obj.subLayer);\n";
        }
        model += "                    .task_o {\n"
                + "                        stroke:#2cff20;\n"
                + "                        fill:url(#linearGradientTask);\n"
                + "                        stroke-width:2;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "                    .callactivity {\n"
                + "                        stroke:#79adc8;\n"
                + "                        fill:url(#linearGradientTask);\n"
                + "                        stroke-width:4;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .callactivity_o {\n"
                + "                        stroke:#2cff20;\n"
                + "                        fill:url(#linearGradientTask);\n"
                + "                        stroke-width:4;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "                    .eventsubTask {\n"
                + "                        stroke:#79adc8;\n"
                + "                        fill:url(#linearGradientTask);\n"
                + "                        stroke-width:2;\n"
                + "                        cursor:pointer;\n"
                + "                        stroke-dasharray:6,4;\n"
                + "                    }\n"
                + "\n"
                + "                    .eventsubTask_o {\n"
                + "                        stroke:#2cff20;\n"
                + "                        fill:url(#linearGradientTask);\n"
                + "                        stroke-width:2;\n"
                + "                        cursor:pointer;\n"
                + "                        stroke-dasharray:6,4;\n"
                + "                    }\n"
                + "                    .startEvent\n"
                + "                    {\n"
                + "                        stroke:#008000;\n"
                + "                        fill:url(#linearGradientStartEvent);\n"
                + "                        stroke-width:1.5;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .startEvent_o\n"
                + "                    {\n"
                + "                        stroke:#2cff20;\n"
                + "                        fill:url(#linearGradientStartEvent);\n"
                + "                        stroke-width:1.5;\n"
                + "                        cursor: pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .intermediateInterruptingEvent\n"
                + "                    {\n"
                + "                        stroke:#2c5aa0;\n"
                + "                        fill:url(#linearGradientIntermediateEvent);\n"
                + "                        fill-opacity:1;\n"
                + "                        stroke-width:1.5;\n"
                + "                        stroke-dasharray: 3,3;\n"
                + "                        /*stroke-dashoffset: 5;*/\n"
                + "                    }\n"
                + "\n"
                + "                    .intermediateInterruptingEvent_o\n"
                + "                    {\n"
                + "                        stroke:#2cff20;\n"
                + "                        fill:url(#linearGradientIntermediateEvent);\n"
                + "                        fill-opacity:1;\n"
                + "                        stroke-width:1.5;\n"
                + "                        stroke-dasharray: 3,3;\n"
                + "                        /*stroke-dashoffset: 5;*/\n"
                + "                    }\n"
                + "\n"
                + "                    .intermediateEvent\n"
                + "                    {\n"
                + "                        stroke:#2c5aa0;\n"
                + "                        fill:url(#linearGradientIntermediateEvent);\n"
                + "                        fill-opacity:1;\n"
                + "                        stroke-width:1.5;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .intermediateEvent_o\n"
                + "                    {\n"
                + "                        stroke:#2cff20;\n"
                + "                        fill:url(#linearGradientIntermediateEvent);\n"
                + "                        fill-opacity:1;\n"
                + "                        stroke-width:1.5;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "                    .endEvent\n"
                + "                    {\n"
                + "                        stroke:#550000;\n"
                + "                        fill:url(#linearGradientEndEvent);\n"
                + "                        stroke-width:2.5;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .endEvent_o\n"
                + "                    {\n"
                + "                        stroke:#2cff20;\n"
                + "                        fill:url(#linearGradientEndEvent);\n"
                + "                        stroke-width:2.5;\n"
                + "                        cursor:pointer;\n"
                + "                    }    \n"
                + "\n"
                + "                    .gateway\n"
                + "                    {\n"
                + "                        stroke:#d4aa00;\n"
                + "                        fill:url(#linearGradientGateway);\n"
                + "                        stroke-width:2;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .gateway_o\n"
                + "                    {\n"
                + "                        stroke:#2cff20;\n"
                + "                        fill:url(#linearGradientGateway);\n"
                + "                        stroke-width:2;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .sequenceFlowSubLine {\n"
                + "                        fill:none;\n"
                + "                        stroke:#ffffff;\n"
                + "                        stroke-opacity:0.1;\n"
                + "                        stroke-width:8;\n"
                + "                        //cursor: pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .sequenceFlowSubLine_o {\n"
                + "                        fill:none;\n"
                + "                        stroke:#2cff20;\n"
                + "                        stroke-width:8;\n"
                + "                        //cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .swimlane\n"
                + "                    {\n"
                + "                        fill: #E8E8FF;\n"
                + "                        stroke-width:2;\n"
                + "                        stroke: #ADADAE;\n"
                + "                        fill-opacity:1;\n"
                + "                        //cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .swimlane_o\n"
                + "                    {\n"
                + "                        stroke:#2cff20;\n"
                + "                        fill: #E8E8FF;\n"
                + "                        stroke-width:2;\n"
                + "                        fill-opacity:1;\n"
                + "                        //cursor: pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .sequenceFlowLine {\n"
                + "                        fill: none;\n"
                + "                        stroke-width: 2;\n"
                + "                        stroke: #000000;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .sequenceFlowLine_o {\n"
                + "                        fill: none;\n"
                + "                        stroke-width: 2;\n"
                + "                        stroke: #2cff20;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .intermediateEvent1\n"
                + "                    {\n"
                + "                        stroke:#2c5aa0;\n"
                + "                        fill:none;\n"
                + "                        stroke-width:1;\n"
                + "                    }\n"
                + "\n"
                + "                    .itemaware {\n"
                + "                        fill:url(#linearGradientDataObject);\n"
                + "                        stroke:#666666;\n"
                + "                        stroke-width:2;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .itemaware_o {\n"
                + "                        fill:url(#linearGradientDataObject);\n"
                + "                        stroke:#2cff20;\n"
                + "                        stroke-width:2;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .transactionSquare {\n"
                + "                        stroke:#2c5aa0;\n"
                + "                        cursor:pointer;\n"
                + "                        fill:none;\n"
                + "                        stroke-width:1.5;\n"
                + "                    }\n"
                + "\n"
                + "                    .group {\n"
                + "                        stroke:#2c5aa0;\n"
                + "                        cursor:pointer;\n"
                + "                        fill:none;\n"
                + "                        stroke-width:1.5;\n"
                + "                    }\n"
                + "\n"
                + "                    .annotationArtifact {\n"
                + "                        fill:none;\n"
                + "                        stroke:#000000;\n"
                + "                        stroke-width:1.5px;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .annotationArtifactRect {\n"
                + "                        fill:none;\n"
                + "                        stroke:none;\n"
                + "                        fill:#E6E6E6;\n"
                + "                        fill-opacity:0.2;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .annotationArtifactRect_o {\n"
                + "                        fill:none;\n"
                + "                        fill:#E6E6E6;\n"
                + "                        stroke:#2cff20;\n"
                + "                        fill-opacity:0.2;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .group_o {\n"
                + "                        stroke:#2cff20;\n"
                + "                        cursor:pointer;\n"
                + "                        fill:none;\n"
                + "                        stroke-width:1.5;\n"
                + "                    }\n"
                + "\n"
                + "                    .taskMarker\n"
                + "                    {\n"
                + "                        stroke:#2c5aa0;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .pathMarker{\n"
                + "                        fill:none;\n"
                + "                        stroke-width:1;\n"
                + "                    }\n"
                + "\n"
                + "                    .startMarker{\n"
                + "                        stroke:#008000;\n"
                + "                        fill:none;\n"
                + "                        stroke-width:2.5;                        \n"
                + "                    }\n"
                + "\n"
                + "                    .startMarker{\n"
                + "                        stroke:#008000;\n"
                + "                        fill:none;\n"
                + "                        stroke-width:2.5;                        \n"
                + "                    }\n"
                + "\n"
                + "                    .startFilledMarker{\n"
                + "                        fill:#008000;\n"
                + "                        stroke:none;\n"
                + "                        stroke-width:1;\n"
                + "                    }\n"
                + "\n"
                + "                    .intermediateMarker{\n"
                + "                        stroke:#2c5aa0;\n"
                + "                        fill:#ffffff;\n"
                + "                        fill-opacity:0.1;\n"
                + "                        stroke-width:2.5;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .intermediateFilledMarker {\n"
                + "                        fill:#2c5aa0;\n"
                + "                        stroke:#2c5aa0;\n"
                + "                        stroke-width:1;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .endFilledMarked\n"
                + "                    {\n"
                + "                        fill:#550000;   \n"
                + "                        stroke:#550000;\n"
                + "                        stroke-width:1;\n"
                + "                        cursor:pointer;\n"
                + "                    }\n"
                + "\n"
                + "                    .navPath {\n"
                + "                        fill:#e7e7e7;\n"
                + "                        stroke:gray;\n"
                + "                        stroke-width:1;\n"
                + "                    }\n"
                + "\n"
                + "                    .navPathHidden {\n"
                + "                        fill:#e7e7e7;\n"
                + "                        stroke:gray;\n"
                + "                        stroke-width:1;\n"
                + "                        display:none;\n"
                + "                    }\n"
                + "                    ]]></style>\n"
                + "                <defs id=\"globalDef\">\n"
                + "                <!--Definición de gradientes para las figuras-->\n"
                + "                <linearGradient id=\"linearGradientStartEvent\" x1=\"0%\" y1=\"0%\" x2=\"100%\" y2=\"100%\">\n"
                + "                <stop offset=\"10%\" style=\"stop-color:#ffffff;stop-opacity:1\" />\n"
                + "                <stop offset=\"90%\" style=\"stop-color:#ccffaa;stop-opacity:1\" />\n"
                + "                </linearGradient>\n"
                + "                <linearGradient id=\"linearGradientEndEvent\" x1=\"0%\" y1=\"0%\" x2=\"100%\" y2=\"100%\">\n"
                + "                <stop offset=\"10%\" style=\"stop-color:#ffffff;stop-opacity:1\" />\n"
                + "                <stop offset=\"90%\" style=\"stop-color:#e7c1c1;stop-opacity:1\" />\n"
                + "                </linearGradient>\n"
                + "                <linearGradient id=\"linearGradientTask\" x1=\"100%\" y1=\"0%\" x2=\"100%\" y2=\"100%\">\n"
                + "                <stop offset=\"10%\" style=\"stop-color:#ffffff;stop-opacity:1\" />\n"
                + "                <stop offset=\"100%\" style=\"stop-color:#c1d3e1;stop-opacity:1\" />\n"
                + "                </linearGradient>\n"
                + "                <linearGradient id=\"linearGradientIntermediateEvent\" x1=\"0%\" y1=\"0%\" x2=\"100%\" y2=\"100%\">\n"
                + "                <stop offset=\"10%\" style=\"stop-color:#ffffff;stop-opacity:1\" />\n"
                + "                <stop offset=\"100%\" style=\"stop-color:#87aade;stop-opacity:1\" />\n"
                + "                </linearGradient>\n"
                + "                <linearGradient id=\"linearGradientGateway\" x1=\"0%\" y1=\"0%\" x2=\"100%\" y2=\"100%\">\n"
                + "                <stop offset=\"10%\" style=\"stop-color:#FFFDE2;stop-opacity:1\" />\n"
                + "                <stop offset=\"100%\" style=\"stop-color:#FFFAA6;stop-opacity:1\" />\n"
                + "                </linearGradient>\n"
                + "                <linearGradient id=\"linearGradientDataObject\" x1=\"0%\" y1=\"0%\" x2=\"100%\" y2=\"100%\">\n"
                + "                <stop offset=\"10%\" style=\"stop-color:#ffffff;stop-opacity:1\" />\n"
                + "                <stop offset=\"100%\" style=\"stop-color:#afbac7;stop-opacity:1\" />\n"
                + "                </linearGradient>\n"
                + "                <circle id=\"resizeBox\" cx=\"0\" cy=\"0\" r=\"5\" class=\"resizeBox\"/>\n"
                + "                <!--definición de marcadores para las figuras-->\n"
                + "                <path id=\"errorMarker\" d=\"m 0.5,1051.8622 17.0774,-39.6847 15.0444,21.9792 19.5171,-27.474 L 34.8582,1048.199 19.8137,1029.6795 0.5,1051.8622 z\" transform=\"scale(0.35,0.35) translate(-26, -1030)\" />\n"
                + "                <g id=\"ruleMarker\">\n"
                + "                <path d=\"m 0,0 0,43.9063 46.75,0 0,-43.9063 z m 4.25,9.875 18.5,0 0,4.8438 -18.5,0 z m 20,0 18.5,0 0,4.8438 -18.5,0 z m -20,8 18.5,0 0,4.8438 -18.5,0 z m 20,0 18.5,0 0,4.8438 -18.5,0 z m -20,8 18.5,0 0,4.8438 -18.5,0 z m 20,0 18.5,0 0,4.8438 -18.5,0 z m -20,8 18.5,0 0,4.8438 -18.5,0 z m 20,0 18.5,0 0,4.8438 -18.5,0 z\" transform=\"scale(0.32,0.32) translate(-23.5,-23.5)\"/>\n"
                + "                </g>\n"
                + "                <g id=\"taskRuleMarker\" class=\"intermediateFilledMarker\">\n"
                + "                <path d=\"m 0,0 0,43.9063 46.75,0 0,-43.9063 z m 4.25,9.875 18.5,0 0,4.8438 -18.5,0 z m 20,0 18.5,0 0,4.8438 -18.5,0 z m -20,8 18.5,0 0,4.8438 -18.5,0 z m 20,0 18.5,0 0,4.8438 -18.5,0 z m -20,8 18.5,0 0,4.8438 -18.5,0 z m 20,0 18.5,0 0,4.8438 -18.5,0 z m -20,8 18.5,0 0,4.8438 -18.5,0 z m 20,0 18.5,0 0,4.8438 -18.5,0 z\" transform=\"scale(0.32,0.32) translate(-23.5,-23.5)\"/>\n"
                + "                </g>\n"
                + "                <path id=\"multipleMarker\" d=\"m 0,0 -33.73973,0.37343 -10.78131,-31.973 27.07653,-20.13383 27.51552,19.52961 z\" transform=\"scale(0.35,0.35) translate(17,23)\" />\n"
                + "                <path id=\"parallelMarker\" d=\"m 0,0 0,19.4788 -19.45519,0 0,10.5627 19.45519,0 0,19.4787 10.58626,0 0,-19.4787 19.47871,0 0,-10.5627 -19.47871,0 0,-19.4788 z\" transform=\"scale(0.35,0.35) translate(-5, -25)\"/>\n"
                + "                <path id=\"linkMarker\" d=\"m 0,0 -20.432,-24.2007 0,16.9761 -25.0594,0 0,14.1289 25.0594,0 0,17.332 z\" transform=\"scale(0.35,0.35) translate(23,0)\"/>\n"
                + "                <path id=\"complexMarker\" d=\"m 0,0 0,15.875 -11.25,-11.2187 -2.34375,2.3125 11.25,11.25 -15.90625,0 0,3.3125 15.90625,0 -11.25,11.2187 2.34375,2.3438 11.25,-11.2188 0,15.875 3.28125,0 0,-15.9062 11.25,11.25 2.3125,-2.3438 -11.21875,-11.2187 15.875,0 0,-3.3125 -15.875,0 11.21875,-11.2188 -2.3125,-2.3437 -11.25,11.25 0,-15.9063 z\" transform=\"translate(-1, -20)\"/>\n"
                + "                <path id=\"signalMarker\" d=\"m 0,0 -23.59924,0 -23.59925,0 11.79962,-20.43755 11.79963,-20.43755 11.79962,20.43755 z\" transform=\"scale(0.35,0.35) translate(24,14)\"/>\n"
                + "                <path id=\"scalationMarker\" transform=\"scale(0.35,0.35) translate(0,-26)\" d=\"m 0,0 -21.34041,47.167 21.34041,-17.3811 21.340402,17.3811 -21.340402,-47.167 z\" />\n"
                + "                <path id=\"cancelMarker\" d=\"m 0,0 -18.3627,18.3627 -18.3848004,-18.3848 -3.3366,3.3367 18.3847004,18.3847 -18.3847004,18.3848 3.3366,3.3367 18.3848004,-18.3848 18.3627,18.3627 3.3366,-3.3367 -18.3626,-18.3627 18.3626,-18.3626 z\" transform=\"scale(0.35,0.35) translate(18, -22)\"/>\n"
                + "                <g id=\"manualMarker\" class=\"taskMarker\" transform=\"scale(0.65)\">\n"
                + "                <path d=\"m 0,0 c -0.27155,0 -0.5225,0.072 -0.75179,0.1792 -0.007,0 -0.0189,0 -0.0259,0 -1.25518,0.3798 -2.16186,4.1013 -2.9035,6.1425 -0.22877,0.5536 -0.36294,1.1549 -0.36294,1.7916 l 0,4.7861 c 0,2.6228 2.13932,4.7348 4.79597,4.7348 l 5.0552,0 5.69034,0 7.72539,0 c 0.97664,0 1.76284,-0.7762 1.76284,-1.7404 0,-0.9641 -0.7862,-1.7403 -1.76284,-1.7403 l -3.11089,0 c 0.0118,-0.043 0.0283,-0.085 0.0389,-0.128 l 4.16081,0 c 0.97664,0 1.76285,-0.7762 1.76285,-1.7404 0,-0.9642 -2.73949,-1.7404 -1.76285,-1.7404 l -4.01823,0 0,-0.064 5.23667,0 c 0.97664,0 1.76284,-0.7762 1.76284,-1.7404 0,-0.9641 -0.7862,-1.7403 -1.76284,-1.7403 l -5.39221,0 c -0.0116,-0.046 -0.0132,-0.095 -0.0259,-0.1408 l 7.56984,0 c 0.97663,0 1.76284,-0.7762 1.76284,-1.7404 0,-0.9642 -0.78621,-1.7404 -1.76284,-1.7404 l -12.18433,0 -0.92031,0 c 0.69854,-0.2311 1.20547,-0.8686 1.20547,-1.638 0,-0.9642 -0.7862,-1.7404 -1.76284,-1.7404 l -10.01967,0 z\" class=\"pathMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"messageThrowMarker\" transform=\"scale(1.3,1.3) translate(-7.5,-4.5)\" >\n"
                + "                <path d=\"m 0,0 0,9.175062 14.84706,0 0,-9.175062 -0.0245,0 -7.40716,4.648917 -7.41535,-4.648917 z\" style=\"stroke:#ffffff;stroke-opacity:0.4\"/>\n"
                + "                <path d=\"m 0.6,0 6.88839,4.318911 6.88838,-4.318911\" style=\"stroke:#ffffff;stroke-opacity:0.4\"/>\n"
                + "                </g>\n"
                + "                <g id=\"taskMessageThrowMarker\" transform=\"scale(1.3,1.3) translate(-7.5,-4.5)\" class=\"intermediateFilledMarker\">\n"
                + "                <path d=\"m 0,0 0,9.175062 14.84706,0 0,-9.175062 -0.0245,0 -7.40716,4.648917 -7.41535,-4.648917 z\" style=\"stroke:#ffffff;stroke-opacity:0.4\"/>\n"
                + "                <path d=\"m 0.6,0 6.88839,4.318911 6.88838,-4.318911\" style=\"stroke:#ffffff;stroke-opacity:0.4\"/>\n"
                + "                </g>\n"
                + "                <g id=\"messageCatchMarker\" transform=\"scale(0.35,0.35) translate(-27,-17)\" >\n"
                + "                <rect width=\"52.704407\" height=\"32.573116\" x=\"0\" y=\"0\"/>\n"
                + "                <path d=\"m 0,0 26.30906,16.49536 26.30905,-16.49536\"/>\n"
                + "                </g>\n"
                + "                <g id=\"taskMessageCatchMarker\" transform=\"scale(0.35,0.35) translate(-27,-17)\" class=\"intermediateMarker\">\n"
                + "                <rect width=\"52.704407\" height=\"32.573116\" x=\"0\" y=\"0\"/>\n"
                + "                <path d=\"m 0,0 26.30906,16.49536 26.30905,-16.49536\"/>\n"
                + "                </g>\n"
                + "                <g id=\"compensaMarker\" transform=\"scale(0.35,0.35) translate(-2,-13)\">\n"
                + "                <path d=\"m 0,0 -28.36636,0 14.18318,-24.56599 z\" transform=\"matrix(0,-1,1,0,0,0)\"/>\n"
                + "                <path d=\"m 0,0 -28.36636,0 14.18318,-24.56599 z\" transform=\"matrix(0,-1,1,0,23,0)\"/>\n"
                + "                </g>\n"
                + "                <filter id=\"dropshadow\" height=\"130%\">\n"
                + "                    <feGaussianBlur in=\"SourceAlpha\" stdDeviation=\"3\"/> \n"
                + "                    <feOffset dx=\"0\" dy=\"0\" result=\"offsetblur\"/>\n"
                + "                    <feComponentTransfer>\n"
                + "                        <feFuncA type=\"linear\" slope=\"0.5\"/>\n"
                + "                    </feComponentTransfer>\n"
                + "                    <feMerge> \n"
                + "                        <feMergeNode/>\n"
                + "                        <feMergeNode in=\"SourceGraphic\"/> \n"
                + "                    </feMerge>\n"
                + "                </filter>\n"
                + "                <g id=\"timerMarker\" transform=\"scale(0.35,0.35) translate(-396,-219)\" >\n"
                + "                <path d=\"m 232.18784,217.1541 a 40.089989,40.089989 0 1 1 -80.17997,0 40.089989,40.089989 0 1 1 80.17997,0 z\" transform=\"matrix(0.6346099,0,0,0.6346099,274.19065,81.345956)\"/>\n"
                + "                <path d=\"m 421.49964,219.1541 -8.76968,0\"/>\n"
                + "                <path d=\"m 396.40906,193.78618 -0.0964,8.76915\"/>\n"
                + "                <path d=\"m 370.76392,218.75252 8.76858,0.13882\"/>\n"
                + "                <path d=\"m 396.04976,244.52342 0.0278,-8.76963\"/>\n"
                + "                <path d=\"m 408.75323,219.1541 -12.86938,0\"/>\n"
                + "                <path d=\"m 401.39526,204.36536 -4.8964,14.70678\"/>\n"
                + "                <path d=\"m 414.26521,201.41346 -6.26889,6.13256\"/>\n"
                + "                <path d=\"m 413.87083,237.28912 -6.13256,-6.26889\"/>\n"
                + "                <path d=\"m 377.99517,236.89474 6.26889,-6.13256\"/>\n"
                + "                <path d=\"m 378.38955,201.01908 6.13256,6.26889\"/>\n"
                + "                </g>\n"
                + "                <g id=\"userMarker\" class=\"taskMarker\" transform=\"scale(0.8) translate(0,-1042)\">\n"
                + "                <path d=\"m 0,1039 c -3.3158023,0 -6,2.8058 -6,6.25 0,2.0045 0.9288788,3.7654 2.34375,4.9062 -3.3002786,1.7745 -5.6842907,5.8793 -6,10.75 l 19.3125,0 c -0.3158218,-4.8705 -2.7074728,-8.9755 -6,-10.75 C 5.0711212,1049.0154 6,1047.2545 6,1045.25 6,1041.8058 3.3158023,1039 0,1039 z\" class=\"pathMarker\" />\n"
                + "                <path d=\"m -377.4453,473.79996 a 9.3826418,9.3826418 0 0 1 -12.52292,0.66553\" transform=\"matrix(0.6398828,0,0,0.66465394,245.76439,734.73647)\" class=\"pathMarker\" />\n"
                + "                <path d=\"m -5.1190626,1060.732 0,-3.8413\" class=\"pathMarker\"/>\n"
                + "                <path d=\"m 5.1190626,1060.732 0,-3.8413\" class=\"pathMarker\" />\n"
                + "                <path d=\"m 0.06204402,1039.6046 c -3.10856302,0 -5.66539592,2.4352 -5.68305502,5.5312 0.8963285,-1.0836 1.7094089,-1.6888 3.0835319,-1.6888 2.07094865,0 4.4437605,1.0758 6.2188589,0.7688 0.570252,-0.099 1.2624582,-0.3804 1.939646,-0.7272 -0.6073258,-2.271 -3.0493799,-3.884 -5.55898178,-3.884 z\" style=\"fill:#2c5aa0;stroke-width:1;\" />\n"
                + "                </g>\n"
                + "                <g id=\"serviceMarker\" class=\"taskMarker\" transform=\"scale(0.8)\">\n"
                + "                <path d=\"m 0,0 1.478093,-2.5912 c -0.762317,-0.4662 -1.601697,-0.8229 -2.495483,-1.0366 l -0.767843,2.8793 -2.495483,0 -0.787044,-2.8793 c -0.895236,0.2142 -1.733065,0.5691 -2.495483,1.0366 l 1.487693,2.5721 -1.766033,1.7659 -2.581869,-1.478 c -0.467511,0.7623 -0.822527,1.6003 -1.036583,2.4952 l 2.86981,0.7678 0,2.5048 -2.860209,0.7775 c 0.21606,0.8875 0.562993,1.7267 1.026982,2.4856 l 2.562669,-1.478\" class=\"pathMarker\" />\n"
                + "                <path d=\"m 0,0 c -0.989621,0.2366 -1.915788,0.629 -2.758588,1.1457 l 1.644541,2.8433 -1.952235,1.9521 -2.85408,-1.6338 c -0.516803,0.8427 -0.909247,1.769 -1.145882,2.7583 l 3.172388,0.8488 0,2.769 -3.161774,0.8593 c 0.238849,0.9811 0.622356,1.9088 1.135268,2.7477 l 2.832859,-1.6337 1.952235,1.952 -1.633935,2.8539 c 0.84654,0.5219 1.774518,0.918 2.769203,1.1564 l 0.848804,-3.1828 2.758588,0 0.870025,3.1828 c 0.988288,-0.238 1.914849,-0.6289 2.758588,-1.1459 l -1.644542,-2.8431 1.952236,-1.9521 2.864694,1.6337 c 0.515612,-0.8389 0.908032,-1.7642 1.145874,-2.7477 l -3.182994,-0.8593 0,-2.769 3.182994,-0.87 c -0.237717,-0.9884 -0.628065,-1.9158 -1.145874,-2.7583 l -2.854088,1.655 -1.952235,-1.9521 1.633935,-2.8645 c -0.842691,-0.5152 -1.77056,-0.9096 -2.758588,-1.1457 l -0.848804,3.1828 -2.758588,0 z m 2.238708,5.697 c 1.994007,0 3.607392,1.6133 3.607392,3.6072 0,1.9938 -1.613385,3.6176 -3.607392,3.6176 -1.994007,0 -3.607392,-1.6238 -3.607392,-3.6176 0,-1.9939 1.613385,-3.6072 3.607392,-3.6072 z\" class=\"pathMarker\" />\n"
                + "                </g>\n"
                + "                <g id=\"scriptMarker\" class=\"taskMarker\"  transform=\"scale(0.7)\">\n"
                + "                <path d=\"m 0,0 c 3.5628793,2.98 3.5212524,7.9199 0,10.8403 l 0,0.1186 13.5952063,-10e-4 c 4.246065,-3.0502 4.698752,-8.6843 0.03211,-12.4597 -1.719778,-1.441 -2.097829,-3.2691 -2.297848,-5.5453 0,-1.9186 0.795633,-3.6321 2.05209,-4.7945 l -9.1791045,0 c -7.65536286,-0.3702 -8.7288909,8.3174 -4.2024826,11.8414 z\" class=\"pathMarker\" />\n"
                + "                <path d=\"m 0,-8 9.5315769,0\" class=\"pathMarker\" />\n"
                + "                <path d=\"m -0.5,-4.1568 9.5315769,0\" class=\"pathMarker\" />\n"
                + "                <path d=\"m 2.5,0 9.5315769,0\" class=\"pathMarker\" />\n"
                + "                <path d=\"m 4.5,4 9.5315768,0\" class=\"pathMarker\" />\n"
                + "                <path d=\"m 4,8 9.5315768,0\" class=\"pathMarker\" />\n"
                + "                </g>\n"
                + "                <g id=\"subProcessMarker\" class=\"taskMarker\">\n"
                + "                <rect x=\"-7\" y=\"-7\" width=\"14\" height=\"14\" style=\"fill:#ffffff;fill-opacity:0.1;\"/>\n"
                + "                <path d=\"M-5 0 L5 0 M0 -5 L0 5\"/>\n"
                + "                </g>\n"
                + "                <marker id=\"sequenceArrow\" viewBox=\"0 0 12 12\" refX=\"0\" refY=\"5\" markerUnits=\"userSpaceOnUse\" markerWidth=\"10\" markerHeight=\"10\" orient=\"auto\" fill=\"black\" stroke=\"none\" stroke-dasharray=\"0\">\n"
                + "                    <path d=\"M 0 0 L 10 5 L 0 10\"/>\n"
                + "                </marker>\n"
                + "                <marker id=\"messageArrow\" viewBox=\"0 0 12 12\" refX=\"7\" refY=\"5\" markerUnits=\"userSpaceOnUse\" markerWidth=\"10\" markerHeight=\"10\" orient=\"auto\" fill=\"none\" stroke=\"black\" stroke-width=\"1.5\" stroke-dasharray=\"0\">\n"
                + "                    <path d=\"M 0 0 L 10 5 L 0 10\"/>\n"
                + "                </marker>\n"
                + "                <marker id=\"conditionTail\" viewBox=\"-6 -6 12 12\" refX=\"3\" refY=\"0\" markerUnits=\"userSpaceOnUse\" markerWidth=\"12\" markerHeight=\"12\" orient=\"auto\" fill=\"none\" stroke=\"black\" stroke-width=\"1.5\" stroke-dasharray=\"0\">\n"
                + "                    <rect x=\"-3\" y=\"-3\" width=\"6\" height=\"6\" transform=\"rotate(-45)\"/>\n"
                + "                </marker>\n"
                + "                <marker id=\"defaultTail\" viewBox=\"0 0 12 12\" refX=\"-3\" refY=\"5\" markerUnits=\"userSpaceOnUse\" markerWidth=\"10\" markerHeight=\"10\" orient=\"auto\" fill=\"none\" stroke=\"black\" stroke-width=\"2\" stroke-dasharray=\"0\">\n"
                + "                    <path d=\"M 5 0 L 0 10\"/>\n"
                + "                </marker>\n"
                + "                <marker id=\"messageTail\" viewBox=\"-5 -5 12 12\" refX=\"3\" refY=\"0\" markerUnits=\"userSpaceOnUse\" markerWidth=\"10\" markerHeight=\"10\" orient=\"auto\" fill=\"none\" stroke=\"black\" stroke-width=\"1.5\" stroke-dasharray=\"0\">\n"
                + "                    <circle r=\"3\" />\n"
                + "                </marker>\n"
                + "                <!--Definición de eventos iniciales-->\n"
                + "                <circle id=\"startEvent\" r=\"15\" bclass=\"startEvent\" oclass=\"startEvent_o\"/>\n"
                + "                <g id=\"messageStartEvent\" bclass=\"startEvent\" oclass=\"startEvent_o\">\n"
                + "                <use xlink:href=\"#startEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#messageCatchMarker\" x=\"0\" y=\"0\" class=\"startMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"timerStartEvent\" bclass=\"startEvent\" oclass=\"startEvent_o\">\n"
                + "                <use xlink:href=\"#startEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#timerMarker\" x=\"0\" y=\"0\" class=\"startMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"ruleStartEvent\" bclass=\"startEvent\" oclass=\"startEvent_o\">\n"
                + "                <use xlink:href=\"#startEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#ruleMarker\" x=\"0\" y=\"0\" class=\"startFilledMarker\" transform=\"scale(1.1)\"/>\n"
                + "                </g>\n"
                + "                <g id=\"signalStartEvent\" bclass=\"startEvent\" oclass=\"startEvent_o\">\n"
                + "                <use xlink:href=\"#startEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#signalMarker\" x=\"0\" y=\"0\" class=\"startMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"multipleStartEvent\" bclass=\"startEvent\" oclass=\"startEvent_o\">\n"
                + "                <use xlink:href=\"#startEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#multipleMarker\" x=\"0\" y=\"0\" class=\"startMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"parallelStartEvent\" bclass=\"startEvent\" oclass=\"startEvent_o\">\n"
                + "                <use xlink:href=\"#startEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#parallelMarker\" x=\"0\" y=\"0\" class=\"startMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"scalationStartEvent\" bclass=\"startEvent\" oclass=\"startEvent_o\">\n"
                + "                <use xlink:href=\"#startEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#scalationMarker\" x=\"0\" y=\"0\" class=\"startMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"errorStartEvent\" bclass=\"startEvent\" oclass=\"startEvent_o\">\n"
                + "                <use xlink:href=\"#startEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#errorMarker\" x=\"0\" y=\"0\" class=\"startMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"compensationStartEvent\" bclass=\"startEvent\" oclass=\"startEvent_o\">\n"
                + "                <use xlink:href=\"#startEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#compensaMarker\" x=\"0\" y=\"0\" class=\"startMarker\"/>\n"
                + "                </g>\n"
                + "                <!--Definición de eventos intermedios-->\n"
                + "                <g id=\"intermediateEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <circle r=\"15\"/>\n"
                + "                <circle r=\"12\" class=\"intermediateEvent1\"/>\n"
                + "                </g>\n"
                + "                <g id=\"messageIntermediateCatchEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <use xlink:href=\"#intermediateEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#messageCatchMarker\" x=\"0\" y=\"0\" class=\"intermediateMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"messageIntermediateThrowEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <use xlink:href=\"#intermediateEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#messageThrowMarker\" x=\"0\" y=\"0\" class=\"intermediateFilledMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"timerIntermediateEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <use xlink:href=\"#intermediateEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#timerMarker\" x=\"0\" y=\"0\" class=\"intermediateMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"errorIntermediateEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <use xlink:href=\"#intermediateEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#errorMarker\" x=\"0\" y=\"0\" class=\"intermediateMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"cancelIntermediateEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <use xlink:href=\"#intermediateEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#cancelMarker\" x=\"0\" y=\"0\" class=\"intermediateFilledMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"compensationIntermediateCatchEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <use xlink:href=\"#intermediateEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#compensaMarker\" x=\"0\" y=\"0\" class=\"intermediateMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"compensationIntermediateThrowEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <use xlink:href=\"#intermediateEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#compensaMarker\" x=\"0\" y=\"0\" class=\"intermediateFilledMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"ruleIntermediateEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <use xlink:href=\"#intermediateEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#ruleMarker\" x=\"0\" y=\"0\" class=\"intermediateFilledMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"linkIntermediateCatchEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <use xlink:href=\"#intermediateEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#linkMarker\" x=\"0\" y=\"0\" class=\"intermediateMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"linkIntermediateThrowEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <use xlink:href=\"#intermediateEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#linkMarker\" x=\"0\" y=\"0\" class=\"intermediateFilledMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"signalIntermediateCatchEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <use xlink:href=\"#intermediateEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#signalMarker\" x=\"0\" y=\"0\" class=\"intermediateMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"signalIntermediateThrowEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <use xlink:href=\"#intermediateEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#signalMarker\" x=\"0\" y=\"0\" class=\"intermediateFilledMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"multipleIntermediateCatchEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <use xlink:href=\"#intermediateEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#multipleMarker\" x=\"0\" y=\"0\" class=\"intermediateMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"multipleIntermediateThrowEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <use xlink:href=\"#intermediateEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#multipleMarker\" x=\"0\" y=\"0\" class=\"intermediateFilledMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"scalationIntermediateCatchEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <use xlink:href=\"#intermediateEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#scalationMarker\" x=\"0\" y=\"0\" class=\"intermediateMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"scalationIntermediateThrowEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <use xlink:href=\"#intermediateEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#scalationMarker\" x=\"0\" y=\"0\" class=\"intermediateFilledMarker\"/>\n"
                + "                </g>\n"
                + "                <g id=\"parallelIntermediateEvent\" bclass=\"intermediateEvent\" oclass=\"intermediateEvent_o\">\n"
                + "                <use xlink:href=\"#intermediateEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#parallelMarker\" x=\"0\" y=\"0\" class=\"intermediateMarker\"/>\n"
                + "                </g>\n"
                + "                <!--Definición de eventos finales-->\n"
                + "                <circle id=\"endEvent\" r=\"15\" bclass=\"endEvent\" oclass=\"endEvent_o\"/>\n"
                + "\n"
                + "                <g id=\"messageEndEvent\" bclass=\"endEvent\" oclass=\"endEvent_o\">\n"
                + "                <use xlink:href=\"#endEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#messageThrowMarker\" x=\"0\" y=\"0\" class=\"endFilledMarked\"/>\n"
                + "                </g>\n"
                + "                <g id=\"signalEndEvent\" bclass=\"endEvent\" oclass=\"endEvent_o\">\n"
                + "                <use xlink:href=\"#endEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#signalMarker\" x=\"0\" y=\"0\" class=\"endFilledMarked\"/>\n"
                + "                </g>\n"
                + "                <g id=\"scalationEndEvent\" bclass=\"endEvent\" oclass=\"endEvent_o\">\n"
                + "                <use xlink:href=\"#endEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#scalationMarker\" x=\"0\" y=\"0\" class=\"endFilledMarked\"/>\n"
                + "                </g>\n"
                + "                <g id=\"errorEndEvent\" bclass=\"endEvent\" oclass=\"endEvent_o\">\n"
                + "                <use xlink:href=\"#endEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#errorMarker\" x=\"0\" y=\"0\" class=\"endFilledMarked\"/>\n"
                + "                </g>\n"
                + "                <g id=\"multipleEndEvent\" bclass=\"endEvent\" oclass=\"endEvent_o\">\n"
                + "                <use xlink:href=\"#endEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#multipleMarker\" x=\"0\" y=\"0\" class=\"endFilledMarked\"/>\n"
                + "                </g>\n"
                + "                <g id=\"cancelationEndEvent\" bclass=\"endEvent\" oclass=\"endEvent_o\">\n"
                + "                <use xlink:href=\"#endEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#cancelMarker\" x=\"0\" y=\"0\" class=\"endFilledMarked\"/>\n"
                + "                </g>\n"
                + "                <g id=\"terminationEndEvent\" bclass=\"endEvent\" oclass=\"endEvent_o\">\n"
                + "                <use xlink:href=\"#endEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <circle r=\"8\" class=\"endFilledMarked\"/>\n"
                + "                </g>\n"
                + "                <g id=\"compensationEndEvent\" bclass=\"endEvent\" oclass=\"endEvent_o\">\n"
                + "                <use xlink:href=\"#endEvent\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#compensaMarker\" x=\"0\" y=\"0\" class=\"endFilledMarked\"/>\n"
                + "                </g>\n"
                + "                <!--Definición de compuertas-->\n"
                + "                <rect id=\"gateway\" x=\"-17.5\" y=\"-17.5\" width=\"35\" height=\"35\" bclass=\"gateway\" oclass=\"gateway_o\" transform=\"rotate(45,0,0)\"/>\n"
                + "                <g id=\"exclusiveDataGateway\" bclass=\"gateway\" oclass=\"gateway_o\" >\n"
                + "                <use xlink:href=\"#gateway\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#cancelMarker\" x=\"0\" y=\"0\" style=\"stroke:none;fill:#d4aa00\" transform=\"scale(1.3,1.3)\"/>\n"
                + "                </g>\n"
                + "                <g id=\"inclusiveDataGateway\" bclass=\"gateway\" oclass=\"gateway_o\" >\n"
                + "                <use xlink:href=\"#gateway\" x=\"0\" y=\"0\"/>\n"
                + "                <circle r=\"14\" style=\"fill:none;stroke:#d4aa00;stroke-width:2;\"/>\n"
                + "                </g>\n"
                + "                <g id=\"exclusiveStartGateway\" bclass=\"gateway\" oclass=\"gateway_o\" >\n"
                + "                <use xlink:href=\"#gateway\" x=\"0\" y=\"0\"/>\n"
                + "                <circle r=\"14\" style=\"fill:none;stroke:#d4aa00;stroke-width:2;\"/>\n"
                + "                <use xlink:href=\"#multipleMarker\" x=\"0\" y=\"0\" style=\"fill:none;stroke:#d4aa00;stroke-width:4.5;\" transform=\"scale(0.9,0.9)\"/>\n"
                + "                </g>\n"
                + "                <g id=\"eventGateway\" bclass=\"gateway\" oclass=\"gateway_o\" >\n"
                + "                <use xlink:href=\"#gateway\" x=\"0\" y=\"0\"/>\n"
                + "                <circle r=\"14\" style=\"fill:none;stroke:#d4aa00;stroke-width:2;\"/>\n"
                + "                <circle r=\"10\" style=\"fill:none;stroke:#d4aa00;stroke-width:2;\"/>\n"
                + "                <use xlink:href=\"#multipleMarker\" x=\"0\" y=\"0\" style=\"fill:none;stroke:#d4aa00;stroke-width:6;\" transform=\"scale(0.75,0.75)\"/>\n"
                + "                </g>\n"
                + "                <g id=\"parallelGateway\" bclass=\"gateway\" oclass=\"gateway_o\" >\n"
                + "                <use xlink:href=\"#gateway\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#parallelMarker\" x=\"0\" y=\"0\" style=\"stroke:none;fill:#d4aa00\" transform=\"scale(1.5,1.5)\"/>\n"
                + "                </g>\n"
                + "                <g id=\"parallelStartGateway\" bclass=\"gateway\" oclass=\"gateway_o\" >\n"
                + "                <use xlink:href=\"#gateway\" x=\"0\" y=\"0\"/>\n"
                + "                <circle r=\"14\" style=\"fill:none;stroke:#d4aa00;stroke-width:2;\"/>\n"
                + "                <use xlink:href=\"#parallelMarker\" x=\"0\" y=\"0\" style=\"stroke:#d4aa00;fill:none;stroke-width:3.5;\"/>\n"
                + "                </g>\n"
                + "                <g id=\"complexGateway\" bclass=\"gateway\" oclass=\"gateway_o\" >\n"
                + "                <use xlink:href=\"#gateway\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#complexMarker\" x=\"0\" y=\"0\" style=\"stroke:none;fill:#d4aa00\" transform=\"scale(0.7,0.7)\"/>\n"
                + "                </g>\n"
                + "                <!--Definición de objetos de datos-->\n"
                + "                <g id=\"data\" bclass=\"itemaware\" oclass=\"itemaware_o\">\n"
                + "                <path transform=\"scale(0.7,0.7) translate(-3,-10)\" d=\"m -25,-25 0,74.61135 58.22841,0 0,-55.2509 -19.88426,-19.36045 -38.34415,0 z\"/>\n"
                + "                <path transform=\"scale(0.7,0.7) translate(-2,-10)\" d=\"m 12.5,-24 0,19.5625 20.0601,0\"/>\n"
                + "                </g>\n"
                + "                <g id=\"dataStore\" bclass=\"itemaware\" oclass=\"itemaware_o\" transform=\"translate(-12,-10)\">\n"
                + "                <path d=\"m 0,0 c -27.75868,0 -50.28125,5.6228 -50.28125,12.5625 0,0.1516 0.0412,0.2871 0.0625,0.4375 l -0.0625,0 0,61.5624 0,0.3125 0.0312,0 c 0.68314,6.7909 22.92187,12.25 50.25,12.25 27.3249498,0 49.53067,-5.4602 50.21875,-12.25 l 0.0312,0 0,-0.3125 0,-61.5624 -0.0312,0 c 0.0212,-0.1501 0.0312,-0.2862 0.0312,-0.4375 0,-6.9397 -22.4913202,-12.5625 -50.25,-12.5625 z\" transform=\"scale(0.6,0.6) translate(22,-24)\"/>\n"
                + "                <path d=\"m 0,0 c 0,6.9397 -22.5028602,12.5654 -50.26153,12.5654 -27.39179,0 -49.73975,-5.4833 -50.25272,-12.33\" transform=\"scale(0.6,0.6) translate(72,-12)\"/>\n"
                + "                </g>	\n"
                + "                <g id=\"dataInput\" bclass=\"itemaware\" oclass=\"itemaware_o\">\n"
                + "                <use xlink:href=\"#data\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#linkMarker\" x=\"-12\" y=\"-17\" style=\"fill:none;stroke:#666666;stroke-width:2.5;\" transform=\"scale(0.8,0.8)\"/>\n"
                + "                </g>\n"
                + "                <g id=\"dataOutput\" bclass=\"itemaware\" oclass=\"itemaware_o\">\n"
                + "                <use xlink:href=\"#data\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#linkMarker\" x=\"-12\" y=\"-17\" style=\"fill:#666666;stroke:none;stroke-width:2.5;\" transform=\"scale(0.8,0.8)\"/>\n"
                + "                </g>\n"
                + "                <!--Definición de tareas-->\n"
                + "                <g id=\"task\" styled=\"stroke:#2c5aa0\">\n"
                + "                <rect x=\"-50\" y=\"-30\" rx=\"10\" ry=\"10\" width=\"100\" height=\"60\" class=\"task\"/>\n"
                + "                </g>\n"
                + "                <g id=\"userTask\" styled=\"stroke:#2c5aa0\">\n"
                + "                <use xlink:href=\"#task\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#userMarker\" transform=\"scale(0.8,0.8) translate(-45, -30)\"/>\n"
                + "                </g>\n"
                + "                <g id=\"serviceTask\" styled=\"stroke:#2c5aa0\">\n"
                + "                <use xlink:href=\"#task\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#serviceMarker\" transform=\"scale(0.8,0.8) translate(-43, -27)\"/>\n"
                + "                </g>\n"
                + "                <g id=\"scriptTask\" styled=\"stroke:#2c5aa0\">\n"
                + "                <use xlink:href=\"#task\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#scriptMarker\" transform=\"scale(0.7,0.7) translate(-60, -24)\"/>\n"
                + "                </g>\n"
                + "                <g id=\"ruleTask\" styled=\"stroke:#2c5aa0\">\n"
                + "                <use xlink:href=\"#task\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#ruleMarker\" transform=\"translate(-35, -17)\" style=\"fill:#2c5aa0;\"/>\n"
                + "                </g>\n"
                + "                <g id=\"sendTask\" styled=\"stroke:#2c5aa0\">\n"
                + "                <use xlink:href=\"#task\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#messageThrowMarker\" transform=\"translate(-35, -19)\" style=\"fill:#2c5aa0;\"/>\n"
                + "                </g>\n"
                + "                <g id=\"receiveTask\" styled=\"stroke:#2c5aa0\">\n"
                + "                <use xlink:href=\"#task\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#messageCatchMarker\" transform=\"translate(-35, -19)\" style=\"fill:none;stroke:#2c5aa0;stroke-width:3;\"/>\n"
                + "                </g>\n"
                + "                <g id=\"manualTask\" styled=\"stroke:#2c5aa0\">\n"
                + "                <use xlink:href=\"#task\" x=\"0\" y=\"0\"/>\n"
                + "                <use xlink:href=\"#manualMarker\" transform=\"scale(0.7,0.7) translate(-60, -35)\" style=\"fill:none;stroke:#2c5aa0;stroke-width:4;\"/>\n"
                + "                </g>\n"
                + "                <!--Definición de swimlanes-->\n"
                + "                <g id=\"pool\" bclass=\"swimlane\" oclass=\"swimlane_o\">\n"
                + "                <rect width=\"600\" x=\"-300\" y=\"-100\" height=\"200\" style=\"fill:#E8E8FF;stroke-width:2\"/>\n"
                + "                <path d=\"m -280,-100 l 0,200\" style=\"fill:none;stroke-width:2;\"/>\n"
                + "                </g>\n"
                + "                </defs>\n"
                + "                </svg>"
                + "<script type=\"text/javascript\">\n"
                + "             Modeler.init('modeler', {mode: 'view', layerNavigation: false}, callbackHandler);\n"
                + "             var zoomFactor = 1.1;\n"
                + "             var panRate = 50;\n"
                + "             //var panRateLR = 50;\n"
                + "             function callbackHandler() {\n"
                + "                 var strJSON = '" + data + "';\n"
                + "                 Modeler.loadProcess(strJSON);\n"
                + "\n                 Modeler._svgSize = getDiagramSize();\n"
                + "                 //fitToScreen();\n"
                + "                 resetZoom();\n"
                + "             }\n"
                + "             function zoomin() {\n"
                + "                 var viewBox = document.getElementById(\"modeler\").getAttribute('viewBox');\n"
                + "                 var viewBoxValues = viewBox.split(' ');\n"
                + "\n"
                + "                 viewBoxValues[2] = parseFloat(viewBoxValues[2]);\n"
                + "                 viewBoxValues[3] = parseFloat(viewBoxValues[3]);\n"
                + "\n"
                + "                 viewBoxValues[2] /= zoomFactor;\n"
                + "                 viewBoxValues[3] /= zoomFactor;\n"
                + "\n"
                + "                 document.getElementById(\"modeler\").setAttribute('viewBox', viewBoxValues.join(' '));\n"
                + "             }\n"
                + "\n"
                + "             function zoomout() {\n"
                + "                 var viewBox = document.getElementById(\"modeler\").getAttribute('viewBox');\n"
                + "                 var viewBoxValues = viewBox.split(' ');\n"
                + "\n"
                + "                 viewBoxValues[2] = parseFloat(viewBoxValues[2]);\n"
                + "                 viewBoxValues[3] = parseFloat(viewBoxValues[3]);\n"
                + "\n"
                + "                 viewBoxValues[2] *= zoomFactor;\n"
                + "                 viewBoxValues[3] *= zoomFactor;\n"
                + "\n"
                + "                 document.getElementById(\"modeler\").setAttribute('viewBox', viewBoxValues.join(' '));\n"
                + "             }\n"
                + "\n"
                + "             function resetZoom() {\n"
                + "                 var el = document.getElementById(\"modeler\");\n"
                + "                 el.setAttribute('viewBox', '0 0 ' + $(\"#modeler\").parent().width() + ' ' + $(\"#modeler\").parent().height());\n"
                + "                 el.setAttribute('width', '1024');\n"
                + "                 el.setAttribute('height', '768');\n"
                + "             }\n"
                + "\n"
                + "             function handlePanning(code) {\n"
                + "                 var viewBox = document.getElementById(\"modeler\").getAttribute('viewBox');\n"
                + "                 var viewBoxValues = viewBox.split(' ');\n"
                + "                 viewBoxValues[0] = parseFloat(viewBoxValues[0]);\n"
                + "                 viewBoxValues[1] = parseFloat(viewBoxValues[1]);\n"
                + "\n"
                + "                 switch (code) {\n"
                + "                     case 'left':\n"
                + "                         viewBoxValues[0] += panRate;\n"
                + "                         break;\n"
                + "                     case 'right':\n"
                + "                         viewBoxValues[0] -= panRate;\n"
                + "                         break;\n"
                + "                     case 'up':\n"
                + "                         viewBoxValues[1] += panRate;\n"
                + "                         break;\n"
                + "                     case 'down':\n"
                + "                         viewBoxValues[1] -= panRate;\n"
                + "                         break;\n"
                + "                 }\n"
                + "                 document.getElementById(\"modeler\").setAttribute('viewBox', viewBoxValues.join(' '));\n"
                + "             }\n"
                + "\n"
                + "             function getDiagramSize() {\n"
                + "                 var cw = 0;\n"
                + "                 var ch = 0;\n"
                + "                 var fx = null;\n"
                + "                 var fy = null;\n"
                + "                 for (var i = 0; i < ToolKit.contents.length; i++) {\n"
                + "                     var obj = ToolKit.contents[i];\n"
                + "                     if (obj.typeOf && (obj.typeOf(\"GraphicalElement\") || obj.typeOf(\"Pool\"))) {\n"
                + "                         if (obj.layer === ToolKit.layer) {\n"
                + "                             if (obj.getX() > cw) {\n"
                + "                                 cw = obj.getX();\n"
                + "                                 fx = obj;\n"
                + "                             }\n"
                + "\n"
                + "                             if (obj.getY() > ch) {\n"
                + "                                 ch = obj.getY();\n"
                + "                                 fy = obj;\n"
                + "                             }\n"
                + "                         }\n"
                + "                     }\n"
                + "                 }\n"
                + "                 cw = cw + fx.getBBox().width;\n"
                + "                 ch = ch + fy.getBBox().height;\n"
                + "\n"
                + "                 var ret = {w: cw, h: ch};\n"
                + "                 return ret;\n"
                + "             }\n"
                + "\n"
                + "             function fitToScreen() {\n"
                + "                 resetZoom();\n"
                + "                 var ws = $(\"#modeler\").parent().width();\n"
                + "                 var hs = $(\"#modeler\").parent().height();\n"
                + "                 var wi = Modeler._svgSize.w;\n"
                + "                 var hi = Modeler._svgSize.h;\n"
                + "\n"
                + "                 if (wi > ws || hi > hs) {\n"
                + "                     var el = document.getElementById(\"modeler\");\n"
                + "                     el.setAttribute('viewBox', '0 0 ' + wi + ' ' + hi);\n"
                + "                     el.setAttribute('width', ws);\n"
                + "                     el.setAttribute('height', hs);\n"
                + "                 }\n"
                + "             }\n"
                + "     </script>";
        return model;
    }

    public static void copyFileFromSWBAdmin(String source, String destination, String fileName) throws FileNotFoundException, IOException {
        InputStream inputStream = SWBPortal.getAdminFileStream(source);
        File css = new File(destination);
        if (!css.exists()) {
            css.mkdirs();
        }

        File file = new File(css.getAbsolutePath() + fileName);
        OutputStream outputStream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
//        outputStream.write(SWBUtils.IO.readInputStream(inputStream).getBytes());
        outputStream.close();
        inputStream.close();
    }

    public static void copyFile(String sourceFile, String destFile) throws IOException {
        InputStream inStream = null;
        OutputStream outStream = null;
        try {
            File afile = new File(sourceFile);
            File bfile = new File(destFile);
            inStream = new FileInputStream(afile);
            outStream = new FileOutputStream(bfile);
            byte[] buffer = new byte[1024];
            int length;
            //copy the file content in bytes 
            while ((length = inStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, length);
            }
            inStream.close();
            outStream.close();
        } catch (IOException e) {
            System.err.println("Error to copy file " + sourceFile + ", " + e.getMessage());
        }
    }

    public static void deleteDerectory(File dir) {
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.isDirectory()) {
                deleteDerectory(file);
                file.delete();
            } else {
                file.delete();
            }
        }
        dir.delete();
    }

    public static void generateImageModel(org.semanticwb.process.model.Process p, String format, String data, String viewBox) {
        try {
            String[] values = viewBox != null ? viewBox.split("\\ ") : "0 0 3800 2020".split("\\ ");
            String basePathDest = SWBPortal.getWorkPath() + "/models/" + p.getProcessSite().getId() + "/Resource/" + p.getId() + "/download/rep_files/";
            File baseDir = new File(basePathDest);
            if (!baseDir.exists()) {
                baseDir.mkdirs();
            }
            FileOutputStream out = new FileOutputStream(baseDir + "/" + p.getId() + "." + format);
            if (format.equals(FORMAT_SVG)) {
                String svg = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
                        + "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n";
                svg += data;
                svg = svg.replace("<g id=\"data\" bclass=\"itemaware\" oclass=\"itemaware_o\">", "<g id=\"data\" bclass=\"itemaware\" oclass=\"itemaware_o\" class=\"itemAware\">");
                svg = svg.replace("<g id=\"dataStore\" bclass=\"itemaware\" oclass=\"itemaware_o\" transform=\"translate(-12,-10)\">", "<g id=\"dataStore\" bclass=\"itemaware\" oclass=\"itemaware_o\" transform=\"translate(-12,-10)\" class=\"itemAware\">");
                svg = svg.replace("style=\"display: none;\"", "");
                out.write(svg.getBytes("ISO-8859-1"));
            } else if (format.equals(FORMAT_PNG)) {
                InputStream strStream = new ByteArrayInputStream(data.getBytes("ISO-8859-1"));
                TranscoderInput ti = new TranscoderInput(strStream/*svgFile.toURI().toString()*/);
                TranscoderOutput to = new TranscoderOutput(out);

                PNGTranscoder t = new PNGTranscoder();
                t.addTranscodingHint(PNGTranscoder.KEY_WIDTH, new Float(values[2]) + 2048);
                t.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, new Float(values[3]) + 1292);
                t.addTranscodingHint(PNGTranscoder.KEY_FORCE_TRANSPARENT_WHITE, Boolean.TRUE);
                t.transcode(ti, to);
            }
            out.flush();
            out.close();

        } catch (Exception e) {
        }
    }

    public static void saveFile(String src, String dest) {
        try {

            URL url = new URL(src);
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(dest);

            byte[] b = new byte[2048];
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }
            is.close();
            os.close();
        } catch (Exception e) {
            log.error("Error on saveFile, " + e.getMessage() + ", " + e.getCause());
        }
    }

    public static void addTextHtmlToRtf(String html, com.lowagie.text.Document doc, SectionElement se, WebSite model, org.semanticwb.process.model.Process p) {
        try {
            String basePath = SWBPortal.getWorkPath() + "/models/" + model.getId() + "/Resource/" + p.getId() + "/download/";
            File dir = new File(basePath + "rep_files/img/" + se.getId() + "/");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            org.jsoup.nodes.Document d = Jsoup.parse(html);
            Elements elements = d.select("[src]");
            html = d.html();
            int i = 1;
            org.jsoup.nodes.Document d1 = Jsoup.parse(html.substring(0));
            doc.add(new Paragraph(d1.text()));   
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

                    if (width != null && height != null && !width.isEmpty() && !height.isEmpty()) {
                        image.scaleToFit(Float.parseFloat(width), Float.parseFloat(height));
                    }
                    doc.add(image);
                    i++;
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
