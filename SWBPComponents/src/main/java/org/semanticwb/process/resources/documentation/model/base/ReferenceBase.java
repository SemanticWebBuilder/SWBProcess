package org.semanticwb.process.resources.documentation.model.base;


public abstract class ReferenceBase extends org.semanticwb.process.resources.documentation.model.SectionElement implements org.semanticwb.process.resources.documentation.model.Prefixeable, org.semanticwb.process.resources.documentation.model.Instantiable,org.semanticwb.model.Traceable, org.semanticwb.process.resources.documentation.model.Numerable,org.semanticwb.model.Sortable,org.semanticwb.model.Descriptiveable, org.semanticwb.process.resources.documentation.model.ElementReferable, org.semanticwb.process.resources.documentation.model.Referable
{
    public static final org.semanticwb.platform.SemanticProperty swpdoc_typeReference=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#typeReference");
    public static final org.semanticwb.platform.SemanticClass swpdoc_Reference=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#Reference");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#Reference");

    public static class ClassMgr
    {
       /**
       * Returns a list of Reference for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.resources.documentation.model.Reference
       */

        public static java.util.Iterator<org.semanticwb.process.resources.documentation.model.Reference> listReferences(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.documentation.model.Reference>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.resources.documentation.model.Reference for all models
       * @return Iterator of org.semanticwb.process.resources.documentation.model.Reference
       */

        public static java.util.Iterator<org.semanticwb.process.resources.documentation.model.Reference> listReferences()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.documentation.model.Reference>(it, true);
        }

