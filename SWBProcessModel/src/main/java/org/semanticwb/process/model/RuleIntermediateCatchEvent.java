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

import java.util.Iterator;

import org.semanticwb.model.User;
import org.semanticwb.platform.SemanticObject;

/**
 * Clase que encapsula las propiedades y comportamiento de un evento intermedio receptor de de regla de negocio.
 * @author Javier Solís
 * @author Hasdai Pacheco
 *
 */
public class RuleIntermediateCatchEvent extends org.semanticwb.process.model.base.RuleIntermediateCatchEventBase 
{
	/**
	 * Constructor.
	 * @param base
	 */
    public RuleIntermediateCatchEvent(SemanticObject base)
    {
        super(base);
    }

    @Override
    public void execute(FlowNodeInstance instance, User user)
    {
        boolean cond=false;
        Iterator<ProcessRuleRef> it=listProcessRuleRefs();
        while (it.hasNext())
        {
            ProcessRuleRef ref = it.next();
            if(ref.isActive())
            {
                ProcessRule rule=ref.getProcessRule();
                if(rule.evaluate(instance, user))
                {
                    cond = true;
                    break;
                }
            }
        }

        if(cond)
        {
            instance.close(user);
            if(isInterruptor())
            {
                GraphicalElement parent=getParent();
                if(parent!=null)
                {
                    FlowNodeInstance source=instance.getRelatedFlowNodeInstance((FlowNode)parent);
                    source.close(user, Instance.STATUS_CLOSED, Instance.ACTION_EVENT, false);
                }
            }
        }else
        {
            ProcessObserver obs=instance.getProcessSite().getProcessObserver();
            if(!obs.hasRuleObserverInstance(instance))
            {
                obs.addRuleObserverInstance(instance);
            }
        }
    }

    @Override
    public void close(FlowNodeInstance instance, User user)
    {
        super.close(instance, user);
        ProcessObserver obs=instance.getProcessSite().getProcessObserver();
        obs.removeRuleObserverInstance(instance);
    }
}
