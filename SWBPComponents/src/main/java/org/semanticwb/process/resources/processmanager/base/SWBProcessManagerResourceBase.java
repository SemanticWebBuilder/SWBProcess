package org.semanticwb.process.resources.processmanager.base;


public abstract class SWBProcessManagerResourceBase extends org.semanticwb.portal.api.GenericSemResource implements org.semanticwb.process.resources.RoleAdminConfigurable
{
    public static final org.semanticwb.platform.SemanticProperty swpres_inheritProcessAccessRoles=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#inheritProcessAccessRoles");
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    public static final org.semanticwb.platform.SemanticProperty swpres_templatesPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#templatesPage");
    public static final org.semanticwb.platform.SemanticClass swb_Role=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Role");
    public static final org.semanticwb.platform.SemanticProperty swpres_adminRole=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#adminRole");
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticProperty swb_semanticResourceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#semanticResourceInv");
    public static final org.semanticwb.platform.SemanticProperty swpres_documenterRole=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#documenterRole");
    public static final org.semanticwb.platform.SemanticProperty swpres_editDocumentationPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#editDocumentationPage");
    public static final org.semanticwb.platform.SemanticProperty swpres_configPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#configPage");
    public static final org.semanticwb.platform.SemanticProperty swpres_viewRole=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#viewRole");
    public static final org.semanticwb.platform.SemanticClass swpres_SWBProcessManagerResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process/resources#SWBProcessManagerResource");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process/resources#SWBProcessManagerResource");

    public SWBProcessManagerResourceBase()
    {
    }

   /**
   * Constructs a SWBProcessManagerResourceBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SWBProcessManagerResource
   */
    public SWBProcessManagerResourceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    /*
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() 
    {
        return getSemanticObject().hashCode();
    }

    /*
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) 
    {
        if(obj==null)return false;
        return hashCode()==obj.hashCode();
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
   * Sets the value for the property Resource
   * @param value Resource to set
   */

    public void setResource(org.semanticwb.model.Resource value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_semanticResourceInv, value.getSemanticObject());
        }else
        {
            removeResource();
        }
    }
   /**
   * Remove the value for Resource property
   */

    public void removeResource()
    {
        getSemanticObject().removeProperty(swb_semanticResourceInv);
    }

   /**
   * Gets the Resource
   * @return a org.semanticwb.model.Resource
   */
    public org.semanticwb.model.Resource getResource()
    {
         org.semanticwb.model.Resource ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_semanticResourceInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.Resource)obj.createGenericInstance();
         }
         return ret;
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
