package org.semanticwb.process.resources.base;


public abstract class SWPResourcesConfigBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable
{
    public static final org.semanticwb.platform.SemanticProperty swpres_inheritProcessAccessRoles=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#inheritProcessAccessRoles");
   /**
   * Una Página Web es el elemento de SemanticWebBuilder a través del cual se estructura la información del portal.
   */
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    public static final org.semanticwb.platform.SemanticProperty swpres_templatesPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#templatesPage");
   /**
   * Objeto que define un Role dentro de un repositorio de usuarios aplicable a un Usuario para filtrar componente, seccion, plantillas, etc.
   */
    public static final org.semanticwb.platform.SemanticClass swb_Role=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Role");
    public static final org.semanticwb.platform.SemanticProperty swpres_adminRole=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#adminRole");
    public static final org.semanticwb.platform.SemanticProperty swpres_documenterRole=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#documenterRole");
    public static final org.semanticwb.platform.SemanticProperty swpres_filterByGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#filterByGroup");
    public static final org.semanticwb.platform.SemanticProperty swpres_editDocumentationPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#editDocumentationPage");
    public static final org.semanticwb.platform.SemanticProperty swpres_configPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#configPage");
    public static final org.semanticwb.platform.SemanticProperty swpres_viewRole=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#viewRole");
    public static final org.semanticwb.platform.SemanticClass swpres_SWPResourcesConfig=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process/resources#SWPResourcesConfig");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process/resources#SWPResourcesConfig");

    public static class ClassMgr
    {
       /**
       * Returns a list of SWPResourcesConfig for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.resources.SWPResourcesConfig
       */

        public static java.util.Iterator<org.semanticwb.process.resources.SWPResourcesConfig> listSWPResourcesConfigs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.SWPResourcesConfig>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.resources.SWPResourcesConfig for all models
       * @return Iterator of org.semanticwb.process.resources.SWPResourcesConfig
       */

        public static java.util.Iterator<org.semanticwb.process.resources.SWPResourcesConfig> listSWPResourcesConfigs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.SWPResourcesConfig>(it, true);
        }

