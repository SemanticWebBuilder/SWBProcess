package org.semanticwb.process.resources.documentation;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Descriptiveable;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.VersionInfo;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.api.SWBResourceModes;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.api.SWBResourceURLImp;
import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.*;
import org.semanticwb.process.model.documentation.Activity;
import org.semanticwb.process.model.documentation.*;
import org.semanticwb.process.resources.ProcessFileRepository;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static org.semanticwb.process.resources.utils.SWPUtils.copyFile;

public class Util {
    private static final Logger LOG = SWBUtils.getLogger(Util.class);
    public static final String TITLE = "title";
    public static final String CLASS_NAME = "className";
    public static final String PROPID = "propid";

    private Util () {
        //This class is not intended to be instantiated
    }

    /**
     * Obtiene un documento XML con la información de la instancia de la documentación.
     * @param request Obheto HTTPServletRequest para construir URLS
     * @param basePath ruta base para la exportación de archivos.
     * @param export Indica si la información en el documento XML estará procesada para exportación estática.
     * @return Documento XML con la información de la instancia de la documentación.
     */
    public static Document getXMLDocument(DocumentationInstance inst, HttpServletRequest request, String basePath, boolean export) {
        Document doc = SWBUtils.XML.getNewDocument();
        Process p = inst.getProcessRef();
        Element root = doc.createElement("root");
        root.setAttribute(TITLE, p.getTitle());
        root.setAttribute("uri", p.getURI());
        root.setAttribute("export", String.valueOf(export));
        root.setAttribute("contextPath", SWBPortal.getContextPath());
        doc.appendChild(root);
        String colorTask = "";
        boolean hasModel = false;

        try {
            Iterator<DocumentSectionInstance> itdsi = SWBComparator.sortSortableObject(inst.listDocumentSectionInstances());
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
                    section.setAttribute(CLASS_NAME, cls.getName());
                    section.setAttribute(TITLE, dsi.getSecTypeDefinition().getTitle());
                    section.setAttribute("uri", dsi.getURI());
                    section.setAttribute("idSection", dsi.getId());
                    section.setAttribute("url", cls.getName() + dsi.getId());

                    Iterator<SectionElement> itse = SWBComparator.sortSortableObject(dsi.listDocuSectionElementInstances());
                    int count = 1;
                    while (itse.hasNext()) {//Instances
                        boolean addInstance = true;
                        section.appendChild(doc.createTextNode("\n\t\t"));
                        SectionElement se = itse.next();

                        Element instance = doc.createElement("instance");
                        instance.setAttribute("id", se.getId());
                        instance.setAttribute("uri", se.getURI());
                        instance.setAttribute(CLASS_NAME, cls.getName());
                        if (cls.isSubClass(Instantiable.swpdoc_Instantiable, false)) {//Elements Instantiable

                            String[] props = dsi.getSecTypeDefinition().getVisibleProperties().split("\\|");
                            for (String propt : props) {

                                String idprop = propt.substring(propt.indexOf(';') + 1, propt.length());
                                String titleprop = propt.substring(0, propt.indexOf(';'));
                                SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(idprop);
                                String value = "";
                                Element property = doc.createElement("property");
                                instance.appendChild(property);
                                property.setAttribute(TITLE, titleprop);
                                property.setAttribute(PROPID, idprop);

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
                                    if (!ref.hasRepositoryReference()) addInstance = false;
                                    if (!addInstance) continue;

                                    RepositoryDirectory rd = ref.getRefRepository().getRepositoryDirectory();
                                    SWBResourceURL urld = new SWBResourceURLImp(request, rd.getResource(), rd, SWBResourceModes.UrlType_RENDER);

                                    RepositoryElement re = ref.getRefRepository();
                                    VersionInfo vi = ref.getVersion() != null ? ref.getVersion() : re.getLastVersion();
                                    urld.setMode(ProcessFileRepository.MODE_GETFILE).setCallMethod(SWBResourceURL.Call_DIRECT).setParameter("fid", ref.getRefRepository().getId());
                                    urld.setParameter("verNum", vi.getVersionNumber() + "");
                                    String urlDownload = urld.toString();
                                    if (export) { // add file to zip
                                        String basePathRepo = SWBPortal.getWorkPath() + "/models/" + p.getProcessSite().getId() + "/swp_RepositoryFile/" + ref.getRefRepository().getId() + "/" + vi.getVersionNumber() + "/";
                                        File baseDir = new File(basePathRepo);
                                        String basePathDest = basePath;

                                        File repFile = new File(basePathDest + "rep_files/" + ref.getRefRepository().getId() + "/" + vi.getVersionNumber() + "/");

                                        if (!repFile.exists()) {
                                            repFile.mkdirs();
                                        }

                                        if (baseDir.isDirectory()) {
                                            File[] files = baseDir.listFiles();
                                            if (null != files && files.length > 0) {
                                                File file = files[0];
                                                urlDownload = "rep_files/" + ref.getRefRepository().getId() + "/" + vi.getVersionNumber() + "/" + file.getName();
                                                copyFile(file.getAbsolutePath(), repFile.getAbsolutePath() + "/" + file.getName());
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
                            String html = ft.getText();
                            org.jsoup.nodes.Document d = null;
                            if (html != null) {
                                d = Jsoup.parse(html);
                                //Fix relative path in src attrs
                                Elements elements = d.select("[src]");
                                for (org.jsoup.nodes.Element src : elements) {
                                    if (src.tagName().equals("img") || src.tagName().equals("iframe")) {
                                        String attr = src.attr("src");
                                        if (attr.contains("../..")) {
                                            src.attr("src", src.attr("src").substring(5));
                                        }
                                        if (export && !attr.contains("http")) {
                                            File file = new File(SWBPortal.getWorkPath() + "/" + src.attr("src").substring(5));
                                            String basePathDest = basePath;
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
                            instance.appendChild(doc.createTextNode((d != null ? d.body().html() : "")));
                        } else if (cls.equals(Activity.sclass)) {//Activity
                            Activity a = (Activity) se;
                            Element property = doc.createElement("property");
                            instance.appendChild(property);
                            property.setAttribute(TITLE, Descriptiveable.swb_title.getLabel());
                            property.setAttribute(PROPID, Descriptiveable.swb_title.getPropId());
                            property.appendChild(doc.createTextNode(a.getTitle() != null ? a.getTitle() : ""));

                            Element propertyd = doc.createElement("propertyd");
                            instance.appendChild(propertyd);
                            propertyd.setAttribute(TITLE, Descriptiveable.swb_description.getLabel());
                            propertyd.setAttribute(PROPID, Descriptiveable.swb_description.getPropId());

                            String html = a.getDescription();
                            org.jsoup.nodes.Document d = null;
                            if (html != null) {
                                d = Jsoup.parse(html);
                                Elements elements = d.select("[src]");
                                for (org.jsoup.nodes.Element src : elements) {
                                    if (src.tagName().equals("img") || src.tagName().equals("iframe")) {
                                        String attr = src.attr("src");
                                        if (attr.contains("../..")) {
                                            src.attr("src", src.attr("src").substring(5));
                                        }
                                        if (export && !attr.contains("http")) {
                                            File file = new File(SWBPortal.getWorkPath() + "/" + src.attr("src").substring(5));
                                            String basePathDest = basePath;
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
                            propertyd.appendChild(doc.createTextNode(d != null ? d.body().html() : ""));

                            instance.setAttribute("fill", a.getFill());
                            instance.setAttribute("id", a.getActivityRef().getProcessActivity().getId());

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
                                if (ser.getSectionElement() != null) {
                                    String uris;
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
                                eds.setAttribute(TITLE, ds.getTitle());
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
                                    related.setAttribute(CLASS_NAME, scls.getName());
                                    SectionElement ser = (SectionElement) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uri);
                                    SemanticObject so = SemanticObject.createSemanticObject(uri);
                                    if (so != null) {
                                        for (String propt : props) {
                                            String idprop = propt.substring(propt.indexOf(';') + 1, propt.length());
                                            String titleprop = propt.substring(0, propt.indexOf(';'));
                                            SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(idprop);
                                            Element erprop = doc.createElement("relatedprop");
                                            related.appendChild(erprop);
                                            erprop.setAttribute(TITLE, titleprop);
                                            erprop.setAttribute(PROPID, idprop);
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
                                                RepositoryElement re = ref.getRefRepository();
                                                VersionInfo vi = ref.getVersion() != null ? ref.getVersion() : re.getLastVersion();
                                                urld.setParameter("verNum", vi.getVersionNumber() + "");

                                                String urlDownload = urld.toString();
                                                if (export) { // add file to zip
                                                    String basePathRep = SWBPortal.getWorkPath() + "/models/" + p.getProcessSite().getId() + "/swp_RepositoryFile/" + ref.getRefRepository().getId() + "/" + vi.getVersionNumber() + "/";
                                                    File baseDir = new File(basePathRep);
                                                    String basePathDest = basePath;
                                                    File repFile = new File(basePathDest + "rep_files/" + ref.getRefRepository().getId() + "/" + vi.getVersionNumber() + "/");
                                                    if (!repFile.exists()) {
                                                        repFile.mkdirs();
                                                    }
                                                    if (baseDir.isDirectory()) {
                                                        File[] files = baseDir.listFiles();
                                                        if (null != files && files.length > 0) {
                                                            File file = files[0];
                                                            urlDownload = "rep_files/" + ref.getRefRepository().getId() + "/" + vi.getVersionNumber() + "/" + file.getName();
                                                            copyFile(file.getAbsolutePath(), repFile.getAbsolutePath() + "/" + file.getName());
                                                        }
                                                    }
                                                }
                                                if (re instanceof RepositoryFile) {
                                                    value = "<a target=\"_blank\" href=\"" + urlDownload + "\">" + ref.getRefRepository().getTitle() + " <i class=\"fa fa-download\"></i></a>";
                                                } else if (re instanceof RepositoryURL) {
                                                    value = "<a target=\"_blank\" href=\"" + vi.getVersionFile() + "\">" + ref.getRefRepository().getTitle() + " <i class=\"fa fa-external-link\"></i></a>";
                                                }
                                            }
                                            erprop.appendChild(doc.createTextNode(value));
                                        }
                                    }
                                }

                            }
                        }
                        if (addInstance) {
                            instance.setAttribute("count", count + "");
                            count++;
                            section.appendChild(instance);
                        }
                    }
                }
            }
            if (hasModel) {
                Process process = inst.getProcessRef();
                Element model = doc.createElement("model");
                model.setAttribute("id", process.getId());
                root.appendChild(doc.createTextNode("\n\t"));
                String data = process.getData();
                model.appendChild(doc.createTextNode(data));
                root.appendChild(model);
                root.appendChild(doc.createTextNode("\n\t"));
                if (colorTask.length() > 0) {
                    String[] tasks = colorTask.split("\\|");
                    for (String task : tasks) {
                        Element colorTaskE = doc.createElement("colorTask");
                        colorTaskE.setAttribute("id", task.substring(0, task.lastIndexOf(';')));
                        colorTaskE.setAttribute("color", task.substring(task.lastIndexOf(';') + 1, task.length()));
                        root.appendChild(colorTaskE);
                    }
                }
            }

        } catch (DOMException doe) {
            LOG.error("Error on getDocument, DOMEXception" + doe);
        } catch (IOException ioe) {
            LOG.error("Error on getDocument, IOEXception" + ioe);
        }

        return doc;
    }
}
