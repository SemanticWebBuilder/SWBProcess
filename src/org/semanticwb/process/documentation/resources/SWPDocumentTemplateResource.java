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
import org.semanticwb.process.model.Process;

/**
 * Componente que permite la administración de plantillas de documentación.
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
        WebSite model = response.getWebPage().getWebSite();  
        String uriTemplateCont = request.getParameter("uritc") != null ? request.getParameter("uritc").trim() : "";
        String uriDocTemplate = request.getParameter("uridt") != null ? request.getParameter("uridt").trim() : "";
        String uriDocSection = request.getParameter("urids") != null ? request.getParameter("urids").trim() : "";
        DocumentTemplate docTemplate = null;
        String titleTemplateCont = request.getParameter("titletc") != null ? request.getParameter("titletc").trim() : "";
        TemplateContainer templateCont = null;
        DocumentationInstance docInstance = null;
        DocumentSection docSection = null;
        
        response.setRenderParameter("action", action);
        
        if (action.equals(SWBResourceURL.Action_ADD) && !titleTemplateCont.isEmpty()) {//Agregar nueva plantilla de documentación
            templateCont = TemplateContainer.ClassMgr.createTemplateContainer(model);
            templateCont.setTitle(titleTemplateCont);
            docTemplate = DocumentTemplate.ClassMgr.createDocumentTemplate(model);
            docTemplate.setTemplateContainer(templateCont);
            templateCont.addTemplate(docTemplate);
            templateCont.setActualTemplate(docTemplate);
            templateCont.setLastTemplate(docTemplate);
            docTemplate.setTitle(titleTemplateCont);
            String[] processes = request.getParameterValues("procesess");
            if (processes != null) {
                for (String idp : processes) {
                    Process process = (Process) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(idp);
                    if (process != null) {
                        templateCont.addProcess(process);
                    }
                }
            }
            response.setRenderParameter("uritc", templateCont.getURI());
        } else if (action.equals(SWBResourceURL.Action_EDIT)) { //Editar plantilla de documentación
            templateCont = (TemplateContainer) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uriTemplateCont);
            if (templateCont != null) {
                templateCont.setTitle(titleTemplateCont);
                docTemplate = templateCont.getActualTemplate();
                if (docTemplate != null) {
                    templateCont.removeAllProcess();//TODO: Verificar que esto se deba hacer antes de validar que vengan procesos en el parámetro
                    String[] processes = request.getParameterValues("procesess");
                    if (processes != null) {
                        for (String idp : processes) {
                            Process process = (Process) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(idp);
                            if (process != null) {
                                templateCont.addProcess(process);
                            }
                        }
                    }
                    Iterator<DocumentSection> itDocSections = docTemplate.listDocumentSections();
                    while (itDocSections.hasNext()) {
                        docSection = itDocSections.next();
                        try{
                            Integer index = Integer.parseInt(request.getParameter("ind" + docSection.getURI()));
                            docSection.setIndex(index);
                        } catch (NumberFormatException e) {
                            log.error("NumberFormatException, on " + action + ", " + e.getMessage());
                        }

                        if (request.getParameter(docSection.getURI()) != null) {
                            docSection.setActive(true);
                        } else {
                            docSection.setActive(false);
                        }
                    }
                }
            }
            response.setRenderParameter("uritc", uriTemplateCont);
        } else if (action.equals(ACTION_ADD_VERSION_TEMPLATE)) {
            String title = request.getParameter("title") != null ? request.getParameter("title").trim() : "";
            String description = request.getParameter("description") != null ? request.getParameter("description").trim() : "";
            String uridtp = request.getParameter("uridtp") != null ? request.getParameter("uridtp").trim() : "";
            
            boolean actual = request.getParameter("actual") != null;
            docTemplate = (DocumentTemplate) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uridtp);
            templateCont = (TemplateContainer) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uriTemplateCont);
            if (docTemplate != null && templateCont != null) {
                DocumentTemplate docTemplateNew = DocumentTemplate.ClassMgr.createDocumentTemplate(model);
                Iterator<DocumentSection> itds = docTemplate.listDocumentSections();
                Map map = new HashMap();
                while (itds.hasNext()) {
                    docSection = itds.next();
                    if (request.getParameter(docSection.getURI()) != null) {
                        DocumentSection docSectionNew = DocumentSection.ClassMgr.createDocumentSection(model);
                        docSectionNew.setActive(docSection.isActive());
                        docSectionNew.setConfigData(docSection.getConfigData());
                        docSectionNew.setDescription(docSection.getDescription());
                        docSectionNew.setIndex(docSection.getIndex());
                        docSectionNew.setSectionType(docSection.getSectionType());
                        docSectionNew.setTitle(docSection.getTitle());
                        docSectionNew.setVisibleProperties(docSection.getVisibleProperties());
                        docSectionNew.setParentTemplate(docTemplateNew);
                        docTemplateNew.addDocumentSection(docSectionNew);
                        map.put(docSection.getURI(), docSectionNew);
                    }
                }
                //Copiar instancias de documentación
                Iterator<Process> itpro = templateCont.listProcesses();
                while (itpro.hasNext()) {
                    Process process = itpro.next();
                    Iterator<DocumentationInstance> itDocInstance = DocumentationInstance.ClassMgr.listDocumentationInstanceByProcessRef(process, model);
                    while (itDocInstance.hasNext()) {
                        docInstance = itDocInstance.next();

                        //Duplicar objeto DocumentationInstance
                        DocumentationInstance docInstanceNew = DocumentationInstance.ClassMgr.createDocumentationInstance(model);
                        docInstanceNew.setDocTypeDefinition(docTemplateNew);
                        docInstanceNew.setProcessRef(process);
                        
                        if (docInstance.getDocTypeDefinition().getURI().equals(docTemplate.getURI())) {
                            Iterator<DocumentSectionInstance> itDocSectInst = docInstance.listDocumentSectionInstances();
                            while (itDocSectInst.hasNext()) {
                                DocumentSectionInstance docSectionInstance = itDocSectInst.next();
                                if (map.containsKey(docSectionInstance.getSecTypeDefinition().getURI())) {

                                    //Duplicar objeto DocumentSectionInstance
                                    DocumentSectionInstance docSectionInstanceNew = DocumentSectionInstance.ClassMgr.createDocumentSectionInstance(model);
                                    docInstanceNew.addDocumentSectionInstance(docSectionInstanceNew);
                                    docSectionInstanceNew.setDocumentationInstance(docInstanceNew);

                                    Map replySe = new HashMap();//Almacenar elementos relacionados
                                    docSection = (DocumentSection) map.get(docSectionInstance.getSecTypeDefinition().getURI());
                                    docSectionInstanceNew.setSecTypeDefinition(docSection);
                                    docSectionInstance.setIndex(docSection.getIndex());
                                    Iterator<SectionElement> itse = docSectionInstance.listDocuSectionElementInstances();
                                    while (itse.hasNext()) {
                                        SectionElement se = itse.next();

//                                        SemanticObject semObj = SemanticObject.createSemanticObject(se.getSemanticObject().getRDFResource());
                                        SemanticClass scls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(docSectionInstance.getSecTypeDefinition().getSectionType().getURI());
                                        SemanticObject semOb = model.getSemanticModel().createSemanticObjectById(model.getSemanticModel().getCounter(scls) + "", scls);
                                        SectionElement sectionElementNew = (SectionElement) semOb.createGenericInstance();
                                        sectionElementNew.setDescription(se.getDescription());
                                        sectionElementNew.setDocumentSectionInst(docSectionInstanceNew);
                                        sectionElementNew.setDocumentTemplate(docTemplateNew);
                                        sectionElementNew.setIndex(se.getIndex());
                                        sectionElementNew.setParentSection(docSection);
                                        sectionElementNew.setTitle(se.getTitle());
                                        docSectionInstanceNew.addDocuSectionElementInstance(sectionElementNew);
                                        replySe.put(se, sectionElementNew);
                                        //Role OK
                                        if (se instanceof Rule) {
                                            ((Rule) sectionElementNew).setNumber(((Rule) se).getNumber());
                                            ((Rule) sectionElementNew).setPrefix(((Rule) se).getPrefix());
                                        }
                                        //Referable ok
                                        else if (se instanceof Referable) {
                                            ((Referable) sectionElementNew).setRefRepository(((Referable) se).getRefRepository());
                                        }
                                        else if (se instanceof Definition) {
                                            ((Definition) sectionElementNew).setFile(((Definition) se).getFile());
                                            ((Definition) sectionElementNew).setNumber(((Definition) se).getNumber());
                                            ((Definition) sectionElementNew).setPrefix(((Definition) se).getPrefix());
                                            ((Definition) sectionElementNew).setRefRepository(((Definition) se).getRefRepository());
                                        }
                                        else if (se instanceof ElementReference) {
                                            ((ElementReference) sectionElementNew).setElementRef(((ElementReference) se).getElementRef());
                                            ((ElementReference) sectionElementNew).setElementType(((ElementReference) se).getElementType());
                                        }
                                        else if (se instanceof Format) {
                                            ((Format) sectionElementNew).setFile(((Format) se).getFile());
                                            ((Format) sectionElementNew).setKeyWords(((Format) se).getKeyWords());
                                            ((Format) sectionElementNew).setRelatedSubjects(((Format) se).getRelatedSubjects());
                                        }
                                        else if (se instanceof FreeText) {
                                            ((FreeText) sectionElementNew).setText(((FreeText) se).getText());
                                        }
                                        else if (se instanceof Indicator) {
                                            ((Indicator) sectionElementNew).setFormula(((Indicator) se).getFormula());
                                            ((Indicator) sectionElementNew).setFrequencyCalculation(((Indicator) se).getFrequencyCalculation());
                                            ((Indicator) sectionElementNew).setInformationSource(((Indicator) se).getInformationSource());
                                            ((Indicator) sectionElementNew).setMethodVerification(((Indicator) se).getMethodVerification());
                                            ((Indicator) sectionElementNew).setNumber(((Indicator) se).getNumber());
                                            ((Indicator) sectionElementNew).setObjetive(((Indicator) se).getObjetive());
                                            ((Indicator) sectionElementNew).setResponsible(((Indicator) se).getResponsible());
                                            ((Indicator) sectionElementNew).setWeightingIndicator(((Indicator) se).getWeightingIndicator());
                                        }
                                        //Model OK
                                        //Objetive OK
                                        else if (se instanceof Policy) {
                                            ((Policy) sectionElementNew).setNumber(((Policy) se).getNumber());
                                            ((Policy) sectionElementNew).setPrefix(((Policy) se).getPrefix());
                                        }
                                        else if (se instanceof Reference) {
                                            ((Reference) sectionElementNew).setFile(((Reference) se).getFile());
                                            ((Reference) sectionElementNew).setNumber(((Reference) se).getNumber());
                                            ((Reference) sectionElementNew).setPrefix(((Reference) se).getPrefix());
                                            ((Reference) sectionElementNew).setRefRepository(((Reference) se).getRefRepository());
                                            ((Reference) sectionElementNew).setTypeReference(((Reference) se).getTypeReference());
                                        }
                                        //Risk OK

                                        else if (se instanceof Activity) {//Activity
                                            Activity activity = (Activity) se;
                                            Activity activityNew = (Activity) sectionElementNew;

                                            ActivityRef activityRefNew = ActivityRef.ClassMgr.createActivityRef(model);
                                            activityRefNew.setProcessActivity(activity.getActivityRef().getProcessActivity());
                                            activityNew.setDescription(activity.getDescription());
                                            activityNew.setActivityRef(activityRefNew);
                                            activityNew.setDocumentTemplate(docTemplateNew);
                                            activityNew.setFill(activity.getFill());
                                            activityNew.setIndex(activity.getIndex());
                                            activityNew.setTitle(activity.getTitle());
                                            activityNew.setParentSection(docSection);

                                            Iterator<SectionElementRef> itser = activity.listSectionElementRefs();
                                            while (itser.hasNext()) {
                                                SectionElementRef ser = itser.next();
                                                SectionElementRef sern = SectionElementRef.ClassMgr.createSectionElementRef(model);
                                                sern.setActivity(activityNew);
                                                sern.setSectionElement((SectionElement) replySe.get(ser.getSectionElement()));
                                                activityNew.addSectionElementRef(sern);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                docTemplateNew.setTitle(title);
                docTemplateNew.setDescription(!description.equals("") ? description : null);
                docTemplateNew.setTemplateContainer(templateCont);
                docTemplateNew.setPreviousTemplate(templateCont.getLastTemplate());
                DocumentTemplate lastTemplate = templateCont.getLastTemplate();
                if (lastTemplate != null) {
                    lastTemplate.setNextTemplate(docTemplateNew);
                }
                templateCont.addTemplate(docTemplateNew);
                templateCont.setLastTemplate(docTemplateNew);
                if (actual) {
                    templateCont.setActualTemplate(docTemplateNew);
                }

                response.setRenderParameter("uridtn", docTemplateNew.getURI());

            }
            response.setRenderParameter("uridtp", uridtp);
            response.setRenderParameter("uritc", uriTemplateCont);
        } else if (action.equals(ACTION_EDIT_VERSION_TEMPLATE)) {
            docTemplate = (DocumentTemplate) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uriDocTemplate);
            templateCont = (TemplateContainer) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uriTemplateCont);
            if (docTemplate != null && templateCont != null) {
                String title = request.getParameter("title") != null ? request.getParameter("title").trim() : "";
                String description = request.getParameter("description") != null ? request.getParameter("description").trim() : "";
                boolean actual = request.getParameter("actual") != null;
                docTemplate.setTitle(title);
                docTemplate.setDescription(description);
                if (actual) {
                    templateCont.setActualTemplate(docTemplate);
                }
            }
            response.setRenderParameter("uritc", uriTemplateCont);
        } else if (action.equals(SWBResourceURL.Action_REMOVE)) {//Eliminar plantilla de documentación
            templateCont = (TemplateContainer) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uriTemplateCont);
            if (templateCont != null) {
                Iterator<DocumentTemplate> itDocTemplate = templateCont.listTemplates();
                while (itDocTemplate.hasNext()) {
                    docTemplate = itDocTemplate.next();
                    Iterator<DocumentationInstance> itDocInstance = docTemplate.listDocumentationInstances();
                    while (itDocInstance.hasNext()) {
                        docInstance = itDocInstance.next();
                        docInstance.remove();
                    }
                    docTemplate.removeAllDocumentSection();
                    docTemplate.remove();
                }
                templateCont.removeAllTemplate();
                templateCont.remove();
            }
        } else if (action.equals(ACTION_ADD_DOCUMENT_SECTION) && model != null) {
            String titleSection = request.getParameter("titleSection") != null ? request.getParameter("titleSection").trim() : "";
            String dstype = request.getParameter("dstype") != null ? request.getParameter("dstype").trim() : "";
            SemanticClass semCls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(URLDecoder.decode(dstype));
            String configData = "configdata";
            if (semCls != null) {
                if (semCls.isSubClass(Referable.swpdoc_Referable, false)) {
                    configData = request.getParameter("configdata") != null ? request.getParameter("configdata").trim() : "";
                }
                if (!titleSection.isEmpty() && !uriTemplateCont.isEmpty() && !dstype.isEmpty() && !configData.isEmpty()) {
                    templateCont = (TemplateContainer) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uriTemplateCont);
                    if (templateCont != null) {
                        docTemplate = templateCont.getActualTemplate();
                        docSection = DocumentSection.ClassMgr.createDocumentSection(model);
                        docSection.setConfigData(configData);
                        docSection.setTitle(titleSection);
                        docSection.setParentTemplate(docTemplate);
                        int i = (Integer.parseInt(SWBUtils.Collections.sizeOf(docTemplate.listDocumentSections()) + "") + 1);
                        docSection.setIndex(i);
                        docSection.setSectionType(semCls.getSemanticObject());
                        docSection.setActive(true);
                        docTemplate.addDocumentSection(docSection);
                        SWBFormMgr formMgr = new SWBFormMgr(semCls, model.getSemanticObject(), SWBFormMgr.MODE_EDIT);
                        formMgr.clearProperties();
                        Iterator<SemanticProperty> itsp = semCls.listProperties();
                        while (itsp.hasNext()) {
                            SemanticProperty sp = itsp.next();
                            if (sp.getDisplayProperty() != null) {
                                formMgr.addProperty(sp);
                            }
                        }
                        docSection.setVisibleProperties("");
                        String newprop = "";
                        itsp = formMgr.getProperties().iterator();
                        while (itsp.hasNext()) {
                            SemanticProperty sp = itsp.next();
                            if (request.getParameter(sp.getPropId()) != null) {
                                String label = request.getParameter("label" + sp.getPropId());
                                newprop += label + ";" + sp.getPropId() + "|";
                            }
                        }
                        docSection.setVisibleProperties(newprop);
                        response.setRenderParameter("urids", docSection.getURI());
                    }
                }
            }
            response.setRenderParameter("uritc", uriTemplateCont);
        } else if (action.equals(ACTION_DEFINE_VERSION_TEMPLATE)) {
            docTemplate = (DocumentTemplate) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uriDocTemplate);
            templateCont = (TemplateContainer) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uriTemplateCont);

            if (docTemplate != null && templateCont != null) {
                templateCont.setActualTemplate(docTemplate);
            }
            response.setRenderParameter("uritc", uriTemplateCont);
        } else if (action.equals(ACTION_REMOVE_VERSION_TEMPLATE)) {
            docTemplate = (DocumentTemplate) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uriDocTemplate);
            templateCont = (TemplateContainer) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uriTemplateCont);
            if (docTemplate != null && templateCont != null) {
                DocumentTemplate docTemplatePrev = docTemplate.getPreviousTemplate();//Previous template
                DocumentTemplate docTemplateNext = docTemplate.getNextTemplate();//Next template
                if (docTemplatePrev != null) {
                    docTemplatePrev.setNextTemplate(docTemplateNext);
                }
                if (docTemplateNext != null) {
                    docTemplateNext.setPreviousTemplate(docTemplatePrev);
                }
                if (templateCont.getLastTemplate() != null
                        && templateCont.getLastTemplate().getURI().equals(docTemplate.getURI())
                        && docTemplate.getPreviousTemplate() != null
                        && docTemplatePrev != null) {
                    templateCont.setLastTemplate(docTemplatePrev);
                } else if (templateCont.getLastTemplate() != null
                        && templateCont.getLastTemplate().getURI().equals(docTemplate.getURI())
                        && docTemplate.getPreviousTemplate() != null
                        && docTemplateNext != null) {
                    templateCont.setLastTemplate(docTemplateNext);
                }
                if (templateCont.getActualTemplate() != null
                        && templateCont.getActualTemplate().getURI().equals(docTemplate.getURI())
                        && docTemplatePrev != null) {
                    templateCont.setActualTemplate(docTemplatePrev);
                } else if (templateCont.getActualTemplate() != null
                        && templateCont.getActualTemplate().getURI().equals(docTemplate.getURI())
                        && docTemplateNext != null) {
                    templateCont.setActualTemplate(docTemplateNext);
                }
                templateCont.removeTemplate(docTemplate);
                docTemplate.remove();
            }
            response.setRenderParameter("uritc", uriTemplateCont);
        } else if (action.equals(ACTION_EDIT_DOCUMENT_SECTION) && model != null) {
            String titleSection = request.getParameter("titleSection") != null ? request.getParameter("titleSection").trim() : "";
            if (!titleSection.isEmpty() && !uriTemplateCont.isEmpty() && !uriDocSection.isEmpty()) {
                docSection = (DocumentSection) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uriDocSection);
                docSection.setTitle(titleSection);
                templateCont = (TemplateContainer) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uriTemplateCont);
                docSection.setParentTemplate(templateCont != null ? templateCont.getActualTemplate() : null);
                SemanticClass semCls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(docSection.getSectionType().getURI());
                docSection.setSectionType(semCls.getSemanticObject());
                SWBFormMgr formMgr = new SWBFormMgr(semCls, model.getSemanticObject(), SWBFormMgr.MODE_EDIT);
                formMgr.clearProperties();
                Iterator<SemanticProperty> itSemanticProperty = semCls.listProperties();
                while (itSemanticProperty.hasNext()) {
                    SemanticProperty sp = itSemanticProperty.next();
                    if (sp.getDisplayProperty() != null) {
                        formMgr.addProperty(sp);
                    }
                }
                docSection.setVisibleProperties("");
                String newprop = "";
                itSemanticProperty = formMgr.getProperties().iterator();
                while (itSemanticProperty.hasNext()) {
                    SemanticProperty sp = itSemanticProperty.next();
                    if (request.getParameter(sp.getPropId()) != null) {
                        String label = request.getParameter("label" + sp.getPropId());
                        newprop += label + ";" + sp.getPropId() + "|";
                    }
                }
                docSection.setVisibleProperties(newprop);
            }
            response.setRenderParameter("urids", uriDocSection);
            response.setRenderParameter("uritc", uriTemplateCont);
        } else if (action.equals(ACTION_REMOVE_DOCUMENT_SECTION)) {
            if (!uriTemplateCont.isEmpty() && !uriDocSection.isEmpty()) {
                templateCont = (TemplateContainer) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uriTemplateCont);
                if (templateCont != null) {
                    docTemplate = templateCont.getActualTemplate();
                    DocumentSection ds = (DocumentSection) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uriDocSection);
                    if (docTemplate != null && ds != null) {
                        docTemplate.removeDocumentSection(ds);
                        ds.remove();
                    }
                }
            }
            response.setRenderParameter("uritc", uriTemplateCont);
        } else if (action.equals(ACTION_DUPLICATE_TEMPLATE)) {
            String titletcd = request.getParameter("titletcd") != null ? request.getParameter("titletcd").trim() : "";
            String versiontemp = request.getParameter("versiontemp") != null ? request.getParameter("versiontemp").trim() : "";

            DocumentTemplate dtact = (DocumentTemplate) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(versiontemp);
            if (dtact != null) {
                templateCont = TemplateContainer.ClassMgr.createTemplateContainer(model);
                templateCont.setTitle(titletcd.trim());
                docTemplate = DocumentTemplate.ClassMgr.createDocumentTemplate(model);
                docTemplate.setTemplateContainer(templateCont);
                templateCont.addTemplate(docTemplate);
                templateCont.setActualTemplate(docTemplate);
                templateCont.setLastTemplate(docTemplate);
                docTemplate.setTitle(dtact.getTitle());
                docTemplate.setDescription(dtact.getDescription());

                Iterator<DocumentSection> itDocSection = SWBComparator.sortSortableObject(dtact.listDocumentSections());
                while (itDocSection.hasNext()) {
                    docSection = itDocSection.next();
                    //New DocumentSection
                    DocumentSection docSectionNew = DocumentSection.ClassMgr.createDocumentSection(model);
                    docSectionNew.setParentTemplate(docTemplate);
                    docTemplate.addDocumentSection(docSectionNew);
                    docSectionNew.setConfigData(docSection.getConfigData());
                    docSectionNew.setIndex(docSection.getIndex());
                    docSectionNew.setSectionType(docSection.getSectionType());
                    docSectionNew.setTitle(docSection.getTitle());
                    docSectionNew.setVisibleProperties(docSection.getVisibleProperties());
                    docSectionNew.setActive(docSection.isActive());
                }
                uriTemplateCont = templateCont.getURI();
            }
            response.setCallMethod(SWBResourceURL.Call_DIRECT);
            response.setMode(SWBResourceURL.Mode_EDIT);
            response.setRenderParameter("uritc", uriTemplateCont);
        } else {
            super.processAction(request, response);
        }
    }

    @Override
    public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
        String mode = paramRequest.getMode();
        switch (mode) {
            case SWBResourceURL.Mode_VIEW:
                doView(request, response, paramRequest);
                break;
            case SWBResourceURL.Mode_EDIT:
                doEdit(request, response, paramRequest);
                break;
            case MODE_EDIT_DOCUMENT_SECTION:
                doEditDocumentSection(request, response, paramRequest);
                break;
            case MODE_PROPERTIES:
                doViewProperties(request, response, paramRequest);
                break;
            case MODE_VIEW_LOG:
                doViewLog(request, response, paramRequest);
                break;
            case MODE_VIEW_VERSION:
                doViewVersion(request, response, paramRequest);
                break;
            case MODE_EDIT_VERSION_TEMPLATE:
                doEditVersionTemplate(request, response, paramRequest);
                break;
            case MODE_DUPLICATE_TEMPLATE:
                doDuplicateTemplate(request, response, paramRequest);
                break;
            default:
                super.processRequest(request, response, paramRequest);
                break;
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
}
