package org.semanticwb.process.resources;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBPortal;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.model.Resource;
import org.semanticwb.model.ResourceType;
import org.semanticwb.model.Resourceable;
import org.semanticwb.model.SWBClass;
import org.semanticwb.model.User;
import org.semanticwb.model.WebPage;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticOntology;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.platform.SemanticVocabulary;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.SWBForms;
import org.semanticwb.portal.api.GenericResource;
import org.semanticwb.portal.api.SWBActionResponse;
import org.semanticwb.portal.api.SWBParamRequest;
import org.semanticwb.portal.api.SWBParameters;
import org.semanticwb.portal.api.SWBResourceException;
import org.semanticwb.portal.api.SWBResourceURL;
import org.semanticwb.portal.util.Base64;
import org.semanticwb.process.model.DataTypes;
import org.semanticwb.process.model.FlowNodeInstance;
import org.semanticwb.process.model.Instance;
import org.semanticwb.process.model.ItemAware;
import org.semanticwb.process.model.ItemAwareReference;
import org.semanticwb.process.model.SWBProcessFormMgr;
import org.semanticwb.process.model.SWBProcessMgr;
import org.semanticwb.process.model.UserTask;
import org.semanticwb.process.model.X509Certificate;
import org.semanticwb.process.model.X509SingInstance;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.NodeIterator;
import com.hp.hpl.jena.rdf.model.RDFNode;

/**
 * @author javier.solis
 * @modified by Juan Fernández
 * @modified by Hasdai Pacheco {ebenezer.sanchez@infotec.mx}
 */
public class ProcessForm extends GenericResource {
	public static final String ATT_PARAMREQUEST = "paramRequest";
	public static final String MODE_EDITPROP = "editProp";
	public static final String MODE_SIGN = "sign";
	public static final String MODE_ACUSE = "acuse";
	public static final String MODE_ADDPROPS = "addProps";
	public static final String MODE_RESPONSE = "response";
	public static final String PARAM_PROPIDX = "prop";
	public static final String PARAM_PROPMODE = "propMode";
	public static final String PARAM_PROPFE = "propFe";
	public static final String PARAM_PROPLBL = "propLabel";
	public static final String PARAM_DIR = "dir";
	public static final String PARAM_ADMMODE = "adminMode";
	public static final String PARAM_BTNID = "buttonId";
	public static final String PARAM_SHOWHEADER = "showHeader";
	public static final String PARAM_ACCEPT = "accept";
	public static final String PARAM_REJECT = "reject";
	public static final String PARAM_ROLES = "roles";
	public static final String ADM_MODEADVANCED = "advance";
	public static final String ADM_MODESIMPLE = "simple";
	public static final String ATT_TASK = "task";
	public static final String ATT_RBASE = "rbase";
	public static final String ATT_PROPMAP = "propMap";
	public static final String ATT_CLASSMAP = "classMap";
	public static final String PARAM_SURI = "suri";
	public static final String ATT_USERVARS = "userDefinedVars";
	public static final String ACT_TOGGLEBUTTON = "toggleBut";
	public static final String ACT_ADDPROPS = "addProps";
	public static final String ACT_REMOVEPROP = "removeProp";
	public static final String ACT_UPDPROP = "updateProp";
	public static final String ACT_UPDBTNLABEL = "updateButtonLabel";
	public static final String ACT_UPDATEXML = "updateXml";
	public static final String ACT_SAVEXML = "saveXml";
	public static final String ACT_PROCESSSIGN = "processSign";
	public static final String ACT_SWAP = "swap";
	public static final String ACT_UPDADMINMODE = "updateMode";
	public static final String ACT_PROCESS = "process";
	public static final String FE_DEFAULT = "generico";
	public static final String FE_MODE_VIEW = "view";
	public static final String FE_MODE_EDIT = "edit";
	public static final Logger LOG = SWBUtils.getLogger(ProcessForm.class);

