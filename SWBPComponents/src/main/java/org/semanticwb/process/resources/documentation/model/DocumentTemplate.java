package org.semanticwb.process.resources.documentation.model;

import java.text.NumberFormat;
import java.util.Iterator;
import org.semanticwb.model.SWBComparator;
import org.semanticwb.model.WebSite;

/**
 * Clase que encapsula la información de una plantilla para documentación.
 */
public class DocumentTemplate extends org.semanticwb.process.resources.documentation.model.base.DocumentTemplateBase
{
    private static final NumberFormat numf = NumberFormat.getNumberInstance();
    /**
     * Constructor. Crea una nueva instancia de una plantilla de documentación.
     * @param base 
     */
    public DocumentTemplate(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    /**
     * Obtiene el valor de la siguiente versión, de acuerdo al valor proporcionado.
     * @param prevValue Valor de versión tomado como punto de partida.
     * @return Número de la siguiente versión.
     */
    public static String getNextVersionValue(String prevValue) {
        String prev = prevValue;
        if (null == prevValue || prevValue.isEmpty()) {
            return "1.0";
        }
        
        numf.setMaximumFractionDigits(1);

        String sver = prev;
        double f = Double.parseDouble(sver);
        f = f + 0.10D;

        String sfver = numf.format(f);
        if (!sfver.contains(".")) {
            sfver = "" + (float) f;
        }
        sver = sfver;
        return sver;
    }

    @Override
    public void addDocumentSection(DocumentSection value) {
        super.addDocumentSection(value);
        if (null != value) value.setParentTemplate(this);
    }
    
    /**
     * Crea una copia de la plantilla.
     * @return 
     */
    public DocumentTemplate cloneTemplate() {
        DocumentTemplate ret = null;
        WebSite model = (WebSite)getSemanticObject().getModel().getModelObject().getGenericInstance();
        
        if (null != model) {
            ret = DocumentTemplate.ClassMgr.createDocumentTemplate(model);
             
            //Set common properties
            ret.setTitle(getTitle());
            ret.setVersionValue(getNextVersionValue(null));
            ret.setDescription(getDescription());

            //Clone section elements
            Iterator<DocumentSection> sections = SWBComparator.sortSortableObject(listDocumentSections());
            while (sections.hasNext()) {
                DocumentSection section = sections.next();
                DocumentSection cloned = section.cloneDocumentSection();
                //cloned.setParentTemplate(ret);
                ret.addDocumentSection(cloned);
            }
        }
        return ret;
    }
}
