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

import org.semanticwb.Logger;
import org.semanticwb.SWBPlatform;
import org.semanticwb.SWBUtils;
import org.semanticwb.model.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;


public class SparQLQuery extends org.semanticwb.process.model.base.SparQLQueryBase 
{
    /** The log. */
    private Logger LOG=SWBUtils.getLogger(SparQLQuery.class);

    public SparQLQuery(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    @Override
    public void execute(FlowNodeInstance instance, User user) {
        super.execute(instance, user);
        String q = getQuery();

        try
        {
            Document dom = SWBUtils.XML.getNewDocument();
            Element node = dom.createElement("resultset");
                    dom.appendChild(node);
            if(q!=null&&q.length()>0)
            {
                Model model=SWBPlatform.getSemanticMgr().getOntology().getRDFOntModel();
                String queryString = q;

                Query query = QueryFactory.create(queryString) ;
                query.serialize();
                // Create a single execution of this query, apply to a model
                // which is wrapped up as a Dataset
                QueryExecution qexec = QueryExecutionFactory.create(query, model) ;
                // Or QueryExecutionFactory.create(queryString, model) ;
                try {
                    // Assumption: it's a SELECT query.
                    ResultSet rs = qexec.execSelect() ;
                    

                    // The order of results is undefined.
                    for ( ; rs.hasNext() ; )
                    {
                        QuerySolution rb = rs.nextSolution() ;

                        Element nodeRow = dom.createElement("row");
                        node.appendChild(nodeRow);

                        Iterator<String> it=rs.getResultVars().iterator();
                        while(it.hasNext())
                        {
                            Element nodeColumn = dom.createElement("column");
                            String name=it.next();
                            RDFNode x = rb.get(name) ;
                            nodeColumn.setAttribute("name", name);
                            nodeColumn.setNodeValue(x!=null?x.toString():" - ");
                            nodeRow.appendChild(nodeColumn);
                        }
                    }
                }
                finally
                {
                    // QueryExecution objects should be closed to free any system resources
                    qexec.close() ;
                }
            }
        }catch(Exception e)
        {
            LOG.error("Error en la expresion SPARQL.",e);
        }        
    }
}
