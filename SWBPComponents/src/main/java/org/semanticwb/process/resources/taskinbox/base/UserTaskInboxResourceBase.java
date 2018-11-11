package org.semanticwb.process.resources.taskinbox.base;


public abstract class UserTaskInboxResourceBase extends org.semanticwb.portal.api.GenericSemResource implements org.semanticwb.process.resources.ProcessResourceConfigurable,org.semanticwb.process.resources.PagingConfigurable
{
    public static final org.semanticwb.platform.SemanticProperty swpres_itemsPerPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#itemsPerPage");
    public static final org.semanticwb.platform.SemanticProperty swpres_showAutoCreated=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#showAutoCreated");
    public static final org.semanticwb.platform.SemanticProperty swpres_configJSP=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#configJSP");
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticProperty swb_semanticResourceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#semanticResourceInv");
    public static final org.semanticwb.platform.SemanticProperty swpres_allowForward=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#allowForward");
    public static final org.semanticwb.platform.SemanticProperty swpres_showProcessWPLink=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#showProcessWPLink");
    public static final org.semanticwb.platform.SemanticProperty swpres_viewJSP=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#viewJSP");
    public static final org.semanticwb.platform.SemanticProperty swpres_displayCols=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#displayCols");
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    public static final org.semanticwb.platform.SemanticProperty swpres_displayMapWp=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#displayMapWp");
    public static final org.semanticwb.platform.SemanticClass swpres_UserTaskInboxResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process/resources#UserTaskInboxResource");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process/resources#UserTaskInboxResource");

    public UserTaskInboxResourceBase()
    {
    }

   /**
   * Constructs a UserTaskInboxResourceBase with a SemanticObject
   * @param base The SemanticObject with the properties for the UserTaskInboxResource
   */
    public UserTaskInboxResourceBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the ItemsPerPage property
* @return int with the ItemsPerPage
*/
    public int getItemsPerPage()
    {
        return getSemanticObject().getIntProperty(swpres_itemsPerPage);
    }

/**
* Sets the ItemsPerPage property
* @param value long with the ItemsPerPage
*/
    public void setItemsPerPage(int value)
    {
        getSemanticObject().setIntProperty(swpres_itemsPerPage, value);
    }

/**
* Gets the ShowAutoCreated property
* @return boolean with the ShowAutoCreated
*/
    public boolean isShowAutoCreated()
    {
        return getSemanticObject().getBooleanProperty(swpres_showAutoCreated);
    }

/**
* Sets the ShowAutoCreated property
* @param value long with the ShowAutoCreated
*/
    public void setShowAutoCreated(boolean value)
    {
        getSemanticObject().setBooleanProperty(swpres_showAutoCreated, value);
    }

/**
* Gets the ConfigJSP property
* @return String with the ConfigJSP
*/
    public String getConfigJSP()
    {
        return getSemanticObject().getProperty(swpres_configJSP);
    }

/**
* Sets the ConfigJSP property
* @param value long with the ConfigJSP
*/
    public void setConfigJSP(String value)
    {
        getSemanticObject().setProperty(swpres_configJSP, value);
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
* Gets the AllowForward property
* @return boolean with the AllowForward
*/
    public boolean isAllowForward()
    {
        return getSemanticObject().getBooleanProperty(swpres_allowForward);
    }

/**
* Sets the AllowForward property
* @param value long with the AllowForward
*/
    public void setAllowForward(boolean value)
    {
        getSemanticObject().setBooleanProperty(swpres_allowForward, value);
    }

/**
* Gets the ShowProcessWPLink property
* @return boolean with the ShowProcessWPLink
*/
    public boolean isShowProcessWPLink()
    {
        return getSemanticObject().getBooleanProperty(swpres_showProcessWPLink);
    }

/**
* Sets the ShowProcessWPLink property
* @param value long with the ShowProcessWPLink
*/
    public void setShowProcessWPLink(boolean value)
    {
        getSemanticObject().setBooleanProperty(swpres_showProcessWPLink, value);
    }

/**
* Gets the ViewJSP property
* @return String with the ViewJSP
*/
    public String getViewJSP()
    {
        return getSemanticObject().getProperty(swpres_viewJSP);
    }

/**
* Sets the ViewJSP property
* @param value long with the ViewJSP
*/
    public void setViewJSP(String value)
    {
        getSemanticObject().setProperty(swpres_viewJSP, value);
    }

/**
* Gets the DisplayCols property
* @return String with the DisplayCols
*/
    public String getDisplayCols()
    {
        return getSemanticObject().getProperty(swpres_displayCols);
    }

/**
* Sets the DisplayCols property
* @param value long with the DisplayCols
*/
    public void setDisplayCols(String value)
    {
        getSemanticObject().setProperty(swpres_displayCols, value);
    }
   /**
   * Sets the value for the property DisplayMapWp
   * @param value DisplayMapWp to set
   */

    public void setDisplayMapWp(org.semanticwb.model.WebPage value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swpres_displayMapWp, value.getSemanticObject());
        }else
        {
            removeDisplayMapWp();
        }
    }
   /**
   * Remove the value for DisplayMapWp property
   */

    public void removeDisplayMapWp()
    {
        getSemanticObject().removeProperty(swpres_displayMapWp);
    }

   /**
   * Gets the DisplayMapWp
   * @return a org.semanticwb.model.WebPage
   */
    public org.semanticwb.model.WebPage getDisplayMapWp()
    {
         org.semanticwb.model.WebPage ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpres_displayMapWp);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.WebPage)obj.createGenericInstance();
         }
         return ret;
    }
}
