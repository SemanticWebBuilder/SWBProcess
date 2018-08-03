package org.semanticwb.process.resources.documentation.model;

import static org.semanticwb.process.utils.SWPUtils.copyFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
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
import org.semanticwb.process.model.GraphicalElement;
import org.semanticwb.process.model.ProcessSite;
import org.semanticwb.process.model.Task;
import org.semanticwb.process.model.Process;
import org.semanticwb.process.model.RepositoryDirectory;
import org.semanticwb.process.model.RepositoryElement;
import org.semanticwb.process.model.RepositoryFile;
import org.semanticwb.process.model.RepositoryURL;
import org.semanticwb.process.model.SubProcess;
import org.semanticwb.process.resources.ProcessFileRepository;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Clase que encapsula las propiedades de una instancia particular de la documentación de un proceso.
 */
public class DocumentationInstance extends org.semanticwb.process.resources.documentation.model.base.DocumentationInstanceBase {
    private static final Logger log = SWBUtils.getLogger(DocumentationInstance.class);
    /**
     * Constructor.
     * @param base
     */
    public DocumentationInstance(org.semanticwb.platform.SemanticObject base) {
        super(base);
    }

    /**
     * Obtiene un mapa con las instancias de secciones asociadas a una instancia de documentación.
     * @param instance Instancia de documentación.
     * @return Mapa con instancias de recciones asociadas a la instancia de documentación.
     */
    public static Map getDocumentSectionInstanceMap(DocumentationInstance instance) {
        Map map = new HashMap();
        Iterator<DocumentSectionInstance> itdsi = instance.listDocumentSectionInstances();
        while (itdsi.hasNext()) {
            DocumentSectionInstance dsit = itdsi.next();
            DocumentSection ds = dsit.getSecTypeDefinition();
            if (null != ds) {
                dsit.setIndex(ds.getIndex());
                map.put(dsit.getSecTypeDefinition().getURI(), dsit.getURI());
            } else {
                dsit.remove();
            }
        }
        return map;
    }

