package org.semanticwb.process.documentation.resources;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.Traceable;
import org.semanticwb.model.User;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
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
import org.semanticwb.process.documentation.model.Instantiable;
import org.semanticwb.process.documentation.model.Policy;
import org.semanticwb.process.documentation.model.Reference;
import org.semanticwb.process.documentation.model.Rule;
import org.semanticwb.process.documentation.model.SectionElement;
import org.semanticwb.process.documentation.model.SectionElementRef;
import org.semanticwb.process.documentation.model.TemplateContainer;
import org.semanticwb.process.model.Process;

/**
 * Componente que permite la administración de plantillas de documentación.
 * 
 * @author carlos.alvarez
 */
public class SWPDocumentTemplateResource extends GenericResource {
	private static final Logger log = SWBUtils.getLogger(SWPDocumentTemplateResource.class);
	public static final String MODE_EDIT_DOCUMENT_SECTION = "m_eds";// Modo EDITAR de sección de documentación
	public static final String ACTION_ADD_DOCUMENT_SECTION = "a_ads";// Acción GUARDAR NUEVA sección de documentación
	public static final String ACTION_ADD_VERSION_TEMPLATE = "a_anvt";// Acción GUARDAR NUEVA sección de documentación
	public static final String ACTION_DEFINE_VERSION_TEMPLATE = "a_dvt";// Acción GUARDAR NUEVA sección de documentación
	public static final String ACTION_REMOVE_VERSION_TEMPLATE = "a_rvt";// Acción GUARDAR NUEVA sección de documentación
	public static final String ACTION_EDIT_VERSION_TEMPLATE = "a_aevt";// Acción GUARDAR NUEVA sección de documentación
	public static final String ACTION_EDIT_DOCUMENT_SECTION = "a_eds";// Acción EDITAR de sección de documentación
	public static final String ACTION_REMOVE_DOCUMENT_SECTION = "a_rds";// Acción ELIMINAR de sección de documentación
	public static final String ACTION_DUPLICATE_TEMPLATE = "a_dute";// Acción ELIMINAR de sección de documentación
	public static final String MODE_VIEW_LOG = "m_vlo";// Acción ELIMINAR de sección de documentación
	public static final String MODE_VIEW_VERSION = "m_vve";// Acción ELIMINAR de sección de documentación
	public static final String MODE_DUPLICATE_TEMPLATE = "m_dute";// Acción ELIMINAR de sección de documentación
	public static final String MODE_EDIT_VERSION_TEMPLATE = "m_nvet";// Acción ELIMINAR de sección de documentación
	public static final String MODE_ADD_TEMPLATE = "m_addt";// Acción ELIMINAR de sección de documentación
	public static final String MODE_ADD_DOCUMENT_SECTION = "m_ads";// Acción GUARDAR NUEVA sección de documentación
	public static final String MODE_PROPERTIES = "m_pro";// Acción ELIMINAR de sección de documentación
	public static final String MODE_RESPONSE = "m_res";// Acción ELIMINAR de sección de documentación
	public static final String LIST_PROCESSES = "listTemplates";// Listar instancias de objeto Process
	public static final String PARAM_REQUEST = "paramRequest";// Bean paramRequest
	public static final String LIST_TEMPLATES_CONTAINER = "listContainers";// Listar instancias de objeto DocumentTemplate
	public static final String PARAM_URITC = "uritc";
	public static final String PARAM_URIDT = "uridt";
	public static final String PARAM_URIDTP = "uridtp";
	public static final String PARAM_URIDS = "urids";
	public static final String PARAM_STATUS = "status";
	public static final String PARAM_DESCRIPTION = "description";
	
