package org.semanticwb.process.model.documentation;


import org.semanticwb.SWBPlatform;
import org.semanticwb.model.WebSite;
import org.semanticwb.platform.SemanticClass;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SectionElement extends org.semanticwb.process.model.documentation.base.SectionElementBase
{
    public SectionElement(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /**
     * Lista los elementos asociados a una instancia de sección en la documentación.
     * @param template Plantilla de documentación asociada.
     * @param model Sitio de procesos.
     * @param scls Nombre de clase
     * @param instance Instancia de Sección que contiene los elementos.
     * @return Lista de elementos de la sección asociados a una plantilla.
     */
    public static List<SectionElement> listSectionElementByTemplate(DocumentTemplate template, WebSite model, SemanticClass scls, DocumentSectionInstance instance) {
        List<SectionElement> list = new ArrayList<>();
        if (template != null) {
            Iterator<SectionElement> itsee = SectionElement.ClassMgr.listSectionElementByDocumentTemplate(template, model);
            while (itsee.hasNext()) {
                SectionElement see = itsee.next();
                SemanticClass sclst = SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass(see.getParentSection().getSectionType().getURI());
                if (scls.getClassId().equals(sclst.getClassId())) {
                    if (see.getDocumentSectionInst().getURI().equals(instance.getURI())
                            || (see instanceof ElementReference)) {
                        continue;
                    }
                    list.add(see);
                }
            }
        }
        return list;
    }
}
