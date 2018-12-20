package org.semanticwb.process.model.documentation.base;


public abstract class ReferenceObjectBase extends org.semanticwb.process.model.documentation.DocumentationElement implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swpdoc_ReferenceObject=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#ReferenceObject");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#ReferenceObject");

    public static class ClassMgr
    {
       /**
       * Returns a list of ReferenceObject for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.documentation.ReferenceObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.ReferenceObject> listReferenceObjects(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.ReferenceObject>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.documentation.ReferenceObject for all models
       * @return Iterator of org.semanticwb.process.model.documentation.ReferenceObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.ReferenceObject> listReferenceObjects()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.ReferenceObject>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.model.documentation.ReferenceObject
       * @param id Identifier for org.semanticwb.process.model.documentation.ReferenceObject
       * @param model Model of the org.semanticwb.process.model.documentation.ReferenceObject
       * @return A org.semanticwb.process.model.documentation.ReferenceObject
       */
        public static org.semanticwb.process.model.documentation.ReferenceObject getReferenceObject(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.ReferenceObject)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.documentation.ReferenceObject
       * @param id Identifier for org.semanticwb.process.model.documentation.ReferenceObject
       * @param model Model of the org.semanticwb.process.model.documentation.ReferenceObject
       * @return A org.semanticwb.process.model.documentation.ReferenceObject
       */
        public static org.semanticwb.process.model.documentation.ReferenceObject createReferenceObject(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.ReferenceObject)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.documentation.ReferenceObject
       * @param id Identifier for org.semanticwb.process.model.documentation.ReferenceObject
       * @param model Model of the org.semanticwb.process.model.documentation.ReferenceObject
       */
        public static void removeReferenceObject(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.documentation.ReferenceObject
       * @param id Identifier for org.semanticwb.process.model.documentation.ReferenceObject
       * @param model Model of the org.semanticwb.process.model.documentation.ReferenceObject
       * @return true if the org.semanticwb.process.model.documentation.ReferenceObject exists, false otherwise
       */

        public static boolean hasReferenceObject(String id, org.semanticwb.model.SWBModel model)
        {
            return (getReferenceObject(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.ReferenceObject with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.ReferenceObject
       * @return Iterator with all the org.semanticwb.process.model.documentation.ReferenceObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.ReferenceObject> listReferenceObjectByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.ReferenceObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.ReferenceObject with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.ReferenceObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.ReferenceObject> listReferenceObjectByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.ReferenceObject> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.ReferenceObject with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.ReferenceObject
       * @return Iterator with all the org.semanticwb.process.model.documentation.ReferenceObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.ReferenceObject> listReferenceObjectByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.ReferenceObject> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.ReferenceObject with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.ReferenceObject
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.ReferenceObject> listReferenceObjectByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.ReferenceObject> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ReferenceObjectBase.ClassMgr getReferenceObjectClassMgr()
    {
        return new ReferenceObjectBase.ClassMgr();
    }

   /**
   * Constructs a ReferenceObjectBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ReferenceObject
   */
    public ReferenceObjectBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
