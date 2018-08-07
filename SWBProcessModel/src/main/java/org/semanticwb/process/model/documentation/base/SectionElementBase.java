package org.semanticwb.process.model.documentation.base;


public abstract class SectionElementBase extends org.semanticwb.process.model.documentation.DocumentationElement implements org.semanticwb.model.Sortable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swp_ProcessElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ProcessElement");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_hasProcessElementScope=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#hasProcessElementScope");
    public static final org.semanticwb.platform.SemanticClass swpdoc_DocumentSectionInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#DocumentSectionInstance");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_documentSectionInst=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#documentSectionInst");
    public static final org.semanticwb.platform.SemanticClass swpdoc_DocumentSection=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#DocumentSection");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_parentSection=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#parentSection");
   /**
   * Plantilla de documentacion de procesos
   */
    public static final org.semanticwb.platform.SemanticClass swpdoc_DocumentTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#DocumentTemplate");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_documentTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#documentTemplate");
    public static final org.semanticwb.platform.SemanticClass swpdoc_SectionElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#SectionElement");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#SectionElement");

    public static class ClassMgr
    {
       /**
       * Returns a list of SectionElement for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.documentation.SectionElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.SectionElement> listSectionElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.SectionElement>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.documentation.SectionElement for all models
       * @return Iterator of org.semanticwb.process.model.documentation.SectionElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.SectionElement> listSectionElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.SectionElement>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.model.documentation.SectionElement
       * @param id Identifier for org.semanticwb.process.model.documentation.SectionElement
       * @param model Model of the org.semanticwb.process.model.documentation.SectionElement
       * @return A org.semanticwb.process.model.documentation.SectionElement
       */
        public static org.semanticwb.process.model.documentation.SectionElement getSectionElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.SectionElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.documentation.SectionElement
       * @param id Identifier for org.semanticwb.process.model.documentation.SectionElement
       * @param model Model of the org.semanticwb.process.model.documentation.SectionElement
       * @return A org.semanticwb.process.model.documentation.SectionElement
       */
        public static org.semanticwb.process.model.documentation.SectionElement createSectionElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.SectionElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.documentation.SectionElement
       * @param id Identifier for org.semanticwb.process.model.documentation.SectionElement
       * @param model Model of the org.semanticwb.process.model.documentation.SectionElement
       */
        public static void removeSectionElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.documentation.SectionElement
       * @param id Identifier for org.semanticwb.process.model.documentation.SectionElement
       * @param model Model of the org.semanticwb.process.model.documentation.SectionElement
       * @return true if the org.semanticwb.process.model.documentation.SectionElement exists, false otherwise
       */

        public static boolean hasSectionElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSectionElement(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.SectionElement with a determined ProcessElementScope
       * @param value ProcessElementScope of the type org.semanticwb.process.model.ProcessElement
       * @param model Model of the org.semanticwb.process.model.documentation.SectionElement
       * @return Iterator with all the org.semanticwb.process.model.documentation.SectionElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.SectionElement> listSectionElementByProcessElementScope(org.semanticwb.process.model.ProcessElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.SectionElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasProcessElementScope, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.SectionElement with a determined ProcessElementScope
       * @param value ProcessElementScope of the type org.semanticwb.process.model.ProcessElement
       * @return Iterator with all the org.semanticwb.process.model.documentation.SectionElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.SectionElement> listSectionElementByProcessElementScope(org.semanticwb.process.model.ProcessElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.SectionElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasProcessElementScope,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.SectionElement with a determined DocumentSectionInst
       * @param value DocumentSectionInst of the type org.semanticwb.process.model.documentation.DocumentSectionInstance
       * @param model Model of the org.semanticwb.process.model.documentation.SectionElement
       * @return Iterator with all the org.semanticwb.process.model.documentation.SectionElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.SectionElement> listSectionElementByDocumentSectionInst(org.semanticwb.process.model.documentation.DocumentSectionInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.SectionElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentSectionInst, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.SectionElement with a determined DocumentSectionInst
       * @param value DocumentSectionInst of the type org.semanticwb.process.model.documentation.DocumentSectionInstance
       * @return Iterator with all the org.semanticwb.process.model.documentation.SectionElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.SectionElement> listSectionElementByDocumentSectionInst(org.semanticwb.process.model.documentation.DocumentSectionInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.SectionElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentSectionInst,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.SectionElement with a determined ParentSection
       * @param value ParentSection of the type org.semanticwb.process.model.documentation.DocumentSection
       * @param model Model of the org.semanticwb.process.model.documentation.SectionElement
       * @return Iterator with all the org.semanticwb.process.model.documentation.SectionElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.SectionElement> listSectionElementByParentSection(org.semanticwb.process.model.documentation.DocumentSection value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.SectionElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_parentSection, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.SectionElement with a determined ParentSection
       * @param value ParentSection of the type org.semanticwb.process.model.documentation.DocumentSection
       * @return Iterator with all the org.semanticwb.process.model.documentation.SectionElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.SectionElement> listSectionElementByParentSection(org.semanticwb.process.model.documentation.DocumentSection value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.SectionElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_parentSection,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.SectionElement with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.SectionElement
       * @return Iterator with all the org.semanticwb.process.model.documentation.SectionElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.SectionElement> listSectionElementByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.SectionElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.SectionElement with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.SectionElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.SectionElement> listSectionElementByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.SectionElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.SectionElement with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.SectionElement
       * @return Iterator with all the org.semanticwb.process.model.documentation.SectionElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.SectionElement> listSectionElementByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.SectionElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.SectionElement with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.SectionElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.SectionElement> listSectionElementByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.SectionElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.SectionElement with a determined DocumentTemplate
       * @param value DocumentTemplate of the type org.semanticwb.process.model.documentation.DocumentTemplate
       * @param model Model of the org.semanticwb.process.model.documentation.SectionElement
       * @return Iterator with all the org.semanticwb.process.model.documentation.SectionElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.SectionElement> listSectionElementByDocumentTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.SectionElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.SectionElement with a determined DocumentTemplate
       * @param value DocumentTemplate of the type org.semanticwb.process.model.documentation.DocumentTemplate
       * @return Iterator with all the org.semanticwb.process.model.documentation.SectionElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.SectionElement> listSectionElementByDocumentTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.SectionElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentTemplate,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SectionElementBase.ClassMgr getSectionElementClassMgr()
    {
        return new SectionElementBase.ClassMgr();
    }

   /**
   * Constructs a SectionElementBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SectionElement
   */
    public SectionElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.process.model.ProcessElement
   * @return A GenericIterator with all the org.semanticwb.process.model.ProcessElement
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessElement> listProcessElementScopes()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.ProcessElement>(getSemanticObject().listObjectProperties(swpdoc_hasProcessElementScope));
    }

   /**
   * Gets true if has a ProcessElementScope
   * @param value org.semanticwb.process.model.ProcessElement to verify
   * @return true if the org.semanticwb.process.model.ProcessElement exists, false otherwise
   */
    public boolean hasProcessElementScope(org.semanticwb.process.model.ProcessElement value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swpdoc_hasProcessElementScope,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a ProcessElementScope
   * @param value org.semanticwb.process.model.ProcessElement to add
   */

    public void addProcessElementScope(org.semanticwb.process.model.ProcessElement value)
    {
        getSemanticObject().addObjectProperty(swpdoc_hasProcessElementScope, value.getSemanticObject());
    }
   /**
   * Removes all the ProcessElementScope
   */

    public void removeAllProcessElementScope()
    {
        getSemanticObject().removeProperty(swpdoc_hasProcessElementScope);
    }
   /**
   * Removes a ProcessElementScope
   * @param value org.semanticwb.process.model.ProcessElement to remove
   */

    public void removeProcessElementScope(org.semanticwb.process.model.ProcessElement value)
    {
        getSemanticObject().removeObjectProperty(swpdoc_hasProcessElementScope,value.getSemanticObject());
    }

   /**
   * Gets the ProcessElementScope
   * @return a org.semanticwb.process.model.ProcessElement
   */
    public org.semanticwb.process.model.ProcessElement getProcessElementScope()
    {
         org.semanticwb.process.model.ProcessElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_hasProcessElementScope);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.ProcessElement)obj.createGenericInstance();
         }
         return ret;
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
   * Sets the value for the property DocumentSectionInst
   * @param value DocumentSectionInst to set
   */

    public void setDocumentSectionInst(org.semanticwb.process.model.documentation.DocumentSectionInstance value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swpdoc_documentSectionInst, value.getSemanticObject());
        }else
        {
            removeDocumentSectionInst();
        }
    }
   /**
   * Remove the value for DocumentSectionInst property
   */

    public void removeDocumentSectionInst()
    {
        getSemanticObject().removeProperty(swpdoc_documentSectionInst);
    }

   /**
   * Gets the DocumentSectionInst
   * @return a org.semanticwb.process.model.documentation.DocumentSectionInstance
   */
    public org.semanticwb.process.model.documentation.DocumentSectionInstance getDocumentSectionInst()
    {
         org.semanticwb.process.model.documentation.DocumentSectionInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_documentSectionInst);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.documentation.DocumentSectionInstance)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property ParentSection
   * @param value ParentSection to set
   */

    public void setParentSection(org.semanticwb.process.model.documentation.DocumentSection value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swpdoc_parentSection, value.getSemanticObject());
        }else
        {
            removeParentSection();
        }
    }
   /**
   * Remove the value for ParentSection property
   */

    public void removeParentSection()
    {
        getSemanticObject().removeProperty(swpdoc_parentSection);
    }

   /**
   * Gets the ParentSection
   * @return a org.semanticwb.process.model.documentation.DocumentSection
   */
    public org.semanticwb.process.model.documentation.DocumentSection getParentSection()
    {
         org.semanticwb.process.model.documentation.DocumentSection ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_parentSection);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.documentation.DocumentSection)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property DocumentTemplate
   * @param value DocumentTemplate to set
   */

    public void setDocumentTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swpdoc_documentTemplate, value.getSemanticObject());
        }else
        {
            removeDocumentTemplate();
        }
    }
   /**
   * Remove the value for DocumentTemplate property
   */

    public void removeDocumentTemplate()
    {
        getSemanticObject().removeProperty(swpdoc_documentTemplate);
    }

   /**
   * Gets the DocumentTemplate
   * @return a org.semanticwb.process.model.documentation.DocumentTemplate
   */
    public org.semanticwb.process.model.documentation.DocumentTemplate getDocumentTemplate()
    {
         org.semanticwb.process.model.documentation.DocumentTemplate ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_documentTemplate);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.documentation.DocumentTemplate)obj.createGenericInstance();
         }
         return ret;
    }
}
