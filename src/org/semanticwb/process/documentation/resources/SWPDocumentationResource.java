/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.documentation.resources;

import com.lowagie.text.Anchor;
import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.rtf.RtfWriter2;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.util.IOUtils;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.Descriptiveable;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.Sortable;
import org.semanticwb.model.User;
import org.semanticwb.model.VersionInfo;
import org.semanticwb.model.WebPage;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.api.GenericAdmResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.process.documentation.model.Activity;
import org.semanticwb.process.documentation.model.Definition;
import org.semanticwb.process.documentation.model.DocumentSection;
import org.semanticwb.process.documentation.model.DocumentSectionInstance;
import org.semanticwb.process.documentation.model.Documentation;
import org.semanticwb.process.documentation.model.DocumentationInstance;
import org.semanticwb.process.documentation.model.ElementReferable;
import org.semanticwb.process.documentation.model.ElementReference;
import org.semanticwb.process.documentation.model.Format;
import org.semanticwb.process.documentation.model.FreeText;
import org.semanticwb.process.documentation.model.Instantiable;
import org.semanticwb.process.documentation.model.Model;
import org.semanticwb.process.documentation.model.Referable;
import org.semanticwb.process.documentation.model.Reference;
import org.semanticwb.process.documentation.model.SectionElement;
import org.semanticwb.process.documentation.model.SectionElementRef;
import org.semanticwb.process.documentation.resources.utils.SWPUtils;
import static org.semanticwb.process.documentation.resources.utils.SWPUtils.copyFile;
import static org.semanticwb.process.documentation.resources.utils.SWPUtils.copyFileFromSWBAdmin;
import static org.semanticwb.process.documentation.resources.utils.SWPUtils.deleteDerectory;
import org.semanticwb.process.model.RepositoryDirectory;
import org.semanticwb.process.model.RepositoryElement;
import org.semanticwb.process.model.RepositoryFile;
import org.semanticwb.process.model.RepositoryURL;

/**
 *
 * @author carlos.alvarez
 */
public class SWPDocumentationResource extends GenericAdmResource {

    private final Logger log = SWBUtils.getLogger(SWPDocumentationResource.class);

