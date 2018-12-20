package org.semanticwb.process.model.documentation.base;


public abstract class ActivityRefBase extends org.semanticwb.process.model.documentation.ReferenceObject implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swp_Activity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#Activity");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_processActivity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#processActivity");
    public static final org.semanticwb.platform.SemanticClass swpdoc_ActivityRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#ActivityRef");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#ActivityRef");

    public static class ClassMgr
    {
       /**
       * Returns a list of ActivityRef for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.documentation.ActivityRef
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.ActivityRef> listActivityRefs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.ActivityRef>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.documentation.ActivityRef for all models
       * @return Iterator of org.semanticwb.process.model.documentation.ActivityRef
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.ActivityRef> listActivityRefs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.ActivityRef>(it, true);
        }

        public static org.semanticwb.process.model.documentation.ActivityRef createActivityRef(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.documentation.ActivityRef.ClassMgr.createActivityRef(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.documentation.ActivityRef
       * @param id Identifier for org.semanticwb.process.model.documentation.ActivityRef
       * @param model Model of the org.semanticwb.process.model.documentation.ActivityRef
       * @return A org.semanticwb.process.model.documentation.ActivityRef
       */
        public static org.semanticwb.process.model.documentation.ActivityRef getActivityRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.ActivityRef)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.documentation.ActivityRef
       * @param id Identifier for org.semanticwb.process.model.documentation.ActivityRef
       * @param model Model of the org.semanticwb.process.model.documentation.ActivityRef
       * @return A org.semanticwb.process.model.documentation.ActivityRef
       */
        public static org.semanticwb.process.model.documentation.ActivityRef createActivityRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.ActivityRef)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.documentation.ActivityRef
       * @param id Identifier for org.semanticwb.process.model.documentation.ActivityRef
       * @param model Model of the org.semanticwb.process.model.documentation.ActivityRef
       */
        public static void removeActivityRef(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.documentation.ActivityRef
       * @param id Identifier for org.semanticwb.process.model.documentation.ActivityRef
       * @param model Model of the org.semanticwb.process.model.documentation.ActivityRef
       * @return true if the org.semanticwb.process.model.documentation.ActivityRef exists, false otherwise
       */

        public static boolean hasActivityRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (getActivityRef(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.ActivityRef with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.ActivityRef
       * @return Iterator with all the org.semanticwb.process.model.documentation.ActivityRef
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.ActivityRef> listActivityRefByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.ActivityRef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.ActivityRef with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.ActivityRef
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.ActivityRef> listActivityRefByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.ActivityRef> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.ActivityRef with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.ActivityRef
       * @return Iterator with all the org.semanticwb.process.model.documentation.ActivityRef
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.ActivityRef> listActivityRefByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.ActivityRef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.ActivityRef with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.ActivityRef
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.ActivityRef> listActivityRefByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.ActivityRef> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.ActivityRef with a determined ProcessActivity
       * @param value ProcessActivity of the type org.semanticwb.process.model.Activity
       * @param model Model of the org.semanticwb.process.model.documentation.ActivityRef
       * @return Iterator with all the org.semanticwb.process.model.documentation.ActivityRef
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.ActivityRef> listActivityRefByProcessActivity(org.semanticwb.process.model.Activity value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.ActivityRef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_processActivity, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.ActivityRef with a determined ProcessActivity
       * @param value ProcessActivity of the type org.semanticwb.process.model.Activity
       * @return Iterator with all the org.semanticwb.process.model.documentation.ActivityRef
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.ActivityRef> listActivityRefByProcessActivity(org.semanticwb.process.model.Activity value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.ActivityRef> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_processActivity,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ActivityRefBase.ClassMgr getActivityRefClassMgr()
    {
        return new ActivityRefBase.ClassMgr();
    }

   /**
   * Constructs a ActivityRefBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ActivityRef
   */
    public ActivityRefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property ProcessActivity
   * @param value ProcessActivity to set
   */

    public void setProcessActivity(org.semanticwb.process.model.Activity value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swpdoc_processActivity, value.getSemanticObject());
        }else
        {
            removeProcessActivity();
        }
    }
   /**
   * Remove the value for ProcessActivity property
   */

    public void removeProcessActivity()
    {
        getSemanticObject().removeProperty(swpdoc_processActivity);
    }

   /**
   * Gets the ProcessActivity
   * @return a org.semanticwb.process.model.Activity
   */
    public org.semanticwb.process.model.Activity getProcessActivity()
    {
         org.semanticwb.process.model.Activity ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_processActivity);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Activity)obj.createGenericInstance();
         }
         return ret;
    }
}
