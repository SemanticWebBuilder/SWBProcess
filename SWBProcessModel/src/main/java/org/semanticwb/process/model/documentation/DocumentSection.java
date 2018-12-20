package org.semanticwb.process.model.documentation;


import org.semanticwb.model.WebSite;

public class DocumentSection extends org.semanticwb.process.model.documentation.base.DocumentSectionBase
{
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
