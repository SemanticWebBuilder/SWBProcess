package org.semanticwb.process.resources.documentation.model.base;


   /**
   * Contenedor para versionamiento de plantillas de documentación 
   */
public abstract class TemplateContainerBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable,org.semanticwb.model.Trashable
{
   /**
   * Plantilla de documentación de procesos
   */
    public static final org.semanticwb.platform.SemanticClass swpdoc_DocumentTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#DocumentTemplate");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_lastTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#lastTemplate");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_actualTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#actualTemplate");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_hasTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#hasTemplate");
    public static final org.semanticwb.platform.SemanticClass swp_Process=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Process");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_hasProcess=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#hasProcess");
   /**
   * Contenedor para versionamiento de plantillas de documentación
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
       * @return Iterator of org.semanticwb.process.resources.documentation.model.TemplateContainer
       */

        public static java.util.Iterator<org.semanticwb.process.resources.documentation.model.TemplateContainer> listTemplateContainers(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.documentation.model.TemplateContainer>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.resources.documentation.model.TemplateContainer for all models
       * @return Iterator of org.semanticwb.process.resources.documentation.model.TemplateContainer
       */

        public static java.util.Iterator<org.semanticwb.process.resources.documentation.model.TemplateContainer> listTemplateContainers()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.documentation.model.TemplateContainer>(it, true);
        }

        public static org.semanticwb.process.resources.documentation.model.TemplateContainer createTemplateContainer(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.resources.documentation.model.TemplateContainer.ClassMgr.createTemplateContainer(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.resources.documentation.model.TemplateContainer
       * @param id Identifier for org.semanticwb.process.resources.documentation.model.TemplateContainer
       * @param model Model of the org.semanticwb.process.resources.documentation.model.TemplateContainer
       * @return A org.semanticwb.process.resources.documentation.model.TemplateContainer
       */
        public static org.semanticwb.process.resources.documentation.model.TemplateContainer getTemplateContainer(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.resources.documentation.model.TemplateContainer)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.resources.documentation.model.TemplateContainer
       * @param id Identifier for org.semanticwb.process.resources.documentation.model.TemplateContainer
       * @param model Model of the org.semanticwb.process.resources.documentation.model.TemplateContainer
       * @return A org.semanticwb.process.resources.documentation.model.TemplateContainer
       */
        public static org.semanticwb.process.resources.documentation.model.TemplateContainer createTemplateContainer(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.resources.documentation.model.TemplateContainer)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.resources.documentation.model.TemplateContainer
       * @param id Identifier for org.semanticwb.process.resources.documentation.model.TemplateContainer
       * @param model Model of the org.semanticwb.process.resources.documentation.model.TemplateContainer
       */
        public static void removeTemplateContainer(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.resources.documentation.model.TemplateContainer
       * @param id Identifier for org.semanticwb.process.resources.documentation.model.TemplateContainer
       * @param model Model of the org.semanticwb.process.resources.documentation.model.TemplateContainer
       * @return true if the org.semanticwb.process.resources.documentation.model.TemplateContainer exists, false otherwise
       */

        public static boolean hasTemplateContainer(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTemplateContainer(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.resources.documentation.model.TemplateContainer with a determined LastTemplate
       * @param value LastTemplate of the type org.semanticwb.process.resources.documentation.model.DocumentTemplate
       * @param model Model of the org.semanticwb.process.resources.documentation.model.TemplateContainer
       * @return Iterator with all the org.semanticwb.process.resources.documentation.model.TemplateContainer
       */

        public static java.util.Iterator<org.semanticwb.process.resources.documentation.model.TemplateContainer> listTemplateContainerByLastTemplate(org.semanticwb.process.resources.documentation.model.DocumentTemplate value, org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.documentation.model.TemplateContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_lastTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.documentation.model.TemplateContainer with a determined LastTemplate
       * @param value LastTemplate of the type org.semanticwb.process.resources.documentation.model.DocumentTemplate
       * @return Iterator with all the org.semanticwb.process.resources.documentation.model.TemplateContainer
       */

        public static java.util.Iterator<org.semanticwb.process.resources.documentation.model.TemplateContainer> listTemplateContainerByLastTemplate(org.semanticwb.process.resources.documentation.model.DocumentTemplate value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.documentation.model.TemplateContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_lastTemplate,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.documentation.model.TemplateContainer with a determined ActualTemplate
       * @param value ActualTemplate of the type org.semanticwb.process.resources.documentation.model.DocumentTemplate
       * @param model Model of the org.semanticwb.process.resources.documentation.model.TemplateContainer
       * @return Iterator with all the org.semanticwb.process.resources.documentation.model.TemplateContainer
       */

        public static java.util.Iterator<org.semanticwb.process.resources.documentation.model.TemplateContainer> listTemplateContainerByActualTemplate(org.semanticwb.process.resources.documentation.model.DocumentTemplate value, org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.documentation.model.TemplateContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_actualTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.documentation.model.TemplateContainer with a determined ActualTemplate
       * @param value ActualTemplate of the type org.semanticwb.process.resources.documentation.model.DocumentTemplate
       * @return Iterator with all the org.semanticwb.process.resources.documentation.model.TemplateContainer
       */

        public static java.util.Iterator<org.semanticwb.process.resources.documentation.model.TemplateContainer> listTemplateContainerByActualTemplate(org.semanticwb.process.resources.documentation.model.DocumentTemplate value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.documentation.model.TemplateContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_actualTemplate,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.documentation.model.TemplateContainer with a determined Template
       * @param value Template of the type org.semanticwb.process.resources.documentation.model.DocumentTemplate
       * @param model Model of the org.semanticwb.process.resources.documentation.model.TemplateContainer
       * @return Iterator with all the org.semanticwb.process.resources.documentation.model.TemplateContainer
       */

        public static java.util.Iterator<org.semanticwb.process.resources.documentation.model.TemplateContainer> listTemplateContainerByTemplate(org.semanticwb.process.resources.documentation.model.DocumentTemplate value, org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.documentation.model.TemplateContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.documentation.model.TemplateContainer with a determined Template
       * @param value Template of the type org.semanticwb.process.resources.documentation.model.DocumentTemplate
       * @return Iterator with all the org.semanticwb.process.resources.documentation.model.TemplateContainer
       */

        public static java.util.Iterator<org.semanticwb.process.resources.documentation.model.TemplateContainer> listTemplateContainerByTemplate(org.semanticwb.process.resources.documentation.model.DocumentTemplate value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.documentation.model.TemplateContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasTemplate,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.documentation.model.TemplateContainer with a determined Process
       * @param value Process of the type org.semanticwb.process.model.Process
       * @param model Model of the org.semanticwb.process.resources.documentation.model.TemplateContainer
       * @return Iterator with all the org.semanticwb.process.resources.documentation.model.TemplateContainer
       */

        public static java.util.Iterator<org.semanticwb.process.resources.documentation.model.TemplateContainer> listTemplateContainerByProcess(org.semanticwb.process.model.Process value, org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.documentation.model.TemplateContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasProcess, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.documentation.model.TemplateContainer with a determined Process
       * @param value Process of the type org.semanticwb.process.model.Process
       * @return Iterator with all the org.semanticwb.process.resources.documentation.model.TemplateContainer
       */

        public static java.util.Iterator<org.semanticwb.process.resources.documentation.model.TemplateContainer> listTemplateContainerByProcess(org.semanticwb.process.model.Process value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.documentation.model.TemplateContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasProcess,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.documentation.model.TemplateContainer with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.resources.documentation.model.TemplateContainer
       * @return Iterator with all the org.semanticwb.process.resources.documentation.model.TemplateContainer
       */

        public static java.util.Iterator<org.semanticwb.process.resources.documentation.model.TemplateContainer> listTemplateContainerByModifiedBy(org.semanticwb.model.User value, org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.documentation.model.TemplateContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.documentation.model.TemplateContainer with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.resources.documentation.model.TemplateContainer
       */

        public static java.util.Iterator<org.semanticwb.process.resources.documentation.model.TemplateContainer> listTemplateContainerByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.documentation.model.TemplateContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.documentation.model.TemplateContainer with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.resources.documentation.model.TemplateContainer
       * @return Iterator with all the org.semanticwb.process.resources.documentation.model.TemplateContainer
       */

        public static java.util.Iterator<org.semanticwb.process.resources.documentation.model.TemplateContainer> listTemplateContainerByCreator(org.semanticwb.model.User value, org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.documentation.model.TemplateContainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.documentation.model.TemplateContainer with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.resources.documentation.model.TemplateContainer
       */

        public static java.util.Iterator<org.semanticwb.process.resources.documentation.model.TemplateContainer> listTemplateContainerByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.documentation.model.TemplateContainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
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

    public void setLastTemplate(org.semanticwb.process.resources.documentation.model.DocumentTemplate value)
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
   * @return a org.semanticwb.process.resources.documentation.model.DocumentTemplate
   */
    public org.semanticwb.process.resources.documentation.model.DocumentTemplate getLastTemplate()
    {
         org.semanticwb.process.resources.documentation.model.DocumentTemplate ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_lastTemplate);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.resources.documentation.model.DocumentTemplate)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Created property
* @return java.util.Date with the Created
*/
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

/**
* Sets the Created property
* @param value long with the Created
*/
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }
   /**
   * Sets the value for the property ActualTemplate
   * @param value ActualTemplate to set
   */

    public void setActualTemplate(org.semanticwb.process.resources.documentation.model.DocumentTemplate value)
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
   * @return a org.semanticwb.process.resources.documentation.model.DocumentTemplate
   */
    public org.semanticwb.process.resources.documentation.model.DocumentTemplate getActualTemplate()
    {
         org.semanticwb.process.resources.documentation.model.DocumentTemplate ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_actualTemplate);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.resources.documentation.model.DocumentTemplate)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.process.resources.documentation.model.DocumentTemplate
   * @return A GenericIterator with all the org.semanticwb.process.resources.documentation.model.DocumentTemplate
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.documentation.model.DocumentTemplate> listTemplates()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.documentation.model.DocumentTemplate>(getSemanticObject().listObjectProperties(swpdoc_hasTemplate));
    }

   /**
   * Gets true if has a Template
   * @param value org.semanticwb.process.resources.documentation.model.DocumentTemplate to verify
   * @return true if the org.semanticwb.process.resources.documentation.model.DocumentTemplate exists, false otherwise
   */
    public boolean hasTemplate(org.semanticwb.process.resources.documentation.model.DocumentTemplate value)
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
   * @param value org.semanticwb.process.resources.documentation.model.DocumentTemplate to add
   */

    public void addTemplate(org.semanticwb.process.resources.documentation.model.DocumentTemplate value)
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
   * @param value org.semanticwb.process.resources.documentation.model.DocumentTemplate to remove
   */

    public void removeTemplate(org.semanticwb.process.resources.documentation.model.DocumentTemplate value)
    {
        getSemanticObject().removeObjectProperty(swpdoc_hasTemplate,value.getSemanticObject());
    }

   /**
   * Gets the Template
   * @return a org.semanticwb.process.resources.documentation.model.DocumentTemplate
   */
    public org.semanticwb.process.resources.documentation.model.DocumentTemplate getTemplate()
    {
         org.semanticwb.process.resources.documentation.model.DocumentTemplate ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_hasTemplate);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.resources.documentation.model.DocumentTemplate)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Updated property
* @return java.util.Date with the Updated
*/
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

/**
* Sets the Updated property
* @param value long with the Updated
*/
    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
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

/**
* Gets the Description property
* @return String with the Description
*/
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

/**
* Sets the Description property
* @param value long with the Description
*/
    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }

/**
* Gets the Title property
* @return String with the Title
*/
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

/**
* Sets the Title property
* @param value long with the Title
*/
    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }
   /**
   * Sets the value for the property ModifiedBy
   * @param value ModifiedBy to set
   */

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
        }else
        {
            removeModifiedBy();
        }
    }
   /**
   * Remove the value for ModifiedBy property
   */

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

   /**
   * Gets the ModifiedBy
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Creator
   * @param value Creator to set
   */

    public void setCreator(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
        }else
        {
            removeCreator();
        }
    }
   /**
   * Remove the value for Creator property
   */

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

   /**
   * Gets the Creator
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the Deleted property
* @return boolean with the Deleted
*/
    public boolean isDeleted()
    {
        return getSemanticObject().getBooleanProperty(swb_deleted);
    }

/**
* Sets the Deleted property
* @param value long with the Deleted
*/
    public void setDeleted(boolean value)
    {
        getSemanticObject().setBooleanProperty(swb_deleted, value);
    }
}
