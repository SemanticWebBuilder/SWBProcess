package org.semanticwb.process.base;


public class ORGateWayBase extends org.semanticwb.process.GateWay implements org.semanticwb.process.FlowObject,org.semanticwb.model.Traceable,org.semanticwb.model.Descriptiveable
{
       public static final org.semanticwb.platform.SemanticClass swbps_ORGateWay=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ORGateWay");
       public static final org.semanticwb.platform.SemanticClass sclass=org.semanticwb.SWBPlatform.getSemanticMgr().getVocabulary().getSemanticClass("http://www.semanticwebbuilder.org/swb4/process#ORGateWay");
    public static class ClassMgr
    {

       public static java.util.Iterator<org.semanticwb.process.ORGateWay> listORGateWays(org.semanticwb.model.SWBModel model)
       {
           java.util.Iterator it=model.getSemanticObject().getModel().listInstancesOfClass(sclass);
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ORGateWay>(it, true);
       }

       public static java.util.Iterator<org.semanticwb.process.ORGateWay> listORGateWays()
       {
           java.util.Iterator it=sclass.listInstances();
           return new org.semanticwb.model.GenericIterator<org.semanticwb.process.ORGateWay>(it, true);
       }

       public static org.semanticwb.process.ORGateWay createORGateWay(org.semanticwb.model.SWBModel model)
       {
           long id=model.getSemanticObject().getModel().getCounter(sclass);
           return org.semanticwb.process.ORGateWay.ClassMgr.createORGateWay(String.valueOf(id), model);
       }

       public static org.semanticwb.process.ORGateWay getORGateWay(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.ORGateWay)model.getSemanticObject().getModel().getGenericObject(model.getSemanticObject().getModel().getObjectUri(id,sclass),sclass);
       }

       public static org.semanticwb.process.ORGateWay createORGateWay(String id, org.semanticwb.model.SWBModel model)
       {
           return (org.semanticwb.process.ORGateWay)model.getSemanticObject().getModel().createGenericObject(model.getSemanticObject().getModel().getObjectUri(id, sclass), sclass);
       }

       public static void removeORGateWay(String id, org.semanticwb.model.SWBModel model)
       {
           model.getSemanticObject().getModel().removeSemanticObject(model.getSemanticObject().getModel().getObjectUri(id,sclass));
       }

       public static boolean hasORGateWay(String id, org.semanticwb.model.SWBModel model)
       {
           return (getORGateWay(id, model)!=null);
       }
    }

    public ORGateWayBase(org.semanticwb.platform.SemanticObject base)
    {
        super(base);
    }

    public org.semanticwb.process.ProcessSite getProcessSite()
    {
        return (org.semanticwb.process.ProcessSite)getSemanticObject().getModel().getModelObject().createGenericInstance();
    }
}
