/* SemanticWebBuilder es una plataforma para el desarrollo de portales y aplicaciones de integración,
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

import javax.servlet.http.HttpServletRequest;

import org.semanticwb.model.DisplayProperty;
import org.semanticwb.platform.SemanticObject;
import org.semanticwb.platform.SemanticProperty;

public class WikiUrlFormElement extends org.semanticwb.process.model.base.WikiUrlFormElementBase {
	public WikiUrlFormElement(org.semanticwb.platform.SemanticObject base) {
		super(base);
	}

	@Override
	public String renderElement(HttpServletRequest request, SemanticObject obj, SemanticProperty prop, String propName,
			String type, String mode, String lang) {
		
		SemanticObject sobje = obj;
		if (sobje == null) {
			sobje = new SemanticObject();
		}

		boolean isDojo = type.equals("dojo");
		StringBuilder ret = new StringBuilder();
		String name = propName;
		String label = prop.getDisplayName(lang);
		SemanticObject sobj = prop.getDisplayProperty();
		boolean required = prop.isRequired();
		String pmsg = null;
		String imsg = null;
		boolean disabled = false;

		if (sobj != null) {
			DisplayProperty dobj = new DisplayProperty(sobj);

			pmsg = dobj.getPromptMessage();
			imsg = dobj.getInvalidMessage();
			disabled = dobj.isDisabled();
		}

		if (isDojo) {
			if (imsg == null || (imsg != null && imsg.trim().length() == 0)) {
				if (required) {
					imsg = label + " es requerido.";

					if (lang.equals("en")) {
						imsg = label + " is required.";
					}
				} else {
					imsg = label + " no es válido";
					if (lang.equals("en")) {
						imsg = label + " is not valid";
					}
				}
			}

			if (pmsg == null) {
				pmsg = "Captura " + label + ".";

				if (lang.equals("en")) {
					pmsg = "Enter " + label + ".";
				}
			}
		}

		String ext = "";

		if (disabled) {
			ext += " disabled=\"disabled\"";
		}

		String value = request.getParameter(propName);

		if (value == null) {
			value = sobje.getProperty(prop);
		}

		if (value == null) {
			value = "";
		}

		value = value.replace("\"", "&quot;");

		if (mode.equals("edit") || mode.equals("create")) {
			ret.append("<input name=\"" + name + "\" size=\"30\" value=\"" + value + "\"");

			if (isDojo) {
				ret.append(" dojoType=\"dijit.form.ValidationTextBox\"");
				ret.append(" required=\"" + required + "\"");
				ret.append(" promptMessage=\"" + pmsg + "\"");
				ret.append(((getRegExp() != null) ? (" regExp=\"" + getRegExp() + "\"") : ""));
				ret.append(" invalidMessage=\"" + imsg + "\"");
			}

			ret.append(" " + getAttributes());

			if (isDojo) {
				ret.append(" trim=\"true\"");
			}

			ret.append(" style=\"width:300px;\"");
			ret.append(ext);
			ret.append("/>");

		} else if (mode.equals("view")) {
			String lbl = value;
			String url = value;
			if (value.startsWith("[") && value.endsWith("]")) {
				String[] temp = value.replaceAll("\\[", "").replaceAll("\\]", "").split("\\|");
				if (temp != null && temp.length == 2) {
					url = temp[0];
					lbl = temp[1];
				}
			}
			ret.append("<a href=\"" + url + "\">" + lbl + "</a>");
		}
		return ret.toString();
	}
}