        public static org.semanticwb.process.resources.SWPResourcesConfig createSWPResourcesConfig(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.resources.SWPResourcesConfig.ClassMgr.createSWPResourcesConfig(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.resources.SWPResourcesConfig
       * @param id Identifier for org.semanticwb.process.resources.SWPResourcesConfig
       * @param model Model of the org.semanticwb.process.resources.SWPResourcesConfig
       * @return A org.semanticwb.process.resources.SWPResourcesConfig
       */
        public static org.semanticwb.process.resources.SWPResourcesConfig getSWPResourcesConfig(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.resources.SWPResourcesConfig)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.resources.SWPResourcesConfig
       * @param id Identifier for org.semanticwb.process.resources.SWPResourcesConfig
       * @param model Model of the org.semanticwb.process.resources.SWPResourcesConfig
       * @return A org.semanticwb.process.resources.SWPResourcesConfig
       */
        public static org.semanticwb.process.resources.SWPResourcesConfig createSWPResourcesConfig(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.resources.SWPResourcesConfig)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.resources.SWPResourcesConfig
       * @param id Identifier for org.semanticwb.process.resources.SWPResourcesConfig
       * @param model Model of the org.semanticwb.process.resources.SWPResourcesConfig
       */
        public static void removeSWPResourcesConfig(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.resources.SWPResourcesConfig
       * @param id Identifier for org.semanticwb.process.resources.SWPResourcesConfig
       * @param model Model of the org.semanticwb.process.resources.SWPResourcesConfig
       * @return true if the org.semanticwb.process.resources.SWPResourcesConfig exists, false otherwise
       */

        public static boolean hasSWPResourcesConfig(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSWPResourcesConfig(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.resources.SWPResourcesConfig with a determined TemplatesPage
       * @param value TemplatesPage of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.process.resources.SWPResourcesConfig
       * @return Iterator with all the org.semanticwb.process.resources.SWPResourcesConfig
       */

        public static java.util.Iterator<org.semanticwb.process.resources.SWPResourcesConfig> listSWPResourcesConfigByTemplatesPage(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.SWPResourcesConfig> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpres_templatesPage, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.SWPResourcesConfig with a determined TemplatesPage
       * @param value TemplatesPage of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.process.resources.SWPResourcesConfig
       */

        public static java.util.Iterator<org.semanticwb.process.resources.SWPResourcesConfig> listSWPResourcesConfigByTemplatesPage(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.SWPResourcesConfig> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpres_templatesPage,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.SWPResourcesConfig with a determined AdminRole
       * @param value AdminRole of the type org.semanticwb.model.Role
       * @param model Model of the org.semanticwb.process.resources.SWPResourcesConfig
       * @return Iterator with all the org.semanticwb.process.resources.SWPResourcesConfig
       */

        public static java.util.Iterator<org.semanticwb.process.resources.SWPResourcesConfig> listSWPResourcesConfigByAdminRole(org.semanticwb.model.Role value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.SWPResourcesConfig> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpres_adminRole, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.SWPResourcesConfig with a determined AdminRole
       * @param value AdminRole of the type org.semanticwb.model.Role
       * @return Iterator with all the org.semanticwb.process.resources.SWPResourcesConfig
       */

        public static java.util.Iterator<org.semanticwb.process.resources.SWPResourcesConfig> listSWPResourcesConfigByAdminRole(org.semanticwb.model.Role value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.SWPResourcesConfig> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpres_adminRole,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.SWPResourcesConfig with a determined DocumenterRole
       * @param value DocumenterRole of the type org.semanticwb.model.Role
       * @param model Model of the org.semanticwb.process.resources.SWPResourcesConfig
       * @return Iterator with all the org.semanticwb.process.resources.SWPResourcesConfig
       */

        public static java.util.Iterator<org.semanticwb.process.resources.SWPResourcesConfig> listSWPResourcesConfigByDocumenterRole(org.semanticwb.model.Role value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.SWPResourcesConfig> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpres_documenterRole, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.SWPResourcesConfig with a determined DocumenterRole
       * @param value DocumenterRole of the type org.semanticwb.model.Role
       * @return Iterator with all the org.semanticwb.process.resources.SWPResourcesConfig
       */

        public static java.util.Iterator<org.semanticwb.process.resources.SWPResourcesConfig> listSWPResourcesConfigByDocumenterRole(org.semanticwb.model.Role value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.SWPResourcesConfig> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpres_documenterRole,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.SWPResourcesConfig with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.resources.SWPResourcesConfig
       * @return Iterator with all the org.semanticwb.process.resources.SWPResourcesConfig
       */

        public static java.util.Iterator<org.semanticwb.process.resources.SWPResourcesConfig> listSWPResourcesConfigByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.SWPResourcesConfig> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.SWPResourcesConfig with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.resources.SWPResourcesConfig
       */

        public static java.util.Iterator<org.semanticwb.process.resources.SWPResourcesConfig> listSWPResourcesConfigByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.SWPResourcesConfig> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.SWPResourcesConfig with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.resources.SWPResourcesConfig
       * @return Iterator with all the org.semanticwb.process.resources.SWPResourcesConfig
       */

        public static java.util.Iterator<org.semanticwb.process.resources.SWPResourcesConfig> listSWPResourcesConfigByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.SWPResourcesConfig> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.SWPResourcesConfig with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.resources.SWPResourcesConfig
       */

        public static java.util.Iterator<org.semanticwb.process.resources.SWPResourcesConfig> listSWPResourcesConfigByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.SWPResourcesConfig> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.SWPResourcesConfig with a determined EditDocumentationPage
       * @param value EditDocumentationPage of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.process.resources.SWPResourcesConfig
       * @return Iterator with all the org.semanticwb.process.resources.SWPResourcesConfig
       */

        public static java.util.Iterator<org.semanticwb.process.resources.SWPResourcesConfig> listSWPResourcesConfigByEditDocumentationPage(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.SWPResourcesConfig> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpres_editDocumentationPage, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.SWPResourcesConfig with a determined EditDocumentationPage
       * @param value EditDocumentationPage of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.process.resources.SWPResourcesConfig
       */

        public static java.util.Iterator<org.semanticwb.process.resources.SWPResourcesConfig> listSWPResourcesConfigByEditDocumentationPage(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.SWPResourcesConfig> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpres_editDocumentationPage,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.SWPResourcesConfig with a determined ConfigPage
       * @param value ConfigPage of the type org.semanticwb.model.WebPage
       * @param model Model of the org.semanticwb.process.resources.SWPResourcesConfig
       * @return Iterator with all the org.semanticwb.process.resources.SWPResourcesConfig
       */

        public static java.util.Iterator<org.semanticwb.process.resources.SWPResourcesConfig> listSWPResourcesConfigByConfigPage(org.semanticwb.model.WebPage value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.SWPResourcesConfig> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpres_configPage, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.SWPResourcesConfig with a determined ConfigPage
       * @param value ConfigPage of the type org.semanticwb.model.WebPage
       * @return Iterator with all the org.semanticwb.process.resources.SWPResourcesConfig
       */

        public static java.util.Iterator<org.semanticwb.process.resources.SWPResourcesConfig> listSWPResourcesConfigByConfigPage(org.semanticwb.model.WebPage value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.SWPResourcesConfig> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpres_configPage,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.SWPResourcesConfig with a determined ViewRole
       * @param value ViewRole of the type org.semanticwb.model.Role
       * @param model Model of the org.semanticwb.process.resources.SWPResourcesConfig
       * @return Iterator with all the org.semanticwb.process.resources.SWPResourcesConfig
       */

        public static java.util.Iterator<org.semanticwb.process.resources.SWPResourcesConfig> listSWPResourcesConfigByViewRole(org.semanticwb.model.Role value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.SWPResourcesConfig> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpres_viewRole, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.SWPResourcesConfig with a determined ViewRole
       * @param value ViewRole of the type org.semanticwb.model.Role
       * @return Iterator with all the org.semanticwb.process.resources.SWPResourcesConfig
       */

        public static java.util.Iterator<org.semanticwb.process.resources.SWPResourcesConfig> listSWPResourcesConfigByViewRole(org.semanticwb.model.Role value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.SWPResourcesConfig> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpres_viewRole,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SWPResourcesConfigBase.ClassMgr getSWPResourcesConfigClassMgr()
    {
        return new SWPResourcesConfigBase.ClassMgr();
    }

   /**
   * Constructs a SWPResourcesConfigBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SWPResourcesConfig
   */
    public SWPResourcesConfigBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the InheritProcessAccessRoles property
* @return boolean with the InheritProcessAccessRoles
*/
    public boolean isInheritProcessAccessRoles()
    {
        return getSemanticObject().getBooleanProperty(swpres_inheritProcessAccessRoles);
    }

/**
* Sets the InheritProcessAccessRoles property
* @param value long with the InheritProcessAccessRoles
*/
    public void setInheritProcessAccessRoles(boolean value)
    {
        getSemanticObject().setBooleanProperty(swpres_inheritProcessAccessRoles, value);
    }
   /**
   * Sets the value for the property TemplatesPage
   * @param value TemplatesPage to set
   */

    public void setTemplatesPage(org.semanticwb.model.WebPage value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swpres_templatesPage, value.getSemanticObject());
        }else
        {
            removeTemplatesPage();
        }
    }
   /**
   * Remove the value for TemplatesPage property
   */

    public void removeTemplatesPage()
    {
        getSemanticObject().removeProperty(swpres_templatesPage);
    }

   /**
   * Gets the TemplatesPage
   * @return a org.semanticwb.model.WebPage
   */
    public org.semanticwb.model.WebPage getTemplatesPage()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpres_templatesPage);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
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
   * Sets the value for the property AdminRole
   * @param value AdminRole to set
   */

    public void setAdminRole(org.semanticwb.model.Role value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swpres_adminRole, value.getSemanticObject());
        }else
        {
            removeAdminRole();
        }
    }
   /**
   * Remove the value for AdminRole property
   */

    public void removeAdminRole()
    {
        getSemanticObject().removeProperty(swpres_adminRole);
    }

   /**
   * Gets the AdminRole
   * @return a org.semanticwb.model.Role
   */
    public org.semanticwb.model.Role getAdminRole()
    {
         org.semanticwb.model.Role ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpres_adminRole);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Role)obj.createGenericInstance();
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
   * Sets the value for the property DocumenterRole
   * @param value DocumenterRole to set
   */

    public void setDocumenterRole(org.semanticwb.model.Role value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swpres_documenterRole, value.getSemanticObject());
        }else
        {
            removeDocumenterRole();
        }
    }
   /**
   * Remove the value for DocumenterRole property
   */

    public void removeDocumenterRole()
    {
        getSemanticObject().removeProperty(swpres_documenterRole);
    }

   /**
   * Gets the DocumenterRole
   * @return a org.semanticwb.model.Role
   */
    public org.semanticwb.model.Role getDocumenterRole()
    {
         org.semanticwb.model.Role ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpres_documenterRole);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Role)obj.createGenericInstance();
         }
         return ret;
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
* Gets the FilterByGroup property
* @return boolean with the FilterByGroup
*/
    public boolean isFilterByGroup()
    {
        return getSemanticObject().getBooleanProperty(swpres_filterByGroup);
    }

/**
* Sets the FilterByGroup property
* @param value long with the FilterByGroup
*/
    public void setFilterByGroup(boolean value)
    {
        getSemanticObject().setBooleanProperty(swpres_filterByGroup, value);
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
   * Sets the value for the property EditDocumentationPage
   * @param value EditDocumentationPage to set
   */

    public void setEditDocumentationPage(org.semanticwb.model.WebPage value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swpres_editDocumentationPage, value.getSemanticObject());
        }else
        {
            removeEditDocumentationPage();
        }
    }
   /**
   * Remove the value for EditDocumentationPage property
   */

    public void removeEditDocumentationPage()
    {
        getSemanticObject().removeProperty(swpres_editDocumentationPage);
    }

   /**
   * Gets the EditDocumentationPage
   * @return a org.semanticwb.model.WebPage
   */
    public org.semanticwb.model.WebPage getEditDocumentationPage()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpres_editDocumentationPage);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property ConfigPage
   * @param value ConfigPage to set
   */

    public void setConfigPage(org.semanticwb.model.WebPage value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swpres_configPage, value.getSemanticObject());
        }else
        {
            removeConfigPage();
        }
    }
   /**
   * Remove the value for ConfigPage property
   */

    public void removeConfigPage()
    {
        getSemanticObject().removeProperty(swpres_configPage);
    }

   /**
   * Gets the ConfigPage
   * @return a org.semanticwb.model.WebPage
   */
    public org.semanticwb.model.WebPage getConfigPage()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpres_configPage);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property ViewRole
   * @param value ViewRole to set
   */

    public void setViewRole(org.semanticwb.model.Role value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swpres_viewRole, value.getSemanticObject());
        }else
        {
            removeViewRole();
        }
    }
   /**
   * Remove the value for ViewRole property
   */

    public void removeViewRole()
    {
        getSemanticObject().removeProperty(swpres_viewRole);
    }

   /**
   * Gets the ViewRole
   * @return a org.semanticwb.model.Role
   */
    public org.semanticwb.model.Role getViewRole()
    {
         org.semanticwb.model.Role ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpres_viewRole);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Role)obj.createGenericInstance();
         }
         return ret;
    }
}
