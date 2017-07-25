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
 *  http://www.semanticwebbuilder.org.mx
 */
package org.semanticwb.process.model;

import org.semanticwb.model.User;

/**
 * Clase que encapsula las propiedades y comportamiento de un objeto de conexión
 * en un modelo de procesos.
 * 
 * @author Javier Solís
 * @author Hasdai Pacheco
 *
 */
public class ConnectionObject extends org.semanticwb.process.model.base.ConnectionObjectBase {
	/**
	 * Constructor.
	 * 
	 * @param base
	 */
	public ConnectionObject(org.semanticwb.platform.SemanticObject base) {
		super(base);
	}

	/**
	 * Ejecuta las acciones configuradas del objeto de conexión y pasa el flujo a
	 * los elementos correspondientes.
	 * 
	 * @param source
	 *            {@link FlowNodeInstance} que inicia el flujo.
	 * @param user
	 *            {@link User} que ejecuta la instancia del {@link FlowNodeInstance}
	 */
	public void execute(FlowNodeInstance source, User user) {
		// Implementar en subclases
	}

	/**
	 * Evalúa las reglas de negocio o condiciones asociadas al objeto de conexión.
	 * 
	 * @param source
	 *            {@link FlowNodeInstance} que inicia el flujo.
	 * @param user
	 *            {@link User} que ejecuta la instancia del {@link FlowNodeInstance}
	 * @return true si se cumplen las reglas de negocio o condiciones asociadas.
	 */
	public boolean evaluate(FlowNodeInstance source, User user) {
		return true;
	}

	@Override
	public String serialize(String format) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
