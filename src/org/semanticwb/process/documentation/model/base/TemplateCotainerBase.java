package org.semanticwb.process.documentation.model.base;


public abstract class TemplateCotainerBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticClass swpdoc_DocumentTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#DocumentTemplate");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_lastTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#lastTemplate");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_actualTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#actualTemplate");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_hasTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#hasTemplate");
    public static final org.semanticwb.platform.SemanticClass swpdoc_TemplateCotainer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#TemplateCotainer");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#TemplateCotainer");

    public static class ClassMgr
    {
       /**
       * Returns a list of TemplateCotainer for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.documentation.model.TemplateCotainer
       */

        public static java.util.Iterator<org.semanticwb.process.documentation.model.TemplateCotainer> listTemplateCotainers(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.documentation.model.TemplateCotainer>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.documentation.model.TemplateCotainer for all models
       * @return Iterator of org.semanticwb.process.documentation.model.TemplateCotainer
       */

        public static java.util.Iterator<org.semanticwb.process.documentation.model.TemplateCotainer> listTemplateCotainers()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.documentation.model.TemplateCotainer>(it, true);
        }

        public static org.semanticwb.process.documentation.model.TemplateCotainer createTemplateCotainer(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.documentation.model.TemplateCotainer.ClassMgr.createTemplateCotainer(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.documentation.model.TemplateCotainer
       * @param id Identifier for org.semanticwb.process.documentation.model.TemplateCotainer
       * @param model Model of the org.semanticwb.process.documentation.model.TemplateCotainer
       * @return A org.semanticwb.process.documentation.model.TemplateCotainer
       */
        public static org.semanticwb.process.documentation.model.TemplateCotainer getTemplateCotainer(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.documentation.model.TemplateCotainer)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.documentation.model.TemplateCotainer
       * @param id Identifier for org.semanticwb.process.documentation.model.TemplateCotainer
       * @param model Model of the org.semanticwb.process.documentation.model.TemplateCotainer
       * @return A org.semanticwb.process.documentation.model.TemplateCotainer
       */
        public static org.semanticwb.process.documentation.model.TemplateCotainer createTemplateCotainer(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.documentation.model.TemplateCotainer)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.documentation.model.TemplateCotainer
       * @param id Identifier for org.semanticwb.process.documentation.model.TemplateCotainer
       * @param model Model of the org.semanticwb.process.documentation.model.TemplateCotainer
       */
        public static void removeTemplateCotainer(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.documentation.model.TemplateCotainer
       * @param id Identifier for org.semanticwb.process.documentation.model.TemplateCotainer
       * @param model Model of the org.semanticwb.process.documentation.model.TemplateCotainer
       * @return true if the org.semanticwb.process.documentation.model.TemplateCotainer exists, false otherwise
       */

        public static boolean hasTemplateCotainer(String id, org.semanticwb.model.SWBModel model)
        {
            return (getTemplateCotainer(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.documentation.model.TemplateCotainer with a determined LastTemplate
       * @param value LastTemplate of the type org.semanticwb.process.documentation.model.DocumentTemplate
       * @param model Model of the org.semanticwb.process.documentation.model.TemplateCotainer
       * @return Iterator with all the org.semanticwb.process.documentation.model.TemplateCotainer
       */

        public static java.util.Iterator<org.semanticwb.process.documentation.model.TemplateCotainer> listTemplateCotainerByLastTemplate(org.semanticwb.process.documentation.model.DocumentTemplate value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.documentation.model.TemplateCotainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_lastTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.documentation.model.TemplateCotainer with a determined LastTemplate
       * @param value LastTemplate of the type org.semanticwb.process.documentation.model.DocumentTemplate
       * @return Iterator with all the org.semanticwb.process.documentation.model.TemplateCotainer
       */

        public static java.util.Iterator<org.semanticwb.process.documentation.model.TemplateCotainer> listTemplateCotainerByLastTemplate(org.semanticwb.process.documentation.model.DocumentTemplate value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.documentation.model.TemplateCotainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_lastTemplate,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.documentation.model.TemplateCotainer with a determined ActualTemplate
       * @param value ActualTemplate of the type org.semanticwb.process.documentation.model.DocumentTemplate
       * @param model Model of the org.semanticwb.process.documentation.model.TemplateCotainer
       * @return Iterator with all the org.semanticwb.process.documentation.model.TemplateCotainer
       */

        public static java.util.Iterator<org.semanticwb.process.documentation.model.TemplateCotainer> listTemplateCotainerByActualTemplate(org.semanticwb.process.documentation.model.DocumentTemplate value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.documentation.model.TemplateCotainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_actualTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.documentation.model.TemplateCotainer with a determined ActualTemplate
       * @param value ActualTemplate of the type org.semanticwb.process.documentation.model.DocumentTemplate
       * @return Iterator with all the org.semanticwb.process.documentation.model.TemplateCotainer
       */

        public static java.util.Iterator<org.semanticwb.process.documentation.model.TemplateCotainer> listTemplateCotainerByActualTemplate(org.semanticwb.process.documentation.model.DocumentTemplate value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.documentation.model.TemplateCotainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_actualTemplate,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.documentation.model.TemplateCotainer with a determined Template
       * @param value Template of the type org.semanticwb.process.documentation.model.DocumentTemplate
       * @param model Model of the org.semanticwb.process.documentation.model.TemplateCotainer
       * @return Iterator with all the org.semanticwb.process.documentation.model.TemplateCotainer
       */

        public static java.util.Iterator<org.semanticwb.process.documentation.model.TemplateCotainer> listTemplateCotainerByTemplate(org.semanticwb.process.documentation.model.DocumentTemplate value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.documentation.model.TemplateCotainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.documentation.model.TemplateCotainer with a determined Template
       * @param value Template of the type org.semanticwb.process.documentation.model.DocumentTemplate
       * @return Iterator with all the org.semanticwb.process.documentation.model.TemplateCotainer
       */

        public static java.util.Iterator<org.semanticwb.process.documentation.model.TemplateCotainer> listTemplateCotainerByTemplate(org.semanticwb.process.documentation.model.DocumentTemplate value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.documentation.model.TemplateCotainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasTemplate,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.documentation.model.TemplateCotainer with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.documentation.model.TemplateCotainer
       * @return Iterator with all the org.semanticwb.process.documentation.model.TemplateCotainer
       */

        public static java.util.Iterator<org.semanticwb.process.documentation.model.TemplateCotainer> listTemplateCotainerByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.documentation.model.TemplateCotainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.documentation.model.TemplateCotainer with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.documentation.model.TemplateCotainer
       */

        public static java.util.Iterator<org.semanticwb.process.documentation.model.TemplateCotainer> listTemplateCotainerByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.documentation.model.TemplateCotainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.documentation.model.TemplateCotainer with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.documentation.model.TemplateCotainer
       * @return Iterator with all the org.semanticwb.process.documentation.model.TemplateCotainer
       */

        public static java.util.Iterator<org.semanticwb.process.documentation.model.TemplateCotainer> listTemplateCotainerByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.documentation.model.TemplateCotainer> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.documentation.model.TemplateCotainer with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.documentation.model.TemplateCotainer
       */

        public static java.util.Iterator<org.semanticwb.process.documentation.model.TemplateCotainer> listTemplateCotainerByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.documentation.model.TemplateCotainer> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static TemplateCotainerBase.ClassMgr getTemplateCotainerClassMgr()
    {
        return new TemplateCotainerBase.ClassMgr();
    }

   /**
   * Constructs a TemplateCotainerBase with a SemanticObject
   * @param base The SemanticObject with the properties for the TemplateCotainer
   */
    public TemplateCotainerBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property LastTemplate
   * @param value LastTemplate to set
   */

    public void setLastTemplate(org.semanticwb.process.documentation.model.DocumentTemplate value)
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
   * @return a org.semanticwb.process.documentation.model.DocumentTemplate
   */
    public org.semanticwb.process.documentation.model.DocumentTemplate getLastTemplate()
    {
         org.semanticwb.process.documentation.model.DocumentTemplate ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_lastTemplate);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.documentation.model.DocumentTemplate)obj.createGenericInstance();
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

    public void setActualTemplate(org.semanticwb.process.documentation.model.DocumentTemplate value)
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
   * @return a org.semanticwb.process.documentation.model.DocumentTemplate
   */
    public org.semanticwb.process.documentation.model.DocumentTemplate getActualTemplate()
    {
         org.semanticwb.process.documentation.model.DocumentTemplate ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_actualTemplate);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.documentation.model.DocumentTemplate)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.process.documentation.model.DocumentTemplate
   * @return A GenericIterator with all the org.semanticwb.process.documentation.model.DocumentTemplate
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.documentation.model.DocumentTemplate> listTemplates()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.documentation.model.DocumentTemplate>(getSemanticObject().listObjectProperties(swpdoc_hasTemplate));
    }

   /**
   * Gets true if has a Template
   * @param value org.semanticwb.process.documentation.model.DocumentTemplate to verify
   * @return true if the org.semanticwb.process.documentation.model.DocumentTemplate exists, false otherwise
   */
    public boolean hasTemplate(org.semanticwb.process.documentation.model.DocumentTemplate value)
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
   * @param value org.semanticwb.process.documentation.model.DocumentTemplate to add
   */

    public void addTemplate(org.semanticwb.process.documentation.model.DocumentTemplate value)
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
   * @param value org.semanticwb.process.documentation.model.DocumentTemplate to remove
   */

    public void removeTemplate(org.semanticwb.process.documentation.model.DocumentTemplate value)
    {
        getSemanticObject().removeObjectProperty(swpdoc_hasTemplate,value.getSemanticObject());
    }

   /**
   * Gets the Template
   * @return a org.semanticwb.process.documentation.model.DocumentTemplate
   */
    public org.semanticwb.process.documentation.model.DocumentTemplate getTemplate()
    {
         org.semanticwb.process.documentation.model.DocumentTemplate ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_hasTemplate);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.documentation.model.DocumentTemplate)obj.createGenericInstance();
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
}
