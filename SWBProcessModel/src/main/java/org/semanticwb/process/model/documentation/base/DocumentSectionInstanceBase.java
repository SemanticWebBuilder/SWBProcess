package org.semanticwb.process.model.documentation.base;


public abstract class DocumentSectionInstanceBase extends org.semanticwb.process.model.documentation.DocumentationElement implements org.semanticwb.model.Sortable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swpdoc_SectionElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#SectionElement");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_hasDocuSectionElementInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#hasDocuSectionElementInstance");
    public static final org.semanticwb.platform.SemanticClass swpdoc_DocumentationInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#DocumentationInstance");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_documentationInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#documentationInstance");
    public static final org.semanticwb.platform.SemanticClass swpdoc_DocumentSection=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#DocumentSection");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_secTypeDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#secTypeDefinition");
    public static final org.semanticwb.platform.SemanticClass swpdoc_DocumentSectionInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#DocumentSectionInstance");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#DocumentSectionInstance");

    public static class ClassMgr
    {
       /**
       * Returns a list of DocumentSectionInstance for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.documentation.DocumentSectionInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentSectionInstance> listDocumentSectionInstances(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentSectionInstance>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.documentation.DocumentSectionInstance for all models
       * @return Iterator of org.semanticwb.process.model.documentation.DocumentSectionInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentSectionInstance> listDocumentSectionInstances()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentSectionInstance>(it, true);
        }

        public static org.semanticwb.process.model.documentation.DocumentSectionInstance createDocumentSectionInstance(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.documentation.DocumentSectionInstance.ClassMgr.createDocumentSectionInstance(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.documentation.DocumentSectionInstance
       * @param id Identifier for org.semanticwb.process.model.documentation.DocumentSectionInstance
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentSectionInstance
       * @return A org.semanticwb.process.model.documentation.DocumentSectionInstance
       */
        public static org.semanticwb.process.model.documentation.DocumentSectionInstance getDocumentSectionInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.DocumentSectionInstance)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.documentation.DocumentSectionInstance
       * @param id Identifier for org.semanticwb.process.model.documentation.DocumentSectionInstance
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentSectionInstance
       * @return A org.semanticwb.process.model.documentation.DocumentSectionInstance
       */
        public static org.semanticwb.process.model.documentation.DocumentSectionInstance createDocumentSectionInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.DocumentSectionInstance)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.documentation.DocumentSectionInstance
       * @param id Identifier for org.semanticwb.process.model.documentation.DocumentSectionInstance
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentSectionInstance
       */
        public static void removeDocumentSectionInstance(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.documentation.DocumentSectionInstance
       * @param id Identifier for org.semanticwb.process.model.documentation.DocumentSectionInstance
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentSectionInstance
       * @return true if the org.semanticwb.process.model.documentation.DocumentSectionInstance exists, false otherwise
       */

        public static boolean hasDocumentSectionInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDocumentSectionInstance(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentSectionInstance with a determined DocuSectionElementInstance
       * @param value DocuSectionElementInstance of the type org.semanticwb.process.model.documentation.SectionElement
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentSectionInstance
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentSectionInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentSectionInstance> listDocumentSectionInstanceByDocuSectionElementInstance(org.semanticwb.process.model.documentation.SectionElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentSectionInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasDocuSectionElementInstance, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentSectionInstance with a determined DocuSectionElementInstance
       * @param value DocuSectionElementInstance of the type org.semanticwb.process.model.documentation.SectionElement
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentSectionInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentSectionInstance> listDocumentSectionInstanceByDocuSectionElementInstance(org.semanticwb.process.model.documentation.SectionElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentSectionInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasDocuSectionElementInstance,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentSectionInstance with a determined DocumentationInstance
       * @param value DocumentationInstance of the type org.semanticwb.process.model.documentation.DocumentationInstance
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentSectionInstance
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentSectionInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentSectionInstance> listDocumentSectionInstanceByDocumentationInstance(org.semanticwb.process.model.documentation.DocumentationInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentSectionInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentationInstance, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentSectionInstance with a determined DocumentationInstance
       * @param value DocumentationInstance of the type org.semanticwb.process.model.documentation.DocumentationInstance
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentSectionInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentSectionInstance> listDocumentSectionInstanceByDocumentationInstance(org.semanticwb.process.model.documentation.DocumentationInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentSectionInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentationInstance,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentSectionInstance with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentSectionInstance
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentSectionInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentSectionInstance> listDocumentSectionInstanceByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentSectionInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentSectionInstance with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentSectionInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentSectionInstance> listDocumentSectionInstanceByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentSectionInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentSectionInstance with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentSectionInstance
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentSectionInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentSectionInstance> listDocumentSectionInstanceByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentSectionInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentSectionInstance with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentSectionInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentSectionInstance> listDocumentSectionInstanceByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentSectionInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentSectionInstance with a determined SecTypeDefinition
       * @param value SecTypeDefinition of the type org.semanticwb.process.model.documentation.DocumentSection
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentSectionInstance
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentSectionInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentSectionInstance> listDocumentSectionInstanceBySecTypeDefinition(org.semanticwb.process.model.documentation.DocumentSection value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentSectionInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_secTypeDefinition, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentSectionInstance with a determined SecTypeDefinition
       * @param value SecTypeDefinition of the type org.semanticwb.process.model.documentation.DocumentSection
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentSectionInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentSectionInstance> listDocumentSectionInstanceBySecTypeDefinition(org.semanticwb.process.model.documentation.DocumentSection value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentSectionInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_secTypeDefinition,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DocumentSectionInstanceBase.ClassMgr getDocumentSectionInstanceClassMgr()
    {
        return new DocumentSectionInstanceBase.ClassMgr();
    }

   /**
   * Constructs a DocumentSectionInstanceBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DocumentSectionInstance
   */
    public DocumentSectionInstanceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.process.model.documentation.SectionElement
   * @return A GenericIterator with all the org.semanticwb.process.model.documentation.SectionElement
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.SectionElement> listDocuSectionElementInstances()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.SectionElement>(getSemanticObject().listObjectProperties(swpdoc_hasDocuSectionElementInstance));
    }

   /**
   * Gets true if has a DocuSectionElementInstance
   * @param value org.semanticwb.process.model.documentation.SectionElement to verify
   * @return true if the org.semanticwb.process.model.documentation.SectionElement exists, false otherwise
   */
    public boolean hasDocuSectionElementInstance(org.semanticwb.process.model.documentation.SectionElement value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swpdoc_hasDocuSectionElementInstance,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a DocuSectionElementInstance
   * @param value org.semanticwb.process.model.documentation.SectionElement to add
   */

    public void addDocuSectionElementInstance(org.semanticwb.process.model.documentation.SectionElement value)
    {
        getSemanticObject().addObjectProperty(swpdoc_hasDocuSectionElementInstance, value.getSemanticObject());
    }
   /**
   * Removes all the DocuSectionElementInstance
   */

    public void removeAllDocuSectionElementInstance()
    {
        getSemanticObject().removeProperty(swpdoc_hasDocuSectionElementInstance);
    }
   /**
   * Removes a DocuSectionElementInstance
   * @param value org.semanticwb.process.model.documentation.SectionElement to remove
   */

    public void removeDocuSectionElementInstance(org.semanticwb.process.model.documentation.SectionElement value)
    {
        getSemanticObject().removeObjectProperty(swpdoc_hasDocuSectionElementInstance,value.getSemanticObject());
    }

   /**
   * Gets the DocuSectionElementInstance
   * @return a org.semanticwb.process.model.documentation.SectionElement
   */
    public org.semanticwb.process.model.documentation.SectionElement getDocuSectionElementInstance()
    {
         org.semanticwb.process.model.documentation.SectionElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_hasDocuSectionElementInstance);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.documentation.SectionElement)obj.createGenericInstance();
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
   * Sets the value for the property DocumentationInstance
   * @param value DocumentationInstance to set
   */

    public void setDocumentationInstance(org.semanticwb.process.model.documentation.DocumentationInstance value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swpdoc_documentationInstance, value.getSemanticObject());
        }else
        {
            removeDocumentationInstance();
        }
    }
   /**
   * Remove the value for DocumentationInstance property
   */

    public void removeDocumentationInstance()
    {
        getSemanticObject().removeProperty(swpdoc_documentationInstance);
    }

   /**
   * Gets the DocumentationInstance
   * @return a org.semanticwb.process.model.documentation.DocumentationInstance
   */
    public org.semanticwb.process.model.documentation.DocumentationInstance getDocumentationInstance()
    {
         org.semanticwb.process.model.documentation.DocumentationInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_documentationInstance);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.documentation.DocumentationInstance)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property SecTypeDefinition
   * @param value SecTypeDefinition to set
   */

    public void setSecTypeDefinition(org.semanticwb.process.model.documentation.DocumentSection value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swpdoc_secTypeDefinition, value.getSemanticObject());
        }else
        {
            removeSecTypeDefinition();
        }
    }
   /**
   * Remove the value for SecTypeDefinition property
   */

    public void removeSecTypeDefinition()
    {
        getSemanticObject().removeProperty(swpdoc_secTypeDefinition);
    }

   /**
   * Gets the SecTypeDefinition
   * @return a org.semanticwb.process.model.documentation.DocumentSection
   */
    public org.semanticwb.process.model.documentation.DocumentSection getSecTypeDefinition()
    {
         org.semanticwb.process.model.documentation.DocumentSection ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_secTypeDefinition);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.documentation.DocumentSection)obj.createGenericInstance();
         }
         return ret;
    }
}
