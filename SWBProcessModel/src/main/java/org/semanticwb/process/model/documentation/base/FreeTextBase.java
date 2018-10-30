package org.semanticwb.process.model.documentation.base;


public abstract class FreeTextBase extends org.semanticwb.process.model.documentation.SectionElement implements org.semanticwb.model.Sortable,org.semanticwb.process.model.documentation.ElementReferable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty swpdoc_text=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#text");
    public static final org.semanticwb.platform.SemanticClass swpdoc_FreeText=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#FreeText");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#FreeText");

    public static class ClassMgr
    {
       /**
       * Returns a list of FreeText for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.documentation.FreeText
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.FreeText> listFreeTexts(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.FreeText>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.documentation.FreeText for all models
       * @return Iterator of org.semanticwb.process.model.documentation.FreeText
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.FreeText> listFreeTexts()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.FreeText>(it, true);
        }

        public static org.semanticwb.process.model.documentation.FreeText createFreeText(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.documentation.FreeText.ClassMgr.createFreeText(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.documentation.FreeText
       * @param id Identifier for org.semanticwb.process.model.documentation.FreeText
       * @param model Model of the org.semanticwb.process.model.documentation.FreeText
       * @return A org.semanticwb.process.model.documentation.FreeText
       */
        public static org.semanticwb.process.model.documentation.FreeText getFreeText(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.FreeText)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.documentation.FreeText
       * @param id Identifier for org.semanticwb.process.model.documentation.FreeText
       * @param model Model of the org.semanticwb.process.model.documentation.FreeText
       * @return A org.semanticwb.process.model.documentation.FreeText
       */
        public static org.semanticwb.process.model.documentation.FreeText createFreeText(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.FreeText)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.documentation.FreeText
       * @param id Identifier for org.semanticwb.process.model.documentation.FreeText
       * @param model Model of the org.semanticwb.process.model.documentation.FreeText
       */
        public static void removeFreeText(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.documentation.FreeText
       * @param id Identifier for org.semanticwb.process.model.documentation.FreeText
       * @param model Model of the org.semanticwb.process.model.documentation.FreeText
       * @return true if the org.semanticwb.process.model.documentation.FreeText exists, false otherwise
       */

        public static boolean hasFreeText(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFreeText(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.FreeText with a determined ProcessElementScope
       * @param value ProcessElementScope of the type org.semanticwb.process.model.ProcessElement
       * @param model Model of the org.semanticwb.process.model.documentation.FreeText
       * @return Iterator with all the org.semanticwb.process.model.documentation.FreeText
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.FreeText> listFreeTextByProcessElementScope(org.semanticwb.process.model.ProcessElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.FreeText> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasProcessElementScope, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.FreeText with a determined ProcessElementScope
       * @param value ProcessElementScope of the type org.semanticwb.process.model.ProcessElement
       * @return Iterator with all the org.semanticwb.process.model.documentation.FreeText
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.FreeText> listFreeTextByProcessElementScope(org.semanticwb.process.model.ProcessElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.FreeText> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasProcessElementScope,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.FreeText with a determined DocumentSectionInst
       * @param value DocumentSectionInst of the type org.semanticwb.process.model.documentation.DocumentSectionInstance
       * @param model Model of the org.semanticwb.process.model.documentation.FreeText
       * @return Iterator with all the org.semanticwb.process.model.documentation.FreeText
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.FreeText> listFreeTextByDocumentSectionInst(org.semanticwb.process.model.documentation.DocumentSectionInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.FreeText> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentSectionInst, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.FreeText with a determined DocumentSectionInst
       * @param value DocumentSectionInst of the type org.semanticwb.process.model.documentation.DocumentSectionInstance
       * @return Iterator with all the org.semanticwb.process.model.documentation.FreeText
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.FreeText> listFreeTextByDocumentSectionInst(org.semanticwb.process.model.documentation.DocumentSectionInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.FreeText> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentSectionInst,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.FreeText with a determined ParentSection
       * @param value ParentSection of the type org.semanticwb.process.model.documentation.DocumentSection
       * @param model Model of the org.semanticwb.process.model.documentation.FreeText
       * @return Iterator with all the org.semanticwb.process.model.documentation.FreeText
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.FreeText> listFreeTextByParentSection(org.semanticwb.process.model.documentation.DocumentSection value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.FreeText> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_parentSection, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.FreeText with a determined ParentSection
       * @param value ParentSection of the type org.semanticwb.process.model.documentation.DocumentSection
       * @return Iterator with all the org.semanticwb.process.model.documentation.FreeText
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.FreeText> listFreeTextByParentSection(org.semanticwb.process.model.documentation.DocumentSection value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.FreeText> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_parentSection,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.FreeText with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.FreeText
       * @return Iterator with all the org.semanticwb.process.model.documentation.FreeText
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.FreeText> listFreeTextByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.FreeText> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.FreeText with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.FreeText
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.FreeText> listFreeTextByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.FreeText> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.FreeText with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.FreeText
       * @return Iterator with all the org.semanticwb.process.model.documentation.FreeText
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.FreeText> listFreeTextByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.FreeText> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.FreeText with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.FreeText
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.FreeText> listFreeTextByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.FreeText> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.FreeText with a determined DocumentTemplate
       * @param value DocumentTemplate of the type org.semanticwb.process.model.documentation.DocumentTemplate
       * @param model Model of the org.semanticwb.process.model.documentation.FreeText
       * @return Iterator with all the org.semanticwb.process.model.documentation.FreeText
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.FreeText> listFreeTextByDocumentTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.FreeText> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.FreeText with a determined DocumentTemplate
       * @param value DocumentTemplate of the type org.semanticwb.process.model.documentation.DocumentTemplate
       * @return Iterator with all the org.semanticwb.process.model.documentation.FreeText
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.FreeText> listFreeTextByDocumentTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.FreeText> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentTemplate,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static FreeTextBase.ClassMgr getFreeTextClassMgr()
    {
        return new FreeTextBase.ClassMgr();
    }

   /**
   * Constructs a FreeTextBase with a SemanticObject
   * @param base The SemanticObject with the properties for the FreeText
   */
    public FreeTextBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Text property
* @return String with the Text
*/
    public String getText()
    {
        return getSemanticObject().getProperty(swpdoc_text);
    }

/**
* Sets the Text property
* @param value long with the Text
*/
    public void setText(String value)
    {
        getSemanticObject().setProperty(swpdoc_text, value);
    }
}
