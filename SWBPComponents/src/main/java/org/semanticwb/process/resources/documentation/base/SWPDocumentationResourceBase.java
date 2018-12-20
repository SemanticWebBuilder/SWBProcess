package org.semanticwb.process.resources.documentation.base;


public abstract class SWPDocumentationResourceBase extends org.semanticwb.portal.api.GenericSemResource 
{
    public static final org.semanticwb.platform.SemanticProperty swpres_wordIncludeCoverPage=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#wordIncludeCoverPage");
    public static final org.semanticwb.platform.SemanticProperty swpres_wordUseSectionPageBreak=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#wordUseSectionPageBreak");
    public static final org.semanticwb.platform.SemanticProperty swpres_wordActivtyDisplayMode=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#wordActivtyDisplayMode");
    public static final org.semanticwb.platform.SemanticClass swb_Resource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Resource");
    public static final org.semanticwb.platform.SemanticProperty swb_semanticResourceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/ontology#semanticResourceInv");
    public static final org.semanticwb.platform.SemanticProperty swpres_wordTemplateFile=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#wordTemplateFile");
    public static final org.semanticwb.platform.SemanticProperty swpres_wordIncludeHeaderFooter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#wordIncludeHeaderFooter");
    public static final org.semanticwb.platform.SemanticProperty swpres_enableWordExport=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#enableWordExport");
    public static final org.semanticwb.platform.SemanticProperty swpres_useAdvancedEditor=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process/resources#useAdvancedEditor");
    public static final org.semanticwb.platform.SemanticClass swpres_SWPDocumentationResource=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process/resources#SWPDocumentationResource");
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process/resources#SWPDocumentationResource");

    public SWPDocumentationResourceBase()
    {
    }

   /**
   * Constructs a SWPDocumentationResourceBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SWPDocumentationResource
   */
    public SWPDocumentationResourceBase(org.semanticwb.platform.SemanticObject base)
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
* Gets the WordIncludeCoverPage property
* @return boolean with the WordIncludeCoverPage
*/
    public boolean isWordIncludeCoverPage()
    {
        return getSemanticObject().getBooleanProperty(swpres_wordIncludeCoverPage);
    }

/**
* Sets the WordIncludeCoverPage property
* @param value long with the WordIncludeCoverPage
*/
    public void setWordIncludeCoverPage(boolean value)
    {
        getSemanticObject().setBooleanProperty(swpres_wordIncludeCoverPage, value);
    }

/**
* Gets the WordUseSectionPageBreak property
* @return boolean with the WordUseSectionPageBreak
*/
    public boolean isWordUseSectionPageBreak()
    {
        return getSemanticObject().getBooleanProperty(swpres_wordUseSectionPageBreak);
    }

/**
* Sets the WordUseSectionPageBreak property
* @param value long with the WordUseSectionPageBreak
*/
    public void setWordUseSectionPageBreak(boolean value)
    {
        getSemanticObject().setBooleanProperty(swpres_wordUseSectionPageBreak, value);
    }

/**
* Gets the WordActivtyDisplayMode property
* @return String with the WordActivtyDisplayMode
*/
    public String getWordActivtyDisplayMode()
    {
        return getSemanticObject().getProperty(swpres_wordActivtyDisplayMode);
    }

/**
* Sets the WordActivtyDisplayMode property
* @param value long with the WordActivtyDisplayMode
*/
    public void setWordActivtyDisplayMode(String value)
    {
        getSemanticObject().setProperty(swpres_wordActivtyDisplayMode, value);
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
* Gets the WordTemplateFile property
* @return String with the WordTemplateFile
*/
    public String getWordTemplateFile()
    {
        return getSemanticObject().getProperty(swpres_wordTemplateFile);
    }

/**
* Sets the WordTemplateFile property
* @param value long with the WordTemplateFile
*/
    public void setWordTemplateFile(String value)
    {
        getSemanticObject().setProperty(swpres_wordTemplateFile, value);
    }

/**
* Gets the WordIncludeHeaderFooter property
* @return boolean with the WordIncludeHeaderFooter
*/
    public boolean isWordIncludeHeaderFooter()
    {
        return getSemanticObject().getBooleanProperty(swpres_wordIncludeHeaderFooter);
    }

/**
* Sets the WordIncludeHeaderFooter property
* @param value long with the WordIncludeHeaderFooter
*/
    public void setWordIncludeHeaderFooter(boolean value)
    {
        getSemanticObject().setBooleanProperty(swpres_wordIncludeHeaderFooter, value);
    }

/**
* Gets the EnableWordExport property
* @return boolean with the EnableWordExport
*/
    public boolean isEnableWordExport()
    {
        return getSemanticObject().getBooleanProperty(swpres_enableWordExport);
    }

/**
* Sets the EnableWordExport property
* @param value long with the EnableWordExport
*/
    public void setEnableWordExport(boolean value)
    {
        getSemanticObject().setBooleanProperty(swpres_enableWordExport, value);
    }

/**
* Gets the UseAdvancedEditor property
* @return boolean with the UseAdvancedEditor
*/
    public boolean isUseAdvancedEditor()
    {
        return getSemanticObject().getBooleanProperty(swpres_useAdvancedEditor);
    }

/**
* Sets the UseAdvancedEditor property
* @param value long with the UseAdvancedEditor
*/
    public void setUseAdvancedEditor(boolean value)
    {
        getSemanticObject().setBooleanProperty(swpres_useAdvancedEditor, value);
    }
}
