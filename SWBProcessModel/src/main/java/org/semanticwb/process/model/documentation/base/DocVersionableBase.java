package org.semanticwb.process.model.documentation.base;

public interface DocVersionableBase extends org.semanticwb.model.GenericObject
{
    public static final org.semanticwb.platform.SemanticProperty swpdoc_versionValue=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#versionValue");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_versionComment=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#versionComment");
    public static final org.semanticwb.platform.SemanticClass swpdoc_DocVersionable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#DocVersionable");

    public String getVersionValue();

    public void setVersionValue(String value);

    public String getVersionComment();

    public void setVersionComment(String value);
}