	@Override
	public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
		String action = response.getAction();
		WebSite model = response.getWebPage().getWebSite();
		String uriTemplateCont = request.getParameter(PARAM_URITC) != null ? request.getParameter(PARAM_URITC).trim() : "";
		String uriDocTemplate = request.getParameter(PARAM_URIDT) != null ? request.getParameter(PARAM_URIDT).trim() : "";
		String uriDocSection = request.getParameter(PARAM_URIDS) != null ? request.getParameter(PARAM_URIDS).trim() : "";
		DocumentTemplate docTemplate = null;
		String titleTemplateCont = request.getParameter("titletc") != null ? request.getParameter("titletc").trim() : "";
		TemplateContainer templateCont = null;
		DocumentationInstance docInstance = null;
		DocumentSection docSection = null;
		String lang = "es";
		User user = response.getUser();
		if (user != null && user.getLanguage() != null) {
			lang = user.getLanguage();
		}

		response.setRenderParameter("action", action);

		if (action.equals(SWBResourceURL.Action_ADD)) {
			if (!titleTemplateCont.isEmpty()) {// Agregar nueva plantilla de documentación
				templateCont = TemplateContainer.ClassMgr.createTemplateContainer(model);
				templateCont.setTitle(titleTemplateCont);
				docTemplate = DocumentTemplate.ClassMgr.createDocumentTemplate(model);
				docTemplate.setVersionValue(DocumentTemplate.getNextVersionValue(null));
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
				response.setRenderParameter(PARAM_URITC, templateCont.getURI());
				response.setRenderParameter(PARAM_STATUS, "ok");
			} else {
				response.setRenderParameter(PARAM_STATUS, "error");
			}
			response.setMode(MODE_RESPONSE);
		} else if (action.equals(SWBResourceURL.Action_EDIT)) { // Editar plantilla de documentación
			String docTemplateURI = request.getParameter(PARAM_URIDT);
			DocumentTemplate dt = (DocumentTemplate) SWBPlatform.getSemanticMgr().getOntology()
					.getGenericObject(docTemplateURI);
			if (null != dt) {
				// Get document template to edit
				templateCont = dt.getTemplateContainer();
				templateCont.setTitle(titleTemplateCont);

				// Set up process assignation
				templateCont.removeAllProcess();// TODO: Verificar que esto se deba hacer antes de validar que vengan
												// procesos en el parámetro
				String[] processes = request.getParameterValues("procesess");
				if (processes != null) {
					for (String idp : processes) {
						Process process = (Process) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(idp);
						if (process != null) {
							templateCont.addProcess(process);
						}
					}
				}

				// Update document temlpate sections
				Iterator<DocumentSection> itDocSections = dt.listDocumentSections();
				while (itDocSections.hasNext()) {
					docSection = itDocSections.next();
					try {
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
				response.setRenderParameter(PARAM_URIDT, dt.getURI());
			}
		} else if (action.equals(ACTION_ADD_VERSION_TEMPLATE)) {
			String description = request.getParameter(PARAM_DESCRIPTION) != null
					? request.getParameter(PARAM_DESCRIPTION).trim()
					: "";
			String uridtp = request.getParameter(PARAM_URIDTP) != null ? request.getParameter(PARAM_URIDTP).trim() : "";

			// Selected template version
			templateCont = (TemplateContainer) SWBPlatform.getSemanticMgr().getOntology()
					.getGenericObject(uriTemplateCont);
			docTemplate = (DocumentTemplate) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uridtp);

			if (null != templateCont && null == docTemplate)
				docTemplate = templateCont.getLastTemplate();
			if (docTemplate != null && templateCont != null) {
				DocumentTemplate docTemplateNew = DocumentTemplate.ClassMgr.createDocumentTemplate(model);
				Iterator<DocumentSection> itds = docTemplate.listDocumentSections();
				Map<String, DocumentSection> map = new HashMap<>();
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
						docTemplateNew.addDocumentSection(docSectionNew);
						map.put(docSection.getURI(), docSectionNew);
					}
				}
				// Copiar instancias de documentación
				Iterator<Process> itpro = templateCont.listProcesses();
				while (itpro.hasNext()) {
					Process process = itpro.next();
					Iterator<DocumentationInstance> itDocInstance = DocumentationInstance.ClassMgr
							.listDocumentationInstanceByProcessRef(process, model);
					while (itDocInstance.hasNext()) {
						docInstance = itDocInstance.next();

						// Duplicar objeto DocumentationInstance
						DocumentationInstance docInstanceNew = DocumentationInstance.ClassMgr
								.createDocumentationInstance(model);
						docInstanceNew.setDocTypeDefinition(docTemplateNew);
						docInstanceNew.setProcessRef(process);

						if (docInstance.getDocTypeDefinition().getURI().equals(docTemplate.getURI())) {
							Iterator<DocumentSectionInstance> itDocSectInst = docInstance
									.listDocumentSectionInstances();
							while (itDocSectInst.hasNext()) {
								DocumentSectionInstance docSectionInstance = itDocSectInst.next();
								if (map.containsKey(docSectionInstance.getSecTypeDefinition().getURI())) {

									// Duplicar objeto DocumentSectionInstance
									DocumentSectionInstance docSectionInstanceNew = DocumentSectionInstance.ClassMgr
											.createDocumentSectionInstance(model);
									docInstanceNew.addDocumentSectionInstance(docSectionInstanceNew);
									docSectionInstanceNew.setDocumentationInstance(docInstanceNew);

									Map<SectionElement, SectionElement> replySe = new HashMap<SectionElement, SectionElement>();
									//Almacenar elementos relacionados
									docSection = (DocumentSection) map
											.get(docSectionInstance.getSecTypeDefinition().getURI());
									docSectionInstanceNew.setSecTypeDefinition(docSection);
									docSectionInstance.setIndex(docSection.getIndex());
									Iterator<SectionElement> itse = docSectionInstance
											.listDocuSectionElementInstances();
									while (itse.hasNext()) {
										SectionElement se = itse.next();

										SemanticClass scls = SWBPlatform.getSemanticMgr().getVocabulary()
												.getSemanticClass(docSectionInstance.getSecTypeDefinition()
														.getSectionType().getURI());
										SemanticObject semOb = model.getSemanticModel().createSemanticObjectById(
												model.getSemanticModel().getCounter(scls) + "", scls);
										SectionElement sectionElementNew = (SectionElement) semOb
												.createGenericInstance();
										sectionElementNew.setDescription(se.getDescription());
										sectionElementNew.setDocumentSectionInst(docSectionInstanceNew);
										sectionElementNew.setDocumentTemplate(docTemplateNew);
										sectionElementNew.setIndex(se.getIndex());
										sectionElementNew.setParentSection(docSection);
										sectionElementNew.setTitle(se.getTitle());
										docSectionInstanceNew.addDocuSectionElementInstance(sectionElementNew);
										replySe.put(se, sectionElementNew);
										// Role OK
										if (se instanceof Rule) {
											((Rule) sectionElementNew).setNumber(((Rule) se).getNumber());
											((Rule) sectionElementNew).setPrefix(((Rule) se).getPrefix());
										}
										// Referable ok
										else if (se instanceof Definition) {
											((Definition) sectionElementNew).setFile(((Definition) se).getFile());
											((Definition) sectionElementNew).setNumber(((Definition) se).getNumber());
											((Definition) sectionElementNew).setPrefix(((Definition) se).getPrefix());
										} else if (se instanceof ElementReference) {
											((ElementReference) sectionElementNew)
													.setElementRef(((ElementReference) se).getElementRef());
											((ElementReference) sectionElementNew)
													.setElementType(((ElementReference) se).getElementType());
										} else if (se instanceof Format) {
											((Format) sectionElementNew).setFile(((Format) se).getFile());
											((Format) sectionElementNew).setKeyWords(((Format) se).getKeyWords());
											((Format) sectionElementNew)
													.setRelatedSubjects(((Format) se).getRelatedSubjects());
										} else if (se instanceof FreeText) {
											((FreeText) sectionElementNew).setText(((FreeText) se).getText());
										} else if (se instanceof Indicator) {
											((Indicator) sectionElementNew).setFormula(((Indicator) se).getFormula());
											((Indicator) sectionElementNew).setFrequencyCalculation(
													((Indicator) se).getFrequencyCalculation());
											((Indicator) sectionElementNew)
													.setInformationSource(((Indicator) se).getInformationSource());
											((Indicator) sectionElementNew)
													.setMethodVerification(((Indicator) se).getMethodVerification());
											((Indicator) sectionElementNew).setNumber(((Indicator) se).getNumber());
											((Indicator) sectionElementNew).setObjetive(((Indicator) se).getObjetive());
											((Indicator) sectionElementNew)
													.setResponsible(((Indicator) se).getResponsible());
											((Indicator) sectionElementNew)
													.setWeightingIndicator(((Indicator) se).getWeightingIndicator());
										}
										// Model OK
										// Objetive OK
										else if (se instanceof Policy) {
											((Policy) sectionElementNew).setNumber(((Policy) se).getNumber());
											((Policy) sectionElementNew).setPrefix(((Policy) se).getPrefix());
										} else if (se instanceof Reference) {
											((Reference) sectionElementNew).setFile(((Reference) se).getFile());
											((Reference) sectionElementNew).setNumber(((Reference) se).getNumber());
											((Reference) sectionElementNew).setPrefix(((Reference) se).getPrefix());
											((Reference) sectionElementNew)
													.setTypeReference(((Reference) se).getTypeReference());
										}
										// Risk OK

										else if (se instanceof Activity) {// Activity
											Activity activity = (Activity) se;
											Activity activityNew = (Activity) sectionElementNew;

											ActivityRef activityRefNew = ActivityRef.ClassMgr.createActivityRef(model);
											activityRefNew
													.setProcessActivity(activity.getActivityRef().getProcessActivity());
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
												SectionElementRef sern = SectionElementRef.ClassMgr
														.createSectionElementRef(model);
												sern.setActivity(activityNew);
												sern.setSectionElement(
														(SectionElement) replySe.get(ser.getSectionElement()));
												activityNew.addSectionElementRef(sern);
											}
										}
									}
								}
							}
						}
					}
				}

