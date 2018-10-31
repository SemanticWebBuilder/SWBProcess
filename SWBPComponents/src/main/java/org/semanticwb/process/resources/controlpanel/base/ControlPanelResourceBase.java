package org.semanticwb.process.resources.controlpanel.base;


public abstract class ControlPanelResourceBase extends org.semanticwb.portal.api.GenericSemResource implements org.semanticwb.process.resources.ProcessResourceConfigurable,org.semanticwb.process.resources.PagingConfigurable
{
    public static final org.semanticwb.platform.SemanticProperty swpres_showStatusChart=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#showStatusChart");
    public static final org.semanticwb.platform.SemanticProperty swpres_docsJSP=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#docsJSP");
    public static final org.semanticwb.platform.SemanticProperty swpres_itemsPerPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#itemsPerPage");
    public static final org.semanticwb.platform.SemanticProperty swpres_showCharts=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#showCharts");
    public static final org.semanticwb.platform.SemanticProperty swpres_configJSP=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#configJSP");
    public static final org.semanticwb.platform.SemanticProperty swpres_chartsEngine=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#chartsEngine");
    public static final org.semanticwb.platform.SemanticProperty swpres_showInstanceChart=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#showInstanceChart");
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticProperty swb_semanticResourceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#semanticResourceInv");
    public static final org.semanticwb.platform.SemanticProperty swpres_showPartChart=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#showPartChart");
    public static final org.semanticwb.platform.SemanticProperty swpres_showResponseChart=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#showResponseChart");
    public static final org.semanticwb.platform.SemanticProperty swpres_viewJSP=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#viewJSP");
    public static final org.semanticwb.platform.SemanticProperty swpres_displayCols=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#displayCols");
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    public static final org.semanticwb.platform.SemanticProperty swpres_displayMapWp=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#displayMapWp");
    public static final org.semanticwb.platform.SemanticClass swpres_ControlPanelResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process/resources#ControlPanelResource");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process/resources#ControlPanelResource");

    public ControlPanelResourceBase()
    {
    }

   /**
   * Constructs a ControlPanelResourceBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ControlPanelResource
   */
    public ControlPanelResourceBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the ShowStatusChart property
* @return boolean with the ShowStatusChart
*/
    public boolean isShowStatusChart()
    {
        return getSemanticObject().getBooleanProperty(swpres_showStatusChart);
    }

/**
* Sets the ShowStatusChart property
* @param value long with the ShowStatusChart
*/
    public void setShowStatusChart(boolean value)
    {
        getSemanticObject().setBooleanProperty(swpres_showStatusChart, value);
    }

/**
* Gets the DocsJSP property
* @return String with the DocsJSP
*/
    public String getDocsJSP()
    {
        return getSemanticObject().getProperty(swpres_docsJSP);
    }

/**
* Sets the DocsJSP property
* @param value long with the DocsJSP
*/
    public void setDocsJSP(String value)
    {
        getSemanticObject().setProperty(swpres_docsJSP, value);
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
* Gets the ShowCharts property
* @return boolean with the ShowCharts
*/
    public boolean isShowCharts()
    {
        return getSemanticObject().getBooleanProperty(swpres_showCharts);
    }

/**
* Sets the ShowCharts property
* @param value long with the ShowCharts
*/
    public void setShowCharts(boolean value)
    {
        getSemanticObject().setBooleanProperty(swpres_showCharts, value);
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
* Gets the ChartsEngine property
* @return String with the ChartsEngine
*/
    public String getChartsEngine()
    {
        return getSemanticObject().getProperty(swpres_chartsEngine);
    }

/**
* Sets the ChartsEngine property
* @param value long with the ChartsEngine
*/
    public void setChartsEngine(String value)
    {
        getSemanticObject().setProperty(swpres_chartsEngine, value);
    }

/**
* Gets the ShowInstanceChart property
* @return boolean with the ShowInstanceChart
*/
    public boolean isShowInstanceChart()
    {
        return getSemanticObject().getBooleanProperty(swpres_showInstanceChart);
    }

/**
* Sets the ShowInstanceChart property
* @param value long with the ShowInstanceChart
*/
    public void setShowInstanceChart(boolean value)
    {
        getSemanticObject().setBooleanProperty(swpres_showInstanceChart, value);
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
* Gets the ShowPartChart property
* @return boolean with the ShowPartChart
*/
    public boolean isShowPartChart()
    {
        return getSemanticObject().getBooleanProperty(swpres_showPartChart);
    }

/**
* Sets the ShowPartChart property
* @param value long with the ShowPartChart
*/
    public void setShowPartChart(boolean value)
    {
        getSemanticObject().setBooleanProperty(swpres_showPartChart, value);
    }

/**
* Gets the ShowResponseChart property
* @return boolean with the ShowResponseChart
*/
    public boolean isShowResponseChart()
    {
        return getSemanticObject().getBooleanProperty(swpres_showResponseChart);
    }

/**
* Sets the ShowResponseChart property
* @param value long with the ShowResponseChart
*/
    public void setShowResponseChart(boolean value)
    {
        getSemanticObject().setBooleanProperty(swpres_showResponseChart, value);
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
