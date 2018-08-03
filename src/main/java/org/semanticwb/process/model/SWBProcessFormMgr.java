/*
 * SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
 * colaboración y conocimiento, que gracias al uso de tecnología semántica puede generar contextos de
 * información alrededor de algún tema de interés o bien integrar información y aplicaciones de diferentes
 * fuentes, donde a la información se le asigna un significado, de forma que pueda ser interpretada y
 * procesada por personas y/o sistemas, es una creación original del Fondo de Información y Documentación
 * para la Industria INFOTEC, cuyo registro se encuentra actualmente en trámite.
 *
 * INFOTEC pone a su disposición la herramienta SemanticWebBuilder a través de su licenciamiento abierto al público ('open source'),
 * en virtud del cual, usted podrá usarlo en las mismas condiciones con que INFOTEC lo ha diseñado y puesto a su disposición;
 * aprender de él; distribuirlo a terceros; acceder a su código fuente y modificarlo, y combinarlo o enlazarlo con otro software,
 * todo ello de conformidad con los términos y condiciones de la LICENCIA ABIERTA AL PÚBLICO que otorga INFOTEC para la utilización
 * del SemanticWebBuilder 4.0.
 *
 * INFOTEC no otorga garantía sobre SemanticWebBuilder, de ninguna especie y naturaleza, ni implícita ni explícita,
 * siendo usted completamente responsable de la utilización que le dé y asumiendo la totalidad de los riesgos que puedan derivar
 * de la misma.
 *
 * Si usted tiene cualquier duda o comentario sobre SemanticWebBuilder, INFOTEC pone a su disposición la siguiente
 * dirección electrónica: http://www.semanticwebbuilder.org.mx
 */
package org.semanticwb.process.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.semanticwb.model.DisplayProperty;
import org.semanticwb.model.FormElement;
import org.semanticwb.model.FormValidateException;
import org.semanticwb.model.PropertyGroup;
import org.semanticwb.model.SWBClass;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.platform.SemanticClass;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;
import org.semanticwb.portal.SWBFormButton;
import org.semanticwb.portal.SWBFormMgr;
import org.semanticwb.portal.SWBForms;

/**
 *
 * @author Javier Solís {javier.solis}
 */
public class SWBProcessFormMgr implements SWBForms {
	private FlowNodeInstance mPinst = null;
	private HashMap<String, SWBFormMgr> mgrs;
	private HashMap<String, HashMap<SemanticProperty, String>> views;
	private Map<SemanticProperty, String> mPropmap = null;
	private String mMode = MODE_EDIT;
	private String mAction = "";
	private String mMethod = "POST";
	private String mOnsubmit = null;
	private String mLang = "es";
	private String mType = TYPE_DOJO;

	private HashMap<String, String> hidden = null;
	private ArrayList<Object> buttons = null;

	private boolean submitByAjax = false;

	public SWBProcessFormMgr(FlowNodeInstance inst) {
		this.mPinst = inst;
		mgrs = new HashMap<>();
		views = new HashMap<>();
		hidden = new HashMap<>();
		buttons = new ArrayList<>();

		Iterator<ItemAwareReference> objs = inst.listHeraquicalItemAwareReference().iterator();
		while (objs.hasNext()) {
			ItemAwareReference item = objs.next();
			SWBClass processObject = item.getProcessObject();
			if (processObject != null) {
				SWBFormMgr mgr = new SWBFormMgr(processObject.getSemanticObject(), null, MODE_EDIT);
				mgr.setType(mType);
				if (item.getItemAware() != null) {
					mgr.setVarName(item.getItemAware().getName());
					if (item.getItemAware().getDataObjectClass().transformToSemanticClass()
							.isSubClass(DataTypes.sclass)) {
						mgr.setVarReference(item.getItemAware().getSemanticObject());
					}
					mgrs.put(item.getItemAware().getName(), mgr);
				}
			}
			// TODO: agregar variable en lugar del uri de la clase
		}
	}

	public void setType(String type) {
		this.mType = type;
	}

	public void clearProperties() {
		Iterator<SWBFormMgr> it = mgrs.values().iterator();
		while (it.hasNext()) {
			SWBFormMgr mgr = it.next();
			mgr.clearProperties();
		}
		views = new HashMap<>();
	}

	public void addProperty(SemanticProperty prop, String varName) {
		addProperty(prop, varName, MODE_EDIT);
	}

	public void addProperty(SemanticProperty prop, String varName, String view) {
		SWBFormMgr mgr = mgrs.get(varName);
		mgr.addProperty(prop);

		HashMap<SemanticProperty, String> map = views.get(varName);
		if (map == null) {
			map = new HashMap<>();
			views.put(varName, map);
		}
		map.put(prop, view);
	}

