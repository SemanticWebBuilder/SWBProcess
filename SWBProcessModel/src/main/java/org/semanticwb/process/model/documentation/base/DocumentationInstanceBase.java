package org.semanticwb.process.model.documentation.base;


public abstract class DocumentationInstanceBase extends org.semanticwb.process.model.documentation.DocumentationElement implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swpdoc_DocumentSectionInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#DocumentSectionInstance");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_hasDocumentSectionInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#hasDocumentSectionInstance");
    public static final org.semanticwb.platform.SemanticClass swp_Process=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Process");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_processRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#processRef");
   /**
   * Plantilla de documentacion de procesos
   */
    public static final org.semanticwb.platform.SemanticClass swpdoc_DocumentTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#DocumentTemplate");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_docTypeDefinition=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#docTypeDefinition");
    public static final org.semanticwb.platform.SemanticClass swpdoc_DocumentationInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#DocumentationInstance");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#DocumentationInstance");

    public static class ClassMgr
    {
       /**
       * Returns a list of DocumentationInstance for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.documentation.DocumentationInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentationInstance> listDocumentationInstances(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentationInstance>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.documentation.DocumentationInstance for all models
       * @return Iterator of org.semanticwb.process.model.documentation.DocumentationInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentationInstance> listDocumentationInstances()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentationInstance>(it, true);
        }

        public static org.semanticwb.process.model.documentation.DocumentationInstance createDocumentationInstance(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.documentation.DocumentationInstance.ClassMgr.createDocumentationInstance(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.documentation.DocumentationInstance
       * @param id Identifier for org.semanticwb.process.model.documentation.DocumentationInstance
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentationInstance
       * @return A org.semanticwb.process.model.documentation.DocumentationInstance
       */
        public static org.semanticwb.process.model.documentation.DocumentationInstance getDocumentationInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.DocumentationInstance)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.documentation.DocumentationInstance
       * @param id Identifier for org.semanticwb.process.model.documentation.DocumentationInstance
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentationInstance
       * @return A org.semanticwb.process.model.documentation.DocumentationInstance
       */
        public static org.semanticwb.process.model.documentation.DocumentationInstance createDocumentationInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.DocumentationInstance)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.documentation.DocumentationInstance
       * @param id Identifier for org.semanticwb.process.model.documentation.DocumentationInstance
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentationInstance
       */
        public static void removeDocumentationInstance(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.documentation.DocumentationInstance
       * @param id Identifier for org.semanticwb.process.model.documentation.DocumentationInstance
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentationInstance
       * @return true if the org.semanticwb.process.model.documentation.DocumentationInstance exists, false otherwise
       */

        public static boolean hasDocumentationInstance(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDocumentationInstance(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentationInstance with a determined DocumentSectionInstance
       * @param value DocumentSectionInstance of the type org.semanticwb.process.model.documentation.DocumentSectionInstance
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentationInstance
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentationInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentationInstance> listDocumentationInstanceByDocumentSectionInstance(org.semanticwb.process.model.documentation.DocumentSectionInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentationInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasDocumentSectionInstance, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentationInstance with a determined DocumentSectionInstance
       * @param value DocumentSectionInstance of the type org.semanticwb.process.model.documentation.DocumentSectionInstance
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentationInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentationInstance> listDocumentationInstanceByDocumentSectionInstance(org.semanticwb.process.model.documentation.DocumentSectionInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentationInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasDocumentSectionInstance,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentationInstance with a determined ProcessRef
       * @param value ProcessRef of the type org.semanticwb.process.model.Process
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentationInstance
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentationInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentationInstance> listDocumentationInstanceByProcessRef(org.semanticwb.process.model.Process value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentationInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_processRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentationInstance with a determined ProcessRef
       * @param value ProcessRef of the type org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentationInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentationInstance> listDocumentationInstanceByProcessRef(org.semanticwb.process.model.Process value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentationInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_processRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentationInstance with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentationInstance
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentationInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentationInstance> listDocumentationInstanceByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentationInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentationInstance with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentationInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentationInstance> listDocumentationInstanceByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentationInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentationInstance with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentationInstance
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentationInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentationInstance> listDocumentationInstanceByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentationInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentationInstance with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentationInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentationInstance> listDocumentationInstanceByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentationInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentationInstance with a determined DocTypeDefinition
       * @param value DocTypeDefinition of the type org.semanticwb.process.model.documentation.DocumentTemplate
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentationInstance
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentationInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentationInstance> listDocumentationInstanceByDocTypeDefinition(org.semanticwb.process.model.documentation.DocumentTemplate value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentationInstance> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_docTypeDefinition, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentationInstance with a determined DocTypeDefinition
       * @param value DocTypeDefinition of the type org.semanticwb.process.model.documentation.DocumentTemplate
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentationInstance
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentationInstance> listDocumentationInstanceByDocTypeDefinition(org.semanticwb.process.model.documentation.DocumentTemplate value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentationInstance> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_docTypeDefinition,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DocumentationInstanceBase.ClassMgr getDocumentationInstanceClassMgr()
    {
        return new DocumentationInstanceBase.ClassMgr();
    }

   /**
   * Constructs a DocumentationInstanceBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DocumentationInstance
   */
    public DocumentationInstanceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.process.model.documentation.DocumentSectionInstance
   * @return A GenericIterator with all the org.semanticwb.process.model.documentation.DocumentSectionInstance
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentSectionInstance> listDocumentSectionInstances()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentSectionInstance>(getSemanticObject().listObjectProperties(swpdoc_hasDocumentSectionInstance));
    }

   /**
   * Gets true if has a DocumentSectionInstance
   * @param value org.semanticwb.process.model.documentation.DocumentSectionInstance to verify
   * @return true if the org.semanticwb.process.model.documentation.DocumentSectionInstance exists, false otherwise
   */
    public boolean hasDocumentSectionInstance(org.semanticwb.process.model.documentation.DocumentSectionInstance value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swpdoc_hasDocumentSectionInstance,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a DocumentSectionInstance
   * @param value org.semanticwb.process.model.documentation.DocumentSectionInstance to add
   */

    public void addDocumentSectionInstance(org.semanticwb.process.model.documentation.DocumentSectionInstance value)
    {
        getSemanticObject().addObjectProperty(swpdoc_hasDocumentSectionInstance, value.getSemanticObject());
    }
   /**
   * Removes all the DocumentSectionInstance
   */

    public void removeAllDocumentSectionInstance()
    {
        getSemanticObject().removeProperty(swpdoc_hasDocumentSectionInstance);
    }
   /**
   * Removes a DocumentSectionInstance
   * @param value org.semanticwb.process.model.documentation.DocumentSectionInstance to remove
   */

    public void removeDocumentSectionInstance(org.semanticwb.process.model.documentation.DocumentSectionInstance value)
    {
        getSemanticObject().removeObjectProperty(swpdoc_hasDocumentSectionInstance,value.getSemanticObject());
    }

   /**
   * Gets the DocumentSectionInstance
   * @return a org.semanticwb.process.model.documentation.DocumentSectionInstance
   */
    public org.semanticwb.process.model.documentation.DocumentSectionInstance getDocumentSectionInstance()
    {
         org.semanticwb.process.model.documentation.DocumentSectionInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_hasDocumentSectionInstance);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.documentation.DocumentSectionInstance)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property ProcessRef
   * @param value ProcessRef to set
   */

    public void setProcessRef(org.semanticwb.process.model.Process value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swpdoc_processRef, value.getSemanticObject());
        }else
        {
            removeProcessRef();
        }
    }
   /**
   * Remove the value for ProcessRef property
   */

    public void removeProcessRef()
    {
        getSemanticObject().removeProperty(swpdoc_processRef);
    }

   /**
   * Gets the ProcessRef
   * @return a org.semanticwb.process.model.Process
   */
    public org.semanticwb.process.model.Process getProcessRef()
    {
         org.semanticwb.process.model.Process ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_processRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Process)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property DocTypeDefinition
   * @param value DocTypeDefinition to set
   */

    public void setDocTypeDefinition(org.semanticwb.process.model.documentation.DocumentTemplate value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swpdoc_docTypeDefinition, value.getSemanticObject());
        }else
        {
            removeDocTypeDefinition();
        }
    }
   /**
   * Remove the value for DocTypeDefinition property
   */

    public void removeDocTypeDefinition()
    {
        getSemanticObject().removeProperty(swpdoc_docTypeDefinition);
    }

   /**
   * Gets the DocTypeDefinition
   * @return a org.semanticwb.process.model.documentation.DocumentTemplate
   */
    public org.semanticwb.process.model.documentation.DocumentTemplate getDocTypeDefinition()
    {
         org.semanticwb.process.model.documentation.DocumentTemplate ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_docTypeDefinition);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.documentation.DocumentTemplate)obj.createGenericInstance();
         }
         return ret;
    }
}
