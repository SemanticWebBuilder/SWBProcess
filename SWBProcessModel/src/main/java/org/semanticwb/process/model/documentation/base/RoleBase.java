package org.semanticwb.process.model.documentation.base;


public abstract class RoleBase extends org.semanticwb.process.model.documentation.SectionElement implements org.semanticwb.model.Sortable,org.semanticwb.process.model.documentation.ElementReferable,org.semanticwb.model.Traceable,org.semanticwb.process.model.documentation.Instantiable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swpdoc_Role=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#BusinessRole");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#BusinessRole");

    public static class ClassMgr
    {
       /**
       * Returns a list of Role for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.documentation.Role
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Role> listRoles(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Role>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.documentation.Role for all models
       * @return Iterator of org.semanticwb.process.model.documentation.Role
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Role> listRoles()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Role>(it, true);
        }

        public static org.semanticwb.process.model.documentation.Role createRole(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.documentation.Role.ClassMgr.createRole(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.documentation.Role
       * @param id Identifier for org.semanticwb.process.model.documentation.Role
       * @param model Model of the org.semanticwb.process.model.documentation.Role
       * @return A org.semanticwb.process.model.documentation.Role
       */
        public static org.semanticwb.process.model.documentation.Role getRole(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.Role)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.documentation.Role
       * @param id Identifier for org.semanticwb.process.model.documentation.Role
       * @param model Model of the org.semanticwb.process.model.documentation.Role
       * @return A org.semanticwb.process.model.documentation.Role
       */
        public static org.semanticwb.process.model.documentation.Role createRole(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.Role)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.documentation.Role
       * @param id Identifier for org.semanticwb.process.model.documentation.Role
       * @param model Model of the org.semanticwb.process.model.documentation.Role
       */
        public static void removeRole(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.documentation.Role
       * @param id Identifier for org.semanticwb.process.model.documentation.Role
       * @param model Model of the org.semanticwb.process.model.documentation.Role
       * @return true if the org.semanticwb.process.model.documentation.Role exists, false otherwise
       */

        public static boolean hasRole(String id, org.semanticwb.model.SWBModel model)
        {
            return (getRole(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Role with a determined ProcessElementScope
       * @param value ProcessElementScope of the type org.semanticwb.process.model.ProcessElement
       * @param model Model of the org.semanticwb.process.model.documentation.Role
       * @return Iterator with all the org.semanticwb.process.model.documentation.Role
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Role> listRoleByProcessElementScope(org.semanticwb.process.model.ProcessElement value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Role> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasProcessElementScope, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Role with a determined ProcessElementScope
       * @param value ProcessElementScope of the type org.semanticwb.process.model.ProcessElement
       * @return Iterator with all the org.semanticwb.process.model.documentation.Role
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Role> listRoleByProcessElementScope(org.semanticwb.process.model.ProcessElement value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Role> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_hasProcessElementScope,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Role with a determined DocumentSectionInst
       * @param value DocumentSectionInst of the type org.semanticwb.process.model.documentation.DocumentSectionInstance
       * @param model Model of the org.semanticwb.process.model.documentation.Role
       * @return Iterator with all the org.semanticwb.process.model.documentation.Role
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Role> listRoleByDocumentSectionInst(org.semanticwb.process.model.documentation.DocumentSectionInstance value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Role> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentSectionInst, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Role with a determined DocumentSectionInst
       * @param value DocumentSectionInst of the type org.semanticwb.process.model.documentation.DocumentSectionInstance
       * @return Iterator with all the org.semanticwb.process.model.documentation.Role
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Role> listRoleByDocumentSectionInst(org.semanticwb.process.model.documentation.DocumentSectionInstance value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Role> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentSectionInst,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Role with a determined ParentSection
       * @param value ParentSection of the type org.semanticwb.process.model.documentation.DocumentSection
       * @param model Model of the org.semanticwb.process.model.documentation.Role
       * @return Iterator with all the org.semanticwb.process.model.documentation.Role
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Role> listRoleByParentSection(org.semanticwb.process.model.documentation.DocumentSection value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Role> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_parentSection, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Role with a determined ParentSection
       * @param value ParentSection of the type org.semanticwb.process.model.documentation.DocumentSection
       * @return Iterator with all the org.semanticwb.process.model.documentation.Role
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Role> listRoleByParentSection(org.semanticwb.process.model.documentation.DocumentSection value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Role> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_parentSection,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Role with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.Role
       * @return Iterator with all the org.semanticwb.process.model.documentation.Role
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Role> listRoleByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Role> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Role with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.Role
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Role> listRoleByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Role> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Role with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.Role
       * @return Iterator with all the org.semanticwb.process.model.documentation.Role
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Role> listRoleByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Role> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Role with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.Role
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Role> listRoleByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Role> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Role with a determined DocumentTemplate
       * @param value DocumentTemplate of the type org.semanticwb.process.model.documentation.DocumentTemplate
       * @param model Model of the org.semanticwb.process.model.documentation.Role
       * @return Iterator with all the org.semanticwb.process.model.documentation.Role
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Role> listRoleByDocumentTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Role> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentTemplate, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.Role with a determined DocumentTemplate
       * @param value DocumentTemplate of the type org.semanticwb.process.model.documentation.DocumentTemplate
       * @return Iterator with all the org.semanticwb.process.model.documentation.Role
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.Role> listRoleByDocumentTemplate(org.semanticwb.process.model.documentation.DocumentTemplate value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.Role> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swpdoc_documentTemplate,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static RoleBase.ClassMgr getRoleClassMgr()
    {
        return new RoleBase.ClassMgr();
    }

   /**
   * Constructs a RoleBase with a SemanticObject
   * @param base The SemanticObject with the properties for the Role
   */
    public RoleBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
}
