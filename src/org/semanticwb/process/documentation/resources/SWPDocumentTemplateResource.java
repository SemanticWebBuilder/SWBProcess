/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.semanticwb.process.documentation.resources;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.process.documentation.model.Activity;
import org.semanticwb.process.documentation.model.ActivityRef;
import org.semanticwb.process.documentation.model.Definition;
import org.semanticwb.process.documentation.model.DocumentSection;
import org.semanticwb.process.documentation.model.DocumentSectionInstance;
import org.semanticwb.process.documentation.model.DocumentTemplate;
import org.semanticwb.process.documentation.model.DocumentationInstance;
import org.semanticwb.process.documentation.model.ElementReference;
import org.semanticwb.process.documentation.model.Format;
import org.semanticwb.process.documentation.model.FreeText;
import org.semanticwb.process.documentation.model.Indicator;
import org.semanticwb.process.documentation.model.Policy;
import org.semanticwb.process.documentation.model.Referable;
import org.semanticwb.process.documentation.model.Reference;
import org.semanticwb.process.documentation.model.Rule;
import org.semanticwb.process.documentation.model.SectionElement;
import org.semanticwb.process.documentation.model.SectionElementRef;
import org.semanticwb.process.documentation.model.TemplateContainer;
import org.semanticwb.process.documentation.resources.utils.SWPUtils;

/**
 *
 * @author carlos.alvarez
 */
public class SWPDocumentTemplateResource extends GenericResource {

    private final Logger log = SWBUtils.getLogger(SWPDocumentTemplateResource.class);

    public final static String MODE_EDIT_DOCUMENT_SECTION = "m_eds";//Modo EDITAR de sección de documentación
    public final static String ACTION_ADD_DOCUMENT_SECTION = "a_ads";//Acción GUARDAR NUEVA sección de documentación
    public final static String ACTION_ADD_VERSION_TEMPLATE = "a_anvt";//Acción GUARDAR NUEVA sección de documentación
    public final static String ACTION_DEFINE_VERSION_TEMPLATE = "a_dvt";//Acción GUARDAR NUEVA sección de documentación
    public final static String ACTION_REMOVE_VERSION_TEMPLATE = "a_rvt";//Acción GUARDAR NUEVA sección de documentación
    public final static String ACTION_EDIT_VERSION_TEMPLATE = "a_aevt";//Acción GUARDAR NUEVA sección de documentación
    public final static String ACTION_EDIT_DOCUMENT_SECTION = "a_eds";//Acción EDITAR de sección de documentación
    public final static String ACTION_REMOVE_DOCUMENT_SECTION = "a_rds";//Acción ELIMINAR de sección de documentación
    public final static String ACTION_DUPLICATE_TEMPLATE = "a_dute";//Acción ELIMINAR de sección de documentación
    public final static String MODE_VIEW_LOG = "m_vlo";//Acción ELIMINAR de sección de documentación
    public final static String MODE_VIEW_VERSION = "m_vve";//Acción ELIMINAR de sección de documentación
    public final static String MODE_DUPLICATE_TEMPLATE = "m_dute";//Acción ELIMINAR de sección de documentación
    public final static String MODE_EDIT_VERSION_TEMPLATE = "m_nvet";//Acción ELIMINAR de sección de documentación
    public final static String MODE_PROPERTIES = "m_pro";//Acción ELIMINAR de sección de documentación