        public static org.semanticwb.process.resources.documentation.model.Reference createReference(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.resources.documentation.model.Reference.ClassMgr.createReference(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.resources.documentation.model.Reference
       * @param id Identifier for org.semanticwb.process.resources.documentation.model.Reference
       * @param model Model of the org.semanticwb.process.resources.documentation.model.Reference
       * @return A org.semanticwb.process.resources.documentation.model.Reference
       */
        public static org.semanticwb.process.resources.documentation.model.Reference getReference(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.resources.documentation.model.Reference)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.resources.documentation.model.Reference
       * @param id Identifier for org.semanticwb.process.resources.documentation.model.Reference
       * @param model Model of the org.semanticwb.process.resources.documentation.model.Reference
       * @return A org.semanticwb.process.resources.documentation.model.Reference
       */
        public static org.semanticwb.process.resources.documentation.model.Reference createReference(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.resources.documentation.model.Reference)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.resources.documentation.model.Reference
       * @param id Identifier for org.semanticwb.process.resources.documentation.model.Reference
       * @param model Model of the org.semanticwb.process.resources.documentation.model.Reference
       */
        public static void removeReference(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.resources.documentation.model.Reference
       * @param id Identifier for org.semanticwb.process.resources.documentation.model.Reference
       * @param model Model of the org.semanticwb.process.resources.documentation.model.Reference
       * @return true if the org.semanticwb.process.resources.documentation.model.Reference exists, false otherwise
       */

        public static boolean hasReference(String id, org.semanticwb.model.SWBModel model)
        {
            return (getReference(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.resources.documentation.model.Reference with a determined ProcessElementScope
       * @param value ProcessElementScope of the type org.semanticwb.process.model.ProcessElement
       * @param model Model of the org.semanticwb.process.resources.documentation.model.Reference
       * @return Iterator with all the org.semanticwb.process.resources.documentation.model.Reference
       */

        public static java.util.Iterator<org.semanticwb.process.resources.documentation.model.Reference> listReferenceByProcessElementScope(org.semanticwb.process.model.ProcessElement value, org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.documentation.model.Reference> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasProcessElementScope, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.documentation.model.Reference with a determined ProcessElementScope
       * @param value ProcessElementScope of the type org.semanticwb.process.model.ProcessElement
       * @return Iterator with all the org.semanticwb.process.resources.documentation.model.Reference
       */

        public static java.util.Iterator<org.semanticwb.process.resources.documentation.model.Reference> listReferenceByProcessElementScope(org.semanticwb.process.model.ProcessElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.documentation.model.Reference> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasProcessElementScope,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.documentation.model.Reference with a determined RefRepository
       * @param value RefRepository of the type org.semanticwb.process.model.RepositoryElement
       * @param model Model of the org.semanticwb.process.resources.documentation.model.Reference
       * @return Iterator with all the org.semanticwb.process.resources.documentation.model.Reference
       */

        public static java.util.Iterator<org.semanticwb.process.resources.documentation.model.Reference> listReferenceByRefRepository(org.semanticwb.process.model.RepositoryElement value, org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.documentation.model.Reference> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_refRepository, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.documentation.model.Reference with a determined RefRepository
       * @param value RefRepository of the type org.semanticwb.process.model.RepositoryElement
       * @return Iterator with all the org.semanticwb.process.resources.documentation.model.Reference
       */

        public static java.util.Iterator<org.semanticwb.process.resources.documentation.model.Reference> listReferenceByRefRepository(org.semanticwb.process.model.RepositoryElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.documentation.model.Reference> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_refRepository,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.documentation.model.Reference with a determined Version
       * @param value Version of the type org.semanticwb.model.VersionInfo
       * @param model Model of the org.semanticwb.process.resources.documentation.model.Reference
       * @return Iterator with all the org.semanticwb.process.resources.documentation.model.Reference
       */

        public static java.util.Iterator<org.semanticwb.process.resources.documentation.model.Reference> listReferenceByVersion(org.semanticwb.model.VersionInfo value, org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.documentation.model.Reference> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_version, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.documentation.model.Reference with a determined Version
       * @param value Version of the type org.semanticwb.model.VersionInfo
       * @return Iterator with all the org.semanticwb.process.resources.documentation.model.Reference
       */

        public static java.util.Iterator<org.semanticwb.process.resources.documentation.model.Reference> listReferenceByVersion(org.semanticwb.model.VersionInfo value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.documentation.model.Reference> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_version,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.documentation.model.Reference with a determined DocumentSectionInst
       * @param value DocumentSectionInst of the type org.semanticwb.process.resources.documentation.model.DocumentSectionInstance
       * @param model Model of the org.semanticwb.process.resources.documentation.model.Reference
       * @return Iterator with all the org.semanticwb.process.resources.documentation.model.Reference
       */

        public static java.util.Iterator<org.semanticwb.process.resources.documentation.model.Reference> listReferenceByDocumentSectionInst(org.semanticwb.process.resources.documentation.model.DocumentSectionInstance value, org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.documentation.model.Reference> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentSectionInst, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.documentation.model.Reference with a determined DocumentSectionInst
       * @param value DocumentSectionInst of the type org.semanticwb.process.resources.documentation.model.DocumentSectionInstance
       * @return Iterator with all the org.semanticwb.process.resources.documentation.model.Reference
       */

        public static java.util.Iterator<org.semanticwb.process.resources.documentation.model.Reference> listReferenceByDocumentSectionInst(org.semanticwb.process.resources.documentation.model.DocumentSectionInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.documentation.model.Reference> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentSectionInst,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.documentation.model.Reference with a determined ParentSection
       * @param value ParentSection of the type org.semanticwb.process.resources.documentation.model.DocumentSection
       * @param model Model of the org.semanticwb.process.resources.documentation.model.Reference
       * @return Iterator with all the org.semanticwb.process.resources.documentation.model.Reference
       */

        public static java.util.Iterator<org.semanticwb.process.resources.documentation.model.Reference> listReferenceByParentSection(org.semanticwb.process.resources.documentation.model.DocumentSection value, org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.documentation.model.Reference> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_parentSection, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.documentation.model.Reference with a determined ParentSection
       * @param value ParentSection of the type org.semanticwb.process.resources.documentation.model.DocumentSection
       * @return Iterator with all the org.semanticwb.process.resources.documentation.model.Reference
       */

        public static java.util.Iterator<org.semanticwb.process.resources.documentation.model.Reference> listReferenceByParentSection(org.semanticwb.process.resources.documentation.model.DocumentSection value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.documentation.model.Reference> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_parentSection,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.documentation.model.Reference with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.resources.documentation.model.Reference
       * @return Iterator with all the org.semanticwb.process.resources.documentation.model.Reference
       */

        public static java.util.Iterator<org.semanticwb.process.resources.documentation.model.Reference> listReferenceByModifiedBy(org.semanticwb.model.User value, org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.documentation.model.Reference> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.documentation.model.Reference with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.resources.documentation.model.Reference
       */

        public static java.util.Iterator<org.semanticwb.process.resources.documentation.model.Reference> listReferenceByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.documentation.model.Reference> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.documentation.model.Reference with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.resources.documentation.model.Reference
       * @return Iterator with all the org.semanticwb.process.resources.documentation.model.Reference
       */

        public static java.util.Iterator<org.semanticwb.process.resources.documentation.model.Reference> listReferenceByCreator(org.semanticwb.model.User value, org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.documentation.model.Reference> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.documentation.model.Reference with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.resources.documentation.model.Reference
       */

        public static java.util.Iterator<org.semanticwb.process.resources.documentation.model.Reference> listReferenceByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.documentation.model.Reference> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.documentation.model.Reference with a determined DocumentTemplate
       * @param value DocumentTemplate of the type org.semanticwb.process.resources.documentation.model.DocumentTemplate
       * @param model Model of the org.semanticwb.process.resources.documentation.model.Reference
       * @return Iterator with all the org.semanticwb.process.resources.documentation.model.Reference
       */

        public static java.util.Iterator<org.semanticwb.process.resources.documentation.model.Reference> listReferenceByDocumentTemplate(org.semanticwb.process.resources.documentation.model.DocumentTemplate value, org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.documentation.model.Reference> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.resources.documentation.model.Reference with a determined DocumentTemplate
       * @param value DocumentTemplate of the type org.semanticwb.process.resources.documentation.model.DocumentTemplate
       * @return Iterator with all the org.semanticwb.process.resources.documentation.model.Reference
       */

        public static java.util.Iterator<org.semanticwb.process.resources.documentation.model.Reference> listReferenceByDocumentTemplate(org.semanticwb.process.resources.documentation.model.DocumentTemplate value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.resources.documentation.model.Reference> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentTemplate,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ReferenceBase.ClassMgr getReferenceClassMgr()
    {
        return new ReferenceBase.ClassMgr();
    }

   /**
   * Constructs a ReferenceBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Reference
   */
    public ReferenceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Prefix property
* @return String with the Prefix
*/
    public String getPrefix()
    {
        return getSemanticObject().getProperty(swpdoc_prefix);
    }

/**
* Sets the Prefix property
* @param value long with the Prefix
*/
    public void setPrefix(String value)
    {
        getSemanticObject().setProperty(swpdoc_prefix, value);
    }

    public String getPrefix(String lang)
    {
        return getSemanticObject().getProperty(swpdoc_prefix, null, lang);
    }

    public String getDisplayPrefix(String lang)
    {
        return getSemanticObject().getLocaleProperty(swpdoc_prefix, lang);
    }

    public void setPrefix(String prefix, String lang)
    {
        getSemanticObject().setProperty(swpdoc_prefix, prefix, lang);
    }

/**
* Gets the Number property
* @return String with the Number
*/
    public String getNumber()
    {
        return getSemanticObject().getProperty(swpdoc_number);
    }

/**
* Sets the Number property
* @param value long with the Number
*/
    public void setNumber(String value)
    {
        getSemanticObject().setProperty(swpdoc_number, value);
    }

    public String getNumber(String lang)
    {
        return getSemanticObject().getProperty(swpdoc_number, null, lang);
    }

    public String getDisplayNumber(String lang)
    {
        return getSemanticObject().getLocaleProperty(swpdoc_number, lang);
    }

    public void setNumber(String number, String lang)
    {
        getSemanticObject().setProperty(swpdoc_number, number, lang);
    }

/**
* Gets the TypeReference property
* @return String with the TypeReference
*/
    public String getTypeReference()
    {
        return getSemanticObject().getProperty(swpdoc_typeReference);
    }

/**
* Sets the TypeReference property
* @param value long with the TypeReference
*/
    public void setTypeReference(String value)
    {
        getSemanticObject().setProperty(swpdoc_typeReference, value);
    }

    public String getTypeReference(String lang)
    {
        return getSemanticObject().getProperty(swpdoc_typeReference, null, lang);
    }

    public String getDisplayTypeReference(String lang)
    {
        return getSemanticObject().getLocaleProperty(swpdoc_typeReference, lang);
    }

    public void setTypeReference(String typeReference, String lang)
    {
        getSemanticObject().setProperty(swpdoc_typeReference, typeReference, lang);
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
}
