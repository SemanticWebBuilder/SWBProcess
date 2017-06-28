package org.semanticwb.process.documentation.model;

import org.semanticwb.model.WebSite;

/**
 * Clase que encapsula la información de una sección de documentación.
 */
public class DocumentSection extends org.semanticwb.process.documentation.model.base.DocumentSectionBase 
{
    /**
     * Constructor. Crea una nueva sección de documentación.
     * @param base 
     */
    public DocumentSection(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
    
    /**
     * Crea una copia del DocumentSection
     * @return DocumentSection
     */
    public DocumentSection cloneDocumentSection() {
        DocumentSection ret = null;
        WebSite model = (WebSite)getSemanticObject().getModel().getModelObject().getGenericInstance();
        
        if (null != model) {
            ret = DocumentSection.ClassMgr.createDocumentSection(model);
            ret.setConfigData(getConfigData());
            ret.setIndex(getIndex());
            ret.setSectionType(getSectionType());
            ret.setTitle(getTitle());
            ret.setVisibleProperties(getVisibleProperties());
            ret.setActive(isActive());
        }
        
        return ret;
    }
}
