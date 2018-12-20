package org.semanticwb.process.model.documentation.base;


public abstract class FormatBase extends org.semanticwb.process.model.documentation.SectionElement implements org.semanticwb.model.Sortable,org.semanticwb.model.Traceable,org.semanticwb.process.model.documentation.Instantiable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.documentation.ElementReferable,org.semanticwb.process.model.documentation.Referable
{
    public static final org.semanticwb.platform.SemanticProperty swpdoc_relatedSubjects=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#relatedSubjects");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_documentType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#documentType");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_keyWords=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#keyWords");
    public static final org.semanticwb.platform.SemanticClass swpdoc_Format=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#Format");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#Format");

    public static class ClassMgr
    {
       /**
       * Returns a list of Format for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.documentation.Format
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Format> listFormats(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Format>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.documentation.Format for all models
       * @return Iterator of org.semanticwb.process.model.documentation.Format
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Format> listFormats()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Format>(it, true);
        }

        public static org.semanticwb.process.model.documentation.Format createFormat(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.documentation.Format.ClassMgr.createFormat(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.documentation.Format
       * @param id Identifier for org.semanticwb.process.model.documentation.Format
       * @param model Model of the org.semanticwb.process.model.documentation.Format
       * @return A org.semanticwb.process.model.documentation.Format
       */
        public static org.semanticwb.process.model.documentation.Format getFormat(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.Format)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.documentation.Format
       * @param id Identifier for org.semanticwb.process.model.documentation.Format
       * @param model Model of the org.semanticwb.process.model.documentation.Format
       * @return A org.semanticwb.process.model.documentation.Format
       */
        public static org.semanticwb.process.model.documentation.Format createFormat(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.Format)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.documentation.Format
       * @param id Identifier for org.semanticwb.process.model.documentation.Format
       * @param model Model of the org.semanticwb.process.model.documentation.Format
       */
        public static void removeFormat(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.documentation.Format
       * @param id Identifier for org.semanticwb.process.model.documentation.Format
       * @param model Model of the org.semanticwb.process.model.documentation.Format
       * @return true if the org.semanticwb.process.model.documentation.Format exists, false otherwise
       */

        public static boolean hasFormat(String id, org.semanticwb.model.SWBModel model)
        {
            return (getFormat(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Format with a determined ProcessElementScope
       * @param value ProcessElementScope of the type org.semanticwb.process.model.ProcessElement
       * @param model Model of the org.semanticwb.process.model.documentation.Format
       * @return Iterator with all the org.semanticwb.process.model.documentation.Format
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Format> listFormatByProcessElementScope(org.semanticwb.process.model.ProcessElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Format> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasProcessElementScope, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Format with a determined ProcessElementScope
       * @param value ProcessElementScope of the type org.semanticwb.process.model.ProcessElement
       * @return Iterator with all the org.semanticwb.process.model.documentation.Format
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Format> listFormatByProcessElementScope(org.semanticwb.process.model.ProcessElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Format> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasProcessElementScope,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Format with a determined RefRepository
       * @param value RefRepository of the type org.semanticwb.process.model.RepositoryElement
       * @param model Model of the org.semanticwb.process.model.documentation.Format
       * @return Iterator with all the org.semanticwb.process.model.documentation.Format
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Format> listFormatByRefRepository(org.semanticwb.process.model.RepositoryElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Format> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_refRepository, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Format with a determined RefRepository
       * @param value RefRepository of the type org.semanticwb.process.model.RepositoryElement
       * @return Iterator with all the org.semanticwb.process.model.documentation.Format
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Format> listFormatByRefRepository(org.semanticwb.process.model.RepositoryElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Format> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_refRepository,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Format with a determined Version
       * @param value Version of the type org.semanticwb.model.VersionInfo
       * @param model Model of the org.semanticwb.process.model.documentation.Format
       * @return Iterator with all the org.semanticwb.process.model.documentation.Format
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Format> listFormatByVersion(org.semanticwb.model.VersionInfo value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Format> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_version, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Format with a determined Version
       * @param value Version of the type org.semanticwb.model.VersionInfo
       * @return Iterator with all the org.semanticwb.process.model.documentation.Format
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Format> listFormatByVersion(org.semanticwb.model.VersionInfo value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Format> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_version,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Format with a determined DocumentSectionInst
       * @param value DocumentSectionInst of the type org.semanticwb.process.model.documentation.DocumentSectionInstance
       * @param model Model of the org.semanticwb.process.model.documentation.Format
       * @return Iterator with all the org.semanticwb.process.model.documentation.Format
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Format> listFormatByDocumentSectionInst(org.semanticwb.process.model.documentation.DocumentSectionInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Format> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentSectionInst, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Format with a determined DocumentSectionInst
       * @param value DocumentSectionInst of the type org.semanticwb.process.model.documentation.DocumentSectionInstance
       * @return Iterator with all the org.semanticwb.process.model.documentation.Format
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Format> listFormatByDocumentSectionInst(org.semanticwb.process.model.documentation.DocumentSectionInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Format> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentSectionInst,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Format with a determined ParentSection
       * @param value ParentSection of the type org.semanticwb.process.model.documentation.DocumentSection
       * @param model Model of the org.semanticwb.process.model.documentation.Format
       * @return Iterator with all the org.semanticwb.process.model.documentation.Format
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Format> listFormatByParentSection(org.semanticwb.process.model.documentation.DocumentSection value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Format> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_parentSection, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Format with a determined ParentSection
       * @param value ParentSection of the type org.semanticwb.process.model.documentation.DocumentSection
       * @return Iterator with all the org.semanticwb.process.model.documentation.Format
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Format> listFormatByParentSection(org.semanticwb.process.model.documentation.DocumentSection value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Format> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_parentSection,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Format with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.Format
       * @return Iterator with all the org.semanticwb.process.model.documentation.Format
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Format> listFormatByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Format> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Format with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.Format
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Format> listFormatByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Format> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Format with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.Format
       * @return Iterator with all the org.semanticwb.process.model.documentation.Format
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Format> listFormatByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Format> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Format with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.Format
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Format> listFormatByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Format> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Format with a determined DocumentTemplate
       * @param value DocumentTemplate of the type org.semanticwb.process.model.documentation.DocumentTemplate
       * @param model Model of the org.semanticwb.process.model.documentation.Format
       * @return Iterator with all the org.semanticwb.process.model.documentation.Format
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Format> listFormatByDocumentTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Format> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Format with a determined DocumentTemplate
       * @param value DocumentTemplate of the type org.semanticwb.process.model.documentation.DocumentTemplate
       * @return Iterator with all the org.semanticwb.process.model.documentation.Format
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Format> listFormatByDocumentTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Format> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentTemplate,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static FormatBase.ClassMgr getFormatClassMgr()
    {
        return new FormatBase.ClassMgr();
    }

   /**
   * Constructs a FormatBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Format
   */
    public FormatBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the RelatedSubjects property
* @return String with the RelatedSubjects
*/
    public String getRelatedSubjects()
    {
        return getSemanticObject().getProperty(swpdoc_relatedSubjects);
    }

/**
* Sets the RelatedSubjects property
* @param value long with the RelatedSubjects
*/
    public void setRelatedSubjects(String value)
    {
        getSemanticObject().setProperty(swpdoc_relatedSubjects, value);
    }

    public String getRelatedSubjects(String lang)
    {
        return getSemanticObject().getProperty(swpdoc_relatedSubjects, null, lang);
    }

    public String getDisplayRelatedSubjects(String lang)
    {
        return getSemanticObject().getLocaleProperty(swpdoc_relatedSubjects, lang);
    }

    public void setRelatedSubjects(String relatedSubjects, String lang)
    {
        getSemanticObject().setProperty(swpdoc_relatedSubjects, relatedSubjects, lang);
    }
   /**
   * Sets the value for the property RefRepository
   * @param value RefRepository to set
   */

    public void setRefRepository(org.semanticwb.process.model.RepositoryElement value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swpdoc_refRepository, value.getSemanticObject());
        }else
        {
            removeRefRepository();
        }
    }
   /**
   * Remove the value for RefRepository property
   */

    public void removeRefRepository()
    {
        getSemanticObject().removeProperty(swpdoc_refRepository);
    }

   /**
   * Gets the RefRepository
   * @return a org.semanticwb.process.model.RepositoryElement
   */
    public org.semanticwb.process.model.RepositoryElement getRefRepository()
    {
         org.semanticwb.process.model.RepositoryElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_refRepository);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.RepositoryElement)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Version
   * @param value Version to set
   */

    public void setVersion(org.semanticwb.model.VersionInfo value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swpdoc_version, value.getSemanticObject());
        }else
        {
            removeVersion();
        }
    }
   /**
   * Remove the value for Version property
   */

    public void removeVersion()
    {
        getSemanticObject().removeProperty(swpdoc_version);
    }

   /**
   * Gets the Version
   * @return a org.semanticwb.model.VersionInfo
   */
    public org.semanticwb.model.VersionInfo getVersion()
    {
         org.semanticwb.model.VersionInfo ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_version);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.VersionInfo)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the DocumentType property
* @return String with the DocumentType
*/
    public String getDocumentType()
    {
        return getSemanticObject().getProperty(swpdoc_documentType);
    }

/**
* Sets the DocumentType property
* @param value long with the DocumentType
*/
    public void setDocumentType(String value)
    {
        getSemanticObject().setProperty(swpdoc_documentType, value);
    }

    public String getDocumentType(String lang)
    {
        return getSemanticObject().getProperty(swpdoc_documentType, null, lang);
    }

    public String getDisplayDocumentType(String lang)
    {
        return getSemanticObject().getLocaleProperty(swpdoc_documentType, lang);
    }

    public void setDocumentType(String documentType, String lang)
    {
        getSemanticObject().setProperty(swpdoc_documentType, documentType, lang);
    }

/**
* Gets the File property
* @return String with the File
*/
    public String getFile()
    {
        return getSemanticObject().getProperty(swpdoc_file);
    }

/**
* Sets the File property
* @param value long with the File
*/
    public void setFile(String value)
    {
        getSemanticObject().setProperty(swpdoc_file, value);
    }

/**
* Gets the KeyWords property
* @return String with the KeyWords
*/
    public String getKeyWords()
    {
        return getSemanticObject().getProperty(swpdoc_keyWords);
    }

/**
* Sets the KeyWords property
* @param value long with the KeyWords
*/
    public void setKeyWords(String value)
    {
        getSemanticObject().setProperty(swpdoc_keyWords, value);
    }

    public String getKeyWords(String lang)
    {
        return getSemanticObject().getProperty(swpdoc_keyWords, null, lang);
    }

    public String getDisplayKeyWords(String lang)
    {
        return getSemanticObject().getLocaleProperty(swpdoc_keyWords, lang);
    }

    public void setKeyWords(String keyWords, String lang)
    {
        getSemanticObject().setProperty(swpdoc_keyWords, keyWords, lang);
    }
}
