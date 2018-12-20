package org.semanticwb.process.model.documentation.base;


public abstract class ElementReferenceBase extends org.semanticwb.process.model.documentation.SectionElement implements org.semanticwb.model.Sortable,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swpdoc_ElementReferable=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#ElementReferable");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_elementRef=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#elementRef");
    public static final org.semanticwb.platform.SemanticClass swb_Class=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/ontology#Class");
    public static final org.semanticwb.platform.SemanticProperty swpdoc_elementType=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/resources/documentation#elementType");
    public static final org.semanticwb.platform.SemanticClass swpdoc_ElementReference=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#ElementReference");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#ElementReference");

    public static class ClassMgr
    {
       /**
       * Returns a list of ElementReference for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.documentation.ElementReference
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.ElementReference> listElementReferences(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.ElementReference>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.documentation.ElementReference for all models
       * @return Iterator of org.semanticwb.process.model.documentation.ElementReference
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.ElementReference> listElementReferences()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.ElementReference>(it, true);
        }

        public static org.semanticwb.process.model.documentation.ElementReference createElementReference(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.documentation.ElementReference.ClassMgr.createElementReference(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.documentation.ElementReference
       * @param id Identifier for org.semanticwb.process.model.documentation.ElementReference
       * @param model Model of the org.semanticwb.process.model.documentation.ElementReference
       * @return A org.semanticwb.process.model.documentation.ElementReference
       */
        public static org.semanticwb.process.model.documentation.ElementReference getElementReference(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.ElementReference)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.documentation.ElementReference
       * @param id Identifier for org.semanticwb.process.model.documentation.ElementReference
       * @param model Model of the org.semanticwb.process.model.documentation.ElementReference
       * @return A org.semanticwb.process.model.documentation.ElementReference
       */
        public static org.semanticwb.process.model.documentation.ElementReference createElementReference(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.ElementReference)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.documentation.ElementReference
       * @param id Identifier for org.semanticwb.process.model.documentation.ElementReference
       * @param model Model of the org.semanticwb.process.model.documentation.ElementReference
       */
        public static void removeElementReference(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.documentation.ElementReference
       * @param id Identifier for org.semanticwb.process.model.documentation.ElementReference
       * @param model Model of the org.semanticwb.process.model.documentation.ElementReference
       * @return true if the org.semanticwb.process.model.documentation.ElementReference exists, false otherwise
       */

        public static boolean hasElementReference(String id, org.semanticwb.model.SWBModel model)
        {
            return (getElementReference(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.ElementReference with a determined ProcessElementScope
       * @param value ProcessElementScope of the type org.semanticwb.process.model.ProcessElement
       * @param model Model of the org.semanticwb.process.model.documentation.ElementReference
       * @return Iterator with all the org.semanticwb.process.model.documentation.ElementReference
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.ElementReference> listElementReferenceByProcessElementScope(org.semanticwb.process.model.ProcessElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.ElementReference> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasProcessElementScope, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.ElementReference with a determined ProcessElementScope
       * @param value ProcessElementScope of the type org.semanticwb.process.model.ProcessElement
       * @return Iterator with all the org.semanticwb.process.model.documentation.ElementReference
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.ElementReference> listElementReferenceByProcessElementScope(org.semanticwb.process.model.ProcessElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.ElementReference> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasProcessElementScope,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.ElementReference with a determined ElementRef
       * @param value ElementRef of the type org.semanticwb.process.model.documentation.ElementReferable
       * @param model Model of the org.semanticwb.process.model.documentation.ElementReference
       * @return Iterator with all the org.semanticwb.process.model.documentation.ElementReference
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.ElementReference> listElementReferenceByElementRef(org.semanticwb.process.model.documentation.ElementReferable value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.ElementReference> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_elementRef, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.ElementReference with a determined ElementRef
       * @param value ElementRef of the type org.semanticwb.process.model.documentation.ElementReferable
       * @return Iterator with all the org.semanticwb.process.model.documentation.ElementReference
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.ElementReference> listElementReferenceByElementRef(org.semanticwb.process.model.documentation.ElementReferable value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.ElementReference> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_elementRef,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.ElementReference with a determined DocumentSectionInst
       * @param value DocumentSectionInst of the type org.semanticwb.process.model.documentation.DocumentSectionInstance
       * @param model Model of the org.semanticwb.process.model.documentation.ElementReference
       * @return Iterator with all the org.semanticwb.process.model.documentation.ElementReference
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.ElementReference> listElementReferenceByDocumentSectionInst(org.semanticwb.process.model.documentation.DocumentSectionInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.ElementReference> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentSectionInst, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.ElementReference with a determined DocumentSectionInst
       * @param value DocumentSectionInst of the type org.semanticwb.process.model.documentation.DocumentSectionInstance
       * @return Iterator with all the org.semanticwb.process.model.documentation.ElementReference
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.ElementReference> listElementReferenceByDocumentSectionInst(org.semanticwb.process.model.documentation.DocumentSectionInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.ElementReference> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentSectionInst,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.ElementReference with a determined ParentSection
       * @param value ParentSection of the type org.semanticwb.process.model.documentation.DocumentSection
       * @param model Model of the org.semanticwb.process.model.documentation.ElementReference
       * @return Iterator with all the org.semanticwb.process.model.documentation.ElementReference
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.ElementReference> listElementReferenceByParentSection(org.semanticwb.process.model.documentation.DocumentSection value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.ElementReference> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_parentSection, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.ElementReference with a determined ParentSection
       * @param value ParentSection of the type org.semanticwb.process.model.documentation.DocumentSection
       * @return Iterator with all the org.semanticwb.process.model.documentation.ElementReference
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.ElementReference> listElementReferenceByParentSection(org.semanticwb.process.model.documentation.DocumentSection value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.ElementReference> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_parentSection,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.ElementReference with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.ElementReference
       * @return Iterator with all the org.semanticwb.process.model.documentation.ElementReference
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.ElementReference> listElementReferenceByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.ElementReference> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.ElementReference with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.ElementReference
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.ElementReference> listElementReferenceByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.ElementReference> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.ElementReference with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.ElementReference
       * @return Iterator with all the org.semanticwb.process.model.documentation.ElementReference
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.ElementReference> listElementReferenceByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.ElementReference> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.ElementReference with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.ElementReference
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.ElementReference> listElementReferenceByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.ElementReference> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.ElementReference with a determined DocumentTemplate
       * @param value DocumentTemplate of the type org.semanticwb.process.model.documentation.DocumentTemplate
       * @param model Model of the org.semanticwb.process.model.documentation.ElementReference
       * @return Iterator with all the org.semanticwb.process.model.documentation.ElementReference
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.ElementReference> listElementReferenceByDocumentTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.ElementReference> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.ElementReference with a determined DocumentTemplate
       * @param value DocumentTemplate of the type org.semanticwb.process.model.documentation.DocumentTemplate
       * @return Iterator with all the org.semanticwb.process.model.documentation.ElementReference
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.ElementReference> listElementReferenceByDocumentTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.ElementReference> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentTemplate,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static ElementReferenceBase.ClassMgr getElementReferenceClassMgr()
    {
        return new ElementReferenceBase.ClassMgr();
    }

   /**
   * Constructs a ElementReferenceBase with a SemanticObject
   * @param base The SemanticObject with the properties for the ElementReference
   */
    public ElementReferenceBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Sets the value for the property ElementRef
   * @param value ElementRef to set
   */

    public void setElementRef(org.semanticwb.process.model.documentation.ElementReferable value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swpdoc_elementRef, value.getSemanticObject());
        }else
        {
            removeElementRef();
        }
    }
   /**
   * Remove the value for ElementRef property
   */

    public void removeElementRef()
    {
        getSemanticObject().removeProperty(swpdoc_elementRef);
    }

   /**
   * Gets the ElementRef
   * @return a org.semanticwb.process.model.documentation.ElementReferable
   */
    public org.semanticwb.process.model.documentation.ElementReferable getElementRef()
    {
         org.semanticwb.process.model.documentation.ElementReferable ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swpdoc_elementRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.documentation.ElementReferable)obj.createGenericInstance();
         }
         return ret;
    }

    public void setElementType(org.semanticwb.platform.SemanticObject value)
    {
        getSemanticObject().setObjectProperty(swpdoc_elementType, value);
    }

    public void removeElementType()
    {
        getSemanticObject().removeProperty(swpdoc_elementType);
    }

/**
* Gets the ElementType property
* @return the value for the property as org.semanticwb.platform.SemanticObject
*/
    public org.semanticwb.platform.SemanticObject getElementType()
    {
         org.semanticwb.platform.SemanticObject ret=null;
         ret=getSemanticObject().getObjectProperty(swpdoc_elementType);
         return ret;
    }
}