    @Override
    public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
        String action = response.getAction();
        response.setRenderParameter("action", action);
        WebSite model = response.getWebPage().getWebSite();
        if (action.equals(SWBResourceURL.Action_ADD)) {//Agregar nueva plantilla de documentación
            //String title = request.getParameter("titleTemplate") != null ? request.getParameter("titleTemplate").trim() : "";
            String titletc = request.getParameter("titletc") != null ? request.getParameter("titletc").trim() : "";
            if (!titletc.equals("")) {
                TemplateContainer tc = TemplateContainer.ClassMgr.createTemplateContainer(model);
                tc.setTitle(titletc);
                DocumentTemplate dt = DocumentTemplate.ClassMgr.createDocumentTemplate(model);
                dt.setTemplateContainer(tc);
                tc.addTemplate(dt);
                tc.setActualTemplate(dt);
                tc.setLastTemplate(dt);
                dt.setTitle(titletc);
                String[] processes = request.getParameterValues("procesess");
                if (processes != null) {
                    for (int i = 0; i < processes.length; i++) {
                        String idp = processes[i];
                        org.semanticwb.process.model.Process process = (org.semanticwb.process.model.Process) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(idp);
                        if (process != null) {
                            tc.addProcess(process);
                        }
                    }
                }
                response.setRenderParameter("uritc", tc.getURI());
            }
        } else if (action.equals(SWBResourceURL.Action_EDIT)) { //Editar plantilla de documentación
            String uritc = request.getParameter("uritc") != null ? request.getParameter("uritc").trim() : "";
            String titletc = request.getParameter("titletc") != null ? request.getParameter("titletc").trim() : "";
            //String title = request.getParameter("titleTemplate") != null ? request.getParameter("titleTemplate").trim() : "";
            if (!uritc.equals("") && !titletc.equals("")) {
                TemplateContainer tc = (TemplateContainer) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uritc);
                if (tc != null) {
                    tc.setTitle(titletc);
                    DocumentTemplate dt = tc.getActualTemplate();
                    if (dt != null) {
                        //dt.setTitle(title);
                        tc.removeAllProcess();
                        String[] processes = request.getParameterValues("procesess");
                        if (processes != null) {
                            for (int i = 0; i < processes.length; i++) {
                                String idp = processes[i];
                                org.semanticwb.process.model.Process process = (org.semanticwb.process.model.Process) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(idp);
                                if (process != null) {
                                    tc.addProcess(process);
                                }
                            }
                        }
                        Iterator<DocumentSection> iterator = dt.listDocumentSections();
                        while (iterator.hasNext()) {
                            DocumentSection ds = iterator.next();
                            try {
                                Integer index = Integer.parseInt(request.getParameter("ind" + ds.getURI()));
                                ds.setIndex(index);
                            } catch (NumberFormatException e) {
                                log.error("NumberFormatException, on " + action + ", " + e.getMessage() + ", " + e.getCause());
                            }
                            if (request.getParameter(ds.getURI()) != null) {
                                ds.setActive(true);
                            } else {
                                ds.setActive(false);
                            }
                        }
                    }
                }
            }
            response.setRenderParameter("uritc", uritc);
        } else if (action.equals(ACTION_ADD_VERSION_TEMPLATE)) {
            String title = request.getParameter("title") != null ? request.getParameter("title").trim() : "";
            String description = request.getParameter("description") != null ? request.getParameter("description").trim() : "";

            String uridtp = request.getParameter("uridtp") != null ? request.getParameter("uridtp").trim() : "";
            String uritc = request.getParameter("uritc") != null ? request.getParameter("uritc").trim() : "";
            boolean actual = request.getParameter("actual") != null;
            DocumentTemplate dtp = (DocumentTemplate) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uridtp);
            TemplateContainer tc = (TemplateContainer) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uritc);
            if (dtp != null && tc != null) {
                DocumentTemplate dtn = DocumentTemplate.ClassMgr.createDocumentTemplate(model);
                Iterator<DocumentSection> itds = dtp.listDocumentSections();
                Map map = new HashMap();
                while (itds.hasNext()) {
                    DocumentSection ds = itds.next();
                    if (request.getParameter(ds.getURI()) != null) {
                        DocumentSection dsn = DocumentSection.ClassMgr.createDocumentSection(model);
                        dsn.setActive(ds.isActive());
                        dsn.setConfigData(ds.getConfigData());
                        dsn.setDescription(ds.getDescription());
                        dsn.setIndex(ds.getIndex());
                        dsn.setSectionType(ds.getSectionType());
                        dsn.setTitle(ds.getTitle());
                        dsn.setVisibleProperties(ds.getVisibleProperties());
                        dsn.setParentTemplate(dtn);
                        dtn.addDocumentSection(dsn);
                        map.put(ds.getURI(), dsn);
                    }
                }
                //Copiar instancias de documentación
                Iterator<org.semanticwb.process.model.Process> itpro = tc.listProcesses();
                while (itpro.hasNext()) {
                    org.semanticwb.process.model.Process process = itpro.next();
                    Iterator<DocumentationInstance> itdi = DocumentationInstance.ClassMgr.listDocumentationInstanceByProcessRef(process, model);
                    while (itdi.hasNext()) {
                        DocumentationInstance di = itdi.next();

                        //Duplicar objeto DocumentationInstance
                        DocumentationInstance din = DocumentationInstance.ClassMgr.createDocumentationInstance(model);
                        din.setDocTypeDefinition(dtn);
                        din.setProcessRef(process);
                        
                        if (di.getDocTypeDefinition().getURI().equals(dtp.getURI())) {
                            Iterator<DocumentSectionInstance> itdsi = di.listDocumentSectionInstances();
                            while (itdsi.hasNext()) {
                                DocumentSectionInstance dsi = itdsi.next();
                                if (map.containsKey(dsi.getSecTypeDefinition().getURI())) {

                                    //Duplicar objeto DocumentSectionInstance
                                    DocumentSectionInstance dsin = DocumentSectionInstance.ClassMgr.createDocumentSectionInstance(model);
                                    din.addDocumentSectionInstance(dsin);
                                    dsin.setDocumentationInstance(din);

                                    Map replySe = new HashMap();//Almacenar elementos relacionados
                                    DocumentSection ds = (DocumentSection) map.get(dsi.getSecTypeDefinition().getURI());
                                    dsin.setSecTypeDefinition(ds);
                                    dsi.setIndex(ds.getIndex());
                                    Iterator<SectionElement> itse = dsi.listDocuSectionElementInstances();
                                    while (itse.hasNext()) {
                                        SectionElement se = itse.next();

//                                        SemanticObject semObj = SemanticObject.createSemanticObject(se.getSemanticObject().getRDFResource());
                                        SemanticClass scls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(dsi.getSecTypeDefinition().getSectionType().getURI());
                                        SemanticObject semOb = model.getSemanticModel().createSemanticObjectById(model.getSemanticModel().getCounter(scls) + "", scls);
                                        SectionElement sen = (SectionElement) semOb.createGenericInstance();
                                        sen.setDescription(se.getDescription());
                                        sen.setDocumentSectionInst(dsin);
                                        sen.setDocumentTemplate(dtn);
                                        sen.setIndex(se.getIndex());
                                        sen.setParentSection(ds);
                                        sen.setTitle(se.getTitle());
                                        dsin.addDocuSectionElementInstance(sen);
                                        replySe.put(se, sen);
                                        //Role OK
                                        if (se instanceof Rule) {
                                            ((Rule) sen).setNumber(((Rule) se).getNumber());
                                            ((Rule) sen).setPrefix(((Rule) se).getPrefix());
                                        }
                                        //Referable ok
                                        if (se instanceof Referable) {
                                            ((Referable) sen).setRefRepository(((Referable) se).getRefRepository());
                                        }
                                        if (se instanceof Definition) {
                                            ((Definition) sen).setFile(((Definition) se).getFile());
                                            ((Definition) sen).setNumber(((Definition) se).getNumber());
                                            ((Definition) sen).setPrefix(((Definition) se).getPrefix());
                                            ((Definition) sen).setRefRepository(((Definition) se).getRefRepository());
                                        }
                                        if (se instanceof ElementReference) {
                                            ((ElementReference) sen).setElementRef(((ElementReference) se).getElementRef());
                                            ((ElementReference) sen).setElementType(((ElementReference) se).getElementType());
                                        }
                                        if (se instanceof Format) {
                                            ((Format) sen).setFile(((Format) se).getFile());
                                            ((Format) sen).setKeyWords(((Format) se).getKeyWords());
                                            ((Format) sen).setRelatedSubjects(((Format) se).getRelatedSubjects());
                                        }
                                        if (se instanceof FreeText) {
                                            ((FreeText) sen).setText(((FreeText) se).getText());
                                        }
                                        if (se instanceof Indicator) {
                                            ((Indicator) sen).setFormula(((Indicator) se).getFormula());
                                            ((Indicator) sen).setFrequencyCalculation(((Indicator) se).getFrequencyCalculation());
                                            ((Indicator) sen).setInformationSource(((Indicator) se).getInformationSource());
                                            ((Indicator) sen).setMethodVerification(((Indicator) se).getMethodVerification());
                                            ((Indicator) sen).setNumber(((Indicator) se).getNumber());
                                            ((Indicator) sen).setObjetive(((Indicator) se).getObjetive());
                                            ((Indicator) sen).setResponsible(((Indicator) se).getResponsible());
                                            ((Indicator) sen).setWeightingIndicator(((Indicator) se).getWeightingIndicator());
                                        }
                                        //Model OK
                                        //Objetive OK
                                        if (se instanceof Policy) {
                                            ((Policy) sen).setNumber(((Policy) se).getNumber());
                                            ((Policy) sen).setPrefix(((Policy) se).getPrefix());
                                        }
                                        if (se instanceof Reference) {
                                            ((Reference) sen).setFile(((Reference) se).getFile());
                                            ((Reference) sen).setNumber(((Reference) se).getNumber());
                                            ((Reference) sen).setPrefix(((Reference) se).getPrefix());
                                            ((Reference) sen).setRefRepository(((Reference) se).getRefRepository());
                                            ((Reference) sen).setTypeReference(((Reference) se).getTypeReference());
                                        }
                                        //Risk OK

                                        if (se instanceof Activity) {//Activity
                                            Activity act = (Activity) se;
                                            Activity actn = (Activity) sen;

                                            ActivityRef actrn = ActivityRef.ClassMgr.createActivityRef(model);
                                            actrn.setProcessActivity(act.getActivityRef().getProcessActivity());
                                            actn.setDescription(act.getDescription());
                                            actn.setActivityRef(actrn);
                                            actn.setDocumentTemplate(dtn);
                                            actn.setFill(act.getFill());
                                            actn.setIndex(act.getIndex());
                                            actn.setTitle(act.getTitle());
                                            actn.setParentSection(ds);

                                            Iterator<SectionElementRef> itser = act.listSectionElementRefs();
                                            while (itser.hasNext()) {
                                                SectionElementRef ser = itser.next();
                                                SectionElementRef sern = SectionElementRef.ClassMgr.createSectionElementRef(model);
                                                sern.setActivity(actn);
                                                sern.setSectionElement((SectionElement) replySe.get(ser.getSectionElement()));
                                                actn.addSectionElementRef(sern);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                dtn.setTitle(title);
                dtn.setDescription(!description.equals("") ? description : null);
                dtn.setTemplateContainer(tc);
                dtn.setPreviousTemplate(tc.getLastTemplate());
                DocumentTemplate lt = tc.getLastTemplate();
                if (lt != null) {
                    lt.setNextTemplate(dtn);
                }
                tc.addTemplate(dtn);
                tc.setLastTemplate(dtn);
                if (actual) {
                    tc.setActualTemplate(dtn);
                }

                response.setRenderParameter("uridtn", dtn.getURI());

            }
            response.setRenderParameter("uridtp", uridtp);
            response.setRenderParameter("uritc", uritc);
        } else if (action.equals(ACTION_EDIT_VERSION_TEMPLATE)) {
            String uridt = request.getParameter("uridt") != null ? request.getParameter("uridt").trim() : "";
            String uritc = request.getParameter("uritc") != null ? request.getParameter("uritc").trim() : "";

            DocumentTemplate dt = (DocumentTemplate) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uridt);
            TemplateContainer tc = (TemplateContainer) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uritc);
            if (dt != null && tc != null) {
                String title = request.getParameter("title") != null ? request.getParameter("title").trim() : "";
                String description = request.getParameter("description") != null ? request.getParameter("description").trim() : "";
                boolean actual = request.getParameter("actual") != null;
                dt.setTitle(title);
                dt.setDescription(description);
                if (actual) {
                    tc.setActualTemplate(dt);
                }
            }
            response.setRenderParameter("uritc", uritc);
        } else if (action.equals(SWBResourceURL.Action_REMOVE)) {//Eliminar plantilla de documentación
            String uritc = request.getParameter("uritc") != null ? request.getParameter("uritc").trim() : "";
            TemplateContainer tc = (TemplateContainer) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uritc);
            if (tc != null) {
                Iterator<DocumentTemplate> itdt = tc.listTemplates();
                while (itdt.hasNext()) {
                    DocumentTemplate dt = itdt.next();
                    Iterator<DocumentationInstance> itdi = dt.listDocumentationInstances();
                    while (itdi.hasNext()) {
                        DocumentationInstance di = itdi.next();
                        di.remove();
                    }
                    dt.removeAllDocumentSection();
                    dt.remove();
                }
                tc.removeAllTemplate();
                tc.remove();
            }
        } else if (action.equals(ACTION_ADD_DOCUMENT_SECTION) && model != null) {
            String titleSection = request.getParameter("titleSection") != null ? request.getParameter("titleSection").trim() : "";
            String uritc = request.getParameter("uritc") != null ? request.getParameter("uritc").trim() : "";
            String dstype = request.getParameter("dstype") != null ? request.getParameter("dstype").trim() : "";
            SemanticClass semCls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(URLDecoder.decode(dstype));
            String configdata = "configdata";
            if (semCls != null) {
                if (semCls.isSubClass(Referable.swpdoc_Referable, false)) {
                    configdata = request.getParameter("configdata") != null ? request.getParameter("configdata").trim() : "";
                }
                if (!titleSection.equals("") && !uritc.equals("") && !dstype.equals("") && !configdata.equals("")) {
                    TemplateContainer tc = (TemplateContainer) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uritc);
                    if (tc != null) {
                        DocumentTemplate dt = tc.getActualTemplate();
                        DocumentSection ds = DocumentSection.ClassMgr.createDocumentSection(model);
                        ds.setConfigData(configdata);
                        ds.setTitle(titleSection);
                        ds.setParentTemplate(dt);
                        int i = (Integer.parseInt(SWBUtils.Collections.sizeOf(dt.listDocumentSections()) + "") + 1);
                        ds.setIndex(i);
                        ds.setSectionType(semCls.getSemanticObject());
                        ds.setActive(true);
                        dt.addDocumentSection(ds);
                        SWBFormMgr formMgr = new SWBFormMgr(semCls, model.getSemanticObject(), SWBFormMgr.MODE_EDIT);
                        formMgr.clearProperties();
                        Iterator<SemanticProperty> itsp = semCls.listProperties();
                        while (itsp.hasNext()) {
                            SemanticProperty sp = itsp.next();
                            if (sp.getDisplayProperty() != null) {
                                formMgr.addProperty(sp);
                            }
                        }
                        ds.setVisibleProperties("");
                        String newprop = "";
                        itsp = formMgr.getProperties().iterator();
                        while (itsp.hasNext()) {
                            SemanticProperty sp = itsp.next();
                            if (request.getParameter(sp.getPropId()) != null) {
                                String label = request.getParameter("label" + sp.getPropId());
                                newprop += label + ";" + sp.getPropId() + "|";
                            }
                        }
                        ds.setVisibleProperties(newprop);
                        response.setRenderParameter("urids", ds.getURI());
                    }
                }
            }
            response.setRenderParameter("uritc", uritc);
        } else if (action.equals(ACTION_DEFINE_VERSION_TEMPLATE)) {
            String uridt = request.getParameter("uridt") != null ? request.getParameter("uridt").trim() : "";
            String uritc = request.getParameter("uritc") != null ? request.getParameter("uritc").trim() : "";

            DocumentTemplate dt = (DocumentTemplate) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uridt);
            TemplateContainer tc = (TemplateContainer) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uritc);

            if (dt != null && tc != null) {
                tc.setActualTemplate(dt);
            }
            response.setRenderParameter("uritc", uritc);
        } else if (action.equals(ACTION_REMOVE_VERSION_TEMPLATE)) {
            String uridt = request.getParameter("uridt") != null ? request.getParameter("uridt").trim() : "";
            String uritc = request.getParameter("uritc") != null ? request.getParameter("uritc").trim() : "";
            DocumentTemplate dt = (DocumentTemplate) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uridt);
            TemplateContainer tc = (TemplateContainer) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uritc);
            if (dt != null && tc != null) {
                DocumentTemplate dtp = dt.getPreviousTemplate();//Previous template
                DocumentTemplate dtn = dt.getNextTemplate();//Next template
                if (dtp != null) {
                    dtp.setNextTemplate(dtn);
                }
                if (dtn != null) {
                    dtn.setPreviousTemplate(dtp);
                }
                if (tc.getLastTemplate() != null
                        && tc.getLastTemplate().getURI().equals(dt.getURI())
                        && dt.getPreviousTemplate() != null
                        && dtp != null) {
                    tc.setLastTemplate(dtp);
                } else if (tc.getLastTemplate() != null
                        && tc.getLastTemplate().getURI().equals(dt.getURI())
                        && dt.getPreviousTemplate() != null
                        && dtn != null) {
                    tc.setLastTemplate(dtn);
                }
                if (tc.getActualTemplate() != null
                        && tc.getActualTemplate().getURI().equals(dt.getURI())
                        && dtp != null) {
                    tc.setActualTemplate(dtp);
                } else if (tc.getActualTemplate() != null
                        && tc.getActualTemplate().getURI().equals(dt.getURI())
                        && dtn != null) {
                    tc.setActualTemplate(dtn);
                }
                tc.removeTemplate(dt);
                dt.remove();
            }
            response.setRenderParameter("uritc", uritc);
        } else if (action.equals(ACTION_EDIT_DOCUMENT_SECTION) && model != null) {
            String titleSection = request.getParameter("titleSection") != null ? request.getParameter("titleSection").trim() : "";
            String uritc = request.getParameter("uritc") != null ? request.getParameter("uritc").trim() : "";
            String urids = request.getParameter("urids") != null ? request.getParameter("urids").trim() : "";
            if (!titleSection.equals("") && !uritc.equals("") && !urids.equals("")) {
                DocumentSection ds = (DocumentSection) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(urids);
                ds.setTitle(titleSection);
                TemplateContainer tc = (TemplateContainer) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uritc);
                ds.setParentTemplate(tc != null ? tc.getActualTemplate() : null);
                SemanticClass semCls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(ds.getSectionType().getURI());
                ds.setSectionType(semCls.getSemanticObject());
                SWBFormMgr formMgr = new SWBFormMgr(semCls, model.getSemanticObject(), SWBFormMgr.MODE_EDIT);
                formMgr.clearProperties();
                Iterator<SemanticProperty> itsp = semCls.listProperties();
                while (itsp.hasNext()) {
                    SemanticProperty sp = itsp.next();
                    if (sp.getDisplayProperty() != null) {
                        formMgr.addProperty(sp);
                    }
                }
                ds.setVisibleProperties("");
                String newprop = "";
                itsp = formMgr.getProperties().iterator();
                while (itsp.hasNext()) {
                    SemanticProperty sp = itsp.next();
                    if (request.getParameter(sp.getPropId()) != null) {
                        String label = request.getParameter("label" + sp.getPropId());
                        newprop += label + ";" + sp.getPropId() + "|";
                    }
                }
                ds.setVisibleProperties(newprop);
            }
            response.setRenderParameter("urids", urids);
            response.setRenderParameter("uritc", uritc);
        } else if (action.equals(ACTION_REMOVE_DOCUMENT_SECTION)) {
            String uritc = request.getParameter("uritc") != null ? request.getParameter("uritc").trim() : "";
            String urids = request.getParameter("urids") != null ? request.getParameter("urids").trim() : "";
            if (!uritc.equals("") && !urids.equals("")) {
                TemplateContainer tc = (TemplateContainer) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uritc);
                if (tc != null) {
                    DocumentTemplate dt = tc.getActualTemplate();
                    DocumentSection ds = (DocumentSection) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(urids);
                    if (dt != null && ds != null) {
                        dt.removeDocumentSection(ds);
                        ds.remove();
                    }
                }
            }
            response.setRenderParameter("uritc", uritc);
        } else if (action.equals(ACTION_DUPLICATE_TEMPLATE)) {
            String uritc = request.getParameter("uritc") != null ? request.getParameter("uritc").trim() : "";
            String titletcd = request.getParameter("titletcd") != null ? request.getParameter("titletcd").trim() : "";
            String versiontemp = request.getParameter("versiontemp") != null ? request.getParameter("versiontemp").trim() : "";
//            TemplateContainer tc = (TemplateContainer) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uritc);
            DocumentTemplate dtact = (DocumentTemplate) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(versiontemp);
            if (dtact != null) {
                TemplateContainer tcn = TemplateContainer.ClassMgr.createTemplateContainer(model);
                tcn.setTitle(titletcd.trim());
                DocumentTemplate dt = DocumentTemplate.ClassMgr.createDocumentTemplate(model);
                dt.setTemplateContainer(tcn);
                tcn.addTemplate(dt);
                tcn.setActualTemplate(dt);
                tcn.setLastTemplate(dt);
                dt.setTitle(dtact.getTitle());
                dt.setDescription(dtact.getDescription());

                Iterator<DocumentSection> itds = SWBComparator.sortSortableObject(dtact.listDocumentSections());
                while (itds.hasNext()) {
                    DocumentSection ds = itds.next();
                    //New DocumentSection
                    DocumentSection dsn = DocumentSection.ClassMgr.createDocumentSection(model);
                    dsn.setParentTemplate(dt);
                    dt.addDocumentSection(dsn);
                    dsn.setConfigData(ds.getConfigData());
                    dsn.setIndex(ds.getIndex());
                    dsn.setSectionType(ds.getSectionType());
                    dsn.setTitle(ds.getTitle());
                    dsn.setVisibleProperties(ds.getVisibleProperties());
                    dsn.setActive(ds.isActive());
                }
                uritc = tcn.getURI();
            }
            response.setCallMethod(SWBResourceURL.Call_DIRECT);
            response.setMode(SWBResourceURL.Mode_EDIT);
            response.setRenderParameter("uritc", uritc);
        } else {
            super.processAction(request, response);
        }
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        if (mode.equals(SWBResourceURL.Mode_VIEW)) {
            doView(request, response, paramRequest);
        } else if (mode.equals(SWBResourceURL.Mode_EDIT)) {
            doEdit(request, response, paramRequest);
        } else if (mode.equals(MODE_EDIT_DOCUMENT_SECTION)) {
            doEditDocumentSection(request, response, paramRequest);
        } else if (mode.equals(MODE_PROPERTIES)) {
            doViewProperties(request, response, paramRequest);
        } else if (mode.equals(MODE_VIEW_LOG)) {
            doViewLog(request, response, paramRequest);
        } else if (mode.equals(MODE_VIEW_VERSION)) {
            doViewVersion(request, response, paramRequest);
        } else if (mode.equals(MODE_EDIT_VERSION_TEMPLATE)) {
            doEditVersionTemplate(request, response, paramRequest);
        } else if (mode.equals(MODE_DUPLICATE_TEMPLATE)) {
            doDuplicateTemplate(request, response, paramRequest);
        } else {
            super.processRequest(request, response, paramRequest);
        }
    }

    @Override
    public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String path = "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/documentation/documentTemplate.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(path);
        try {
            request.setAttribute(SWPUtils.PARAM_REQUEST, paramRequest);
            request.setAttribute(SWPUtils.LIST_TEMPLATES_CONTAINER, SWPUtils.listTemplateContainers(request, paramRequest));
            rd.forward(request, response);
        } catch (ServletException ex) {
            log.error("Error on doView, " + path + ", " + ex.getMessage());
        }
    }

    @Override
    public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String path = "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/documentation/documentTemplateEdit.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(path);
        try {
            request.setAttribute(SWPUtils.PARAM_REQUEST, paramRequest);
            request.setAttribute(SWPUtils.LIST_PROCESSES, SWPUtils.listProcessesByTemplate(request, paramRequest));
            rd.forward(request, response);
        } catch (ServletException ex) {
            log.error("Error on doEdit, " + path + ", " + ex.getMessage());
        }
    }

    public void doEditDocumentSection(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String path = "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/documentation/documentTemplateEditSection.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(path);
        try {
            request.setAttribute(SWPUtils.PARAM_REQUEST, paramRequest);
            rd.forward(request, response);
        } catch (ServletException ex) {
            log.error("Error on doAddDS, " + path + ", " + ex.getMessage());
        }
    }

    public void doViewProperties(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String path = "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/documentation/documentTemplateProperties.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(path);
        try {
            request.setAttribute(SWPUtils.PARAM_REQUEST, paramRequest);
            rd.forward(request, response);
        } catch (ServletException ex) {
            log.error("Error on doViewProperties, " + path + ", " + ex.getMessage());
        }
    }

    public void doViewLog(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String path = "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/documentation/logView.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(path);
        try {
            request.setAttribute(SWPUtils.PARAM_REQUEST, paramRequest);
            rd.forward(request, response);
        } catch (ServletException ex) {
            log.error("Error on doViewLog, " + path + ", " + ex.getMessage());
        }
    }

    public void doViewVersion(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String path = "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/documentation/viewVersion.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(path);
        try {
            request.setAttribute(SWPUtils.PARAM_REQUEST, paramRequest);
            rd.forward(request, response);
        } catch (ServletException ex) {
            log.error("Error on doViewVersion, " + path + ", " + ex.getMessage());
        }
    }

    public void doEditVersionTemplate(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String path = "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/documentation/newVersionTemplate.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(path);
        try {
            request.setAttribute(SWPUtils.PARAM_REQUEST, paramRequest);
            rd.forward(request, response);
        } catch (ServletException ex) {
            log.error("Error on doNewVersion, " + path + ", " + ex.getMessage());
        }
    }

    public void doDuplicateTemplate(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String path = "/work/models/" + paramRequest.getWebPage().getWebSiteId() + "/jsp/documentation/duplicateTemplate.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(path);
        try {
            request.setAttribute(SWPUtils.PARAM_REQUEST, paramRequest);
            rd.forward(request, response);
        } catch (ServletException ex) {
            log.error("Error on doDuplicateTemplate, " + path + ", " + ex.getMessage());
        }
    }
