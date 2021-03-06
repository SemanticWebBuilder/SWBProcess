package org.semanticwb.process.model.documentation;


import org.semanticwb.model.WebSite;
import org.semanticwb.process.model.Process;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
   * Contenedor para versionamiento de plantillas de documentacion 
   */
public class TemplateContainer extends org.semanticwb.process.model.documentation.base.TemplateContainerBase 
{
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
        return listAllAvailableProcesses(model, this);
    }

    /**
     * Obtiene la lista de los proecsos que no están asignados a un {@code TemplateContainer}.
     * @param model Modelo
     * @param skipContainer Objeto para omitir en el filtrado. Si se proporciona, los procesos asociados al objeto no son filtrados de la lista de asignados.
     * @return Lista de procesos sin asignar a algún {@code TemplateContainer}.
     */
    public static List<Process> listAllAvailableProcesses(WebSite model, TemplateContainer skipContainer) {
        ArrayList<Process> assigned = new ArrayList<>();
        //Get all containers
        Iterator<TemplateContainer> containers = ClassMgr.listTemplateContainers(model);
        while (containers.hasNext()) {
            TemplateContainer container = containers.next();
            //Get processes asigned to container and add them to assigned list
            Iterator<Process> processes = container.listProcesses();
            while (processes.hasNext()) {
                Process process = processes.next();
                if (null != skipContainer && container.getURI().equals(skipContainer.getURI())) {
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
            if (p.isActive() && !p.isDeleted() && !assigned.contains(p)) { //all remaining processes must be available, but deleted ones
                available.add(p);
            }
        }
        return available;
    }

    @Override
    public void addTemplate(DocumentTemplate value) {
        super.addTemplate(value);
        if (null != value) value.setTemplateContainer(this);
    }
}
