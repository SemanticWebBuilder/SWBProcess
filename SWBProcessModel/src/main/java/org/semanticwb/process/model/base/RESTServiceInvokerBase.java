package org.semanticwb.process.model.base;


   /**
   * Service to make a REST call 
   */
public abstract class RESTServiceInvokerBase extends org.semanticwb.process.model.ProcessService 
{
    public static final org.semanticwb.platform.SemanticClass swp_WebServiceParameter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#WebServiceParameter");
    public static final org.semanticwb.platform.SemanticProperty swp_hasRESTHeaderParameter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasRESTHeaderParameter");
    public static final org.semanticwb.platform.SemanticProperty swp_hasRESTInputParameter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasRESTInputParameter");
    public static final org.semanticwb.platform.SemanticProperty swp_httpVerb=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#httpVerb");
    public static final org.semanticwb.platform.SemanticProperty swp_endpointURL=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#endpointURL");
    public static final org.semanticwb.platform.SemanticProperty swp_hasRESTOutputParameter=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticProperty("http://www.semanticwebbuilder.org/swb4/process#hasRESTOutputParameter");
   /**
   * Service to make a REST call
   */
    public static final org.semanticwb.platform.SemanticClass swp_RESTServiceInvoker=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#RESTServiceInvoker");
   /**
   * The semantic class that represents the currentObject
   */
    public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#RESTServiceInvoker");

    public static class ClassMgr
    {
       /**
       * Returns a list of RESTServiceInvoker for a model
       * @param model Model to find
       * @return Iterator of org.semanticwb.process.model.RESTServiceInvoker
       */

        public static java.util.Iterator<org.semanticwb.process.model.RESTServiceInvoker> listRESTServiceInvokers(org.semanticwb.model.SWBModel model)
        {
            java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RESTServiceInvoker>(it, true);
        }
       /**
       * Returns a list of org.semanticwb.process.model.RESTServiceInvoker for all models
       * @return Iterator of org.semanticwb.process.model.RESTServiceInvoker
       */

        public static java.util.Iterator<org.semanticwb.process.model.RESTServiceInvoker> listRESTServiceInvokers()
        {
            java.util.Iterator it=sclass.listInstances();
            return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RESTServiceInvoker>(it, true);
        }

        public static org.semanticwb.process.model.RESTServiceInvoker createRESTServiceInvoker(org.semanticwb.model.SWBModel model)
        {
            long id=model.getSemanticObject().getModel().getCounter(sclass);
            return org.semanticwb.process.model.RESTServiceInvoker.ClassMgr.createRESTServiceInvoker(String.valueOf(id), model);
        }
       /**
       * Gets a org.semanticwb.process.model.RESTServiceInvoker
       * @param id Identifier for org.semanticwb.process.model.RESTServiceInvoker
       * @param model Model of the org.semanticwb.process.model.RESTServiceInvoker
       * @return A org.semanticwb.process.model.RESTServiceInvoker
       */
        public static org.semanticwb.process.model.RESTServiceInvoker getRESTServiceInvoker(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.RESTServiceInvoker)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Create a org.semanticwb.process.model.RESTServiceInvoker
       * @param id Identifier for org.semanticwb.process.model.RESTServiceInvoker
       * @param model Model of the org.semanticwb.process.model.RESTServiceInvoker
       * @return A org.semanticwb.process.model.RESTServiceInvoker
       */
        public static org.semanticwb.process.model.RESTServiceInvoker createRESTServiceInvoker(String id, org.semanticwb.model.SWBModel model)
        {
            return (org.semanticwb.process.model.RESTServiceInvoker)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
        }
       /**
       * Remove a org.semanticwb.process.model.RESTServiceInvoker
       * @param id Identifier for org.semanticwb.process.model.RESTServiceInvoker
       * @param model Model of the org.semanticwb.process.model.RESTServiceInvoker
       */
        public static void removeRESTServiceInvoker(String id, org.semanticwb.model.SWBModel model)
        {
            model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
        }
       /**
       * Returns true if exists a org.semanticwb.process.model.RESTServiceInvoker
       * @param id Identifier for org.semanticwb.process.model.RESTServiceInvoker
       * @param model Model of the org.semanticwb.process.model.RESTServiceInvoker
       * @return true if the org.semanticwb.process.model.RESTServiceInvoker exists, false otherwise
       */

