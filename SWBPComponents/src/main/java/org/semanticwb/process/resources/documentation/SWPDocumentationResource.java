package org.semanticwb.process.resources.documentation;

import static org.semanticwb.process.utils.SWPUtils.copyFile;
import static org.semanticwb.process.utils.SWPUtils.copyFileFromSWBAdmin;
import static org.semanticwb.process.utils.SWPUtils.generateImageModel;
import static org.semanticwb.process.utils.SWPUtils.FORMAT_PNG;
import static org.semanticwb.process.utils.SWPUtils.FORMAT_SVG;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
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
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
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
import org.semanticwb.process.resources.documentation.model.Activity;
import org.semanticwb.process.resources.documentation.model.Definition;
import org.semanticwb.process.resources.documentation.model.DocumentSection;
import org.semanticwb.process.resources.documentation.model.DocumentSectionInstance;
import org.semanticwb.process.resources.documentation.model.Documentation;
import org.semanticwb.process.resources.documentation.model.DocumentationInstance;
import org.semanticwb.process.resources.documentation.model.Format;
import org.semanticwb.process.resources.documentation.model.FreeText;
import org.semanticwb.process.resources.documentation.model.Instantiable;
import org.semanticwb.process.resources.documentation.model.Referable;
import org.semanticwb.process.resources.documentation.model.Reference;
import org.semanticwb.process.resources.documentation.model.SectionElement;
import org.semanticwb.process.resources.documentation.model.SectionElementRef;
import org.semanticwb.process.resources.documentation.writers.DOCXWriter;
import org.semanticwb.process.model.RepositoryDirectory;
import org.semanticwb.process.model.RepositoryElement;
import org.semanticwb.process.model.RepositoryFile;
import org.semanticwb.process.model.RepositoryURL;
import org.w3c.dom.Document;

/**
 * Componente que permite capturar la documentación de un proceso.
 * 
 * @author carlos.alvarez
 */
public class SWPDocumentationResource extends GenericAdmResource {
	private static final Logger log = SWBUtils.getLogger(SWPDocumentationResource.class);

	public static final String ACTION_ADD_INSTANTIABLE = "a_ain";
	public static final String ACTION_EDIT_INSTANTIABLE = "a_ein";
	public static final String ACTION_ADD_RELATE = "a_arel";
	public static final String ACTION_EDIT_TEXT = "a_edt";
	public static final String ACTION_EDIT_DESCRIPTION = "a_edes";
	public static final String ACTION_SAVE_VERSION = "a_sver";
	public static final String ACTION_REMOVE_VERSION = "a_rever";
	public static final String ACTION_ACTIVE_VERSION = "a_actver";
	public static final String ACTION_RELATED_ACTIVITY = "a_ract";
	public static final String ACTION_UPDATE_FILL = "a_ufill";
	public static final String ACTION_DOWNLOAD = "a_down";
	public static final String ACTION_UPLOAD_PICTURE = "a_upp";
	public static final String MODE_EDIT_INSTANTIABLE = "m_ein";
	public static final String MODE_EDIT_DESCRIPTION = "m_edes";
	public static final String MODE_RELATED = "m_rel";
	public static final String MODE_RELATED_ACTIVITY = "m_relact";
	public static final String MODE_TRACEABLE = "m_trac";
	public static final String MODE_VIEW_REMOVE = "m_viewrem";
	public static final String MODE_VERSION = "m_nver";
	public static final String MODE_ADMIN_VERSION = "m_nadver";
	public static final String MODE_VIEW_DESCRIPTION = "m_nvdesc";
	public static final String MODE_MSG_VERSION = "m_msgv";
	public static final String MODE_DOWNLOAD = "m_down";
	public static final String MODE_RESPONSE = "m_response";
	public static final String PARAM_REQUEST = "paramRequest";// Bean paramRequest
	public static final String FORMAT_HTML = "html";
	public static final String FORMAT_WORD = "word";
	public static final String CONFIG_INCLUDEHF = "includeHeaderFooter";
	public static final String CONFIG_ACTTABLE = "activityAsTable";
	public static final String CONFIG_TPL = "template";
	public static final String CONFIG_FIRSTPAGE = "includeFirstPage";
	public static final String CONFIG_FORCETBLSTYLES = "forceTableStyles";
	private static final String PARAM_FFILE = "ffile";
	private static final String PARAM_URIDSI = "uridsi";
	private static final String PARAM_URISE = "urise";
	private static final String PARAM_HFTYPE = "hftype";
	private static final String PARAM_STATUS = "status";
	private static final String PARAM_URIDI = "uridi";
	private static final String PARAM_URIDOC = "uridoc";
	private static final String HTMLCONTENTTYPE = "text/html";
	private static final String UTF8 = "UTF-8";

