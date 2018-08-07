package org.semanticwb.process.model.documentation.base;


public abstract class ObjetiveBase extends org.semanticwb.process.model.documentation.SectionElement implements org.semanticwb.model.Sortable,org.semanticwb.model.Traceable,org.semanticwb.process.model.documentation.Instantiable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.documentation.ElementReferable
{
    public static final org.semanticwb.platform.SemanticClass swpdoc_Objetive=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#Objetive");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#Objetive");

    public static class ClassMgr
    {
       /**
       * Returns a list of Objetive for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.documentation.Objetive
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Objetive> listObjetives(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Objetive>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.documentation.Objetive for all models
       * @return Iterator of org.semanticwb.process.model.documentation.Objetive
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Objetive> listObjetives()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Objetive>(it, true);
        }

        public static org.semanticwb.process.model.documentation.Objetive createObjetive(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.documentation.Objetive.ClassMgr.createObjetive(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.documentation.Objetive
       * @param id Identifier for org.semanticwb.process.model.documentation.Objetive
       * @param model Model of the org.semanticwb.process.model.documentation.Objetive
       * @return A org.semanticwb.process.model.documentation.Objetive
       */
        public static org.semanticwb.process.model.documentation.Objetive getObjetive(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.Objetive)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.documentation.Objetive
       * @param id Identifier for org.semanticwb.process.model.documentation.Objetive
       * @param model Model of the org.semanticwb.process.model.documentation.Objetive
       * @return A org.semanticwb.process.model.documentation.Objetive
       */
        public static org.semanticwb.process.model.documentation.Objetive createObjetive(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.Objetive)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.documentation.Objetive
       * @param id Identifier for org.semanticwb.process.model.documentation.Objetive
       * @param model Model of the org.semanticwb.process.model.documentation.Objetive
       */
        public static void removeObjetive(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.documentation.Objetive
       * @param id Identifier for org.semanticwb.process.model.documentation.Objetive
       * @param model Model of the org.semanticwb.process.model.documentation.Objetive
       * @return true if the org.semanticwb.process.model.documentation.Objetive exists, false otherwise
       */

        public static boolean hasObjetive(String id, org.semanticwb.model.SWBModel model)
        {
            return (getObjetive(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Objetive with a determined ProcessElementScope
       * @param value ProcessElementScope of the type org.semanticwb.process.model.ProcessElement
       * @param model Model of the org.semanticwb.process.model.documentation.Objetive
       * @return Iterator with all the org.semanticwb.process.model.documentation.Objetive
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Objetive> listObjetiveByProcessElementScope(org.semanticwb.process.model.ProcessElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Objetive> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasProcessElementScope, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Objetive with a determined ProcessElementScope
       * @param value ProcessElementScope of the type org.semanticwb.process.model.ProcessElement
       * @return Iterator with all the org.semanticwb.process.model.documentation.Objetive
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Objetive> listObjetiveByProcessElementScope(org.semanticwb.process.model.ProcessElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Objetive> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasProcessElementScope,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Objetive with a determined DocumentSectionInst
       * @param value DocumentSectionInst of the type org.semanticwb.process.model.documentation.DocumentSectionInstance
       * @param model Model of the org.semanticwb.process.model.documentation.Objetive
       * @return Iterator with all the org.semanticwb.process.model.documentation.Objetive
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Objetive> listObjetiveByDocumentSectionInst(org.semanticwb.process.model.documentation.DocumentSectionInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Objetive> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentSectionInst, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Objetive with a determined DocumentSectionInst
       * @param value DocumentSectionInst of the type org.semanticwb.process.model.documentation.DocumentSectionInstance
       * @return Iterator with all the org.semanticwb.process.model.documentation.Objetive
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Objetive> listObjetiveByDocumentSectionInst(org.semanticwb.process.model.documentation.DocumentSectionInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Objetive> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentSectionInst,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Objetive with a determined ParentSection
       * @param value ParentSection of the type org.semanticwb.process.model.documentation.DocumentSection
       * @param model Model of the org.semanticwb.process.model.documentation.Objetive
       * @return Iterator with all the org.semanticwb.process.model.documentation.Objetive
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Objetive> listObjetiveByParentSection(org.semanticwb.process.model.documentation.DocumentSection value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Objetive> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_parentSection, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Objetive with a determined ParentSection
       * @param value ParentSection of the type org.semanticwb.process.model.documentation.DocumentSection
       * @return Iterator with all the org.semanticwb.process.model.documentation.Objetive
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Objetive> listObjetiveByParentSection(org.semanticwb.process.model.documentation.DocumentSection value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Objetive> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_parentSection,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Objetive with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.Objetive
       * @return Iterator with all the org.semanticwb.process.model.documentation.Objetive
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Objetive> listObjetiveByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Objetive> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Objetive with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.Objetive
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Objetive> listObjetiveByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Objetive> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Objetive with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.Objetive
       * @return Iterator with all the org.semanticwb.process.model.documentation.Objetive
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Objetive> listObjetiveByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Objetive> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Objetive with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.Objetive
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Objetive> listObjetiveByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Objetive> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Objetive with a determined DocumentTemplate
       * @param value DocumentTemplate of the type org.semanticwb.process.model.documentation.DocumentTemplate
       * @param model Model of the org.semanticwb.process.model.documentation.Objetive
       * @return Iterator with all the org.semanticwb.process.model.documentation.Objetive
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Objetive> listObjetiveByDocumentTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Objetive> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Objetive with a determined DocumentTemplate
       * @param value DocumentTemplate of the type org.semanticwb.process.model.documentation.DocumentTemplate
       * @return Iterator with all the org.semanticwb.process.model.documentation.Objetive
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Objetive> listObjetiveByDocumentTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Objetive> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentTemplate,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ObjetiveBase.ClassMgr getObjetiveClassMgr()
    {
        return new ObjetiveBase.ClassMgr();
    }

   /**
   * Constructs a ObjetiveBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Objetive
   */
    public ObjetiveBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