        public static boolean hasRESTServiceInvoker(String id, org.semanticwb.model.SWBModel model)
        {
            return (getRESTServiceInvoker(id, model)!=null);
        }
       /**
       * Gets all org.semanticwb.process.model.RESTServiceInvoker with a determined RESTHeaderParameter
       * @param value RESTHeaderParameter of the type org.semanticwb.process.model.WebServiceParameter
       * @param model Model of the org.semanticwb.process.model.RESTServiceInvoker
       * @return Iterator with all the org.semanticwb.process.model.RESTServiceInvoker
       */

        public static java.util.Iterator<org.semanticwb.process.model.RESTServiceInvoker> listRESTServiceInvokerByRESTHeaderParameter(org.semanticwb.process.model.WebServiceParameter value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RESTServiceInvoker> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasRESTHeaderParameter, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RESTServiceInvoker with a determined RESTHeaderParameter
       * @param value RESTHeaderParameter of the type org.semanticwb.process.model.WebServiceParameter
       * @return Iterator with all the org.semanticwb.process.model.RESTServiceInvoker
       */

        public static java.util.Iterator<org.semanticwb.process.model.RESTServiceInvoker> listRESTServiceInvokerByRESTHeaderParameter(org.semanticwb.process.model.WebServiceParameter value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RESTServiceInvoker> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasRESTHeaderParameter,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RESTServiceInvoker with a determined RESTInputParameter
       * @param value RESTInputParameter of the type org.semanticwb.process.model.WebServiceParameter
       * @param model Model of the org.semanticwb.process.model.RESTServiceInvoker
       * @return Iterator with all the org.semanticwb.process.model.RESTServiceInvoker
       */

        public static java.util.Iterator<org.semanticwb.process.model.RESTServiceInvoker> listRESTServiceInvokerByRESTInputParameter(org.semanticwb.process.model.WebServiceParameter value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RESTServiceInvoker> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasRESTInputParameter, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RESTServiceInvoker with a determined RESTInputParameter
       * @param value RESTInputParameter of the type org.semanticwb.process.model.WebServiceParameter
       * @return Iterator with all the org.semanticwb.process.model.RESTServiceInvoker
       */

        public static java.util.Iterator<org.semanticwb.process.model.RESTServiceInvoker> listRESTServiceInvokerByRESTInputParameter(org.semanticwb.process.model.WebServiceParameter value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RESTServiceInvoker> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasRESTInputParameter,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RESTServiceInvoker with a determined RESTOutputParameter
       * @param value RESTOutputParameter of the type org.semanticwb.process.model.WebServiceParameter
       * @param model Model of the org.semanticwb.process.model.RESTServiceInvoker
       * @return Iterator with all the org.semanticwb.process.model.RESTServiceInvoker
       */

        public static java.util.Iterator<org.semanticwb.process.model.RESTServiceInvoker> listRESTServiceInvokerByRESTOutputParameter(org.semanticwb.process.model.WebServiceParameter value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RESTServiceInvoker> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_hasRESTOutputParameter, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RESTServiceInvoker with a determined RESTOutputParameter
       * @param value RESTOutputParameter of the type org.semanticwb.process.model.WebServiceParameter
       * @return Iterator with all the org.semanticwb.process.model.RESTServiceInvoker
       */

        public static java.util.Iterator<org.semanticwb.process.model.RESTServiceInvoker> listRESTServiceInvokerByRESTOutputParameter(org.semanticwb.process.model.WebServiceParameter value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RESTServiceInvoker> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_hasRESTOutputParameter,value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RESTServiceInvoker with a determined ServiceTask
       * @param value ServiceTask of the type org.semanticwb.process.model.ServiceTask
       * @param model Model of the org.semanticwb.process.model.RESTServiceInvoker
       * @return Iterator with all the org.semanticwb.process.model.RESTServiceInvoker
       */

        public static java.util.Iterator<org.semanticwb.process.model.RESTServiceInvoker> listRESTServiceInvokerByServiceTask(org.semanticwb.process.model.ServiceTask value,org.semanticwb.model.SWBModel model)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RESTServiceInvoker> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjectsByClass(swp_serviceTaskInv, value.getSemanticObject(),sclass));
            return it;
        }
       /**
       * Gets all org.semanticwb.process.model.RESTServiceInvoker with a determined ServiceTask
       * @param value ServiceTask of the type org.semanticwb.process.model.ServiceTask
       * @return Iterator with all the org.semanticwb.process.model.RESTServiceInvoker
       */

        public static java.util.Iterator<org.semanticwb.process.model.RESTServiceInvoker> listRESTServiceInvokerByServiceTask(org.semanticwb.process.model.ServiceTask value)
        {
            org.semanticwb.model.GenericIterator<org.semanticwb.process.model.RESTServiceInvoker> it=new org.semanticwb.model.GenericIterator(value.getSemanticObject().getModel().listSubjectsByClass(swp_serviceTaskInv,value.getSemanticObject(),sclass));
            return it;
        }
    }

    public static RESTServiceInvokerBase.ClassMgr getRESTServiceInvokerClassMgr()
    {
        return new RESTServiceInvokerBase.ClassMgr();
    }

   /**
   * Constructs a RESTServiceInvokerBase with a SemanticObject
   * @param base The SemanticObject with the properties for the RESTServiceInvoker
   */
    public RESTServiceInvokerBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }
   /**
   * Gets all the org.semanticwb.process.model.WebServiceParameter
   * @return A GenericIterator with all the org.semanticwb.process.model.WebServiceParameter
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WebServiceParameter> listRESTHeaderParameters()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WebServiceParameter>(getSemanticObject().listObjectProperties(swp_hasRESTHeaderParameter));
    }

   /**
   * Gets true if has a RESTHeaderParameter
   * @param value org.semanticwb.process.model.WebServiceParameter to verify
   * @return true if the org.semanticwb.process.model.WebServiceParameter exists, false otherwise
   */
    public boolean hasRESTHeaderParameter(org.semanticwb.process.model.WebServiceParameter value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasRESTHeaderParameter,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a RESTHeaderParameter
   * @param value org.semanticwb.process.model.WebServiceParameter to add
   */

    public void addRESTHeaderParameter(org.semanticwb.process.model.WebServiceParameter value)
    {
        getSemanticObject().addObjectProperty(swp_hasRESTHeaderParameter, value.getSemanticObject());
    }
   /**
   * Removes all the RESTHeaderParameter
   */

    public void removeAllRESTHeaderParameter()
    {
        getSemanticObject().removeProperty(swp_hasRESTHeaderParameter);
    }
   /**
   * Removes a RESTHeaderParameter
   * @param value org.semanticwb.process.model.WebServiceParameter to remove
   */

    public void removeRESTHeaderParameter(org.semanticwb.process.model.WebServiceParameter value)
    {
        getSemanticObject().removeObjectProperty(swp_hasRESTHeaderParameter,value.getSemanticObject());
    }

   /**
   * Gets the RESTHeaderParameter
   * @return a org.semanticwb.process.model.WebServiceParameter
   */
    public org.semanticwb.process.model.WebServiceParameter getRESTHeaderParameter()
    {
         org.semanticwb.process.model.WebServiceParameter ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasRESTHeaderParameter);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.WebServiceParameter)obj.createGenericInstance();
         }
         return ret;
    }
   /**
   * Gets all the org.semanticwb.process.model.WebServiceParameter
   * @return A GenericIterator with all the org.semanticwb.process.model.WebServiceParameter
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WebServiceParameter> listRESTInputParameters()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WebServiceParameter>(getSemanticObject().listObjectProperties(swp_hasRESTInputParameter));
    }

   /**
   * Gets true if has a RESTInputParameter
   * @param value org.semanticwb.process.model.WebServiceParameter to verify
   * @return true if the org.semanticwb.process.model.WebServiceParameter exists, false otherwise
   */
    public boolean hasRESTInputParameter(org.semanticwb.process.model.WebServiceParameter value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasRESTInputParameter,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a RESTInputParameter
   * @param value org.semanticwb.process.model.WebServiceParameter to add
   */

    public void addRESTInputParameter(org.semanticwb.process.model.WebServiceParameter value)
    {
        getSemanticObject().addObjectProperty(swp_hasRESTInputParameter, value.getSemanticObject());
    }
   /**
   * Removes all the RESTInputParameter
   */

    public void removeAllRESTInputParameter()
    {
        getSemanticObject().removeProperty(swp_hasRESTInputParameter);
    }
   /**
   * Removes a RESTInputParameter
   * @param value org.semanticwb.process.model.WebServiceParameter to remove
   */

    public void removeRESTInputParameter(org.semanticwb.process.model.WebServiceParameter value)
    {
        getSemanticObject().removeObjectProperty(swp_hasRESTInputParameter,value.getSemanticObject());
    }

   /**
   * Gets the RESTInputParameter
   * @return a org.semanticwb.process.model.WebServiceParameter
   */
    public org.semanticwb.process.model.WebServiceParameter getRESTInputParameter()
    {
         org.semanticwb.process.model.WebServiceParameter ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasRESTInputParameter);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.WebServiceParameter)obj.createGenericInstance();
         }
         return ret;
    }