				docTemplateNew.setTitle(docTemplate.getTitle());
				docTemplateNew.setVersionComment(!description.equals("") ? description : null);
				// docTemplateNew.setTemplateContainer(templateCont);
				docTemplateNew.setVersionValue(DocumentTemplate.getNextVersionValue(docTemplate.getVersionValue()));
				docTemplateNew.setPreviousTemplate(templateCont.getLastTemplate());
				DocumentTemplate lastTemplate = templateCont.getLastTemplate();
				if (lastTemplate != null) {
					lastTemplate.setNextTemplate(docTemplateNew);
				}
				templateCont.addTemplate(docTemplateNew);
				templateCont.setLastTemplate(docTemplateNew);

				response.setRenderParameter(PARAM_STATUS, "ok");
				response.setRenderParameter("uridtn", docTemplateNew.getURI());
			} else {
				response.setRenderParameter(PARAM_STATUS, "error");
			}
			response.setRenderParameter(PARAM_URIDTP, uridtp);
			response.setRenderParameter(PARAM_URITC, uriTemplateCont);
			response.setMode(MODE_RESPONSE);
		} else if (action.equals(ACTION_EDIT_VERSION_TEMPLATE)) {
			docTemplate = (DocumentTemplate) SWBPlatform.getSemanticMgr().getOntology()
					.getGenericObject(uriDocTemplate);
			templateCont = (TemplateContainer) SWBPlatform.getSemanticMgr().getOntology()
					.getGenericObject(uriTemplateCont);
			if (docTemplate != null && templateCont != null) {
				String title = request.getParameter("title") != null ? request.getParameter("title").trim() : "";
				String description = request.getParameter(PARAM_DESCRIPTION) != null
						? request.getParameter(PARAM_DESCRIPTION).trim()
						: "";
				boolean actual = request.getParameter("actual") != null;
				docTemplate.setTitle(title);
				docTemplate.setDescription(description);
				if (actual) {
					templateCont.setActualTemplate(docTemplate);
				}
			}
			response.setRenderParameter(PARAM_URITC, uriTemplateCont);
		} else if (action.equals(SWBResourceURL.Action_REMOVE)) {// Eliminar plantilla de documentación
			templateCont = (TemplateContainer) SWBPlatform.getSemanticMgr().getOntology()
					.getGenericObject(uriTemplateCont);
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
			docTemplate = (DocumentTemplate) SWBPlatform.getSemanticMgr().getOntology()
					.getGenericObject(uriDocTemplate);
			SemanticClass semCls = SWBPlatform.getSemanticMgr().getVocabulary()
					.getSemanticClass(request.getParameter("dstype"));
			String titleSection = request.getParameter("titleSection");

			if (null != docTemplate && null != semCls && null != titleSection && !titleSection.isEmpty()) {
				StringBuilder ip = new StringBuilder();
				String ignoreProps = "";

				Iterator<SemanticProperty> props = Traceable.swb_Traceable.listProperties();
				while (props.hasNext()) {
					SemanticProperty prop = props.next();
					ip.append(prop.getPropId()).append("|");
				}
				ignoreProps = ip.toString();

				docSection = DocumentSection.ClassMgr.createDocumentSection(model);
				docSection.setTitle(titleSection);
				int i = (Integer.parseInt(SWBUtils.Collections.sizeOf(docTemplate.listDocumentSections()) + "") + 1);
				docSection.setIndex(i);
				docSection.setSectionType(semCls.getSemanticObject());
				docSection.setActive(true);
				docTemplate.addDocumentSection(docSection);

				// Set properties configuration
				if (semCls.isSubClass(Instantiable.swpdoc_Instantiable, false)) {
					Iterator<SemanticProperty> itSemanticProperty = semCls.listProperties();
					String newprop = "";
					while (itSemanticProperty.hasNext()) {
						SemanticProperty sp = itSemanticProperty.next();
						SemanticObject dp = sp.getDisplayProperty();

						if (!ignoreProps.contains(sp.getPropId()) && null != dp) {
							String label = dp.getLabel(lang);
							if (null == label || label.isEmpty())
								label = sp.getDisplayName(lang);
							newprop += label + ";" + sp.getPropId() + "|";
						}
					}
					docSection.setVisibleProperties(newprop);
				}

				response.setRenderParameter(PARAM_STATUS, "ok");
				response.setRenderParameter(PARAM_URIDS, docSection.getURI());
				response.setRenderParameter(PARAM_URIDT, docTemplate.getURI());
			} else {
				response.setRenderParameter(PARAM_STATUS, "error");
			}
			response.setMode(MODE_RESPONSE);
		} else if (action.equals(ACTION_DEFINE_VERSION_TEMPLATE)) {
			docTemplate = (DocumentTemplate) SWBPlatform.getSemanticMgr().getOntology()
					.getGenericObject(uriDocTemplate);
			templateCont = (TemplateContainer) SWBPlatform.getSemanticMgr().getOntology()
					.getGenericObject(uriTemplateCont);

			if (docTemplate != null && templateCont != null) {
				templateCont.setActualTemplate(docTemplate);
			}
			response.setRenderParameter(PARAM_URITC, uriTemplateCont);
		} else if (action.equals(ACTION_REMOVE_VERSION_TEMPLATE)) {
			docTemplate = (DocumentTemplate) SWBPlatform.getSemanticMgr().getOntology()
					.getGenericObject(uriDocTemplate);
			templateCont = (TemplateContainer) SWBPlatform.getSemanticMgr().getOntology()
					.getGenericObject(uriTemplateCont);
			if (docTemplate != null && templateCont != null) {
				DocumentTemplate docTemplatePrev = docTemplate.getPreviousTemplate();// Previous template
				DocumentTemplate docTemplateNext = docTemplate.getNextTemplate();// Next template
				if (docTemplatePrev != null) {
					docTemplatePrev.setNextTemplate(docTemplateNext);
				}
				if (docTemplateNext != null) {
					docTemplateNext.setPreviousTemplate(docTemplatePrev);
				}
				if (templateCont.getLastTemplate() != null
						&& templateCont.getLastTemplate().getURI().equals(docTemplate.getURI())
						&& docTemplate.getPreviousTemplate() != null && docTemplatePrev != null) {
					templateCont.setLastTemplate(docTemplatePrev);
				} else if (templateCont.getLastTemplate() != null
						&& templateCont.getLastTemplate().getURI().equals(docTemplate.getURI())
						&& docTemplate.getPreviousTemplate() != null && docTemplateNext != null) {
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
			response.setRenderParameter(PARAM_URITC, uriTemplateCont);
		} else if (action.equals(ACTION_EDIT_DOCUMENT_SECTION)) {
			String urids = request.getParameter(PARAM_URIDS);
			DocumentSection ds = (DocumentSection) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(urids);
			String titleSection = request.getParameter("titleSection") != null ? request.getParameter("titleSection")
					: "";
			String configData = request.getParameter("configData");

			if (null != ds && !titleSection.isEmpty()) {
				// Update title
				if (!titleSection.equals(ds.getTitle()))
					ds.setTitle(titleSection);

				// Update config data
				if (null != configData && !configData.isEmpty())
					ds.setConfigData(configData);

				// Get sectiontype properties
				SemanticObject st = ds.getSectionType();
				SemanticClass semCls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(st.getURI());
				ArrayList<SemanticProperty> properties = new ArrayList<>();
				Iterator<SemanticProperty> itSemanticProperty = semCls.listProperties();
				while (itSemanticProperty.hasNext()) {
					SemanticProperty sp = itSemanticProperty.next();
					if (sp.getDisplayProperty() != null) {
						properties.add(sp);
					}
				}

				// Get properties from request and add them accordingly to configuration
				ds.setVisibleProperties("");
				String newprop = "";
				itSemanticProperty = properties.iterator();
				while (itSemanticProperty.hasNext()) {
					SemanticProperty sp = itSemanticProperty.next();
					if (request.getParameter(sp.getPropId()) != null) {
						String label = request.getParameter("label" + sp.getPropId());
						newprop += label + ";" + sp.getPropId() + "|";
					}
				}
				ds.setVisibleProperties(newprop);
				response.setRenderParameter(PARAM_URIDS, urids);
			}
		} else if (action.equals(ACTION_REMOVE_DOCUMENT_SECTION)) {
			if (!uriDocSection.isEmpty()) {
				DocumentSection ds = (DocumentSection) SWBPlatform.getSemanticMgr().getOntology()
						.getGenericObject(uriDocSection);

				if (null != ds) {
					DocumentTemplate dt = ds.getParentTemplate();
					dt.removeDocumentSection(ds);
					ds.remove();
				}
				response.setRenderParameter(PARAM_STATUS, "ok");
			}
			response.setMode(MODE_RESPONSE);
		} else if (action.equals(ACTION_DUPLICATE_TEMPLATE)) {
			String titletcd = request.getParameter("titletcd") != null ? request.getParameter("titletcd").trim() : "";
			DocumentTemplate toClone = (DocumentTemplate) SWBPlatform.getSemanticMgr().getOntology()
					.getGenericObject(request.getParameter("versiontemp"));

			if (null != toClone) {
				// Clone container
				TemplateContainer newContainer = TemplateContainer.ClassMgr.createTemplateContainer(model);
				newContainer.setTitle(titletcd);

				// clone template
				DocumentTemplate clone = toClone.cloneTemplate();

				// Add clone to new container
				newContainer.addTemplate(clone);
				newContainer.setActualTemplate(clone);
				newContainer.setLastTemplate(clone);

				response.setRenderParameter(PARAM_STATUS, "ok");
			}
			response.setMode(MODE_RESPONSE);
		} else {
			super.processAction(request, response);
		}
	}

	@Override
	public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest)
			throws SWBResourceException, IOException {
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
		case MODE_RESPONSE:
			doResponse(request, response, paramRequest);
			break;
		case MODE_ADD_TEMPLATE:
			doAddTemplate(request, response, paramRequest);
			break;
		case MODE_ADD_DOCUMENT_SECTION:
			doAddDocumentSection(request, response, paramRequest);
			break;
		default:
			super.processRequest(request, response, paramRequest);
			break;
		}
	}

	@Override
	public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest)
			throws SWBResourceException, IOException {
		String path = "/swbadmin/jsp/process/documentation/documentTemplate.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		try {
			request.setAttribute(PARAM_REQUEST, paramRequest);
			request.setAttribute(LIST_TEMPLATES_CONTAINER, listTemplateContainers(request, paramRequest));
			rd.forward(request, response);
		} catch (ServletException ex) {
			log.error("Error on doView, " + path + ", " + ex.getMessage());
		}
	}

	@Override
	public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest)
			throws SWBResourceException, IOException {
		String path = "/swbadmin/jsp/process/documentation/documentTemplateEdit.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		String contURI = request.getParameter(PARAM_URITC) != null ? request.getParameter(PARAM_URITC) : "";
		String docURI = request.getParameter(PARAM_URIDT) != null ? request.getParameter(PARAM_URIDT) : "";
		DocumentTemplate docTemplate = (DocumentTemplate) SWBPlatform.getSemanticMgr().getOntology()
				.getGenericObject(docURI);

		TemplateContainer tc = null;
		if (null != docTemplate) {
			tc = docTemplate.getTemplateContainer();
		} else {
			tc = (TemplateContainer) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(contURI);
		}

		try {
			request.setAttribute(PARAM_REQUEST, paramRequest);
			if (null != tc) {
				request.setAttribute(LIST_PROCESSES, tc.listAvailableProcesses());
			} else {
				request.setAttribute(LIST_PROCESSES,
						TemplateContainer.listAllAvailableProcesses(paramRequest.getWebPage().getWebSite(), null));
			}
			rd.forward(request, response);
		} catch (ServletException ex) {
			log.error(ex);
		}
	}

	public void doEditDocumentSection(HttpServletRequest request, HttpServletResponse response,
			SWBParamRequest paramRequest) throws SWBResourceException, IOException {
		String path = "/swbadmin/jsp/process/documentation/documentTemplateEditSection.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		try {
			request.setAttribute(PARAM_REQUEST, paramRequest);
			rd.forward(request, response);
		} catch (ServletException ex) {
			log.error("Error on doAddDS, " + path + ", " + ex.getMessage());
		}
	}

	public void doViewProperties(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest)
			throws SWBResourceException, IOException {
		String path = "/swbadmin/jsp/process/documentation/documentTemplateProperties.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		try {
			request.setAttribute(PARAM_REQUEST, paramRequest);
			rd.forward(request, response);
		} catch (ServletException ex) {
			log.error("Error on doViewProperties, " + path + ", " + ex.getMessage());
		}
	}

	public void doViewLog(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest)
			throws SWBResourceException, IOException {
		String path = "/swbadmin/jsp/process/documentation/logView.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		try {
			request.setAttribute(PARAM_REQUEST, paramRequest);
			rd.forward(request, response);
		} catch (ServletException ex) {
			log.error("Error on doViewLog, " + path + ", " + ex.getMessage());
		}
	}

	public void doViewVersion(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest)
			throws SWBResourceException, IOException {
		String path = "/swbadmin/jsp/process/documentation/viewVersion.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		try {
			request.setAttribute(PARAM_REQUEST, paramRequest);
			rd.forward(request, response);
		} catch (ServletException ex) {
			log.error("Error on doViewVersion, " + path + ", " + ex.getMessage());
		}
	}

	public void doEditVersionTemplate(HttpServletRequest request, HttpServletResponse response,
			SWBParamRequest paramRequest) throws SWBResourceException, IOException {
		String path = "/swbadmin/jsp/process/documentation/newVersionTemplate.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		try {
			request.setAttribute(PARAM_REQUEST, paramRequest);
			rd.forward(request, response);
		} catch (ServletException ex) {
			log.error("Error on doNewVersion, " + path + ", " + ex.getMessage());
		}
	}

	public void doDuplicateTemplate(HttpServletRequest request, HttpServletResponse response,
			SWBParamRequest paramRequest) throws SWBResourceException, IOException {
		String path = "/swbadmin/jsp/process/documentation/duplicateTemplate.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		try {
			request.setAttribute(PARAM_REQUEST, paramRequest);
			rd.forward(request, response);
		} catch (ServletException ex) {
			log.error("Error on doDuplicateTemplate, " + path + ", " + ex.getMessage());
		}
	}

	public void doAddTemplate(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest)
			throws SWBResourceException, IOException {
		String path = "/swbadmin/jsp/process/documentation/addTemplate.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		try {
			request.setAttribute(PARAM_REQUEST, paramRequest);
			rd.forward(request, response);
		} catch (ServletException ex) {
			log.error("Error on doAddTemplate, " + path + ", " + ex.getMessage());
		}
	}

	public void doAddDocumentSection(HttpServletRequest request, HttpServletResponse response,
			SWBParamRequest paramRequest) throws SWBResourceException, IOException {
		String path = "/swbadmin/jsp/process/documentation/documentTemplateAddDocumentSection.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		try {
			request.setAttribute(PARAM_REQUEST, paramRequest);
			rd.forward(request, response);
		} catch (ServletException ex) {
			log.error("Error on doAddTemplate, " + path + ", " + ex.getMessage());
		}
	}

	/**
	 * Obtiene la lista de {@code TemplateContainers} del sitio.
	 * 
	 * @param request
	 * @param paramRequest
	 * @return
	 */
	 public static List<TemplateContainer> listTemplateContainers(HttpServletRequest request,
			SWBParamRequest paramRequest) {
		ArrayList<TemplateContainer> unpaged = new ArrayList<>();
		WebSite model = paramRequest.getWebPage().getWebSite();
		String lang = "es";
		User user = paramRequest.getUser();
		if (user != null && user.getLanguage() != null) {
			lang = user.getLanguage();
		}
		int page = 1;
		int itemsPerPage = 10;
		Iterator<TemplateContainer> tplContainersIt = SWBComparator
				.sortByCreated(TemplateContainer.ClassMgr.listTemplateContainers(model), false);
		if (tplContainersIt != null && tplContainersIt.hasNext()) {
			Iterator<TemplateContainer> it = SWBComparator.sortByDisplayName(tplContainersIt, lang);
			while (it.hasNext()) {
				TemplateContainer dt = it.next();
				unpaged.add(dt);
			}
		}
		// Realizar paginado de instancias
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
		ArrayList<TemplateContainer> ret = new ArrayList<>();
		for (int i = sIndex; i < eIndex; i++) {
			TemplateContainer dt = unpaged.get(i);
			ret.add(dt);
		}
		return ret;
	}

	public void doResponse(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest)
			throws SWBResourceException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Map<String, String[]> params = request.getParameterMap();
		Iterator<String> keys = params.keySet().iterator();

		out.print("{");
		while (keys.hasNext()) {
			String key = keys.next();
			String val = request.getParameter(key);
			out.print("\"" + key + "\":\"" + val + "\"");
			if (keys.hasNext())
				out.print(",");
		}
		out.print("}");
	}
}
