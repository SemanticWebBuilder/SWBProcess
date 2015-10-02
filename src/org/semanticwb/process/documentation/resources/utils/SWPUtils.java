package org.semanticwb.process.documentation.resources.utils;

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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
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
import org.semanticwb.process.model.ProcessGroup;
import org.semanticwb.process.model.RepositoryDirectory;
import org.semanticwb.process.model.RepositoryElement;
import org.semanticwb.process.model.RepositoryFile;
import org.semanticwb.process.model.RepositoryURL;
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
            ProcessGroup group = ProcessGroup.ClassMgr.getProcessGroup(idpg, model);
            if (null != group) {
                Iterator<org.semanticwb.process.model.Process> iterator = group.listProcesses();
                while (iterator.hasNext()) {
                    org.semanticwb.process.model.Process process = iterator.next();
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
        root.setAttribute("export", String.valueOf(export));
        root.setAttribute("contextPath", SWBPortal.getContextPath());
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
                            html = html.replace("&mdash;", "-");
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
                model.setAttribute("id", process.getId());
                root.appendChild(doc.createTextNode("\n\t"));
                String data = process.getData();
                model.appendChild(doc.createTextNode(data));
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
