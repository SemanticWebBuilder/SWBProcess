package org.semanticwb.process.model.base;


public abstract class MessageFlowBase extends org.semanticwb.process.model.ConnectingObject implements org.semanticwb.model.Descriptiveable,org.semanticwb.process.model.Messageable
{
       public static final org.semanticwb.platform.SemanticClass swp_MessageFlow=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#MessageFlow");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/swp#MessageFlow");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.model.MessageFlow> listMessageFlows(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageFlow>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.model.MessageFlow> listMessageFlows()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageFlow>(it, true);
       }

       public static org.semanticwb.process.model.MessageFlow getMessageFlow(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.MessageFlow)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.model.MessageFlow createMessageFlow(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.model.MessageFlow)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeMessageFlow(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasMessageFlow(String id, org.semanticwb.model.SWBModel model)
       {
           return (getMessageFlow(id, model)!=null);
       }
   public static java.util.Iterator<org.semanticwb.process.model.MessageFlow> listMessageFlowByInMessageRef(org.semanticwb.process.model.Message inmessageref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_inMessageRef, inmessageref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.MessageFlow> listMessageFlowByInMessageRef(org.semanticwb.process.model.Message inmessageref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageFlow> it=new org.semanticwb.model.GenericIterator(inmessageref.getSemanticObject().getModel().listSubjects(swp_inMessageRef,inmessageref.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.MessageFlow> listMessageFlowByOutMessageRef(org.semanticwb.process.model.Message outmessageref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_outMessageRef, outmessageref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.MessageFlow> listMessageFlowByOutMessageRef(org.semanticwb.process.model.Message outmessageref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageFlow> it=new org.semanticwb.model.GenericIterator(outmessageref.getSemanticObject().getModel().listSubjects(swp_outMessageRef,outmessageref.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.MessageFlow> listMessageFlowBySourceRef(org.semanticwb.process.model.GraphicalElement sourceref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_sourceRef, sourceref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.MessageFlow> listMessageFlowBySourceRef(org.semanticwb.process.model.GraphicalElement sourceref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageFlow> it=new org.semanticwb.model.GenericIterator(sourceref.getSemanticObject().getModel().listSubjects(swp_sourceRef,sourceref.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.MessageFlow> listMessageFlowByCategory(org.semanticwb.process.model.Category hascategory,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_hasCategory, hascategory.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.MessageFlow> listMessageFlowByCategory(org.semanticwb.process.model.Category hascategory)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageFlow> it=new org.semanticwb.model.GenericIterator(hascategory.getSemanticObject().getModel().listSubjects(swp_hasCategory,hascategory.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.MessageFlow> listMessageFlowByMessageRef(org.semanticwb.process.model.Message messageref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_messageRef, messageref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.MessageFlow> listMessageFlowByMessageRef(org.semanticwb.process.model.Message messageref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageFlow> it=new org.semanticwb.model.GenericIterator(messageref.getSemanticObject().getModel().listSubjects(swp_messageRef,messageref.getSemanticObject()));
       return it;
   }
   public static java.util.Iterator<org.semanticwb.process.model.MessageFlow> listMessageFlowByTargetRef(org.semanticwb.process.model.GraphicalElement targetref,org.semanticwb.model.SWBModel model)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageFlow> it=new org.semanticwb.model.GenericIterator(model.getSemanticObject().getModel().listSubjects(swp_targetRef, targetref.getSemanticObject()));
       return it;
   }

   public static java.util.Iterator<org.semanticwb.process.model.MessageFlow> listMessageFlowByTargetRef(org.semanticwb.process.model.GraphicalElement targetref)
   {
       org.semanticwb.model.GenericIterator<org.semanticwb.process.model.MessageFlow> it=new org.semanticwb.model.GenericIterator(targetref.getSemanticObject().getModel().listSubjects(swp_targetRef,targetref.getSemanticObject()));
       return it;
   }
    }

    public MessageFlowBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public void setInMessageRef(org.semanticwb.process.model.Message value)
    {
        getSemanticObject().setObjectProperty(swp_inMessageRef, value.getSemanticObject());
    }

    public void removeInMessageRef()
    {
        getSemanticObject().removeProperty(swp_inMessageRef);
    }


    public org.semanticwb.process.model.Message getInMessageRef()
    {
         org.semanticwb.process.model.Message ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_inMessageRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Message)obj.createGenericInstance();
         }
         return ret;
    }

    public void setOutMessageRef(org.semanticwb.process.model.Message value)
    {
        getSemanticObject().setObjectProperty(swp_outMessageRef, value.getSemanticObject());
    }

    public void removeOutMessageRef()
    {
        getSemanticObject().removeProperty(swp_outMessageRef);
    }


    public org.semanticwb.process.model.Message getOutMessageRef()
    {
         org.semanticwb.process.model.Message ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_outMessageRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Message)obj.createGenericInstance();
         }
         return ret;
    }

    public void setMessageRef(org.semanticwb.process.model.Message value)
    {
        getSemanticObject().setObjectProperty(swp_messageRef, value.getSemanticObject());
    }

    public void removeMessageRef()
    {
        getSemanticObject().removeProperty(swp_messageRef);
    }


    public org.semanticwb.process.model.Message getMessageRef()
    {
         org.semanticwb.process.model.Message ret=null;
         org.semanticwb.platform.SemanticObject obj=getSemanticObject().getObjectProperty(swp_messageRef);
         if(obj!=null)
         {
             ret=(org.semanticwb.process.model.Message)obj.createGenericInstance();
         }
         return ret;
    }
}
