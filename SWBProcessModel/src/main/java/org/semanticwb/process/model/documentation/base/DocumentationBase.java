package org.semanticwb.process.model.documentation.base;


public abstract class DocumentationBase extends org.semanticwb.process.model.documentation.DocumentSectionInstance implements org.semanticwb.model.Sortable,org.semanticwb.process.model.documentation.DocVersionable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty swpdoc_nameVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#nameVersion");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_actualVersion=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#actualVersion");
    public static final org.semanticwb.platform.SemanticClass swp_Process=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Process");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_process=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#process");
    public static final org.semanticwb.platform.SemanticClass swpdoc_Documentation=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#Documentation");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#Documentation");

    public static class ClassMgr
    {
       /**
       * Returns a list of Documentation for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.documentation.Documentation
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Documentation> listDocumentations(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Documentation>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.documentation.Documentation for all models
       * @return Iterator of org.semanticwb.process.model.documentation.Documentation
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Documentation> listDocumentations()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Documentation>(it, true);
        }

        public static org.semanticwb.process.model.documentation.Documentation createDocumentation(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.documentation.Documentation.ClassMgr.createDocumentation(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.documentation.Documentation
       * @param id Identifier for org.semanticwb.process.model.documentation.Documentation
       * @param model Model of the org.semanticwb.process.model.documentation.Documentation
       * @return A org.semanticwb.process.model.documentation.Documentation
       */
        public static org.semanticwb.process.model.documentation.Documentation getDocumentation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.Documentation)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.documentation.Documentation
       * @param id Identifier for org.semanticwb.process.model.documentation.Documentation
       * @param model Model of the org.semanticwb.process.model.documentation.Documentation
       * @return A org.semanticwb.process.model.documentation.Documentation
       */
        public static org.semanticwb.process.model.documentation.Documentation createDocumentation(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.Documentation)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.documentation.Documentation
       * @param id Identifier for org.semanticwb.process.model.documentation.Documentation
       * @param model Model of the org.semanticwb.process.model.documentation.Documentation
       */
        public static void removeDocumentation(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.documentation.Documentation
       * @param id Identifier for org.semanticwb.process.model.documentation.Documentation
       * @param model Model of the org.semanticwb.process.model.documentation.Documentation
       * @return true if the org.semanticwb.process.model.documentation.Documentation exists, false otherwise
       */

        public static boolean hasDocumentation(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDocumentation(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Documentation with a determined DocuSectionElementInstance
       * @param value DocuSectionElementInstance of the type org.semanticwb.process.model.documentation.SectionElement
       * @param model Model of the org.semanticwb.process.model.documentation.Documentation
       * @return Iterator with all the org.semanticwb.process.model.documentation.Documentation
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Documentation> listDocumentationByDocuSectionElementInstance(org.semanticwb.process.model.documentation.SectionElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Documentation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasDocuSectionElementInstance, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Documentation with a determined DocuSectionElementInstance
       * @param value DocuSectionElementInstance of the type org.semanticwb.process.model.documentation.SectionElement
       * @return Iterator with all the org.semanticwb.process.model.documentation.Documentation
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Documentation> listDocumentationByDocuSectionElementInstance(org.semanticwb.process.model.documentation.SectionElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Documentation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasDocuSectionElementInstance,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Documentation with a determined DocumentationInstance
       * @param value DocumentationInstance of the type org.semanticwb.process.model.documentation.DocumentationInstance
       * @param model Model of the org.semanticwb.process.model.documentation.Documentation
       * @return Iterator with all the org.semanticwb.process.model.documentation.Documentation
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Documentation> listDocumentationByDocumentationInstance(org.semanticwb.process.model.documentation.DocumentationInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Documentation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentationInstance, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Documentation with a determined DocumentationInstance
       * @param value DocumentationInstance of the type org.semanticwb.process.model.documentation.DocumentationInstance
       * @return Iterator with all the org.semanticwb.process.model.documentation.Documentation
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Documentation> listDocumentationByDocumentationInstance(org.semanticwb.process.model.documentation.DocumentationInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Documentation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentationInstance,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Documentation with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.Documentation
       * @return Iterator with all the org.semanticwb.process.model.documentation.Documentation
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Documentation> listDocumentationByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Documentation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Documentation with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.Documentation
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Documentation> listDocumentationByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Documentation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Documentation with a determined Process
       * @param value Process of the type org.semanticwb.process.model.Process
       * @param model Model of the org.semanticwb.process.model.documentation.Documentation
       * @return Iterator with all the org.semanticwb.process.model.documentation.Documentation
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Documentation> listDocumentationByProcess(org.semanticwb.process.model.Process value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Documentation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_process, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Documentation with a determined Process
       * @param value Process of the type org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.model.documentation.Documentation
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Documentation> listDocumentationByProcess(org.semanticwb.process.model.Process value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Documentation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_process,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Documentation with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.Documentation
       * @return Iterator with all the org.semanticwb.process.model.documentation.Documentation
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Documentation> listDocumentationByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Documentation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Documentation with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.Documentation
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Documentation> listDocumentationByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Documentation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Documentation with a determined SecTypeDefinition
       * @param value SecTypeDefinition of the type org.semanticwb.process.model.documentation.DocumentSection
       * @param model Model of the org.semanticwb.process.model.documentation.Documentation
       * @return Iterator with all the org.semanticwb.process.model.documentation.Documentation
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Documentation> listDocumentationBySecTypeDefinition(org.semanticwb.process.model.documentation.DocumentSection value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Documentation> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_secTypeDefinition, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Documentation with a determined SecTypeDefinition
       * @param value SecTypeDefinition of the type org.semanticwb.process.model.documentation.DocumentSection
       * @return Iterator with all the org.semanticwb.process.model.documentation.Documentation
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Documentation> listDocumentationBySecTypeDefinition(org.semanticwb.process.model.documentation.DocumentSection value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Documentation> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_secTypeDefinition,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DocumentationBase.ClassMgr getDocumentationClassMgr()
    {
        return new DocumentationBase.ClassMgr();
    }

   /**
   * Constructs a DocumentationBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Documentation
   */
    public DocumentationBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the NameVersion property
* @return String with the NameVersion
*/
    public String getNameVersion()
    {
        return getSemanticObject().getProperty(swpdoc_nameVersion);
    }

/**
* Sets the NameVersion property
* @param value long with the NameVersion
*/
    public void setNameVersion(String value)
    {
        getSemanticObject().setProperty(swpdoc_nameVersion, value);
    }

/**
* Gets the VersionValue property
* @return String with the VersionValue
*/
    public String getVersionValue()
    {
        return getSemanticObject().getProperty(swpdoc_versionValue);
    }

/**
* Sets the VersionValue property
* @param value long with the VersionValue
*/
    public void setVersionValue(String value)
    {
        getSemanticObject().setProperty(swpdoc_versionValue, value);
    }

/**
* Gets the VersionComment property
* @return String with the VersionComment
*/
    public String getVersionComment()
    {
        return getSemanticObject().getProperty(swpdoc_versionComment);
    }

/**
* Sets the VersionComment property
* @param value long with the VersionComment
*/
    public void setVersionComment(String value)
    {
        getSemanticObject().setProperty(swpdoc_versionComment, value);
    }

/**
* Gets the ActualVersion property
* @return boolean with the ActualVersion
*/
    public boolean isActualVersion()
    {
        return getSemanticObject().getBooleanProperty(swpdoc_actualVersion);
    }

/**
* Sets the ActualVersion property
* @param value long with the ActualVersion
*/
    public void setActualVersion(boolean value)
    {
        getSemanticObject().setBooleanProperty(swpdoc_actualVersion, value);
    }
   /**
   * Sets the value for the property Process
   * @param value Process to set
   */

    public void setProcess(org.semanticwb.process.model.Process value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swpdoc_process, value.getSemanticObject());
        }else
        {
            removeProcess();
        }
    }
   /**
   * Remove the value for Process property
   */

    public void removeProcess()
    {
        getSemanticObject().removeProperty(swpdoc_process);
    }

   /**
   * Gets the Process
   * @return a org.semanticwb.process.model.Process
   */
    public org.semanticwb.process.model.Process getProcess()
    {
         org.semanticwb.process.model.Process ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_process);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Process)obj.createGenericInstance();
         }
         return ret;
    }
}
