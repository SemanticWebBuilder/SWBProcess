package org.semanticwb.process.model.documentation.base;


   /**
   * Contenedor para versionamiento de plantillas de documentacion 
   */
public abstract class TemplateContainerBase extends org.semanticwb.process.model.documentation.DocumentationElement implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
   /**
   * Plantilla de documentacion de procesos
   */
    public static final org.semanticwb.platform.SemanticClass swpdoc_DocumentTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#DocumentTemplate");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_lastTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#lastTemplate");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_actualTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#actualTemplate");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_hasTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#hasTemplate");
    public static final org.semanticwb.platform.SemanticClass swp_Process=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Process");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_hasProcess=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#hasProcess");
   /**
   * Contenedor para versionamiento de plantillas de documentacion
   */
    public static final org.semanticwb.platform.SemanticClass swpdoc_TemplateContainer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#TemplateContainer");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#TemplateContainer");

    public static class ClassMgr
    {
       /**
       * Returns a list of TemplateContainer for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.documentation.TemplateContainer
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.TemplateContainer> listTemplateContainers(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.TemplateContainer>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.documentation.TemplateContainer for all models
       * @return Iterator of org.semanticwb.process.model.documentation.TemplateContainer
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.TemplateContainer> listTemplateContainers()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.TemplateContainer>(it, true);
        }

        public static org.semanticwb.process.model.documentation.TemplateContainer createTemplateContainer(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.documentation.TemplateContainer.ClassMgr.createTemplateContainer(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.documentation.TemplateContainer
       * @param id Identifier for org.semanticwb.process.model.documentation.TemplateContainer
       * @param model Model of the org.semanticwb.process.model.documentation.TemplateContainer
       * @return A org.semanticwb.process.model.documentation.TemplateContainer
       */
        public static org.semanticwb.process.model.documentation.TemplateContainer getTemplateContainer(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.TemplateContainer)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.documentation.TemplateContainer
       * @param id Identifier for org.semanticwb.process.model.documentation.TemplateContainer
       * @param model Model of the org.semanticwb.process.model.documentation.TemplateContainer
       * @return A org.semanticwb.process.model.documentation.TemplateContainer
       */
        public static org.semanticwb.process.model.documentation.TemplateContainer createTemplateContainer(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.TemplateContainer)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.documentation.TemplateContainer
       * @param id Identifier for org.semanticwb.process.model.documentation.TemplateContainer
       * @param model Model of the org.semanticwb.process.model.documentation.TemplateContainer
       */
        public static void removeTemplateContainer(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.documentation.TemplateContainer
       * @param id Identifier for org.semanticwb.process.model.documentation.TemplateContainer
       * @param model Model of the org.semanticwb.process.model.documentation.TemplateContainer
       * @return true if the org.semanticwb.process.model.documentation.TemplateContainer exists, false otherwise
       */

        public static boolean hasTemplateContainer(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTemplateContainer(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.TemplateContainer with a determined LastTemplate
       * @param value LastTemplate of the type org.semanticwb.process.model.documentation.DocumentTemplate
       * @param model Model of the org.semanticwb.process.model.documentation.TemplateContainer
       * @return Iterator with all the org.semanticwb.process.model.documentation.TemplateContainer
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.TemplateContainer> listTemplateContainerByLastTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.TemplateContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_lastTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.TemplateContainer with a determined LastTemplate
       * @param value LastTemplate of the type org.semanticwb.process.model.documentation.DocumentTemplate
       * @return Iterator with all the org.semanticwb.process.model.documentation.TemplateContainer
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.TemplateContainer> listTemplateContainerByLastTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.TemplateContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_lastTemplate,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.TemplateContainer with a determined ActualTemplate
       * @param value ActualTemplate of the type org.semanticwb.process.model.documentation.DocumentTemplate
       * @param model Model of the org.semanticwb.process.model.documentation.TemplateContainer
       * @return Iterator with all the org.semanticwb.process.model.documentation.TemplateContainer
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.TemplateContainer> listTemplateContainerByActualTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.TemplateContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_actualTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.TemplateContainer with a determined ActualTemplate
       * @param value ActualTemplate of the type org.semanticwb.process.model.documentation.DocumentTemplate
       * @return Iterator with all the org.semanticwb.process.model.documentation.TemplateContainer
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.TemplateContainer> listTemplateContainerByActualTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.TemplateContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_actualTemplate,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.TemplateContainer with a determined Template
       * @param value Template of the type org.semanticwb.process.model.documentation.DocumentTemplate
       * @param model Model of the org.semanticwb.process.model.documentation.TemplateContainer
       * @return Iterator with all the org.semanticwb.process.model.documentation.TemplateContainer
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.TemplateContainer> listTemplateContainerByTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.TemplateContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.TemplateContainer with a determined Template
       * @param value Template of the type org.semanticwb.process.model.documentation.DocumentTemplate
       * @return Iterator with all the org.semanticwb.process.model.documentation.TemplateContainer
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.TemplateContainer> listTemplateContainerByTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.TemplateContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasTemplate,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.TemplateContainer with a determined Process
       * @param value Process of the type org.semanticwb.process.model.Process
       * @param model Model of the org.semanticwb.process.model.documentation.TemplateContainer
       * @return Iterator with all the org.semanticwb.process.model.documentation.TemplateContainer
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.TemplateContainer> listTemplateContainerByProcess(org.semanticwb.process.model.Process value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.TemplateContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasProcess, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.TemplateContainer with a determined Process
       * @param value Process of the type org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.model.documentation.TemplateContainer
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.TemplateContainer> listTemplateContainerByProcess(org.semanticwb.process.model.Process value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.TemplateContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasProcess,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.TemplateContainer with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.TemplateContainer
       * @return Iterator with all the org.semanticwb.process.model.documentation.TemplateContainer
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.TemplateContainer> listTemplateContainerByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.TemplateContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.TemplateContainer with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.TemplateContainer
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.TemplateContainer> listTemplateContainerByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.TemplateContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.TemplateContainer with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.TemplateContainer
       * @return Iterator with all the org.semanticwb.process.model.documentation.TemplateContainer
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.TemplateContainer> listTemplateContainerByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.TemplateContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.TemplateContainer with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.TemplateContainer
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.TemplateContainer> listTemplateContainerByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.TemplateContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static TemplateContainerBase.ClassMgr getTemplateContainerClassMgr()
    {
        return new TemplateContainerBase.ClassMgr();
    }

   /**
   * Constructs a TemplateContainerBase with a SemanticObject
   * @param base The SemanticObject with the properties for the TemplateContainer
   */
    public TemplateContainerBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property LastTemplate
   * @param value LastTemplate to set
   */

    public void setLastTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swpdoc_lastTemplate, value.getSemanticObject());
        }else
        {
            removeLastTemplate();
        }
    }
   /**
   * Remove the value for LastTemplate property
   */

    public void removeLastTemplate()
    {
        getSemanticObject().removeProperty(swpdoc_lastTemplate);
    }

   /**
   * Gets the LastTemplate
   * @return a org.semanticwb.process.model.documentation.DocumentTemplate
   */
    public org.semanticwb.process.model.documentation.DocumentTemplate getLastTemplate()
    {
         org.semanticwb.process.model.documentation.DocumentTemplate ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_lastTemplate);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.documentation.DocumentTemplate)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property ActualTemplate
   * @param value ActualTemplate to set
   */

    public void setActualTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swpdoc_actualTemplate, value.getSemanticObject());
        }else
        {
            removeActualTemplate();
        }
    }
   /**
   * Remove the value for ActualTemplate property
   */

    public void removeActualTemplate()
    {
        getSemanticObject().removeProperty(swpdoc_actualTemplate);
    }

   /**
   * Gets the ActualTemplate
   * @return a org.semanticwb.process.model.documentation.DocumentTemplate
   */
    public org.semanticwb.process.model.documentation.DocumentTemplate getActualTemplate()
    {
         org.semanticwb.process.model.documentation.DocumentTemplate ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_actualTemplate);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.documentation.DocumentTemplate)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.process.model.documentation.DocumentTemplate
   * @return A GenericIterator with all the org.semanticwb.process.model.documentation.DocumentTemplate
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentTemplate> listTemplates()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentTemplate>(getSemanticObject().listObjectProperties(swpdoc_hasTemplate));
    }

   /**
   * Gets true if has a Template
   * @param value org.semanticwb.process.model.documentation.DocumentTemplate to verify
   * @return true if the org.semanticwb.process.model.documentation.DocumentTemplate exists, false otherwise
   */
    public boolean hasTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swpdoc_hasTemplate,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Template
   * @param value org.semanticwb.process.model.documentation.DocumentTemplate to add
   */

    public void addTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value)
    {
        getSemanticObject().addObjectProperty(swpdoc_hasTemplate, value.getSemanticObject());
    }
   /**
   * Removes all the Template
   */

    public void removeAllTemplate()
    {
        getSemanticObject().removeProperty(swpdoc_hasTemplate);
    }
   /**
   * Removes a Template
   * @param value org.semanticwb.process.model.documentation.DocumentTemplate to remove
   */

    public void removeTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value)
    {
        getSemanticObject().removeObjectProperty(swpdoc_hasTemplate,value.getSemanticObject());
    }

   /**
   * Gets the Template
   * @return a org.semanticwb.process.model.documentation.DocumentTemplate
   */
    public org.semanticwb.process.model.documentation.DocumentTemplate getTemplate()
    {
         org.semanticwb.process.model.documentation.DocumentTemplate ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_hasTemplate);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.documentation.DocumentTemplate)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.process.model.Process
   * @return A GenericIterator with all the org.semanticwb.process.model.Process
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process> listProcesses()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.Process>(getSemanticObject().listObjectProperties(swpdoc_hasProcess));
    }

   /**
   * Gets true if has a Process
   * @param value org.semanticwb.process.model.Process to verify
   * @return true if the org.semanticwb.process.model.Process exists, false otherwise
   */
    public boolean hasProcess(org.semanticwb.process.model.Process value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swpdoc_hasProcess,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a Process
   * @param value org.semanticwb.process.model.Process to add
   */

    public void addProcess(org.semanticwb.process.model.Process value)
    {
        getSemanticObject().addObjectProperty(swpdoc_hasProcess, value.getSemanticObject());
    }
   /**
   * Removes all the Process
   */

    public void removeAllProcess()
    {
        getSemanticObject().removeProperty(swpdoc_hasProcess);
    }
   /**
   * Removes a Process
   * @param value org.semanticwb.process.model.Process to remove
   */

    public void removeProcess(org.semanticwb.process.model.Process value)
    {
        getSemanticObject().removeObjectProperty(swpdoc_hasProcess,value.getSemanticObject());
    }

   /**
   * Gets the Process
   * @return a org.semanticwb.process.model.Process
   */
    public org.semanticwb.process.model.Process getProcess()
    {
         org.semanticwb.process.model.Process ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_hasProcess);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Process)obj.createGenericInstance();
         }
         return ret;
    }
}