	/**
	 * Genera HTML de la forma del tipo de objeto especificado en el constructor
	 * 
	 * @param request
	 * @return
	 */
	public String renderForm(HttpServletRequest request) {
		StringBuilder ret = new StringBuilder();
		String frmname = getFormName();
		String onsubmit = "";
		boolean isDojo = mType.equals(TYPE_DOJO);
		
		if (mOnsubmit != null)
			onsubmit = " onsubmit=\"" + mOnsubmit + "\"";
		// si es dojo por default se manda por ajax
		if (mOnsubmit == null && submitByAjax)
			onsubmit = "  onsubmit=\"submitForm('" + frmname + "');return false;\"";

		if (isDojo) {
			ret.append(DOJO_REQUIRED);
			ret.append("<form id=\"" + frmname + "\" dojoType=\"dijit.form.Form\" class=\"swbform\" action=\""
					+ mAction + "\"" + onsubmit + " method=\"" + mMethod.toLowerCase() + "\">\n");
		} else {
			ret.append("<form id=\"" + frmname + "\" class=\"swbform\" action=\"" + mAction + "\"" + onsubmit
					+ " method=\"" + mMethod.toLowerCase() + "\">\n");
		}
		
		ret.append(getFormHiddens());

		// Merge properties
		HashMap<PropertyGroup, TreeSet<SWBProcessProperty>> groups = new HashMap<>();
		Iterator<SWBFormMgr> mgrit = mgrs.values().iterator();
		while (mgrit.hasNext()) {
			SWBFormMgr mgr = mgrit.next();
			Iterator<PropertyGroup> grpit = mgr.getGroups().keySet().iterator();
			while (grpit.hasNext()) {
				PropertyGroup propertyGroup = grpit.next();
				TreeSet<SWBProcessProperty> set = groups.get(propertyGroup);
				if (set == null) {
					set = new TreeSet<>(new Comparator<SWBProcessProperty>() {
						public int compare(SWBProcessProperty o1, SWBProcessProperty o2) {
							SemanticObject sobj1 = o1.getSemanticProperty()
									.getDisplayProperty();
							SemanticObject sobj2 = o2.getSemanticProperty()
									.getDisplayProperty();
							int v1 = 999999999;
							int v2 = 999999999;
							if (sobj1 != null)
								v1 = new DisplayProperty(sobj1).getIndex();
							if (sobj2 != null)
								v2 = new DisplayProperty(sobj2).getIndex();
							return v1 < v2 ? -1 : 1;
						}
					});
					groups.put(propertyGroup, set);
				}
				Iterator<SemanticProperty> itprop = mgr.getGroups().get(propertyGroup).iterator();
				while (itprop.hasNext()) {
					SemanticProperty semanticProperty = itprop.next();
					SemanticClass cls = mgr.getSemanticObject().getSemanticClass();
					if (mgr.getVarReference() != null) {
						set.add(new SWBProcessProperty(mgr.getVarName(), semanticProperty,
								mgr.getVarReference().getDisplayName(mLang),
								views.get(cls).get(semanticProperty)));
					} else {
						set.add(new SWBProcessProperty(mgr.getVarName(), semanticProperty,
								views.get(cls).get(semanticProperty)));
					}
				}
			}
		}

		Iterator<PropertyGroup> itgp = SWBComparator.sortSortableObject(groups.keySet().iterator());
		while (itgp.hasNext()) {
			PropertyGroup group = itgp.next();
			Iterator<SWBProcessProperty> it = groups.get(group).iterator();
			if (it.hasNext()) {
				ret.append("	<fieldset>\n");
				ret.append("	    <legend>" + group.getSemanticObject().getDisplayName(mLang) + "</legend>\n");
				ret.append("	    <table>\n");

				while (it.hasNext()) {
					SWBProcessProperty pp = it.next();
					SWBFormMgr mgr = mgrs.get(pp.getVarName());
					SemanticProperty prop = pp.getSemanticProperty();
					FormElement ele = mgr.getFormElement(prop);
					mgr.renderProp(request, ret, prop, pp.getVarName() + "." + prop.getName(), ele, pp.getMode(),
							pp.getVarTitle());
				}
				ret.append("	    </table>\n");
				ret.append("	</fieldset>\n");
			}
		}

		ret.append("<fieldset><span align=\"center\">\n");

		Iterator<Object> it = buttons.iterator();
		while (it.hasNext()) {
			Object aux = it.next();
			ret.append("    ");
			if (aux instanceof SWBFormButton) {
				ret.append(((SWBFormButton) aux).renderButton(request, mType, mLang));
			} else {
				ret.append(aux.toString());
			}
			ret.append("\n");
		}
		ret.append("</span></fieldset>\n");
		ret.append("</form>\n");

		return ret.toString();
	}

