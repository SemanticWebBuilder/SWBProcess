package org.semanticwb.process.resources.base;

public interface RoleAdminConfigurableBase extends org.semanticwb.model.GenericObject
{
   /**
   * Objeto que define un Role dentro de un repositorio de usuarios aplicable a un Usuario para filtrar componente, seccion, plantillas, etc. 
   */
    public static final org.semanticwb.platform.SemanticClass swb_Role=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Role");
    public static final org.semanticwb.platform.SemanticProperty swpres_adminRole=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#adminRole");
    public static final org.semanticwb.platform.SemanticProperty swpres_viewRole=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#viewRole");
    public static final org.semanticwb.platform.SemanticClass swpres_RoleAdminConfigurable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process/resources#RoleAdminConfigurable");

   /**
   * Sets a value from the property AdminRole
   * @param value An instance of org.semanticwb.model.Role
   */
    public void setAdminRole(org.semanticwb.model.Role value);

   /**
   * Remove the value from the property AdminRole
   */
    public void removeAdminRole();

    public org.semanticwb.model.Role getAdminRole();

   /**
   * Sets a value from the property ViewRole
   * @param value An instance of org.semanticwb.model.Role
   */
    public void setViewRole(org.semanticwb.model.Role value);

   /**
   * Remove the value from the property ViewRole
   */
    public void removeViewRole();

    public org.semanticwb.model.Role getViewRole();
}