//int i = 0;
//    public void cloneInstance(SemanticObject instance, WebSite model) {
//        System.out.print("\n\ncloning instance...." + instance);
//        Iterator<SemanticProperty> itsp = instance.getSemanticClass().listProperties();
//        while (itsp.hasNext()) {
//            i++;
//            SemanticProperty sp = itsp.next();
//            if (!sp.getPropId().contains("swb:") && !sp.getPropId().contains("swpdoc:has")) {
//                System.out.print("\nsp: " + sp.getPropId());
//                if (sp.isObjectProperty()) {
//                    System.out.print(", obj value: " + instance.getObjectProperty(sp));
//                    SemanticObject sem = instance.getObjectProperty(sp);
//                    if (sem != null) {
//                        cloneInstance(instance.getObjectProperty(sp), model);
//                    }
//                } else {
//                    System.out.print(", prop value: " + instance.getProperty(sp));
//                }
//            }
//            if(i>100){
//                break;
//            }
//        }
//
////        try {
////            Field[] fields = instance.getClass().getFields();
////            for (Field field : fields) {
//////                if (!field.getName().contains("sclass") && !field.getName().contains("swb_")) {
////                    System.out.println("obj : " + instance + ", field : " + field.getName() + ", value : " + field.get(instance));
//////                }
////            }
////        } catch (Exception e) {
////            log.error("Error on cloneInstance, " + e.getMessage() + ", " + e.getCause());
////        }
//    }

}
