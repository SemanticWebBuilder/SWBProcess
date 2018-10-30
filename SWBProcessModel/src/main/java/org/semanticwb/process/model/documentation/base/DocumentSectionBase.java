package org.semanticwb.process.model.documentation.base;


public abstract class DocumentSectionBase extends org.semanticwb.process.model.documentation.DocumentationElement implements org.semanticwb.process.model.documentation.Prefixeable,org.semanticwb.model.Sortable,org.semanticwb.model.Traceable,org.semanticwb.model.Activeable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty swpdoc_configData=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#configData");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_visibleProperties=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#visibleProperties");
    public static final org.semanticwb.platform.SemanticClass swpdoc_SectionElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#SectionElement");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_hasSectionElementInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#hasSectionElementInv");
   /**
   * Plantilla de documentacion de procesos
   */
    public static final org.semanticwb.platform.SemanticClass swpdoc_DocumentTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#DocumentTemplate");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_parentTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#parentTemplate");
    public static final org.semanticwb.platform.SemanticClass swb_Class=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Class");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_sectionType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#sectionType");
    public static final org.semanticwb.platform.SemanticClass swpdoc_DocumentSection=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#DocumentSection");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#DocumentSection");

    public static class ClassMgr
    {
       /**
       * Returns a list of DocumentSection for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.documentation.DocumentSection
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentSection> listDocumentSections(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentSection>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.documentation.DocumentSection for all models
       * @return Iterator of org.semanticwb.process.model.documentation.DocumentSection
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentSection> listDocumentSections()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentSection>(it, true);
        }

        public static org.semanticwb.process.model.documentation.DocumentSection createDocumentSection(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.documentation.DocumentSection.ClassMgr.createDocumentSection(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.documentation.DocumentSection
       * @param id Identifier for org.semanticwb.process.model.documentation.DocumentSection
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentSection
       * @return A org.semanticwb.process.model.documentation.DocumentSection
       */
        public static org.semanticwb.process.model.documentation.DocumentSection getDocumentSection(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.DocumentSection)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.documentation.DocumentSection
       * @param id Identifier for org.semanticwb.process.model.documentation.DocumentSection
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentSection
       * @return A org.semanticwb.process.model.documentation.DocumentSection
       */
        public static org.semanticwb.process.model.documentation.DocumentSection createDocumentSection(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.DocumentSection)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.documentation.DocumentSection
       * @param id Identifier for org.semanticwb.process.model.documentation.DocumentSection
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentSection
       */
        public static void removeDocumentSection(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.documentation.DocumentSection
       * @param id Identifier for org.semanticwb.process.model.documentation.DocumentSection
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentSection
       * @return true if the org.semanticwb.process.model.documentation.DocumentSection exists, false otherwise
       */

        public static boolean hasDocumentSection(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDocumentSection(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentSection with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentSection
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentSection
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentSection> listDocumentSectionByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentSection> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentSection with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentSection
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentSection> listDocumentSectionByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentSection> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentSection with a determined SectionElement
       * @param value SectionElement of the type org.semanticwb.process.model.documentation.SectionElement
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentSection
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentSection
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentSection> listDocumentSectionBySectionElement(org.semanticwb.process.model.documentation.SectionElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentSection> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasSectionElementInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentSection with a determined SectionElement
       * @param value SectionElement of the type org.semanticwb.process.model.documentation.SectionElement
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentSection
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentSection> listDocumentSectionBySectionElement(org.semanticwb.process.model.documentation.SectionElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentSection> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasSectionElementInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentSection with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentSection
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentSection
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentSection> listDocumentSectionByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentSection> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentSection with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentSection
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentSection> listDocumentSectionByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentSection> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentSection with a determined ParentTemplate
       * @param value ParentTemplate of the type org.semanticwb.process.model.documentation.DocumentTemplate
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentSection
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentSection
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentSection> listDocumentSectionByParentTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentSection> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_parentTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentSection with a determined ParentTemplate
       * @param value ParentTemplate of the type org.semanticwb.process.model.documentation.DocumentTemplate
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentSection
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentSection> listDocumentSectionByParentTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentSection> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_parentTemplate,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DocumentSectionBase.ClassMgr getDocumentSectionClassMgr()
    {
        return new DocumentSectionBase.ClassMgr();
    }

   /**
   * Constructs a DocumentSectionBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DocumentSection
   */
    public DocumentSectionBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the ConfigData property
* @return String with the ConfigData
*/
    public String getConfigData()
    {
        return getSemanticObject().getProperty(swpdoc_configData);
    }

/**
* Sets the ConfigData property
* @param value long with the ConfigData
*/
    public void setConfigData(String value)
    {
        getSemanticObject().setProperty(swpdoc_configData, value);
    }

/**
* Gets the Prefix property
* @return String with the Prefix
*/
    public String getPrefix()
    {
        return getSemanticObject().getProperty(swpdoc_prefix);
    }

/**
* Sets the Prefix property
* @param value long with the Prefix
*/
    public void setPrefix(String value)
    {
        getSemanticObject().setProperty(swpdoc_prefix, value);
    }

    public String getPrefix(String lang)
    {
        return getSemanticObject().getProperty(swpdoc_prefix, null, lang);
    }

    public String getDisplayPrefix(String lang)
    {
        return getSemanticObject().getLocaleProperty(swpdoc_prefix, lang);
    }

    public void setPrefix(String prefix, String lang)
    {
        getSemanticObject().setProperty(swpdoc_prefix, prefix, lang);
    }

/**
* Gets the Active property
* @return boolean with the Active
*/
    public boolean isActive()
    {
        return getSemanticObject().getBooleanProperty(swb_active);
    }

/**
* Sets the Active property
* @param value long with the Active
*/
    public void setActive(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_active, value);
    }

/**
* Gets the Index property
* @return int with the Index
*/
    public int getIndex()
    {
        return getSemanticObject().getIntProperty(swb_index);
    }

/**
* Sets the Index property
* @param value long with the Index
*/
    public void setIndex(int value)
    {
        getSemanticObject().setIntProperty(swb_index, value);
    }

/**
* Gets the VisibleProperties property
* @return String with the VisibleProperties
*/
    public String getVisibleProperties()
    {
        return getSemanticObject().getProperty(swpdoc_visibleProperties);
    }

/**
* Sets the VisibleProperties property
* @param value long with the VisibleProperties
*/
    public void setVisibleProperties(String value)
    {
        getSemanticObject().setProperty(swpdoc_visibleProperties, value);
    }
   /**
   * Gets all the org.semanticwb.process.model.documentation.SectionElement
   * @return A GenericIterator with all the org.semanticwb.process.model.documentation.SectionElement
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.SectionElement> listSectionElements()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.SectionElement>(getSemanticObject().listObjectProperties(swpdoc_hasSectionElementInv));
    }

   /**
   * Gets true if has a SectionElement
   * @param value org.semanticwb.process.model.documentation.SectionElement to verify
   * @return true if the org.semanticwb.process.model.documentation.SectionElement exists, false otherwise
   */
    public boolean hasSectionElement(org.semanticwb.process.model.documentation.SectionElement value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swpdoc_hasSectionElementInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the SectionElement
   * @return a org.semanticwb.process.model.documentation.SectionElement
   */
    public org.semanticwb.process.model.documentation.SectionElement getSectionElement()
    {
         org.semanticwb.process.model.documentation.SectionElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_hasSectionElementInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.documentation.SectionElement)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property ParentTemplate
   * @param value ParentTemplate to set
   */

    public void setParentTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swpdoc_parentTemplate, value.getSemanticObject());
        }else
        {
            removeParentTemplate();
        }
    }
   /**
   * Remove the value for ParentTemplate property
   */

    public void removeParentTemplate()
    {
        getSemanticObject().removeProperty(swpdoc_parentTemplate);
    }

   /**
   * Gets the ParentTemplate
   * @return a org.semanticwb.process.model.documentation.DocumentTemplate
   */
    public org.semanticwb.process.model.documentation.DocumentTemplate getParentTemplate()
    {
         org.semanticwb.process.model.documentation.DocumentTemplate ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_parentTemplate);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.documentation.DocumentTemplate)obj.createGenericInstance();
         }
         return ret;
    }

    public void setSectionType(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().setObjectProperty(swpdoc_sectionType, value);
    }

    public void removeSectionType()
    {
        getSemanticObject().removeProperty(swpdoc_sectionType);
    }

/**
* Gets the SectionType property
* @return the value for the property as org.semanticwb.platform.SemanticObject
*/
    public org.semanticwb.platform.SemanticObject getSectionType()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swpdoc_sectionType);
         return ret;
    }
}