    public final static String ACTION_ADD_INSTANTIABLE = "a_ain";
    public final static String ACTION_EDIT_INSTANTIABLE = "a_ein";
    public final static String ACTION_ADD_RELATE = "a_arel";
    public final static String ACTION_EDIT_TEXT = "a_edt";
    public final static String ACTION_EDIT_DESCRIPTION = "a_edes";
    public final static String ACTION_SAVE_VERSION = "a_sver";
    public final static String ACTION_RELATED_ACTIVITY = "a_ract";
    public final static String ACTION_UPDATE_FILL = "a_ufill";
    public final static String ACTION_DOWNLOAD = "a_down";
    public final static String ACTION_UPLOAD_PICTURE = "a_upp";
    public final static String MODE_EDIT_INSTANTIABLE = "m_ein";
    public final static String MODE_EDIT_DESCRIPTION = "m_edes";
    public final static String MODE_ACTUALIZA_TAB = "m_act";
    public final static String MODE_RELATED = "m_rel";
    public final static String MODE_RELATED_ACTIVITY = "m_relact";
    public final static String MODE_TRACEABLE = "m_trac";
    public final static String MODE_VERSION = "m_nver";
    public final static String MODE_MSG_VERSION = "m_msgv";
    public final static String MODE_DOWNLOAD = "m_down";

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        WebSite model = response.getWebPage().getWebSite();
        try {
            //Leer parámetros
            String uridsi = "uridsi";
            String urise = "urise";
            String uriscls = "scls";
            String props = "props";
            String configData = DocumentSection.swpdoc_configData.getPropId();
            Map values = new HashMap();
            InputStream is = null;
            String filename = null;
            String link = "lfile";
            String urire = "urire";
            RepositoryDirectory rd = null;
            Referable referable = null;
            if (ServletFileUpload.isMultipartContent(request)) {
                List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
                String encoding = "UTF-8";
                for (FileItem item : multiparts) {
                    if (item.isFormField()) {
                        if (item.getFieldName().equals(uridsi)) {
                            uridsi = SWBUtils.TEXT.decode(item.getString(), encoding);
                        }
                        if (item.getFieldName().equals(urise)) {
                            urise = SWBUtils.TEXT.decode(item.getString(), encoding);
                        }
                        if (item.getFieldName().equals(uriscls)) {
                            uriscls = SWBUtils.TEXT.decode(item.getString(), encoding);
                        }
                        if (item.getFieldName().equals(props)) {
                            props = SWBUtils.TEXT.decode(item.getString(), encoding);
                        }
                        if (item.getFieldName().equals(props)) {
                            props = SWBUtils.TEXT.decode(item.getString(), encoding);
                        }
                        if (item.getFieldName().equals(configData)) {
                            configData = SWBUtils.TEXT.decode(item.getString(), encoding);
                        }
                        if (item.getFieldName().equals(link)) {
                            link = SWBUtils.TEXT.decode(item.getString(), encoding);
                        }
                        if (item.getFieldName().equals(urire)) {
                            urire = SWBUtils.TEXT.decode(item.getString(), encoding);
                        }
                        values.put(item.getFieldName(), SWBUtils.TEXT.decode(item.getString(), encoding));
                    } else {

                        is = item.getInputStream();
                        filename = SWBUtils.TEXT.decode(item.getName(), "UTF-8").trim();
                    }
                }
            }
            if (action.equals(ACTION_ADD_INSTANTIABLE)) {
                DocumentSectionInstance dsi = (DocumentSectionInstance) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uridsi);
                if (dsi != null) {
                    SemanticClass scls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(uriscls);
                    SemanticObject semObj = null;
                    String clsId = scls.getClassId();
                    if (clsId.equals(Definition.sclass.getClassId())
                            || clsId.equals(Format.sclass.getClassId())
                            || clsId.equals(Reference.sclass.getClassId())) { //Referable
                        SectionElement se = null;
                        if (is != null && !configData.equals(DocumentSection.swpdoc_configData.getPropId())) {
                            rd = (RepositoryDirectory) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(configData);
                            if (clsId.equals(Definition.sclass.getClassId())) { //Definition
                                Definition def = Definition.ClassMgr.createDefinition(model);
                                semObj = def.getSemanticObject();
                                referable = (Referable) def;
                                se = (SectionElement) def;
                            } else if (clsId.equals(Format.sclass.getClassId())) {
                                Format def = Format.ClassMgr.createFormat(model);
                                semObj = def.getSemanticObject();
                                referable = (Referable) def;
                                se = (SectionElement) def;
                            } else {
                                Reference def = Reference.ClassMgr.createReference(model);
                                semObj = def.getSemanticObject();
                                referable = (Referable) def;
                                se = (SectionElement) def;
                            }
                            se.setDocumentSectionInst(dsi);
                            se.setDocumentTemplate(dsi.getSecTypeDefinition().getParentTemplate());
                            se.setParentSection(dsi.getSecTypeDefinition());
                            dsi.addDocuSectionElementInstance(se);
                            urise = se.getURI();
                        }
                    } else { // Instantiable
                        SemanticObject semOb = model.getSemanticModel().createSemanticObjectById(model.getSemanticModel().getCounter(scls) + "", scls);
                        SectionElement se = (SectionElement) semOb.createGenericInstance();
                        urise = se.getURI();
                        se.setDocumentSectionInst(dsi);
                        se.setDocumentTemplate(dsi.getSecTypeDefinition().getParentTemplate());
                        se.setParentSection(dsi.getSecTypeDefinition());
                        dsi.addDocuSectionElementInstance(se);
                        semObj = se.getSemanticObject();
                    }
                    if (semObj != null) {
                        String[] addprops = props.split("\\|");
                        for (String propt : addprops) {
                            String idprop = propt.substring(propt.indexOf(";") + 1, propt.length());
                            SemanticProperty sp = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(idprop);
                            if (!idprop.equals(Sortable.swb_index.getPropId())) {
                                semObj.setProperty(sp, values.containsKey(idprop) ? values.get(idprop).toString() : null);
                            } else {
                                int index;
                                try {
                                    index = Integer.parseInt(values.containsKey(idprop) ? values.get(idprop).toString() : semObj.getId());
                                } catch (NumberFormatException e) {
                                    index = Integer.parseInt(semObj.getId());
                                    log.info("Error parsing " + values.get(idprop) + " to int, " + e.getMessage() + ", " + e.getCause());
                                }
                                semObj.setProperty(sp, index + "");
                            }
                        }
                        if (!values.containsKey(Sortable.swb_index.getPropId())) {
                            SemanticProperty sp = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(Sortable.swb_index.getPropId());
                            semObj.setProperty(sp, semObj.getId());
                        }
                        if (rd != null && referable != null) {//Es Archivo
                            if (values.containsKey("hftype") && values.get("hftype").equals("file")) {
                                RepositoryFile rf = RepositoryFile.ClassMgr.createRepositoryFile(rd.getProcessSite());
                                referable.setRefRepository(rf);
                                rf.setRepositoryDirectory(rd);
                                rf.storeFile(filename, is, null, Boolean.TRUE, null);
                                rf.setTitle(semObj.getProperty(Descriptiveable.swb_title));
                                rf.setDescription(semObj.getProperty(Descriptiveable.swb_description));
                            } else { //Es URL
                                RepositoryURL repoUrl = RepositoryURL.ClassMgr.createRepositoryURL(rd.getProcessSite());
                                repoUrl.setRepositoryDirectory(rd);
                                referable.setRefRepository(repoUrl);
                                User usr = response.getUser();
                                repoUrl.setTitle(semObj.getProperty(Descriptiveable.swb_title));
                                repoUrl.setDescription(semObj.getProperty(Descriptiveable.swb_description));
                                repoUrl.setOwnerUserGroup(usr.getUserGroup());
                                repoUrl.storeFile(link.startsWith("http://") ? link : "http://" + link, null, Boolean.TRUE, null);
                            }
                        }
                    }
                }
                response.setRenderParameter("uridsi", uridsi);
                response.setRenderParameter("urise", urise);
                response.setCallMethod(SWBResourceURL.Call_DIRECT);
                response.setMode(MODE_ACTUALIZA_TAB);
            } else if (action.equals(ACTION_EDIT_INSTANTIABLE)) {
                SemanticObject semObj = SemanticObject.createSemanticObject(urise);
                if (semObj != null) {
                    String[] addprops = props.split("\\|");
                    for (String propt : addprops) {
                        String idprop = propt.substring(propt.indexOf(";") + 1, propt.length());
                        SemanticProperty sp = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(idprop);
                        if (idprop.equals(DocumentSection.swpdoc_configData.getPropId())) {
                            configData = values.containsKey(idprop) ? values.get(idprop).toString() : "";
                        }
                        if (!idprop.equals(Sortable.swb_index.getPropId())) {
                            semObj.setProperty(sp, values.containsKey(idprop) ? values.get(idprop).toString() : null);
                        } else {
                            int index;
                            try {
                                index = Integer.parseInt(values.get(idprop).toString());
                            } catch (NumberFormatException e) {
                                index = Integer.parseInt(semObj.getId());
                                log.info("Error parsing " + values.get(idprop) + " to int, " + e.getMessage() + ", " + e.getCause());
                            }
                            semObj.setProperty(sp, index + "");
                        }
                    }
                }
                rd = (RepositoryDirectory) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(configData);
                RepositoryElement re = (RepositoryElement) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(urire);
                SectionElement se = semObj != null ? (SectionElement) semObj.createGenericInstance() : null;
                if (se != null && se instanceof Referable) {
                    Referable ref = (Referable) se;
                    String urivi = values.containsKey("versionref") ? values.get("versionref").toString() : "";
                    VersionInfo vi = (VersionInfo) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(urivi);
                    if (vi != null) {
                        ref.setVersion(vi);
                    }
                }
                if (re != null) {
                    re.setRepositoryDirectory(rd);
                    if (semObj != null) {
                        re.getSemanticObject().setProperty(Descriptiveable.swb_title, semObj.getProperty(Descriptiveable.swb_title));
                        re.getSemanticObject().setProperty(Descriptiveable.swb_description, semObj.getProperty(Descriptiveable.swb_description));
                    }
                }
                response.setRenderParameter("uridsi", uridsi);
                response.setRenderParameter("urise", urise);
                response.setCallMethod(SWBResourceURL.Call_DIRECT);
                response.setMode(MODE_ACTUALIZA_TAB);
            } else if (action.equals(ACTION_ADD_RELATE)) {
                uridsi = request.getParameter("uridsi") != null ? request.getParameter("uridsi") : "";
                String urirel = request.getParameter("related") != null ? request.getParameter("related") : "";
                if (!uridsi.equals("") && !urirel.equals("")) {
                    DocumentSectionInstance dsi = (DocumentSectionInstance) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uridsi);

                    ElementReferable er = (ElementReferable) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(urirel);
                    ElementReference eref = ElementReference.ClassMgr.createElementReference(model);
                    eref.setDocumentTemplate(dsi.getSecTypeDefinition().getParentTemplate());
                    eref.setParentSection(dsi.getSecTypeDefinition());
                    eref.setDocumentSectionInst(dsi);
                    eref.setElementRef(er);
                    eref.setIndex(Integer.parseInt(er.getId()));

                    dsi.addDocuSectionElementInstance(eref);
                    urise = eref.getURI();
                }
                response.setRenderParameter("uridsi", uridsi);
                response.setRenderParameter("urise", urise);
                response.setCallMethod(SWBResourceURL.Call_DIRECT);
                response.setMode(MODE_ACTUALIZA_TAB);
            } else if (action.equals(SWBResourceURL.Action_REMOVE)) {
                uridsi = request.getParameter("uridsi") != null ? request.getParameter("uridsi") : "";
                urise = request.getParameter("urise") != null ? request.getParameter("urise") : "";
                DocumentSectionInstance dsi = (DocumentSectionInstance) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uridsi);
                SectionElement se = (SectionElement) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(urise);
                dsi.removeDocuSectionElementInstance(se);
                se.remove();
            } else if (action.equals(ACTION_EDIT_TEXT)) {
                uridsi = request.getParameter("uridsi") != null ? request.getParameter("uridsi") : "";
                String data = request.getParameter("data") != null ? request.getParameter("data") : "";
                urise = request.getParameter("urise") != null ? request.getParameter("urise") : "";
                SectionElement se = (SectionElement) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(urise);
                if (se instanceof FreeText) {
                    FreeText ft = (FreeText) se;
                    ft.setText(data);
                } else if (se instanceof Activity) {
                    Activity a = (Activity) se;
                    a.setDescription(data);
                    response.setRenderParameter("uridsi", a.getDocumentSectionInst().getURI());
                    response.setRenderParameter("urise", urise);
                    response.setRenderParameter("urip", a.getActivityRef().getProcessActivity().getProcess().getURI());
                    response.setCallMethod(SWBResourceURL.Call_DIRECT);
                    response.setMode(MODE_ACTUALIZA_TAB);
                }
            } else if (action.equals(ACTION_EDIT_DESCRIPTION)) {
                urise = request.getParameter("urise") != null ? request.getParameter("urise") : "";
                uridsi = request.getParameter("uridsi") != null ? request.getParameter("uridsi") : "";
                String urip = request.getParameter("urip") != null ? request.getParameter("urip") : "";
                String data = request.getParameter("data") != null ? request.getParameter("data") : "";
                Activity activity = (Activity) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(urise);
                if (activity != null) {
                    activity.setDescription(data);
                }
                response.setRenderParameter("uridsi", uridsi);
                response.setRenderParameter("urise", urise);
                response.setRenderParameter("urip", urip);
                response.setCallMethod(SWBResourceURL.Call_DIRECT);
                response.setMode(MODE_ACTUALIZA_TAB);

            } else if (action.equals(ACTION_SAVE_VERSION)) {
                String uridi = values.containsKey("uridi") ? values.get("uridi").toString() : "";
                String title = values.containsKey("title") ? values.get("title").toString() : "";

                String description = values.containsKey("description") ? values.get("description").toString() : null;
                if (!uridi.equals("") && !title.equals("")) {
                    DocumentationInstance di = (DocumentationInstance) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uridi);
                    if (di != null) {
                        Iterator<Documentation> itdoc = SWBComparator.sortSortableObject(Documentation.ClassMgr.listDocumentationByProcess(di.getProcessRef()));
                        while (itdoc.hasNext()) {
                            Documentation d = itdoc.next();
                            if (d.isActualVersion()) {
                                d.setActualVersion(false);
                            }
                        }
                        //Nueva versión publicada
                        Documentation doc = Documentation.ClassMgr.createDocumentation(model);
                        doc.setProcess(di.getProcessRef());
                        doc.setTitle(title);
                        doc.setDescription(description);
                        doc.setActualVersion(true);

                        String path = SWBPortal.getWorkPath() + "/models/" + model.getId() + "/Resource/" + di.getProcessRef().getId() + "/" + doc.getId() + "/";
                        File base = new File(path);
                        if (!base.exists()) {
                            base.mkdirs();
                        }
                        File index = new File(base + "/" + di.getProcessRef().getId() + ".html");
                        FileOutputStream out = new FileOutputStream(index);
                        try {
                            org.w3c.dom.Document dom = SWPUtils.getDocument(di, request, false);
                            if (dom != null) {
                                String tlpPath = "/work/models/" + response.getWebPage().getWebSiteId() + "/jsp/documentation/documentation.xsl";
                                javax.xml.transform.Templates tpl = SWBUtils.XML.loadTemplateXSLT(new FileInputStream(SWBUtils.getApplicationPath() + tlpPath));
                                out.write(SWBUtils.XML.transformDom(tpl, dom).getBytes("UTF-8"));

                                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                                Result output = new StreamResult(new File(path + "/" + di.getProcessRef().getId() + ".xml"));
                                Source input = new DOMSource(dom);
                                transformer.transform(input, output);

                            }
//                            out.write(modals.getBytes("UTF-8"));
                            out.flush();
                            out.close();
                        } catch (Exception e) {
                            log.error("Error on write file " + index.getAbsolutePath() + ", " + e.getMessage() + ", " + e.getCause());
                        }
                        response.setRenderParameter("uridoc", doc.getURI());
                        response.setRenderParameter("urip", doc.getProcess().getURI());
                    }
                }
                response.setRenderParameter("uridi", uridi);
                response.setRenderParameter("uridsi", uridsi);
                response.setMode(MODE_MSG_VERSION);
                response.setCallMethod(SWBResourceURL.Call_DIRECT);
            } else if (action.equals(ACTION_RELATED_ACTIVITY)) {
                Activity a = (Activity) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(urise);
                DocumentSectionInstance dsi = (DocumentSectionInstance) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uridsi);
                if (a != null && dsi != null && dsi.getDocumentationInstance() != null) {
                    DocumentationInstance di = dsi.getDocumentationInstance();
                    Iterator<DocumentSectionInstance> itdsi = SWBComparator.sortSortableObject(di.listDocumentSectionInstances());
                    Map map = new HashMap();
                    Iterator<SectionElementRef> itser = a.listSectionElementRefs();
                    while (itser.hasNext()) {
                        SectionElementRef ser = itser.next();
                        map.put(ser.getSectionElement(), ser);
                    }
                    while (itdsi.hasNext()) {
                        DocumentSectionInstance dsin = itdsi.next();
                        SemanticClass cls = dsin.getSecTypeDefinition() != null && dsin.getSecTypeDefinition().getSectionType() != null ? dsin.getSecTypeDefinition().getSectionType().transformToSemanticClass() : null;
                        if (cls != null
                                && cls.isSubClass(Instantiable.swpdoc_Instantiable, false)
                                && dsin.listDocuSectionElementInstances().hasNext()) {
                            Iterator<SectionElement> itse = dsin.listDocuSectionElementInstances();
                            while (itse.hasNext()) {
                                SectionElement sen = itse.next();
                                if (values.containsKey(sen.getURI()) && !map.containsKey(sen)) { // Add new SER
                                    SectionElementRef ser = SectionElementRef.ClassMgr.createSectionElementRef(model);
                                    ser.setSectionElement(sen);
                                    ser.setActivity(a);
                                    a.addSectionElementRef(ser);
                                } else if (map.containsKey(sen) && !values.containsKey(sen.getURI())) { //Remove SER
                                    SectionElementRef ser = (SectionElementRef) map.get(sen);
                                    if (ser != null) {
                                        ser.remove();
                                        a.removeSectionElementRef(ser);
                                    }
                                }
                            }
                        }
                    }
                }
                response.setRenderParameter("uridsi", uridsi);
                response.setRenderParameter("urise", urise);
            } else if (action.equals(ACTION_UPDATE_FILL)) {
                urise = request.getParameter("urise") != null ? request.getParameter("urise") : "";
                String fill = request.getParameter("fill") != null ? request.getParameter("fill") : "";
                Activity activity = (Activity) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(urise);
                if (activity != null) {
                    if (fill.equals("defaultFill")) {
                        activity.setFill(null);
                    } else {
                        activity.setFill(fill);
                    }
                }
            } else if (action.equals(ACTION_UPLOAD_PICTURE)) {
                SectionElement se = (SectionElement) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(urise);
                if (is != null && se != null) {
                    String basePath = SWBPortal.getWorkPath() + "/models/" + model.getId() + "/swp_DocumentationImage";
                    File dir = new File(basePath);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    OutputStream out = new FileOutputStream(new File(basePath + "/" + filename));
                    IOUtils.copy(is, out);
                    is.close();
                    out.flush();
                    out.close();
                }
            } else {
                super.processAction(request, response); //To change body of generated methods, choose Tools | Templates.
            }
        } catch (Exception e) {
            log.error("Error on processAction, ACTION: " + action + ", " + e.getMessage() + ", " + e.getCause());
        }
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        if (mode.equals(MODE_EDIT_INSTANTIABLE)) {
            doEdit(request, response, paramRequest);
        } else if (mode.equals(MODE_ACTUALIZA_TAB)) {
            doActualizaTab(request, response, paramRequest);
        } else if (mode.equals(MODE_RELATED)) {
            doRelated(request, response, paramRequest);
        } else if (mode.equals(MODE_TRACEABLE)) {
            doTrace(request, response, paramRequest);
        } else if (mode.equals(MODE_VERSION)) {
            doVersion(request, response, paramRequest);
        } else if (mode.equals(MODE_RELATED_ACTIVITY)) {
            doRelateActivity(request, response, paramRequest);
        } else if (mode.equals(MODE_EDIT_DESCRIPTION)) {
            doEditDescription(request, response, paramRequest);
        } else if (mode.equals(MODE_MSG_VERSION)) {
            doMsgVersion(request, response, paramRequest);
        } else if (mode.equals(MODE_DOWNLOAD)) {
            doDownload(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String path = "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/documentation/documentation.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(path);

        try {
            request.setAttribute(SWPUtils.PARAM_REQUEST, paramRequest);
            rd.forward(request, response);
        } catch (ServletException ex) {
            log.error("Error on doView, " + path + ", " + ex.getMessage() + ", " + ex.getCause());
        }
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String path = "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/documentation/documentationEdit.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(path);
        try {
            request.setAttribute(SWPUtils.PARAM_REQUEST, paramRequest);
            rd.forward(request, response);
        } catch (ServletException ex) {
            log.error("Error on doEdit, " + path + ", " + ex.getMessage() + ", " + ex.getCause());
        }
    }

    public void doActualizaTab(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String path = "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/documentation/actualizaTab.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(path);
        try {
            request.setAttribute(SWPUtils.PARAM_REQUEST, paramRequest);
            rd.forward(request, response);
        } catch (ServletException ex) {
            log.error("Error on doActualizaTab, " + path + ", " + ex.getMessage() + ", " + ex.getCause());
        }
    }

    public void doRelated(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String path = "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/documentation/relatedItem.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(path);
        try {
            request.setAttribute(SWPUtils.PARAM_REQUEST, paramRequest);
            rd.forward(request, response);
        } catch (ServletException ex) {
            log.error("Error on doRelated, " + path + ", " + ex.getMessage() + ", " + ex.getCause());
        }
    }

    public void doTrace(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String path = "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/documentation/logView.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(path);
        try {
            request.setAttribute(SWPUtils.PARAM_REQUEST, paramRequest);
            rd.forward(request, response);
        } catch (ServletException ex) {
            log.error("Error on doTrace, " + path + ", " + ex.getMessage() + ", " + ex.getCause());
        }
    }

    public void doVersion(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String path = "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/documentation/saveVersion.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(path);
        try {
            request.setAttribute(SWPUtils.PARAM_REQUEST, paramRequest);
            rd.forward(request, response);
        } catch (ServletException ex) {
            log.error("Error on doVersion, " + path + ", " + ex.getMessage() + ", " + ex.getCause());
        }
    }

    public void doRelateActivity(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String path = "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/documentation/relateActivity.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(path);
        try {
            request.setAttribute(SWPUtils.PARAM_REQUEST, paramRequest);
            rd.forward(request, response);
        } catch (ServletException ex) {
            log.error("Error on doRelateActivity, " + path + ", " + ex.getMessage() + ", " + ex.getCause());
        }
    }

    public void doEditDescription(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String path = "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/documentation/editDescription.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(path);
        try {
            request.setAttribute(SWPUtils.PARAM_REQUEST, paramRequest);
            rd.forward(request, response);
        } catch (ServletException ex) {
            log.error("Error on doEditDescription(" + paramRequest.getMode() + "), " + path + ", " + ex.getMessage() + ", " + ex.getCause());
        }
    }

    public void doMsgVersion(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().print("");
//        response.getWriter().print(request.getParameter("uridoc"));
//        String path = "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/documentation/showNewVersion.jsp";
//        RequestDispatcher rd = request.getRequestDispatcher(path);
//        try {
//            request.setAttribute(SWPUtils.PARAM_REQUEST, paramRequest);
//            rd.forward(request, response);
//        } catch (ServletException ex) {
//            log.error("Error on doMsgVersion, " + path + ", " + ex.getMessage() + ", " + ex.getCause());
//        }
    }

    public void doDownload(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {

        try {
            String urip = request.getParameter("urip") != null ? request.getParameter("urip") : "";
            String uridi = request.getParameter("uridi") != null ? request.getParameter("uridi") : "";
            String data = request.getParameter("data");
            String format = request.getParameter("format") != null ? request.getParameter("format") : "";
            String viewBox = request.getParameter("viewBox");

            org.semanticwb.process.model.Process p = (org.semanticwb.process.model.Process) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(urip);
            DocumentationInstance di = (DocumentationInstance) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uridi);
            if (p != null && di != null) {
                WebSite model = paramRequest.getWebPage().getWebSite();

                response.setContentType("text/html; charset=UTF-8");
                response.setContentType("application/zip");
                response.setHeader("Content-Disposition", "attachment; filename=\"" + p.getId() + ".zip\"");
                String basePath = SWBPortal.getWorkPath() + "/models/" + model.getId() + "/Resource/" + p.getId() + "/download/";
                File dirBase = new File(basePath);
                if (!dirBase.exists()) {
                    dirBase.mkdirs();
                }
                SWPUtils.generateImageModel(p, SWPUtils.FORMAT_PNG, data, viewBox);
                SWPUtils.generateImageModel(p, SWPUtils.FORMAT_SVG, data, viewBox);

                File dest = new File(basePath);
                if (!dest.exists()) {
                    dest.mkdirs();
                }
                if (format.equals(SWPUtils.FORMAT_HTML)) {
                    //Copy bootstrap files
                    copyFileFromSWBAdmin("/swbadmin/css/bootstrap/bootstrap.css", basePath + "css/bootstrap/", "/bootstrap.css");
                    copyFileFromSWBAdmin("/swbadmin/js/bootstrap/bootstrap.js", basePath + "js/bootstrap/", "/bootstrap.js");
                    //Copy font-awesome files
                    copyFileFromSWBAdmin("/swbadmin/css/fontawesome/font-awesome.css", basePath + "css/fontawesome/", "/font-awesome.css");
                    copyFileFromSWBAdmin("/swbadmin/css/fonts/FontAwesome.otf", basePath + "css/fonts/", "/FontAwesome.otf");
                    copyFileFromSWBAdmin("/swbadmin/css/fonts/fontawesome-webfont.eot", basePath + "css/fonts/", "/fontawesome-webfont.eot");
                    copyFileFromSWBAdmin("/swbadmin/css/fonts/fontawesome-webfont.svg", basePath + "css/fonts/", "/fontawesome-webfont.svg");
                    copyFileFromSWBAdmin("/swbadmin/css/fonts/fontawesome-webfont.ttf", basePath + "css/fonts/", "/fontawesome-webfont.ttf");
                    copyFileFromSWBAdmin("/swbadmin/css/fonts/fontawesome-webfont.woff", basePath + "css/fonts/", "/fontawesome-webfont.woff");
                    copyFileFromSWBAdmin("/swbadmin/js/jquery/jquery.js", basePath + "js/jquery/", "/jquery.js");
                    //Add modeler
                    File modeler = new File(basePath + "css/modeler/");
                    if (!modeler.exists()) {
                        modeler.mkdirs();
                    }
                    modeler = new File(basePath + "js/modeler/");
                    if (!modeler.exists()) {
                        modeler.mkdirs();
                    }
                    copyFile(SWBUtils.getApplicationPath() + "/swbadmin/jsp/process/modeler/toolkit.js", basePath + "/js/modeler/toolkit.js");
                    copyFile(SWBUtils.getApplicationPath() + "/swbadmin/jsp/process/modeler/modeler.js", basePath + "/js/modeler/modeler.js");
                    copyFile(SWBUtils.getApplicationPath() + "/swbadmin/jsp/process/modeler/images/modelerFrame.css", basePath + "/css/modeler/modelerFrame.css");

                    copyFile(SWBPortal.getWorkPath() + "/models/" + model.getId() + "/css/swbp.css", basePath + "/css/swbp.css");
                    //Add images
                    File images = new File(basePath + "css/images/");
                    if (!images.exists()) {
                        images.mkdirs();
                    }
                    SWBUtils.IO.copyStructure(SWBUtils.getApplicationPath() + "/swbadmin/jsp/process/commons/css/images/", basePath + "/css/images/");
                    File index = new File(basePath + "/" + p.getId() + ".html");
                    FileOutputStream out = new FileOutputStream(index);
                    org.w3c.dom.Document dom = SWPUtils.getDocument(di, request, true);
                    if (dom != null) {
                        String tlpPath = "/work/models/" + model.getId() + "/jsp/documentation/documentation.xsl";
                        javax.xml.transform.Templates tpl = SWBUtils.XML.loadTemplateXSLT(new FileInputStream(SWBUtils.getApplicationPath() + tlpPath));
                        //out.write(SWBUtils.XML.transformDom(tpl, dom).getBytes("UTF-8"));
                        out.write(SWBUtils.XML.transformDom(tpl, dom).getBytes());

                        Transformer transformer = TransformerFactory.newInstance().newTransformer();
                        Result output = new StreamResult(new File(basePath + "/" + di.getProcessRef().getId() + ".xml"));
                        Source input = new DOMSource(dom);
                        transformer.transform(input, output);

                    }
                    out.flush();
                    out.close();
                } else if (format.equals(SWPUtils.FORMAT_WORD)) {

                    //Document
                    Document doc = new Document();
                    RtfWriter2.getInstance(doc, new FileOutputStream(basePath + p.getId() + ".rtf"));
                    doc.open();
                    doc.addTitle(p.getId());

                    Paragraph pTitle = new Paragraph();//Title
                    pTitle.add(p.getTitle());
                    doc.add(pTitle);

                    Image image2 = Image.getInstance(basePath + "rep_files/" + p.getId() + ".png");//Model Image
                    image2.scaleToFit(400, 400);
                    doc.add(image2);

                    //Sections
                    doc.newPage();
                    doc.add(new Paragraph("Secciones"));
                    Iterator<DocumentSectionInstance> itdsi = SWBComparator.sortSortableObject(di.listDocumentSectionInstances());
                    while (itdsi.hasNext()) {//Sections
                        DocumentSectionInstance dsi = itdsi.next();
                        SemanticClass cls = dsi.getSecTypeDefinition() != null && dsi.getSecTypeDefinition().getSectionType() != null ? dsi.getSecTypeDefinition().getSectionType().transformToSemanticClass() : null;
                        if (!dsi.getSecTypeDefinition().isActive() || (cls != null && cls.equals(Model.sclass))) {
                            continue;
                        }
                        doc.add(new Paragraph(dsi.getSecTypeDefinition().getTitle()));
                    }

                    //Content
                    doc.newPage();
                    doc.add(new Paragraph("Contenido"));

                    itdsi = SWBComparator.sortSortableObject(di.listDocumentSectionInstances());
                    while (itdsi.hasNext()) {//Sections
                        DocumentSectionInstance dsi = itdsi.next();
                        doc.add(new Paragraph(dsi.getSecTypeDefinition().getTitle()));

                        Iterator<SectionElement> itse = SWBComparator.sortSortableObject(dsi.listDocuSectionElementInstances());
                        List<SectionElement> list = new ArrayList<SectionElement>();
                        while (itse.hasNext()) {
                            SectionElement se = itse.next();
                            list.add(se);
                        }

                        SemanticClass cls = dsi.getSecTypeDefinition() != null && dsi.getSecTypeDefinition().getSectionType() != null ? dsi.getSecTypeDefinition().getSectionType().transformToSemanticClass() : null;
                        if (cls != null) {
                            if (!dsi.getSecTypeDefinition().isActive() || cls.equals(Model.sclass)) {
                                continue;
                            }
                            if (cls.isSubClass(Instantiable.swpdoc_Instantiable, false)) {//Instantiable
                                String[] props = dsi.getSecTypeDefinition().getVisibleProperties().split("\\|");
                                //Add Table
                                Table t = new Table(props.length, (list.size() + 1));

                                for (String propt : props) {//Header
                                    String titleprop = propt.substring(0, propt.indexOf(";"));
                                    t.addCell(new Cell(titleprop));
                                }
                                for (SectionElement se : list) {//Instances
                                    for (String propt : props) {
                                        String idprop = propt.substring(propt.indexOf(";") + 1, propt.length());
                                        SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(idprop);
                                        String value = "es archivo";
                                        if (prop != null && !prop.getPropId().equals(Referable.swpdoc_file.getPropId())) {
                                            value = (se.getSemanticObject().getProperty(prop) != null ? se.getSemanticObject().getProperty(prop) : "");
                                            t.addCell(new Cell(value));
                                        } else {

                                            Anchor anchor = new Anchor(se.getTitle());
                                            Referable ref = (Referable) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(se.getURI());
                                            //RepositoryDirectory rd = ref.getRefRepository().getRepositoryDirectory();
                                            RepositoryElement re = (RepositoryElement) ref.getRefRepository();
                                            VersionInfo vi = ref.getVersion() != null ? ref.getVersion() : re.getLastVersion();

                                            if (re instanceof RepositoryFile) {
                                                String basePathE = SWBPortal.getWorkPath() + "/models/" + p.getProcessSite().getId() + "/swp_RepositoryFile/" + ref.getRefRepository().getId() + "/" + vi.getVersionNumber() + "/";
                                                File baseDir = new File(basePathE);
                                                String basePathDest = SWBPortal.getWorkPath() + "/models/" + p.getProcessSite().getId() + "/Resource/" + p.getId() + "/download/";
                                                File repFile = new File(basePathDest + "rep_files/" + ref.getRefRepository().getId() + "/" + vi.getVersionNumber() + "/");
                                                if (!repFile.exists()) {
                                                    repFile.mkdirs();
                                                }
                                                if (baseDir.isDirectory()) {
                                                    File[] files = baseDir.listFiles();
                                                    for (File file : files) {
                                                        String fileName = file.getName().substring(file.getName().lastIndexOf("."));
                                                        anchor.setReference("rep_Files/" + ref.getRefRepository().getId() + "/" + vi.getVersionNumber() + "/" + se.getId() + fileName);
                                                        copyFile(file.getAbsolutePath(), repFile.getAbsolutePath() + "/" + se.getId() + fileName);
                                                        break;
                                                    }
                                                }
                                            } else if (re instanceof RepositoryURL) {
                                                anchor.setReference(vi.getVersionFile());
                                            }
                                            t.addCell(new Cell(anchor));
                                        }
                                    }
                                }
                                doc.add(t);
                            } else if (cls.equals(FreeText.sclass)) {//FreeText
                                for (SectionElement se : list) {
                                    FreeText ft = (FreeText) se;
                                    File dir = new File(basePath + "rep_files/img/" + se.getId() + "/");
                                    if (!dir.exists()) {
                                        dir.mkdirs();
                                    }
                                    if (ft.getText() != null) {
                                        SWPUtils.addTextHtmlToRtf(ft.getText(), doc, se, model, p);
                                    }
                                }
                            } else if (cls.equals(Activity.sclass)) {//Activity
                                for (SectionElement se : list) {
                                    Activity a = (Activity) se;
                                    doc.add(new Paragraph(a.getTitle()));
                                    if (a.getDescription() != null) {
                                        SWPUtils.addTextHtmlToRtf(a.getDescription(), doc, se, model, p);
                                    }
                                }
                            }
                        }
                    }

                    /*
                     CustomWord document = new CustomWord();
                     //Title
                     XWPFParagraph pTitle = document.createParagraph();
                     pTitle.setAlignment(ParagraphAlignment.CENTER);
                     XWPFRun rTitle = pTitle.createRun();
                     rTitle.setText(p.getTitle());
                     rTitle.setFontSize(25);
                     rTitle.setFontFamily("Helvetica");

                     InputStream is = new FileInputStream(new File(basePath + "rep_files/" + p.getId() + ".png"));
                     String blipId = document.addPictureData(is, XWPFDocument.PICTURE_TYPE_PNG);
                     document.createPicture(blipId, document.getNextPicNameNumber(Document.PICTURE_TYPE_PNG), 600, 600);
                     document.createParagraph().createRun().addBreak(BreakType.PAGE);

                     //Sections
                     XWPFParagraph pSections = document.createParagraph();
                     XWPFRun rSections = pSections.createRun();
                     rSections.setText("Secciones");
                     rSections.setFontSize(18);
                     rSections.addBreak();

                     //Sections
                     XWPFParagraph pSection = document.createParagraph();
                     Iterator<DocumentSectionInstance> itdsi = SWBComparator.sortSortableObject(di.listDocumentSectionInstances());
                     while (itdsi.hasNext()) {//Sections
                     DocumentSectionInstance dsi = itdsi.next();
                     SemanticClass cls = dsi.getSecTypeDefinition() != null && dsi.getSecTypeDefinition().getSectionType() != null ? dsi.getSecTypeDefinition().getSectionType().transformToSemanticClass() : null;
                     if (!dsi.getSecTypeDefinition().isActive() || (cls != null && cls.equals(Model.sclass))) {
                     continue;
                     }
                     XWPFRun rSection = pSection.createRun();
                     rSection.setText(dsi.getSecTypeDefinition().getTitle());
                     rSection.addBreak();
                     }
                     document.createParagraph().createRun().addBreak(BreakType.PAGE);

                     //Sections
                     XWPFParagraph pContent = document.createParagraph();
                     XWPFRun rContent = pContent.createRun();
                     rContent.setText("Contenido a detalle");
                     rContent.setFontSize(18);
                     rContent.addBreak();

                     //Sections
                     XWPFParagraph pContents = document.createParagraph();
                     itdsi = SWBComparator.sortSortableObject(di.listDocumentSectionInstances());
                     while (itdsi.hasNext()) {//Sections
                     DocumentSectionInstance dsi = itdsi.next();
                     SemanticClass cls = dsi.getSecTypeDefinition() != null && dsi.getSecTypeDefinition().getSectionType() != null ? dsi.getSecTypeDefinition().getSectionType().transformToSemanticClass() : null;
                     if (cls != null) {
                     if (!dsi.getSecTypeDefinition().isActive() || cls.equals(Model.sclass)) {
                     continue;
                     }
                     XWPFRun rSection = pContents.createRun();
                     rSection.setText(dsi.getSecTypeDefinition().getTitle());
                     rSection.addBreak();

                     Iterator<SectionElement> itse = SWBComparator.sortSortableObject(dsi.listDocuSectionElementInstances());
                     List<SectionElement> list = new ArrayList<SectionElement>();
                     while (itse.hasNext()) {
                     SectionElement se = itse.next();
                     list.add(se);
                     }
                            

                     if (cls.isSubClass(Instantiable.swpdoc_Instantiable, false)) {//Instantiable
                     String[] props = dsi.getSecTypeDefinition().getVisibleProperties().split("\\|");
                     //Add Table
                     XWPFTable table = document.createTable((list.size() + 1), props.length);
                     XWPFTableRow header = table.getRow(0);
                     int col = 0;
                     for (String propt : props) {//Header
                     String titleprop = propt.substring(0, propt.indexOf(";"));
                     header.getCell(col++).setText(titleprop);
                     }
                     int rowi = 1;
                     for (SectionElement se : list) {//Instances
                     XWPFTableRow row = table.getRow(rowi++);
                     col = 0;
                     for (String propt : props) {
                     String idprop = propt.substring(propt.indexOf(";") + 1, propt.length());
                     SemanticProperty prop = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(idprop);
                     String value = "";
                     if (prop != null && !prop.getPropId().equals(Referable.swpdoc_file.getPropId())) {
                     value = (se.getSemanticObject().getProperty(prop) != null ? se.getSemanticObject().getProperty(prop) : "");
                     } else {

                     }
                     row.getCell(col++).setText(value);
                     }
                     }
                     document.createParagraph().createRun().addBreak(BreakType.TEXT_WRAPPING);
                     document.createParagraph().createRun().addBreak(BreakType.TEXT_WRAPPING);
                     } else if (cls.equals(FreeText.sclass)) {//FreeText
                     for (SectionElement se : list) {
                     XWPFRun rSectionT = pContents.createRun();
                     FreeText ft = (FreeText) se;
                     rSectionT.setText(SWBUtils.TEXT.parseHTML(ft.getText()));
                     rSectionT.addBreak();
                     rSectionT.addBreak();
                     rSectionT.addBreak();
                     }
                     } else if (cls.equals(Activity.sclass)) {//Activity

                     }

                     }

                     }
                     */
                    doc.close();

//                    FileOutputStream output = new FileOutputStream(basePath + p.getId() + ".docx");
//                    doc.write(output);
//                    output.close();
                }
                OutputStream ou = response.getOutputStream();
                ZipOutputStream zos = new ZipOutputStream(ou);
                SWBUtils.IO.zip(dest, new File(basePath), zos);
                zos.flush();
                zos.close();
                ou.flush();
                ou.close();
                deleteDerectory(dest);

            }

        } catch (Exception ex) {
            log.error("Error on doDownload, " + ex.getMessage() + ", " + ex.getCause());
            ex.printStackTrace();
        }
    }
    static List<RepositoryDirectory> list = new ArrayList<RepositoryDirectory>();

    public static List<RepositoryDirectory> listRepositoryDerectoryByParent(WebPage webpage, boolean clear) {
        if (clear) {
            list.clear();
        }
        Iterator<RepositoryDirectory> it = SWBComparator.sortByCreated(RepositoryDirectory.ClassMgr.listRepositoryDirectoryByParent(webpage), true);
        while (it.hasNext()) {
            RepositoryDirectory rep = it.next();
            Iterator<RepositoryDirectory> itRep = SWBComparator.sortByCreated(RepositoryDirectory.ClassMgr.listRepositoryDirectoryByParent(rep), true);
            while (itRep.hasNext()) {
                RepositoryDirectory repDir = itRep.next();
                list.add(repDir);
                listRepositoryDerectoryByParent(repDir, false);
            }
            list.add(rep);
        }
        return list;
    }
}
