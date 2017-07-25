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
 * dirección electrónica:
 *  http://www.semanticwebbuilder.org
 */
package org.semanticwb.process.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.semanticwb.SWBUtils;
import org.semanticwb.model.DisplayProperty;
import org.semanticwb.model.GenericObject;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

public class ItemAwareMappingElement extends org.semanticwb.process.model.base.ItemAwareMappingElementBase {
	/**
	 * Constructor.
	 * 
	 * @param base
	 */
	public ItemAwareMappingElement(SemanticObject base) {
		super(base);
	}

	@Override
	public void process(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName) {
		if (obj == null) {
			obj = new SemanticObject();
		}

		GenericObject gen = obj.createGenericInstance();
		CatchMessageable catchmsg = (CatchMessageable) gen;
		List<ItemAwareMapping> arr = SWBUtils.Collections.copyIterator(catchmsg.listItemAwareMappings());

		Iterator<ItemAware> it = ((FlowNode) catchmsg).getContainer().listHerarquicalRelatedItemAware().iterator();
		while (it.hasNext()) {
			ItemAware itemAware = it.next();
			String var = propName + "-" + itemAware.getId();
			String value = request.getParameter(var);

			if (value != null && value.length() > 0) {
				SemanticObject sobjr = SemanticObject.createSemanticObject(SemanticObject.shortToFullURI(value));
				if (sobjr != null) {
					ItemAware ritemAware = (ItemAware) sobjr.createGenericInstance();
					ItemAwareMapping mapping = getItemAwareMapping(arr, itemAware);
					if (mapping != null) {
						mapping.setRemoteItemAware(ritemAware);
						arr.remove(mapping);
					} else {
						mapping = ItemAwareMapping.ClassMgr.createItemAwareMapping(itemAware.getProcessSite());
						mapping.setLocalItemAware(itemAware);
						mapping.setRemoteItemAware(ritemAware);
						catchmsg.addItemAwareMapping(mapping);

					}
				}
			}
		}

		Iterator<ItemAwareMapping> it2 = arr.iterator();
		while (it2.hasNext()) {
			ItemAwareMapping mapping = it2.next();
			mapping.remove();
		}
	}

	private ItemAwareMapping getItemAwareMapping(List arr, ItemAware itemAware) {
		Iterator<ItemAwareMapping> it = arr.iterator();
		while (it.hasNext()) {
			ItemAwareMapping itemAwareMapping = it.next();
			if (itemAwareMapping.getLocalItemAware().equals(itemAware)) {
				return itemAwareMapping;
			}
		}
		return null;
	}

	/**
	 * Render element.
	 * 
	 * @param request
	 *            the request
	 * @param obj
	 *            the obj
	 * @param prop
	 *            the prop
	 * @param type
	 *            the type
	 * @param mode
	 *            the mode
	 * @param lang
	 *            the lang
	 * @return the string
	 */
	@Override
	public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName, String type, String mode, String lang) {
		SemanticObject theObj = obj;
		if (obj == null) {
			theObj = new SemanticObject();
		}

		boolean dojo = type.equals("dojo");

		StringBuilder ret = new StringBuilder();
		String label = prop.getDisplayName(lang);
		SemanticObject sobj = prop.getDisplayProperty();
		boolean required = prop.isRequired();
		String imsg = null;

		if (sobj != null) {
			DisplayProperty dobj = new DisplayProperty(sobj);
			imsg = dobj.getInvalidMessage();
		}

		if (dojo) {
			if (imsg == null) {
				if (required) {
					imsg = label + " es requerido.";

					if (lang.equals("en")) {
						imsg = label + " is required.";
					}
				} else {
					imsg = "Dato invalido.";

					if (lang.equals("en")) {
						imsg = "Invalid data.";
					}
				}
			}
		}

		if (prop.isObjectProperty()) {
			ArrayList<String> vals = new ArrayList<>();
			String[] auxs = request.getParameterValues(propName);

			if (auxs == null) {
				auxs = new String[0];
			}

			for (int x = 0; x < auxs.length; x++) {
				vals.add(auxs[x]);
			}

			Iterator<SemanticObject> it2 = theObj.listObjectProperties(prop);

			while (it2.hasNext()) {
				SemanticObject semanticObject = it2.next();
				vals.add(semanticObject.getURI());
			}

			if (mode.equals("edit") || mode.equals("create")) {
				GenericObject gen = theObj.createGenericInstance();

				if (gen instanceof CatchMessageable) {
					CatchMessageable catchmsg = (CatchMessageable) gen;

					List<ItemAwareMapping> arr = SWBUtils.Collections.copyIterator(catchmsg.listItemAwareMappings());
					String action = ((ActionCodeable) gen).getActionCode();
					ThrowMessageable throwmsg = null;

					Iterator<SemanticObject> it3 = theObj.getModel().listSubjects(ActionCodeable.swp_actionCode, action == null ? "_NULL_" : action);
					while (it3.hasNext()) {
						SemanticObject semanticObject = it3.next();
						if (semanticObject.instanceOf(ThrowMessageable.swp_ThrowMessageable)) {
							throwmsg = (ThrowMessageable) semanticObject.createGenericInstance();
						}
					}

					if (throwmsg != null && throwmsg.listMessageItemAwares().hasNext()) {
						ret.append("<table border=\"0\">");
						Iterator<ItemAware> it = ((FlowNode) catchmsg).getContainer().listHerarquicalRelatedItemAware().iterator();
						while (it.hasNext()) {
							ItemAware itemAware = it.next();
							if (itemAware.getDataObjectClass() != null) { // Si no lo han configurado no se muestra
								ret.append("<tr>");
								ret.append("<td>");

								String selected = "";
								ItemAwareMapping mapping = getItemAwareMapping(arr, itemAware);
								if (mapping != null)
									selected = mapping.getRemoteItemAware().getShortURI();

								ret.append("<select name=\"").append(propName).append("-").append(itemAware.getId()).append("\"");
								if (dojo) {
									ret.append(" dojoType=\"dijit.form.FilteringSelect\" autoComplete=\"true\" invalidMessage=\"").append(imsg).append("\"");
									if (!selected.isEmpty()) {
										ret.append(" value=\"").append(selected).append("\"");
									}
								}
								ret.append(">");
								ret.append("<option value=\"\"></option>");
								Iterator<ItemAware> it4 = throwmsg.listMessageItemAwares();
								while (it4.hasNext()) {
									ItemAware ritemAware = it4.next();
									if (ritemAware.getDataObjectClass() != null && itemAware.getDataObjectClass().equals(ritemAware.getDataObjectClass())) {
										ret.append("<option value=\"" + ritemAware.getShortURI() + "\">");
										ret.append(ritemAware.getName());
										ret.append("</option>");
									}
								}
								ret.append("</select>");
								ret.append("</td>");

								ret.append("<td>");
								ret.append("->");
								ret.append(itemAware.getName());
								ret.append("</td>");

								ret.append("</tr>");
							}
						}
						ret.append("</table>");
					}
				}
			}
		}

		return ret.toString();
	}
}