	public void processForm(HttpServletRequest request) throws FormValidateException {
		// TODO: validateForm(request);
		String smode = request.getParameter(PRM_MODE);
		if (smode != null) {
			Iterator<String> itcls = views.keySet().iterator();
			while (itcls.hasNext()) {
				String varName = itcls.next();
				Iterator<SemanticProperty> it = views.get(varName).keySet().iterator();
				while (it.hasNext()) {
					SemanticProperty prop = it.next();
					SWBFormMgr mgr = mgrs.get(varName);
					if (MODE_EDIT.equals(views.get(varName).get(prop))) {
						mgr.processElement(request, prop, varName + "." + prop.getName());
					}
				}

			}
		}
	}

	public String getFormName() {
		String ret = "/form";
		if (mPinst != null) {
			ret = mPinst.getId() + ret;
		}
		return ret;
	}

	public void setOnSubmit(String onsubmit) {
		mOnsubmit = onsubmit;
	}

	public void addHiddenParameter(String key, String value) {
		if (key != null && value != null)
			hidden.put(key, value);
	}

	/**
	 * Regresa input del tipo hidden requeridos para el processForm
	 * 
	 * @return
	 */
	public String getFormHiddens() {
		StringBuilder ret = new StringBuilder();
		if (mPinst != null)
			ret.append("    <input type=\"hidden\" name=\"" + PRM_URI + "\" value=\"" + mPinst.getURI() + "\"/>\n");

		if (mMode != null)
			ret.append("    <input type=\"hidden\" name=\"" + PRM_MODE + "\" value=\"" + mMode + "\"/>\n");

		Iterator<Map.Entry<String, String>> hit = hidden.entrySet().iterator();
		while (hit.hasNext()) {
			Map.Entry entry = hit.next();
			ret.append("    <input type=\"hidden\" name=\"" + entry.getKey() + "\" value=\"" + entry.getValue()
					+ "\"/>\n");
		}
		return ret.toString();
	}

	/**
	 * Add HTML text for Button Sample:
	 * <button dojoType="dijit.form.Button" type="submit">Guardar</button>
	 * 
	 * @param html
	 */
	public void addButton(String html) {
		buttons.add(html);
	}

	public void addButton(SWBFormButton button) {
		buttons.add(button);
	}

	public String getAction() {
		return mAction;
	}

	public void setAction(String action) {
		this.mAction = action;
	}

	public FormElement getFormElement(SemanticProperty prop, String varName) {
		FormElement ele = null;
		SWBFormMgr mgr = mgrs.get(varName);
		if (mgr != null) {
			ele = mgr.getFormElement(prop);
		}
		return ele;
	}

	/**
	 * Render label.
	 *
	 * @param request
	 *            the request
	 * @param prop
	 *            the prop
	 * @param mode
	 *            the mode
	 * @return the string
	 */
	public String renderLabel(HttpServletRequest request, SemanticProperty prop, String varName, String mode) {
		SWBFormMgr mgr = mgrs.get(varName);
		FormElement ele = mgr.getFormElement(prop);

		if (mgr.getVarReference() != null) {
			return ele.renderLabel(request, mgr.getSemanticObject(), prop, varName + "." + prop.getName(), mType, mode,
					mLang, mgr.getVarReference().getDisplayName(mLang));
		}
		return ele.renderLabel(request, mgr.getSemanticObject(), prop, varName + "." + prop.getName(), mType, mode,
				mLang);
	}

	/**
	 * Render element.
	 *
	 * @param request
	 *            the request
	 * @param prop
	 *            the prop
	 * @param mode
	 *            the mode
	 * @return the string
	 */
	public String renderElement(HttpServletRequest request, String varName, SemanticProperty prop, String mode) {
		SWBFormMgr mgr = mgrs.get(varName);
		return mgr.renderElement(request, prop, varName + "." + prop.getName(), mode);
	}

	public String renderElement(HttpServletRequest request, String varName, SemanticProperty prop, FormElement element,
			String mode) {
		SWBFormMgr mgr = mgrs.get(varName);
		
		element.setModel(mgr.getSemanticObject().getModel());
		return element.renderElement(request, mgr.getSemanticObject(), prop, varName + "." + prop.getName(), mType,
				mode, mLang);
	}

}
