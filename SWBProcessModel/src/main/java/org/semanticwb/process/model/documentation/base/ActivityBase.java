package org.semanticwb.process.model.documentation.base;


public abstract class ActivityBase extends org.semanticwb.process.model.documentation.SectionElement implements org.semanticwb.model.Sortable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticProperty swpdoc_fill=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#fill");
    public static final org.semanticwb.platform.SemanticClass swpdoc_SectionElementRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#SectionElementRef");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_hasSectionElementRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#hasSectionElementRef");
    public static final org.semanticwb.platform.SemanticClass swpdoc_ActivityRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#ActivityRef");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_activityRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#activityRef");
    public static final org.semanticwb.platform.SemanticClass swpdoc_Activity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#Activity");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#Activity");

    public static class ClassMgr
    {
       /**
       * Returns a list of Activity for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.documentation.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Activity> listActivities(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Activity>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.documentation.Activity for all models
       * @return Iterator of org.semanticwb.process.model.documentation.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Activity> listActivities()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Activity>(it, true);
        }

        public static org.semanticwb.process.model.documentation.Activity createActivity(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.documentation.Activity.ClassMgr.createActivity(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.documentation.Activity
       * @param id Identifier for org.semanticwb.process.model.documentation.Activity
       * @param model Model of the org.semanticwb.process.model.documentation.Activity
       * @return A org.semanticwb.process.model.documentation.Activity
       */
        public static org.semanticwb.process.model.documentation.Activity getActivity(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.Activity)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.documentation.Activity
       * @param id Identifier for org.semanticwb.process.model.documentation.Activity
       * @param model Model of the org.semanticwb.process.model.documentation.Activity
       * @return A org.semanticwb.process.model.documentation.Activity
       */
        public static org.semanticwb.process.model.documentation.Activity createActivity(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.Activity)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.documentation.Activity
       * @param id Identifier for org.semanticwb.process.model.documentation.Activity
       * @param model Model of the org.semanticwb.process.model.documentation.Activity
       */
        public static void removeActivity(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.documentation.Activity
       * @param id Identifier for org.semanticwb.process.model.documentation.Activity
       * @param model Model of the org.semanticwb.process.model.documentation.Activity
       * @return true if the org.semanticwb.process.model.documentation.Activity exists, false otherwise
       */

        public static boolean hasActivity(String id, org.semanticwb.model.SWBModel model)
        {
            return (getActivity(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Activity with a determined ProcessElementScope
       * @param value ProcessElementScope of the type org.semanticwb.process.model.ProcessElement
       * @param model Model of the org.semanticwb.process.model.documentation.Activity
       * @return Iterator with all the org.semanticwb.process.model.documentation.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Activity> listActivityByProcessElementScope(org.semanticwb.process.model.ProcessElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasProcessElementScope, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Activity with a determined ProcessElementScope
       * @param value ProcessElementScope of the type org.semanticwb.process.model.ProcessElement
       * @return Iterator with all the org.semanticwb.process.model.documentation.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Activity> listActivityByProcessElementScope(org.semanticwb.process.model.ProcessElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasProcessElementScope,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Activity with a determined DocumentSectionInst
       * @param value DocumentSectionInst of the type org.semanticwb.process.model.documentation.DocumentSectionInstance
       * @param model Model of the org.semanticwb.process.model.documentation.Activity
       * @return Iterator with all the org.semanticwb.process.model.documentation.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Activity> listActivityByDocumentSectionInst(org.semanticwb.process.model.documentation.DocumentSectionInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentSectionInst, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Activity with a determined DocumentSectionInst
       * @param value DocumentSectionInst of the type org.semanticwb.process.model.documentation.DocumentSectionInstance
       * @return Iterator with all the org.semanticwb.process.model.documentation.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Activity> listActivityByDocumentSectionInst(org.semanticwb.process.model.documentation.DocumentSectionInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentSectionInst,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Activity with a determined SectionElementRef
       * @param value SectionElementRef of the type org.semanticwb.process.model.documentation.SectionElementRef
       * @param model Model of the org.semanticwb.process.model.documentation.Activity
       * @return Iterator with all the org.semanticwb.process.model.documentation.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Activity> listActivityBySectionElementRef(org.semanticwb.process.model.documentation.SectionElementRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasSectionElementRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Activity with a determined SectionElementRef
       * @param value SectionElementRef of the type org.semanticwb.process.model.documentation.SectionElementRef
       * @return Iterator with all the org.semanticwb.process.model.documentation.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Activity> listActivityBySectionElementRef(org.semanticwb.process.model.documentation.SectionElementRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasSectionElementRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Activity with a determined ParentSection
       * @param value ParentSection of the type org.semanticwb.process.model.documentation.DocumentSection
       * @param model Model of the org.semanticwb.process.model.documentation.Activity
       * @return Iterator with all the org.semanticwb.process.model.documentation.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Activity> listActivityByParentSection(org.semanticwb.process.model.documentation.DocumentSection value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_parentSection, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Activity with a determined ParentSection
       * @param value ParentSection of the type org.semanticwb.process.model.documentation.DocumentSection
       * @return Iterator with all the org.semanticwb.process.model.documentation.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Activity> listActivityByParentSection(org.semanticwb.process.model.documentation.DocumentSection value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_parentSection,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Activity with a determined ActivityRef
       * @param value ActivityRef of the type org.semanticwb.process.model.documentation.ActivityRef
       * @param model Model of the org.semanticwb.process.model.documentation.Activity
       * @return Iterator with all the org.semanticwb.process.model.documentation.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Activity> listActivityByActivityRef(org.semanticwb.process.model.documentation.ActivityRef value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_activityRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Activity with a determined ActivityRef
       * @param value ActivityRef of the type org.semanticwb.process.model.documentation.ActivityRef
       * @return Iterator with all the org.semanticwb.process.model.documentation.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Activity> listActivityByActivityRef(org.semanticwb.process.model.documentation.ActivityRef value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_activityRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Activity with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.Activity
       * @return Iterator with all the org.semanticwb.process.model.documentation.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Activity> listActivityByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Activity with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Activity> listActivityByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Activity with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.Activity
       * @return Iterator with all the org.semanticwb.process.model.documentation.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Activity> listActivityByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Activity with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Activity> listActivityByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Activity with a determined DocumentTemplate
       * @param value DocumentTemplate of the type org.semanticwb.process.model.documentation.DocumentTemplate
       * @param model Model of the org.semanticwb.process.model.documentation.Activity
       * @return Iterator with all the org.semanticwb.process.model.documentation.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Activity> listActivityByDocumentTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Activity> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Activity with a determined DocumentTemplate
       * @param value DocumentTemplate of the type org.semanticwb.process.model.documentation.DocumentTemplate
       * @return Iterator with all the org.semanticwb.process.model.documentation.Activity
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Activity> listActivityByDocumentTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Activity> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentTemplate,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ActivityBase.ClassMgr getActivityClassMgr()
    {
        return new ActivityBase.ClassMgr();
    }

   /**
   * Constructs a ActivityBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Activity
   */
    public ActivityBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Fill property
* @return String with the Fill
*/
    public String getFill()
    {
        return getSemanticObject().getProperty(swpdoc_fill);
    }

/**
* Sets the Fill property
* @param value long with the Fill
*/
    public void setFill(String value)
    {
        getSemanticObject().setProperty(swpdoc_fill, value);
    }
   /**
   * Gets all the org.semanticwb.process.model.documentation.SectionElementRef
   * @return A GenericIterator with all the org.semanticwb.process.model.documentation.SectionElementRef
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.SectionElementRef> listSectionElementRefs()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.SectionElementRef>(getSemanticObject().listObjectProperties(swpdoc_hasSectionElementRef));
    }

   /**
   * Gets true if has a SectionElementRef
   * @param value org.semanticwb.process.model.documentation.SectionElementRef to verify
   * @return true if the org.semanticwb.process.model.documentation.SectionElementRef exists, false otherwise
   */
    public boolean hasSectionElementRef(org.semanticwb.process.model.documentation.SectionElementRef value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swpdoc_hasSectionElementRef,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a SectionElementRef
   * @param value org.semanticwb.process.model.documentation.SectionElementRef to add
   */

    public void addSectionElementRef(org.semanticwb.process.model.documentation.SectionElementRef value)
    {
        getSemanticObject().addObjectProperty(swpdoc_hasSectionElementRef, value.getSemanticObject());
    }
   /**
   * Removes all the SectionElementRef
   */

    public void removeAllSectionElementRef()
    {
        getSemanticObject().removeProperty(swpdoc_hasSectionElementRef);
    }
   /**
   * Removes a SectionElementRef
   * @param value org.semanticwb.process.model.documentation.SectionElementRef to remove
   */

    public void removeSectionElementRef(org.semanticwb.process.model.documentation.SectionElementRef value)
    {
        getSemanticObject().removeObjectProperty(swpdoc_hasSectionElementRef,value.getSemanticObject());
    }

   /**
   * Gets the SectionElementRef
   * @return a org.semanticwb.process.model.documentation.SectionElementRef
   */
    public org.semanticwb.process.model.documentation.SectionElementRef getSectionElementRef()
    {
         org.semanticwb.process.model.documentation.SectionElementRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_hasSectionElementRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.documentation.SectionElementRef)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property ActivityRef
   * @param value ActivityRef to set
   */

    public void setActivityRef(org.semanticwb.process.model.documentation.ActivityRef value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swpdoc_activityRef, value.getSemanticObject());
        }else
        {
            removeActivityRef();
        }
    }
   /**
   * Remove the value for ActivityRef property
   */

    public void removeActivityRef()
    {
        getSemanticObject().removeProperty(swpdoc_activityRef);
    }

   /**
   * Gets the ActivityRef
   * @return a org.semanticwb.process.model.documentation.ActivityRef
   */
    public org.semanticwb.process.model.documentation.ActivityRef getActivityRef()
    {
         org.semanticwb.process.model.documentation.ActivityRef ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_activityRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.documentation.ActivityRef)obj.createGenericInstance();
         }
         return ret;
    }
}
