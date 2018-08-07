package org.semanticwb.process.model.documentation.base;


public abstract class SectionElementRefBase extends org.semanticwb.process.model.documentation.ReferenceObject implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swpdoc_SectionElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#SectionElement");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_sectionElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#sectionElement");
    public static final org.semanticwb.platform.SemanticClass swpdoc_Activity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#Activity");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_activity=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#activity");
    public static final org.semanticwb.platform.SemanticClass swpdoc_SectionElementRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#SectionElementRef");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#SectionElementRef");

    public static class ClassMgr
    {
       /**
       * Returns a list of SectionElementRef for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.documentation.SectionElementRef
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.SectionElementRef> listSectionElementRefs(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.SectionElementRef>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.documentation.SectionElementRef for all models
       * @return Iterator of org.semanticwb.process.model.documentation.SectionElementRef
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.SectionElementRef> listSectionElementRefs()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.SectionElementRef>(it, true);
        }

        public static org.semanticwb.process.model.documentation.SectionElementRef createSectionElementRef(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.documentation.SectionElementRef.ClassMgr.createSectionElementRef(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.documentation.SectionElementRef
       * @param id Identifier for org.semanticwb.process.model.documentation.SectionElementRef
       * @param model Model of the org.semanticwb.process.model.documentation.SectionElementRef
       * @return A org.semanticwb.process.model.documentation.SectionElementRef
       */
        public static org.semanticwb.process.model.documentation.SectionElementRef getSectionElementRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.SectionElementRef)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.documentation.SectionElementRef
       * @param id Identifier for org.semanticwb.process.model.documentation.SectionElementRef
       * @param model Model of the org.semanticwb.process.model.documentation.SectionElementRef
       * @return A org.semanticwb.process.model.documentation.SectionElementRef
       */
        public static org.semanticwb.process.model.documentation.SectionElementRef createSectionElementRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.SectionElementRef)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.documentation.SectionElementRef
       * @param id Identifier for org.semanticwb.process.model.documentation.SectionElementRef
       * @param model Model of the org.semanticwb.process.model.documentation.SectionElementRef
       */
        public static void removeSectionElementRef(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.documentation.SectionElementRef
       * @param id Identifier for org.semanticwb.process.model.documentation.SectionElementRef
       * @param model Model of the org.semanticwb.process.model.documentation.SectionElementRef
       * @return true if the org.semanticwb.process.model.documentation.SectionElementRef exists, false otherwise
       */

        public static boolean hasSectionElementRef(String id, org.semanticwb.model.SWBModel model)
        {
            return (getSectionElementRef(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.SectionElementRef with a determined SectionElement
       * @param value SectionElement of the type org.semanticwb.process.model.documentation.SectionElement
       * @param model Model of the org.semanticwb.process.model.documentation.SectionElementRef
       * @return Iterator with all the org.semanticwb.process.model.documentation.SectionElementRef
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.SectionElementRef> listSectionElementRefBySectionElement(org.semanticwb.process.model.documentation.SectionElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.SectionElementRef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_sectionElement, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.SectionElementRef with a determined SectionElement
       * @param value SectionElement of the type org.semanticwb.process.model.documentation.SectionElement
       * @return Iterator with all the org.semanticwb.process.model.documentation.SectionElementRef
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.SectionElementRef> listSectionElementRefBySectionElement(org.semanticwb.process.model.documentation.SectionElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.SectionElementRef> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_sectionElement,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.SectionElementRef with a determined Activity
       * @param value Activity of the type org.semanticwb.process.model.documentation.Activity
       * @param model Model of the org.semanticwb.process.model.documentation.SectionElementRef
       * @return Iterator with all the org.semanticwb.process.model.documentation.SectionElementRef
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.SectionElementRef> listSectionElementRefByActivity(org.semanticwb.process.model.documentation.Activity value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.SectionElementRef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_activity, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.SectionElementRef with a determined Activity
       * @param value Activity of the type org.semanticwb.process.model.documentation.Activity
       * @return Iterator with all the org.semanticwb.process.model.documentation.SectionElementRef
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.SectionElementRef> listSectionElementRefByActivity(org.semanticwb.process.model.documentation.Activity value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.SectionElementRef> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_activity,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.SectionElementRef with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.SectionElementRef
       * @return Iterator with all the org.semanticwb.process.model.documentation.SectionElementRef
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.SectionElementRef> listSectionElementRefByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.SectionElementRef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.SectionElementRef with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.SectionElementRef
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.SectionElementRef> listSectionElementRefByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.SectionElementRef> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.SectionElementRef with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.SectionElementRef
       * @return Iterator with all the org.semanticwb.process.model.documentation.SectionElementRef
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.SectionElementRef> listSectionElementRefByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.SectionElementRef> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.SectionElementRef with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.SectionElementRef
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.SectionElementRef> listSectionElementRefByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.SectionElementRef> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static SectionElementRefBase.ClassMgr getSectionElementRefClassMgr()
    {
        return new SectionElementRefBase.ClassMgr();
    }

   /**
   * Constructs a SectionElementRefBase with a SemanticObject
   * @param base The SemanticObject with the properties for the SectionElementRef
   */
    public SectionElementRefBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property SectionElement
   * @param value SectionElement to set
   */

    public void setSectionElement(org.semanticwb.process.model.documentation.SectionElement value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swpdoc_sectionElement, value.getSemanticObject());
        }else
        {
            removeSectionElement();
        }
    }
   /**
   * Remove the value for SectionElement property
   */

    public void removeSectionElement()
    {
        getSemanticObject().removeProperty(swpdoc_sectionElement);
    }

   /**
   * Gets the SectionElement
   * @return a org.semanticwb.process.model.documentation.SectionElement
   */
    public org.semanticwb.process.model.documentation.SectionElement getSectionElement()
    {
         org.semanticwb.process.model.documentation.SectionElement ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_sectionElement);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.documentation.SectionElement)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Activity
   * @param value Activity to set
   */

    public void setActivity(org.semanticwb.process.model.documentation.Activity value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swpdoc_activity, value.getSemanticObject());
        }else
        {
            removeActivity();
        }
    }
   /**
   * Remove the value for Activity property
   */

    public void removeActivity()
    {
        getSemanticObject().removeProperty(swpdoc_activity);
    }

   /**
   * Gets the Activity
   * @return a org.semanticwb.process.model.documentation.Activity
   */
    public org.semanticwb.process.model.documentation.Activity getActivity()
    {
         org.semanticwb.process.model.documentation.Activity ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_activity);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.documentation.Activity)obj.createGenericInstance();
         }
         return ret;
    }
}
