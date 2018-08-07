package org.semanticwb.process.model.documentation.base;

public interface ReferableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticClass swp_RepositoryElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#RepositoryElement");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_refRepository=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#refRepository");
   /**
   * Objeto utilizado para identificar una version de algun componente 
   */
    public static final org.semanticwb.platform.SemanticClass swb_VersionInfo=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#VersionInfo");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_version=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#version");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_file=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#file");
    public static final org.semanticwb.platform.SemanticClass swpdoc_Referable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#Referable");

   /**
   * Sets a value from the property RefRepository
   * @param value An instance of org.semanticwb.process.model.RepositoryElement
   */
    public void setRefRepository(org.semanticwb.process.model.RepositoryElement value);

   /**
   * Remove the value from the property RefRepository
   */
    public void removeRefRepository();

    public org.semanticwb.process.model.RepositoryElement getRefRepository();

   /**
   * Sets a value from the property Version
   * @param value An instance of org.semanticwb.model.VersionInfo
   */
    public void setVersion(org.semanticwb.model.VersionInfo value);

   /**
   * Remove the value from the property Version
   */
    public void removeVersion();

    public org.semanticwb.model.VersionInfo getVersion();

    public String getFile();

    public void setFile(String value);
}