    /**
     * Obtiene la instancia de documentación asociada al proceso especificado.
     * @param process Proceso de interés
     * @param container Contenedor de versiones de la documentación asociado al proceso.
     * @return Instancia de la documentación del proceso especificado.
     */
    public static DocumentationInstance getDocumentationInstanceByProcess(Process process, TemplateContainer container) {
        DocumentationInstance di = null;
        ProcessSite model = process.getProcessSite();
        DocumentTemplate dt = container.getActualTemplate();
        Iterator<DocumentationInstance> itdi = DocumentationInstance.ClassMgr.listDocumentationInstanceByProcessRef(process);
        if (itdi.hasNext()) {//Obtener DocumentationInstance de plantilla actual
            while (itdi.hasNext()) {
                DocumentationInstance dit = itdi.next();
                if (dit.getDocTypeDefinition() != null
                        && dt != null
                        && dit.getDocTypeDefinition().getURI().equals(dt.getURI())) {
                    di = dit;
                    Map map = getDocumentSectionInstanceMap(di);
                    Iterator<DocumentSection> itds = di.getDocTypeDefinition().listDocumentSections();
                    while (itds.hasNext()) {
                        DocumentSection ds = itds.next();
                        if (!map.containsKey(ds.getURI())) {
                            DocumentSectionInstance dsi = DocumentSectionInstance.ClassMgr.createDocumentSectionInstance(model);
                            dsi.setSecTypeDefinition(ds);
                            dsi.setDocumentationInstance(dit);
                            di.addDocumentSectionInstance(dsi);
                            dsi.setIndex(ds.getIndex());

                            SemanticClass cls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(dsi.getSecTypeDefinition().getSectionType().getURI());
                            if (FreeText.sclass.getClassId().equals(cls.getClassId())) {
                                FreeText ft = FreeText.ClassMgr.createFreeText(model);
                                ft.setText("");
                                ft.setDocumentTemplate(dt);
                                ft.setDocumentSectionInst(dsi);
                                SectionElement se = (SectionElement) ft.getSemanticObject().createGenericInstance();
                                dsi.addDocuSectionElementInstance(se);
                            }
                            if (Activity.sclass.getClassId().equals(cls.getClassId())) {
                                Iterator<GraphicalElement> itge = di.getProcessRef().listAllContaineds();
                                while (itge.hasNext()) {
                                    GraphicalElement ge = itge.next();
                                    if (ge instanceof org.semanticwb.process.model.SubProcess || ge instanceof Task) {//TODO:Revisar esto para permitir editar otras actividades
                                        String urige = ge.getURI();
                                        org.semanticwb.process.model.Activity act = (org.semanticwb.process.model.Activity) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(urige);
                                        ActivityRef actRef = ActivityRef.ClassMgr.createActivityRef(model);
                                        actRef.setProcessActivity(act);
                                        Activity actFin = Activity.ClassMgr.createActivity(model);
                                        actFin.setTitle(act.getTitle());
                                        actFin.setDescription(act.getDescription());
                                        actFin.setDocumentTemplate(dt);
                                        actFin.setActivityRef(actRef);
                                        actFin.setIndex(ge.getIndex());
                                        actFin.setDocumentSectionInst(dsi);
                                        dsi.addDocuSectionElementInstance(actFin);
                                    }
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }

        if (di == null) { //Crear DocumentationInstance
            di = DocumentationInstance.ClassMgr.createDocumentationInstance(process.getProcessSite());
            di.setDocTypeDefinition(dt);
            di.setProcessRef(process);

            Iterator<DocumentSection> itdsi = di.getDocTypeDefinition().listDocumentSections();
            while (itdsi.hasNext()) {
                DocumentSection ds = itdsi.next();
                DocumentSectionInstance dsi = DocumentSectionInstance.ClassMgr.createDocumentSectionInstance(model);
                dsi.setSecTypeDefinition(ds);
                dsi.setDocumentationInstance(di);
                di.addDocumentSectionInstance(dsi);
                dsi.setIndex(ds.getIndex());

                SemanticClass cls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(dsi.getSecTypeDefinition().getSectionType().getURI());
                if (FreeText.sclass.getClassId().equals(cls.getClassId())) {
                    FreeText ft = FreeText.ClassMgr.createFreeText(model);
                    ft.setText("");
                    dsi.addDocuSectionElementInstance(ft);
                    ft.setParentSection(ds);
                    ft.setDocumentTemplate(dt);
                    ft.setDocumentSectionInst(dsi);
                }
                if (Activity.sclass.getClassId().equals(cls.getClassId())) {
                    Iterator<GraphicalElement> itge = di.getProcessRef().listAllContaineds();
                    while (itge.hasNext()) {
                        GraphicalElement ge = itge.next();
                        if (ge instanceof org.semanticwb.process.model.SubProcess || ge instanceof Task) {//TODO:Revisar esto para permitir editar otras actividades
                            String urige = ge.getURI();
                            org.semanticwb.process.model.Activity act = (org.semanticwb.process.model.Activity) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(urige);

                            ActivityRef actRef = ActivityRef.ClassMgr.createActivityRef(model);
                            actRef.setProcessActivity(act);
                            Activity actFin = Activity.ClassMgr.createActivity(model);
                            actFin.setTitle(act.getTitle());
                            actFin.setDescription(act.getDescription());
                            actFin.setDocumentTemplate(dt);
                            actFin.setActivityRef(actRef);
                            actFin.setIndex(ge.getIndex());
                            dsi.addDocuSectionElementInstance(actFin);
                            actFin.setParentSection(ds);
                            actFin.setDocumentSectionInst(dsi);
                        }
                    }
                }
            }
        }
        return di;
    }

    /**
     * Actualiza la información asociada a las actividades de un proceso documentado.
     * @param process Proceso asociado a la documentación.
     * @param container Contenedor de versiones asociado al proceso.
     * @param instance Instancia de la sección de actividades en la documentación.
     */
    public static void updateActivityFromProcess(Process process, TemplateContainer container, DocumentSectionInstance instance) {
        Iterator<SectionElement> itse = SWBComparator.sortSortableObject(instance.listDocuSectionElementInstances());
        List<GraphicalElement> listPa = new ArrayList<GraphicalElement>();
        while (itse.hasNext()) {
            SectionElement se = itse.next();
            Activity act = (Activity) se.getSemanticObject().createGenericInstance();
            if (act.getActivityRef().getProcessActivity() != null) {
                act.setTitle(act.getActivityRef().getProcessActivity().getTitle());
                //act.setDescription(act.getActivityRef().getProcessActivity().getDescription());
                act.setIndex(act.getActivityRef().getProcessActivity().getIndex());
                act.setParentSection(instance.getSecTypeDefinition());
                listPa.add(act.getActivityRef().getProcessActivity());
            } else {
                se.remove();
                instance.removeDocuSectionElementInstance(se);
            }
        }
        ProcessSite model = process.getProcessSite();
        DocumentTemplate dt = container.getActualTemplate();
        Iterator<GraphicalElement> itge = process.listAllContaineds();
        while (itge.hasNext()) {
            GraphicalElement ge = itge.next();
            if ((ge instanceof SubProcess || ge instanceof Task)) {
                if (!listPa.contains(ge)) {
                    String urige = ge.getURI();
                    org.semanticwb.process.model.Activity act = (org.semanticwb.process.model.Activity) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(urige);
                    ActivityRef actRef = ActivityRef.ClassMgr.createActivityRef(model);
                    actRef.setProcessActivity(act);
                    Activity actFin = Activity.ClassMgr.createActivity(model);
                    actFin.setDocumentTemplate(dt);
                    actFin.setTitle(act.getTitle());
                    actFin.setActivityRef(actRef);
                    actFin.setIndex(ge.getIndex());
                    actFin.setDocumentSectionInst(instance);
                    instance.addDocuSectionElementInstance(actFin);
                    actFin.setParentSection(instance.getSecTypeDefinition());
                }
            }
        }
    }

    /**
     * Obtiene un documento XML con la información de la instancia de la documentación.
     * @param request Obheto HTTPServletRequest para construir URLS
     * @param basePath ruta base para la exportación de archivos.
     * @param export Indica si la información en el documento XML estará procesada para exportación estática.
     * @return Documento XML con la información de la instancia de la documentación.
     */
    public Document getXMLDocument(HttpServletRequest request, String basePath, boolean export) {
        Document doc = SWBUtils.XML.getNewDocument();
        Process p = getProcessRef();
        Element root = doc.createElement("root");
        root.setAttribute("title", p.getTitle());
        root.setAttribute("uri", p.getURI());
        root.setAttribute("export", String.valueOf(export));
        root.setAttribute("contextPath", SWBPortal.getContextPath());
        doc.appendChild(root);
        String colorTask = "";
        boolean hasModel = false;

        try {
            Iterator<DocumentSectionInstance> itdsi = SWBComparator.sortSortableObject(listDocumentSectionInstances());
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
                    section.setAttribute("idSection", dsi.getId());
                    section.setAttribute("url", cls.getName() + dsi.getId());

                    Iterator<SectionElement> itse = SWBComparator.sortSortableObject(dsi.listDocuSectionElementInstances());
                    int count = 1;
                    while (itse.hasNext()) {//Instances
                        boolean addInstance = true;
                        section.appendChild(doc.createTextNode("\n\t\t"));
                        SectionElement se = itse.next();
//                        System.out.println("Procesando elemento "+se.getURI());
                        Element instance = doc.createElement("instance");
                        instance.setAttribute("id", se.getId());
                        instance.setAttribute("uri", se.getURI());
                        instance.setAttribute("className", cls.getName());
                        if (cls.isSubClass(Instantiable.swpdoc_Instantiable, false)) {//Elements Instantiable
//                            System.out.println("  Es un instanciable");
                            String[] props = dsi.getSecTypeDefinition().getVisibleProperties().split("\\|");
                            for (String propt : props) {
//                                System.out.println("  -Agregando propiedad "+propt);
                                String idprop = propt.substring(propt.indexOf(";") + 1, propt.length());
                                String titleprop = propt.substring(0, propt.indexOf(";"));
                                SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(idprop);
                                String value = "";
                                Element property = doc.createElement("property");
                                instance.appendChild(property);
                                property.setAttribute("title", titleprop);
                                property.setAttribute("propid", idprop);
//                                System.out.println("  -SemanticProperty: "+prop);
                                if (prop != null && !prop.getPropId().equals(Referable.swpdoc_file.getPropId())) {
//                                    System.out.println("  -Prop is not file ref");
                                    value = (se.getSemanticObject().getProperty(prop) != null ? se.getSemanticObject().getProperty(prop) : "");
//                                    System.out.println("  -Prop value: "+value);
                                } else {//Show URL download file
//                                    System.out.println("  -Prop is file ref");
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
//                                    System.out.println("addInstance: "+addInstance);
                                    if (!addInstance) continue;

//                                    System.out.println("  ...Continue adding element");
                                    RepositoryDirectory rd = ref.getRefRepository().getRepositoryDirectory();
                                    SWBResourceURL urld = new SWBResourceURLImp(request, rd.getResource(), rd, SWBResourceModes.UrlType_RENDER);

                                    RepositoryElement re = (RepositoryElement) ref.getRefRepository();
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
//                                System.out.println("Adding property with val: "+value);
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
                            property.setAttribute("title", Descriptiveable.swb_title.getLabel());
                            property.setAttribute("propid", Descriptiveable.swb_title.getPropId());
                            property.appendChild(doc.createTextNode(a.getTitle() != null ? a.getTitle() : ""));

                            Element propertyd = doc.createElement("propertyd");
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
                        if (addInstance) {
                            instance.setAttribute("count", count + "");
                            count++;
                            section.appendChild(instance);
                        }
                    }
                }
            }
            if (hasModel) {
                Process process = getProcessRef();
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
                        colorTaskE.setAttribute("id", task.substring(0, task.lastIndexOf(";")));
                        colorTaskE.setAttribute("color", task.substring(task.lastIndexOf(";") + 1, task.length()));
                        root.appendChild(colorTaskE);
                    }
                }
            }

        } catch (DOMException doe) {
            log.error("Error on getDocument, DOMEXception" + doe);
        } catch (IOException ioe) {
            log.error("Error on getDocument, IOEXception" + ioe);
        }

//        try {
//            // Use a Transformer for output
//            TransformerFactory tFactory = TransformerFactory.newInstance();
//            Transformer transformer = tFactory.newTransformer();
//
//            DOMSource source = new DOMSource(doc);
//            StreamResult result = new StreamResult(new FileOutputStream(new File("/Users/hasdai/Documents/xmlDocumentation.xml")));
//            transformer.transform(source, result);
//        } catch (Exception ex) {
//
//        }

        return doc;
    }

    /**
     * Crea una ruta temporal para las descargas de documentación.
     * @return Ruta a la carpeta de trabajo de la instancia, con un modificador único para archivos temporales.
     */
    public String getDownloadTempPath() {
        String uid = UUID.randomUUID().toString();
        return SWBPortal.getWorkPath()+getProcessRef().getWorkPath()+"/docs/download/"+uid+"/";
    }
}
