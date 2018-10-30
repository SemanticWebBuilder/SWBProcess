package org.semanticwb.process.resources.base;

   /**
   * Interfaz común para elementos con configuración para paginación 
   */
public interface PagingConfigurableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swpres_itemsPerPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#itemsPerPage");
   /**
   * Interfaz común para elementos con configuración para paginación 
   */
    public static final org.semanticwb.platform.SemanticClass swpres_PagingConfigurable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process/resources#PagingConfigurable");

    public int getItemsPerPage();

    public void setItemsPerPage(int value);
}
