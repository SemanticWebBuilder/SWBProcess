package org.semanticwb.process.documentation.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.semanticwb.model.WebSite;
import org.semanticwb.process.model.Process;

/**
 * Clase que funciona como agrupador de las versiones de una plantilla de documentación de procesos.
 */
public class TemplateContainer extends org.semanticwb.process.documentation.model.base.TemplateContainerBase 
{
    /**
     * Constructor.
     * @param base 
     */
    public TemplateContainer(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    /**
     * Obtiene la lista de los procesos disponibles para el {@code TemplateContainer}, excluyendo aquellos que están asignados a otro {@code TemplateContainer}.
     *
     * @return Lista de procesos disponibles para el {@code TemplateContainer}
     */
    public List<org.semanticwb.process.model.Process> listAvailableProcesses() {
        WebSite model = (WebSite) this.getSemanticObject().getModel().getModelObject().getGenericInstance();
        ArrayList<Process> assigned = new ArrayList<>();
        //Get all containers
        Iterator<TemplateContainer> containers = ClassMgr.listTemplateContainers(model);
        while (containers.hasNext()) {
            TemplateContainer container = containers.next();
            //Get processes asigned to container and add them to assigned list
            Iterator<Process> processes = container.listProcesses();
            while (processes.hasNext()) {
                Process process = processes.next();
                if (container.getURI().equals(this.getURI())) { //Skip current container
                    continue;
                }
                assigned.add(process);
            }
        }
        
        //Filter all processes to exclude assigned ones
        List<Process> available = new ArrayList<>();
        Iterator<Process> all = Process.ClassMgr.listProcesses(model);
        while(all.hasNext()) {
            Process p = all.next();
            if (p.isValid() && !assigned.contains(p)) {
                available.add(p);
            }
        }
        return available;
    }
}