	@Override
	public void processAction(HttpServletRequest request, SWBActionResponse response)
			throws SWBResourceException, IOException {
		String action = response.getAction();
		boolean isMultiPart = ServletFileUpload.isMultipartContent(request);
		HashMap<String, Object> params = new HashMap<>();
		InputStream file = null;
		WebSite model = response.getWebPage().getWebSite();
		User user = response.getUser();

		// Get request parameter map
		if (isMultiPart) {
			try {
				List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
				for (FileItem item : items) {
					if (item.isFormField()) {
						String name = item.getFieldName();
						String val = item.getString();
						params.put(name, val);
					} else {
						file = item.getInputStream();
						params.put(PARAM_FFILE, item.getName());
					}
				}
			} catch (FileUploadException fue) {
				log.error("Error processing file upload", fue);
			}
		} else {
			Map<String, String[]> pars = request.getParameterMap();
			Iterator<String> keys = pars.keySet().iterator();
			while (keys.hasNext()) {
				String key = keys.next();
				String val = request.getParameter(key);
				params.put(key, val);
			}
		}

		switch (action) {
		case ACTION_ADD_INSTANTIABLE: {
			DocumentSectionInstance dsi = (DocumentSectionInstance) SWBPlatform.getSemanticMgr().getOntology().getGenericObject((String) params.get(PARAM_URIDSI));
			SemanticClass semCls = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass((String) params.get("scls"));

			if (null != dsi && null != semCls) {
				String clsId = semCls.getClassId();
				String configData = (String) params.get("configData");
				boolean referable = clsId.equals(Definition.sclass.getClassId()) || clsId.equals(Format.sclass.getClassId()) || clsId.equals(Reference.sclass.getClassId());
				SectionElement se = null;
				RepositoryDirectory rd = null;

				// Create element
				if (referable) {
					rd = (RepositoryDirectory) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(configData);
					if (null != rd) {
						if (clsId.equals(Definition.sclass.getClassId())) { // Definition
							Definition definition = Definition.ClassMgr.createDefinition(model);
							se = (SectionElement) definition;
						} else if (clsId.equals(Format.sclass.getClassId())) {
							Format format = Format.ClassMgr.createFormat(model);
							se = (SectionElement) format;
						} else {
							Reference reference = Reference.ClassMgr.createReference(model);
							se = (SectionElement) reference;
						}
					}
				} else {
					SemanticObject semOb = model.getSemanticModel().createSemanticObjectById(model.getSemanticModel().getCounter(semCls) + "", semCls);
					se = (SectionElement) semOb.createGenericInstance();
				}

				// Set element config properties
				if (null != se) {
					SemanticObject sobj = se.getSemanticObject();
					String props = (String) params.get("props");
					if (null != props && !props.isEmpty()) {
						String[] addProps = props.split("\\|");
						for (String propt : addProps) {
							String idProperty = propt.substring(propt.indexOf(';') + 1, propt.length());
							SemanticProperty sp = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(idProperty);
							if (!idProperty.equals(Sortable.swb_index.getPropId())) {
								sobj.setProperty(sp, params.containsKey(idProperty) ? params.get(idProperty).toString() : null);
							} else {
								int index;
								try {
									index = Integer.parseInt(params.containsKey(idProperty) ? params.get(idProperty).toString() : sobj.getId());
								} catch (NumberFormatException e) {
									index = Integer.parseInt(sobj.getId());
									log.info("Error parsing " + params.get(idProperty) + " to int, " + e.getMessage());
								}
								sobj.setProperty(sp, Integer.toString(index));
							}
						}
					}

					if (!params.containsKey(Sortable.swb_index.getPropId())) {
						SemanticProperty sp = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(Sortable.swb_index.getPropId());
						sobj.setProperty(sp, sobj.getId());
					}

					// Relate element to section instance and template
					se.setDocumentSectionInst(dsi);
					se.setDocumentTemplate(dsi.getSecTypeDefinition().getParentTemplate());
					se.setParentSection(dsi.getSecTypeDefinition());
					dsi.addDocuSectionElementInstance(se);
				}

				if (referable && null != se) {// For referables we need file and repository
					// Store related file
					String fileName = (String) params.get(PARAM_FFILE);
					String url = (String) params.get("lfile");
					Referable ref = (Referable) se;

					if (params.containsKey(PARAM_HFTYPE) && params.get(PARAM_HFTYPE).equals("file") && null != fileName && !fileName.isEmpty()) {
						RepositoryFile rfile = RepositoryFile.ClassMgr.createRepositoryFile(rd.getProcessSite());
						ref.setRefRepository(rfile);
						rfile.setRepositoryDirectory(rd);
						rfile.storeFile(file, fileName, null, null, Boolean.TRUE);
						rfile.setTitle(se.getSemanticObject().getProperty(Descriptiveable.swb_title));
						rfile.setDescription(se.getSemanticObject().getProperty(Descriptiveable.swb_description));
						rfile.setOwnerUserGroup(user.getUserGroup());
					} else if (params.containsKey(PARAM_HFTYPE) && params.get(PARAM_HFTYPE).equals("url") && null != url && !url.isEmpty()) { // Es URL
						RepositoryURL repositoryUrl = RepositoryURL.ClassMgr.createRepositoryURL(rd.getProcessSite());
						repositoryUrl.setRepositoryDirectory(rd);
						ref.setRefRepository(repositoryUrl);
						repositoryUrl.setTitle(se.getSemanticObject().getProperty(Descriptiveable.swb_title));
						repositoryUrl.setDescription(se.getSemanticObject().getProperty(Descriptiveable.swb_description));
						repositoryUrl.setOwnerUserGroup(user.getUserGroup());
						repositoryUrl.storeFile(url.startsWith("http://") ? url : "http://" + url, null, Boolean.TRUE, null);
					}
				}
				response.setRenderParameter(PARAM_STATUS, "ok");
			}
			response.setMode(MODE_RESPONSE);
			break;
		}
		case ACTION_EDIT_INSTANTIABLE: {
			SemanticObject semObj = SemanticObject.createSemanticObject((String) params.get(PARAM_URISE));
			String props = (String) params.get("props");
			String configData = (String) params.get("configData");
			SectionElement se = null;

			// Set object properties
			if (null != semObj) {
				se = (SectionElement) semObj.createGenericInstance();

				String[] addProps = props.split("\\|");
				for (String propt : addProps) {
					String idProperty = propt.substring(propt.indexOf(';') + 1, propt.length());
					SemanticProperty sp = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(idProperty);
					if (idProperty.equals(DocumentSection.swpdoc_configData.getPropId())) {
						configData = params.containsKey(idProperty) ? params.get(idProperty).toString() : "";
					}
					if (!idProperty.equals(Sortable.swb_index.getPropId())) {
						semObj.setProperty(sp, params.containsKey(idProperty) ? params.get(idProperty).toString() : null);
					} else {
						int index;
						try {
							index = Integer.parseInt(params.get(idProperty).toString());
						} catch (NumberFormatException e) {
							index = Integer.parseInt(semObj.getId());
							log.info("Error parsing " + params.get(idProperty) + " to int, " + e.getMessage());
						}
						semObj.setProperty(sp, Integer.toString(index));
					}
				}

				if (se instanceof Referable) {
					String uriVersionInfo = params.containsKey("versionref") ? params.get("versionref").toString() : "";
					VersionInfo versionInfo = (VersionInfo) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uriVersionInfo);
					if (versionInfo != null) {
						((Referable) se).setVersion(versionInfo); // TODO: Revisar, esto tira siempre un NPE
					}

					RepositoryDirectory rd = (RepositoryDirectory) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(configData);
					RepositoryElement repElement = (RepositoryElement) SWBPlatform.getSemanticMgr().getOntology().getGenericObject((String) params.get("urire"));

					if (repElement != null) {
						repElement.setRepositoryDirectory(rd);
						repElement.getSemanticObject().setProperty(Descriptiveable.swb_title, semObj.getProperty(Descriptiveable.swb_title));
						repElement.getSemanticObject().setProperty(Descriptiveable.swb_description, semObj.getProperty(Descriptiveable.swb_description));
					}
				}
				response.setRenderParameter(PARAM_STATUS, "ok");
				response.setMode(MODE_RESPONSE);
			}
			break;
		}
		case SWBResourceURL.Action_REMOVE: {
			DocumentSectionInstance dsi = (DocumentSectionInstance) SWBPlatform.getSemanticMgr().getOntology().getGenericObject((String) params.get(PARAM_URIDSI));
			SectionElement se = (SectionElement) SWBPlatform.getSemanticMgr().getOntology().getGenericObject((String) params.get(PARAM_URISE));

			if (null != dsi && null != se) {
				dsi.removeDocuSectionElementInstance(se);
				se.remove();

				if (params.containsKey("optionRemove") && se instanceof Referable) {
					RepositoryElement ele = (RepositoryElement) SWBPlatform.getSemanticMgr().getOntology().getGenericObject((String) params.get("fileSe"));
					if (null != ele)
						ele.remove();
				}
				response.setRenderParameter(PARAM_STATUS, "ok");
			}
			response.setRenderParameter(PARAM_URIDSI, (String) params.get(PARAM_URIDSI));
			response.setRenderParameter(PARAM_URISE, (String) params.get(PARAM_URISE));
			response.setRenderParameter("idp", request.getParameter("idp"));
			response.setRenderParameter("wp", request.getParameter("wp"));
			response.setRenderParameter("_rid", request.getParameter("_rid"));
			response.setMode(MODE_RESPONSE);
			break;
		}
		case ACTION_EDIT_TEXT: {
			SectionElement se = (SectionElement) SWBPlatform.getSemanticMgr().getOntology().getGenericObject((String) params.get(PARAM_URISE));
			String data = (String) params.get("data");

			if (se instanceof FreeText) {
				FreeText freeText = (FreeText) se;
				if (null != data)
					freeText.setText(data);
			} else if (se instanceof Activity) {
				Activity activity = (Activity) se;
				if (null != data)
					activity.setDescription(data);
			}
			response.setRenderParameter(PARAM_STATUS, "ok");
			response.setMode(MODE_RESPONSE);
			break;
		}
		case ACTION_RELATED_ACTIVITY: {
			Activity act = (Activity) SWBPlatform.getSemanticMgr().getOntology().getGenericObject((String) params.get(PARAM_URISE));
			DocumentSectionInstance dsi = (DocumentSectionInstance) SWBPlatform.getSemanticMgr().getOntology().getGenericObject((String) params.get(PARAM_URIDSI));

			if (act != null && dsi != null && dsi.getDocumentationInstance() != null) {
				DocumentationInstance docInstance = dsi.getDocumentationInstance();
				Iterator<DocumentSectionInstance> itdsi = SWBComparator.sortSortableObject(docInstance.listDocumentSectionInstances());
				Map<SectionElement, SectionElementRef> map = new HashMap<>();
				Iterator<SectionElementRef> itSectionElementRef = act.listSectionElementRefs();
				while (itSectionElementRef.hasNext()) {
					SectionElementRef ser = itSectionElementRef.next();
					map.put(ser.getSectionElement(), ser);
				}
				while (itdsi.hasNext()) {
					DocumentSectionInstance docSectInstance = itdsi.next();
					SemanticClass cls = docSectInstance.getSecTypeDefinition() != null && docSectInstance.getSecTypeDefinition().getSectionType() != null
									? docSectInstance.getSecTypeDefinition().getSectionType().transformToSemanticClass()
									: null;
					if (cls != null && cls.isSubClass(Instantiable.swpdoc_Instantiable, false) && docSectInstance.listDocuSectionElementInstances().hasNext()) {
						Iterator<SectionElement> itse = docSectInstance.listDocuSectionElementInstances();
						while (itse.hasNext()) {
							SectionElement sectElement = itse.next();
							if (params.containsKey(sectElement.getURI()) && !map.containsKey(sectElement)) { // Add new SER
								SectionElementRef sectElementRef = SectionElementRef.ClassMgr.createSectionElementRef(model);
								sectElementRef.setSectionElement(sectElement);
								sectElementRef.setActivity(act);
								act.addSectionElementRef(sectElementRef);
							} else if (map.containsKey(sectElement) && !params.containsKey(sectElement.getURI())) { // Remove SER
								SectionElementRef sectElementRef = (SectionElementRef) map.get(sectElement);
								if (sectElementRef != null) {
									sectElementRef.remove();
									act.removeSectionElementRef(sectElementRef);
								}
							}
						}
					}
				}
				response.setRenderParameter(PARAM_STATUS, "ok");
				response.setMode(MODE_RESPONSE);
			}
			break;
		}
		case ACTION_UPDATE_FILL: {
			String fill = request.getParameter("fill") != null ? request.getParameter("fill") : "";
			Activity activity = (Activity) SWBPlatform.getSemanticMgr().getOntology().getGenericObject((String) params.get(PARAM_URISE));
			if (activity != null) {
				if (fill.equals("defaultFill")) {
					activity.setFill(null);
				} else {
					activity.setFill(fill);
				}
				response.setRenderParameter(PARAM_STATUS, "ok");
				response.setMode(MODE_RESPONSE);
			}
			break;
		}
		case ACTION_UPLOAD_PICTURE: {
			if (file != null) {
				String basePath = SWBPortal.getWorkPath() + "/models/" + model.getId() + "/swp_DocumentationImage";
				File dir = new File(basePath);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				try (OutputStream out = new FileOutputStream(new File(basePath + "/" + (String) params.get(PARAM_FFILE)))) {
					IOUtils.copy(file, out);
					file.close();
					out.flush();
				}
			}
			response.setRenderParameter(PARAM_URISE, (String) params.get(PARAM_URISE));
			break;
		}
		case ACTION_SAVE_VERSION:
			DocumentationInstance docInstance = (DocumentationInstance) SWBPlatform.getSemanticMgr().getOntology()
					.getGenericObject((String) params.get(PARAM_URIDI));
			if (null != docInstance) {
				String vComment = (String) params.get("description");
				Documentation actual = null;
				Documentation lastVersion = null;
				String lastVValue = null;
				ArrayList<Documentation> versions = new ArrayList<>();

				Iterator<Documentation> itdoc = SWBComparator.sortByCreated(Documentation.ClassMgr.listDocumentationByProcess(docInstance.getProcessRef()), false);
				while (itdoc.hasNext()) {
					Documentation doc = itdoc.next();
					versions.add(doc);

					if (doc.isActualVersion()) {
						actual = doc;
					}
				}

				if (!versions.isEmpty())
					lastVersion = versions.get(0);
				// Crear objeto de documentación
				Documentation doc = Documentation.ClassMgr.createDocumentation(model);
				doc.setProcess(docInstance.getProcessRef());
				doc.setVersionComment(vComment);

				// Tratar de generar la salida
				// Check for previous documentation
				String path = doc.getDocWorkPath(); // TODO: Eliminar workpath y archivos relacionados si falla
													// procesamiento del XSL, procesar en memoria y después escribir
				File base = new File(path);
				if (!base.exists()) {
					base.mkdirs();
				}
				File index = new File(base + "/index.html");
				FileOutputStream out = new FileOutputStream(index);
				try {
					Document dom = docInstance.getXMLDocument(request, null, false);
					if (dom != null) {
						String tlpPath = "/swbadmin/jsp/process/documentation/documentation.xsl";
						javax.xml.transform.Templates tpl = SWBUtils.XML.loadTemplateXSLT(new FileInputStream(SWBUtils.getApplicationPath() + tlpPath));
						out.write(SWBUtils.XML.transformDom(tpl, dom).getBytes(UTF8));

						Transformer transformer = TransformerFactory.newInstance().newTransformer();
						Result output = new StreamResult(new File(path + "/documentation.xml"));
						Source input = new DOMSource(dom);
						transformer.transform(input, output);
					}
					out.flush();
					out.close();
					response.setRenderParameter(PARAM_URIDI, docInstance.getURI());
					response.setRenderParameter(PARAM_URIDOC, doc.getURI());
					response.setRenderParameter("idp", doc.getProcess().getId());
					response.setRenderParameter("_rid", (String) params.get("_rid"));
					response.setRenderParameter("wp", (String) params.get("wp"));
					response.setRenderParameter(PARAM_STATUS, "ok");
				} catch (IOException | TransformerException e) {
					log.error("Error on write file " + index.getAbsolutePath(), e);
					doc.remove();
					doc = null;
				}

				// Si todo salió bien, ajustar versiones
				if (null != doc) {
					doc.setActualVersion(true);
					if (null != lastVersion)
						lastVValue = lastVersion.getVersionValue();
					doc.setVersionValue(Documentation.getNextVersionValue(lastVValue));

					if (null != actual) {
						actual.setActualVersion(false);
						if (null == actual.getVersionValue()) {
							fixVersionNumbers(versions); // TO REMOVE CALL IN FUTURE VERSIONS
						}
					}
				}
			}
			response.setMode(MODE_RESPONSE);
			break;
		case ACTION_REMOVE_VERSION: {
			Documentation doc = (Documentation) SWBPlatform.getSemanticMgr().getOntology().getGenericObject((String) params.get(PARAM_URIDOC));
			String idp = "";
			if (null != doc && !doc.isActualVersion()) {
				idp = doc.getProcess().getId();
				String path = doc.getDocWorkPath();
				SWBUtils.IO.removeDirectory(path);
				doc.remove();
			}
			response.setRenderParameter("idp", idp);
			response.setRenderParameter("_rid", (String) params.get("_rid"));
			response.setRenderParameter("wp", (String) params.get("wp"));
			break;
		}
		case ACTION_ACTIVE_VERSION: {
			Documentation doc = (Documentation) SWBPlatform.getSemanticMgr().getOntology().getGenericObject((String) params.get(PARAM_URIDOC));
			String idp = "";

			if (null != doc) {
				idp = doc.getProcess().getId();
				Documentation actual = Documentation.getActualDocumentationVersion(doc.getProcess());
				actual.setActualVersion(false);
				doc.setActualVersion(true);
			}
			response.setRenderParameter("idp", idp);
			response.setRenderParameter("_rid", (String) params.get("_rid"));
			response.setRenderParameter("wp", (String) params.get("wp"));
			break;
		}
		default:
			super.processAction(request, response);
			break;
		}
	}

	@Override
	public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
		String mode = paramRequest.getMode();
		switch (mode) {
		case MODE_EDIT_INSTANTIABLE:
			doEdit(request, response, paramRequest);
			break;
		case MODE_RELATED:
			doRelated(request, response, paramRequest);
			break;
		case MODE_TRACEABLE:
			doTrace(request, response, paramRequest);
			break;
		case MODE_VIEW_REMOVE:
			doViewRemove(request, response, paramRequest);
			break;
		case MODE_VERSION:
			doVersion(request, response, paramRequest);
			break;
		case MODE_ADMIN_VERSION:
			doAdminVersion(request, response, paramRequest);
			break;
		case MODE_VIEW_DESCRIPTION:
			doViewDesc(request, response, paramRequest);
			break;
		case MODE_RELATED_ACTIVITY:
			doRelateActivity(request, response, paramRequest);
			break;
		case MODE_EDIT_DESCRIPTION:
			doEditDescription(request, response, paramRequest);
			break;
		case MODE_DOWNLOAD:
			doDownload(request, response, paramRequest);
			break;
		case MODE_RESPONSE:
			doResponse(request, response, paramRequest);
			break;
		default:
			super.processRequest(request, response, paramRequest);
			break;
		}
	}

	@Override
	public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
		response.setContentType(HTMLCONTENTTYPE);
		response.setCharacterEncoding(UTF8);
		String path = "/swbadmin/jsp/process/documentation/documentation.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(path);

		try {
			request.setAttribute(PARAM_REQUEST, paramRequest);
			rd.forward(request, response);
		} catch (ServletException ex) {
			log.error("Error on doView, ", ex);
		}
	}

	public void doResponse(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding(UTF8);
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

	@Override
	public void doEdit(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
		response.setContentType(HTMLCONTENTTYPE);
		response.setCharacterEncoding(UTF8);
		String path = "/swbadmin/jsp/process/documentation/documentationEdit.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		try {
			request.setAttribute(PARAM_REQUEST, paramRequest);
			rd.forward(request, response);
		} catch (ServletException ex) {
			log.error("Error on doEdit, " + path, ex);
		}
	}

	public void doActualizaTab(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
		response.setContentType(HTMLCONTENTTYPE);
		response.setCharacterEncoding(UTF8);
		String path = "/swbadmin/jsp/process/documentation/actualizaTab.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		try {
			request.setAttribute(PARAM_REQUEST, paramRequest);
			rd.forward(request, response);
		} catch (ServletException ex) {
			log.error("Error on doActualizaTab, " + path, ex);
		}
	}

	public void doRelated(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
		response.setContentType(HTMLCONTENTTYPE);
		response.setCharacterEncoding(UTF8);
		String path = "/swbadmin/jsp/process/documentation/relatedItem.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		try {
			request.setAttribute(PARAM_REQUEST, paramRequest);
			rd.forward(request, response);
		} catch (ServletException ex) {
			log.error("Error on doRelated, " + path, ex);
		}
	}

	public void doTrace(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
		response.setContentType(HTMLCONTENTTYPE);
		response.setCharacterEncoding(UTF8);
		String path = "/swbadmin/jsp/process/documentation/logView.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		try {
			request.setAttribute(PARAM_REQUEST, paramRequest);
			rd.forward(request, response);
		} catch (ServletException ex) {
			log.error("Error on doTrace, " + path, ex);
		}
	}

	public void doViewRemove(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
		response.setContentType(HTMLCONTENTTYPE);
		response.setCharacterEncoding(UTF8);
		String path = "/swbadmin/jsp/process/documentation/viewRemove.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		try {
			request.setAttribute(PARAM_REQUEST, paramRequest);
			rd.forward(request, response);
		} catch (ServletException ex) {
			log.error("Error on doRemove, " + path, ex);
		}
	}

	public void doVersion(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
		response.setContentType(HTMLCONTENTTYPE);
		response.setCharacterEncoding(UTF8);
		String path = "/swbadmin/jsp/process/documentation/saveVersion.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		try {
			request.setAttribute(PARAM_REQUEST, paramRequest);
			rd.forward(request, response);
		} catch (ServletException ex) {
			log.error("Error on doVersion, " + path, ex);
		}
	}

	public void doAdminVersion(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
		response.setContentType(HTMLCONTENTTYPE);
		response.setCharacterEncoding(UTF8);
		String path = "/swbadmin/jsp/process/documentation/documentationVersion.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		try {
			request.setAttribute(PARAM_REQUEST, paramRequest);
			rd.forward(request, response);
		} catch (ServletException ex) {
			log.error("Error on doAdminVersion, " + path, ex);
		}
	}

	public void doViewDesc(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
		response.setContentType(HTMLCONTENTTYPE);
		response.setCharacterEncoding(UTF8);
		String path = "/swbadmin/jsp/process/documentation/viewDescVersion.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		try {
			request.setAttribute(PARAM_REQUEST, paramRequest);
			rd.forward(request, response);
		} catch (ServletException ex) {
			log.error("Error on doviewDesc, " + path, ex);
		}
	}

	public void doRelateActivity(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
		response.setContentType(HTMLCONTENTTYPE);
		response.setCharacterEncoding(UTF8);
		String path = "/swbadmin/jsp/process/documentation/relateActivity.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		try {
			request.setAttribute(PARAM_REQUEST, paramRequest);
			rd.forward(request, response);
		} catch (ServletException ex) {
			log.error("Error on doRelateActivity, " + path, ex);
		}
	}

	public void doEditDescription(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
		response.setContentType(HTMLCONTENTTYPE);
		response.setCharacterEncoding(UTF8);
		String path = "/swbadmin/jsp/process/documentation/editDescription.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(path);
		try {
			request.setAttribute(PARAM_REQUEST, paramRequest);
			rd.forward(request, response);
		} catch (ServletException ex) {
			log.error("Error on doEditDescription, " + path, ex);
		}
	}

	public void doDownload(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
		String urip = request.getParameter("urip") != null ? request.getParameter("urip") : "";
		String uridi = request.getParameter(PARAM_URIDI) != null ? request.getParameter(PARAM_URIDI) : "";
		String data = request.getParameter("data");
		String format = request.getParameter("format") != null ? request.getParameter("format") : "";

		double w = 3800;
		double h = 2020;
		try {
			w = Double.parseDouble(request.getParameter("width"));
			h = Double.parseDouble(request.getParameter("height"));
		} catch (NumberFormatException nfe) {
		}

		org.semanticwb.process.model.Process p = (org.semanticwb.process.model.Process) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(urip);
		DocumentationInstance docInstance = (DocumentationInstance) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(uridi);
		String basePath = docInstance.getDownloadTempPath();

		if (docInstance != null) {
			try {
				File dirBase = new File(basePath);
				if (!dirBase.exists()) {
					dirBase.mkdirs();
				}
				generateImageModel(p, basePath + "/rep_files", FORMAT_PNG, data, w, h);
				generateImageModel(p, basePath + "/rep_files", FORMAT_SVG, data, w, h);

				File dest = new File(basePath);
				if (!dest.exists()) {
					dest.mkdirs();
				}
				if (format.equals(FORMAT_HTML)) {
					String fontsPath = basePath + "css/fonts/";
					response.setContentType("application/zip");
					response.setHeader("Content-Disposition", "attachment; filename=\"" + p.getId() + ".zip\"");

					// Copy bootstrap files
					copyFileFromSWBAdmin("/swbadmin/css/bootstrap/bootstrap.css", basePath + "css/bootstrap/", "/bootstrap.css");
					copyFileFromSWBAdmin("/swbadmin/js/bootstrap/bootstrap.js", basePath + "js/bootstrap/", "/bootstrap.js");
					// Copy font-awesome files
					copyFileFromSWBAdmin("/swbadmin/css/fontawesome/font-awesome.css", basePath + "css/fontawesome/", "/font-awesome.css");
					copyFileFromSWBAdmin("/swbadmin/css/fonts/FontAwesome.otf", fontsPath, "/FontAwesome.otf");
					copyFileFromSWBAdmin("/swbadmin/css/fonts/fontawesome-webfont.eot", fontsPath, "/fontawesome-webfont.eot");
					copyFileFromSWBAdmin("/swbadmin/css/fonts/fontawesome-webfont.svg", fontsPath, "/fontawesome-webfont.svg");
					copyFileFromSWBAdmin("/swbadmin/css/fonts/fontawesome-webfont.ttf", fontsPath, "/fontawesome-webfont.ttf");
					copyFileFromSWBAdmin("/swbadmin/css/fonts/fontawesome-webfont.woff", fontsPath, "/fontawesome-webfont.woff");
					copyFileFromSWBAdmin("/swbadmin/js/jquery/jquery.js", basePath + "js/jquery/", "/jquery.js");
					// Add modeler
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
					copyFile(SWBUtils.getApplicationPath() + "/swbadmin/jsp/process/commons/css/swbp.css", basePath + "/css/swbp.css");
					// Add images
					File images = new File(basePath + "css/images/");
					if (!images.exists()) {
						images.mkdirs();
					}
					SWBUtils.IO.copyStructure(SWBUtils.getApplicationPath() + "/swbadmin/jsp/process/commons/css/images/", basePath + "/css/images/");
					File index = new File(basePath + "/index.html");
					Document dom = docInstance.getXMLDocument(request, basePath, true);
					FileOutputStream out = null;

					if (dom != null) {
						try {
							out = new FileOutputStream(index);
							String tlpPath = SWBUtils.getApplicationPath() + "/swbadmin/jsp/process/documentation/documentation.xsl";
							javax.xml.transform.Templates tpl = SWBUtils.XML.loadTemplateXSLT(new FileInputStream(tlpPath));
							out.write(SWBUtils.XML.transformDom(tpl, dom).getBytes());

							Transformer transformer = TransformerFactory.newInstance().newTransformer();
							Result output = new StreamResult(new File(basePath + "/documentation.xml"));
							Source input = new DOMSource(dom);
							transformer.transform(input, output);

							// ZIP content and remove temp dir
							try (OutputStream ou = response.getOutputStream(); ZipOutputStream zos = new ZipOutputStream(ou)) {
								SWBUtils.IO.zip(dest, new File(basePath), zos);
								zos.flush();
							}
							SWBUtils.IO.removeDirectory(basePath);
							out.flush();
						} finally {
							if (null != out)
								out.close();
						}
					}
				} else if (format.equals(FORMAT_WORD)) {
					response.setContentType("application/msword");
					response.setHeader("Content-Disposition", "attachment; filename=\"" + p.getId() + ".docx\"");

					HashMap<String, String> params = new HashMap<>();
					if (null != getResourceBase().getAttribute(CONFIG_INCLUDEHF))
						params.put(CONFIG_INCLUDEHF, "true");
					if (null != getResourceBase().getAttribute(CONFIG_FIRSTPAGE))
						params.put(CONFIG_FIRSTPAGE, "true");
					if (null != getResourceBase().getAttribute(CONFIG_ACTTABLE))
						params.put(CONFIG_ACTTABLE, "true");
					if (null != getResourceBase().getAttribute(CONFIG_TPL))
						params.put(CONFIG_TPL, SWBPortal.getWorkPath() + getResourceBase().getWorkPath() + "/"
								+ getResourceBase().getAttribute("template"));
					if (null != getResourceBase().getAttribute(CONFIG_FORCETBLSTYLES))
						params.put(CONFIG_FORCETBLSTYLES, "true");

					DOCXWriter docxw = new DOCXWriter(docInstance, basePath + "rep_files", params);
					docxw.write(response.getOutputStream());
					response.flushBuffer();
				}
			} catch (IOException | TransformerException ex) {
				log.error("Error on doDownload", ex);
			}
		}
	}

	static List<RepositoryDirectory> list = new ArrayList<>();

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

	/**
	 * Actualiza las versiones de los objetos de documentación de un contenedor.
	 * Este método será removido en versiones futuras.
	 * 
	 * @param docs
	 *            Lista de documentación de un contenedor.
	 */
	private void fixVersionNumbers(ArrayList<Documentation> docs) {// TO DEPRECATE IN FUTURE VERSIONS
		String vvalue = "";
		for (Documentation doc : docs) {
			doc.setVersionValue(Documentation.getNextVersionValue(vvalue));
			vvalue = doc.getVersionValue();
		}
	}
}