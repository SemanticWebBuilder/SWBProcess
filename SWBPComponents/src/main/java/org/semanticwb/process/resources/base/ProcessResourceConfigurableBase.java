package org.semanticwb.process.resources.base;

public interface ProcessResourceConfigurableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swpres_configJSP=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#configJSP");
    public static final org.semanticwb.platform.SemanticProperty swpres_filterByGroup=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#filterByGroup");
    public static final org.semanticwb.platform.SemanticProperty swpres_viewJSP=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#viewJSP");
   /**
   * Almacena, separados por pipes, los nombres de las propiedades a desplegar como columnas en el panel de monitoreo. 
   */
    public static final org.semanticwb.platform.SemanticProperty swpres_displayCols=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#displayCols");
   /**
   * Una Página Web es el elemento de SemanticWebBuilder a través del cual se estructura la información del portal. 
   */
    public static final org.semanticwb.platform.SemanticClass swb_WebPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#WebPage");
    public static final org.semanticwb.platform.SemanticProperty swpres_displayMapWp=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#displayMapWp");
    public static final org.semanticwb.platform.SemanticClass swpres_ProcessResourceConfigurable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process/resources#ProcessResourceConfigurable");

    public String getConfigJSP();

    public void setConfigJSP(String value);

    public boolean isFilterByGroup();

    public void setFilterByGroup(boolean value);

    public String getViewJSP();

    public void setViewJSP(String value);

    public String getDisplayCols();

    public void setDisplayCols(String value);

   /**
   * Sets a value from the property DisplayMapWp
   * @param value An instance of org.semanticwb.model.WebPage
   */
    public void setDisplayMapWp(org.semanticwb.model.WebPage value);

   /**
   * Remove the value from the property DisplayMapWp
   */
    public void removeDisplayMapWp();

    public org.semanticwb.model.WebPage getDisplayMapWp();
}
