package org.semanticwb.process.model.documentation.base;


public abstract class DocumentationElementBase extends org.semanticwb.model.SWBClass implements org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
    public static final org.semanticwb.platform.SemanticClass swpdoc_DocumentationElement=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#DocumentationElement");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/resources/documentation#DocumentationElement");

    public static class ClassMgr
    {
       /**
       * Returns a list of DocumentationElement for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.documentation.DocumentationElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentationElement> listDocumentationElements(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentationElement>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.documentation.DocumentationElement for all models
       * @return Iterator of org.semanticwb.process.model.documentation.DocumentationElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentationElement> listDocumentationElements()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentationElement>(it, true);
        }
       /**
       * Gets a org.semanticwb.process.model.documentation.DocumentationElement
       * @param id Identifier for org.semanticwb.process.model.documentation.DocumentationElement
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentationElement
       * @return A org.semanticwb.process.model.documentation.DocumentationElement
       */
        public static org.semanticwb.process.model.documentation.DocumentationElement getDocumentationElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.DocumentationElement)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.documentation.DocumentationElement
       * @param id Identifier for org.semanticwb.process.model.documentation.DocumentationElement
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentationElement
       * @return A org.semanticwb.process.model.documentation.DocumentationElement
       */
        public static org.semanticwb.process.model.documentation.DocumentationElement createDocumentationElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.documentation.DocumentationElement)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.documentation.DocumentationElement
       * @param id Identifier for org.semanticwb.process.model.documentation.DocumentationElement
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentationElement
       */
        public static void removeDocumentationElement(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.documentation.DocumentationElement
       * @param id Identifier for org.semanticwb.process.model.documentation.DocumentationElement
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentationElement
       * @return true if the org.semanticwb.process.model.documentation.DocumentationElement exists, false otherwise
       */

        public static boolean hasDocumentationElement(String id, org.semanticwb.model.SWBModel model)
        {
            return (getDocumentationElement(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentationElement with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentationElement
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentationElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentationElement> listDocumentationElementByModifiedBy(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentationElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentationElement with a determined ModifiedBy
       * @param value ModifiedBy of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentationElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentationElement> listDocumentationElementByModifiedBy(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentationElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_modifiedBy,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentationElement with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @param model Model of the org.semanticwb.process.model.documentation.DocumentationElement
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentationElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentationElement> listDocumentationElementByCreator(org.semanticwb.model.User value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentationElement> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swb_creator, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.documentation.DocumentationElement with a determined Creator
       * @param value Creator of the type org.semanticwb.model.User
       * @return Iterator with all the org.semanticwb.process.model.documentation.DocumentationElement
       */

        public static java.util.Iterator<org.semanticwb.process.model.documentation.DocumentationElement> listDocumentationElementByCreator(org.semanticwb.model.User value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.documentation.DocumentationElement> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swb_creator,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static DocumentationElementBase.ClassMgr getDocumentationElementClassMgr()
    {
        return new DocumentationElementBase.ClassMgr();
    }

   /**
   * Constructs a DocumentationElementBase with a SemanticObject
   * @param base The SemanticObject with the properties for the DocumentationElement
   */
    public DocumentationElementBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

/**
* Gets the Created property
* @return java.util.Date with the Created
*/
    public java.util.Date getCreated()
    {
        return getSemanticObject().getDateProperty(swb_created);
    }

/**
* Sets the Created property
* @param value long with the Created
*/
    public void setCreated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_created, value);
    }

/**
* Gets the Updated property
* @return java.util.Date with the Updated
*/
    public java.util.Date getUpdated()
    {
        return getSemanticObject().getDateProperty(swb_updated);
    }

/**
* Sets the Updated property
* @param value long with the Updated
*/
    public void setUpdated(java.util.Date value)
    {
        getSemanticObject().setDateProperty(swb_updated, value);
    }

/**
* Gets the Description property
* @return String with the Description
*/
    public String getDescription()
    {
        return getSemanticObject().getProperty(swb_description);
    }

/**
* Sets the Description property
* @param value long with the Description
*/
    public void setDescription(String value)
    {
        getSemanticObject().setProperty(swb_description, value);
    }

    public String getDescription(String lang)
    {
        return getSemanticObject().getProperty(swb_description, null, lang);
    }

    public String getDisplayDescription(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_description, lang);
    }

    public void setDescription(String description, String lang)
    {
        getSemanticObject().setProperty(swb_description, description, lang);
    }

/**
* Gets the Title property
* @return String with the Title
*/
    public String getTitle()
    {
        return getSemanticObject().getProperty(swb_title);
    }

/**
* Sets the Title property
* @param value long with the Title
*/
    public void setTitle(String value)
    {
        getSemanticObject().setProperty(swb_title, value);
    }

    public String getTitle(String lang)
    {
        return getSemanticObject().getProperty(swb_title, null, lang);
    }

    public String getDisplayTitle(String lang)
    {
        return getSemanticObject().getLocaleProperty(swb_title, lang);
    }

    public void setTitle(String title, String lang)
    {
        getSemanticObject().setProperty(swb_title, title, lang);
    }
   /**
   * Sets the value for the property ModifiedBy
   * @param value ModifiedBy to set
   */

    public void setModifiedBy(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_modifiedBy, value.getSemanticObject());
        }else
        {
            removeModifiedBy();
        }
    }
   /**
   * Remove the value for ModifiedBy property
   */

    public void removeModifiedBy()
    {
        getSemanticObject().removeProperty(swb_modifiedBy);
    }

   /**
   * Gets the ModifiedBy
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getModifiedBy()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_modifiedBy);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Sets the value for the property Creator
   * @param value Creator to set
   */

    public void setCreator(org.semanticwb.model.User value)
    {
        if(value!=null)
        {
            getSemanticObject().setObjectProperty(swb_creator, value.getSemanticObject());
        }else
        {
            removeCreator();
        }
    }
   /**
   * Remove the value for Creator property
   */

    public void removeCreator()
    {
        getSemanticObject().removeProperty(swb_creator);
    }

   /**
   * Gets the Creator
   * @return a org.semanticwb.model.User
   */
    public org.semanticwb.model.User getCreator()
    {
         org.semanticwb.model.User ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swb_creator);
         if(obj!=null)
         {
             ret=(org.semanticwb.model.User)obj.createGenericInstance();
         }
         return ret;
    }
}
