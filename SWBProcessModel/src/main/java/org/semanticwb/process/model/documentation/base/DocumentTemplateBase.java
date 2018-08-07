package org.semanticwb.process.model.documentation.base;


   /**
   * Plantilla de documentacion de procesos 
   */
public abstract class DocumentTemplateBase extends org.semanticwb.process.model.documentation.DocumentationElement implements org.semanticwb.process.model.documentation.DocVersionable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swpdoc_DocumentationInstance=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#DocumentationInstance");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_hasDocumentationInstanceInv=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#hasDocumentationInstanceInv");
   /**
   * Plantilla de documentacion de procesos
   */
    public static final org.semanticwb.platform.SemanticClass swpdoc_DocumentTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#DocumentTemplate");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_nextTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#nextTemplate");
    public static final org.semanticwb.platform.SemanticClass swpdoc_DocumentSection=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#DocumentSection");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_hasDocumentSection=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#hasDocumentSection");
   /**
   * Contenedor para versionamiento de plantillas de documentacion
   */
    public static final org.semanticwb.platform.SemanticClass swpdoc_TemplateContainer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#TemplateContainer");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_templateContainer=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#templateContainer");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_previousTemplate=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#previousTemplate");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#DocumentTemplate");

    public static class ClassMgr
    {
       /**
       * Returns a list of DocumentTemplate for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.documentation.DocumentTemplate
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentTemplate> listDocumentTemplates(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentTemplate>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.documentation.DocumentTemplate for all models
       * @return Iterator of org.semanticwb.process.model.documentation.DocumentTemplate
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentTemplate> listDocumentTemplates()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentTemplate>(it, true);
        }

        public static org.semanticwb.process.model.documentation.DocumentTemplate createDocumentTemplate(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.documentation.DocumentTemplate.ClassMgr.createDocumentTemplate(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.documentation.DocumentTemplate
       * @param id Identifier for org.semanticwb.process.model.documentation.DocumentTemplate
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentTemplate
       * @return A org.semanticwb.process.model.documentation.DocumentTemplate
       */
        public static org.semanticwb.process.model.documentation.DocumentTemplate getDocumentTemplate(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.DocumentTemplate)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.documentation.DocumentTemplate
       * @param id Identifier for org.semanticwb.process.model.documentation.DocumentTemplate
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentTemplate
       * @return A org.semanticwb.process.model.documentation.DocumentTemplate
       */
        public static org.semanticwb.process.model.documentation.DocumentTemplate createDocumentTemplate(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.DocumentTemplate)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.documentation.DocumentTemplate
       * @param id Identifier for org.semanticwb.process.model.documentation.DocumentTemplate
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentTemplate
       */
        public static void removeDocumentTemplate(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.documentation.DocumentTemplate
       * @param id Identifier for org.semanticwb.process.model.documentation.DocumentTemplate
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentTemplate
       * @return true if the org.semanticwb.process.model.documentation.DocumentTemplate exists, false otherwise
       */

        public static boolean hasDocumentTemplate(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDocumentTemplate(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentTemplate with a determined DocumentationInstance
       * @param value DocumentationInstance of the type org.semanticwb.process.model.documentation.DocumentationInstance
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentTemplate
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentTemplate
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentTemplate> listDocumentTemplateByDocumentationInstance(org.semanticwb.process.model.documentation.DocumentationInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentTemplate> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasDocumentationInstanceInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentTemplate with a determined DocumentationInstance
       * @param value DocumentationInstance of the type org.semanticwb.process.model.documentation.DocumentationInstance
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentTemplate
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentTemplate> listDocumentTemplateByDocumentationInstance(org.semanticwb.process.model.documentation.DocumentationInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentTemplate> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasDocumentationInstanceInv,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentTemplate with a determined NextTemplate
       * @param value NextTemplate of the type org.semanticwb.process.model.documentation.DocumentTemplate
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentTemplate
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentTemplate
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentTemplate> listDocumentTemplateByNextTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentTemplate> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_nextTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentTemplate with a determined NextTemplate
       * @param value NextTemplate of the type org.semanticwb.process.model.documentation.DocumentTemplate
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentTemplate
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentTemplate> listDocumentTemplateByNextTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentTemplate> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_nextTemplate,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentTemplate with a determined DocumentSection
       * @param value DocumentSection of the type org.semanticwb.process.model.documentation.DocumentSection
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentTemplate
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentTemplate
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentTemplate> listDocumentTemplateByDocumentSection(org.semanticwb.process.model.documentation.DocumentSection value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentTemplate> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasDocumentSection, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentTemplate with a determined DocumentSection
       * @param value DocumentSection of the type org.semanticwb.process.model.documentation.DocumentSection
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentTemplate
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentTemplate> listDocumentTemplateByDocumentSection(org.semanticwb.process.model.documentation.DocumentSection value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentTemplate> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasDocumentSection,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentTemplate with a determined TemplateContainer
       * @param value TemplateContainer of the type org.semanticwb.process.model.documentation.TemplateContainer
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentTemplate
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentTemplate
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentTemplate> listDocumentTemplateByTemplateContainer(org.semanticwb.process.model.documentation.TemplateContainer value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentTemplate> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_templateContainer, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentTemplate with a determined TemplateContainer
       * @param value TemplateContainer of the type org.semanticwb.process.model.documentation.TemplateContainer
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentTemplate
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentTemplate> listDocumentTemplateByTemplateContainer(org.semanticwb.process.model.documentation.TemplateContainer value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentTemplate> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_templateContainer,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentTemplate with a determined PreviousTemplate
       * @param value PreviousTemplate of the type org.semanticwb.process.model.documentation.DocumentTemplate
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentTemplate
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentTemplate
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentTemplate> listDocumentTemplateByPreviousTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentTemplate> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_previousTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentTemplate with a determined PreviousTemplate
       * @param value PreviousTemplate of the type org.semanticwb.process.model.documentation.DocumentTemplate
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentTemplate
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentTemplate> listDocumentTemplateByPreviousTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentTemplate> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_previousTemplate,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentTemplate with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentTemplate
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentTemplate
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentTemplate> listDocumentTemplateByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentTemplate> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentTemplate with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentTemplate
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentTemplate> listDocumentTemplateByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentTemplate> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentTemplate with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentTemplate
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentTemplate
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentTemplate> listDocumentTemplateByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentTemplate> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentTemplate with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentTemplate
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentTemplate> listDocumentTemplateByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentTemplate> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DocumentTemplateBase.ClassMgr getDocumentTemplateClassMgr()
    {
        return new DocumentTemplateBase.ClassMgr();
    }

   /**
   * Constructs a DocumentTemplateBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DocumentTemplate
   */
    public DocumentTemplateBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.process.model.documentation.DocumentationInstance
   * @return A GenericIterator with all the org.semanticwb.process.model.documentation.DocumentationInstance
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentationInstance> listDocumentationInstances()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentationInstance>(getSemanticObject().listObjectProperties(swpdoc_hasDocumentationInstanceInv));
    }

   /**
   * Gets true if has a DocumentationInstance
   * @param value org.semanticwb.process.model.documentation.DocumentationInstance to verify
   * @return true if the org.semanticwb.process.model.documentation.DocumentationInstance exists, false otherwise
   */
    public boolean hasDocumentationInstance(org.semanticwb.process.model.documentation.DocumentationInstance value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swpdoc_hasDocumentationInstanceInv,value.getSemanticObject());
        }
        return ret;
    }

   /**
   * Gets the DocumentationInstance
   * @return a org.semanticwb.process.model.documentation.DocumentationInstance
   */
    public org.semanticwb.process.model.documentation.DocumentationInstance getDocumentationInstance()
    {
         org.semanticwb.process.model.documentation.DocumentationInstance ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_hasDocumentationInstanceInv);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.documentation.DocumentationInstance)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property NextTemplate
   * @param value NextTemplate to set
   */

    public void setNextTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swpdoc_nextTemplate, value.getSemanticObject());
        }else
        {
            removeNextTemplate();
        }
    }
   /**
   * Remove the value for NextTemplate property
   */

    public void removeNextTemplate()
    {
        getSemanticObject().removeProperty(swpdoc_nextTemplate);
    }

   /**
   * Gets the NextTemplate
   * @return a org.semanticwb.process.model.documentation.DocumentTemplate
   */
    public org.semanticwb.process.model.documentation.DocumentTemplate getNextTemplate()
    {
         org.semanticwb.process.model.documentation.DocumentTemplate ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_nextTemplate);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.documentation.DocumentTemplate)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the VersionValue property
* @return String with the VersionValue
*/
    public String getVersionValue()
    {
        return getSemanticObject().getProperty(swpdoc_versionValue);
    }

/**
* Sets the VersionValue property
* @param value long with the VersionValue
*/
    public void setVersionValue(String value)
    {
        getSemanticObject().setProperty(swpdoc_versionValue, value);
    }
   /**
   * Gets all the org.semanticwb.process.model.documentation.DocumentSection
   * @return A GenericIterator with all the org.semanticwb.process.model.documentation.DocumentSection
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentSection> listDocumentSections()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentSection>(getSemanticObject().listObjectProperties(swpdoc_hasDocumentSection));
    }

   /**
   * Gets true if has a DocumentSection
   * @param value org.semanticwb.process.model.documentation.DocumentSection to verify
   * @return true if the org.semanticwb.process.model.documentation.DocumentSection exists, false otherwise
   */
    public boolean hasDocumentSection(org.semanticwb.process.model.documentation.DocumentSection value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swpdoc_hasDocumentSection,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a DocumentSection
   * @param value org.semanticwb.process.model.documentation.DocumentSection to add
   */

    public void addDocumentSection(org.semanticwb.process.model.documentation.DocumentSection value)
    {
        getSemanticObject().addObjectProperty(swpdoc_hasDocumentSection, value.getSemanticObject());
    }
   /**
   * Removes all the DocumentSection
   */

    public void removeAllDocumentSection()
    {
        getSemanticObject().removeProperty(swpdoc_hasDocumentSection);
    }
   /**
   * Removes a DocumentSection
   * @param value org.semanticwb.process.model.documentation.DocumentSection to remove
   */

    public void removeDocumentSection(org.semanticwb.process.model.documentation.DocumentSection value)
    {
        getSemanticObject().removeObjectProperty(swpdoc_hasDocumentSection,value.getSemanticObject());
    }

   /**
   * Gets the DocumentSection
   * @return a org.semanticwb.process.model.documentation.DocumentSection
   */
    public org.semanticwb.process.model.documentation.DocumentSection getDocumentSection()
    {
         org.semanticwb.process.model.documentation.DocumentSection ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_hasDocumentSection);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.documentation.DocumentSection)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property TemplateContainer
   * @param value TemplateContainer to set
   */

    public void setTemplateContainer(org.semanticwb.process.model.documentation.TemplateContainer value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swpdoc_templateContainer, value.getSemanticObject());
        }else
        {
            removeTemplateContainer();
        }
    }
   /**
   * Remove the value for TemplateContainer property
   */

    public void removeTemplateContainer()
    {
        getSemanticObject().removeProperty(swpdoc_templateContainer);
    }

   /**
   * Gets the TemplateContainer
   * @return a org.semanticwb.process.model.documentation.TemplateContainer
   */
    public org.semanticwb.process.model.documentation.TemplateContainer getTemplateContainer()
    {
         org.semanticwb.process.model.documentation.TemplateContainer ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_templateContainer);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.documentation.TemplateContainer)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property PreviousTemplate
   * @param value PreviousTemplate to set
   */

    public void setPreviousTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swpdoc_previousTemplate, value.getSemanticObject());
        }else
        {
            removePreviousTemplate();
        }
    }
   /**
   * Remove the value for PreviousTemplate property
   */

    public void removePreviousTemplate()
    {
        getSemanticObject().removeProperty(swpdoc_previousTemplate);
    }

   /**
   * Gets the PreviousTemplate
   * @return a org.semanticwb.process.model.documentation.DocumentTemplate
   */
    public org.semanticwb.process.model.documentation.DocumentTemplate getPreviousTemplate()
    {
         org.semanticwb.process.model.documentation.DocumentTemplate ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_previousTemplate);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.documentation.DocumentTemplate)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the VersionComment property
* @return String with the VersionComment
*/
    public String getVersionComment()
    {
        return getSemanticObject().getProperty(swpdoc_versionComment);
    }

/**
* Sets the VersionComment property
* @param value long with the VersionComment
*/
    public void setVersionComment(String value)
    {
        getSemanticObject().setProperty(swpdoc_versionComment, value);
    }
}