	@Override
	public void processAction(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
		String action = response.getAction();
		Resource base = getResourceBase();
		String suri = request.getParameter("suri");
		User user = response.getUser();
		response.setRenderParameter("suri", suri);
		String lang = "es";
		if (user != null && user.getLanguage() != null) {
			lang = user.getLanguage();
		}

		if (ACT_UPDBTNLABEL.equals(action)) {
			String btn = request.getParameter(ProcessForm.PARAM_BTNID);
			String label = request.getParameter("btnLabel");

			if (label != null && !label.isEmpty()) {
				base.setAttribute(btn + "Label", label);

				try {
					base.updateAttributesToDB();
				} catch (Exception e) {
					LOG.error("FormsBuilderResource - Error al actualizar las etiquetas de botones.", e);
				}
				response.setRenderParameter("status", "ok");
			}
			response.setMode(MODE_RESPONSE);
		} else if (ACT_TOGGLEBUTTON.equals(action)) {
			String toggle = request.getParameter("btns");
			if (base.getAttribute("btnAcceptLabel", "").equals("")) {
				base.setAttribute("btnAcceptLabel", "Concluir Tarea");
			}

			if (base.getAttribute("btnRejectLabel", "").equals("")) {
				base.setAttribute("btnRejectLabel", "Rechazar Tarea");
			}

			if (base.getAttribute("btnCancelLabel", "").equals("")) {
				base.setAttribute("btnCancelLabel", "Regresar");
			}

			if (base.getAttribute("btnSaveLabel", "").equals("")) {
				base.setAttribute("btnSaveLabel", "Guardar");
			}

			if (PARAM_SHOWHEADER.equals(toggle)) {
				if (base.getAttribute(PARAM_SHOWHEADER, "").equals("use")) {
					base.removeAttribute(PARAM_SHOWHEADER);
				} else {
					base.setAttribute(PARAM_SHOWHEADER, "use");
				}
			}
			if (PARAM_ACCEPT.equals(toggle)) {
				if (base.getAttribute("btnAccept", "").equals("use")) {
					base.removeAttribute("btnAccept");
				} else {
					base.setAttribute("btnAccept", "use");
				}
			}
			if (PARAM_REJECT.equals(toggle)) {
				if (base.getAttribute("btnReject", "").equals("use")) {
					base.removeAttribute("btnReject");
				} else {
					base.setAttribute("btnReject", "use");
				}
			}
			if ("cancel".equals(toggle)) {
				if (base.getAttribute("btnCancel", "").equals("use")) {
					base.removeAttribute("btnCancel");
				} else {
					base.setAttribute("btnCancel", "use");
				}
			}
			if ("save".equals(toggle)) {
				if (base.getAttribute("btnSave", "").equals("use")) {
					base.removeAttribute("btnSave");
				} else {
					base.setAttribute("btnSave", "use");
				}
			}
			if ("sign".equals(toggle)) {
				if (base.getAttribute("useSign", "").equals("use")) {
					base.removeAttribute("useSign");
				} else {
					base.setAttribute("useSign", "use");
				}
			}

			try {
				base.updateAttributesToDB();
			} catch (Exception e) {
				LOG.error("FormsBuilderResource - Error al actualizar la configuración de botones.", e);
			}
		} else if (ACT_ADDPROPS.equals(action)) {
			UserTask task = null;
			if (getResourceBase().getResourceable() instanceof UserTask) {
				task = (UserTask) getResourceBase().getResourceable();
			}
			HashMap<String, ItemAware> userDefinedVars = new HashMap<>();
			HashMap<String, SemanticProperty> allprops = new HashMap<>();
			if (task != null) {
				Iterator<ItemAware> it = task.listHerarquicalRelatedItemAwarePlusNullOutputs().iterator();
				while (it.hasNext()) {
					ItemAware item = it.next();
					SemanticClass cls = item.getItemSemanticClass();
					if (cls != null) {
						Iterator<SemanticProperty> itp = cls.listProperties();
						while (itp.hasNext()) {
							SemanticProperty prop = itp.next();
							String name = item.getName() + "|" + prop.getPropId();
							if (cls.isSubClass(DataTypes.sclass) && !userDefinedVars.containsKey(name)) {
								userDefinedVars.put(name, item);
							}
							if (!prop.getPropId().equals("swb:valid") && !allprops.containsKey(name)) {
								allprops.put(name, prop);
							}
						}
					}
				}

				// Obtener la lista de propiedades a agregar
				String[] props = request.getParameterValues("properties");
				HashMap<String, String> hmparam = new HashMap<>();
				if (props != null && props.length > 0) {
					for (String prop : props) {
						hmparam.put(prop, prop);
					}
				}

				// Recuperar las propiedades ya configuradas
				HashMap<Integer, String> hmprops = new HashMap<>();
				int i = 1;
				int j = 1;
				while (!base.getAttribute("prop" + i, "").equals("")) {
					String val = base.getAttribute("prop" + i);
					HashMap<String, String> propMap = getPropertiesMap(val);
					String propKey = propMap.get("varName") + "|" + propMap.get("propId");

					if (allprops.containsKey(propKey)) {
						hmprops.put(j++, val);
						if (hmparam.containsKey(propKey)) {
							hmparam.remove(propKey);
						}
					}

					base.removeAttribute("prop" + i);
					i++;
				}

				// Agregar propiedades faltantes
				String defaultFE = FE_DEFAULT;
				String defaultMode = FE_MODE_EDIT;
				String defaultLabel = "";
				String defaultRoles = "";

				// agregando al hmprops las propiedades nuevas
				Iterator<String> itpar = hmparam.keySet().iterator();
				while (itpar.hasNext()) {
					String strnew = itpar.next();
					HashMap<String, String> propMap = getPropertiesMap(strnew);
					SemanticProperty sempro = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticPropertyById(propMap.get("propId"));
					if (sempro != null) {
						String name = propMap.get("varName") + "|" + propMap.get("propId");
						if (userDefinedVars.containsKey(name)) {
							defaultLabel = userDefinedVars.get(name).getDisplayTitle(lang);
						} else {
							defaultLabel = sempro.getDisplayName(response.getUser().getLanguage());
						}
						SemanticObject dp = sempro.getDisplayProperty();
						if (dp != null) {
							DisplayProperty disprop = new DisplayProperty(dp);
							SemanticObject semobjFE = disprop.getFormElement();
							if (semobjFE != null) {
								defaultFE = semobjFE.getURI();
							} else {
								defaultFE = FE_DEFAULT;
							}
						}
					} else {
						defaultFE = FE_DEFAULT;
					}

					String value = strnew + "|" + defaultMode + "|" + defaultFE + "|" + defaultLabel + "|" + defaultRoles;
					hmprops.put(j, value);
					j++;
				}

				// guardando las propiedades
				Iterator<Integer> itprop = hmprops.keySet().iterator();
				while (itprop.hasNext()) {
					Integer integer = itprop.next();
					String thisprop = hmprops.get(integer);
					base.setAttribute("prop" + integer, thisprop);
				}

				try {
					base.updateAttributesToDB();
					response.setRenderParameter("status", "ok");
				} catch (Exception e) {
					LOG.error("Error al guardar las propiedades de acuerdo al display property.", e);
				}
			}
			response.setMode(MODE_RESPONSE);
		} else if (ACT_REMOVEPROP.equals(action)) {
			String prop = request.getParameter(PARAM_PROPIDX);
			ArrayList<String> hmprops = new ArrayList<>();
			int i = 1;
			while (!base.getAttribute("prop" + i, "").equals("")) {
				if (!("" + i).equals(prop)) {
					hmprops.add(base.getAttribute("prop" + i));
				}
				base.removeAttribute("prop" + i);
				i++;
			}

			i = 1;
			Iterator<String> itprop = hmprops.iterator();
			while (itprop.hasNext()) {
				String thisprop = itprop.next();
				base.setAttribute("prop" + i, thisprop);
				i++;
			}

			try {
				base.updateAttributesToDB();
			} catch (Exception ex) {
				LOG.error("Error al actualizar la propiedad", ex);
			}
		} else if (ACT_UPDPROP.equals(action)) {
			String propId = request.getParameter(PARAM_PROPIDX);
			if (!base.getAttribute("prop" + propId, "").equals("")) {
				String str = base.getAttribute("prop" + propId, "");
				HashMap<String, String> propMap = getPropertiesMap(str);

				SemanticProperty sprop = SWBPlatform.getSemanticMgr().getVocabulary()
						.getSemanticPropertyById(propMap.get("propId"));
				String label = request.getParameter(PARAM_PROPLBL);
				String mode = request.getParameter(PARAM_PROPMODE);
				String formElement = request.getParameter(PARAM_PROPFE);
				String[] roles = request.getParameterValues(PARAM_ROLES);

				if (sprop != null) {
					if (label != null) {
						propMap.put("label", label);
					}
					if (mode != null && propMap.get("mode") != null) {
						propMap.put("mode", mode);
					}
					if (formElement != null && propMap.get("fe") != null) {
						propMap.put("fe", formElement);
					}
					if (roles != null && roles.length > 0) {
						String roleList = "";
						for (int i = 0; i < roles.length; i++) {
							String rName = roles[i];
							if (rName.trim().length() > 0) {
								roleList += rName;
							}
							if (i < roles.length) {
								roleList += ":";
							}
						}
						propMap.put(PARAM_ROLES, roleList);
					}

					base.setAttribute("prop" + propId,
							propMap.get("varName") + "|" + propMap.get("propId") + "|" + propMap.get("mode") + "|"
									+ propMap.get("fe") + "|" + propMap.get("label") + "|" + propMap.get(PARAM_ROLES));
					try {
						base.updateAttributesToDB();
						response.setRenderParameter("status", "ok");
					} catch (Exception ex) {
						LOG.error("Error al actualizar la propiedad", ex);
					}
					request.getSession(true).setAttribute("reload", true);
				}
			}
			response.setMode(MODE_RESPONSE);
		} else if (ACT_SWAP.equals(action)) {
			String dir = request.getParameter(PARAM_DIR);
			String propid = request.getParameter(PARAM_PROPIDX);

			if (propid != null && propid.trim().length() > 0 && dir != null) {
				int pos = 0;
				int newPos = 0;
				boolean valid = false;

				String temp = base.getAttribute("prop" + propid);
				if (temp != null) {
					try {
						pos = Integer.parseInt(propid);
					} catch (Exception e) {
					}

					if (dir.equals("down")) {
						newPos = pos + 1;
						if (pos > 0 && newPos > 0 && pos < newPos) {
							valid = true;
						}
					} else if (dir.equals("up")) {
						newPos = pos - 1;
						if (pos > 0 && newPos > 0 && pos > newPos) {
							valid = true;
						}
					}

					String tmp2 = base.getAttribute("prop" + newPos);
					if (tmp2 != null && valid) {
						base.setAttribute("prop" + pos, tmp2);
						base.setAttribute("prop" + newPos, temp);
					}

					try {
						base.updateAttributesToDB();
					} catch (Exception e) {
						LOG.error("Error al reordenar propiedad....", e);
					}
				}
			}
		} else if (ACT_UPDADMINMODE.equals(action)) {
			base.setAttribute(PARAM_ADMMODE, request.getParameter(PARAM_ADMMODE));
			try {
				base.updateAttributesToDB();
			} catch (Exception e) {
				LOG.error("Error al actualizar el modo de administración del recurso.", e);
			}
		} else if (ACT_UPDATEXML.equals(action)) {
			String basepath = SWBPortal.getWorkPath() + base.getWorkPath() + "/";
			File xmlFile = new File(basepath);
			if (!xmlFile.exists()) {
				xmlFile.mkdirs();
			}

			if (xmlFile.exists()) {
				try {
					String value = getFormHTML(request, response);
					xmlFile = new File(basepath + "code.xml");
					PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(xmlFile)));
					out.print(value);
					out.flush();
				} catch (Exception e) {
					LOG.error("Error saving file: " + xmlFile.getAbsolutePath(), e);
				}
			}
		} else if (ACT_SAVEXML.equals(action)) {
			String basepath = SWBPortal.getWorkPath() + base.getWorkPath() + "/";
			File xmlFile = new File(basepath);
			if (xmlFile.exists()) {
				try {
					String value;
					if (request.getParameter("hiddencode") != null
							&& request.getParameter("hiddencode").trim().length() > 0) {
						value = request.getParameter("hiddencode");
					} else {
						value = request.getParameter("resource" + base.getId());
					}
					xmlFile = new File(basepath + "code.xml");
					PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(xmlFile)));
					out.print(value);
					out.flush();
				} catch (Exception e) {
					LOG.error("Error saving file: " + xmlFile.getAbsolutePath(), e);
				}
			}
		} else if (ACT_PROCESSSIGN.equals(action)) {
			FlowNodeInstance foi = (FlowNodeInstance) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);
			if (suri == null || foi.getStatus() == FlowNodeInstance.STATUS_CLOSED) {
				return;
			}
			String cadenaOrig = null;
			String cadenaBase = getStringToSign(foi);
			try {
				cadenaOrig = SWBUtils.CryptoWrapper.comparablePassword(cadenaBase);
			} catch (GeneralSecurityException gse) {
				LOG.error(gse);
			}

			String appletHidden = request.getParameter("hiddenSign");
			try {
				CertificateFactory cf = CertificateFactory.getInstance("X.509");
				if (null != user && null != user.getExternalID()) {
					Iterator<SemanticObject> it = foi.getProcessSite().getSemanticModel().listSubjects(X509Certificate.swp_X509Serial, user.getExternalID()); // TODO: Modificar Búsqueda
					if (it.hasNext()) {
						X509Certificate certObj = (X509Certificate) it.next().createGenericInstance();
						FileInputStream fis = new FileInputStream(SWBPortal.getWorkPath() + certObj.getWorkPath() + "/" + certObj.getFile());
						BufferedInputStream bis = new BufferedInputStream(fis);

						Certificate cert = null;
						if (bis.available() > 0) {
							cert = cf.generateCertificate(bis);
						}

						bis.close();
						fis.close();
						Signature sig = Signature.getInstance("SHA1withRSA");
						sig.initVerify(cert);
						byte[] data = Base64.decode(appletHidden);
						sig.update(cadenaOrig.getBytes());
						boolean flag = sig.verify(data);

						if (flag) {
							X509SingInstance x509SingInstance = X509SingInstance.ClassMgr.createX509SingInstance(foi.getProcessSite());
							x509SingInstance.setCertificate(certObj);
							x509SingInstance.setFlowNodeInstance(foi);
							x509SingInstance.setOriginalString(cadenaOrig);
							x509SingInstance.setSignedString(appletHidden);

							File file = new File(SWBPortal.getWorkPath() + x509SingInstance.getWorkPath());
							file.mkdirs();
							FileOutputStream fileOut = new FileOutputStream(SWBPortal.getWorkPath() + x509SingInstance.getWorkPath() + "/baseData.nt");
							fileOut.write(cadenaBase.getBytes("utf8"));
							fileOut.flush();
							fileOut.close();

							foi.close(response.getUser(), Instance.ACTION_ACCEPT);
							response.setMode(MODE_ACUSE);
							response.setRenderParameter("sg", x509SingInstance.getURI());
						} else {
							LOG.error(new Exception("Error al validar firma..."));
							// TODO: Fallo de validación de firma.....
							response.setMode(MODE_SIGN);
							response.setRenderParameter("err", "invCert");
						}
					} else {
						response.setMode(MODE_SIGN);
						response.setRenderParameter("err", "noCert");
					}
				} else {
					response.setMode(MODE_SIGN);
					response.setRenderParameter("err", "noCert");
				}
			} catch (Exception gse) {
				// TODO: Como poner el error...
				LOG.error("Error al firmar la tarea...", gse);
			}
			// TODO: falta procesar el parámetro del applet de la firma
			// al final se cierra ....
		} else if (ACT_PROCESS.equals(action)) {
			FlowNodeInstance foi = (FlowNodeInstance) SWBPlatform.getSemanticMgr().getOntology().getGenericObject(suri);
			if (suri == null || foi.getStatus() == FlowNodeInstance.STATUS_CLOSED) {
				return;
			}
			SWBProcessFormMgr mgr = new SWBProcessFormMgr(foi);
			mgr.clearProperties();

			HashMap<String, String> hmprops = new HashMap();
			int i = 1;
			while (!base.getAttribute("prop" + i, "").equals("")) {
				String sprop = base.getAttribute("prop" + i);
				HashMap<String, String> propMap = getPropertiesMap(sprop);

				hmprops.put(propMap.get("varName") + "|" + propMap.get("propId"), sprop);
				i++;
			}

			Iterator<ItemAwareReference> it = foi.listHeraquicalItemAwareReference().iterator();
			while (it.hasNext()) {
				ItemAwareReference item = it.next();
				SWBClass obj = item.getProcessObject();
				if (obj != null) {
					String varName = item.getItemAware().getName();
					SemanticClass cls = obj.getSemanticObject().getSemanticClass();
					Iterator<SemanticProperty> itp = cls.listProperties();
					while (itp.hasNext()) {
						SemanticProperty prop = itp.next();
						if (isViewProperty(response, varName, prop, hmprops)) {
							mgr.addProperty(prop, varName, SWBFormMgr.MODE_VIEW);
						} else if (isEditProperty(response, varName, prop, hmprops)) {
							mgr.addProperty(prop, varName, SWBFormMgr.MODE_EDIT);
						}
					}
				}
			}
			try {
				mgr.processForm(request);
				String act = null;
				String redirect = null;

				// validar por si requiere firmado
				if (base.getAttribute("useSign", "").equals("use")) {
					// redireccionamiento a doSign
					response.setMode(MODE_SIGN);
				} else if (request.getParameter(PARAM_ACCEPT) != null && request.getParameter(PARAM_ACCEPT).length() > 0) {
					act = Instance.ACTION_ACCEPT;
				} else if (request.getParameter(PARAM_REJECT) != null && request.getParameter(PARAM_REJECT).length() > 0) {
					act = Instance.ACTION_REJECT;
				}

				if (act != null) {
					foi.close(response.getUser(), act);
					if (foi.getFlowNodeType() instanceof UserTask
							&& ((UserTask) foi.getFlowNodeType()).isLinkNextUserTask()) {
						List<FlowNodeInstance> arr = SWBProcessMgr.getActiveUserTaskInstances(foi.getProcessInstance(),
								response.getUser());
						if (!arr.isEmpty()) {
							redirect = arr.get(0).getUserTaskUrl();
						} else {
							redirect = foi.getUserTaskInboxUrl();
						}
					} else {
						redirect = foi.getUserTaskInboxUrl();
					}
				}
				if (redirect != null) {
					response.sendRedirect(redirect);
				}
			} catch (Exception e) {
				LOG.error("Error al procesar ....", e);
				response.setRenderParameter("err", "invalidForm");
			}
		} else {
			super.processAction(request, response);
		}
	}

	@Override
	public void processRequest(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
		String mode = paramRequest.getMode();
		if (MODE_EDITPROP.equals(mode)) {
			doConfig(request, response, paramRequest);
		} else if (MODE_SIGN.equals(mode)) {
			doSign(request, response, paramRequest);
		} else if (MODE_ACUSE.equals(mode)) {
			doAcuse(request, response, paramRequest);
		} else if (MODE_ADDPROPS.equals(mode)) {
			doAddProps(request, response, paramRequest);
		} else if (MODE_RESPONSE.equals(mode)) {
			doResponse(request, response, paramRequest);
		} else {
			super.processRequest(request, response, paramRequest);
		}
	}

	public void doAddProps(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
		String jsp = "/swbadmin/jsp/process/formsBuilder/listProps.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(jsp);
		Resource base = getResourceBase();

		UserTask uTask = (UserTask) base.getResourceable();
		if (uTask != null) {
			HashMap<String, SemanticProperty> allprops = new HashMap();
			Iterator<ItemAware> it = uTask.listHerarquicalRelatedItemAwarePlusNullOutputs().iterator();

			while (it.hasNext()) {
				ItemAware item = it.next();
				SemanticClass cls = item.getItemSemanticClass();
				if (cls != null) {
					Iterator<SemanticProperty> itp = cls.listProperties();
					while (itp.hasNext()) {
						SemanticProperty prop = itp.next();
						String name = item.getName() + "|" + prop.getPropId();
						if (!prop.getPropId().equals("swb:valid") && !allprops.containsKey(name)) {
							allprops.put(name, prop);
						}
					}
				}
			}

			int max = 1;
			while (!base.getAttribute("prop" + max, "").equals("")) {
				String val = base.getAttribute("prop" + max++);
				HashMap<String, String> map = ProcessForm.getPropertiesMap(val);
				String key = map.get("varName") + "|" + map.get("propId");
				if (allprops.containsKey(key)) {
					allprops.remove(key);
				}
			}

			try {
				request.setAttribute(ATT_PROPMAP, allprops);
				request.setAttribute(ATT_PARAMREQUEST, paramRequest);
				rd.include(request, response);
			} catch (Exception ex) {
				LOG.error(ex);
			}
		}
	}

	@Override
	public void doAdmin(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		String jsp = "/swbadmin/jsp/process/formsBuilder/admin.jsp";
		RequestDispatcher rd = request.getRequestDispatcher(jsp);
		Resource base = getResourceBase();
		HashMap<String, ItemAware> userDefinedVars = new HashMap<>();
		HashMap<String, SemanticProperty> allprops = new HashMap<>();
		HashMap<String, SemanticClass> hmclass = new HashMap<>();
		ArrayList<String> baseProps = new ArrayList<>();
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");

		UserTask task = (UserTask) base.getResourceable();
		Iterator<ItemAware> it = task.listHerarquicalRelatedItemAwarePlusNullOutputs().iterator();
		while (it.hasNext()) {
			ItemAware item = it.next();
			SemanticClass cls = item.getItemSemanticClass();
			if (cls != null) {
				Iterator<SemanticProperty> itp = cls.listProperties();
				while (itp.hasNext()) {
					SemanticProperty prop = itp.next();
					String name = item.getName() + "|" + prop.getPropId();
					if (cls.isSubClass(DataTypes.sclass) && !userDefinedVars.containsKey(name)) {
						userDefinedVars.put(name, item);
					}
					if (!prop.getPropId().equals("swb:valid") && !allprops.containsKey(name)) {
						allprops.put(name, prop);
						if (!hmclass.containsKey(item.getName())) {
							hmclass.put(item.getName(), item.getItemSemanticClass());
						}
					}
				}
			}
		}

		int max = 1;
		while (!base.getAttribute("prop" + max, "").equals("")) {
			String val = base.getAttribute("prop" + max++);
			HashMap<String, String> map = ProcessForm.getPropertiesMap(val);
			String key = map.get("varName") + "|" + map.get("propId");
			if (allprops.containsKey(key)) {
				baseProps.add(val);
			}
		}

		try {
			request.setAttribute(ATT_PARAMREQUEST, paramRequest);
			request.setAttribute(ATT_RBASE, base);
			request.setAttribute(ATT_PROPMAP, baseProps);
			request.setAttribute(ATT_CLASSMAP, hmclass);
			request.setAttribute(ATT_USERVARS, userDefinedVars);
			if (base.getResourceable() instanceof UserTask) {
				request.setAttribute(ATT_TASK, task);
			}
			rd.include(request, response);
		} catch (Exception ex) {
			LOG.error("FormsBuilderResource - Error including admin.jsp", ex);
		}
	}

	@Override
	public void doView(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
		String jsp = SWBPlatform.getContextPath() + "/swbadmin/jsp/process/formsBuilder/render.jsp";

		RequestDispatcher rd = request.getRequestDispatcher(jsp);
		try {
			request.setAttribute(ATT_PARAMREQUEST, paramRequest);
			request.setAttribute(ATT_RBASE, this);
			if (getResourceBase().getResourceable() instanceof UserTask) {
				request.setAttribute(ATT_TASK, (UserTask) getResourceBase().getResourceable());
			}
			rd.include(request, response);
		} catch (Exception ex) {
			LOG.error("FormsBuilderResource - Error including render.jsp", ex);
		}
	}

	public void doConfig(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
		String jsp = "/swbadmin/jsp/process/formsBuilder/config.jsp";
		Resource base = getResourceBase();
		response.setContentType("text/html; charset=ISO-8859-1");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");

		RequestDispatcher rd = request.getRequestDispatcher(jsp);
		try {
			request.setAttribute(ATT_PARAMREQUEST, paramRequest);
			request.setAttribute(ATT_RBASE, base);
			if (base.getResourceable() instanceof UserTask) {
				request.setAttribute(ATT_TASK, (UserTask) base.getResourceable());
			}
			rd.include(request, response);
		} catch (Exception ex) {
			LOG.error("FormsBuilderResource - Error including config.jsp", ex);
		}
	}

	public static String getFESelect(String fEsel, SWBParamRequest paramRequest, SemanticProperty sprop) throws SWBResourceException {
		User usr = paramRequest.getUser();
		SemanticOntology ont = SWBPlatform.getSemanticMgr().getSchema();
		SemanticVocabulary sv = SWBPlatform.getSemanticMgr().getVocabulary();
		StringBuilder ret = new StringBuilder();
		ret.append("\n<optgroup label=\"").append(paramRequest.getLocaleString("lblGenericFE")).append("\">");
		ret.append("\n<option value=\"generico\" selected >GenericFormElement</option>");
		ret.append("\n</optgroup>");

		HashMap<String, SemanticClass> hmscfe = new HashMap<String, SemanticClass>();
		HashMap<String, SemanticObject> hmso;
		SemanticProperty pro = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty(SemanticVocabulary.SWB_ANNOT_FORMELEMENTRANGE);

		Iterator<SemanticClass> itsub = sv.getSemanticClass(SemanticVocabulary.SWB_SWBFORMELEMENT).listSubClasses();
		while (itsub.hasNext()) {
			SemanticClass scobj = itsub.next();

			if (pro == null || sprop == null) {
				continue;
			}
			NodeIterator it = scobj.getOntClass().listPropertyValues(pro.getRDFProperty());

			while (it.hasNext()) {
				RDFNode node = it.next();
				if (node != null) {
					if (sprop.getRange().getURI().equals(node.asResource().getURI())) {
						hmscfe.put(scobj.getDisplayName(usr.getLanguage()), scobj);
					} else if (sprop.getRangeClass() != null && node.isResource()) {
						SemanticClass cls = sv.getSemanticClass(node.asResource().getURI());
						if (cls != null && sprop.getRangeClass().isSubClass(cls)) {
							hmscfe.put(scobj.getDisplayName(usr.getLanguage()), scobj);
						}
					}
				}
			}
		}

		ArrayList list = new ArrayList(hmscfe.keySet());
		Collections.sort(list);

		Iterator<String> itsc = list.iterator();
		while (itsc.hasNext()) {

			String key = itsc.next();
			SemanticClass scfe = hmscfe.get(key);

			ret.append("\n<optgroup label=\"");
			ret.append(scfe.getDisplayName(paramRequest.getUser().getLanguage()));
			ret.append("\">");
			hmso = new HashMap<>();

			Iterator<SemanticObject> itsco = ont.listInstancesOfClass(scfe);
			while (itsco.hasNext()) {
				SemanticObject semObj = itsco.next();
				hmso.put(semObj.getDisplayName(usr.getLanguage()), semObj);
			}

			list = new ArrayList(hmso.keySet());
			Collections.sort(list);

			Iterator<String> itsoo = list.iterator();
			while (itsoo.hasNext()) {
				key = itsoo.next();
				SemanticObject sofe = hmso.get(key);
				ret.append("\n<option value=\"");
				ret.append(sofe.getURI());
				ret.append("\"");
				String stmp = fEsel + "edit|" + sofe.getURI();
				String stmp2 = fEsel + "view|" + sofe.getURI();
				String data = paramRequest.getResourceBase().getData(paramRequest.getWebPage());
				if (fEsel != null && !fEsel.equals("") && (data != null && (data.contains(stmp) || data.contains(stmp2))
						|| fEsel.equals(sofe.getURI()))) {
					ret.append(" selected ");
				}
				ret.append(">");
				ret.append(sofe.getDisplayName(paramRequest.getUser().getLanguage()));
				ret.append("\n</option>");
			}
			ret.append("\n</optgroup>");
		}
		return ret.toString();
	}

	public static HashMap<String, String> getPropertiesMap(String str) {
		HashMap<String, String> ret = new HashMap<>();

		StringTokenizer stoken = new StringTokenizer(str, "|");
		if (stoken.hasMoreTokens()) {
			ret.put("varName", stoken.nextToken());
		}
		if (stoken.hasMoreTokens()) {
			ret.put("propId", stoken.nextToken());
		}
		if (stoken.hasMoreTokens()) {
			ret.put("mode", stoken.nextToken());
		}
		if (stoken.hasMoreTokens()) {
			ret.put("fe", stoken.nextToken());
		}
		if (stoken.hasMoreTokens()) {
			ret.put("label", stoken.nextToken());
		}
		if (stoken.hasMoreTokens()) {
			ret.put(PARAM_ROLES, stoken.nextToken());
		}
		return ret;
	}

	public String getFormHTML(HttpServletRequest request, SWBActionResponse response) throws SWBResourceException, IOException {
		StringBuilder ret = new StringBuilder();
		Resource base = getResourceBase();

		SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
		UserTask ut = (UserTask) base.getResourceable();

		HashMap<String, SemanticClass> hmclass = new HashMap<>();
		HashMap<String, SemanticProperty> hmprops = new HashMap<>();

		Iterator<ItemAware> it = ut.listHerarquicalRelatedItemAwarePlusNullOutputs().iterator();
		while (it.hasNext()) {
			ItemAware item = it.next();

			SemanticClass cls = item.getItemSemanticClass();
			if (cls != null) {
				hmclass.put(item.getName(), cls);

				Iterator<SemanticProperty> itp = cls.listProperties();
				while (itp.hasNext()) {
					SemanticProperty prop = itp.next();
					hmprops.put(prop.getPropId(), prop);
				}
			}

			// TODO: Agregar propiedades del item
		}

		ret.append("<div id=\"processForm\">");
		ret.append("\n<form");

		if (request.getParameter("useDojo") != null && request.getParameter("useDojo").equals("dojo")) {
			ret.append(" htmlType=\"dojo\" ");
		}
		ret.append(" class=\"swbform\">");

		ret.append("\n    <fieldset>");
		ret.append("\n      <table>");

		int max = 1;
		while (!base.getAttribute("prop" + max, "").equals("")) {

			String val = base.getAttribute("prop" + max);
			HashMap<String, String> props = getPropertiesMap(val);
			String varName = props.get("varName");
			String propid = props.get("propId");
			String modo = props.get("mode");
			String fe = props.get("fe");

			SemanticProperty semprop = hmprops.get(propid);
			String strMode = "";
			SemanticClass semclass = hmclass.get(varName);

			if (semclass != null && semprop != null) {
				if (modo.equals(FE_MODE_VIEW)) {
					strMode = SWBFormMgr.MODE_VIEW;
				} else if (modo.equals(FE_MODE_EDIT)) {
					strMode = SWBFormMgr.MODE_EDIT;
				}
				SemanticObject sofe = ont.getSemanticObject(fe);
				String strFE = "";

				if (null != sofe) {
					strFE = "formElement=\"" + sofe.getDisplayName(response.getUser().getLanguage()) + "\"";
				}

				ret.append("\n     <tr>");
				ret.append("\n       <td width=\"200px\" align=\"right\"><label name=\"" + varName + "."
						+ semprop.getName() + "\" /></td>");
				ret.append("\n       <td><property name=\"" + varName + "." + semprop.getName() + "\" " + strFE
						+ " mode=\"" + strMode + "\" /></td>");
				ret.append("\n    </tr>");
			}
			max++;
		}

		ret.append("\n      </table> ");
		ret.append("\n      </fieldset> ");
		ret.append("\n      <fieldset>");

		// validar que botones seleccionaron
		if (base.getAttribute("btnSave") != null) {
			ret.append("\n          <Button type=\"savebtn\"/>");
		}
		if (base.getAttribute("btnAccept") != null) {
			ret.append("\n          <Button type=\"submit\" name=\"accept\" title=\"")
					.append(response.getLocaleString("btnCloseTask")).append("\" isBusyButton=\"true\" />");
		}
		if (base.getAttribute("btnReject") != null) {
			ret.append("\n          <Button isBusyButton=\"true\" name=\"reject\" title=\"")
					.append(response.getLocaleString("btnRejectTask")).append("\" type=\"submit\" />");
		}
		if (base.getAttribute("btnCancel") != null) {
			ret.append("\n          <Button type=\"cancelbtn\"/>");
		}

		ret.append("\n      </fieldset>");
		ret.append("\n</form>");
		ret.append("\n</div>");

		return ret.toString();
	}

	public void doSign(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest) throws SWBResourceException, IOException {
		PrintWriter out = response.getWriter();
		Resource base = getResourceBase();
		User user = paramRequest.getUser();

		String suri = request.getParameter("suri");
		SWBResourceURL viewUrl = paramRequest.getRenderUrl().setMode(SWBParamRequest.Mode_VIEW).setParameter("suri", suri);

		if (suri == null) {
			out.println("<script type=\"text/javascript\">" + " alert('" + paramRequest.getLocaleString("msgNoInstance") + "');" + "window.location='" + viewUrl + "';" + "</script>");
			return;
		}

		SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
		FlowNodeInstance foi = (FlowNodeInstance) ont.getGenericObject(suri);

		boolean canSign = false;
		if (user.getExternalID() != null) {
			Iterator<SemanticObject> it = foi.getProcessSite().getSemanticModel().listSubjects(X509Certificate.swp_X509Serial, user.getExternalID()); // TODO: Modificar Búsqueda
			if (it.hasNext()) {
				canSign = true;
			}
		}
		if (!canSign) {
			out.println("<script type=\"text/javascript\">" + " alert('" + paramRequest.getLocaleString("msgNoCert") + "');" + "window.location='" + viewUrl + "';" + "</script>");
			return;
		}
		if (request.getParameter("err") != null) {
			out.println("<script type=\"text/javascript\">alert('" + paramRequest.getLocaleString("msgNoSign") + "'); window.location='" + viewUrl + "';</script>");
		}

		String sigCad = getStringToSign(foi);
		String signTemp = null;
		try {
			signTemp = SWBUtils.CryptoWrapper.comparablePassword(sigCad);
		} catch (GeneralSecurityException gse) {
			LOG.error(gse);
		}

		User asigned = foi.getAssignedto();
		if (asigned != null && !asigned.equals(user)) {
			out.println("<script type=\"text/javascript\">" + " alert('" + paramRequest.getLocaleString("msgAssigned")
					+ "');" + "window.location='" + viewUrl + "';" + "</script>");
			return;
		}

		if (foi.getStatus() == Instance.STATUS_CLOSED || foi.getStatus() == Instance.STATUS_ABORTED
				|| foi.getStatus() == Instance.STATUS_STOPED) {
			out.println("<script type=\"text/javascript\">" + " alert('" + paramRequest.getLocaleString("msgClosed")
					+ "');" + "window.location='" + viewUrl + "';" + "</script>");
			return;
		}

		SWBResourceURL urlact = paramRequest.getActionUrl();
		urlact.setAction(ACT_PROCESSSIGN);

		out.println(SWBForms.DOJO_REQUIRED);

		out.println("<script type=\"text/javascript\">function validateForm" + foi.getId()
				+ "(form) {if (form.validate()) {return true;} else {alert('"
				+ paramRequest.getLocaleString("msgInvalidData") + "'); return false;}}");
		out.println(
				"function setSignature(data){ dataElement = document.getElementById('hiddenSign'); dataElement.value=data; form = document.getElementById('"
						+ foi.getId() + "/form'); alert('" + paramRequest.getLocaleString("msgSignedForm")
						+ "');form.submit();}</script>");
		out.println("<div id=\"processForm\">");
		out.println("<form id=\"" + foi.getId() + "/form\" dojoType=\"dijit.form.Form\" class=\"swbform\" action=\""
				+ urlact + "\" method=\"post\" onSubmit=\"return validateForm" + foi.getId() + "(this);\">");
		out.println("<input type=\"hidden\" name=\"suri\" value=\"" + suri + "\"/>");
		out.println("<input type=\"hidden\" id=\"hiddenSign\" name=\"hiddenSign\" value=\"\"/>");
		out.println("<fieldset>");
		out.println("<table>");

		out.println("<tr>");
		out.println("<td width=\"200px\" align=\"right\"><label>" + paramRequest.getLocaleString("signForm")
				+ ":</label></td>");
		out.println("<td>");
		out.println("<applet code=\"signatureapplet.SignatureApplet.class\" codebase=\"/swbadmin/lib\" archive=\""
				+ SWBPlatform.getContextPath()
				+ "/swbadmin/lib/SWBAplDigitalSignature.jar\" width=\"600\" height=\"250\">");
		out.println("<param name=\"message\" value=\"" + signTemp + "\">");
		out.println("</applet>");
		out.println("</td></tr>");
		out.println("    </table>");
		out.println("</fieldset>");
		out.println("<fieldset><span align=\"center\">");
		if (base.getAttribute("btnCancel", "").equals("use")) {
			out.println("<button dojoType=\"dijit.form.Button\" onclick=\"window.location='" + foi.getUserTaskInboxUrl()
					+ "?suri=" + suri + "'\">" + paramRequest.getLocaleString("cancel") + "</button>");
		}
		out.println("</span></fieldset>");
		out.println("</form>");
		out.println("</div>");
	}

	public void doResponse(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest)
			throws SWBResourceException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Map params = request.getParameterMap();
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

	public void doAcuse(HttpServletRequest request, HttpServletResponse response, SWBParamRequest paramRequest)
			throws SWBResourceException, IOException {
		PrintWriter out = response.getWriter();

		String suri = request.getParameter("suri");
		if (suri == null) {
			out.println(paramRequest.getLocaleString("msgNoInstance"));
			return;
		}

		String x509 = request.getParameter("sg");
		if (x509 == null) {
			out.println(paramRequest.getLocaleString("msgNoInstance"));
			return;
		}

		SemanticOntology ont = SWBPlatform.getSemanticMgr().getOntology();
		FlowNodeInstance foi = (FlowNodeInstance) ont.getGenericObject(suri);
		X509SingInstance x509SingInstance = (X509SingInstance) ont.getGenericObject(x509);

		if (null == x509SingInstance) {
			out.println(paramRequest.getLocaleString("msgNoCert"));
			return;
		}

		out.println(SWBForms.DOJO_REQUIRED);

		out.println("<div id=\"processForm\" class=\"swbform\">");
		out.println("<h2>" + paramRequest.getLocaleString("signRecpt") + "</h2>");
		out.println("<fieldset>");
		out.println("<table>");

		out.println("<tr>");
		out.println("<td width=\"200px\" align=\"right\"><label>" + paramRequest.getLocaleString("signValue")
				+ ":</label></td>");
		out.println("<td><div id=\"code\">");
		out.println(htmlwrap(x509SingInstance.getOriginalString(), 50));
		out.println("</div></td></tr>");

		out.println("<td width=\"200px\" align=\"right\"><label>" + paramRequest.getLocaleString("signCode")
				+ ":</label></td>");
		out.println("<td><div id=\"code\">");
		out.println(htmlwrap(x509SingInstance.getSignedString(), 50));
		out.println("<div></td></tr>");

		out.println("<td width=\"200px\" align=\"right\"><label>" + paramRequest.getLocaleString("signPInstance")
				+ ":</label></td>");
		out.println("<td>");
		out.println(x509SingInstance.getFlowNodeInstance().getProcessInstance().getProcessType()
				.getDisplayTitle(paramRequest.getUser().getLanguage()));
		out.println("</td></tr>");

		out.println("<td width=\"200px\" align=\"right\"><label>" + paramRequest.getLocaleString("signTInstance")
				+ ":</label></td>");
		out.println("<td>");
		out.println(x509SingInstance.getFlowNodeInstance().getFlowNodeType()
				.getDisplayTitle(paramRequest.getUser().getLanguage()));
		out.println("</td></tr>");

		out.println("<td width=\"200px\" align=\"right\"><label>" + paramRequest.getLocaleString("signUser")
				+ ":</label></td>");
		out.println("<td>");
		out.println(x509SingInstance.getCertificate().getName());
		out.println("</td></tr>");

		out.println("<td width=\"200px\" align=\"right\"><label>" + paramRequest.getLocaleString("signId")
				+ ":</label></td>");
		out.println("<td>");
		out.println(x509SingInstance.getCertificate().getSerial());
		out.println("</td></tr>");

		out.println("<td width=\"200px\" align=\"right\"><label>" + paramRequest.getLocaleString("signDate")
				+ ":</label></td>");
		out.println("<td>");
		out.println(x509SingInstance.getCreated());
		out.println("</td></tr>");

		out.println("    </table>");
		out.println("</fieldset>");
		out.println("<fieldset><span align=\"center\">");

		out.println("<button dojoType=\"dijit.form.Button\" onclick=\"window.location='" + foi.getUserTaskInboxUrl()
				+ "?suri=" + suri + "'\">" + paramRequest.getLocaleString("signExit") + "</button>");

		out.println("</span></fieldset>");
		out.println("</div>");
	}

	private String getStringToSign(FlowNodeInstance flowNodeInstance) {

		Iterator<ItemAwareReference> it = flowNodeInstance.listHeraquicalItemAwareReference().iterator();
		ByteArrayOutputStream bout = new ByteArrayOutputStream();

		Model model = ModelFactory.createDefaultModel();
		while (it.hasNext()) {
			ItemAwareReference itemAwareReference = it.next();
			model.add(itemAwareReference.getProcessObject().getSemanticObject().getRDFResource().listProperties());

		}
		// "RDF/XML", "RDF/XML-ABBREV", "N-TRIPLE" and "N3"
		model.write(bout, "N-TRIPLE");

		String ret = null;
		try {
			ret = bout.toString("UTF8");
		} catch (IOException ioe) {
			LOG.error(ioe);
		}
		return ret;
	}

	public String htmlwrap(String orig, final int length) {
		StringBuilder ret = new StringBuilder(512);
		ret.append("<pre>");
		int linea = 0;
		while (linea * length < orig.length()) {
			int inicial = linea * length;
			int ifinal = ++linea * length;
			if (ifinal > orig.length()) {
				ret.append(orig.substring(inicial));
			} else {
				ret.append(orig.substring(inicial, ifinal));
				ret.append("\n");
			}
		}
		ret.append("</pre>");
		return ret.toString();
	}

	public boolean isViewProperty(SWBParameters paramRequest, String varName, SemanticProperty prop, HashMap<String, String> hm) {
		boolean ret = false;
		String data = hm.get(varName + "|" + prop.getPropId());
		if (data != null && data.indexOf(varName + "|" + prop.getPropId() + "|view") > -1) {
			ret = true;
		}
		return ret;
	}

	public boolean isEditProperty(SWBParameters paramRequest, String varName, SemanticProperty prop, HashMap<String, String> hm) {
		boolean ret = false;
		String data = hm.get(varName + "|" + prop.getPropId());
		if (data != null && data.indexOf(varName + "|" + prop.getPropId() + "|edit") > -1) {
			ret = true;
		}
		return ret;
	}

	/**
	 * Obtiene la URL de la página Web asociada a la Bandeja de tareas del sitio.
	 * 
	 * @return URL de la bandeja de tareas o URL del proceso en su defecto.
	 */
	public String getUserTaskInboxUrl() {
		String url = getResourceBase().getWebSite().getHomePage().getUrl();
		ResourceType rtype = ResourceType.ClassMgr.getResourceType("ProcessTaskInbox", getResourceBase().getWebSite());

		if (rtype != null) {
			Resource res = rtype.getResource();
			if (res != null) {
				Resourceable resable = res.getResourceable();
				if (resable instanceof WebPage) {
					url = ((WebPage) resable).getUrl();
				}
			}
		}
		return url;
	}
}