/**
* Gets the HttpVerb property
* @return String with the HttpVerb
*/
    public String getHttpVerb()
    {
        return getSemanticObject().getProperty(swp_httpVerb);
    }

/**
* Sets the HttpVerb property
* @param value long with the HttpVerb
*/
    public void setHttpVerb(String value)
    {
        getSemanticObject().setProperty(swp_httpVerb, value);
    }

/**
* Gets the EndpointURL property
* @return String with the EndpointURL
*/
    public String getEndpointURL()
    {
        return getSemanticObject().getProperty(swp_endpointURL);
    }

/**
* Sets the EndpointURL property
* @param value long with the EndpointURL
*/
    public void setEndpointURL(String value)
    {
        getSemanticObject().setProperty(swp_endpointURL, value);
    }
   /**
   * Gets all the org.semanticwb.process.model.WebServiceParameter
   * @return A GenericIterator with all the org.semanticwb.process.model.WebServiceParameter
   */

    public org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WebServiceParameter> listRESTOutputParameters()
    {
        return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.WebServiceParameter>(getSemanticObject().listObjectProperties(swp_hasRESTOutputParameter));
    }

   /**
   * Gets true if has a RESTOutputParameter
   * @param value org.semanticwb.process.model.WebServiceParameter to verify
   * @return true if the org.semanticwb.process.model.WebServiceParameter exists, false otherwise
   */
    public boolean hasRESTOutputParameter(org.semanticwb.process.model.WebServiceParameter value)
    {
        boolean ret=false;
        if(value!=null)
        {
           ret=getSemanticObject().hasObjectProperty(swp_hasRESTOutputParameter,value.getSemanticObject());
        }
        return ret;
    }
   /**
   * Adds a RESTOutputParameter
   * @param value org.semanticwb.process.model.WebServiceParameter to add
   */

    public void addRESTOutputParameter(org.semanticwb.process.model.WebServiceParameter value)
    {
        getSemanticObject().addObjectProperty(swp_hasRESTOutputParameter, value.getSemanticObject());
    }
   /**
   * Removes all the RESTOutputParameter
   */

    public void removeAllRESTOutputParameter()
    {
        getSemanticObject().removeProperty(swp_hasRESTOutputParameter);
    }
   /**
   * Removes a RESTOutputParameter
   * @param value org.semanticwb.process.model.WebServiceParameter to remove
   */

    public void removeRESTOutputParameter(org.semanticwb.process.model.WebServiceParameter value)
    {
        getSemanticObject().removeObjectProperty(swp_hasRESTOutputParameter,value.getSemanticObject());
    }

   /**
   * Gets the RESTOutputParameter
   * @return a org.semanticwb.process.model.WebServiceParameter
   */
    public org.semanticwb.process.model.WebServiceParameter getRESTOutputParameter()
    {
         org.semanticwb.process.model.WebServiceParameter ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_hasRESTOutputParameter);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.WebServiceParameter)obj.createGenericInstance();
         }
         return ret;
    }
}
