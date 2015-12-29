package org.semanticwb.process.documentation.model;

import java.util.Iterator;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.process.model.Process;

/**
 * Clase que encapsula las propiedades de la documentación de un proceso.
 * Es el punto de entrada del componente y el mecanismo para relacionar un proceso con su documentación.
 */
public class Documentation extends org.semanticwb.process.documentation.model.base.DocumentationBase 
{
    /**
     * Constructor. Crea un nuevo objeto para administrar la documentación de un proceso.
     * @param base 
     */
    public Documentation(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    /**
     * Obtiene la instancia de la documentación asociada al proceso.
     * @return Instancia de documentación asociada al proceso.
     */
    public DocumentationInstance getDocumentationInstance() {
        Iterator<DocumentationInstance> itdi = DocumentationInstance.ClassMgr.listDocumentationInstanceByProcessRef(getProcess(), getProcess().getProcessSite());
        if (itdi != null && itdi.hasNext()) {
            return itdi.next();
        }
        return null;
    }
    
    /**
     * Obtiene la versión actual de la documentación de un proceso.
     *
     * @param process Proceso del cual se requiere obtener la versión actual de
     * la documentación.
     * @return Versión actual de la documentación o null si esta no existe.
     */
    public static Documentation getActualDocumentationVersion(Process process) {
        Iterator<Documentation> itdoc = SWBComparator.sortByCreated(ClassMgr.listDocumentationByProcess(process), true);
        while (itdoc.hasNext()) {
            Documentation doc = itdoc.next();
            if (doc.isActualVersion()) {
                return doc;
            }
        }
        return null;
    }
}
