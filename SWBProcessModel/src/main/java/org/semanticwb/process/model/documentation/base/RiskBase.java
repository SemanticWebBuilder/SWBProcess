package org.semanticwb.process.model.documentation.base;


public abstract class RiskBase extends org.semanticwb.process.model.documentation.SectionElement implements org.semanticwb.model.Sortable,org.semanticwb.model.Traceable,org.semanticwb.process.model.documentation.Instantiable,org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.documentation.ElementReferable
{
    public static final org.semanticwb.platform.SemanticClass swpdoc_Risk=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#Risk");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#Risk");

    public static class ClassMgr
    {
       /**
       * Returns a list of Risk for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.documentation.Risk
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Risk> listRisks(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Risk>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.documentation.Risk for all models
       * @return Iterator of org.semanticwb.process.model.documentation.Risk
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Risk> listRisks()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Risk>(it, true);
        }

        public static org.semanticwb.process.model.documentation.Risk createRisk(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.documentation.Risk.ClassMgr.createRisk(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.documentation.Risk
       * @param id Identifier for org.semanticwb.process.model.documentation.Risk
       * @param model Model of the org.semanticwb.process.model.documentation.Risk
       * @return A org.semanticwb.process.model.documentation.Risk
       */
        public static org.semanticwb.process.model.documentation.Risk getRisk(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.Risk)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.documentation.Risk
       * @param id Identifier for org.semanticwb.process.model.documentation.Risk
       * @param model Model of the org.semanticwb.process.model.documentation.Risk
       * @return A org.semanticwb.process.model.documentation.Risk
       */
        public static org.semanticwb.process.model.documentation.Risk createRisk(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.Risk)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.documentation.Risk
       * @param id Identifier for org.semanticwb.process.model.documentation.Risk
       * @param model Model of the org.semanticwb.process.model.documentation.Risk
       */
        public static void removeRisk(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.documentation.Risk
       * @param id Identifier for org.semanticwb.process.model.documentation.Risk
       * @param model Model of the org.semanticwb.process.model.documentation.Risk
       * @return true if the org.semanticwb.process.model.documentation.Risk exists, false otherwise
       */

        public static boolean hasRisk(String id, org.semanticwb.model.SWBModel model)
        {
            return (getRisk(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Risk with a determined ProcessElementScope
       * @param value ProcessElementScope of the type org.semanticwb.process.model.ProcessElement
       * @param model Model of the org.semanticwb.process.model.documentation.Risk
       * @return Iterator with all the org.semanticwb.process.model.documentation.Risk
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Risk> listRiskByProcessElementScope(org.semanticwb.process.model.ProcessElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Risk> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasProcessElementScope, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Risk with a determined ProcessElementScope
       * @param value ProcessElementScope of the type org.semanticwb.process.model.ProcessElement
       * @return Iterator with all the org.semanticwb.process.model.documentation.Risk
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Risk> listRiskByProcessElementScope(org.semanticwb.process.model.ProcessElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Risk> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasProcessElementScope,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Risk with a determined DocumentSectionInst
       * @param value DocumentSectionInst of the type org.semanticwb.process.model.documentation.DocumentSectionInstance
       * @param model Model of the org.semanticwb.process.model.documentation.Risk
       * @return Iterator with all the org.semanticwb.process.model.documentation.Risk
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Risk> listRiskByDocumentSectionInst(org.semanticwb.process.model.documentation.DocumentSectionInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Risk> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentSectionInst, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Risk with a determined DocumentSectionInst
       * @param value DocumentSectionInst of the type org.semanticwb.process.model.documentation.DocumentSectionInstance
       * @return Iterator with all the org.semanticwb.process.model.documentation.Risk
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Risk> listRiskByDocumentSectionInst(org.semanticwb.process.model.documentation.DocumentSectionInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Risk> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentSectionInst,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Risk with a determined ParentSection
       * @param value ParentSection of the type org.semanticwb.process.model.documentation.DocumentSection
       * @param model Model of the org.semanticwb.process.model.documentation.Risk
       * @return Iterator with all the org.semanticwb.process.model.documentation.Risk
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Risk> listRiskByParentSection(org.semanticwb.process.model.documentation.DocumentSection value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Risk> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_parentSection, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Risk with a determined ParentSection
       * @param value ParentSection of the type org.semanticwb.process.model.documentation.DocumentSection
       * @return Iterator with all the org.semanticwb.process.model.documentation.Risk
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Risk> listRiskByParentSection(org.semanticwb.process.model.documentation.DocumentSection value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Risk> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_parentSection,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Risk with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.Risk
       * @return Iterator with all the org.semanticwb.process.model.documentation.Risk
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Risk> listRiskByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Risk> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Risk with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.Risk
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Risk> listRiskByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Risk> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Risk with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.Risk
       * @return Iterator with all the org.semanticwb.process.model.documentation.Risk
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Risk> listRiskByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Risk> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Risk with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.Risk
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Risk> listRiskByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Risk> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Risk with a determined DocumentTemplate
       * @param value DocumentTemplate of the type org.semanticwb.process.model.documentation.DocumentTemplate
       * @param model Model of the org.semanticwb.process.model.documentation.Risk
       * @return Iterator with all the org.semanticwb.process.model.documentation.Risk
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Risk> listRiskByDocumentTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Risk> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Risk with a determined DocumentTemplate
       * @param value DocumentTemplate of the type org.semanticwb.process.model.documentation.DocumentTemplate
       * @return Iterator with all the org.semanticwb.process.model.documentation.Risk
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Risk> listRiskByDocumentTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Risk> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentTemplate,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static RiskBase.ClassMgr getRiskClassMgr()
    {
        return new RiskBase.ClassMgr();
    }

   /**
   * Constructs a RiskBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Risk
   */
    public RiskBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
